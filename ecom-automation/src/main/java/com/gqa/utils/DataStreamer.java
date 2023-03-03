package com.gqa.utils;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gqa.data.DataClass;

public class DataStreamer {

	public Object[][] data(String sheetName) {
		Object[][] data = null;
		try {
			DataFormatter format = new DataFormatter();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + DataClass.PATH_TESTDATA);

			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheet(sheetName);

			int sheetRowCount = sheet.getPhysicalNumberOfRows();
			System.out.println("Iteration in " + sheetName + " " + sheetRowCount);
			XSSFRow row = sheet.getRow(0);// header row do not change hard code -GG
			int sheetColCount = row.getLastCellNum();

			data = new Object[sheetRowCount - 1][sheetColCount];
			for (int r = 0; r < sheetRowCount - 1; r++) {
				row = sheet.getRow(r + 1);
				for (int c = 0; c < sheetColCount; c++) {
					XSSFCell cell = row.getCell(c);
					data[r][c] = format.formatCellValue(cell);
				}
			}
			wb.close();

			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

}
