package com.vishnu;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Executor {

	public static void main(String[] args) throws SQLException {

		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		SPHandler handler = context.getBean("spHandler", SPHandler.class);

		ExcelHandler excelHandler = context.getBean("excelHandler", ExcelHandler.class);
		int noOfRows = excelHandler.getNoOfRows();
		System.out.println("No of rows : " + noOfRows);

		for (int i = 1; i < noOfRows; i++) {
			try {
				String sp_name = excelHandler.getSPName(i);
				String message = handler.executeSP(sp_name);
				System.out.println(message);
				
				String error = "";
				if(message.equals("Successful"))
					error = "No";
				else
					error = "Yes";
				
				excelHandler.saveResult(error, message);
				
			} catch (Exception e) {
				System.out.println("Exception caught : " + e.getMessage());
			}
		}

		((ClassPathXmlApplicationContext) context).close();
	}

}
