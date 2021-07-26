package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

public class AfterSearchPage extends TestBase{
	public static WebElement element;
	public static List<WebElement> elements;
	public AfterSearchPage(WebDriver driver) {
		DiagnosticPage.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public static WebElement opencheckbox(WebDriver driver) {
		//Extracting the OPEN 24/7 element
		element = driver.findElement(By.xpath("//div[ @data-qa-id = 'Open_24X7_checkbox' ]"));
		return element;
	}

	public static WebElement allfilters(WebDriver driver) {
		//Extracting the ALL FILTERS element
		element = driver.findElement(By.xpath("//span[text()='All Filters']"));
		return element;
	}

	public static WebElement hasParking(WebDriver driver) {
		//Extracting the HAS PARKING element
		element = driver.findElement(By.xpath("//span[ @data-qa-id = 'Has_Parking_label' ]"));
		return element;
	}
	public static List<WebElement> hospitalCards(WebDriver driver) {
		//Extracting the HOSPITAL CARDS elements
		elements = driver.findElements(By.xpath("//div[ @data-qa-id = 'hospital_card' ]"));
		return elements;
	}
	public static List<WebElement> hospitalCardsWithName(WebDriver driver) {
		//Extracting the HOSPITAL NAMES elements
		elements = driver.findElements(By.xpath("//div[ @data-qa-id = 'hospital_card' ]//h2[@data-qa-id = 'hospital_name' ]")); // CONTAINS ALL HOSPITAL NAMES
		return elements;
	}

}
