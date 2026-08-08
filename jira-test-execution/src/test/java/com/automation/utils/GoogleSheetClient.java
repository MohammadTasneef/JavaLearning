package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

/**
 * Reads the manual test cases out of the Google Sheet and writes the execution status back.
 *
 * The sheet is the source of truth for what runs, so it is read fresh at the start of every
 * suite rather than being copied into the repo.
 */
public class GoogleSheetClient {

	/** Key the sheet row number is stored under, so a status can be written back to the right row. */
	public static final String ROW_NUMBER = "rowNumber";

	private static final String STATUS_HEADER = "ExecutionStatus";

	private final Sheets service;
	private final String spreadsheetId;
	private final String sheetName;

	private int statusColumnIndex = -1;

	public GoogleSheetClient() {
		this.spreadsheetId = ConfigReader.get("spreadsheet.id");
		this.sheetName = ConfigReader.get("sheet.name");
		this.service = buildService();
	}

	/**
	 * Returns one map per data row, keyed by the header names in row 1. Each map also carries
	 * its sheet row number under {@link #ROW_NUMBER}.
	 */
	public List<Map<String, String>> readTestCases() {
		List<List<Object>> rows = read(sheetName + "!A1:Z");
		List<Map<String, String>> testCases = new ArrayList<>();

		if (rows.size() < 2) {
			return testCases;
		}

		List<Object> headers = rows.get(0);
		for (int i = 1; i < rows.size(); i++) {
			List<Object> row = rows.get(i);
			if (row.isEmpty()) {
				continue;
			}

			Map<String, String> testCase = new LinkedHashMap<>();
			for (int column = 0; column < headers.size(); column++) {
				// Sheets truncates trailing empty cells, so a short row is normal.
				String value = column < row.size() ? row.get(column).toString().trim() : "";
				testCase.put(headers.get(column).toString().trim(), value);
			}
			// Row 1 is the header, so the first data row is sheet row 2.
			testCase.put(ROW_NUMBER, String.valueOf(i + 1));
			testCases.add(testCase);
		}
		return testCases;
	}

	/**
	 * Writes every collected status in a single batch call. The old version made one request
	 * per row, which was slow and ran into the Sheets rate limit on bigger cycles.
	 */
	public void updateExecutionStatuses(Map<String, String> statusByRowNumber) {
		if (statusByRowNumber.isEmpty()) {
			System.out.println("No execution statuses to write back");
			return;
		}

		String column = columnLetter(findStatusColumnIndex());
		List<ValueRange> data = new ArrayList<>();

		for (Map.Entry<String, String> entry : statusByRowNumber.entrySet()) {
			List<Object> cell = new ArrayList<>();
			cell.add(entry.getValue());
			List<List<Object>> values = new ArrayList<>();
			values.add(cell);

			data.add(new ValueRange().setRange(sheetName + "!" + column + entry.getKey()).setValues(values));
		}

		BatchUpdateValuesRequest body = new BatchUpdateValuesRequest().setValueInputOption("RAW").setData(data);
		try {
			service.spreadsheets().values().batchUpdate(spreadsheetId, body).execute();
			System.out.println("Wrote " + data.size() + " execution statuses back to the sheet");
			ExtentReportManager.getTest().pass("ExecutionStatus column updated for " + data.size() + " rows");
		} catch (IOException e) {
			throw new IllegalStateException("Could not write the execution status back to the sheet", e);
		}
	}

	private int findStatusColumnIndex() {
		if (statusColumnIndex >= 0) {
			return statusColumnIndex;
		}

		List<List<Object>> header = read(sheetName + "!A1:Z1");
		if (header.isEmpty()) {
			throw new IllegalStateException("Sheet " + sheetName + " has no header row");
		}

		List<Object> headers = header.get(0);
		for (int i = 0; i < headers.size(); i++) {
			if (STATUS_HEADER.equalsIgnoreCase(headers.get(i).toString().trim())) {
				statusColumnIndex = i;
				return statusColumnIndex;
			}
		}
		throw new IllegalStateException("No '" + STATUS_HEADER + "' column found in sheet " + sheetName);
	}

	private List<List<Object>> read(String range) {
		try {
			ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
			List<List<Object>> values = response.getValues();
			return values == null ? new ArrayList<>() : values;
		} catch (IOException e) {
			throw new IllegalStateException("Could not read range " + range + " from the sheet", e);
		}
	}

	// Columns run A..Z then AA, AB and so on, so casting ('A' + index) to a char is wrong
	// as soon as the sheet grows past 26 columns.
	static String columnLetter(int zeroBasedIndex) {
		StringBuilder letters = new StringBuilder();
		int index = zeroBasedIndex;
		while (index >= 0) {
			letters.insert(0, (char) ('A' + index % 26));
			index = index / 26 - 1;
		}
		return letters.toString();
	}

	private static Sheets buildService() {
		try {
			GoogleCredentials credentials = loadCredentials()
					.createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS));

			return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(),
					new HttpCredentialsAdapter(credentials)).setApplicationName("Jira Test Execution").build();
		} catch (Exception e) {
			throw new IllegalStateException("Could not connect to Google Sheets. Check google.credentials.path", e);
		}
	}

	private static GoogleCredentials loadCredentials() throws IOException {
		String keyPath = ConfigReader.get("google.credentials.path");
		if (keyPath.isEmpty()) {
			// Falls back to whatever GOOGLE_APPLICATION_CREDENTIALS points at.
			return GoogleCredentials.getApplicationDefault();
		}
		try (FileInputStream in = new FileInputStream(keyPath)) {
			return GoogleCredentials.fromStream(in);
		}
	}
}
