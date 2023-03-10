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
		
		log.info("type={}", type);
		String beanName = type != null ? "validator." + type : "validator.BASIC";
		
		EmployeeValidator validator = applicationContext.getBean(beanName, EmployeeValidator.class);
		
		if (validator == null) {
			throw new IllegalStateException("Cannot acquire validator instance for type : " + type);
		}
		
		return validator;
	}
	
	/*public EmployeeValidator getValidator() {
		
		EmployeeValidator validator = applicationContext.getBean(EmployeeValidator.class);
		
		if (validator == null) {
			throw new IllegalStateException("Cannot acquire validator instance");
		}
		
		return validator;
	}*/
}
