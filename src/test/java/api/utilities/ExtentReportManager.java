package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager extends  TestListenerAdapter {
	
	public	 ExtentSparkReporter sparkReporter;
	public   ExtentTest test;
	public 	 ExtentReports extent;
	
	String repName;

	public void onStart(ITestContext testContext) {

		//For Create File Name With Time Stamp

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String repName = "Test-Report-"+timeStamp+".html";

		// To Give Path Of The HTML File
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);
		
		//To Give Title For Project
		sparkReporter.config().setDocumentTitle("RestAsuredAutomationProject");
		sparkReporter.config().setReportName("Pet Store Users API");
		sparkReporter.config().setTheme(Theme.STANDARD); //can also be DARK

		// Set SystemInfo
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Pet Store Users API");
		extent.setSystemInfo("Operating System", "Windows11");
		extent.setSystemInfo("Tester", "Mirgolib");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", "Mirgolib");

	}

	//Create Label For PassTests
	public void onTestSuccess(ITestResult tr) {
		test = extent.createTest(tr.getName());
		test.log(Status.PASS, MarkupHelper.createLabel(tr.getName(),ExtentColor.GREEN));
	}

	//Create Label For FaiTests
	public void onTestFailure(ITestResult tr) {
		test=extent.createTest(tr.getName());
		test.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
	}

	//Create A Label For SkipedTests
	public void onTestSkipped(ITestResult tr) {
		test=extent.createTest(tr.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(),ExtentColor.ORANGE));
	}
	//To End The Extent
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}
}
