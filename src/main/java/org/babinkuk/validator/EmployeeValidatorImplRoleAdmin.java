package org.babinkuk.validator;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.exception.EmployeeNotFoundException;
import org.babinkuk.exception.EmployeeValidationException;
import org.babinkuk.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * for future purpose 
 * if special valdations are required depending on the role 
 * 
 * @author Nikola
 *
 */
@Component("validator.ROLE_ADMIN")
public class EmployeeValidatorImplRoleAdmin implements EmployeeValidator {

private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private EmployeeValidatorHelper validatorHelper;
	
	@Override
	public EmployeeVO validate(EmployeeVO employeeVO, boolean isInsert, ActionType action) throws EmployeeValidationException {
		log.info("ROLE_ADMIN validating employee");
		
		List<ValidatorException> exceptionList = new LinkedList<ValidatorException>();
		
		// all action types are enabled
		exceptionList.addAll(validatorHelper.validate(employeeVO, isInsert));
		
		EmployeeValidationException e = new EmployeeValidationException("Validation failed");
		
		for (ValidatorException validationException : exceptionList) {
			//log.error(validationException.getErrorCode().getMessage());
			//e.addValidationError(validationException.getErrorCode().getMessage());
			e.addValidationError(messageSource.getMessage(validationException.getErrorCode().getMessage(), new Object[] {}, LocaleContextHolder.getLocale()));
		}
		
		if (e.hasErrors()) {
			throw e;
		}

		return employeeVO;
	}

	@Override
	public EmployeeVO validate(int employeeId, ActionType action) throws EmployeeNotFoundException {
		log.info("ROLE_ADMIN Validating employee(employeeId={})", employeeId);
		
		EmployeeVO employeeVO = null;
		
		// all action types are eabled
		try {
			employeeVO = validatorHelper.validate(employeeId);
		} catch (EmployeeNotFoundException e) {
			log.error(e.getMessage());
			throw e;
		}
		
		return employeeVO;
	}


}
