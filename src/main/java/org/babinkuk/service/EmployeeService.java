package org.babinkuk.service;

import org.babinkuk.common.ApiResponse;
import org.babinkuk.exception.EmployeeException;
import org.babinkuk.exception.EmployeeNotFoundException;
import org.babinkuk.vo.EmployeeVO;

public interface EmployeeService {
	
	public Iterable<EmployeeVO> getAllEmployees();
	
	public EmployeeVO findById(int id) throws EmployeeNotFoundException;
	
	public EmployeeVO findByEmail(String email) throws EmployeeNotFoundException;
	
	public ApiResponse saveEmployee(EmployeeVO employeeVO) throws EmployeeException;
	
	public ApiResponse deleteEmployee(int id) throws EmployeeNotFoundException;
	
	public ApiResponse sendEmployee(EmployeeVO employeeVO, boolean isTopicDestination);
	
}
