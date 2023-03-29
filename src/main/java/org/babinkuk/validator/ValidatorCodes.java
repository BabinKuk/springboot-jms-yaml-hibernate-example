package org.babinkuk.validator;

public enum ValidatorCodes {

	ERROR_CODE_FIRST_NAME_EMPTY("error_code_first_name_empty"),
	ERROR_CODE_LAST_NAME_EMPTY("error_code_last_name_empty"),
	ERROR_CODE_EMAIL_EMPTY("error_code_email_empty"),
	ERROR_CODE_EMAIL_INVALID("error_code_email_invalid"),
	ERROR_CODE_EMAIL_ALREADY_EXIST("error_code_email_already_exist"),
	ERROR_CODE_EMPLOYEE_INVALID("error_code_employee_invalid"),
	ERROR_CODE_ACTION_INVALID("error_code_action_invalid");
	
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
