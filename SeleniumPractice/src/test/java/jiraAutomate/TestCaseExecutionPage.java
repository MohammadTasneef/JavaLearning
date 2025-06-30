package jiraAutomate;

import java.io.ObjectInputFilter.Status;
import java.lang.classfile.instruction.ExceptionCatch;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.telus.utility.ExtentReportManager;

public class TestCaseExecutionPage {

	public static WebDriver driver;
	static WebDriverWait wait;

	public TestCaseExecutionPage(WebDriver driver) {

		this.driver = driver;
		wait = new WebDriverWait(driver, 15);
	}

	@FindBy(xpath = "//textarea[@role='combobox']")
	public WebElement defectBox;
	
	@FindBy(xpath = "//a[@role='presentation']")
	public WebElement defectNumber;
	
	@FindBy(xpath = "//span[contains(@class,'aui-icon aui-icon-small aui-iconfont-arrow-down dropDown-Trigger')]")
	public WebElement valueDropdownDownwardArrow;

	@FindBy(xpath = "//a[@class='next-page-execution'] | //a[@class='prev-page-execution']")
	public WebElement prevAndNextDefectIDButton;

	@FindBy(xpath = "//a[@class='eButton']")
	public WebElement selectE;

	@FindBy(xpath = "//button[@id='listViewBtn']")
	public WebElement clickListTab;
	
	@FindBy(xpath = "//input[@value='Execute']")
	public WebElement executionStatus;
	
	@FindBy(xpath = "//a[text()='BSBDTR-1616']/parent::h1")
	public WebElement defectIDVerify;
	
	@FindBy(xpath = "//div[@id='readonly-comment-div']/*")
	public WebElement commentBox;
	
	@FindBy(xpath = "//textarea[@placeholder='Start Typing...']")
	public WebElement enterTextcommentBox;
	
	@FindBy(xpath = "//span[@class='trigger-dropDown']")
	public static List<WebElement> passFailDropdownArrow;
	
	@FindBy(xpath = "//span[@class='status-readMode dropDown-select']")
	public WebElement failedOrBlockedexecutionStatus;
	
	@FindBy(xpath = "//span[@class='aui-icon aui-icon-small aui-iconfont-arrow-down dropDown-Trigger status-dropDown-Trigger']")
	public WebElement failedOrBlockedexecutionStatusArrow;
	
	
	//String VerifyDefect = defectIDVerify.getText();
	
