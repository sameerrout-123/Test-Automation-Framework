package methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import driver.DriverScript;

public class UserModuleMethods extends DriverScript{
	/*******************************
	 * Method Name	: createUser()
	 * 
	 * ****************************
	 */
	public boolean createUser(WebDriver oDriver)
	{
		String strStatus = null;
		try {
			test = extent.startTest("createUser");
			strStatus+=appInd.clickObject(oDriver, By.xpath("//div[text()='USERS']"));
			Thread.sleep(2000);
			strStatus+=appInd.clickObject(oDriver, By.xpath("//div[text()='Add User']"));
			Thread.sleep(2000);
			
			strStatus+=appInd.setObject(oDriver, By.name("firstName"), "First");
			strStatus+=appInd.setObject(oDriver, By.name("lastName"), "Last");
			strStatus+=appInd.setObject(oDriver, By.name("email"), "first.last@sg.com");
			strStatus+=appInd.setObject(oDriver, By.name("username"), "firstlast");
			strStatus+=appInd.setObject(oDriver, By.name("password"), "Mercury");
			strStatus+=appInd.setObject(oDriver, By.name("passwordCopy"), "Mercury");
			
			strStatus+=appInd.clickObject(oDriver, By.xpath("//span[text()='Create User']"));
			Thread.sleep(2000);
			
			//Verify user is created
			strStatus+=appInd.verifyElementPresent(oDriver, By.xpath("//div[@class='name']/span[text()='Last, First']"));
			if(strStatus.contains("false"))
			{
				reports.writeResult(oDriver, "Fail", "Failed to create the user", test, "Yes");
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "User created successful", test, "No");
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'createUser()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
	}
	
	
	/*******************************
	 * Method Name	: deleteUser()
	 * 
	 * ****************************
	 */
	public boolean deleteUser(WebDriver oDriver, String strUserName)
	{
		String strStatus = null;
		try {
			test = extent.startTest("deleteUser");
			strStatus+=appInd.clickObject(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+strUserName+"'"+"]"));
			Thread.sleep(2000);
			strStatus+=appInd.clickObject(oDriver, By.xpath("//button[contains(text(),'Delete User')]"));
			Thread.sleep(2000);
			oDriver.switchTo().alert().accept();
			Thread.sleep(2000);
			
			
			//Verify user is deleted 
			strStatus+=appInd.verifyElementNotPresent(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+strUserName+"'"+"]"));
			if(strStatus.contains("false"))
			{
				reports.writeResult(oDriver, "Fail", "Failed to delete the user", test, "Yes");
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "User deleted successful", test, "No");
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception while executing 'deleteUser()' method. "+e.getMessage(), test, "Yes");
			return false;
		}
	}
}
