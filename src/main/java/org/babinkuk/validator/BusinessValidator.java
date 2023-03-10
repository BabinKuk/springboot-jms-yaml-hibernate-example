package org.babinkuk.validator;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.exception.EmployeeNotFoundException;
import org.babinkuk.service.EmployeeService;
import org.babinkuk.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessValidator {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * @param name
	 * @throws ValidationException
	 */
	public void validateFirstName(String name) throws ValidatorException {
		validateStringIsBlank(name, ValidatorCodes.ERROR_CODE_FIRST_NAME_EMPTY);
	}
	
	/**
	 * @param name
	 * @throws ValidationException
	 */
	public void validateLastName(String name) throws ValidatorException {
		validateStringIsBlank(name, ValidatorCodes.ERROR_CODE_LAST_NAME_EMPTY);
	}
	
	/**
	 * @param email
	 * @throws ValidationException
	 */
	public void validateEmail(EmployeeVO employeeVO) throws ValidatorException {
		validateStringIsBlank(employeeVO.getEmailAddress(), ValidatorCodes.ERROR_CODE_EMAIL_EMPTY);
		validateEmailFormat(employeeVO.getEmailAddress(), ValidatorCodes.ERROR_CODE_EMAIL_INVALID);
		employeeEmailExists(employeeVO);
	}
	
	/**
	 * @param email
	 * @param errorCode
	 * @throws ValidatorException
	 */
	public void validateEmailFormat(String email, ValidatorCodes errorCode) throws ValidatorException {
		if (!validateEmailAddress(email)) {
			throw new ValidatorException(errorCode);
		}
	}

	/**
	 * validate email format
	 * @param email
	 * @return
	 */
	private boolean validateEmailAddress(String email) {
		if (StringUtils.isNotBlank(email)) {
			email = StringUtils.upperCase(StringUtils.replace(email, " ", ""));
			for (String pattern : Arrays.asList("[A-Z0-9._%+-]+@[A-Z0-9.-]+")) {
				if (Pattern.matches(pattern, email)) {
					return true;
				}
			}
			return false;
		} else {
			// if empty return true
			return true;
		}
		
	}

	/**
	 * validate if email already exist must be unique (call repository findByEmail)
	 * @param employeeVO
	 * @param isInsert
	 * @return
	 * @throws ValidatorException
	 */
	public void employeeEmailExists(EmployeeVO employeeVO) throws ValidatorException {
		//validateStringIsBlank(employee, ValidatorCodes.ERROR_CODE_EMPLOYEE_INVALID);
		
		EmployeeVO dbEmployee = employeeService.findByEmail(employeeVO.getEmailAddress());
		
		if (dbEmployee == null) {
			// employee email not found
			// that's ok
			log.info("employee email not found");
		} else {
			log.info("employee email found");
			if (dbEmployee.getId() == employeeVO.getId()) {
				// same employee, email has not changed
				log.info("belongs to same employee, email has not changed");
			} else {
				// another employee with same email already exists in db
				log.error(ValidatorCodes.ERROR_CODE_EMAIL_ALREADY_EXIST.getMessage());
				throw new ValidatorException(ValidatorCodes.ERROR_CODE_EMAIL_ALREADY_EXIST);
			}
		}
	}
	
	/**
	 * validate if employee already exist
	 * @param employee
	 * @param isInsert
	 * @return
	 * @throws ValidatorException
	 */
	public void employeeExists(EmployeeVO employeeVO) throws ValidatorException {
		
		EmployeeVO result = employeeService.findById(employeeVO.getId());
		
		log.info("validate employee on update");
		if (result != null) {
			// employee id found
			log.info("employee id found");
		} else {
			// employee id not found
			log.error("result.notPresent");
			throw new ValidatorException(ValidatorCodes.ERROR_CODE_EMPLOYEE_INVALID);
		}
	}

	/**
	 * @param str
	 * @param errorCode
	 * @throws ValidatorException
	 */
	private void validateStringIsBlank(String str, ValidatorCodes errorCode) throws ValidatorException {
		if (StringUtils.isBlank(str)) {
			throw new ValidatorException(errorCode);
		}
	}

	/**
	 * @param id
	 * @return
	 * @throws EmployeeNotFoundException
	 */
	public EmployeeVO employeeExists(int id) throws EmployeeNotFoundException {
		
		EmployeeVO employeeVO = employeeService.findById(id);
		
		if (employeeVO == null) {
			String message = String.format("Employee with id=%s not found.", id);
			log.warn(message);
			throw new EmployeeNotFoundException(message);
		}
		
		return employeeVO;
	}

}
