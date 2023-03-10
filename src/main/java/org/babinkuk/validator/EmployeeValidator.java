package org.babinkuk.validator;

import org.babinkuk.exception.EmployeeNotFoundException;
import org.babinkuk.exception.EmployeeValidationException;
import org.babinkuk.vo.EmployeeVO;

public interface EmployeeValidator {
	
	/** 
	 * @param employee
	 * @param isInsert
	 * @return
	 * @throws EmployeeValidationException
	 */
	public EmployeeVO validate(EmployeeVO employee, boolean isInsert) throws EmployeeValidationException;

	/**
	 * @param id
	 * @return
	 * @throws EmployeeNotFoundException
	 */
	public EmployeeVO validate(int id) throws EmployeeNotFoundException;

}
