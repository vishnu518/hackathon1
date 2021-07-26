package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.PageFactory;

import base.TestBase;



public class PractoHomePage extends TestBase {
	
	public static WebElement element;
	public static List<WebElement> elements;
	public PractoHomePage(WebDriver driver) {
		PractoHomePage.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public static WebElement searchCity(WebDriver driver) {
		//Extracting the LOCATION element
		element = driver.findElement(By.xpath("//input[ @data-qa-id = 'omni-searchbox-locality' ]"));
		return element;
	}
	public static WebElement cityElement(WebDriver driver) {

		//When current city is same as LOCATION
		element = driver.findElement(By.xpath("//div[ @data-qa-id = 'omni-suggestion-main' ]"));
		return element;
	}

	public static WebElement searchBox(WebDriver driver) {
		//Extracting the TYPE element
		element = driver.findElement(By.xpath("//input[ @data-qa-id = 'omni-searchbox-keyword' ]"));
		return element;
	}

	public static List<WebElement> searchElement(WebDriver driver) {
		//When current type is same as LOCATION
		elements = driver.findElements(By.xpath("//div[ @data-qa-id = 'omni-suggestion-main' ]"));
		return elements;
	}

	public static WebElement forProviders(WebDriver driver) {
		//Extracting the FOR PROVIDERS element
		element = driver.findElement(By.xpath("//span[@class='u-d-item up-triangle'][text()='For Providers']"));
		return element;
	}

	public static WebElement corporateWellness(WebDriver driver) {
		//Extracting the CORPORATE WELLNESS element
		element = driver.findElement(By.xpath("//a[@class='nav-interact'][text()='Corporate wellness']"));
		return element;
	}

	public static WebElement diagnostics(WebDriver driver) {
		//Extracting the DIAGNOSTICS element
		element = driver.findElement(By.xpath("//div[@class='product-tab__title'][text()='Diagnostics']"));
		return element;
	}

}
