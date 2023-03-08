package org.babinkuk.listener;

import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.entity.Employee;
import org.babinkuk.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class EmployeeListener {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private ObjectMapper mapper;
	
	// employee service
	private EmployeeService employeeService;
	
	public EmployeeListener(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
    
	@JmsListener(destination = "${emp.jms.topic}", containerFactory = "empJmsContFactory")
	public void getEmployeeListener1(Employee emp) throws JsonProcessingException {
		log.info("Employee topic listener1: {}", mapper.writeValueAsString(emp));
		
		processEmployee(emp);
	}
	
//	@JmsListener(destination = "${emp.jms.topic}", containerFactory = "empJmsContFactory")
//	public void getEmployeeListener2(Employee emp) throws JsonProcessingException {
//		log.info("Employee listener2: {}", mapper.writeValueAsString(emp));
//		
//		processEmployee(emp);
//	}
	
	@JmsListener(destination = "${emp.jms.queue}")
	public void getEmployeeQueueListener1(Employee emp) throws JsonProcessingException {
		log.info("Employee queue listener1: {}", mapper.writeValueAsString(emp));
		
		processEmployee(emp);
	}
	
//	@JmsListener(destination = "${emp.jms.queue}")
//	public void getEmployeeQueueListener2(Employee emp) throws JsonProcessingException {
//		log.info("Employee queue listener2: {}", mapper.writeValueAsString(emp));
//		
//		processEmployee(emp);
//	}
	
	public void processEmployee(Employee employee) throws JsonProcessingException {
		log.info("Employee processing: {}", mapper.writeValueAsString(employee));
		
		// in case id is passed in json, set to 0
		// this is to force a save of new item ... instead of update
		employee.setId(0);
		
		employeeService.saveEmployee(employee);
	}
}