package com.gqa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gqa.data.DataClass;

public class ExcelUtil {

	private XSSFSheet ExcelSheet;
	private XSSFWorkbook Excelbook;

	// constructor
	public ExcelUtil(final String PathofExcel, final String SheetName) throws IOException {
		try {
			FileInputStream ExcelFile = new FileInputStream(PathofExcel);
			this.Excelbook = new XSSFWorkbook(ExcelFile);
			this.ExcelSheet = this.Excelbook.getSheet(SheetName);
		} catch (FileNotFoundException e) {
			throw (e);
		}
	}

	// get total number of rows
	public int getUsedRange() throws Exception {
		try {
			int iRowsCount = this.ExcelSheet.getPhysicalNumberOfRows();
			return iRowsCount;
		} catch (Exception e) {
			throw (e);
		}
	}

	// get value from cell
	public String getCellValue(final int rownum, final int colnum) throws Exception {
		try {
			String cellValue = this.ExcelSheet.getRow(rownum).getCell(colnum).getStringCellValue();
			return cellValue;
		} catch (Exception e) {
			throw e;
			// return "Unable to get Data";
		}
	}

	// set value in sheet
	private void setCellValue(final String value, final int rownum, final int colnum) {
		try {
			XSSFRow row = this.ExcelSheet.getRow(rownum);
			XSSFCell cell = row.getCell(colnum);
			if (cell == null) {
				cell = ExcelSheet.getRow(rownum).createCell(colnum);
			}
			cell.setCellValue(value);

			FileOutputStream fos = new FileOutputStream(
					new File(System.getProperty("user.dir") + DataClass.PATH_TESTDATA));
			this.Excelbook.write(fos);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
