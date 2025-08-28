package gaia.tests.Global;

import com.aventstack.extentreports.ExtentTest;
import gaia.utils.Driver.DriverManager;
import gaia.utils.Reports.ReportManager;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class BaseTest {
    @BeforeSuite
    public void globalSetup() throws Exception {
        ReportManager.initializeReports();
        DriverManager.initializeDrivers();
    }

    @AfterSuite
    public void globalTeardown() throws Exception {
        ReportManager.flushReports();
        DriverManager.quitDrivers();
        DriverManager.killProcess("GAIA");
        DriverManager.stopWinAppDriver();
    }

    protected ExtentTest createTest(String testName) {
        return ReportManager.createTest(testName);
    }

}
