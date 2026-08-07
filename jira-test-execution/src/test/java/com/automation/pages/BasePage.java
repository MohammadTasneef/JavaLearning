package com.automation.pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Driver, wait and the handful of helpers every page needed. Each page class used to carry
 * its own identical copy of waitForElement plus its own JavascriptExecutor casts.
 */
public class BasePage {

	protected final WebDriver driver;
	protected final WebDriverWait wait;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	/**
	 * Zephyr rebuilds its grid after most interactions, so isDisplayed regularly throws a
	 * stale element exception on an element that is about to come back. Treating any
	 * exception as "not ready yet" lets the wait keep polling instead of failing outright.
	 */
	protected void waitForElement(WebElement element) {
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver ignored) {
				try {
					return element.isDisplayed();
				} catch (Exception e) {
					return false;
				}
			}
		});
	}

	protected boolean isPresent(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	protected void clickWithJs(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	protected void scrollIntoView(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
	}

	/**
	 * Zephyr animates parts of the grid and there is no element state that reliably says the
	 * animation finished, so a few places still need a plain pause. Kept in one method so
	 * they are easy to find rather than scattered through the pages.
	 */
	protected void pause(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
