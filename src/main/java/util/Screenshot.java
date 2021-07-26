package util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {

	public static String filepath = "C:\\Users\\vishn\\eclipse-workspace\\hackathon1\\TCScreenshots\\";
	public static String screenshot(WebDriver driver,String fileName){ 
		//creating src file instance for storing the screenshot using TakesScreenshot interface
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);  
		File dest = new File(filepath + fileName + ".png");
		try
		{  //copying the  screenshot to desired location using copyFile method
			FileUtils.copyFile(src, dest);
        }catch (IOException e)      
			{
        		System.out.println(e.getMessage());      
			}
		return dest.getPath();
	  }
	

}
