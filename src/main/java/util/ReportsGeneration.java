package util;

import java.io.File;
import java.io.IOException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

/*
 * 	CREATING EXTENT REPORT FILE "ExtentReportResults.html"
 * 
 * 	CREATING NEW TEST REPORTS
 * 
 * 	WRITING CHANGES INTO REPORT FILE "ExtentReportResults.html"
 * 
 */

public class ReportsGeneration {

	private static File file;
	private static String path = System.getProperty("user.dir") + "\\Reports\\ExtentReportResults.html";

	public static ExtentTest test;
	public static ExtentReports report;

	// CREATING THE REPORT FILE AND INITIALISING THE REPORT

	public static ExtentReports createExtentReport() {

		file = new File(path);
		try {

			if (file.createNewFile())
				System.out.println("File Created: " + file.getName());
			else
				System.out.println("File Already Exists");

		} catch (IOException e) {
			e.printStackTrace();
		}

		report = new ExtentReports(path, true);
		report.addSystemInfo("Selenium", "3.141.59").addSystemInfo("TestNG", "6.11").addSystemInfo("OS", "Windows 10")
				.addSystemInfo("Browser", "Chrome").addSystemInfo("User Name", "QEA20QE023_Group9");
		return report;

	}

	// STARTING REPORT

	public static ExtentTest startReport(String reportName) {

		test = report.startTest(reportName);
		return test;
	}

	// ENDING REPORT

	public static void endReport(ExtentTest test) {

		report.endTest(test);
	}

	// WRITING REPORT

	public static void flushReport() {

		report.flush();
	}

}
