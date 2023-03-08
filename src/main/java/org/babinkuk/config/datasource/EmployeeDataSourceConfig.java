//package org.babinkuk.config.datasource;
//
//import javax.persistence.Basic;
//import javax.sql.DataSource;
//
//import org.babinkuk.common.Qualifiers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.Database;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//@Configuration
//@ConfigurationProperties(prefix = Qualifiers.DataSource.EMPLOYEE_CONFIG_PREFIX)
//@EnableJpaRepositories(basePackages = "org.babinkuk.dao",
//	entityManagerFactoryRef = Qualifiers.EntityManagerFactory.EMPLOYEE)
//public class EmployeeDataSourceConfig extends BasicDataSourceConfig {
//	
//	@Autowired
//	protected Environment environment;
//	
//	@Override
//	@Bean(Qualifiers.DataSource.EMPLOYEE)
//	public DataSource getDataSource() {
//		return super.getDataSource();
//	}
//	
//	@Bean(Qualifiers.EntityManagerFactory.EMPLOYEE)
//	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier(Qualifiers.DataSource.EMPLOYEE) DataSource dataSource) {
//		
//		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
//		
//		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//		jpaVendorAdapter.setDatabase(Database.MYSQL);
//		jpaVendorAdapter.setGenerateDdl(false);
//		jpaVendorAdapter.setShowSql(false);
//		
//		bean.setDataSource(dataSource);
//		bean.setPackagesToScan("org.babinkuk.dao");
//		//bean.setPersistenceUnitName("EMPLOYEE");
//		bean.setJpaVendorAdapter(jpaVendorAdapter);
//		
//		return bean;
//	}
//}
