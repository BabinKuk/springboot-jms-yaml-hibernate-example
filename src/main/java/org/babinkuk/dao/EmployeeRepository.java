package org.babinkuk.dao;

import java.util.Optional;

import org.babinkuk.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

	// that's it... no need to write any code
	// optional
	public Optional<Employee> findByEmail(String email);
	
}
 