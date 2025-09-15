package gaia.pages.DataEntry.BatchRun;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.aventstack.extentreports.ExtentTest;

import gaia.pages.DataEntry.DataEntryBasePage;
import gaia.utils.Driver.DriverManager;
import gaia.utils.TestData.DataEntryTestData.BatchRunData;

public class createBatchRunPage extends DataEntryBasePage {

    // COC window locators
    private static final String TAB_BATCH_RUNS = "//*[@AutomationId=\"ChainOfCustodyView\"]//TabItem[@Name=\"Batch Runs\"]";
    private static final String BTN_NEW_IN_COC = "//*[@AutomationId=\"BatchRunsXtraUserControl\"]//Button[@Name=\"New\"]";

    // Batch Run window locators
    private static final String BR_MAX_RESTORE = "//*[@AutomationId=\"TitleBar\"]//Button[@AutomationId=\"Minimize-Restore\"]";
    private static final String TXT_PROJECT_NAME = "//*[@AutomationId=\"BatchRunView\"]//Edit[@AutomationId=\"ProjectNameTextEdit\"]";
    private static final String TXT_ANALYSIS = "//*[@AutomationId=\"BatchRunView\"]//Edit[@AutomationId=\"ServiceLookUpEdit\"]";
    private static final String DD_ANALYSIS_FIRST_OPTION = "//Table[@Name=\"MainView\"]//ListItem[1]//DataItem[contains(@Name,'Code')]";
    private static final String TXT_TURNAROUND = "//*[@AutomationId=\"BatchRunView\"]//Edit[@AutomationId=\"TurnAroundTimeLookUpEdit\"]";
    private static final String DD_TAT_THIRD_OPTION = "//Table[@Name=\"MainView\"]//ListItem[3]//DataItem[contains(@Name, \"Time\")]";
    private static final String TXT_STATUS = "//*[@AutomationId=\"BatchRunView\"]//Edit[@AutomationId=\"StatusLookUpEdit\"]";
    private static final String DD_STATUS_CREATED = "//Table[@Name=\"MainView\"]//ListItem[1]//DataItem[contains(@Name,'Name')]";
    private static final String TXT_PO = "//*[@AutomationId=\"BatchRunView\"]//Edit[@AutomationId=\"PurchaseOrderTextEdit\"]";
    private static final String TXT_ARCHIVE_BIN = "//*[@AutomationId=\"BatchRunView\"]//Edit[@AutomationId=\"ArchiveBinTextEdit\"]";
    private static final String TXT_NOTES = "//*[@AutomationId=\"BatchRunView\"]//Edit[@AutomationId=\"NotesTextEdit\"]";
    private static final String BTN_SAVE = "//*[@AutomationId=\"BatchRunView\"]//Button[@Name=\"Save\"]";
    private static final String BTN_CLOSE = "//*[@AutomationId=\"TitleBar\"]//Button[@AutomationId=\"Close\"]";

    // Grid validation locators in COC
    private static final String GRID_PANEL = "//*[@AutomationId=\"BatchRunsGridControl\"]//*[@Name=\"Data Panel\"]";

    private WindowsDriver<WindowsElement> brDriver() { return DriverManager.getBatchRunDriver(); }
    private org.openqa.selenium.support.ui.WebDriverWait brWait() { return DriverManager.getBatchRunWait(); }

