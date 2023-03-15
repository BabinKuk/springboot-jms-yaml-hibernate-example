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

/**
 * for future purpose 
 * if special valdations are required depending on the role 
 * 
 * @author Nikola
 *
 */
@Component("validator.ROLE_EMPLOYEE")
public class EmployeeValidatorImplRoleEmployee implements EmployeeValidator {

private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private EmployeeValidatorHelper validatorHelper;
	
	@Override
	public EmployeeVO validate(EmployeeVO employeeVO, boolean isInsert, ActionType action) throws EmployeeValidationException {
		log.info("ROLE_EMPLOYEE validating employee");
		
		List<ValidatorException> exceptionList = new LinkedList<ValidatorException>();
		
		// only READ action enabled
		if (ActionType.READ == action) {
			log.info("read action only");
			exceptionList.addAll(validatorHelper.validate(employeeVO, isInsert));
		} else {
			//String message = String.format("Employee with id=%s not found.", id);
			String message = String.format(ValidatorCodes.ERROR_CODE_ACTION_INVALID.getMessage(), action);
			EmployeeValidationException e = new EmployeeValidationException(message);
			throw e;
		}
		
		return employeeVO;
	}

	@Override
	public EmployeeVO validate(int employeeId, ActionType action) throws EmployeeNotFoundException, EmployeeValidationException {
		log.info("ROLE_EMPLOYEE Validating employee(employeeId={})", employeeId);
		
		EmployeeVO employeeVO = null;
		
		// only READ action enabled
		if (ActionType.READ == action) {
			log.info("read action only");
			
			try {
				employeeVO = validatorHelper.validate(employeeId);
			} catch (EmployeeNotFoundException e) {
				log.error(e.getMessage());
				throw e;
			}
		
		} else {
			String message = String.format(ValidatorCodes.ERROR_CODE_ACTION_INVALID.getMessage(), action);
			EmployeeValidationException e = new EmployeeValidationException(message);
			throw e;
		}
		
		return employeeVO;
	}


}