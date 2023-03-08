package org.babinkuk.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.annotation.Qualifier;

public class Qualifiers {
	
	public static class DataSource {
		
		public static final String EMPLOYEE = "org.babinkuk.employee.datasource";
		public static final String EMPLOYEE_CONFIG_PREFIX = "spring.datasource.employee";
		//add for additional dbs if necessary
		
		@Qualifier(EMPLOYEE)
		@Retention(RetentionPolicy.RUNTIME)
		public @interface Employee {
		}
	}
	
	public static class EntityManagerFactory {
		
		public static final String EMPLOYEE = "employeeEntityManagerFactory";
	}

}