	public void clickPassFailDropdownArrow(String status,String stepNumber,String continueExecution) throws InterruptedException {
		
		int length = passFailDropdownArrow.size();
		System.out.println("Size of the Elements are=" + length);
		ExtentReportManager.getTest().info("Size of the Elements are=" + length);
		 String statusArrowFirst ="(//span[@class='trigger-dropDown'])[";
         String statusArrowSecond ="]";
         String statusSelectFirst ="(//li[@title='" + status + "'])[";
         String statusSelectSecond ="]";
         
         for(int i =1; i<=length;i++) {
        	 
        	 if(status.equalsIgnoreCase("FAIL") || status.equalsIgnoreCase("BLOCKED"))
 			{
 				 FailedOrBlockedTestCases(status,stepNumber,continueExecution);
 				 break;
 			}
//        	 while(i!=1) {
//        	 try{
//        		 wait.until(ExpectedConditions.visibilityOf(staleElement));
//        	 }
//        	 catch (Exception e) {
//        		 wait.until(ExpectedConditions.visibilityOf(staleElement));
//			}}
//        	 while(i!=length) {
//        	 WebElement index= wait.until(ExpectedConditions.visibilityOf(passFailDropdownArrow.get(i)));
//        	 System.out.println("Index ="+index);
//        	 break;
//        	 }
             WebElement StatusArrow = driver.findElement(By.xpath(statusArrowFirst+i+statusArrowSecond));
             WebElement StatusSelect = driver.findElement(By.xpath(statusSelectFirst+i+statusSelectSecond));
             System.out.println("value="+StatusArrow);
             System.out.println("value="+StatusSelect);
//			try{
//				staleElementAction(StatusArrow, "WAITCLICABLE", "");
//			}
//			catch (Exception e) {
//				staleElementAction(StatusArrow, "WAITCLICABLE", "");	
//				}
             
//             try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//				
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(statusArrowFirst+i+statusArrowSecond)));
				JavascriptExecutor js = (JavascriptExecutor) driver;
				//wait.until(waitforElement(StatusArrow));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", StatusArrow);
				js.executeScript("arguments[0].click();", StatusArrow);
				wait.until(waitforElement(StatusSelect));
				js.executeScript("arguments[0].click();", StatusSelect);
				try {
				wait.until(ExpectedConditions.stalenessOf(StatusSelect));
				}
					catch (Exception e) {
						Thread.sleep(3000);
						System.out.println("Sleep is executed for 3 seconds");
					}
				}
			    
	}

	public WebElement orderID(String order) {
		WebElement Order_Number = driver.findElement(By.xpath("//div[contains(text(),'" + order + "')]"));
		return Order_Number;
	}

	public void SelectOrder(String scenario,String status,String order,String stepNumber,String continueExecution) throws InterruptedException {

		// String arrValues[] = ("ORDER_ID").split("\\|");

		//String[] arrValues = { /*"BSBDTR-1553", "BSBDTR-490", "BSBDTR-1276","BSBDTR-1398","BSBDTR-1400","BSBDTR-1387", */"BSBDTR-490","BSBDTR-1276", "BSBDTR-1655"};
		//String[] orderValues = {/*"OR-01587017", "OR-01587007","OR-01587011", "SanityTesting 24Feb", "19133764","testingggggg",*/"OR-01587244", "OR-01587247","OR-01586066"};

		//String ordersAre = "";

//		for (String i : arrValues) {
//			ordersAre = ordersAre + i + " ";
//		}

		//System.out.println("Orders are= [" + ordersAre + "]");

		//for (int i = 0; i < arrValues.length; i++) {
		    System.out.println("JIRA ID =" + scenario);
		    ExtentReportManager.getTest().info("JIRA ID =" + scenario);
		    String[] parts = scenario.split("-");
		    String Split=parts[1].trim();
		    System.out.println("The Test Case No is:"+Split);
		    ExtentReportManager.getTest().info("The Test Case No is:"+Split);
		    SearchOrder(Split);
		    String ExecutionStatusValue = "//div[contains(text(),'" + Split + "')]/../div[@class='execution-status']/*";
		    WebElement ExecutionStatus = driver.findElement(By.xpath(ExecutionStatusValue));
		    String ExecutionStatusText=ExecutionStatus.getText();
		    System.out.println("Execution Status Value="+ExecutionStatusText);
		    ExtentReportManager.getTest().info("Execution Status Value="+ExecutionStatusText);
			if(ExecutionStatusText.toString().equalsIgnoreCase("UNEXECUTED"))	
		{
			PassOrderIDInCommentBox(order,status);
			clickPassFailDropdownArrow(status,stepNumber,continueExecution);
			SelectOverallExecutionStatus(status);
		}
			else
			{
				ExtentReportManager.getTest().info(Split+" "+"is already executed");
				System.out.println(Split+" "+"is already executed");
			}
	}

	public void SearchOrder(String Split) {
		
		int FLAGS = 0;
//		String Verify = "//a[text()='" + ORDER + "']/parent::h1";
//		WebElement VerifyDefect = driver.findElement(By.xpath(Verify));
//		String VerifyDefectText=VerifyDefect.getText();
//		System.out.println("VerifyDefectText value="+VerifyDefectText);
		while (FLAGS != 1) {
			try {
				System.out.println("The Exact Jira ID to be searched:"+orderID(Split).getText());
				ExtentReportManager.getTest().info("The Exact Jira ID to be searched:"+orderID(Split).getText());
				Split=orderID(Split).getText().trim();
				wait.until(waitforElement(orderID(Split)));
				JavascriptExecutor js = (JavascriptExecutor) driver;
				wait.until(ExpectedConditions.elementToBeClickable(orderID(Split)));
				Thread.sleep(5000);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", orderID(Split));
				Thread.sleep(5000);
				Actions action = new Actions(driver);
				action.moveToElement(orderID(Split)).build().perform();
				js.executeScript("arguments[0].click();", orderID(Split));
				Thread.sleep(5000);
//				if (VerifyDefectText.equalsIgnoreCase(ORDER)){
//					System.out.println("Defect ID is already selected");
//				}
//				else {
//					SearchOrder(ORDER);				
//				}
				String Verify = "//a[text()='" + Split + "']/parent::h1";
				try {
				WebElement VerifyDefect = driver.findElement(By.xpath(Verify));
				wait.until(waitforElement(VerifyDefect));
				ExtentReportManager.getTest().info("Verified Order ID Selected:" + VerifyDefect.getText());
				System.out.println("Verified Order ID Selected:" + VerifyDefect.getText());
				}
				catch (Exception e) {
					ExtentReportManager.getTest().info("Again searching for Defect ID as click was not performed successfuly");
					System.out.println("Again searching for Defect ID as click was not performed successfuly");
					SearchOrder(Split);
				}
				
				FLAGS++;

			} catch (Exception e) {
				ExtentReportManager.getTest().info("Defect ID is not there on this page click on next button");
				System.out.println("Defect ID is not there on this page click on next button");
				try {
					if (prevAndNextDefectIDButton.isDisplayed() && FLAGS != 1) {
						JavascriptExecutor js = (JavascriptExecutor) driver;
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", prevAndNextDefectIDButton);
						js.executeScript("arguments[0].click();", prevAndNextDefectIDButton);
						ExtentReportManager.getTest().pass("Previous/Next button clicked");
						SearchOrder(Split);
					}
				} catch (Exception e1) {
					ExtentReportManager.getTest().info("Defect ID is already selected if FLAGS value is 1 and if not then Defect ID is not in this Release Cycle and the FLAG Value is="+FLAGS);
					System.out.println(
							"Defect ID is already selected if FLAGS value is 1 and if not then Defect ID is not in this Release Cycle");
					System.out.println("FLAGS value is =" + FLAGS);
					break;

				}
			}
		}
	}	
	
	public void clickAccessPointArrow() throws InterruptedException {
		 String staleElement ="(//li[@title='PASS'])[";
		 String staleElementSecond ="]";
		int i=0; 
		int length = passFailDropdownArrow.size();
		System.out.println("Size of the Elements are=" + length);
		i++;
		for (WebElement ele : passFailDropdownArrow) 
		{
			 while(i!=1)
			 {
			    try{
			    	
			    	wait.until(ExpectedConditions.stalenessOf(ele));
			    } 
			    catch (StaleElementReferenceException e1) {
					e1.printStackTrace();
				}}
				System.out.println("Value of ele="+ele);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				//wait.until(waitforElement(StatusArrow));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ele);
				js.executeScript("arguments[0].click();", ele);
				try {
					Thread.sleep(6000);
				} catch (StaleElementReferenceException e1) {
					e1.printStackTrace();
				}
				//wait.until(Expectedcon  (By.xpath(ele + "/../../preceding-sibling::i")));
				//wait.until(waitforElement(ele + "/../../preceding-sibling::i")));
				WebElement StatusArrow = driver.findElement(By.xpath(staleElement+i+staleElementSecond));
				js.executeScript("arguments[0].click();", StatusArrow);
			
		}
	}
	
	public void SelectOverallExecutionStatus(String status) throws InterruptedException {

		String FailedOrBlockedExecution ="//li[@class='updateStatus'  and text()='" + status + "']";
		if(status.equalsIgnoreCase("FAIL") || status.equalsIgnoreCase("BLOCKED"))
		{
			wait.until(waitforElement(failedOrBlockedexecutionStatusArrow));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", failedOrBlockedexecutionStatusArrow);
			wait.until(waitforElement(failedOrBlockedexecutionStatusArrow));
			js.executeScript("arguments[0].click();", failedOrBlockedexecutionStatusArrow);
			WebElement Execution = driver.findElement(By.xpath(FailedOrBlockedExecution));
			ExtentReportManager.getTest().info("Overall Execution Status is="+Execution.getText());
			wait.until(waitforElement(Execution));
			js.executeScript("arguments[0].click();", Execution);
			Thread.sleep(5000);	
		}
		else {
		wait.until(waitforElement(executionStatus));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", executionStatus);
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}}
	}
	
	public void PassOrderIDInCommentBox(String ID,String Status) throws InterruptedException {

		if(Status.equalsIgnoreCase("PASS"))
		{
		wait.until(waitforElement(commentBox));
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", commentBox);
	    wait.until(ExpectedConditions.elementToBeClickable(commentBox));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
        Thread.sleep(4000);
	    Actions action = new Actions(driver);
		action.moveToElement(commentBox).doubleClick(commentBox).build().perform();
		js.executeScript("arguments[0].click();", commentBox);
		try {
		wait.until(waitforElement(enterTextcommentBox));
		}
		catch (Exception e) {
			System.out.println("Again searching for text box to enter Order ID");
			PassOrderIDInCommentBox(ID,Status);
		}
		js.executeScript("arguments[0].click();", enterTextcommentBox);
		enterTextcommentBox.sendKeys(ID);
		ExtentReportManager.getTest().pass("Successfully Entered Order ID in Text Box="+ID);
				Thread.sleep(2000);
				}
		else
		    {
			try {
				wait.until(waitforElement(defectBox));
				}
				catch (Exception e) {
					wait.until(waitforElement(defectBox));
				}
		    wait.until(ExpectedConditions.elementToBeClickable(defectBox));
		    Actions action = new Actions(driver);
			action.moveToElement(defectBox).doubleClick(defectBox).build().perform();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", defectBox);
			Thread.sleep(2000);
			defectBox.sendKeys(ID);
//			try {
//			wait.until(waitforElement(defectNumber));
//			js.executeScript("arguments[0].click();", defectNumber);
//			}
//			catch (Exception e) {
//				wait.until(waitforElement(defectNumber));
//				js.executeScript("arguments[0].click();", defectNumber);
//			}
			ExtentReportManager.getTest().pass("Successfully Entered Defect ID in Text Box="+ID);
		}
	
	}
	
