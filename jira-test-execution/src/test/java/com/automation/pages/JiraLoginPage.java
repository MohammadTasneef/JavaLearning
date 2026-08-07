package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.automation.utils.ConfigReader;
import com.automation.utils.ExtentReportManager;

public class JiraLoginPage extends BasePage {

	public JiraLoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "username")
	private WebElement username;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(xpath = "//input[@id='Login'] | //button[@data-label-ignore='login']")
	private WebElement loginButton;

	/**
	 * Credentials come from JIRA_USERNAME and JIRA_PASSWORD in the environment. They used to
	 * sit in a committed json file with the password base64 encoded, which only looks like
	 * protection - anyone with the file can decode it in one line.
	 */
	public void loginWithSso() {
		String user = ConfigReader.get("jira.username");
		String secret = ConfigReader.get("jira.password");

		if (user.isEmpty() || secret.isEmpty()) {
			throw new IllegalStateException("Set JIRA_USERNAME and JIRA_PASSWORD before running the suite");
		}

		waitForElement(username);
		username.sendKeys(user);

		waitForElement(password);
		password.sendKeys(secret);

		waitForElement(loginButton);
		loginButton.click();

		ExtentReportManager.getTest().pass("Submitted SSO login as " + user);
	}
}
