package gaia.tests;

import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import gaia.utils.Driver.DriverManager;
import gaia.utils.Reports.ReportManager;

public class BaseTest {
    
    @BeforeClass
    public void setup() throws Exception {
        // Initialize reports
        ReportManager.initializeReports();
        
        // Initialize drivers
        DriverManager.initializeDrivers();
    }
    
    @AfterClass
    public void tearDown() throws Exception {
        // Flush reports
        ReportManager.flushReports();
        
        // Cleanup drivers
        DriverManager.quitDrivers();
        
        // Kill processes
        DriverManager.killProcess("GAIA");
        DriverManager.stopWinAppDriver();
    }
    
    protected ExtentTest createTest(String testName) {
        return ReportManager.createTest(testName);
    }
}
