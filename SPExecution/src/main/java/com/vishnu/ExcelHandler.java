package com.vishnu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelHandler {
	
	private Workbook wb;
	private Sheet sh;
	private FileInputStream fis;
	private FileOutputStream fos;
	private Row row;
	private Cell cell; 
	private Cell executionCell;
	private Cell errorCell;
	private Cell messageCell;
	
	public void init() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		initiateExcelFile();
	}
	
	public void initiateExcelFile() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		fis = new FileInputStream("./spdata.xlsx");
		wb = WorkbookFactory.create(fis);
		sh = wb.getSheet("Sheet1");
		System.out.println("Opened excel file");
	}
	
	public int getNoOfRows()
	{
		return sh.getPhysicalNumberOfRows();
	}
	
	public String getSPName(int row_value)
	{
		String sp_name = "";
		row = sh.getRow(row_value);
		cell = row.getCell(0);
		sp_name = cell.getStringCellValue();
		
		return sp_name;
	}
	
	public void saveResult(String error, String message) throws IOException
	{
		System.out.println("error : " + error + " , message : " + message);
		executionCell = row.createCell(1);
		executionCell.setCellValue("Yes");
		
		errorCell = row.createCell(2);
		errorCell.setCellValue(error);
		
		messageCell = row.createCell(3);
		messageCell.setCellValue(message);
		
		fos = new FileOutputStream("./spdata.xlsx");
		wb.write(fos);
		System.out.println("File updated");
	}
	
	public void closeExcelFile() throws IOException
	{
		fos.flush();
		fos.close();
		System.out.println("Closed excel file");
	}
	
	public void destroy() throws IOException
	{
		closeExcelFile();
	}
}
