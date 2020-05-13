package methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import driver.DriverScript;

public class AppDependentMethods extends DriverScript{
	/*******************************
	 * Method Name	: navigateURL()
	 * 
	 * 
	 * ****************************
	 */
	public boolean navigateURL(WebDriver oDriver)
	{
		try {
			test = extent.startTest("navigateURL");
			oDriver.navigate().to("http://localhost/login.do");
			Thread.sleep(2000);
			
			if(appInd.compareValues(oDriver, oDriver.getTitle(), "actiTIME - Login")) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'navigateURL()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
	}
	
	
	/*******************************
	 * Method Name	: loginToApp()
	 * 
	 * 
	 * ****************************
	 */
	public boolean loginToApp(WebDriver oDriver)
	{
		WebElement oEle = null;
		String strStatus = null;
		try {
			test = extent.startTest("loginToApp");
			strStatus+=appInd.setObject(oDriver, By.id("username"), "Sameer");
			strStatus+=appInd.setObject(oDriver, By.name("pwd"), "Rout");
			strStatus+=appInd.clickObject(oDriver, By.tagName("a"));
			Thread.sleep(2000);
			
			//Handle Shortcut window
			if(appInd.verifyOptionalElement(oDriver, By.xpath("//div[@style='display: block;']")))
			{
				strStatus+=appInd.clickObject(oDriver, By.id("gettingStartedShortcutsMenuCloseId"));
			}
			
			strStatus+=appInd.verifyText(oDriver, By.xpath("//td[@class='pagetitle']"), "Text", "Enter Time-Track");
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to login to the application", test, "Yes");
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "Login is successful", test, "Yes");
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'loginToApp()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
		finally {
			oEle = null;
		}
	}
	
	
	/*******************************
	 * Method Name	: LogoutFromApp()
	 * 
	 * ****************************
	 */
	public boolean LogoutFromApp(WebDriver oDriver)
	{
		String strStatus = null;
		try {
			test = extent.startTest("LogoutFromApp");
			strStatus+=appInd.clickObject(oDriver, By.xpath("//a[@id='logoutLink']"));
			Thread.sleep(2000);
			
			
			//Verify logout is successful
			strStatus+=appInd.verifyElementPresent(oDriver, By.xpath("//img[contains(@src,'/timer.png')]"));
			
			if(strStatus.contains("false"))
			{
				reports.writeResult(oDriver, "Fail", "Failed to logout from the application", test, "Yes");
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "Logout is successful", test, "No");
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'LogoutFromApp()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
	}
}
