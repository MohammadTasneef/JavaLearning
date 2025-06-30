package jiraAutomate;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.telus.utility.BrowserUtils;
import com.telus.utility.ExcelDataRead;
import com.telus.utility.ExcelDataWrite;
import com.telus.utility.ExtentReportManager;
import com.telus.utility.JsonDataReader;

public class JiraAutomationModule {

	WebDriver driver;

	@Test
	public void LaunchJira() {

		driver = BrowserUtils.launchJira("LaunchJira");

	}

	@Test(dataProvider = "testData", dependsOnMethods = { "LaunchJira" })
	public void LoginSSO(JSONObject testData) {                    // Changed parameter type to JSONObject
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
	public void SelectRelease(JSONObject testData) {
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
		ExcelDataWrite write = PageFactory.initElements(driver, ExcelDataWrite.class);
		write.updateExecutionStatusColumn();
		ExtentReportManager.flushReports(); // 💡 Flush the report
		if (driver != null) {
			//driver.quit();
		}
	}

	@DataProvider(name = "JiraData")
	public String[][] getData() throws IOException {
		String excelPath = ".\\ExcelData\\Book1.xlsx";

		int Totalrows = ExcelDataRead.getRowCount(excelPath, "Manual");
		int TotalColumns = ExcelDataRead.getCellCount(excelPath, "Manual", 1);

		String loginData[][] = new String[Totalrows][TotalColumns];

		for (int i = 1; i <= Totalrows; i++) {

			for (int j = 0; j < TotalColumns; j++) {

				loginData[i - 1][j] = ExcelDataRead.getCellData(excelPath, "Manual", i, j);
			}

		}

		return loginData;
	}

	@DataProvider(name = "testData")
	public Object[][] getTestData() {
		JsonDataReader reader = new JsonDataReader();
		return reader.getTestData();
	}

}
