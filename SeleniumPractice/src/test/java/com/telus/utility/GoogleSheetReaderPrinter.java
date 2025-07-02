package com.telus.utility;

import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleSheetReaderPrinter {

    private static final String SPREADSHEET_ID = "1QiSQ_709cRz7RmrDAhmTcjp0I_-C5IRz_pG-ggvQw_o";
    private static final String RANGE = "Manual!A2:Z";
    private static final String CREDENTIALS_PATH = "src/main/service-account.json";

    @Test
    public void testSheetDataFetch() throws Exception {
        Sheets sheetsService = getSheetsService();

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("❌ No data found in the sheet.");
        } else {
            System.out.println("✅ Data fetched from Google Sheet:");
            for (List<Object> row : values) {
                System.out.print("Row: ");
                for (Object cell : row) {
                    System.out.print(cell.toString() + " | ");
                }
                System.out.println();
            }
        }
    }

    private Sheets getSheetsService() throws Exception {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(CREDENTIALS_PATH))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/spreadsheets.readonly"));

        return new Sheets.Builder(httpTransport, jsonFactory, (HttpRequestInitializer) credential)
                .setApplicationName("Google Sheet Reader")
                .build();
    }
}