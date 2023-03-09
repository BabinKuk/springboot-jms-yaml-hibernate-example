package org.babinkuk.service;

import java.util.Optional;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.common.ApiResponse;
import org.babinkuk.dao.EmployeeRepository;
import org.babinkuk.entity.Employee;
import org.babinkuk.exception.EmployeeException;
import org.babinkuk.exception.EmployeeNotFoundException;
import org.babinkuk.mapper.EmployeeMapper;
import org.babinkuk.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	private static String MESSAGE_SUCCESS = "Employee JMS sending success";
	private static String MESSAGE_FAILED = "Employee JMS sending failed";
	private static String EMPLOYEE_SAVE_SUCCESS = "Employee saving success";
	private static String EMPLOYEE_SAVE_FAILED = "Employee saving failed";
	private static String EMPLOYEE_DELETE_SUCCESS = "Employee delete success";
	private static String EMPLOYEE_DELETE_FAILED = "Employee delete failed";
	
	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	private ObjectMapper mapper;
		
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public EmployeeVO findById(int id) throws EmployeeNotFoundException {
		
		Optional<Employee> result = employeeRepository.findById(id);
		
		Employee employee = null;
		
		if (result.isPresent()) {
			employee = result.get();
			
			// mapping
			EmployeeVO employeeVO = employeeMapper.toVO(employee);
			log.info("empVO ({})", employeeVO);
			
			return employeeVO;
		} else {
			// employee not found
			String message = String.format("Employee with id=%s not found.", id);
			log.warn(message);
			throw new EmployeeNotFoundException(message);
		}
	}
	
	@Override
	public EmployeeVO findByEmail(String email) throws EmployeeNotFoundException {
		
		EmployeeVO employeeVO = null;
		
		Optional<Employee> result = employeeRepository.findByEmail(email);
		
		Employee employee = null;
		
		if (result.isPresent()) {
			employee = result.get();
			
			// mapping
			employeeVO = employeeMapper.toVO(employee);
			log.info("empVO ({})", employeeVO);
		} else {
			// employee not found
			String message = String.format("Employee with email=%s not found.", email);
			log.warn(message);
			//throw new EmployeeNotFoundException(message);
		}

		return employeeVO;
	}
		
	@Override
	public ApiResponse saveEmployee(EmployeeVO employeeVO) throws EmployeeException {
		
		ApiResponse response = new ApiResponse();
		
		response.setStatus(HttpStatus.OK);
		response.setMessage(EMPLOYEE_SAVE_SUCCESS);
		
		Employee employee = null;
		
		// mapping
		employee = employeeMapper.toEntity(employeeVO);
		log.info("employee ({})", employee);
		
//		employee = validator.validate(employee, isInsert);
		
		employeeRepository.save(employee);
		
		return response;
	}
	
	@Override
	public ApiResponse deleteEmployee(int id) throws EmployeeNotFoundException {
		
		ApiResponse response = new ApiResponse();
		
		response.setStatus(HttpStatus.OK);
		response.setMessage(EMPLOYEE_DELETE_SUCCESS);
		
//		Optional<Employee> result = employeeRepository.findById(id);
//		
//		Employee employee = null;
//		
//		if (result.isPresent()) {
//			employee = result.get();
//		} else {
//			// employee not found
//			String message = String.format("Employee with id=%s not found.", id);
//			log.warn(message);
//			throw new EmployeeNotFoundException(message);
//		}
		
		employeeRepository.deleteById(id);
		
		return response;
	}

	@Override
	public Iterable<EmployeeVO> getAllEmployees() {
		return employeeMapper.toVO(employeeRepository.findAll());
	}

	@Override
	public ApiResponse sendEmployee(EmployeeVO employeeVO, boolean isTopicDestination) {
		
		ApiResponse response = new ApiResponse();
		
		Topic empTopic;
		Queue empQueue;
		
		try {
			log.info("Sending Employee({})", mapper.writeValueAsString(employeeVO));
			
			if (isTopicDestination) {
				empTopic = jmsTemplate.getConnectionFactory().createConnection()
						.createSession().createTopic("EmpTopic");
				jmsTemplate.convertAndSend(empTopic, employeeVO);
			} else {
				empQueue = jmsTemplate.getConnectionFactory().createConnection()
						.createSession().createQueue("EmpQueue");
				jmsTemplate.convertAndSend(empQueue, employeeVO);
			}
			response.setStatus(HttpStatus.OK);
	        response.setMessage(MESSAGE_SUCCESS);
	        
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setMessage(MESSAGE_FAILED);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setMessage(MESSAGE_FAILED);
		}
		
		return response;
	}

}
