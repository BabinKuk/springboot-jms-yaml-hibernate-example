package org.babinkuk.vo;


/**
 * instance of this class is used to represent employee data
 * 
 * @author Nikola
 *
 */
public class EmployeeVO {
	
	private int id;
	
	private String firstName;
	
	private String lastName;
	
	private String emailAddress;
	
	public EmployeeVO() {
		// TODO Auto-generated constructor stub
	}
	
	public EmployeeVO(String firstName, String lastName, String emailAddress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", email=" + emailAddress + "]";
	}
}
