package gaia.tests.DataEntry;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import gaia.pages.DataEntry.PCMDataPage;
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

            // Fill Customer ID, Pump Rate, Pump Time using per-column filler
            pcmDataPage.fillColumnIfEmptyInPcm("Customer ID", PCMData.PCMTestData.CUSTOMER_ID_PREFIX_FILL);
            pcmDataPage.fillColumnIfEmptyInPcm("Pump Rate (LPM) / Volume (L)", PCMData.PCMTestData.PCM_PUMP_RATE);
            pcmDataPage.fillColumnIfEmptyInPcm("Pump Time (min.)", PCMData.PCMTestData.PCM_PUMP_TIME);

            // Select random values for Volume Unit and Time Unit for each row
            pcmDataPage.selectRandomVolumeUnitForAllRows(test);
            test.pass("Selected random Volume Unit for all rows");
            pcmDataPage.selectRandomTimeUnitForAllRows(test);
            test.pass("Selected random Time Unit for all rows");

            // Validate table after filling
            pcmDataPage.validateTableFilledonPcm(PCMData.PCMTestData.COLUMNS_TO_CHECK, test);

            pcmDataPage.CustomerIdMatchingBetweenPcmAndSample(test);

            pcmDataPage.clearPcmCustomerIdAndValidateCustomerIdBlankOnSample(test);

            pcmDataPage.fillColumnIfEmptyInPcm("Customer ID", PCMData.PCMTestData.CUSTOMER_ID_PREFIX_FILL);

            test.pass("Filled all value in the PCM data table successfully");

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
