package jiraAutomate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import junit.framework.Assert;

public class LoginJiraPage {

	public WebDriver driver;
	WebDriverWait wait;

	public LoginJiraPage(WebDriver driver) {

		this.driver = driver;
		wait = new WebDriverWait(driver, 10);
	}
    
	
	@FindBy(id = "username")
	WebElement user;

	@FindBy(id = "password")
	WebElement pass;

	@FindBy(xpath = "//input[@id='Login'] | //button[@data-label-ignore='login']")
	public WebElement loginButton;

	@FindBy(xpath = "//section[@id='content']")
	public WebElement loginValidation;

	public void LoginSSO(String usernameSF, String passwordSF) throws InterruptedException {

		wait.until(waitforElement(user));
		user.sendKeys(usernameSF);
		wait.until(waitforElement(pass));
		pass.sendKeys(passwordSF);
		wait.until(waitforElement(loginButton));
		loginButton.click();
		
		try {
		    if (loginValidation.isDisplayed()) {
		        System.out.println("Credential is correct");
		        Reporter.log("Credential is correct");
		    }
		} catch (Exception e) {
			System.out.println("Credential is incorrect");
			Reporter.log("Credential is incorrect"+ e.getMessage());
			Assert.fail("Login failed - Password is incorrect");
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