//package org.babinkuk.config.datasource;
//
//import javax.sql.DataSource;
//
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//public class BasicDataSourceConfig {
//
//	protected String driverClassName;
//	protected String url;
//	protected String username;
//	protected String password;
//	
//	public DataSource getDataSource() {
//		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setUsername(username);
//		dataSource.setPassword(password);
//		dataSource.setUrl(url);
//		dataSource.setDriverClassName(driverClassName);
//		return dataSource;
//	}
//
//	public String getDriverClassName() {
//		return driverClassName;
//	}
//
//	public void setDriverClassName(String driverClassName) {
//		this.driverClassName = driverClassName;
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}	
//}
