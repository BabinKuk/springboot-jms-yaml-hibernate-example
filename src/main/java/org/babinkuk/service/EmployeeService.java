package org.babinkuk.service;

import org.babinkuk.common.ApiResponse;
import org.babinkuk.entity.Employee;
import org.babinkuk.exception.EmployeeException;
import org.babinkuk.exception.EmployeeNotFoundException;

public interface EmployeeService {
	
	public Iterable<Employee> getAllEmployees();
	
	public Employee findById(int id) throws EmployeeNotFoundException;
	
	public Employee findByEmail(String email) throws EmployeeNotFoundException;
	
	public ApiResponse saveEmployee(Employee employee) throws EmployeeException;
	
	public ApiResponse deleteEmployee(int id) throws EmployeeNotFoundException;
	
	public ApiResponse sendEmployee(Employee employee, boolean isTopicDestination);
	
}
