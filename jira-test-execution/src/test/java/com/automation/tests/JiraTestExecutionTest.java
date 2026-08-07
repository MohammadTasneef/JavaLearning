package com.automation.tests;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.pages.JiraLoginPage;
import com.automation.pages.TestCycleSummaryPage;
import com.automation.pages.TestExecutionPage;
import com.automation.utils.BrowserFactory;
import com.automation.utils.ConfigReader;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.GoogleSheetClient;
import com.automation.utils.JsonDataReader;

/**
 * Executes a release cycle's manual test cases in Zephyr for Jira.
 *
 * The steps run in order and each depends on the one before it, because they share a single
 * browser session: log in, open the release cycle, then work through the test cases read
 * from the Google Sheet. Statuses are collected as each case finishes and written back to
 * the sheet in one batch at the end.
 */
public class JiraTestExecutionTest {

	// Sheet column headers. Change these if the headers in your sheet are named differently.
	private static final String COL_JIRA_ID = "Scenario";
	private static final String COL_STATUS = "Status";
	private static final String COL_ORDER_ID = "Order";
	private static final String COL_FAIL_STEPS = "StepNumber";
	private static final String COL_CONTINUE = "ContinueExecution";

	private WebDriver driver;
	private GoogleSheetClient sheet;

	/** Execution status per sheet row, written back together once the suite finishes. */
	private final Map<String, String> statusByRow = new LinkedHashMap<>();

	@Test
	public void launchJira() {
		ExtentReportManager.createTest("Launch Jira");
		driver = BrowserFactory.launchJira();
	}

	@Test(dependsOnMethods = { "launchJira" })
	public void loginToJira() {
		ExtentReportManager.createTest("Login through SSO");
		PageFactory.initElements(driver, JiraLoginPage.class).loginWithSso();
	}

	@Test(dataProvider = "releaseCycles", dependsOnMethods = { "loginToJira" })
	public void openReleaseCycle(Map<String, String> cycle) {
		ExtentReportManager.createTest("Open release cycle " + cycle.get("RELEASE_CYCLE_DATE"));

		TestCycleSummaryPage summary = PageFactory.initElements(driver, TestCycleSummaryPage.class);
		summary.openCycleSummary();
		summary.selectReleaseCycle(cycle.get("RELEASE_CYCLE_NAME"));
		summary.selectReleaseCycleYear(cycle.get("RELEASE_CYCLE_YEAR"));
		summary.openCycleDate(cycle.get("RELEASE_CYCLE_DATE"));
		summary.openExecutionView();
	}

	@Test(dataProvider = "testCases", dependsOnMethods = { "openReleaseCycle" })
	public void executeTestCase(Map<String, String> testCase) {
		String jiraId = testCase.get(COL_JIRA_ID);
		ExtentReportManager.createTest("Execute " + jiraId);

		TestExecutionPage execution = PageFactory.initElements(driver, TestExecutionPage.class);
		execution.executeTestCase(jiraId, testCase.get(COL_STATUS), testCase.get(COL_ORDER_ID),
				testCase.get(COL_FAIL_STEPS), testCase.get(COL_CONTINUE));

		// Read the status straight back off the grid rather than assuming the run set it.
		String actual = execution.getExecutionStatus(jiraId.contains("-") ? jiraId.split("-")[1].trim() : jiraId);
		statusByRow.put(testCase.get(GoogleSheetClient.ROW_NUMBER), actual);
	}

	@AfterClass(alwaysRun = true)
	public void publishResults() {
		try {
			if (sheet != null) {
				sheet.updateExecutionStatuses(statusByRow);
			}
		} finally {
			ExtentReportManager.flushReports();
			if (driver != null) {
				driver.quit();
			}
		}
	}

	@DataProvider(name = "releaseCycles")
	public Object[][] releaseCycles() {
		List<Map<String, String>> cycles = JsonDataReader.readReleaseCycles();
		Object[][] data = new Object[cycles.size()][1];
		for (int i = 0; i < cycles.size(); i++) {
			data[i][0] = cycles.get(i);
		}
		return data;
	}

	@DataProvider(name = "testCases")
	public Object[][] testCases() {
		if (ConfigReader.isBlank("spreadsheet.id")) {
			throw new SkipException("spreadsheet.id is not set - see src/test/resources/config.properties");
		}

		sheet = new GoogleSheetClient();
		List<Map<String, String>> testCases = sheet.readTestCases();

		Object[][] data = new Object[testCases.size()][1];
		for (int i = 0; i < testCases.size(); i++) {
			data[i][0] = testCases.get(i);
		}
		return data;
	}
}
