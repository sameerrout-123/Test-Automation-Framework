package testScripts;

import org.openqa.selenium.WebDriver;
import driver.DriverScript;

public class UserModuleScripts extends DriverScript{
	/************************
	 * Test Script		: TS_LoginLogout()
	 * Test case ID		: User_101
	 * Author			:
	 * 
	 * ***************************
	 */
	public boolean TS_LoginLogout()
	{
		WebDriver oBrowser = null;
		String strStatus = null;
		try {
			extent = reports.startReport("LoginLogout", "User_101");
			oBrowser = appInd.launchBrowser("Chrome");
			strStatus+=appDep.navigateURL(oBrowser);
			strStatus+=appDep.loginToApp(oBrowser);
			strStatus+=appDep.LogoutFromApp(oBrowser);
			strStatus+=appInd.closeBrowser(oBrowser);
			
			if(strStatus.contains("false")) {
				System.out.println("The test script 'TS_LoginLogout()' failed");
				return false;
			}else {
				System.out.println("The test script 'TS_LoginLogout()' Passed");
				return true;
			}
		}catch(Exception e)
		{
			System.out.println("Exception while executing 'TS_LoginLogout()' script");
			return false;
		}
		finally {
			reports.endTest(test);
			oBrowser = null;
		}
	}
	
	
	/************************
	 * Test Script		: TS_CreateDeleteUser()
	 * Test case ID		: TC_102
	 * Author			:
	 * 
	 * ***************************
	 */
	public boolean TS_CreateDeleteUser()
	{
		WebDriver oBrowser = null;
		String strStatus = null;
		try {
			extent = reports.startReport("CreateDeleteUser", "User_102");
			oBrowser = appInd.launchBrowser("Chrome");
			strStatus+=appDep.navigateURL(oBrowser);
			strStatus+=appDep.loginToApp(oBrowser);
			strStatus+=userMethods.createUser(oBrowser);
			strStatus+=userMethods.deleteUser(oBrowser, "Last, First");
			strStatus+=appDep.LogoutFromApp(oBrowser);
			strStatus+=appInd.closeBrowser(oBrowser);
			
			if(strStatus.contains("false")) {
				System.out.println("The test script 'TS_CreateDeleteUser()' failed");
				return false;
			}else {
				System.out.println("The test script 'TS_CreateDeleteUser()' Passed");
				return true;
			}
		}catch(Exception e)
		{
			System.out.println("Exception while executing 'TS_CreateDeleteUser()' script");
			return false;
		}finally {
			reports.endTest(test);
			oBrowser = null;
		}
	}
}
