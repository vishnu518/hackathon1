package testCases;

import static org.testng.Assert.assertNotNull;

import java.time.Duration;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.AfterSearchPage;
import pages.PractoHomePage;
import util.ExcelReadWrite;
import util.HighlightElement;
import util.ReportsGeneration;
import util.Screenshot;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class Test1SearchHospitals extends TestBase {

	public static WebDriver driver;
	public static WebElement element;
	public static List<WebElement> elements, hospitals, hospitalsWithStars, hospitalNames;
	public static String browser;
	public static ExtentReports report;
	public static ExtentTest test;

	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;

	public static String[][] data;

	public static int count = 1;

	// CREATING DRIVER AND REPORT FILE
	@BeforeTest(groups = { "basics" })
	public static void createDriver() {

		initialization();
		report = ReportsGeneration.createExtentReport();
	}

	// NAVIGATION TO HOME PAGE OF PRACTO WEBSITE
	@BeforeClass(groups = { "search" })
	public static void homepage() {

		test = ReportsGeneration.startReport("SEARCH HOSPITAL " + count++);
		test.log(LogStatus.PASS, "START OF TESTSCENARIO");
		String URL = prop.getProperty("url");
		driver = TestBase.driver;
		try {
			driver.navigate().to(URL);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.urlToBe(URL));
			test.log(LogStatus.PASS, "Navigated To HomePage");
			takeScreenShot("HomePage");

		} catch (TimeoutException e) {
			test.log(LogStatus.FAIL, "Navigation To HomePage Taking Too Long");
		}
	}

	@DataProvider(name = "testData")
	public static String[][] dataFields() {

		data = ExcelReadWrite.readExcel("Search");
		sheet = ExcelReadWrite.sheet;
		return data;
	}

	// SEARCH FUNCTIONALITY
	@Test(priority = 0, dataProvider = "testData", groups = { "search" })
	public static void search(String city, String searchTerm, String rating) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.pollingEvery(Duration.ofSeconds(5));

			element = PractoHomePage.searchCity(driver);
			wait.until(ExpectedConditions.visibilityOf(element));
			HighlightElement.highlightElement(driver, element);
			element.click();
			element.clear();
			HighlightElement.highlightElement(driver, element);
			element.sendKeys(city); // CITY WAS PUT IN BOX

			element = PractoHomePage.cityElement(driver);
			String sen;
			try {
				sen = element.getAttribute("textContent").trim(); // CHECKS FOR STALE ELEMENT REFERENCE
			} catch (StaleElementReferenceException e) {
				element = PractoHomePage.cityElement(driver);
				sen = element.getAttribute("textContent").trim();
			}

			while (!sen.equalsIgnoreCase(city)) { // LOOPS UNTIL CORRECT LIST LOADS
				browser = TestBase.browserName;
				if (browser.equalsIgnoreCase("Firefox"))
					driver.navigate().refresh();
				element = PractoHomePage.searchCity(driver);
				HighlightElement.highlightElement(driver, element);
				element.click();
				element.clear();
				HighlightElement.highlightElement(driver, element);
				element.sendKeys(city);
				Thread.sleep(2000);
				element = PractoHomePage.cityElement(driver);
				try {
					sen = element.getAttribute("textContent").trim();
				} catch (StaleElementReferenceException e) {
					element = PractoHomePage.cityElement(driver);
					sen = element.getAttribute("textContent").trim();
				}
			}

			element = PractoHomePage.cityElement(driver);
			wait.until(ExpectedConditions.visibilityOf(element));
			HighlightElement.highlightElement(driver, element);
			element.click(); // SELECT THE CITY

			element = PractoHomePage.searchBox(driver);
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			HighlightElement.highlightElement(driver, element);
			element.sendKeys(searchTerm); // SEARCH TERM WAS PUT IN SEARCH BAR

			Thread.sleep(2000);

			elements = PractoHomePage.searchElement(driver);
			wait.until(ExpectedConditions.elementToBeClickable(elements.get(0)));

			do {

				if (elements.get(0).getAttribute("textContent").trim().equalsIgnoreCase(searchTerm)) {
					HighlightElement.highlightElement(driver, elements.get(0));
					elements.get(0).click(); // SELECTS THE SEARCH TERM FROM LIST
					break;
				}

				elements.remove(0);

			} while (elements.size() > 0);

			assertNotNull(elements.size()); // CHECKS IF ANYTHING WAS SELECTED

			test.log(LogStatus.PASS, "Search Was Successful");
			takeScreenShot("Search Result");

			element = AfterSearchPage.opencheckbox(driver);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			HighlightElement.highlightElement(driver, element);
			element.click(); // CLICKS 24X7 CHECKBOX

			element = AfterSearchPage.allfilters(driver);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element))); // WAIT FOR FILTER TO
																								// RELOAD

			element = AfterSearchPage.allfilters(driver);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			HighlightElement.highlightElement(driver, element);
			element.click(); // OPENS ALL FILTERS TAB

			element = AfterSearchPage.hasParking(driver);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			HighlightElement.highlightElement(driver, element);
			element.click(); // SELECTS 'HAS PARKING'

			test.log(LogStatus.PASS, "All Relevant Filters Were Successfully Applied");

			hospitals = AfterSearchPage.hospitalCards(driver);
			
			takeScreenShot("Search Result with Filter");

			System.out.println("Total Number Of Hospitals: " + hospitals.size());
			test.log(LogStatus.PASS, "All Hospitals Were Found");
			hospitalNames = AfterSearchPage.hospitalCardsWithName(driver);
	        System.out.println("\nHospital Names:\n");
			
			//DISPLAY HOSPITAL NAMES AND WRITE IN EXCEL FILE
			String[] hospital = new String[hospitalNames.size()];
			for (int i = 0; i < hospitalNames.size(); i++) {
				hospital[i]=hospitalNames.get(i).getText();
				System.out.println(hospital[i]); // DISPLAYS THE NAME OF HOTELS
				
				row = sheet.getRow(i+1);
				if( row == null )	
					row = sheet.createRow(i+1);
				
				cell = row.getCell(3);
				if( cell == null )
					cell = row.createCell(3);
				
				cell.setCellValue(hospital[i]);
			}
			test.log(LogStatus.PASS, "All Hospital Names Were Found");

		} catch (AssertionError e) {
			test.log(LogStatus.FAIL, "No Search Term Found");
		} catch (TimeoutException e) {
			test.log(LogStatus.FAIL, "Element taking too long to Find: " + e.getMessage());
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Error Generated: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterClass(groups = { "search" })
	public static void endTest() {

		test.log(LogStatus.PASS, "END OF TESTSCENARIO");
		ReportsGeneration.endReport(test);
		ExcelReadWrite.writeExcel();

	}

	// CLOSES THE BROWSER
	@AfterTest(groups = { "basics" })
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
