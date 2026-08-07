package jiraAutomate;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelSheetDataRead {

	public static void main(String[] args) throws IOException {
		
		String excelPath=".\\ExcelData\\Book1.xlsx";
		
		FileInputStream inputStream=new FileInputStream(excelPath);
		
		XSSFWorkbook workbook= new XSSFWorkbook(inputStream);
		
		XSSFSheet sheet =  workbook.getSheetAt(1);
		
	int rows = sheet.getLastRowNum();     //returns last row number(Indexing starts from 0) in that particular sheet that means number of rows it will return
    int cols=	sheet.getRow(1).getLastCellNum(); //returns last Cell number(Indexing starts from 0) of the row number passed 
    System.out.println("Rows="+rows);
    System.out.println("Columns="+cols);
    for(int r=0;r<=rows;r++)
    {
    XSSFRow row = sheet.getRow(r);
    for(int c=0;c<cols;c++)
    {
    XSSFCell cell=row.getCell(c);
    switch(cell.getCellType())
    {
    case STRING: System.out.print(cell.getStringCellValue()); break;
    case NUMERIC: System.out.print(cell.getNumericCellValue()); break;
    case BOOLEAN: System.out.print(cell.getBooleanCellValue()); break;
    }
    System.out.print("    ");
    }
    System.out.println(" ");
    }

	}
	

}


