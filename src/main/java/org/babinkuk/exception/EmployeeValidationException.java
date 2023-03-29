package org.babinkuk.exception;

import java.util.ArrayList;
import java.util.List;

public class EmployeeValidationException extends EmployeeException {

	private static final long serialVersionUID = 1L;
	
	private List<String> validationErorrs;

	public EmployeeValidationException(String message) {
        super(message);
    }

    public EmployeeValidationException(String message, Throwable cause) {
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
    
    public EmployeeValidationException addValidationError(String error) {
    	getValidationErrors().add(error);
    	return this;
    }
}