//	public static HashMap<String,String> FailedOrBlockedTestCases(String Step,String status) throws InterruptedException {
//
//		HashMap<String,String> TestStep=new HashMap<String,String>();
//	      int length = passFailDropdownArrow.size();
//	      System.out.println("Size of Elements="+length);
//	      
//		for(int i=1;i<=length;i++)
//		{
//			 String TestStepDataFirst ="//div[@class='row-column grid-column htmlStep-column ']/descendant::p[";
//	         String TestStepDataSecond ="]";
//	         String TestStepNumberFirst ="//div[@class='row-column grid-column orderId-column ']/descendant::div[";
//	         String TestStepNumberSecond ="]";
//			 WebElement TestStepData = driver.findElement(By.xpath(TestStepDataFirst+i+TestStepDataSecond));
//			 WebElement TestStepNumber = driver.findElement(By.xpath(TestStepNumberFirst+i+TestStepNumberSecond));
//			 wait.until(waitforElement(TestStepData));
//			 wait.until(waitforElement(TestStepNumber));
//			 String key=TestStepData.getText();
//			 String Value=TestStepNumber.getText();
//			 TestStep.put(key,Value);
//			 System.out.println("Key="+key+"  "+"Value="+Value);
//		}
//		
//		 String statusArrowFirst ="(//span[@class='trigger-dropDown'])[";
//         String statusArrowSecond ="]";
//         String statusSelectFirst ="(//li[@title='PASS'])[";
//         String statusSelectSecond ="]";
//		 String number=TestStep.get(Step);
//		 System.out.println("Test Step Number="+number);
//		 for(int i =1; i<=length;i++) {
//			String IntegerToString=Integer.toString(i).trim();
//			System.out.println("IntegerToString="+IntegerToString);
//			try {
//			if(number.trim().equalsIgnoreCase(IntegerToString))
//			{
//				System.out.println("defect number is found");
//				String FailedstatusSelectFirst ="(//li[@title='" + status + "'])[";
//				WebElement FailedStatusArrow = driver.findElement(By.xpath(statusArrowFirst+i+statusArrowSecond));
//	            WebElement FailedStatusSelect = driver.findElement(By.xpath(FailedstatusSelectFirst+i+statusSelectSecond));
//	            JavascriptExecutor js = (JavascriptExecutor) driver;
//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", FailedStatusArrow);
//				js.executeScript("arguments[0].click();", FailedStatusArrow);
//				wait.until(waitforElement(FailedStatusSelect));
//				js.executeScript("arguments[0].click();", FailedStatusSelect);
//				Thread.sleep(2000);
//				break;
//			}}
//			catch (Exception e) {
//				e.printStackTrace();
//				System.out.println(e.getMessage());
//			}
//
//            WebElement StatusArrow = driver.findElement(By.xpath(statusArrowFirst+i+statusArrowSecond));
//            WebElement StatusSelect = driver.findElement(By.xpath(statusSelectFirst+i+statusSelectSecond));
//            System.out.println("value="+StatusArrow);
//            System.out.println("value="+StatusSelect);
//				JavascriptExecutor js = (JavascriptExecutor) driver;
//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", StatusArrow);
//				js.executeScript("arguments[0].click();", StatusArrow);
//				wait.until(waitforElement(StatusSelect));
//				js.executeScript("arguments[0].click();", StatusSelect);
//				try {
//				wait.until(ExpectedConditions.stalenessOf(StatusSelect));
//				}
//					catch (Exception e) {
//						Thread.sleep(3000);
//						System.out.println("Sleep is executed for 3 seconds");
//					}
//				}
//		return TestStep;
//	}
	
