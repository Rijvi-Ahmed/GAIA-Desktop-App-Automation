package gaia.pages.DataEntry.BatchRun;

import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.aventstack.extentreports.ExtentTest;
import gaia.pages.DataEntry.DataEntryBasePage;
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

    public void MaximizeBatchRunWindow() throws Exception {
        try {
            WindowsElement maxBtn = (WindowsElement) batchRunWait.until(
                ExpectedConditions.elementToBeClickable(batchRunDriver.findElementByXPath(BR_MAX_RESTORE))
            );
            clickElement(maxBtn);
        } catch (Exception ignored) {}
    }
    // Removed date capture as validation is by Analysis only

    public void fillForm(BatchRunData data) {
        WindowsElement projectName = (WindowsElement) batchRunWait.until(
            ExpectedConditions.elementToBeClickable(batchRunDriver.findElementByXPath(TXT_PROJECT_NAME))
        );
        clearText(projectName);
        enterText(projectName, data.projectName);

        WindowsElement analysis = (WindowsElement) batchRunDriver.findElementByXPath(TXT_ANALYSIS);
        clearText(analysis);
        enterText(analysis, data.analysis);
        pause(200);
        try {
            WindowsElement first = batchRunDriver.findElementByXPath(DD_ANALYSIS_FIRST_OPTION);
            clickElement(first);
        } catch (Exception ignored) {}

        WindowsElement tat = (WindowsElement) batchRunDriver.findElementByXPath(TXT_TURNAROUND);
        clickElement(tat);
        pause(150);
        try {
            WindowsElement third = batchRunDriver.findElementByXPath(DD_TAT_THIRD_OPTION);
            clickElement(third);
        } catch (Exception ignored) {}

        WindowsElement status = (WindowsElement) batchRunDriver.findElementByXPath(TXT_STATUS);
        clearText(status);
        enterText(status, data.status);
        pause(150);
        try {
            WindowsElement created = batchRunDriver.findElementByXPath(DD_STATUS_CREATED);
            clickElement(created);
        } catch (Exception ignored) {}

        WindowsElement po = (WindowsElement) batchRunDriver.findElementByXPath(TXT_PO);
        clearText(po);
        enterText(po, data.purchaseOrder);

        WindowsElement archiveBin = (WindowsElement) batchRunDriver.findElementByXPath(TXT_ARCHIVE_BIN);
        clearText(archiveBin);
        enterText(archiveBin, data.archiveBin);

        WindowsElement notes = (WindowsElement) batchRunDriver.findElementByXPath(TXT_NOTES);
        clearText(notes);
        enterText(notes, data.notes);
    }

    public void saveBatchRun() {
        try {
            WindowsElement save = (WindowsElement) batchRunDriver.findElementByXPath(BTN_SAVE);
            clickElement(save);
            pause(400);
        } catch (Exception ignored) {}
    }

    public void closeBatchRunWindow() {
        try {
            WindowsElement close = (WindowsElement) batchRunDriver.findElementByXPath(BTN_CLOSE);
            clickElement(close);
            pause(300);
        } catch (Exception ignored) {}
    }

    public void createBatchRun(BatchRunData data, ExtentTest test) throws Exception {
        navigateToBatchRunsTab();
        if (test != null) test.pass("Navigated to 'Batch Runs' tab");
        clickNewOnCoc();
        if (test != null) test.pass("Clicked 'New' on Batch Runs");
        MaximizeBatchRunWindow();
        fillForm(data);
        if (test != null) test.pass("Filled Batch Run form for creating a batch run");
        saveBatchRun();
        closeBatchRunWindow();
        if (test != null) test.pass("Saved and closed Batch Run window");
    }

    public void validateCreatedBatchRunByAnalysis(BatchRunData data, ExtentTest test) {
        navigateToBatchRunsTab();
        try {
            cocWait.until(ExpectedConditions.visibilityOf(cocDriver.findElementByXPath(GRID_PANEL)));
        } catch (Exception ignored) {}

        // Give the grid a brief moment to refresh and populate rows
        for (int retry = 0; retry < 10; retry++) {
            java.util.List<WindowsElement> r = cocDriver.findElementsByXPath("//*[@AutomationId='BatchRunsGridControl']//ListItem");
            if (r != null && r.size() > 0) break;
            pause(200);
        }

        String gridBase = "//*[@AutomationId='BatchRunsGridControl']";
        java.util.List<WindowsElement> rows = cocDriver.findElementsByXPath(gridBase + "//ListItem");
        boolean found = false;
        for (int i = 1; i <= (rows == null ? 0 : rows.size()); i++) {
            try {
                // Analysis cell: try explicit header; fallback to common variants and row name
                String[] analysisHeaders = new String[] { "Analysis", "Service", "Analysis Type" };
                boolean analysisMatches = false;
                for (String header : analysisHeaders) {
                    try {
                        String analysisCellXPath = gridBase + "//ListItem[@Name='Row " + i + "']//DataItem[contains(@Name,'" + header + " row " + i + "')]";
                        WindowsElement analysisCell = cocDriver.findElementByXPath(analysisCellXPath);
                        String analysisText = getElementValue(analysisCell);
                        if (analysisText == null || analysisText.trim().isEmpty()) {
                            analysisText = analysisCell.getAttribute("Name");
                        }
                        if (analysisText != null && data != null && data.analysis != null &&
                            analysisText.toLowerCase().contains(data.analysis.toLowerCase())) {
                            analysisMatches = true;
                            break;
                        }
                    } catch (Exception ignoredInner) {}
                }

                if (!analysisMatches) {
                    try {
                        WindowsElement row = cocDriver.findElementByXPath(gridBase + "//ListItem[@Name='Row " + i + "']");
                        String rowName = row.getAttribute("Name");
                        analysisMatches = (rowName != null && data != null && data.analysis != null) &&
                                rowName.toLowerCase().contains(data.analysis.toLowerCase());
                    } catch (Exception ignoredInner) {}
                }

                if (analysisMatches) { found = true; break; }
            } catch (Exception ignored) {}
        }
        if (test != null) {
            String analysisForMsg = (data != null && data.analysis != null) ? data.analysis : "";
            if (found) test.pass("Batch Run created and visible in batch run table with Analysis='" + analysisForMsg + "'.");
            else test.fail("Batch Run not found in the batch run table for Analysis='" + analysisForMsg + "'.");
        }
        org.testng.Assert.assertTrue(found, "Newly created Batch Run exist in batch run table.");
    }


}
