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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.telus.utility.ExcelData;
import com.telus.utility.JsonDataReader;

public class JiraAutomationModule {

	WebDriver driver;

	@Test
	public void LaunchJira() {
		// Set The system property
		System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriversNew\\chromedriver.exe");

		// creating instance of Chrome driver
		driver = new ChromeDriver();

		// Storing the URL
		String url = "https://jira.tsl.telus.com/projects/B2BM?selectedItem=com.thed.zephyr.je:zephyr-tests-page#test-cycles-tab";
        //https://jira.tsl.telus.com/projects/B2BM?selectedItem=com.thed.zephyr.je:zephyr-tests-page#test-cycles-tab
		
		// Lunch the browser
		driver.get(url);

		// maximize the window
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		System.out.println("SSO Page appeared successfully");
	}

	@Test(dataProvider = "testData", dependsOnMethods = { "LaunchJira" })
	public void LoginSSO(JSONObject testData) { // Changed parameter type to JSONObject
		try {
			LoginJiraPage login = PageFactory.initElements(driver, LoginJiraPage.class);
			login.LoginSSO(testData.get("USERNAME").toString(), testData.get("PASSWORD").toString());
		} catch (Exception e) {
			e.printStackTrace();
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
	public void SelectOrders(String scenario, String status, String order, String stepNumber, String continueExecution)
			throws InterruptedException {
		TestCaseExecutionPage cycle = PageFactory.initElements(driver, TestCaseExecutionPage.class);
		cycle.SelectOrder(scenario, status, order, stepNumber, continueExecution);

	}

	@DataProvider(name = "JiraData")
	public String[][] getData() throws IOException {
		String excelPath = ".\\ExcelData\\Book1.xlsx";

		int Totalrows = ExcelData.getRowCount(excelPath, "Sheet2");
		int TotalColumns = ExcelData.getCellCount(excelPath, "Sheet2", 1);

		String loginData[][] = new String[Totalrows][TotalColumns];

		for (int i = 1; i <= Totalrows; i++) {

			for (int j = 0; j < TotalColumns; j++) {

				loginData[i - 1][j] = ExcelData.getCellData(excelPath, "Sheet2", i, j);
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
