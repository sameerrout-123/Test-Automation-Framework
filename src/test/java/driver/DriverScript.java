package driver;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import methods.AppDependentMethods;
import methods.AppIndependentMethods;
import methods.Datatable;
import methods.TaskModuleMethods;
import methods.UserModuleMethods;
import reports.ReportUtils;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static Datatable datatable = null;
	public static UserModuleMethods userMethods = null;
	public static TaskModuleMethods taskMethods = null;
	public static ReportUtils reports = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String strResLocation = null;
	public static String strScnShotLocation = null;
	public static String strController = null;
	public static String strModule = null;
	
	@BeforeSuite
	public void preRequisite()
	{
		try {
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			datatable = new Datatable();
			userMethods = new UserModuleMethods();
			taskMethods = new TaskModuleMethods();
			reports = new ReportUtils();
			strController = System.getProperty("user.dir")+"\\Controller\\ExecutionController.xlsx";
		}catch(Exception e)
		{
			System.out.println("Exception while executing 'preRequisite()' method. "+e.getMessage());
		}
	}
	
	
	@Test
	public void executeTest()
	{
		String strRun = null;
		Class cls = null;
		Object obj = null;
		Method M = null;
		try {
			int pRows = datatable.getRowCount(strController, "Project");
			for(int i=0; i<pRows; i++)
			{
				strRun = datatable.getCellData(strController, "Project", "ExecuteProject", i+1);
				if(strRun.equalsIgnoreCase("Yes"))
				{
					String strProject = datatable.getCellData(strController, "Project", "ProjectName", i+1);
					int mRows = datatable.getRowCount(strController, strProject);
					
					for(int j=0; j<mRows; j++)
					{
						strRun = datatable.getCellData(strController, strProject, "ExecuteModule", j+1);
						if(strRun.equalsIgnoreCase("Yes"))
						{
							strModule = datatable.getCellData(strController, strProject, "ModuleName", j+1);
							int tcRows = datatable.getRowCount(strController, strModule);
							for(int k=0; k<tcRows; k++)
							{
								strRun = datatable.getCellData(strController, strModule, "ExecuteTest", k+1);
								if(strRun.equalsIgnoreCase("Yes"))
								{
									String strTCID = datatable.getCellData(strController, strModule, "TestCaseID", k+1);
									String strTestScript = datatable.getCellData(strController, strModule, "ScriptName", k+1);
									String strClassName = datatable.getCellData(strController, strModule, "ClassName", k+1);
									cls = Class.forName(strClassName);
									obj = cls.newInstance();
									M = obj.getClass().getMethod(strTestScript);
									M.invoke(obj);
								}
							}
						}
					}
					
				}
			}
		}catch(Exception e)
		{
			System.out.println("Exception while executing 'executeTest()' method. "+e.getMessage());
		}
		finally {
			cls = null;
			obj = null;
			M = null;
		}
	}
	
}
