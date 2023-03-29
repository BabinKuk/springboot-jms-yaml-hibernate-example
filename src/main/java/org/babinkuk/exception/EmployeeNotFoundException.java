package org.babinkuk.exception;

import java.util.ArrayList;
import java.util.List;

public class EmployeeNotFoundException extends EmployeeException {

	private static final long serialVersionUID = 1L;
	
	private List<String> validationErorrs;

	public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public boolean hasErrors()  {
    	return getValidationErrors().size() > 0;
    }
    
    public List<String> getValidationErrors() {
    	if (validationErorrs == null) {
			validationErorrs = new ArrayList<String>(0);
		}
    	return validationErorrs;
    }
    
    public EmployeeNotFoundException addValidationError(String error) {
    	getValidationErrors().add(error);
    	return this;
    }
}
