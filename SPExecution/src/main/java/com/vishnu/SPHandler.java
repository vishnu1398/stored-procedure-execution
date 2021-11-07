package com.vishnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class SPHandler {
	
	private String driver;
	private String url;
	private String user;
	private String password;
	
	Connection con;
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void display()
	{
		System.out.println("url : " + url);
		System.out.println("user : " + user);
		System.out.println("password : " + password);
	}
	
	public void init() throws SQLException, ClassNotFoundException
	{
		createDBConnection();
	}
	
	public void createDBConnection() throws SQLException, ClassNotFoundException
	{
		Class.forName(driver);
		
		con = DriverManager.getConnection(url, user, password);
		System.out.println("connection created ");
	}
	
	public String executeSP(String sp_name) throws SQLException
	{
		String message = "Successful";
		
		try {
			String sql = "exec " + sp_name;
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setQueryTimeout(90);
			
			ResultSet rs = ps.executeQuery();
			
		} catch (SQLServerException se) {
			
			message = se.getMessage();
		}
		return message;
	}
	
	public void closeDBConnection() throws SQLException
	{
		con.close();
		System.out.println("connection closed");
	}	
	
	public void destroy() throws SQLException
	{
		closeDBConnection();
	}
}
