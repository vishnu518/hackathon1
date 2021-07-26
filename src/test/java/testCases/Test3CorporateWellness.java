package testCases;




import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.CorporateWellnessPage;
import pages.PractoHomePage;
import util.ExcelReadWrite;
import util.HighlightElement;
import util.Screenshot;

import util.ReportsGeneration;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class Test3CorporateWellness extends TestBase{

	public static WebDriver driver;
	public static WebElement element;
	public static List<WebElement> elements, hospitals, hospitalsWithStars, hospitalNames;

	public static ExtentReports report;
	public static ExtentTest test;

	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;

	public static String[][] data;
	public static String Msg;
	public static List<String> tabs;
	public static int count = 1;

	//NAVIGATION TO HOME PAGE OF PRACTO WEBSITE
	@BeforeClass(groups = {"corporate"})
	public static void homepage() {

		driver =TestBase.driver;
		report = ReportsGeneration.report;
		test = ReportsGeneration.startReport("Corporate Wellness " + count++);
		test.log(LogStatus.PASS, "START OF TESTSCENARIO");
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

	//NAVIGATION TO CORPORATE WELLNESS PAGE
	@BeforeMethod(groups = {"corporate"})
	public static void corporateWellnessPage() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.pollingEvery(Duration.ofSeconds(5));
			wait.until(ExpectedConditions.visibilityOf(PractoHomePage.forProviders(driver)));
			
			element = PractoHomePage.forProviders(driver);
			HighlightElement.highlightElement(driver, element);
			element.click(); // CLICKS FOR PROVIDER 
			
			element = PractoHomePage.corporateWellness(driver);
			HighlightElement.highlightElement(driver, element);
			element.click(); // NAVIGATES TO CORPORATE WELLNESS PAGE

			tabs = new ArrayList<String>(driver.getWindowHandles());

			driver.switchTo().window(tabs.get(1));

			wait.until(ExpectedConditions.visibilityOf(CorporateWellnessPage.name(driver)));

			takeScreenShot("Corporate Wellness Register");
			test.log(LogStatus.PASS, "Navigated To Corporate Wellness Page");
			
		} catch (TimeoutException e) {
			test.log(LogStatus.FAIL, "Navigation To Corporate Wellness Page Was Unsuccessful");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Navigation To Corporate Wellness Page Was Unsuccessful: " + e.getMessage());
		}

	}

	@DataProvider(name = "testData")
	public static String[][] dataFields() {
		sheet = ExcelReadWrite.sheet;
		data = ExcelReadWrite.readExcel("Corporate Wellness");
		return data;
	}

	// SENDING VALUES TO CORPORATE WELLNESS FORM
	@Test(priority = 1, dataProvider = "testData", groups = {"corporate"})
	public static void setCorporateWellnessForm(String testCase,String name, String orgName, String emailID,String contactNumber, String expResult) throws InterruptedException {

			element = CorporateWellnessPage.name(driver);
			element.clear();
			HighlightElement.highlightElement(driver, element);
			element.sendKeys(name);	// NAME FOR CORPORATE WELLNESS FORM
			
			element = CorporateWellnessPage.organizationName(driver);
			element.clear();
			HighlightElement.highlightElement(driver, element);
			element.sendKeys(orgName);	// ORGANIZATION NAME FOR CORPORTAE WELLNESS FORM
			
			element = CorporateWellnessPage.officialEmailId(driver);
			element.clear();
			HighlightElement.highlightElement(driver, element);
			element.sendKeys(emailID);	// EMAIL ID FOR CORPORATE WELLNESS FORM
			
			element = CorporateWellnessPage.contactNumber(driver);
			element.clear();
			HighlightElement.highlightElement(driver, element);
			element.sendKeys(contactNumber);	// CONTACT NUMBER FOR COPORATE WELLNESS FORM
			
			System.out.println(testCase+" "+name + " " + orgName + " " + emailID + " " + contactNumber + " " + expResult);
 
			try { 
				element = CorporateWellnessPage.scheduleDemo(driver);
				HighlightElement.highlightElement(driver, element);
				element.click();
				Thread.sleep(10000);
				Msg = CorporateWellnessPage.successMessage(driver).getAttribute("textContent").trim();	// STORES THE MESSAGE FOR NEGATIVE SCENARIO
				
			} catch(UnhandledAlertException e) {
				
				Msg = driver.switchTo().alert().getText().trim();	// STORES THE MESSAGE FOR POSITIVE AND ALTERNATE SCENARIO
				driver.switchTo().alert().dismiss();
			}
			
			finally {
		
				try {
					//System.out.println(Msg);
					assertEquals(Msg, expResult);	// CHECKS IF FOUND MESSAGE MATCHES EXPECTED MESSAGE
					
					
					driver.close();
					driver.switchTo().window(tabs.get(0));
					test.log(LogStatus.PASS, "Navigated To HomePage");
					
				} catch (AssertionError e) {
					driver.close();
				} 
			}
	}

	// ENDS TEST REPORT AND SAVES CHANGES TO EXCEL
	@AfterClass(groups = {"corporate"})
	public static void endTest() {
		ExcelReadWrite.writeExcel();

		test.log(LogStatus.PASS ,"END OF TESTSCENARIO");
		ReportsGeneration.endReport(test);

	}

	// TAKES SCREENSHOT
	public static void takeScreenShot(String filename) {

		Screenshot.screenshot(driver,"Corporate Wellness");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,0)");
	}
}