    public void navigateToBatchRunsTab() {
        WindowsElement tab = (WindowsElement) cocWait.until(
            ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(TAB_BATCH_RUNS))
        );
        clickElement(tab);
    }

    public void clickNewOnCoc() {
        WindowsElement btnNew = (WindowsElement) cocWait.until(
            ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(BTN_NEW_IN_COC))
        );
        clickElement(btnNew);
    }

    public void attachAndMaximizeBatchRunWindow() throws Exception {
        DriverManager.attachBatchRunWindow();
        try {
            WindowsElement maxBtn = (WindowsElement) brWait().until(
                ExpectedConditions.elementToBeClickable(brDriver().findElementByXPath(BR_MAX_RESTORE))
            );
            clickElement(maxBtn);
        } catch (Exception ignored) {}
    }

    public void fillForm(BatchRunData data) {
        WindowsElement projectName = (WindowsElement) brWait().until(
            ExpectedConditions.elementToBeClickable(brDriver().findElementByXPath(TXT_PROJECT_NAME))
        );
        clearText(projectName);
        enterText(projectName, data.projectName);

        WindowsElement analysis = (WindowsElement) brDriver().findElementByXPath(TXT_ANALYSIS);
        clearText(analysis);
        enterText(analysis, data.analysis);
        pause(200);
        try {
            WindowsElement first = brDriver().findElementByXPath(DD_ANALYSIS_FIRST_OPTION);
            clickElement(first);
        } catch (Exception ignored) {}

        WindowsElement tat = (WindowsElement) brDriver().findElementByXPath(TXT_TURNAROUND);
        clickElement(tat);
        pause(150);
        try {
            WindowsElement third = brDriver().findElementByXPath(DD_TAT_THIRD_OPTION);
            clickElement(third);
        } catch (Exception ignored) {}

        WindowsElement status = (WindowsElement) brDriver().findElementByXPath(TXT_STATUS);
        clearText(status);
        enterText(status, data.status);
        pause(150);
        try {
            WindowsElement created = brDriver().findElementByXPath(DD_STATUS_CREATED);
            clickElement(created);
        } catch (Exception ignored) {}

        WindowsElement po = (WindowsElement) brDriver().findElementByXPath(TXT_PO);
        clearText(po);
        enterText(po, data.purchaseOrder);

        WindowsElement archiveBin = (WindowsElement) brDriver().findElementByXPath(TXT_ARCHIVE_BIN);
        clearText(archiveBin);
        enterText(archiveBin, data.archiveBin);

        WindowsElement notes = (WindowsElement) brDriver().findElementByXPath(TXT_NOTES);
        clearText(notes);
        enterText(notes, data.notes);
    }

    public void saveBatchRun() {
        try {
            WindowsElement save = (WindowsElement) brDriver().findElementByXPath(BTN_SAVE);
            clickElement(save);
            pause(400);
        } catch (Exception ignored) {}
    }

    public void closeBatchRunWindow() {
        try {
            WindowsElement close = (WindowsElement) brDriver().findElementByXPath(BTN_CLOSE);
            clickElement(close);
            pause(300);
        } catch (Exception ignored) {}
    }

    public void validateCreatedInGrid(BatchRunData data, ExtentTest test) {
        navigateToBatchRunsTab();
        try {
            cocWait.until(ExpectedConditions.visibilityOf(cocDriver.findElementByXPath(GRID_PANEL)));
        } catch (Exception ignored) {}

        java.util.List<WindowsElement> rows = cocDriver.findElementsByXPath("//*[@AutomationId='BatchRunsGridControl']//ListItem");
        boolean found = false;
        for (WindowsElement row : rows) {
            try {
                String rowName = row.getAttribute("Name");
                if (rowName != null && rowName.toLowerCase().contains(data.analysis.toLowerCase()) && rowName.toLowerCase().contains(data.status.toLowerCase())) {
                    found = true;
                    break;
                }
            } catch (Exception ignored) {}
        }
        if (test != null) {
            if (found) test.pass("Batch Run created and visible in grid with Analysis='" + data.analysis + "' and Status='" + data.status + "'.");
            else test.fail("Batch Run not found in grid for Analysis='" + data.analysis + "' and Status='" + data.status + "'.");
        }
        org.testng.Assert.assertTrue(found, "Newly created Batch Run should exist in grid");
    }

    public void createBatchRun(BatchRunData data, ExtentTest test) throws Exception {
        navigateToBatchRunsTab();
        if (test != null) test.pass("Navigated to 'Batch Runs' tab");
        clickNewOnCoc();
        if (test != null) test.pass("Clicked 'New' on Batch Runs");
        attachAndMaximizeBatchRunWindow();
        fillForm(data);
        if (test != null) test.pass("Filled Batch Run form");
        saveBatchRun();
        closeBatchRunWindow();
        if (test != null) test.pass("Saved and closed Batch Run window");
        validateCreatedInGrid(data, test);
    }
}
