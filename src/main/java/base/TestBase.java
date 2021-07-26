package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public static String browserName;
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java" + "/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** INITIALIZATION OF CHROME AND FIREFOX BROWSERS WITH THE HELP OF CORRESPONDING DRIVERS
	 * FIRST IT INVOKE THE BROWSER
	 * THEN MAXIMIZE THE BROWSER WINDOW AND NAVIGATE TO GIVEN URL
	 * PERFORM REQUIRED TASK
	 * AND CLOSE THE BROWSER
	 **/
	public static void initialization(){
		browserName = prop.getProperty("browser");
		//INVOKING CHORME BROWSER
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\vishn\\eclipse-workspace\\hackathon1\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} 
		//INVOKING FIREFOX BROWSER
		else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\LENOVO\\eclipse-workspace\\FindingHospitals\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();       //MAXIMIZING THE BROWSER WINDOW
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);     //WAIT UNTIL THE WEBPAGE IS GETTING LOADED
		driver.get(prop.getProperty("url"));    
	}
}
