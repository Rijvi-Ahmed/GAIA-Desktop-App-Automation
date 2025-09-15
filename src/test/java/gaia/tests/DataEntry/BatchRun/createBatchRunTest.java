package gaia.tests.DataEntry.BatchRun;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;

import gaia.tests.DataEntry.DataEntryBaseTest;
import gaia.pages.DataEntry.BatchRun.createBatchRunPage;
import gaia.utils.TestData.DataEntryTestData.BatchRunData;

public class createBatchRunTest extends DataEntryBaseTest {

    @Test
    public void runCreateBatchRunTest1() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentTest test = createTest("Create Batch Run");

        createBatchRunPage page = new createBatchRunPage();
        BatchRunData data = BatchRunData.defaultData();

        try {

            page.maximizeWindow();
            page.clickDataEntry();
            page.firstLabID();

            page.createBatchRun(data, test);

        } catch (Exception e) {
            page.captureScreenshot("Failure_" + System.currentTimeMillis());
            test.fail("Test failed: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Create Batch Run completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }
}
