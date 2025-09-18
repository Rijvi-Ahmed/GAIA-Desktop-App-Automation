package gaia.pages.DataEntry;

import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import java.util.List;
import com.aventstack.extentreports.ExtentTest;

import gaia.pages.Global.BasePage;

public class DataEntryBasePage extends BasePage {

    // Core Data Entry locators used for session setup
    private static final String MAXIMIZE_BUTTON = "//Window[@Name='GAIA - Main Menu']//Pane[@AutomationId='ribbonControl']//Button[@Name='Maximize']";
    private static final String DATA_ENTRY_BUTTON = "//List[@AutomationId='tileControl']//ListItem[@Name='Data Entry']";
    private static final String NEW_BUTTON = "//Button[@Name='New']";
    private static final String CUSTOMER_DROPDOWN_BUTTON = "//Edit[@AutomationId='CustomerLookUpEdit']//Button[@Name='Open']";
    private static final String CUSTOMER_ROW_3 = "//Pane[@AutomationId='SearchEditLookUpPopup']//ListItem[@Name='Row 3']";
    private static final String SAVE_BUTTON = "//Button[@Name='Save']";
    private static final String FIRST_LAB_ID = "//Pane[@AutomationId='OrderCollectionView']//DataItem[@Name='Lab Order row 1']";
    private static final String TAB_SAMPLES = "//TabItem[@Name='Samples']";
    private static final String TABLE_SAMPLES_BASE = "//Table[@AutomationId='SamplesGridControl']";

    /**
     * Generic method to select random values from dropdown columns for all rows
     * 
     * @param tableBase  The base XPath for the table
     * @param columnName The name of the column to fill
     * @param test       ExtentTest for reporting
     */
    public void selectValueFromDropdownColumnForAllRows(String tableBase, String columnName, ExtentTest test) {
        List<WindowsElement> rows = cocDriver.findElementsByXPath(tableBase + "//ListItem");
        int rowCount = rows.size();
        if (rowCount == 0)
            return;

        for (int i = 1; i <= rowCount; i++) {
            try {
                // Focus the cell for this row and column
                String cellXPath = tableBase + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='" + columnName + " row " + i + "']";
                WindowsElement cell = (WindowsElement) cocWait.until(
                        ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(cellXPath)));
                clickElement(cell);
                pause(120);

                // Dropdown should open automatically, so just select the second value
                List<WindowsElement> options = cocDriver.findElementsByXPath(
                        "//Table[@Name='MainView']//ListItem[contains(@Name,'Row ')]//DataItem[starts-with(@Name,'Name row')]");
                if (options == null || options.size() < 2)
                    continue;

                WindowsElement second = options.get(1); // zero-based index -> second item
                try {
                    org.openqa.selenium.remote.RemoteWebElement parentListItem = second
                            .findElementByXPath("ancestor::ListItem");
                    if (parentListItem != null) {
                        clickElement(parentListItem);
                    } else {
                        clickElement(second);
                    }
                } catch (Exception e) {
                    clickElement(second);
                }
                pause(500);

                // Press ENTER to commit the value
                new org.openqa.selenium.interactions.Actions(cocDriver).sendKeys(org.openqa.selenium.Keys.ENTER).perform();
                pause(200);
                // Click a neutral cell (first cell of first row) to force focus out
                String neutralCellXPath = tableBase + "//ListItem[@Name='Row 1']//DataItem[1]";
                WindowsElement neutralCell = (WindowsElement) cocWait.until(
                        ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(neutralCellXPath)));
                clickElement(neutralCell);
                pause(400);

