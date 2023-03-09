package org.babinkuk.mapper;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.babinkuk.entity.Employee;
import org.babinkuk.vo.EmployeeVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * mapper for the entity @link {@link Employee} and its DTO {@link EmployeeVO}
 * @author Nikola
 *
 */
@Mapper
(
	componentModel = "spring",
	unmappedSourcePolicy = ReportingPolicy.WARN,
	imports = {StringUtils.class, Objects.class}
	//if needed add uses = {add different classes for complex objects} 
)
public interface EmployeeMapper {
	
	//public EmployeeMapper employeeMapperInstance = Mappers.getMapper(EmployeeMapper.class);
	
	@Named("toEntity")
	@Mapping(source = "emailAddress", target = "email")
	Employee toEntity(EmployeeVO employeeVO);
	
	@Named("toVO")
	@Mapping(source = "email", target = "emailAddress")
	EmployeeVO toVO(Employee employee);
	
	@IterableMapping(qualifiedByName = "toVO")
	@BeanMapping(ignoreByDefault = true)
	Iterable<EmployeeVO> toVO(Iterable<Employee> employeeList);
	
}