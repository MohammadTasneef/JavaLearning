package com.telus.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;

public class ExcelDataWrite {
    private WebDriver driver;
    private WebDriverWait wait;

    public ExcelDataWrite(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void updateExecutionStatusColumn() {
        String excelPath = ".\\ExcelData\\Book1.xlsx";
        Workbook workbook = null;
        Sheet sheet = null;

        try (FileInputStream fis = new FileInputStream(excelPath)) {
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0); // assuming data is in the first sheet
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Find the column index with header "ExecutionStatus"
        int statusColIndex = -1;
        Row headerRow = sheet.getRow(0);
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase("ExecutionStatus")) {
                statusColIndex = cell.getColumnIndex();
                break;
            }
        }

        if (statusColIndex == -1) {
            System.out.println("Column 'ExecutionStatus' not found.");
            return;
        }

        // Get Jira IDs from column A ("Scenario")
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) continue;

            Cell scenarioCell = row.getCell(0); // Column A
            if (scenarioCell == null) continue;

            String scenarioText = scenarioCell.getStringCellValue().trim();
            if (scenarioText.isEmpty()) continue;

            // Extract the ID (e.g., BSBDTR-1553 from link or plain text)
            String id = scenarioText.contains("BSBDTR-") ?
                    scenarioText.substring(scenarioText.indexOf("BSBDTR-")) :
                    scenarioText;

            try {
                String xpath = "//div[contains(text(),'" + id + "')]/..//div[@class='execution-status']/*";
                WebElement statusElement = driver.findElement(By.xpath(xpath));
                String status = statusElement.getText().trim();

                // Write the status to the correct column
                Cell statusCell = row.createCell(statusColIndex);
                statusCell.setCellValue(status);

            } catch (Exception e) {
                // Skip if element not found
                System.out.println("Status not found for ID: " + id);
            }
        }

        // Save the updated file
        try (FileOutputStream fos = new FileOutputStream(excelPath)) {
            workbook.write(fos);
            workbook.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ExtentReportManager.getTest().pass("ExecutionStatus column updated successfully.");
        System.out.println("ExecutionStatus column updated successfully.");
    }
}
