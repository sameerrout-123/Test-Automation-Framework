package methods;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

import driver.DriverScript;

public class AppIndependentMethods extends DriverScript{
	/********************************
	 * Method Name		: getDataTime
	 * 
	 * *******************************
	 */
	public String getDataTime(String dtFormat)
	{
		Date dt = null;
		SimpleDateFormat sdf = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(dtFormat);
			return sdf.format(dt);
		}catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
		finally {
			
		}
	}
	
	/*********************************
	 * Method Name	: clickObject
	 * 
	 * 
	 * ******************************
	 */
	public boolean clickObject(WebDriver oDriver, By objBy)
	{
		WebElement oEle = null;
		try {
			oEle = oDriver.findElement(objBy);
			if(oEle.isDisplayed()) {
				oEle.click();
				reports.writeResult(oDriver, "Pass", "The element '"+String.valueOf(objBy)+"' was clicked successful", test, "No");
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'clickObject()' method. "+e.getMessage(), test, "Yes");
			return false;
		}finally {
			oEle = null;
		}
	}
	
	
	
	/*********************************
	 * Method Name	: setObject
	 * 
	 * 
	 * ******************************
	 */
	public boolean setObject(WebDriver oDriver, By objBy, String strValue)
	{
		WebElement oEle = null;
		try {
			oEle = oDriver.findElement(objBy);
			if(oEle.isDisplayed()) {
				oEle.sendKeys(strValue);
				reports.writeResult(oDriver, "Pass", "The value '"+strValue+"' was entered in the element '"+String.valueOf(objBy)+"' successful", test, "No");
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'setObject()' method. "+e.getMessage(), test, "Yes");
			return false;
		}finally {
			oEle = null;
		}
	}
	
	
	/*********************************
	 * Method Name	: clearAndSetObject
	 * 
	 * 
	 * ******************************
	 */
	public boolean clearAndSetObject(WebDriver oDriver, By objBy, String strValue)
	{
		WebElement oEle = null;
		try {
			oEle = oDriver.findElement(objBy);
			if(oEle.isDisplayed()) {
				oEle.clear();
				oEle.sendKeys(strValue);
				reports.writeResult(oDriver, "Pass", "The value '"+strValue+"' was entered in the element '"+String.valueOf(objBy)+"' successful", test, "No");
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'clearAndSetObject()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
		finally {
			oEle = null;
		}
	}
	
	
	/*********************************
	 * Method Name	: compareValues
	 * 
	 * 
	 * ******************************
	 */
	public boolean compareValues(WebDriver oDriver, String strActual, String strExpected)
	{
		try {
			if(strActual.equalsIgnoreCase(strExpected)) {
				reports.writeResult(oDriver, "Pass", "The actual '"+strActual+"' & Expected '"+strExpected+"' values are matched.", test ,"No");
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Mis-match in both actual '"+strActual+"' & Expected '"+strExpected+"' values.", test, "Yes");
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'compareValues()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
	}
	
	
	/*********************************
	 * Method Name	: verifyText
	 * 
	 * 
	 * ******************************
	 */
	public boolean verifyText(WebDriver oDriver, By objBy, String strObjType, String strExpected)
	{
		WebElement oEle = null;
		String strActual = null;
		Select oSel = null;
		try {
			oEle = oDriver.findElement(objBy);
			if(oEle.isDisplayed())
			{
				switch(strObjType.toLowerCase())
				{
					case "text":
						strActual = oEle.getText();
						break;
					case "value":
						strActual = oEle.getAttribute("value");
						break;
					case "list":
						oSel = new Select(oEle);
						strActual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeResult(oDriver, "Fail", "Invalid object type '"+strObjType+"' was specified. Please correct it.", test, "No");
				}
				
				if(appInd.compareValues(oDriver, strActual, strExpected)) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'verifyText()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
		finally {
			oEle = null;
			oSel = null;
		}
	}
	
	
	
	/*********************************
	 * Method Name	: verifyElementPresent
	 * 
	 * 
	 * ******************************
	 */
	public boolean verifyElementPresent(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size()>0) {
				reports.writeResult(oDriver, "Pass", "The Element '"+String.valueOf(objBy)+"' was present in the DOM", test, "No");
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to find the element '"+String.valueOf(objBy)+"' in the DOM", test, "Yes");
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'verifyElementPresent()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
	}
	
	
	
	/*********************************
	 * Method Name	: verifyElementNotPresent
	 * 
	 * 
	 * ******************************
	 */
	public boolean verifyElementNotPresent(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size()>0) {
				reports.writeResult(oDriver, "Fail", "The element '"+String.valueOf(objBy)+"' still present in the DOM", test, "Yes");
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "The Element '"+String.valueOf(objBy)+"' was removed from the DOM", test, "No");
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'verifyElementPresent()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
	}
	
	
	
	/*********************************
	 * Method Name	: verifyOptionalElement
	 * 
	 * 
	 * ******************************
	 */
	public boolean verifyOptionalElement(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size()>0) {
				reports.writeResult(oDriver, "Warning", "The element '"+String.valueOf(objBy)+"' present in the DOM", test, "No");
				return true;
			}else {
				reports.writeResult(oDriver, "Warning", "The Element '"+String.valueOf(objBy)+"' Not present in the DOM", test, "No");
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'verifyElementPresent()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
	}
	
	
	/*********************************
	 * Method Name	: launchBrowser
	 * 
	 * 
	 * ******************************
	 */
	public WebDriver launchBrowser(String browser)
	{
		WebDriver oDriver = null;
		try {
			test = extent.startTest("launchBrowser");
			switch(browser.toLowerCase())
			{
				case "chrome":
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Library\\drivers\\chromedriver.exe");
					oDriver = new ChromeDriver();
					break;
				case "firefox":
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\Library\\drivers\\geckodriver.exe");
					oDriver = new FirefoxDriver();
					break;
				case "ie":
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\Library\\drivers\\IEDriverServer.exe");
					oDriver = new InternetExplorerDriver();
					break;
				default:
					reports.writeResult(oDriver, "Fail", "Invalid browser name '"+browser+"' was specified", test, "No");		
			}
			
			if(oDriver!=null) {
				reports.writeResult(oDriver, "Pass", "The '"+browser+"' browser launched successful", test, "No");
				oDriver.manage().window().maximize();
				return oDriver;
			}else return null;
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'launchBrowser()' method. "+e.getMessage(), test, "No");
			return null;
		}
	}
	
	
	
	/*********************************
	 * Method Name	: closeBrowser
	 * 
	 * 
	 * ******************************
	 */
	public boolean closeBrowser(WebDriver oDriver)
	{
		try {
			oDriver.close();
			return true;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception while executing 'closeBrowser()' method. "+e.getMessage(), test, "No");
			return false;
		}
	}
}
