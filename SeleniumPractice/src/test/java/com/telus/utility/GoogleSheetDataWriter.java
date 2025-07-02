package com.telus.utility;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.util.*;

public class GoogleSheetDataWriter {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String SPREADSHEET_ID = "1QiSQ_709cRz7RmrDAhmTcjp0I_-C5IRz_pG-ggvQw_o";
    private static final String RANGE = "Manual!A2:Z"; // Assuming data starts from row 2
    private static final String CREDENTIALS_PATH = "src/main/service-account.json";

    public GoogleSheetDataWriter(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void updateExecutionStatusColumn() {
        try {
            Sheets service = getSheetsService();

            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, RANGE)
                    .execute();

            List<List<Object>> rows = response.getValues();
            if (rows == null || rows.isEmpty()) {
                System.out.println("No data found in Google Sheet.");
                return;
            }

            int statusColIndex = -1;
            ValueRange headerResponse = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, "Manual!A1:Z1")
                    .execute();
            List<Object> headerRow = headerResponse.getValues().get(0);

            for (int i = 0; i < headerRow.size(); i++) {
                if ("ExecutionStatus".equalsIgnoreCase(headerRow.get(i).toString().trim())) {
                    statusColIndex = i;
                    break;
                }
            }

            if (statusColIndex == -1) {
                System.out.println("Column 'ExecutionStatus' not found.");
                return;
            }

            List<List<Object>> updatedValues = new ArrayList<>();
            List<String> ranges = new ArrayList<>();

            for (int i = 0; i < rows.size(); i++) {
                List<Object> row = rows.get(i);
                if (row.isEmpty()) continue;

                String scenarioText = row.get(0).toString();
                if (scenarioText.isEmpty()) continue;

                String id = scenarioText.contains("BSBDTR-") ?
                        scenarioText.substring(scenarioText.indexOf("BSBDTR-")) : scenarioText;

                try {
                    String xpath = "//div[contains(text(),'" + id + "')]/..//div[@class='execution-status']/*";
                    WebElement statusElement = driver.findElement(By.xpath(xpath));
                    String status = statusElement.getText().trim();

                    while (row.size() <= statusColIndex) {
                        row.add("");
                    }
                    row.set(statusColIndex, status);

                    updatedValues.add(Collections.singletonList(status));
                    ranges.add("Manual!" + (char) ('A' + statusColIndex) + (i + 2));

                } catch (Exception e) {
                    System.out.println("Status not found for ID: " + id);
                }
            }

            // Write updates back to Google Sheet
            for (int i = 0; i < updatedValues.size(); i++) {
                ValueRange updateBody = new ValueRange().setValues(Collections.singletonList(updatedValues.get(i)));
                UpdateValuesResponse result = service.spreadsheets().values()
                        .update(SPREADSHEET_ID, ranges.get(i), updateBody)
                        .setValueInputOption("RAW")
                        .execute();
            }

            ExtentReportManager.getTest().pass("ExecutionStatus column updated in Google Sheet successfully.");
            System.out.println("ExecutionStatus column updated in Google Sheet successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Sheets getSheetsService() throws Exception {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(CREDENTIALS_PATH))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/spreadsheets"));

        return new Sheets.Builder(httpTransport, jsonFactory, (HttpRequestInitializer) credential)
                .setApplicationName("Google Sheet Writer")
                .build();
    }
}
