package util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HighlightElement {
	public static void highlightElement(WebDriver driver,WebElement element) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
    	jse.executeScript("arguments[0].setAttribute('style','border:2px solid red')", element);Thread.sleep(3000);

    	jse.executeScript("arguments[0].removeAttribute('style','')", element);
	}
}
