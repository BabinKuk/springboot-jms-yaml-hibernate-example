package org.babinkuk.validator;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.entity.Employee;
import org.babinkuk.exception.EmployeeException;
import org.babinkuk.exception.EmployeeNotFoundException;
import org.babinkuk.exception.EmployeeValidationException;
import org.babinkuk.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidatorImpl implements EmployeeValidator {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private EmployeeValidatorHelper validatorHelper;
	
	@Override
	public EmployeeVO validate(EmployeeVO employeeVO, boolean isInsert) throws EmployeeValidationException {
		log.info("validating employee");
		
		List<ValidatorException> exceptionList = new LinkedList<ValidatorException>();
		
		try {
			validatorHelper.validateFirstName(employeeVO.getFirstName());
		} catch (ValidatorException e) {
			exceptionList.add(e);
		}
		
		try {
			validatorHelper.validateLastName(employeeVO.getLastName());
		} catch (ValidatorException e) {
			exceptionList.add(e);
		}
		
		try {
			validatorHelper.validateEmail(employeeVO);
		} catch (ValidatorException e) {
			exceptionList.add(e);
		}
		
		if (!isInsert) {
			try {
				validatorHelper.employeeExists(employeeVO);
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
		
		return employeeVO;
	}

	@Override
	public EmployeeVO validate(int employeeId) throws EmployeeNotFoundException {
		log.info("Validating employee(employeeId={})", employeeId);
		
		EmployeeVO employeeVO = null;
		
		try {
			employeeVO = validatorHelper.employeeExists(employeeId);
		} catch (EmployeeNotFoundException e) {
			log.error(e.getMessage());
			throw e;
		}
		
		return employeeVO;
	}

}
