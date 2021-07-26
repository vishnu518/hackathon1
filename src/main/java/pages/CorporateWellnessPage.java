package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;


public class CorporateWellnessPage extends TestBase{
	    public WebDriver driver;
		
		public static WebElement name(WebDriver driver)
		{
			//Extracting the NAME element
			WebElement name= driver.findElement(By.id("name"));
			return name;
		}

		public static WebElement organizationName(WebDriver driver)
		{
			//Extracting the ORGANIZATION NAME element
			WebElement organizationName= driver.findElement(By.xpath("//input[@placeholder='Organization Name']"));
			return organizationName;
		}

		public static WebElement officialEmailId(WebDriver driver)
		{
			//Extracting the OFFICE EMAIL ID element
			WebElement officialEmaiId= driver.findElement(By.xpath("//input[@placeholder='Official Email ID']"));
			return officialEmaiId;
		}

	    public static WebElement contactNumber(WebDriver driver)
		{
	    	//Extracting the CONTACT NUMBER element
	    	WebElement contactNumber= driver.findElement(By.xpath("//input[@placeholder='Contact Number']"));
			return contactNumber;
		}

		public static WebElement scheduleDemo(WebDriver driver)
		{
			//Extracting the SCHEDULE DEMO element
			WebElement scheduleDemo= driver.findElement(By.id("button-style"));
			return scheduleDemo;
		}
		public static WebElement successMessage(WebDriver driver)
		{
			//Extracting the MESSAGE element
			WebElement successMessage = driver.findElement(By.xpath("//div[ @id = 'thankyou-section' ]"));
			return successMessage;
		}
	 
		 public CorporateWellnessPage(WebDriver driver) {
			 this.driver = driver;
			 PageFactory.initElements(driver, this);
		 }
}
