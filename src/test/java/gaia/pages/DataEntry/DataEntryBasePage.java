package gaia.pages.DataEntry;

import io.appium.java_client.windows.WindowsElement;
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

    /**
     * Generic method to select random values from dropdown columns for all rows
     * @param tableBase The base XPath for the table
     * @param columnName The name of the column to fill
     * @param test ExtentTest for reporting
     */
    public void selectRandomFromDropdownColumnForAllRows(String tableBase, String columnName, ExtentTest test) {
        List<WindowsElement> rows = cocDriver.findElementsByXPath(tableBase + "//ListItem");
        int rowCount = rows.size();
        if (rowCount == 0) return;

        for (int i = 1; i <= rowCount; i++) {
            try {
                // Click the specific cell in this column for the current row to focus the editor
                String cellXPath = tableBase + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='" + columnName + " row " + i + "']";
                WindowsElement cell = (WindowsElement) cocWait.until(
                        ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(cellXPath))
                );
                clickElement(cell);
                pause(120);
                // If dropdown didn't appear yet, try clicking cell again
                try { cell.click(); } catch (Exception ignored) {}
                pause(120);

                // Select the second value using the provided options locator pattern
                List<WindowsElement> options = cocDriver.findElementsByXPath(
                        "//Table[@Name='MainView']//ListItem[contains(@Name,'Row ')]//DataItem[starts-with(@Name,'Name row')]"
                );
                if (options == null || options.size() < 2) {
                    // Try using the Open button if options are not yet visible
                    String openBtnXPath = tableBase + "//Edit[@Name='Editing control']//Button[@Name='Open']";
                    try {
                        WindowsElement openBtn = (WindowsElement) cocWait.until(
                                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(openBtnXPath))
                        );
                        clickElement(openBtn);
                        pause(150);
                        options = cocDriver.findElementsByXPath(
                            "//Table[@Name='MainView']//ListItem[contains(@Name,'Row ')]//DataItem[starts-with(@Name,'Name row')]"
                        );
                    } catch (Exception ignored) {}
                }
                if (options == null || options.size() < 2) continue;

                WindowsElement second = options.get(1); // zero-based index -> second item
                try {
                    org.openqa.selenium.remote.RemoteWebElement parentListItem = second.findElementByXPath("ancestor::ListItem");
                    if (parentListItem != null) {
                        clickElement(parentListItem);
                    } else {
                        clickElement(second);
                    }
                } catch (Exception e) {
                    clickElement(second);
                }
                pause(150);

                // Read back the selected value and log
                String afterValue = null;
                try { afterValue = getElementValue(cell); } catch (Exception ignored) {}
                String logMsg = "Row " + i + " - " + columnName + " selected: " + (afterValue == null ? "" : afterValue);
                System.out.println(logMsg);
                if (test != null) {
                    test.info(logMsg);
                }
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Generic method to fill empty columns with values
     * @param tableBase The base XPath for the table
     * @param columnName The name of the column to fill
     * @param prefix The prefix to use for filling values
     */
    public void fillColumnIfEmptyFromBase(String tableBase, String columnName, String prefix) {
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
                new org.openqa.selenium.interactions.Actions(cocDriver).sendKeys(prefix + counter++).perform();
                pause(300);

                // If last row, move focus out to commit value
                if (i == rowCount) {
                    // Press Tab to move to next cell or first cell
                    new org.openqa.selenium.interactions.Actions(cocDriver).sendKeys(org.openqa.selenium.Keys.TAB).perform();
                    pause(200);
                }
            }
        }
    }

    /**
     * Generic method to validate that all specified columns are filled
     * @param tableBase The base XPath for the table
     * @param columnsToCheck List of column names to validate
     * @param test ExtentTest for reporting
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
                    if (test != null) test.fail(msg);
                    anyBlank = true;
                }
            }
        }
        
        if (anyBlank) {
            if (test != null) test.fail("Some columns are blank. See report for details.");
        }

        if (!anyBlank) {
            String msg = "All fields in the table are successfully filled!";
            System.out.println(msg);
            if (test != null) test.pass(msg);
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
}


