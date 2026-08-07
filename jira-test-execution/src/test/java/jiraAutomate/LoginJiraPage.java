package jiraAutomate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginJiraPage {

	public WebDriver driver;
	WebDriverWait wait;

	public LoginJiraPage(WebDriver driver) {

		this.driver = driver;
		wait = new WebDriverWait(driver, 60);
	}

	@FindBy(id = "username")
	WebElement user;

	@FindBy(id = "password")
	WebElement pass;

	@FindBy(xpath = "//input[@id='Login'] | //button[@data-label-ignore='login']")
	public WebElement loginButton;

	public void LoginSSO(String usernameSF, String passwordSF) {

		wait.until(waitforElement(user));
		user.sendKeys(usernameSF);
		wait.until(waitforElement(pass));
		pass.sendKeys(passwordSF);
		wait.until(waitforElement(loginButton));
		loginButton.click();
		System.out.println("Successfully logged in Jira");

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