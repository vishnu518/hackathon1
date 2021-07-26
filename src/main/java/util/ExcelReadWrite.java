package util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadWrite {
	public static File src;
	public static String path = "C:\\Users\\vishn\\eclipse-workspace\\hackathon1\\src\\main\\java\\testData\\TestData.xlsx"; // get file path																			// file
	public static FileInputStream fileip;
	public static FileOutputStream fileop;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static int row;
	public static XSSFRow Row;
	public static XSSFCell cell;

	public static String testData[][];
	

	public static String[][] readExcel(String sheetName)  { // function for read the data from project
																			// excel file
		try {
			src = new File(path);
			fileip = new FileInputStream(src);
			workbook = new XSSFWorkbook(fileip);
			sheet = workbook.getSheet(sheetName);
			
			DataFormatter formatter = new DataFormatter();
			
			int rowIndex = sheet.getLastRowNum();
			int columnIndex = sheet.getRow(0).getLastCellNum();
			
			if ( sheetName.equalsIgnoreCase( "Search" ) ) {
				rowIndex = 1;
				columnIndex = 3;
			}
			else if(sheetName.equalsIgnoreCase("Corporate Wellness")) {
				rowIndex = 1;
				columnIndex = 6;
			}
			testData=new String[rowIndex][columnIndex];

			for (int i = 1; i <= rowIndex; i++) {
				
				XSSFRow row = sheet.getRow(i);

				for (int j = 0; j < columnIndex; j++) {
					XSSFCell cell = row.getCell(j);

					String temp = formatter.formatCellValue(cell);
					if (temp.equalsIgnoreCase("blank"))
						temp = "";
					testData[i - 1][j] = temp;
				}
  
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return testData;
	}	

	public static void writeExcel() {
		try {
					
			// CLOSE INPUT STREAM
			fileip.close();
			
			fileop = new FileOutputStream(new File(path));
			workbook.write(fileop);
			// CLOSE OUTPUT STREAM
			fileop.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}