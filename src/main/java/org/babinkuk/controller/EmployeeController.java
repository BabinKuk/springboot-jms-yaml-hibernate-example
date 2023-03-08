package org.babinkuk.controller;

import lombok.extern.slf4j.Slf4j;

import org.babinkuk.service.EmployeeService;
import org.babinkuk.validator.EmployeeValidator;
import org.babinkuk.validator.EmployeeValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.common.ApiResponse;
import org.babinkuk.entity.Employee;
import org.babinkuk.exception.EmployeeException;
import org.babinkuk.exception.EmployeeNotFoundException;
import org.babinkuk.exception.EmployeeValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.babinkuk.controller.Api.ROOT;
import static org.babinkuk.controller.Api.EMPLOYEES;

@Slf4j
@RestController
@RequestMapping(ROOT + EMPLOYEES)
public class EmployeeController {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	// employee service
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeValidatorFactory validatorFactory;
	
	@Autowired
	private ObjectMapper mapper;
	
	public EmployeeController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * Create new employee (using topic)
	 *
	 * @param employee
	 * @return ResponseEntity
	 */
	@PostMapping("/topic")
	public ResponseEntity<ApiResponse> addEmployeeTopic(@RequestBody Employee employee) throws JsonProcessingException {
		log.info("Called EmployeeController.addEmployeeTopic({})", mapper.writeValueAsString(employee));
		
		// in case id is passed in json, set to 0
		// this is to force a save of new item ... instead of update
		employee.setId(0);
		
		employee = validatorFactory.getValidator().validate(employee, true);
		
		try {
	        //return new ResponseEntity<>(MESSAGE_SENT_TO_QUEUE, HttpStatus.OK);
			//return ResponseEntity.ok().body(employeeResponse);
			return ResponseEntity.of(Optional.ofNullable(employeeService.sendEmployee(employee, true)));
			
		} catch (Exception exception) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Create new employee (using queue)
	 *
	 * @param employee
	 * @return ResponseEntity
	 */
	@PostMapping("/queue")
	public ResponseEntity<ApiResponse> addEmployeeQueue(@RequestBody Employee employee) throws JsonProcessingException {
		log.info("Called EmployeeController.addEmployeeQueue({})", mapper.writeValueAsString(employee));
		
		// in case id is passed in json, set to 0
		// this is to force a save of new item ... instead of update
		employee.setId(0);
		
		employee = validatorFactory.getValidator().validate(employee, true);
				
		try {
//			Employee emp = new Employee(
//					employee.getFirstName(),
//					employee.getLastName(),
//					employee.getEmail());
			
	        //return new ResponseEntity<>(MESSAGE_SENT_TO_QUEUE, HttpStatus.OK);
			//return ResponseEntity.ok().body(employeeResponse);
			return ResponseEntity.of(Optional.ofNullable(employeeService.sendEmployee(employee, false)));
			
		} catch (Exception exception) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * expose PUT "/employees/topic"
	 * 
	 * @param employee
	 * @return
	 * @throws JsonProcessingException
	 */
	@PutMapping("/topic")
	public ResponseEntity<ApiResponse> updateEmployeeTopic(@RequestBody Employee employee) throws JsonProcessingException {
		log.info("Called EmployeeController.updateEmployeeTopic({})", mapper.writeValueAsString(employee));

		employee = validatorFactory.getValidator().validate(employee, false);
		
		try {
			return ResponseEntity.of(Optional.ofNullable(employeeService.sendEmployee(employee, true)));

		} catch (Exception exception) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * expose PUT "/employees/queue"
	 * 
	 * @param employee
	 * @return
	 * @throws JsonProcessingException
	 */
	@PutMapping("/queue")
	public ResponseEntity<ApiResponse> updateEmployeeQueue(@RequestBody Employee employee) throws JsonProcessingException {
		log.info("Called EmployeeController.updateEmployeeQueue({})", mapper.writeValueAsString(employee));

		employee = validatorFactory.getValidator().validate(employee, false);
		
		try {
			return ResponseEntity.of(Optional.ofNullable(employeeService.sendEmployee(employee, false)));
			
		} catch (Exception exception) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * get employee list
	 *
	 * @param 
	 * @return ResponseEntity
	 */
	@GetMapping("/get")
	//public ResponseEntity<Iterable<Employee>> getAllEmployees() {
	public ResponseEntity<Iterable<Employee>> getAllEmployees() {
		log.info("Called EmployeeController.getAllEmployees");
		//return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
		//return ResponseEntity.ok().body(employeeService.findAll());
		return ResponseEntity.of(Optional.ofNullable(employeeService.getAllEmployees()));
	}
	
	// expose GET "/employees/{employeeId}"
	@GetMapping("/get/{employeeId}")
	public ResponseEntity<Employee> getEmployee(@PathVariable int employeeId) {
		log.info("Called EmployeeController.getEmployee(employeeId={})", employeeId);
		
		//return new ResponseEntity<>(employee, HttpStatus.OK);
		//return ResponseEntity.ok().body(employee);
		return ResponseEntity.of(Optional.ofNullable(employeeService.findById(employeeId)));
	}
	
	// expose POST "/employees"
	@PostMapping("")
	public ResponseEntity<ApiResponse> addEmployee(@RequestBody Employee employee) throws JsonProcessingException {
		log.info("Called EmployeeController.addEmployee({})", mapper.writeValueAsString(employee));
		
		// in case id is passed in json, set to 0
		// this is to force a save of new item ... instead of update
		employee.setId(0);
		
		employee = validatorFactory.getValidator().validate(employee, true);
		
		//return employee;
		return ResponseEntity.of(Optional.ofNullable(employeeService.saveEmployee(employee)));
	}
	
	// expose PUT "/employees"
	@PutMapping("")
	public ResponseEntity<ApiResponse> updateEmployee(@RequestBody Employee employee) throws JsonProcessingException {
		log.info("Called EmployeeController.updateEmployee({})", mapper.writeValueAsString(employee));

		employee = validatorFactory.getValidator().validate(employee, false);

//		Employee dbEmployee = employeeService.findById(employee.getId());
//		
//		if (dbEmployee == null) {
//			throw new EmployeeException("Employee id not found " + employee.getId());
//		}
		
		//return employee;
		return ResponseEntity.of(Optional.ofNullable(employeeService.saveEmployee(employee)));
	}
	
	// expose DELETE "/{employeeId}""
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable int employeeId) {
		log.info("Called EmployeeController.deleteEmployee(employeeId={})", employeeId);
		
		Employee employee = validatorFactory.getValidator().validate(employeeId);
		
		return ResponseEntity.of(Optional.ofNullable(employeeService.deleteEmployee(employeeId)));
	}

	@ExceptionHandler
	public ResponseEntity<ApiResponse> handleException(Exception exc) {
		
		return new ApiResponse(HttpStatus.BAD_REQUEST, exc.getMessage()).toEntity();
	}
	
	@ExceptionHandler
	public ResponseEntity<ApiResponse> handleException(EmployeeException exc) {

		return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage()).toEntity();
	}

	@ExceptionHandler
	public ResponseEntity<ApiResponse> handleException(EmployeeNotFoundException exc) {

		return new ApiResponse(HttpStatus.OK, exc.getMessage()).toEntity();
	}
	
	@ExceptionHandler
	public ResponseEntity<ApiResponse> handleException(EmployeeValidationException exc) {
		ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
		apiResponse.setErrors(exc.getValidationErrors());
		return apiResponse.toEntity();
	}
	
////	@ExceptionHandler
////    public ResponseEntity<EmployeeErrorResponse> handleResponseMessage(String message) {
////
////        EmployeeErrorResponse responseMsg = new EmployeeErrorResponse();
////
////        responseMsg.setStatus(HttpStatus.OK.value());
////        responseMsg.setMessage(message);
////        responseMsg.setTimeStamp(System.currentTimeMillis());
////
////        return new ResponseEntity<>(responseMsg, HttpStatus.OK);
////    }
//	
////    @ExceptionHandler
////    public ResponseEntity<EmployeeResponse> handleException(Exception exc) {
////
////        EmployeeResponse error = new EmployeeResponse();
////
////        error.setStatus(HttpStatus.BAD_REQUEST.value());
////        error.setMessage(exc.getMessage());
////        
////        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
////    }
////    
////    @ExceptionHandler
////    public ResponseEntity<EmployeeResponse> handleException(EmployeeException exc) {
////
////        EmployeeResponse error = new EmployeeResponse();
////
////        error.setStatus(HttpStatus.NOT_FOUND.value());
////        error.setMessage(exc.getMessage());
////        
////        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
////    }

}
