package jiraAutomate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import junit.framework.Assert;

public class SelectExecutionCyclePage {

	public WebDriver driver;
	WebDriverWait wait;
	String releaseCycleName = "Deployment/Production Tests";

	public SelectExecutionCyclePage(WebDriver driver) {

		this.driver = driver;
		wait = new WebDriverWait(driver, 60);
	}

	@FindBy(xpath = "//button[@id='listViewBtn']")
	public WebElement clickListTab;
	
	@FindBy(xpath = "//li[contains(text(),'50')]")
	public WebElement select50Count;
	
	@FindBy(xpath = "//span[contains(@class,'aui-icon aui-icon-small aui-iconfont-arrow-down dropDown-Trigger')]")
	public WebElement valueDropdownDownwardArrow;
	
	@FindBy(xpath = "//a[@class='eButton']")
	public WebElement selectE;
	
	@FindBy(xpath = "//a[contains(@id,'aui-test-cycles-tab')]/* | //h1[contains(., 'view this project')]")
	public WebElement cycleSummaryText;
	
	@FindBy(xpath = "//a[contains(@id,'aui-test-cycles-tab')]/*")
	public WebElement cycleSummaryTextAllowed;
	
	@FindBy(xpath = "//h1[contains(., 'view this project')]")
	public WebElement cycleSummaryTextBlocked;

	@FindBy(xpath = "//a[contains(@id,'aui-test-cycles-tab')]/*")
	public WebElement cycleDate;

	public WebElement clickReleaseCycleArrow(String cycleName) {
		String element = "//div[contains(text(),'" + cycleName + "')]";
		WebElement releaseCycleArrow = driver
				.findElement(By.xpath(element + "/../../../descendant::*[@class='jstree-icon jstree-ocl']"));
		return releaseCycleArrow;
	}

	public WebElement clickReleaseCycleYear(String cycleYear) {
		String element = "//div[contains(text(),'" + cycleYear + "')]";
		WebElement releaseCycleYear = driver.findElement(By.xpath(element + "/../../preceding-sibling::i"));
		return releaseCycleYear;
	}

	public WebElement clickReleaseCycleDate(String cycleName) {
		WebElement releaseCycleDate = driver.findElement(By.xpath("//div[contains(text(),'" + cycleName + "')]"));
		return releaseCycleDate;
	}

	public void OpenCycleSummary() {
		
		wait.until(waitforElement(cycleSummaryText));
		try {
		    if (cycleSummaryTextAllowed.isDisplayed()) {
		    	JavascriptExecutor js = (JavascriptExecutor) driver;
		    	js.executeScript("arguments[0].click();", cycleSummaryTextAllowed);
		    	System.out.println("Cycle Summary Tab Clicked");
		        Reporter.log("Cycle Summary Tab Clicked");
		    }
		} catch (Exception e) {
			System.out.println("You are not allowed to view this project");
			Reporter.log("You are not allowed to view this project"+ e.getMessage());
			Assert.fail("Blocked- You are not allowed to view this project");
		}
		
	}

	public void SelectReleaseCycle(String cycleName) {

		wait.until(waitforElement(clickReleaseCycleArrow(cycleName)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				clickReleaseCycleArrow(cycleName));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", clickReleaseCycleArrow(cycleName));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void SelectReleaseCycleYear(String cycleYear) {

		wait.until(waitforElement(clickReleaseCycleYear(cycleYear)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", clickReleaseCycleYear(cycleYear));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", clickReleaseCycleYear(cycleYear));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void OpenCycleDate(String cycleName) {

		wait.until(waitforElement(clickReleaseCycleDate(cycleName)));
		wait.until(ExpectedConditions.elementToBeClickable(clickReleaseCycleDate(cycleName)));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", clickReleaseCycleDate(cycleName));
	}
	
	public void SelectDefectCountAndClickExecutionButtonE() {

		SelectDefectIDCount();
		SelectDefectExecutionE();
	}
	
	public void SelectDefectIDCount() {

		wait.until(waitforElement(valueDropdownDownwardArrow));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", valueDropdownDownwardArrow);
		js.executeScript("arguments[0].click();", valueDropdownDownwardArrow);
		try {
			wait.until(waitforElement(select50Count));
			js.executeScript("arguments[0].click();", select50Count);
		} catch (Exception e) {
			System.out.println("Already 50 value count is selected");
		}
	}
	
	public void SelectDefectExecutionE() {

		try {
			wait.until(waitforElement(selectE));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", selectE);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", selectE);
			wait.until(waitforElement(clickListTab));
			js.executeScript("arguments[0].click();", clickListTab);
		} catch (Exception e) {
			System.out.println("Element E is already Selected");
		}
	}

	public static ExpectedCondition<Boolean> waitforElement(WebElement el) {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				boolean flag = false;
				try {
					if (el.isDisplayed()) {
						flag = true;
					}
				} catch (Exception e) {
					System.out.println("inside catch block " + e.getMessage());
				}
				return flag;
			}

		};
	}
}
