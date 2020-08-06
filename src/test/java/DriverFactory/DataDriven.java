package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import CommonFunLibrary.ERP_Functions;
import Utilities.ExcelFileUtil;

public class DataDriven {
	WebDriver driver;
	String inputpath="D:\\LIVE_PROJECT\\ERP_StockAccounting\\TestInput\\TestData.xlsx";
	String outputpath="D:\\LIVE_PROJECT\\ERP_StockAccounting\\TestOutput\\DataDriven.xlsx";
	@BeforeTest
	public void setUp()throws Throwable
	{
		String launch=ERP_Functions.verifyUrl("http://projects.qedgetech.com/webapp/login.php");
		Reporter.log(launch,true);
		String loginresults=ERP_Functions.verifyLogin("admin","master");
		Reporter.log(loginresults,true);
	}
	@Test
	public void supplier()throws Throwable
	{
		//access excel methods
		ExcelFileUtil xl=new ExcelFileUtil(inputpath);
		//count no of rows in a sheet
		int rc=xl.rowCount("Supplier");
		Reporter.log("no of rows are ::"+rc,true);
		for(int i=1;i<=rc;i++)
		{
			//read all cell from supplier sheet
			String sname=xl.getCellData("Supplier", i, 0);
			String address=xl.getCellData("Supplier", i, 1);
			String city=xl.getCellData("Supplier", i, 2);
			String country=xl.getCellData("Supplier", i, 3);
			String cperson=xl.getCellData("Supplier", i, 4);
			String pnumber=xl.getCellData("Supplier", i, 5);
			String mail=xl.getCellData("Supplier", i, 6);
			String mnumber=xl.getCellData("Supplier", i, 7);
			String note=xl.getCellData("Supplier", i, 8);
			String Results=ERP_Functions.verifySupplier(sname, address, city, country, cperson, pnumber, mail, mnumber, note);
			Reporter.log(Results,true);
			xl.setCellData("Supplier", i, 9, Results, outputpath);	
		}	
	}
	@AfterTest
	public void tearDown()
	{
		//driver.close();
		ERP_Functions.driver.close();
	}

}
