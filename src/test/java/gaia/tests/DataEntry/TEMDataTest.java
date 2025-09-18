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
            temDataPage.maximizeWindow();
            temDataPage.clickDataEntry();
            temDataPage.firstLabID();

            // navigate the TEM data tab
            temDataPage.navigateToTEMDataTab();
            test.pass("Navigated to 'TEM Data' tab");

            // Fill Customer ID, Pump Rate, Pump Time using per-column filler
            temDataPage.fillColumnIfEmptyInTem("Customer ID", TEMData.TEMTestData.CUSTOMER_ID_PREFIX_FILL, test);
            temDataPage.fillColumnIfEmptyInTem("Air Volume (L)", TEMData.TEMTestData.AIR_VOLUME, test);
            temDataPage.fillColumnIfEmptyInTem("Wipe Area (cm²)", TEMData.TEMTestData.WIPE_AREA, test);

            // Select random values for Volume Unit and Time Unit for each row
            temDataPage.selectFilterForAllRows(test);
            test.pass("Selected value of Volume Unit for all rows");
            temDataPage.selectFilterTypeForAllRows(test);
            test.pass("Selected value of Time Unit for all rows");
            temDataPage.selectGridOpeningAreaForAllRows(test);
            test.pass("Selected value of Time Unit for all rows");

            // Validate table after filling
            temDataPage.validateTableFilledonTem(TEMData.TEMTestData.COLUMNS_TO_CHECK, test);

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

    // @Test
    // public void runTEMDataTest2() throws Exception {
    //     long startTime = System.currentTimeMillis();
    //     ExtentTest test = createTest(TEMData.TEMTestScenarios.TEST2_NAME);

    //     try {

    //         // need this code fo debug only the testcase2
    //         // temDataPage.maximizeWindow();
    //         // temDataPage.clickDataEntry();
    //         // temDataPage.firstLabID();

    //         // navigate the TEM data tab
    //         temDataPage.navigateToTEMDataTab();
    //         test.pass("Navigated to 'TEM Data' tab");

    //         // Vailidate customer ID on TEM data table is equal to Customer ID on Sample
    //         // table
    //         temDataPage.CustomerIdMatchingBetweenTemAndSample(test);

    //     } catch (Exception e) {
    //         // Capture screenshot
    //         temDataPage.captureScreenshot("Failure_" + System.currentTimeMillis());
    //         // Log the error in the report
    //         test.fail("Test failed: " + e.getMessage());
    //         // Do not rethrow to keep execution user-friendly
    //     }

    //     long endTime = System.currentTimeMillis();
    //     System.out.println("Test 2 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    // }

    // @Test
    // public void runTEMDataTest3() throws Exception {
    //     long startTime = System.currentTimeMillis();
    //     ExtentTest test = createTest(TEMData.TEMTestScenarios.TEST3_NAME);

    //     try {

    //         // need this code fo debug only the testcase2
    //         // temDataPage.maximizeWindow();
    //         // temDataPage.clickDataEntry();
    //         // temDataPage.firstLabID();

    //         // navigate the TEM data tab
    //         temDataPage.navigateToTEMDataTab();
    //         test.pass("Navigated to 'TEM Data' tab");

    //         // Validate that if customer ID is remove from TEM data, then it also removes
    //         // from Sample table.
    //         temDataPage.clearTemCustomerIdAndValidateCustomerIdBlankOnSample(test);

    //         // Again fill customer ID
    //         temDataPage.fillColumnIfEmptyInTem("Customer ID", TEMData.TEMTestData.CUSTOMER_ID_PREFIX_FILL, test);

    //     } catch (Exception e) {
    //         // Capture screenshot
    //         temDataPage.captureScreenshot("Failure_" + System.currentTimeMillis());
    //         // Log the error in the report
    //         test.fail("Test failed: " + e.getMessage());
    //         // Do not rethrow to keep execution user-friendly
    //     }

    //     long endTime = System.currentTimeMillis();
    //     System.out.println("Test 3 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    // }

    // @Test
    // public void runTEMDataTest4() throws Exception {
    //     long startTime = System.currentTimeMillis();
    //     ExtentTest test = createTest(TEMData.TEMTestScenarios.TEST4_NAME);

    //     try {

    //         // need this code fo debug only the testcase2
    //         // temDataPage.maximizeWindow();
    //         // temDataPage.clickDataEntry();
    //         // temDataPage.firstLabID();

    //         // navigate the TEM data tab
    //         temDataPage.navigateToTEMDataTab();
    //         test.pass("Navigated to 'TEM Data' tab");

    //         // Validate that the first row's filter value is applied to all rows
    //         temDataPage.verifyFilterValueAppliedToAllRows("Filter", test);
    //         //for Filter Type
    //         temDataPage.verifyFilterValueAppliedToAllRows("Filter Type", test);
    //         //for Grid Opening Area (mm²)
    //         temDataPage.verifyFilterValueAppliedToAllRows("Grid Opening Area (mm²)", test);

    //     } catch (Exception e) {
    //         // Capture screenshot
    //         temDataPage.captureScreenshot("Failure_" + System.currentTimeMillis());
    //         // Log the error in the report
    //         test.fail("Test failed: " + e.getMessage());
    //         // Do not rethrow to keep execution user-friendly
    //     }

    //     long endTime = System.currentTimeMillis();
    //     System.out.println("Test 4 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    // }

}
