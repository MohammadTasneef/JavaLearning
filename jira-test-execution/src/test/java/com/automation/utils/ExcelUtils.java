package com.automation.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel helper kept from the first version of this framework, when the test cases lived in
 * Book1.xlsx instead of a Google Sheet. Still handy for local runs without sheet access.
 */
public class ExcelUtils {

	private ExcelUtils() {
	}

	public static int getRowCount(String file, String sheetName) throws IOException {
		try (FileInputStream in = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(in)) {
			return workbook.getSheet(sheetName).getLastRowNum();
		}
	}

	public static int getCellCount(String file, String sheetName, int rowNumber) throws IOException {
		try (FileInputStream in = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(in)) {
			return workbook.getSheet(sheetName).getRow(rowNumber).getLastCellNum();
		}
	}

	public static String getCellData(String file, String sheetName, int rowNumber, int columnNumber)
			throws IOException {
		try (FileInputStream in = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(in)) {
			Row row = workbook.getSheet(sheetName).getRow(rowNumber);
			if (row == null) {
				return "";
			}
			// A blank cell comes back as null rather than an empty cell, and DataFormatter
			// handles numbers and dates without us checking the cell type.
			Cell cell = row.getCell(columnNumber);
			return cell == null ? "" : new DataFormatter().formatCellValue(cell);
		}
	}

	public static void setCellData(String file, String sheetName, int rowNumber, int columnNumber, String value)
			throws IOException {
		XSSFWorkbook workbook;
		try (FileInputStream in = new FileInputStream(file)) {
			workbook = new XSSFWorkbook(in);
		}

		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNumber);
		if (row == null) {
			row = sheet.createRow(rowNumber);
		}
		Cell cell = row.getCell(columnNumber);
		if (cell == null) {
			cell = row.createCell(columnNumber);
		}
		cell.setCellValue(value);

		// The file has to be closed for reading before it can be opened for writing.
		try (FileOutputStream out = new FileOutputStream(file)) {
			workbook.write(out);
		} finally {
			workbook.close();
		}
	}

	public static void fillGreen(String file, String sheetName, int rowNumber, int columnNumber) throws IOException {
		fillCell(file, sheetName, rowNumber, columnNumber, IndexedColors.GREEN);
	}

	public static void fillRed(String file, String sheetName, int rowNumber, int columnNumber) throws IOException {
		fillCell(file, sheetName, rowNumber, columnNumber, IndexedColors.RED);
	}

	private static void fillCell(String file, String sheetName, int rowNumber, int columnNumber, IndexedColors colour)
			throws IOException {
		XSSFWorkbook workbook;
		try (FileInputStream in = new FileInputStream(file)) {
			workbook = new XSSFWorkbook(in);
		}

		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(colour.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		workbook.getSheet(sheetName).getRow(rowNumber).getCell(columnNumber).setCellStyle(style);

		try (FileOutputStream out = new FileOutputStream(file)) {
			workbook.write(out);
		} finally {
			workbook.close();
		}
	}
}
