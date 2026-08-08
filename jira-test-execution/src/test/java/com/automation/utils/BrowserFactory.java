package com.automation.utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserFactory {

	private BrowserFactory() {
	}

	public static WebDriver launchJira() {
		// Selenium 4 resolves the matching driver binary itself, so there is no
		// chromedriver.exe path to keep in sync any more.
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		// Kept short on purpose. The pages use explicit waits for anything slow, and a long
		// implicit wait makes every one of those polls take the full timeout.
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(ConfigReader.get("jira.url"));
		ExtentReportManager.getTest().pass("Jira launched, SSO page shown");
		return driver;
	}
}
