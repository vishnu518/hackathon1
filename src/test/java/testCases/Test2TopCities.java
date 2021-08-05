package testCases;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.DiagnosticPage;
import util.ExcelReadWrite;
import util.HighlightElement;
import util.ReportsGeneration;
import util.Screenshot;

public class Test2TopCities extends TestBase{
	
	public static WebElement element;
	public static WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest test;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;

	public static int count = 1;

	//NAVIGATION TO HOME PAGE OF PRACTO WEBSITE
	@BeforeClass(groups = {"cities"})
	public static void homepage() {

		driver = TestBase.driver;
		report = ReportsGeneration.report;
		test = ReportsGeneration.startReport("TOP CITIES " + count++);
		test.log(LogStatus.PASS, "START OF TESTSCENARIO");
		ExcelReadWrite.readExcel("Top Cities");
		sheet = ExcelReadWrite.sheet;
		String URL = prop.getProperty("url");
		try {
			driver.navigate().to(URL);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.urlToBe(URL));
			test.log(LogStatus.PASS, "Navigated To HomePage");

		} catch (TimeoutException e) {
			test.log(LogStatus.FAIL, "Navigation To HomePage Taking Too Long");
		}
	}

	// TOP CITY SCENARIO
	@Test(priority=2, groups = {"cities"})
	public static void getCityNames() {
		try {
			element = DiagnosticPage.diagnostics(driver);
			HighlightElement.highlightElement(driver, element);
			element.click();	// NAVIGATES TO DIAGOSTICS PAGE
			test.log(LogStatus.PASS, "Navigated To Diagostic Page");

			List<WebElement> citynames = DiagnosticPage.topCities(driver); // FINDING THE TOP CITES
			test.log(LogStatus.PASS, "Found Top Cities");
			takeScreenShot("Top Cities");
			
			int rowIndex = 1;
			System.out.println("Top Cities :");
			for (WebElement topcities : citynames) {
				
				System.out.println(topcities.getText());	// DISPLAYING THE TOP CITIES
				row = sheet.getRow(rowIndex);
				if (row == null)
					row = sheet.createRow(rowIndex);
				rowIndex++;
				cell = row.getCell(0);
				if (cell == null)

					cell = row.createCell(0);

				cell.setCellValue(topcities.getText());	// STORING TOP CITES IN EXCEL

			}
		} catch (NoSuchElementException e) {
			test.log(LogStatus.FAIL, "Top Cities Could Not Be Found: ");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Top Cities Could Not Be Found: " + e.getMessage());
		}

	}

	// ENDS TEST REPORT AND SAVES CHANGES TO EXCEL
	@AfterClass(groups = {"cities"})
	public static void endTest() {
		ExcelReadWrite.writeExcel();

		test.log(LogStatus.PASS, "END OF TESTSCENARIO");
		ReportsGeneration.endReport(test);
	}
	
	@AfterTest
	public static void closeDriver() {

		ReportsGeneration.flushReport();
		driver.quit();
	}
	
	// TAKES SCREENSHOT
	public static void takeScreenShot(String filename) {

		Screenshot.screenshot(driver, filename);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,0)");
	}
}
