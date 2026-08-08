package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.automation.utils.ExtentReportManager;

/**
 * The Zephyr "Test Cycles" tab, where a release cycle is picked before execution starts.
 * The cycle tree is year > cycle > date, and each level has to be expanded in turn.
 */
public class TestCycleSummaryPage extends BasePage {

	public TestCycleSummaryPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[contains(@id,'aui-test-cycles-tab')]/*")
	private WebElement cycleSummaryTab;

	@FindBy(xpath = "//h1[contains(., 'view this project')]")
	private WebElement noPermissionMessage;

	@FindBy(xpath = "//span[contains(@class,'aui-iconfont-arrow-down dropDown-Trigger')]")
	private WebElement pageSizeDropdown;

	@FindBy(xpath = "//li[contains(text(),'10')]/following-sibling::li[contains(text(),'25')]")
	private WebElement pageSizeAlreadyOpen;

	@FindBy(xpath = "//li[contains(text(),'50')]")
	private WebElement pageSize50;

	@FindBy(xpath = "//a[@class='eButton']")
	private WebElement executeButton;

	@FindBy(xpath = "//button[@id='listViewBtn']")
	private WebElement listViewButton;

	public void openCycleSummary() {
		if (isPresent(noPermissionMessage)) {
			ExtentReportManager.getTest().fail("Blocked - you are not allowed to view this project");
			Assert.fail("Blocked - you are not allowed to view this project");
		}

		waitForElement(cycleSummaryTab);
		clickWithJs(cycleSummaryTab);
		ExtentReportManager.getTest().pass("Opened the Test Cycles tab");
	}

	public void selectReleaseCycle(String cycleName) {
		WebElement expandArrow = cycleExpandArrow(cycleName);
		waitForElement(expandArrow);
		scrollIntoView(expandArrow);
		clickWithJs(expandArrow);
		ExtentReportManager.getTest().pass("Expanded release cycle " + cycleName);
		pause(2000);
	}

	public void selectReleaseCycleYear(String cycleYear) {
		WebElement expandArrow = yearExpandArrow(cycleYear);
		waitForElement(expandArrow);
		scrollIntoView(expandArrow);
		clickWithJs(expandArrow);
		ExtentReportManager.getTest().pass("Expanded cycle year " + cycleYear);
		pause(2000);
	}

	public void openCycleDate(String cycleDate) {
		WebElement dated = cycleByName(cycleDate);
		waitForElement(dated);
		clickWithJs(dated);
		ExtentReportManager.getTest().pass("Opened cycle dated " + cycleDate);
	}

	public void openExecutionView() {
		showFiftyRowsPerPage();
		openListView();
	}

	/**
	 * Default page size is 10, which means paging through far more screens than necessary
	 * when looking for a test case. If the dropdown already shows 50 there is nothing to do.
	 */
	private void showFiftyRowsPerPage() {
		waitForElement(pageSizeDropdown);
		scrollIntoView(pageSizeDropdown);
		clickWithJs(pageSizeDropdown);

		if (isPresent(pageSizeAlreadyOpen)) {
			ExtentReportManager.getTest().info("Page size is already 50");
			return;
		}

		clickWithJs(pageSize50);
		ExtentReportManager.getTest().pass("Page size set to 50");
	}

	private void openListView() {
		if (!isPresent(executeButton)) {
			ExtentReportManager.getTest().info("Execution view is already open");
			return;
		}

		scrollIntoView(executeButton);
		clickWithJs(executeButton);
		waitForElement(listViewButton);
		clickWithJs(listViewButton);
		ExtentReportManager.getTest().pass("Opened the execution list view");
	}

	// The cycle tree has no ids, so these are located relative to the visible label text.
	private WebElement cycleExpandArrow(String cycleName) {
		return driver.findElement(By.xpath(
				"//div[contains(text(),'" + cycleName + "')]/../../../descendant::*[@class='jstree-icon jstree-ocl']"));
	}

	private WebElement yearExpandArrow(String cycleYear) {
		return driver.findElement(By.xpath("//div[contains(text(),'" + cycleYear + "')]/../../preceding-sibling::i"));
	}

	private WebElement cycleByName(String name) {
		return driver.findElement(By.xpath("//div[contains(text(),'" + name + "')]"));
	}
}
