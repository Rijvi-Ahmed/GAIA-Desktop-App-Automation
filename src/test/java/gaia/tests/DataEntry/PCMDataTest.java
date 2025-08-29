package gaia.tests.DataEntry;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import gaia.utils.TestData.DataEntryTestData.PCMData;

public class PCMDataTest extends DataEntryBaseTest {

    @Test
    public void runPCMDataTest1() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(PCMData.PCMTestScenarios.TEST1_NAME);

        try {

            // need this code fo debug only the testcase2
            pcmDataPage.maximizeWindow();
            pcmDataPage.clickDataEntry();
            pcmDataPage.firstLabID();

            // navigate the PCM data tab
            pcmDataPage.navigateToPCMDataTab();
            test.pass("Navigated to 'PCM Data' tab");

        } catch (Exception e) {
            // Capture screenshot
            pcmDataPage.captureScreenshot("Failure_" + System.currentTimeMillis());
            // Log the error in the report
            test.fail("Test failed: " + e.getMessage());
            // Do not rethrow to keep execution user-friendly
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test 1 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }
}
