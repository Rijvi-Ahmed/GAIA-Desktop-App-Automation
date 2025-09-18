package gaia.tests.DataEntry;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;

import gaia.pages.DataEntry.BatchRun.createBatchRunPage;
import gaia.utils.TestData.DataEntryTestData.BatchRunData;

public class BatchRunTest extends DataEntryBaseTest {

    @Test
    public void runCreateBatchRunTest1() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(BatchRunData.BatchRunTestScenarios.TEST1_NAME);

        createBatchRunPage page = new createBatchRunPage();
        BatchRunData data = BatchRunData.defaultData();

        try {
            page.maximizeWindow();
            page.clickDataEntry();
            page.firstLabID();

            page.createBatchRun(data, test);
            page.validateCreatedBatchRunByAnalysis(data, test);

        } catch (Exception e) {
            page.captureScreenshot("Failure_" + System.currentTimeMillis());
            if (test != null) test.fail("Test failed: " + e.getMessage());
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            System.out.println("Create Batch Run completed in " + (endTime - startTime) / 1000.0 + " seconds");
        }

    }

    @Test
    public void runCreateBatchRunTest2() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest(BatchRunData.BatchRunTestScenarios.TEST2_NAME);

        createBatchRunPage page = new createBatchRunPage();
        //BatchRunData data = BatchRunData.defaultData();

        try {
            page.maximizeWindow();
            page.clickDataEntry();
            page.firstLabID();

            // page.createBatchRun(data, test);
            // page.validateCreatedBatchRunByAnalysis(data, test);

        } catch (Exception e) {
            page.captureScreenshot("Failure_" + System.currentTimeMillis());
            if (test != null) test.fail("Test failed: " + e.getMessage());
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            System.out.println("Create Batch Run completed in " + (endTime - startTime) / 1000.0 + " seconds");
        }

    }
}