public void FailedOrBlockedTestCases(String status,String stepNumber,String continueExecution) throws InterruptedException {
		
		int length = passFailDropdownArrow.size();
		System.out.println("Size of the Elements are=" + length);
		ExtentReportManager.getTest().info("Size of the Elements are=" + length);
		 String statusArrowFirst ="(//span[@class='trigger-dropDown'])[";
         String statusArrowSecond ="]";
         String statusSelectFirst ="(//li[@title='PASS'])[";
         String statusSelectSecond ="]";
		 System.out.println("Test Step Number="+stepNumber);
		 for(int i =1; i<=length;i++) {
			String IntegerToString=Integer.toString(i).trim();
			System.out.println("IntegerToString="+IntegerToString);
			ExtentReportManager.getTest().info("IntegerToString="+IntegerToString);
			if(IntegerToString.equalsIgnoreCase(stepNumber))
			{
				ExtentReportManager.getTest().info("Order got failed at this step");
				System.out.println("Order got failed at this step");
				String FailedstatusSelectFirst ="(//li[@title='" + status + "'])[";
				WebElement FailedStatusArrow = driver.findElement(By.xpath(statusArrowFirst+i+statusArrowSecond));
	            WebElement FailedStatusSelect = driver.findElement(By.xpath(FailedstatusSelectFirst+i+statusSelectSecond));
	            JavascriptExecutor js = (JavascriptExecutor) driver;
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", FailedStatusArrow);
				js.executeScript("arguments[0].click();", FailedStatusArrow);
				wait.until(waitforElement(FailedStatusSelect));
				js.executeScript("arguments[0].click();", FailedStatusSelect);
				Thread.sleep(2000);
			    if(continueExecution.trim().equalsIgnoreCase("Yes"))
			   {
			    	ExtentReportManager.getTest().info("Execution Continue Status="+continueExecution);
			    continue;
			   }
			    else
			   {
			    ExtentReportManager.getTest().info("Execution Continue Status="+continueExecution);
			    break;
			   }
			 }
            WebElement StatusArrow = driver.findElement(By.xpath(statusArrowFirst+i+statusArrowSecond));
            WebElement StatusSelect = driver.findElement(By.xpath(statusSelectFirst+i+statusSelectSecond));
            System.out.println("value="+StatusArrow);
            System.out.println("value="+StatusSelect);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", StatusArrow);
				js.executeScript("arguments[0].click();", StatusArrow);
				wait.until(waitforElement(StatusSelect));
				js.executeScript("arguments[0].click();", StatusSelect);
				try {
				wait.until(ExpectedConditions.stalenessOf(StatusSelect));
				}
					catch (Exception e) {
						Thread.sleep(3000);
						System.out.println("Sleep is executed for 3 seconds");
					}
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
					ExtentReportManager.getTest().info("inside catch block");
				}
				return flag;
			}

		};
	}
}