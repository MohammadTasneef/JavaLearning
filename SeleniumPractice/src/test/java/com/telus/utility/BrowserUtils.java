package com.telus.utility;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserUtils {

    public static WebDriver launchJira(String testName) {
        ExtentReportManager.createTest(testName).info("Launching Jira");

        // Set the system property
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriversNew\\chromedriver.exe");

        // Instantiate ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Maximize and configure
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        // Open Jira URL
        String url = "https://jira.tsl.telus.com/projects/BSBDTR?selectedItem=com.thed.zephyr.je%3Azephyr-tests-page#test-cycles-tab";
        driver.get(url);

        ExtentReportManager.getTest().pass("Jira launched successfully and SSO page appeared.");
        System.out.println("SSO Page appeared successfully");

        return driver;
    }
}
