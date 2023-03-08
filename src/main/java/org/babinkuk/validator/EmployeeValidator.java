package org.babinkuk.validator;

import org.babinkuk.entity.Employee;
import org.babinkuk.exception.EmployeeNotFoundException;
import org.babinkuk.exception.EmployeeValidationException;

public interface EmployeeValidator {
	
	public Employee validate(Employee employee, boolean isInsert) throws EmployeeValidationException;

	public Employee validate(int id) throws EmployeeNotFoundException;

}
