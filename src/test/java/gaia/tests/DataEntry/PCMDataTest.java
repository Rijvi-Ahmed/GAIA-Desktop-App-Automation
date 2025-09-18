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

            // Fill Customer ID, Pump Rate, Pump Time using per-column filler
            pcmDataPage.fillColumnIfEmptyInPcm("Customer ID", PCMData.PCMTestData.CUSTOMER_ID_PREFIX_FILL, test);
            pcmDataPage.fillColumnIfEmptyInPcm("Pump Rate (LPM) / Volume (L)", PCMData.PCMTestData.PCM_PUMP_RATE, test);
            pcmDataPage.fillColumnIfEmptyInPcm("Pump Time (min.)", PCMData.PCMTestData.PCM_PUMP_TIME, test);

            // Select random values for Volume Unit and Time Unit for each row
            pcmDataPage.selectVolumeUnitForAllRows(test);
            test.pass("Selected value of Volume Unit for all rows");
            pcmDataPage.selectTimeUnitForAllRows(test);
            test.pass("Selected value of Time Unit for all rows");

            // Validate table after filling
            pcmDataPage.validateTableFilledonPcm(PCMData.PCMTestData.COLUMNS_TO_CHECK, test);



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

    @Test
    public void runPCMDataTest2() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(PCMData.PCMTestScenarios.TEST2_NAME);

        try {

            // // need this code fo debug only the testcase2
            // pcmDataPage.maximizeWindow();
            // pcmDataPage.clickDataEntry();
            // pcmDataPage.firstLabID();

            // navigate the PCM data tab
            pcmDataPage.navigateToPCMDataTab();
            test.pass("Navigated to 'PCM Data' tab");

            // Vailidate customer ID on PCM data table is equal to Customer ID on Sample table
            pcmDataPage.CustomerIdMatchingBetweenPcmAndSample(test);


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

    @Test
    public void runPCMDataTest3() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(PCMData.PCMTestScenarios.TEST3_NAME);

        try {

            // // need this code fo debug only the testcase2
            // pcmDataPage.maximizeWindow();
            // pcmDataPage.clickDataEntry();
            // pcmDataPage.firstLabID();

            // navigate the PCM data tab
            pcmDataPage.navigateToPCMDataTab();
            test.pass("Navigated to 'PCM Data' tab");

            //Validate that if customer ID is remove from PCM data, then it also removes from Sample table.
            pcmDataPage.clearPcmCustomerIdAndValidateCustomerIdBlankOnSample(test);

            //Again fill customer ID 
            pcmDataPage.fillColumnIfEmptyInPcm("Customer ID", PCMData.PCMTestData.CUSTOMER_ID_PREFIX_FILL, test);


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
