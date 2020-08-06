package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript {
	//path for reading excel file
	String inputpath="D:\\LIVE_PROJECT\\ERP_Maven\\TestInput\\InputSheet.xlsx";
	//path of excel for writing results
	String outputpath="D:\\LIVE_PROJECT\\ERP_Maven\\TestOutput\\hybrid.xlsx";
	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	public void startTest()throws Throwable
	{
		//creating reference for excel util methods
		ExcelFileUtil xl=new ExcelFileUtil(inputpath);
		//iterating all the rows in MasterTestCase sheet
		for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
		{
			if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				//store module names into TCModule
				String TCModule=xl.getCellData("MasterTestCases", i, 1);
				report=new ExtentReports("./ExtentReports/"+TCModule+FunctionLibrary.generateDate());
				//iterate all rows in TCModule sheet
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					//test case starts here
					test=report.startTest(TCModule);
					test.assignAuthor("Prasanna QA");
					test.assignCategory(TCModule);
					//read cell from TCModule
					String Description=xl.getCellData(TCModule, j, 0);
					String Function_Name=xl.getCellData(TCModule, j, 1);
					String Locator_Type=xl.getCellData(TCModule, j, 2);
					String Locator_Value=xl.getCellData(TCModule, j, 3);
					String Test_Data=xl.getCellData(TCModule, j, 4);
					try{
						//call functions
						if(Function_Name.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionLibrary.startBrowser();
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("openApplication"))
						{
							FunctionLibrary.openApplication(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("captureData"))
						{
							FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("captureData"))
						{
							FunctionLibrary.tableValidation(driver, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("stockCategories"))
						{
							FunctionLibrary.stockCategories(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("stockValidation"))
						{
							FunctionLibrary.stockValidation(driver, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						
						//write as pass into TCModule
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						test.log(LogStatus.PASS, Description);
						//write as pass into MasterTestCase sheet
						xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
					}catch(Exception e)
					{
						System.out.println(e.getMessage());
						//write as fail into status column TCModule
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						test.log(LogStatus.FAIL, Description);
						File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screen, new File("./Screens/"+TCModule+FunctionLibrary.generateDate()+".png"));
						//Write as fail into MasterTestCase sheet
						xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);

					}
					report.endTest(test);
					report.flush();
				}
			}
	else
			{
				//write as not executed into status column in master test case sheet  
				xl.setCellData("MasterTestCases", i, 3, "Not executed", outputpath);
			}
		}
	}
}


