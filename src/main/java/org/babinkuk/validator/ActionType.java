package org.babinkuk.validator;

import java.util.Arrays;

public enum ActionType {
	
	ADD_EMPLOYEE,
	UPDATE_EMPLOYEE,
	GET_EMPLOYEE,
	DELETE_EMPLOYEE;
	
	public static ActionType valueOfIgnoreCase(String str) {
		return Arrays.stream(ActionType.values())
				.filter(e -> e.name().equalsIgnoreCase(str))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Cannot find enum constant for " + str));
	}
}
