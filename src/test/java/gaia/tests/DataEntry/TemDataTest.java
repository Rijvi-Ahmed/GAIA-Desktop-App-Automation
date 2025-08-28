package gaia.tests.DataEntry;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import gaia.utils.TestData.DataEntryTestData.TEMData;

public class TEMDataTest extends DataEntryBaseTest {

    @Test
    public void runTEMDataTest1() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(TEMData.TEMTestScenarios.TEST1_NAME);

        try {

            // need this code fo debug only the testcase2
            // temDataPage.maximizeWindow();
            // temDataPage.clickDataEntry();
            // temDataPage.firstLabID();

            // navigate the sample tab
            temDataPage.navigateToTEMDataTab();
            test.pass("Navigated to 'TEM Data' tab");

        } catch (Exception e) {
            // Capture screenshot
            temDataPage.captureScreenshot("Failure_" + System.currentTimeMillis());
            // Log the error in the report
            test.fail("Test failed: " + e.getMessage());
            // Do not rethrow to keep execution user-friendly
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test 1 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }

}
