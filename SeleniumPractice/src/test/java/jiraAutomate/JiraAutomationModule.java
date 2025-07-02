package jiraAutomate;

import java.util.List;
import java.util.Collections;
import java.io.FileInputStream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.telus.utility.BrowserUtils;
import com.telus.utility.ExcelDataWrite;
import com.telus.utility.ExtentReportManager;
import com.telus.utility.GoogleSheetDataWriter;

public class JiraAutomationModule {

    WebDriver driver;

    private static final String SPREADSHEET_ID = "1QiSQ_709cRz7RmrDAhmTcjp0I_-C5IRz_pG-ggvQw_o";
    private static final String RANGE = "Manual!A2:Z";
    private static final String CREDENTIALS_PATH = "src/main/service-account.json";

    @Test
    public void LaunchJira() {
        driver = BrowserUtils.launchJira("LaunchJira");
    }

    @Test(dataProvider = "testData", dependsOnMethods = { "LaunchJira" })
    public void LoginSSO(org.json.simple.JSONObject testData) {
        try {
            LoginJiraPage login = PageFactory.initElements(driver, LoginJiraPage.class);
            login.LoginSSO(testData.get("USERNAME").toString(), testData.get("PASSWORD").toString());
        } catch (Exception e) {
            e.printStackTrace();
            ExtentReportManager.getTest().fail("Failed to execute LoginSSO test: " + e.getMessage());
            throw new RuntimeException("Failed to execute LoginSSO test: " + e.getMessage());
        }
    }

    @Test(dataProvider = "testData", dependsOnMethods = { "LoginSSO" })
    public void SelectRelease(org.json.simple.JSONObject testData) {
        SelectExecutionCyclePage cycle = PageFactory.initElements(driver, SelectExecutionCyclePage.class);
        cycle.OpenCycleSummary();
        cycle.SelectReleaseCycle(testData.get("RELEASE_CYCLE_NAME").toString());
        cycle.SelectReleaseCycleYear(testData.get("RELEASE_CYCLE_YEAR").toString());
        cycle.OpenCycleDate(testData.get("RELEASE_CYCLE_DATE").toString());
        cycle.SelectDefectCountAndClickExecutionButtonE();
    }

    @Test(dataProvider = "JiraData", dependsOnMethods = { "SelectRelease" })
    public void SelectOrders(String scenario, String status, String order, String stepNumber, String continueExecution,
            String executionStatus) throws InterruptedException {
        TestCaseExecutionPage cycle = PageFactory.initElements(driver, TestCaseExecutionPage.class);
        cycle.SelectOrder(scenario, status, order, stepNumber, continueExecution);
    }

    @AfterSuite()
    public void SelectOrders() {
    	GoogleSheetDataWriter write = PageFactory.initElements(driver, GoogleSheetDataWriter.class);
        write.updateExecutionStatusColumn();
        ExtentReportManager.flushReports();
        if (driver != null) {
            // driver.quit();
        }
    }

    @DataProvider(name = "JiraData")
    public Object[][] getGoogleSheetData() throws Exception {
        Sheets sheetsService = getSheetsService();

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();

        List<List<Object>> values = response.getValues();
        Object[][] result = new Object[values.size()][];

        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i).toArray(new String[0]);
        }

        return result;
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

    @DataProvider(name = "testData")
    public Object[][] getTestData() {
        com.telus.utility.JsonDataReader reader = new com.telus.utility.JsonDataReader();
        return reader.getTestData();
    }
}
