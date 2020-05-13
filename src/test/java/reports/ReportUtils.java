package reports;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import driver.DriverScript;

public class ReportUtils extends DriverScript{
	public ExtentReports startReport(String strFileName, String scenarioName)
	{
		File resultDir = null;
		File screenshotDir = null;
		String strResDir = null;
		try {
			strResDir = System.getProperty("user.dir")+"\\Results";
			strResLocation = strResDir+"\\"+scenarioName;
			strScnShotLocation = strResLocation+"\\screenshot";
			
			//Create result dir with respect to scenario
			resultDir = new File(strResLocation);
			if(!resultDir.exists()) {
				resultDir.mkdir();
			}
			
			//create screenshot dir under the specific scenario
			screenshotDir = new File(strScnShotLocation);
			if(!screenshotDir.exists()) {
				screenshotDir.mkdir();
			}
			
			extent = new ExtentReports(strResLocation+"\\"+strFileName+"_"+appInd.getDataTime("ddMMYYYY_hhmmss")+".html", true);
			extent.addSystemInfo("Host Name", System.getProperty("os.name"));
			extent.addSystemInfo("Environment", System.getProperty("os.version"));
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
			
			return extent;
		}catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
		finally {
			resultDir = null;
			screenshotDir = null;
			strResDir = null;
		}
	}
	
	
	public void endTest(ExtentTest test)
	{
		try {
			extent.endTest(test);
			extent.flush();
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	
	public String captureScreenshot(WebDriver oDriver)
	{
		File objFile = null;
		String strDestination = null;
		try {
			strDestination = strScnShotLocation+"\\screenShots_"+appInd.getDataTime("ddMMYYYY_hhmmss")+".png";
			objFile = ((TakesScreenshot)oDriver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(objFile, new File(strDestination));
			return strDestination;
		}catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
		finally {
			objFile = null;
		}
	}
	
	
	public void writeResult(WebDriver odDriver, String strStatus, String strDescription, ExtentTest test, String captureScreenshot)
	{
		try {
			if(captureScreenshot.equalsIgnoreCase("Yes"))
			{
				switch(strStatus.toLowerCase()) {
					case "pass":
						test.log(LogStatus.PASS, strDescription+": "+
								test.addScreenCapture(reports.captureScreenshot(odDriver)));
						break;
					case "fail":
						test.log(LogStatus.FAIL, strDescription+": "+
								test.addScreenCapture(reports.captureScreenshot(odDriver)));
						break;
					case "exception":
						test.log(LogStatus.FATAL, strDescription+": "+
								test.addScreenCapture(reports.captureScreenshot(odDriver)));
						break;
					case "warning":
						test.log(LogStatus.WARNING, strDescription+": "+
								test.addScreenCapture(reports.captureScreenshot(odDriver)));
						break;
					default:
						System.out.println("Invalid result status '"+strStatus+"'");
				}
				
			}else {
				switch(strStatus.toLowerCase()) {
				case "pass":
					test.log(LogStatus.PASS, strDescription);
					break;
				case "fail":
					test.log(LogStatus.FAIL, strDescription);
					break;
				case "exception":
					test.log(LogStatus.FATAL, strDescription);
					break;
				case "warning":
					test.log(LogStatus.WARNING, strDescription);
					break;
				default:
					System.out.println("Invalid result status '"+strStatus+"'");
			}
			}
		}catch(Exception e)
		{
			System.out.println("Exception while executing 'writeResult()' method. "+e.getMessage());
		}
	}
}
