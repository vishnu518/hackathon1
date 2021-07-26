package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;


public class DiagnosticPage extends TestBase {
	
	public DiagnosticPage(WebDriver driver) {
		DiagnosticPage.driver=driver;
		PageFactory.initElements(driver, this);
	}
	public static WebElement element;
	public static WebDriver driver;
	public static WebElement diagnostics(WebDriver driver)
	{
		//Extracting the DIAGNOSTICS element
		element= driver.findElement(By.xpath("//div[@class='product-tab__title'][text()='Diagnostics']"));
		return element;
	}
	public static List<WebElement> topCities(WebDriver driver)
	{
		//Extracting the TOP CITIES elements
		List<WebElement> cities= driver.findElements(By.xpath("//ul/li/div[2]"));
		return cities;
	}

}
