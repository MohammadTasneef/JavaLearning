package com.automation.pages;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.automation.utils.ExtentReportManager;

/**
 * The Zephyr execution grid. A test case is found by paging through the cycle, opened, then
 * each of its steps is marked before an overall status is set on the execution.
 */
public class TestExecutionPage extends BasePage {

	/** Cycles are paged, so give up rather than clicking "next" forever if an id is missing. */
	private static final int MAX_PAGES = 25;

	public TestExecutionPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[@class='trigger-dropDown']")
	private List<WebElement> stepStatusDropdowns;

	@FindBy(xpath = "//a[@class='next-page-execution'] | //a[@class='prev-page-execution']")
	private WebElement nextPageButton;

	@FindBy(xpath = "//div[@id='readonly-comment-div']/*")
	private WebElement commentBox;

	@FindBy(xpath = "//textarea[@placeholder='Start Typing...']")
	private WebElement commentTextArea;

	@FindBy(xpath = "//textarea[@role='combobox']")
	private WebElement defectBox;

	@FindBy(xpath = "//input[@value='Execute']")
	private WebElement executeButton;

	@FindBy(xpath = "//span[@class='aui-icon aui-icon-small aui-iconfont-arrow-down dropDown-Trigger status-dropDown-Trigger']")
	private WebElement overallStatusArrow;

	/**
	 * Runs one row from the sheet. Already executed cases are skipped so a rerun does not
	 * overwrite results that were set by hand.
	 */
	public void executeTestCase(String jiraId, String status, String orderId, String failSteps,
			String continueExecution) {

		String testCaseNumber = testCaseNumberOf(jiraId);
		ExtentReportManager.getTest().info("Jira id " + jiraId + ", test case number " + testCaseNumber);

		searchTestCase(testCaseNumber);

		String currentStatus = getExecutionStatus(testCaseNumber);
		ExtentReportManager.getTest().info("Current execution status is " + currentStatus);

		if (!"UNEXECUTED".equalsIgnoreCase(currentStatus)) {
			ExtentReportManager.getTest().info(testCaseNumber + " is already executed, skipping");
			return;
		}

		if ("PASS".equalsIgnoreCase(status)) {
			enterOrderIdInComment(orderId);
		} else {
			linkDefect(orderId);
		}

		setStepStatuses(status, failSteps, continueExecution);
		setOverallStatus(status);
	}

	public String getExecutionStatus(String testCaseNumber) {
		WebElement cell = driver.findElement(
				By.xpath("//div[contains(text(),'" + testCaseNumber + "')]/../div[@class='execution-status']/*"));
		return cell.getText().trim();
	}

	/**
	 * Walks the cycle a page at a time until the test case is open. Used to recurse into
	 * itself with no depth limit, which turned a missing id into a StackOverflowError.
	 */
	private void searchTestCase(String testCaseNumber) {
		for (int page = 1; page <= MAX_PAGES; page++) {
			if (openTestCase(testCaseNumber)) {
				ExtentReportManager.getTest().pass("Opened test case " + testCaseNumber);
				return;
			}
			if (!goToNextPage()) {
				break;
			}
			ExtentReportManager.getTest().info("Not on this page, moved to page " + (page + 1));
		}

		String message = "Test case " + testCaseNumber + " was not found in this release cycle";
		ExtentReportManager.getTest().fail(message);
		Assert.fail(message);
	}

