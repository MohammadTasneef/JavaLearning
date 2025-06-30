package com.telus.support;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeAttach {

	public static void main(String[] args) {
		// Set path to chromedriver.exe
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\\\.cache\\selenium\\chromedriver\\win64\\137.0.7151.68\\chromedriver.exe");
		
		// Automatically sets the right chromedriver binary
        WebDriverManager.chromedriver().setup();
        

        ChromeOptions options = new ChromeOptions();
        // Connect to the Chrome instance launched manually with remote debugging
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, 10);
		// Open a new tab in the already running browser
     // Step 1: Open a new tab using JavaScript
        ((JavascriptExecutor) driver).executeScript("window.open()");

        // Step 2: Get all window handles
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        // Step 3: Switch to the new tab
        driver.switchTo().window(tabs.get(1)); // index 1 is the new tab

        // Now you can navigate or perform actions in the new tab
        //driver.get("https://example.com");
		driver.get("https://sites.google.com/telus.com/crmenablement/salesforce-odyssey/submit-a-salesforce-request/access-and-etl");

		System.out.println("Opened new tab in existing Chrome session.");
		
		 // 3. Switch to iframe3 (outermost iframe)
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
        System.out.println("Switched to iframe3.");

        // 4. Switch to iframe2 (nested inside iframe3)
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
        System.out.println("Switched to iframe2.");

        // 5. Switch to iframe1 (innermost iframe containing form elements)
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
        System.out.println("Switched to iframe1.");
		
		 WebElement fullName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='FullName']")));
		driver.findElement(By.xpath("//input[@name='FullName']")).sendKeys("John Doe");
        driver.findElement(By.xpath("//input[@name='EmailAddress']")).sendKeys("john.doe@example.com");
        driver.findElement(By.xpath("//input[@name='location']")).sendKeys("Ontario");

        // 4. Select from dropdowns
        Select environmentType = new Select(driver.findElement(By.xpath("//select[@name='Environment']")));
        environmentType.selectByValue("Production"); // Choose the appropriate option

        driver.findElement(By.xpath("//input[@id='HowManyImpacted']")).sendKeys("5");
        
        //Select additionalDetails = new Select(driver.findElement(By.xpath("//select[@name='RequestSubType']")));
        //additionalDetails.selectByVisibleText("Other"); // Choose the appropriate option
        
        

        Select requestType = new Select(driver.findElement(By.xpath("//select[@name='RequestType']")));
        requestType.selectByValue("Other"); // Choose the appropriate option
        
      //driver.findElement(By.xpath("//input[@name='FullName']")).click();

        

        // 5. Fill out the additional info textarea
        driver.findElement(By.xpath("//textarea")).sendKeys("Please provide the Manager Name, and if there is an existing user that can be used to clone the profile and permissions.");

        // 6. Submit the form (if applicable)
        System.out.println("Form Filled");
        // driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

}
