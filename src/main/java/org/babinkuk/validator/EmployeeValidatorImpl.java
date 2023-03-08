package org.babinkuk.validator;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.entity.Employee;
import org.babinkuk.exception.EmployeeException;
import org.babinkuk.exception.EmployeeNotFoundException;
import org.babinkuk.exception.EmployeeValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidatorImpl implements EmployeeValidator {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private EmployeeValidatorHelper validatorHelper;
	
	@Override
	public Employee validate(Employee employee, boolean isInsert) throws EmployeeValidationException {
		log.info("validating employee");
		
		List<ValidatorException> exceptionList = new LinkedList<ValidatorException>();
		
		try {
			validatorHelper.validateFirstName(employee.getFirstName());
		} catch (ValidatorException e) {
			exceptionList.add(e);
		}
		
		try {
			validatorHelper.validateLastName(employee.getLastName());
		} catch (ValidatorException e) {
			exceptionList.add(e);
		}
		
		try {
			validatorHelper.validateEmail(employee);
		} catch (ValidatorException e) {
			exceptionList.add(e);
		}
		
		if (!isInsert) {
			try {
				validatorHelper.employeeExists(employee);
			} catch (ValidatorException e) {
				exceptionList.add(e);
			}
		}
		
		EmployeeValidationException e = new EmployeeValidationException("Validation failed");
		
		for (ValidatorException validationException : exceptionList) {
			log.error(validationException.getErrorCode().getMessage());
			e.addValidationError(validationException.getErrorCode().getMessage());
		}
		
		if (e.hasErrors()) {
			throw e;
		}
		
		return employee;
	}

	@Override
	public Employee validate(int employeeId) throws EmployeeNotFoundException {
		log.info("Validating employee(employeeId={})", employeeId);
		
		Employee employee = null;
		
		try {
			employee = validatorHelper.employeeExists(employeeId);
		} catch (EmployeeNotFoundException e) {
			log.error(e.getMessage());
			throw e;
		}
		
		return employee;
	}

}