                // Now fetch and log the value for row i
                cell = (WindowsElement) cocWait.until(
                        ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(cellXPath)));
                String afterValue = null;
                try {
                    afterValue = getElementValue(cell);
                } catch (Exception ignored) {}
                String logMsg = "Row " + i + " - " + columnName + " selected: "
                        + (afterValue == null ? "" : afterValue);
                System.out.println(logMsg);
                if (test != null) {
                    test.info(logMsg);
                }
            } catch (Exception ignored) {}
        }
    }

    /**
     * Generic method to fill empty columns with values
     * 
     * @param tableBase  The base XPath for the table
     * @param columnName The name of the column to fill
     * @param prefix     The prefix to use for filling values
     * @param test       ExtentTest for reporting
     */
    public void fillColumnIfEmptyFromBase(String tableBase, String columnName, String prefix, ExtentTest test) {
        List<WindowsElement> rows = cocDriver.findElementsByXPath(tableBase + "//ListItem");
        int rowCount = rows.size();
        int counter = 1;

        for (int i = 1; i <= rowCount; i++) {
            String cellXPath = tableBase + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='"
                    + columnName + " row " + i + "']";
            WindowsElement cell = cocDriver.findElementByXPath(cellXPath);

            // Try getting the value without edit mode
            String currentValue = getElementValue(cell);
            if (currentValue == null || currentValue.trim().isEmpty()) {
                cell.click();
                String fillValue = prefix + counter++;
                new org.openqa.selenium.interactions.Actions(cocDriver).sendKeys(fillValue).perform();
                pause(300);

                // If last row, move focus out to commit value
                if (i == rowCount) {
                    // Press Tab to move to next cell or first cell
                    new org.openqa.selenium.interactions.Actions(cocDriver).sendKeys(org.openqa.selenium.Keys.TAB)
                            .perform();
                    pause(200);
                }
                String logMsg = "Row " + i + " - " + columnName + " filled with value: " + fillValue;
                System.out.println(logMsg);
                if (test != null) {
                    test.info(logMsg);
                }
            } else {
                String logMsg = "Row " + i + " - " + columnName + " already filled with value: " + currentValue;
                System.out.println(logMsg);
                if (test != null) {
                    test.info(logMsg);
                }
            }
        }
    }

    /**
     * Generic method to validate that all specified columns are filled
     * 
     * @param tableBase      The base XPath for the table
     * @param columnsToCheck List of column names to validate
     * @param test           ExtentTest for reporting
     */
    public void validateTableFilled(String tableBase, List<String> columnsToCheck, ExtentTest test) {
        // Get all rows
        List<WindowsElement> rows = cocDriver.findElementsByXPath(tableBase + "//ListItem");
        int rowCount = rows.size();
        boolean anyBlank = false;

        for (int i = 1; i <= rowCount; i++) {
            // Get Lab ID for this row
            String labIdXPath = tableBase + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='Lab ID row " + i + "']";
            WindowsElement labIdCell = cocDriver.findElementByXPath(labIdXPath);
            String labId = getElementValue(labIdCell);

            // Loop through all columns to check
            for (String columnName : columnsToCheck) {
                String cellXPath = tableBase + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='"
                        + columnName + " row " + i + "']";
                WindowsElement cell = cocDriver.findElementByXPath(cellXPath);
                String value = getElementValue(cell);

                if (value == null || value.trim().isEmpty()) {
                    String msg = "Row " + i + " (Lab ID: " + labId + ") - Column '" + columnName + "' is blank!";
                    System.out.println(msg);
                    if (test != null)
                        test.fail(msg);
                    anyBlank = true;
                }
            }
        }

        if (anyBlank) {
            if (test != null)
                test.fail("Some columns are blank. See report for details.");
        }

        if (!anyBlank) {
            String msg = "All fields in the table are successfully filled!";
            System.out.println(msg);
            if (test != null)
                test.pass(msg);
        }
    }

    public void maximizeWindow() {
        WindowsElement maximize = (WindowsElement) wait.until(
                ExpectedConditions.elementToBeClickable(
                        driver.findElementByXPath(MAXIMIZE_BUTTON)));
        clickElement(maximize);
    }

    public void clickDataEntry() {
        WindowsElement dataEntry = (WindowsElement) wait.until(
                ExpectedConditions.elementToBeClickable(
                        driver.findElementByXPath(DATA_ENTRY_BUTTON)));
        Assert.assertTrue(dataEntry.isDisplayed(), "Data Entry button should be visible");
        clickElement(dataEntry);
    }

    public void firstLabID() {
        WindowsElement firstRow = (WindowsElement) wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElementByXPath(FIRST_LAB_ID)));
        doubleClickElement(firstRow);
    }

    public void clickNew() {
        WindowsElement newBtn = (WindowsElement) wait.until(
                ExpectedConditions.elementToBeClickable(
                        driver.findElementByXPath(NEW_BUTTON)));
        Assert.assertTrue(newBtn.isEnabled(), "New button should be enabled");
        clickElement(newBtn);
    }

    public void selectCustomer() {
        WindowsElement openDropdown = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(
                        cocDriver.findElementByXPath(CUSTOMER_DROPDOWN_BUTTON)));
        clickElement(openDropdown);

        WindowsElement customer = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(
                        cocDriver.findElementByXPath(CUSTOMER_ROW_3)));
        clickElement(customer);
    }

    public void save() {
        WindowsElement saveBtn = cocDriver.findElementByXPath(SAVE_BUTTON);
        Assert.assertTrue(saveBtn.isDisplayed(), "Save button should be visible");
        clickElement(saveBtn);
    }

    public void setupDataEntrySession() {
        maximizeWindow();
        clickDataEntry();
        clickNew();
        selectCustomer();
        save();
    }

    /**
     * Iterate PCM and Sample tables, and for Lab IDs present in both tables,
     * if Customer IDs are equal, log the pair in the test info.
     * Does not fail on mismatches; only logs matches.
     */
    public void CustomerIdMatchingFromDifferentTable(ExtentTest test, String tableName) {
        // Navigate to PCM/TEM Data and collect LabID -> CustomerID
        try {
            WindowsElement dataTab = (WindowsElement) cocWait.until(
                    ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(
                        "//TabItem[@Name='" + tableName + " Data']"
                    )));
            clickElement(dataTab);
            pause(300);
        } catch (Exception ignored) {
        }

        String tableBase = "//Table[@AutomationId='" + tableName + "DataGridControl']";
        java.util.List<WindowsElement> dataRows = cocDriver.findElementsByXPath(tableBase + "//ListItem");
        java.util.Map<String, String> labIdToCustomer = new java.util.HashMap<>();
        for (int i = 1; i <= (dataRows == null ? 0 : dataRows.size()); i++) {
            String labXPath = tableBase + "//ListItem[@Name='Row " + i + "]//DataItem[@Name='Lab ID row " + i + "]";
            String custXPath = tableBase + "//ListItem[@Name='Row " + i + "]//DataItem[@Name='Customer ID row " + i + "]";
            try {
                WindowsElement labCell = cocDriver.findElementByXPath(labXPath);
                WindowsElement custCell = cocDriver.findElementByXPath(custXPath);
                String labId = getElementValue(labCell);
                String custId = getElementValue(custCell);
                if (labId != null && !labId.trim().isEmpty()) {
                    labIdToCustomer.put(labId, custId == null ? "" : custId);
                }
            } catch (Exception ignored) {
            }
        }

        // Navigate to Samples and compare
        try {
            WindowsElement samplesTab = (WindowsElement) cocWait.until(
                    ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(TAB_SAMPLES)));
            clickElement(samplesTab);
            pause(300);
        } catch (Exception ignored) {
        }

        java.util.List<WindowsElement> sampleRows = cocDriver.findElementsByXPath(TABLE_SAMPLES_BASE + "//ListItem");
        java.util.List<String> mismatchedLabIds = new java.util.ArrayList<>();
        for (int i = 1; i <= (sampleRows == null ? 0 : sampleRows.size()); i++) {
            String labXPath = TABLE_SAMPLES_BASE + "//ListItem[@Name='Row " + i + "]//DataItem[@Name='Lab ID row " + i + "]";
            String custXPath = TABLE_SAMPLES_BASE + "//ListItem[@Name='Row " + i + "]//DataItem[@Name='Customer ID row " + i + "]";
            try {
                WindowsElement labCell = cocDriver.findElementByXPath(labXPath);
                WindowsElement custCell = cocDriver.findElementByXPath(custXPath);
                String labId = getElementValue(labCell);
                String sampleCust = getElementValue(custCell);
                if (labId == null || labId.trim().isEmpty())
                    continue;
                String dataCust = labIdToCustomer.get(labId);
                if (dataCust != null) {
                    if (dataCust.equals(sampleCust)) {
                        String msg = "Customer ID match for Lab ID '" + labId + "': " + sampleCust;
                        if (test != null)
                            test.info(msg);
                        System.out.println(msg);
                    } else {
                        mismatchedLabIds.add(labId + " (" + tableName + "='" + dataCust + "', Sample='" + sampleCust + "')");
                    }
                }
            } catch (Exception ignored) {
            }
        }
        if (mismatchedLabIds.isEmpty()) {
            if (test != null) test.pass("All Customer IDs match for each Lab ID present in both " + tableName + " and Sample tables.");
        } else {
            String msg = "Customer ID mismatches found for Lab IDs: " + String.join(", ", mismatchedLabIds);
            if (test != null) test.fail(msg);
            System.out.println(msg);
        }
    }

    /**
     * Clear the Customer ID in the PCM table for the Lab ID in the first row, then
     * navigate to the Samples table and validate that the Customer ID is
     * blank for the same Lab ID. Logs pass/fail accordingly.
     */
    public void clearCustomerIdAndValidateCustomerIdBlankOnSample(ExtentTest test, String tableName) {
        // Navigate to PCM/TEM tab and get the Lab ID from the first row
        String targetLabId = null;
        try {
            WindowsElement dataTab = (WindowsElement) cocWait.until(
                    ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(
                        "//TabItem[@Name='" + tableName + " Data']"
                    )));
            clickElement(dataTab);
            pause(300);
        } catch (Exception ignored) {
        }

        String tableBase = "//Table[@AutomationId='" + tableName + "DataGridControl']";
        java.util.List<WindowsElement> dataRows = cocDriver.findElementsByXPath(tableBase + "//ListItem");
        if (dataRows == null || dataRows.size() == 0) {
            if (test != null)
                test.fail("No rows found in " + tableName + " table");
            return;
        }
        // Get Lab ID from the first row
        try {
            String labXPath = tableBase + "//ListItem[@Name='Row 1']//DataItem[@Name='Lab ID row 1']";
            WindowsElement labCell = cocDriver.findElementByXPath(labXPath);
            targetLabId = getElementValue(labCell);
        } catch (Exception ignored) {
        }

        if (targetLabId == null || targetLabId.trim().isEmpty()) {
            if (test != null)
                test.fail("Lab ID in first row of " + tableName + " table is empty");
            return;
        }

        // Clear the Customer ID for the target Lab ID
        boolean cleared = false;
        int dataCount = dataRows.size();
        for (int i = 1; i <= dataCount; i++) {
            String labXPath = tableBase + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='Lab ID row " + i + "']";
            String custXPath = tableBase + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='Customer ID row " + i + "']";
            try {
                WindowsElement labCell = cocDriver.findElementByXPath(labXPath);
                String labId = getElementValue(labCell);
                if (labId != null && labId.equals(targetLabId)) {
                    WindowsElement custCell = cocDriver.findElementByXPath(custXPath);
                    clickElement(custCell);
                    pause(100);
                    // Clear with CTRL+A, DELETE and commit with TAB
                    new Actions(cocDriver).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.DELETE)
                            .perform();
                    pause(150);
                    new Actions(cocDriver).sendKeys(Keys.TAB).perform();
                    pause(250);
                    cleared = true;
                    break;
                }
            } catch (Exception ignored) {
            }
        }

        if (!cleared) {
            if (test != null)
                test.fail("Could not find Lab ID '" + targetLabId + "' in " + tableName + " table");
            return;
        }

        // Optionally save to persist changes
        try {
            WindowsElement saveBtn = cocDriver.findElementByXPath(SAVE_BUTTON);
            clickElement(saveBtn);
            pause(400);
        } catch (Exception ignored) {
        }

        // Navigate to Samples and validate blank Customer ID for same Lab ID
        try {
            WindowsElement samplesTab = (WindowsElement) cocWait.until(
                    ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(TAB_SAMPLES)));
            clickElement(samplesTab);
            pause(300);
        } catch (Exception ignored) {
        }

        boolean foundSample = false;
        boolean isBlank = false;
        java.util.List<WindowsElement> sampleRows = cocDriver.findElementsByXPath(TABLE_SAMPLES_BASE + "//ListItem");
        int sampleCount = sampleRows == null ? 0 : sampleRows.size();
        for (int i = 1; i <= sampleCount; i++) {
            String labXPath = TABLE_SAMPLES_BASE + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='Lab ID row " + i + "']";
            String custXPath = TABLE_SAMPLES_BASE + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='Customer ID row " + i + "']";
            try {
                WindowsElement labCell = cocDriver.findElementByXPath(labXPath);
                String labId = getElementValue(labCell);
                if (labId != null && labId.equals(targetLabId)) {
                    foundSample = true;
                    WindowsElement custCell = cocDriver.findElementByXPath(custXPath);
                    String cust = getElementValue(custCell);
                    isBlank = (cust == null || cust.trim().isEmpty());
                    break;
                }
            } catch (Exception ignored) {
            }
        }

        if (!foundSample) {
            if (test != null)
                test.fail("No matching Lab ID '" + targetLabId + "' found in Samples table");
            return;
        }

        if (isBlank) {
            if (test != null)
                test.pass("Customer ID is blank in Samples table for Lab ID '" + targetLabId + "' after clearing from " + tableName + " table");
        } else {
            if (test != null)
                test.fail("Customer ID is not blank in Samples for Lab ID '" + targetLabId + "' after clearing in " + tableName);
        }
    }
}
