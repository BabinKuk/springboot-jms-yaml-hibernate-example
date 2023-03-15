package org.babinkuk.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidatorFactory {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public EmployeeValidator getValidator(ValidatorType type) {
		
		log.info("validatorType={}", type);
		String beanName = "validator." + (type != null ? type : ValidatorType.ROLE_EMPLOYEE);
		
		EmployeeValidator validator = applicationContext.getBean(beanName, EmployeeValidator.class);
		
		if (validator == null) {
			throw new IllegalStateException("Cannot acquire validator instance for type : " + type);
		}
		
		return validator;
	}
	
}
