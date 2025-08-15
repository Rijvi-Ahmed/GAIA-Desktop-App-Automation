package gaia.tests.DataEntry;

import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.Test;
import gaia.utils.TestData.DataEntryTestData;

public class SampleTest extends DataEntryBaseTest {

    @Test
    public void runSampleTest1() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(DataEntryTestData.TestScenarios.TEST1_NAME);

        try {
            test.pass("Using existing GAIA session for all testcase");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // Create samples with all data (inline implementation)
            dataEntryPage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            dataEntryPage.enterSampleCount(DataEntryTestData.SampleTestData.SAMPLE_COUNT);
            test.info("Entered sample quantity: " + DataEntryTestData.SampleTestData.SAMPLE_COUNT);

            dataEntryPage.enterCustomerIdPrefix(DataEntryTestData.SampleTestData.CUSTOMER_ID_PREFIX);
            test.info("Entered Customer ID Prefix: " + DataEntryTestData.SampleTestData.CUSTOMER_ID_PREFIX);

            dataEntryPage.enterCustomerIdSuffix(DataEntryTestData.SampleTestData.CUSTOMER_ID_SUFFIX);
            test.info("Entered Customer ID Suffix: " + DataEntryTestData.SampleTestData.CUSTOMER_ID_SUFFIX);

            dataEntryPage.enterAutoNumber(DataEntryTestData.SampleTestData.AUTO_NUMBER_TEST1);
            test.info("Entered Auto Number: " + DataEntryTestData.SampleTestData.AUTO_NUMBER_TEST1);

            dataEntryPage.enterAutoBaseNumber(DataEntryTestData.SampleTestData.AUTO_BASE_NUMBER);
            test.info("Entered Auto base number: " + DataEntryTestData.SampleTestData.AUTO_BASE_NUMBER);

            dataEntryPage.clickNewSamples();
            test.pass("Clicked 'New Sample(s)'");

            // Verify Lab IDs exist for newly created samples
            int createdSamples = Integer.parseInt(DataEntryTestData.SampleTestData.SAMPLE_COUNT);
            dataEntryPage.verifyLabIdsPresentForLastNSamples(createdSamples, test);

            // Fill missing values after creating samples
            dataEntryPage.fillColumnIfEmpty("Customer ID", DataEntryTestData.SampleTestData.CUSTOMER_ID_PREFIX_FILL);
            dataEntryPage.fillColumnIfEmpty("Description", DataEntryTestData.SampleTestData.DESCRIPTION_PREFIX_FILL);
            dataEntryPage.fillColumnIfEmpty("Lab Notes", DataEntryTestData.SampleTestData.LAB_NOTES_PREFIX_FILL);

            // Validate table after filling
            dataEntryPage.validateTableFilled(DataEntryTestData.SampleTestData.COLUMNS_TO_CHECK, test);

            // Verify Customer ID sequence: prefix + autobase + suffix + '-' + autoNumber,
            // incrementing by 1
            int numSamples = Integer.parseInt(DataEntryTestData.SampleTestData.SAMPLE_COUNT);
            int startingAutoNumber = Integer.parseInt(DataEntryTestData.SampleTestData.AUTO_NUMBER_TEST1);
            dataEntryPage.verifyCustomerIdSequentialWithAutoNumber(
                    DataEntryTestData.SampleTestData.CUSTOMER_ID_PREFIX,
                    DataEntryTestData.SampleTestData.AUTO_BASE_NUMBER,
                    DataEntryTestData.SampleTestData.CUSTOMER_ID_SUFFIX,
                    startingAutoNumber,
                    numSamples,
                    test);

            test.pass("Sample test 1 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            dataEntryPage.captureScreenshot("Failure_" + System.currentTimeMillis());
            // Log the error in the report
            test.fail("Test failed: " + e.getMessage());
            // Do not rethrow to keep execution user-friendly
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test 1 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }

    @Test
    public void runSampleTest2() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(DataEntryTestData.TestScenarios.TEST2_NAME);

        try {

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase2
            // dataEntryPage.maximizeWindow();
            // dataEntryPage.clickDataEntry();
            // dataEntryPage.firstLabID();

            // navigate the sample tab
            dataEntryPage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            // Create samples with auto base only (inline implementation)
            dataEntryPage.clearAllSampleInputs();
            test.pass("Cleared Samples tab inputs (sample count, prefix, suffix, auto number, auto base)");

            dataEntryPage.enterSampleCount(DataEntryTestData.SampleTestData.SAMPLE_COUNT);
            test.pass("Entered sample quantity");

            dataEntryPage.clickNewSamples();
            test.pass("Clicked 'New Sample(s)'");

            dataEntryPage.enterAutoBaseNumber(DataEntryTestData.SampleTestData.AUTO_BASE_NUMBER);
            test.pass("Entered Auto base number");

            dataEntryPage.clickAutoBaseNumber();
            test.pass("Clicked 'Auto base button'");

            // Verify Customer ID cells for the newly created samples equal the Auto Base
            // value entered
            int samplesToVerify = Integer.parseInt(DataEntryTestData.SampleTestData.SAMPLE_COUNT);
            dataEntryPage.verifyCustomerIdMatchesAutoBaseForLastNSamples(
                    DataEntryTestData.SampleTestData.AUTO_BASE_NUMBER,
                    samplesToVerify,
                    test);

            test.pass("Sample test 2 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            dataEntryPage.captureScreenshot("Failure_" + System.currentTimeMillis());
            // Log the error in the report
            test.fail("Test failed: " + e.getMessage());
            // Do not rethrow to keep execution user-friendly
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test 2 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }

    @Test
    public void runSampleTest3() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(DataEntryTestData.TestScenarios.TEST3_NAME);

        try {
            test.pass("Using existing GAIA session");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase3
            // dataEntryPage.maximizeWindow();
            // dataEntryPage.clickDataEntry();
            // dataEntryPage.firstLabID();

            // Navigate samples
            dataEntryPage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            // Create samples with auto base only (inline implementation)
            dataEntryPage.clearAllSampleInputs();
            test.pass("Cleared Samples tab inputs (sample count, prefix, suffix, auto number, auto base)");

            dataEntryPage.enterSampleCount(DataEntryTestData.SampleTestData.SAMPLE_COUNT);
            test.pass("Entered Sample quantity: " + DataEntryTestData.SampleTestData.SAMPLE_COUNT);

            dataEntryPage.clickNewSamples();
            test.pass("Clicked 'New Sample(s)'");

            dataEntryPage.clickAutoBaseNumber();
            test.pass("Clicked 'Auto base button'");

            // Verify blank Auto Base popup with message and dismiss it
            dataEntryPage.verifyAndDismissBlankAutoBasePopup(test);

            test.pass("Sample test 3 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            dataEntryPage.captureScreenshot("Failure_" + System.currentTimeMillis());
            // Log the error in the report
            test.fail("Test failed: " + e.getMessage());
            // Do not rethrow to keep execution user-friendly
        }

        long endTime = System.currentTimeMillis();
        System.out.println("TemData test completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }

    @Test
    public void runSampleTest4() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(DataEntryTestData.TestScenarios.TEST4_NAME);

        try {
            test.pass("Using existing GAIA session for all testcase");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase4
            // dataEntryPage.maximizeWindow();
            // dataEntryPage.clickDataEntry();
            // dataEntryPage.firstLabID();

            // Navigate to Samples tab
            dataEntryPage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            // 1. Go to the 2nd sample list and select it
            dataEntryPage.selectSecondSample();
            test.pass("Selected the 2nd sample from the list");

            // 2. Enter layer count from test data
            dataEntryPage.enterLayerCount(DataEntryTestData.SampleTestData.LAYER_COUNT);
            test.pass("Entered layer count: " + DataEntryTestData.SampleTestData.LAYER_COUNT);

            // 3. Click on layer button
            dataEntryPage.clickLayerButton();
            test.pass("Clicked 'New Layer(s)' button");

            // 4. Verify that new two layers are created with new customer IDs
            // and also verify that the 2nd row customer ID changed
            dataEntryPage.verifyLayerCreationAndCustomerIdChange(test);

            test.pass("Sample test 4 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            dataEntryPage.captureScreenshot("Failure_" + System.currentTimeMillis());
            // Log the error in the report
            test.fail("Test failed: " + e.getMessage());
            // Do not rethrow to keep execution user-friendly
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test 4 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }

    @Test
    public void runSampleTest5() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(DataEntryTestData.TestScenarios.TEST5_NAME);

        try {
            test.pass("Using existing GAIA session for all testcase");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase5
            // dataEntryPage.maximizeWindow();
            // dataEntryPage.clickDataEntry();
            // dataEntryPage.firstLabID();

            // Navigate to Samples tab
            dataEntryPage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

             // Create samples with auto base only (inline implementation)
            dataEntryPage.clearAllSampleInputs();
             test.pass("Cleared Samples tab inputs (sample count, prefix, suffix, auto number, auto base)");

             dataEntryPage.enterSampleCount(DataEntryTestData.SampleTestData.SAMPLE_COUNT);
             test.pass("Entered sample quantity");
 
             dataEntryPage.clickNewSamples();
             test.pass("Clicked 'New Sample(s)'");

            // Select the first of the newly created samples (based on SAMPLE_COUNT)
            int createdSamples = Integer.parseInt(DataEntryTestData.SampleTestData.SAMPLE_COUNT);
            dataEntryPage.selectFirstOfLastCreatedSamples(createdSamples, test);

            // Enter layer count from test data
            dataEntryPage.enterLayerCount(DataEntryTestData.SampleTestData.LAYER_COUNT);
            test.pass("Entered layer count: " + DataEntryTestData.SampleTestData.LAYER_COUNT);

            // Click on layer button
            dataEntryPage.clickLayerButton();
            test.pass("Clicked 'New Layer(s)' button");

            // Validate Blank Customer ID popup
            dataEntryPage.verifyAndDismissBlankCustomerIdPopup(test);

            test.pass("Sample test 5 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            dataEntryPage.captureScreenshot("Failure_" + System.currentTimeMillis());
            // Log the error in the report
            test.fail("Test failed: " + e.getMessage());
            // Do not rethrow to keep execution user-friendly
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test 5 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }

    @Test
    public void runSampleTest6() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(DataEntryTestData.TestScenarios.TEST6_NAME);

        try {
            test.pass("Using existing GAIA session for all testcase");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase6
            // dataEntryPage.maximizeWindow();
            // dataEntryPage.clickDataEntry();
            // dataEntryPage.firstLabID();

            // Navigate to Samples tab
            dataEntryPage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            int before = dataEntryPage.getSamplesRowCount();
            dataEntryPage.clickRefreshButton();
            int after = dataEntryPage.getSamplesRowCount();
            test.pass("Refreshed samples list. Row count before=" + before + ", after=" + after);

            test.pass("Sample test 6 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            dataEntryPage.captureScreenshot("Failure_" + System.currentTimeMillis());
            // Log the error in the report
            test.fail("Test failed: " + e.getMessage());
            // Do not rethrow to keep execution user-friendly
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test 6 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }

    @Test
    public void runSampleTest7() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(DataEntryTestData.TestScenarios.TEST7_NAME);

        try {
            test.pass("Using existing GAIA session for all testcase");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase7
            // dataEntryPage.maximizeWindow();
            // dataEntryPage.clickDataEntry();
            // dataEntryPage.firstLabID();

            // Navigate to Samples tab
            dataEntryPage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            int toDelete = Integer.parseInt(DataEntryTestData.SampleTestData.DELETE_COUNT);
            dataEntryPage.deleteLastNSamples(toDelete, test);

            test.pass("Sample test 7 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            dataEntryPage.captureScreenshot("Failure_" + System.currentTimeMillis());
            // Log the error in the report
            test.fail("Test failed: " + e.getMessage());
            // Do not rethrow to keep execution user-friendly
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test 7 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }

    // @Test
    // public void runSampleTest8() throws Exception {
    //     long startTime = System.currentTimeMillis();
    //     ExtentTest test = createTest(DataEntryTestData.TestScenarios.TEST8_NAME);

    //     try {
    //         test.pass("Using existing GAIA session for all testcase");

    //         // Session and Samples tab are prepared by DataEntryBaseTest

    //         // need this code fo debug only the testcase2
    //         // dataEntryPage.maximizeWindow();
    //         // dataEntryPage.clickDataEntry();
    //         // dataEntryPage.firstLabID();

    //         // Navigate to Samples tab
    //         dataEntryPage.navigateToSamplesTab();
    //         test.pass("Navigated to 'Samples' tab");

    //         test.pass("Sample test 8 completed successfully");

    //     } catch (Exception e) {
    //         // Capture screenshot
    //         dataEntryPage.captureScreenshot("Failure_" + System.currentTimeMillis());
    //         // Log the error in the report
    //         test.fail("Test failed: " + e.getMessage());
    //         throw e;
    //     }

    //     long endTime = System.currentTimeMillis();
    //     System.out.println("Test 7 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    // }

}
