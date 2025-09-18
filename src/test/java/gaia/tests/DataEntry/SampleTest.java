package gaia.tests.DataEntry;

import com.aventstack.extentreports.ExtentTest;

import gaia.utils.TestData.DataEntryTestData.SampleData;

import org.testng.annotations.Test;

public class SampleTest extends DataEntryBaseTest {

    @Test
    public void runSampleTest1() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(SampleData.SampleTestScenarios.TEST1_NAME);

        try {

            // Session and Samples tab are prepared by DataEntryBaseTest
            // Create samples with all data (inline implementation)
            samplePage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            samplePage.enterSampleCount(SampleData.SampleTestData.SAMPLE_COUNT);
            test.info("Entered sample quantity: " + SampleData.SampleTestData.SAMPLE_COUNT);

            samplePage.enterCustomerIdPrefix(SampleData.SampleTestData.CUSTOMER_ID_PREFIX);
            test.info("Entered Customer ID Prefix: " + SampleData.SampleTestData.CUSTOMER_ID_PREFIX);

            samplePage.enterCustomerIdSuffix(SampleData.SampleTestData.CUSTOMER_ID_SUFFIX);
            test.info("Entered Customer ID Suffix: " + SampleData.SampleTestData.CUSTOMER_ID_SUFFIX);

            samplePage.enterAutoNumber(SampleData.SampleTestData.AUTO_NUMBER_TEST1);
            test.info("Entered Auto Number: " + SampleData.SampleTestData.AUTO_NUMBER_TEST1);

            samplePage.enterAutoBaseNumber(SampleData.SampleTestData.AUTO_BASE_NUMBER);
            test.info("Entered Auto base number: " + SampleData.SampleTestData.AUTO_BASE_NUMBER);

            samplePage.clickNewSamples();
            test.pass("Clicked 'New Sample(s)'");

            // Verify Lab IDs exist for newly created samples
            int createdSamples = Integer.parseInt(SampleData.SampleTestData.SAMPLE_COUNT);
            samplePage.verifyLabIdsPresentForLastNSamples(createdSamples, test);

            // Fill missing values after creating samples
            samplePage.fillColumnIfEmptyInSample("Customer ID", SampleData.SampleTestData.CUSTOMER_ID_PREFIX_FILL, test);
            samplePage.fillColumnIfEmptyInSample("Description", SampleData.SampleTestData.DESCRIPTION_PREFIX_FILL, test);
            samplePage.fillColumnIfEmptyInSample("Lab Notes", SampleData.SampleTestData.LAB_NOTES_PREFIX_FILL, test);

            // Validate table after filling
            samplePage.validateTableFilledOnSample(SampleData.SampleTestData.COLUMNS_TO_CHECK, test);

            // Verify Customer ID sequence: prefix + autobase + suffix + '-' + autoNumber,
            // incrementing by 1
            int numSamples = Integer.parseInt(SampleData.SampleTestData.SAMPLE_COUNT);
            int startingAutoNumber = Integer.parseInt(SampleData.SampleTestData.AUTO_NUMBER_TEST1);
            samplePage.verifyCustomerIdSequentialWithAutoNumber(
                    SampleData.SampleTestData.CUSTOMER_ID_PREFIX,
                    SampleData.SampleTestData.AUTO_BASE_NUMBER,
                    SampleData.SampleTestData.CUSTOMER_ID_SUFFIX,
                    startingAutoNumber,
                    numSamples,
                    test);

            test.pass("Sample test 1 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            samplePage.captureScreenshot("Failure_" + System.currentTimeMillis());
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
        ExtentTest test = createTest(SampleData.SampleTestScenarios.TEST2_NAME);

        try {

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase2
            // samplePage.maximizeWindow();
            // samplePage.clickDataEntry();
            // samplePage.firstLabID();

            // navigate the sample tab
            samplePage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            // Create samples with auto base only (inline implementation)
            samplePage.clearAllSampleInputs();
            test.pass("Cleared Samples tab inputs (sample count, prefix, suffix, auto number, auto base)");

            samplePage.enterSampleCount(SampleData.SampleTestData.SAMPLE_COUNT);
            test.pass("Entered sample quantity");

            samplePage.clickNewSamples();
            test.pass("Clicked 'New Sample(s)'");

            samplePage.enterAutoBaseNumber(SampleData.SampleTestData.AUTO_BASE_NUMBER);
            test.pass("Entered Auto base number");

            samplePage.clickAutoBaseNumber();
            test.pass("Clicked 'Auto base button'");

            // Verify Customer ID cells for the newly created samples equal the Auto Base
            // value entered
            int samplesToVerify = Integer.parseInt(SampleData.SampleTestData.SAMPLE_COUNT);
            samplePage.verifyCustomerIdMatchesAutoBaseForLastNSamples(
                    SampleData.SampleTestData.AUTO_BASE_NUMBER,
                    samplesToVerify,
                    test);

            test.pass("Sample test 2 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            samplePage.captureScreenshot("Failure_" + System.currentTimeMillis());
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
        ExtentTest test = createTest(SampleData.SampleTestScenarios.TEST3_NAME);

        try {
            test.pass("Using existing GAIA session");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase3
            // samplePage.maximizeWindow();
            // samplePage.clickDataEntry();
            // samplePage.firstLabID();

            // Navigate samples
            samplePage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            // Create samples with auto base only (inline implementation)
            samplePage.clearAllSampleInputs();
            test.pass("Cleared Samples tab inputs (sample count, prefix, suffix, auto number, auto base)");

            samplePage.enterSampleCount(SampleData.SampleTestData.SAMPLE_COUNT);
            test.pass("Entered Sample quantity: " + SampleData.SampleTestData.SAMPLE_COUNT);

            samplePage.clickNewSamples();
            test.pass("Clicked 'New Sample(s)'");

            samplePage.clickAutoBaseNumber();
            test.pass("Clicked 'Auto base button'");

            // Verify blank Auto Base popup with message and dismiss it
            samplePage.verifyAndDismissBlankAutoBasePopup(test);

            test.pass("Sample test 3 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            samplePage.captureScreenshot("Failure_" + System.currentTimeMillis());
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
        ExtentTest test = createTest(SampleData.SampleTestScenarios.TEST4_NAME);

        try {
            test.pass("Using existing GAIA session for all testcase");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase4
            // samplePage.maximizeWindow();
            // samplePage.clickDataEntry();
            // samplePage.firstLabID();

            // Navigate to Samples tab
            samplePage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            // 1. Go to the 2nd sample list and select it
            samplePage.selectSecondSample();
            test.pass("Selected the 2nd sample from the list");

            // 2. Enter layer count from test data
            samplePage.enterLayerCount(SampleData.SampleTestData.LAYER_COUNT);
            test.pass("Entered layer count: " + SampleData.SampleTestData.LAYER_COUNT);

            // 3. Click on layer button
            samplePage.clickLayerButton();
            test.pass("Clicked 'New Layer(s)' button");

            // 4. Verify that new two layers are created with new customer IDs
            // and also verify that the 2nd row customer ID changed
            samplePage.verifyLayerCreationAndCustomerIdChange(test);

            test.pass("Sample test 4 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            samplePage.captureScreenshot("Failure_" + System.currentTimeMillis());
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
        ExtentTest test = createTest(SampleData.SampleTestScenarios.TEST5_NAME);

        try {
            test.pass("Using existing GAIA session for all testcase");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase5
            // samplePage.maximizeWindow();
            // samplePage.clickDataEntry();
            // samplePage.firstLabID();

            // Navigate to Samples tab
            samplePage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

             // Create samples with auto base only (inline implementation)
            samplePage.clearAllSampleInputs();
             test.pass("Cleared Samples tab inputs (sample count, prefix, suffix, auto number, auto base)");

             samplePage.enterSampleCount(SampleData.SampleTestData.SAMPLE_COUNT);
             test.pass("Entered sample quantity");
 
             samplePage.clickNewSamples();
             test.pass("Clicked 'New Sample(s)'");

            // Select the first of the newly created samples (based on SAMPLE_COUNT)
            int createdSamples = Integer.parseInt(SampleData.SampleTestData.SAMPLE_COUNT);
            samplePage.selectFirstOfLastCreatedSamples(createdSamples, test);

            // Enter layer count from test data
            samplePage.enterLayerCount(SampleData.SampleTestData.LAYER_COUNT);
            test.pass("Entered layer count: " + SampleData.SampleTestData.LAYER_COUNT);

            // Click on layer button
            samplePage.clickLayerButton();
            test.pass("Clicked 'New Layer(s)' button");

            // Validate Blank Customer ID popup
            samplePage.verifyAndDismissBlankCustomerIdPopup(test);

            test.pass("Sample test 5 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            samplePage.captureScreenshot("Failure_" + System.currentTimeMillis());
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
        ExtentTest test = createTest(SampleData.SampleTestScenarios.TEST6_NAME);

        try {
            test.pass("Using existing GAIA session for all testcase");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase6
            // samplePage.maximizeWindow();
            // samplePage.clickDataEntry();
            // samplePage.firstLabID();

            // Navigate to Samples tab
            samplePage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            int before = samplePage.getSamplesRowCount();
            samplePage.clickRefreshButton();
            int after = samplePage.getSamplesRowCount();
            test.pass("Refreshed samples list. Row count before=" + before + ", after=" + after);

            test.pass("Sample test 6 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            samplePage.captureScreenshot("Failure_" + System.currentTimeMillis());
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
        ExtentTest test = createTest(SampleData.SampleTestScenarios.TEST7_NAME);

        try {
            test.pass("Using existing GAIA session for all testcase");

            // Session and Samples tab are prepared by DataEntryBaseTest

            // need this code fo debug only the testcase7
            // samplePage.maximizeWindow();
            // samplePage.clickDataEntry();
            // samplePage.firstLabID();

            // Navigate to Samples tab
            samplePage.navigateToSamplesTab();
            test.pass("Navigated to 'Samples' tab");

            int toDelete = Integer.parseInt(SampleData.SampleTestData.DELETE_COUNT);
            samplePage.deleteLastNSamples(toDelete, test);

            test.pass("Sample test 7 completed successfully");

        } catch (Exception e) {
            // Capture screenshot
            samplePage.captureScreenshot("Failure_" + System.currentTimeMillis());
            // Log the error in the report
            test.fail("Test failed: " + e.getMessage());
            // Do not rethrow to keep execution user-friendly
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test 7 completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }

}
