package methods;

import java.io.FileInputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import driver.DriverScript;

public class Datatable extends DriverScript{
	/**********************************
	 * Method NAme		: getRowCount()
	 * 
	 * ***************************
	 */
	public int getRowCount(String strFilePath, String strSheetName)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		int rowCount = 0;
		try {
			fin = new FileInputStream(strFilePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(strSheetName);
			
			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+strSheetName+"' doesnot exist", test, "No");
				return -1;
			}
			
			rowCount = sh.getPhysicalNumberOfRows();
			return rowCount-1;
		}catch(Exception e)
		{
			reports.writeResult(null, "Fail", "Exception while executing 'getRowCount()' method. "+e.getMessage(), test, "No");
			return -1;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Fail", "Exception while executing 'getRowCount()' method. "+e.getMessage(), test, "No");
				return -1;
			}
		}
	}
	
	
	
	/**********************************
	 * Method NAme		: getCellData()
	 * 
	 * ***************************
	 */
	public String getCellData(String strFilePath, String strSheetName, String strColName, int rowNum)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		String strData = null;
		int colNum = 0;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			fin = new FileInputStream(strFilePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(strSheetName);
			
			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+strSheetName+"' doesnot exist", test, "No");
				return null;
			}
			
			//find column number using column name
			row = sh.getRow(0);
			for(int c=0; c<row.getLastCellNum(); c++)
			{
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(strColName))
				{
					colNum = c;
					break;
				}
			}
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			if(row.getCell(colNum)==null) {
				cell = row.createCell(colNum);
			}
			
			//Format & Read the cell value 
			if(cell==null||cell.getCellTypeEnum()==CellType.BLANK)
			{
				strData = "";
			}
			else if(cell.getCellTypeEnum()==CellType.BOOLEAN) {
				strData = String.valueOf(cell.getBooleanCellValue());
			}
			else if(cell.getCellTypeEnum()==CellType.STRING) {
				strData = cell.getStringCellValue();
			}
			else if(cell.getCellTypeEnum()==CellType.NUMERIC) {
				//Find that the cell value is a date?
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					double dt = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(dt));
					
					//Prefix zero if day is <10
					if(cal.get(Calendar.DAY_OF_MONTH)<10)
					{
						sDay = "0"+Calendar.DAY_OF_MONTH;
					}else {
						sDay = String.valueOf(Calendar.DAY_OF_MONTH);
					}
					
					//Prefix zero if month is <10
					if((cal.get(Calendar.MONTH)+1)<10)
					{
						sMonth = "0"+(cal.get(Calendar.MONTH)+1);
					}else {
						sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
					}
					
					sYear = String.valueOf(cal.get(Calendar.YEAR));
					
					strData = sDay+"/"+sMonth+"/"+sYear;
				}else {
					strData = String.valueOf(cell.getNumericCellValue());
				}
			}
			
			return strData;
		}catch(Exception e)
		{
			reports.writeResult(null, "Fail", "Exception while executing 'getCellData()' method. "+e.getMessage(), test, "No");
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Fail", "Exception while executing 'getCellData()' method. "+e.getMessage(), test , "No");
				return null;
			}
		}
	}
}
