package jiraAutomate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.telus.utility.ExcelData;

public class JiraAutomationModule {

	WebDriver driver;
	String USERNAME = "x22";
	String PASSWORD = "";
	String RELEASE_CYCLE_NAME="Sanity Tests"/*"Deployment/Production Tests"*/;
	String RELEASE_CYCLE_YEAR="YYYY - Sanity Execution";
	String RELEASE_CYCLE_DATE="DD-MM-YYYY";

	@Test
	public void LaunchJira() {
		// Set The system property
		System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriversNew\\chromedriver.exe");

		// creating instance of chrome driver
		driver = new ChromeDriver();

		// Storing the URL
		String url = "https://jira.tsl.telus.com/projects/BSBDTR?selectedItem=com.thed.zephyr.je%3Azephyr-tests-page#test-cycles-tab";

		// Lunch the browser
		driver.get(url);

		// maximize the window
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
		
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		String zoom="document.body.style.zoom = '0.7'";
//		js.executeScript(zoom);
		

		System.out.println("SSO Page appeared successfully");
	}

	@Test(dependsOnMethods = { "LaunchJira" })
	public void LoginSSO() {
		LoginJiraPage login = PageFactory.initElements(driver, LoginJiraPage.class);
		login.LoginSSO(USERNAME, PASSWORD);
	}
	
	@Test(dependsOnMethods = { "LoginSSO" })
	public void SelectRelease() {
		SelectExecutionCyclePage cycle = PageFactory.initElements(driver, SelectExecutionCyclePage.class);
		cycle.OpenCycleSummary();
		cycle.SelectReleaseCycle(RELEASE_CYCLE_NAME);
		cycle.SelectReleaseCycleYear(RELEASE_CYCLE_YEAR);
		cycle.OpenCycleDate(RELEASE_CYCLE_DATE);
		cycle.SelectDefectCountAndClickExecutionButtonE();
	}
	
	@Test(dataProvider = "JiraData" ,dependsOnMethods = { "SelectRelease" })
	public void SelectOrders(String scenario,String status,String order) throws InterruptedException  {
		TestCaseExecutionPage cycle = PageFactory.initElements(driver, TestCaseExecutionPage.class);
		cycle.SelectOrder(scenario,status,order);
	}
	
	@DataProvider(name="JiraData")
	public String[][] getData() throws IOException
	{
		String excelPath=".\\ExcelData\\Book1.xlsx";
		
	int Totalrows=ExcelData.getRowCount(excelPath, "Sheet2");
	int TotalColumns=ExcelData.getCellCount(excelPath, "Sheet2", 1);
	
	String loginData[][]=new String[Totalrows][TotalColumns];
	
	for(int i=1;i<=Totalrows;i++) {
		
		for(int j=0;j<TotalColumns;j++) {
			
			loginData[i-1][j]=ExcelData.getCellData(excelPath, "Sheet2", i, j);
		}
		
	}
	
		
		return loginData;
	}
	
	
}