	private boolean openTestCase(String testCaseNumber) {
		// Two attempts, because the first click sometimes lands while the grid is still
		// settling and silently does nothing.
		for (int attempt = 1; attempt <= 2; attempt++) {
			try {
				WebElement row = testCaseCell(testCaseNumber);
				waitForElement(row);
				scrollIntoView(row);
				new Actions(driver).moveToElement(row).perform();
				clickWithJs(row);
				pause(2000);

				if (isTestCaseOpen(testCaseNumber)) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	private boolean isTestCaseOpen(String testCaseNumber) {
		try {
			return driver.findElement(By.xpath("//a[contains(text(),'" + testCaseNumber + "')]/parent::h1"))
					.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	private boolean goToNextPage() {
		try {
			if (!isPresent(nextPageButton)) {
				return false;
			}
			scrollIntoView(nextPageButton);
			clickWithJs(nextPageButton);
			pause(1500);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Marks every step. A passing case sets all steps to PASS; a failing one sets the steps
	 * listed in failSteps to FAIL or BLOCKED and passes the rest. When continueExecution is
	 * anything but Yes, the remaining steps are left alone because execution stopped there.
	 *
	 * This replaces three overlapping methods that each looped the step dropdowns slightly
	 * differently.
	 */
	public void setStepStatuses(String overallStatus, String failSteps, String continueExecution) {
		int stepCount = stepStatusDropdowns.size();
		ExtentReportManager.getTest().info("Test case has " + stepCount + " steps");

		boolean caseIsFailing = "FAIL".equalsIgnoreCase(overallStatus) || "BLOCKED".equalsIgnoreCase(overallStatus);
		Set<String> failingSteps = parseStepNumbers(failSteps);

		if (caseIsFailing) {
			ExtentReportManager.getTest().info("Failing at steps " + failingSteps);
		}

		for (int step = 1; step <= stepCount; step++) {
			boolean stepFails = caseIsFailing && failingSteps.contains(String.valueOf(step));
			setStepStatus(step, stepFails ? overallStatus.toUpperCase() : "PASS");

			if (stepFails && !"Yes".equalsIgnoreCase(continueExecution.trim())) {
				ExtentReportManager.getTest().info("Execution stopped at step " + step);
				break;
			}
		}
	}

	private void setStepStatus(int step, String status) {
		try {
			WebElement arrow = driver.findElement(By.xpath("(//span[@class='trigger-dropDown'])[" + step + "]"));
			scrollIntoView(arrow);
			clickWithJs(arrow);

			WebElement option = driver.findElement(By.xpath("(//li[@title='" + status + "'])[" + step + "]"));
			waitForElement(option);
			clickWithJs(option);

			// The row redraws once the status saves. If it does not go stale, fall back to a
			// short pause rather than failing the whole case.
			try {
				wait.until(ExpectedConditions.stalenessOf(option));
			} catch (Exception e) {
				pause(2000);
			}
		} catch (Exception e) {
			ExtentReportManager.getTest().warning("Could not set step " + step + " to " + status);
		}
	}

	public void setOverallStatus(String status) {
		if ("PASS".equalsIgnoreCase(status)) {
			waitForElement(executeButton);
			clickWithJs(executeButton);
			ExtentReportManager.getTest().pass("Execution marked as PASS");
			pause(3000);
			return;
		}

		waitForElement(overallStatusArrow);
		new Actions(driver).moveToElement(overallStatusArrow).perform();
		scrollIntoView(overallStatusArrow);
		clickWithJs(overallStatusArrow);

		WebElement option = driver
				.findElement(By.xpath("//li[@class='updateStatus' and text()='" + status.toUpperCase() + "']"));
		waitForElement(option);
		clickWithJs(option);

		ExtentReportManager.getTest().pass("Execution marked as " + status.toUpperCase());
		pause(3000);
	}

	/** The order id goes in the execution comment for a passing case. */
	private void enterOrderIdInComment(String orderId) {
		waitForElement(commentBox);
		new Actions(driver).moveToElement(commentBox).doubleClick().perform();
		clickWithJs(commentBox);

		// The comment turns into a textarea only after the double click registers.
		waitForElement(commentTextArea);
		clickWithJs(commentTextArea);
		commentTextArea.sendKeys(orderId);

		ExtentReportManager.getTest().pass("Entered order id " + orderId + " in the comment box");
		pause(2000);
	}

	/** A failing case links the defect instead, picked from Jira's autocomplete list. */
	private void linkDefect(String defectId) {
		waitForElement(defectBox);
		new Actions(driver).moveToElement(defectBox).doubleClick().perform();
		clickWithJs(defectBox);

		defectBox.clear();
		defectBox.sendKeys(defectId);

		By suggestion = By
				.xpath("//ul[@id='history-search']//li[contains(@class,'aui-list-item')]//b[text()='" + defectId + "']");
		wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion));
		clickWithJs(driver.findElement(suggestion));

		ExtentReportManager.getTest().pass("Linked defect " + defectId);
	}

	private WebElement testCaseCell(String testCaseNumber) {
		return driver.findElement(By.xpath("//div[contains(text(),'" + testCaseNumber + "')]"));
	}

	/** The sheet holds full Jira ids like PROJ-1553 but the grid shows only the number. */
	private String testCaseNumberOf(String jiraId) {
		String[] parts = jiraId.split("-");
		return parts.length > 1 ? parts[1].trim() : jiraId.trim();
	}

	private Set<String> parseStepNumbers(String failSteps) {
		if (failSteps == null || failSteps.isBlank()) {
			return new HashSet<>();
		}
		return new HashSet<>(Arrays.asList(failSteps.trim().split("\\s*,\\s*")));
	}
}
