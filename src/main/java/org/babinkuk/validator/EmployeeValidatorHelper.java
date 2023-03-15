package org.babinkuk.validator;

import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.exception.EmployeeNotFoundException;
import org.babinkuk.exception.EmployeeValidationException;
import org.babinkuk.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidatorHelper {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private BusinessValidator validator;
	
	public List<ValidatorException> validate(EmployeeVO employeeVO, boolean isInsert) throws EmployeeValidationException {
		List<ValidatorException> exceptions = new LinkedList<ValidatorException>();
		
		try {
			validator.validateFirstName(employeeVO.getFirstName());
		} catch (ValidatorException e) {
			exceptions.add(e);
		}
		
		try {
			validator.validateLastName(employeeVO.getLastName());
		} catch (ValidatorException e) {
			exceptions.add(e);
		}
		
		try {
			validator.validateEmail(employeeVO);
		} catch (ValidatorException e) {
			exceptions.add(e);
		}
		
		if (!isInsert) {
			try {
				validator.employeeExists(employeeVO);
			} catch (ValidatorException e) {
				exceptions.add(e);
			}
		}
		
		EmployeeValidationException e = new EmployeeValidationException("Validation failed");
		
		for (ValidatorException validationException : exceptions) {
			//log.error(validationException.getErrorCode().getMessage());
			e.addValidationError(validationException.getErrorCode().getMessage());
		}
		
		if (e.hasErrors()) {
			throw e;
		}

		return exceptions;
	}
	
	public EmployeeVO validate(int employeeId) throws EmployeeNotFoundException {
		EmployeeVO employeeVO = null;
		
		try {
			employeeVO = validator.employeeExists(employeeId);
		} catch (EmployeeNotFoundException e) {
			log.error(e.getMessage());
			throw e;
		}
		
		return employeeVO;
	}
}
