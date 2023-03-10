package org.babinkuk.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.service.EmployeeService;
import org.babinkuk.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nikola
 *
 */
@Component
public class EmployeeListener {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private EmployeeService employeeService;
	
	public EmployeeListener(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
    
	/**
	 * JMS topic message listener implemenatation
	 * 
	 * @param empVO
	 * @throws JsonProcessingException
	 */
	@JmsListener(destination = "${emp.jms.topic}", containerFactory = "empJmsContFactory")
	public void getEmployeeTopicListener(EmployeeVO empVO) throws JsonProcessingException {
		log.info("Employee topic listener: {}", mapper.writeValueAsString(empVO));
		
		processEmployee(empVO);
	}
	
//	@JmsListener(destination = "${emp.jms.topic}", containerFactory = "empJmsContFactory")
//	public void getEmployeeListener2(Employee emp) throws JsonProcessingException {
//		log.info("Employee listener2: {}", mapper.writeValueAsString(emp));
//		
//		processEmployee(emp);
//	}
	
	/**
	 * JMS queue message listener implemenatation
	 * 
	 * @param empVO
	 * @throws JsonProcessingException
	 */
	@JmsListener(destination = "${emp.jms.queue}")
	public void getEmployeeQueueListener(EmployeeVO empVO) throws JsonProcessingException {
		log.info("Employee queue listener: {}", mapper.writeValueAsString(empVO));
		
		processEmployee(empVO);
	}
	
//	@JmsListener(destination = "${emp.jms.queue}")
//	public void getEmployeeQueueListener2(Employee emp) throws JsonProcessingException {
//		log.info("Employee queue listener2: {}", mapper.writeValueAsString(emp));
//		
//		processEmployee(emp);
//	}
	
	/**
	 * processing employee
	 * 
	 * @param employeeVO
	 * @throws JsonProcessingException
	 */
	public void processEmployee(EmployeeVO employeeVO) throws JsonProcessingException {
		log.info("Employee processing: {}", mapper.writeValueAsString(employeeVO));
		
		// in case id is passed in json, set to 0
		// this is to force a save of new item ... instead of update
		employeeVO.setId(0);
		
		employeeService.saveEmployee(employeeVO);
	}
}