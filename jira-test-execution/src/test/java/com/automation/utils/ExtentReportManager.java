package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

	private static ExtentReports extent;
	private static ExtentTest test;

	private ExtentReportManager() {
	}

	public static ExtentReports getExtentReports() {
		if (extent == null) {
			ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReport.html");
			extent = new ExtentReports();
			extent.attachReporter(spark);
			extent.setSystemInfo("Automation Tester", "Mohammad Tasneef");
		}
		return extent;
	}

	public static ExtentTest createTest(String testName) {
		test = getExtentReports().createTest(testName);
		return test;
	}

	// The page classes log through this, and a page can be used before any test has been
	// created (for example when the driver is started). Create a placeholder rather than
	// throwing a NullPointerException from inside a page object.
	public static ExtentTest getTest() {
		if (test == null) {
			createTest("Setup");
		}
		return test;
	}

	public static void flushReports() {
		if (extent != null) {
			extent.flush();
		}
	}
}
