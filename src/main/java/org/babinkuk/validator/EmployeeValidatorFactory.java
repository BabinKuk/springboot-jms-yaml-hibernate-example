package org.babinkuk.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidatorFactory {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public EmployeeValidator getValidator(ValidatorType type) {
		
		EmployeeValidator validator = applicationContext.getBean("validator." + type, EmployeeValidator.class);
		
		if (validator == null) {
			throw new IllegalStateException("Cannot acquire validator instance for type : " + type);
		}
		
		return validator;
	}
	
	public EmployeeValidator getValidator() {
		
		EmployeeValidator validator = applicationContext.getBean(EmployeeValidator.class);
		
		if (validator == null) {
			throw new IllegalStateException("Cannot acquire validator instance");
		}
		
		return validator;
	}
}
