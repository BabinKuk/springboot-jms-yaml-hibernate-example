package org.babinkuk.validator;

public enum ValidatorCodes {
	ERROR_CODE_FIRST_NAME_EMPTY("Employee first name is empty"),
	ERROR_CODE_LAST_NAME_EMPTY("Employee last name is empty"),
	ERROR_CODE_EMAIL_EMPTY("Employee email is empty"),
	ERROR_CODE_EMAIL_INVALID("Employee email is invalid"),
	ERROR_CODE_EMAIL_ALREADY_EXIST("Employee with same email already exists"),
	ERROR_CODE_EMPLOYEE_INVALID("Employee not found in database");
	
	private String message;
	
	ValidatorCodes(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public static ValidatorCodes fromMessage(String message) {
		
		for (ValidatorCodes code : ValidatorCodes.values()) {
			if(code.message.equalsIgnoreCase(message)) return code;
		}
		
		return null;
	}

}
