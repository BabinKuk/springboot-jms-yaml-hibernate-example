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
@Component("validator.ROLE_MANAGER")
public class EmployeeValidatorImplRoleManager implements EmployeeValidator {

private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private EmployeeValidatorHelper validatorHelper;
	
	@Override
	public EmployeeVO validate(EmployeeVO employeeVO, boolean isInsert, ActionType action) throws EmployeeValidationException {
		log.info("ROLE_MANAGER validating employee");
		
		List<ValidatorException> exceptionList = new LinkedList<ValidatorException>();
		
		if (ActionType.DELETE == action) {
			log.info("delete action disabled");
			
			String message = String.format(ValidatorCodes.ERROR_CODE_ACTION_INVALID.getMessage(), action);
			EmployeeValidationException e = new EmployeeValidationException(message);
			throw e;
		
		} else {
			exceptionList.addAll(validatorHelper.validate(employeeVO, isInsert));
		}
		
		return employeeVO;
	}
	
	@Override
	public EmployeeVO validate(int employeeId, ActionType action) throws EmployeeNotFoundException {
		log.info("ROLE_MANAGER Validating employee(employeeId={})", employeeId);
		
		EmployeeVO employeeVO = null;
		
		// DELETE action disabled
		if (ActionType.DELETE == action) {
			log.info("delete action disabled");
			
			String message = String.format(ValidatorCodes.ERROR_CODE_ACTION_INVALID.getMessage(), action);
			EmployeeValidationException e = new EmployeeValidationException(message);
			throw e;
		
		} else {
			try {
				employeeVO = validatorHelper.validate(employeeId);
			} catch (EmployeeNotFoundException e) {
				log.error(e.getMessage());
				throw e;
			}
		}
		
		return employeeVO;
	}

}
