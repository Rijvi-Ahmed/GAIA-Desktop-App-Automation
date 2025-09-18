package gaia.pages.DataEntry;

import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.windows.WindowsElement;
import com.aventstack.extentreports.ExtentTest;

public class TEMDataPage extends DataEntryBasePage {


    private static final String TEMDATA_TAB = "//TabItem[@Name='TEM Data']";
    private static final String TEM_TABLE_BASE = "//Table[@AutomationId='TEMDataGridControl']";

    // Navigate the TEM section
    public void navigateToTEMDataTab() {
        WindowsElement TEMDataTab = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(TEMDATA_TAB)));
        clickElement(TEMDataTab);
    }

    public void CustomerIdMatchingBetweenTemAndSample(ExtentTest test){
        CustomerIdMatchingFromDifferentTable(test, "TEM");
    }

    public void clearTemCustomerIdAndValidateCustomerIdBlankOnSample(ExtentTest test){
        clearCustomerIdAndValidateCustomerIdBlankOnSample(test, "TEM");
    }

    public void fillColumnIfEmptyInTem(String columnName, String prefix, ExtentTest test) {
        fillColumnIfEmptyFromBase(TEM_TABLE_BASE, columnName, prefix, test);
    }

    public void selectFilterForAllRows(ExtentTest test) {
        selectValueFromDropdownColumnForAllRows(TEM_TABLE_BASE, "Filter", test);
    }

    public void selectFilterTypeForAllRows(ExtentTest test) {
        selectValueFromDropdownColumnForAllRows(TEM_TABLE_BASE, "Filter Type", test);
    }

    public void selectGridOpeningAreaForAllRows(ExtentTest test) {
        selectValueFromDropdownColumnForAllRows(TEM_TABLE_BASE, "Grid Opening Area (mm²)", test);
    }

    public void validateTableFilledonTem(List<String> columnsToCheck, ExtentTest test) {
        validateTableFilled(TEM_TABLE_BASE, columnsToCheck, test);
    }
    /**
     * Verifies that the selected filter value in the first row is applied to all rows in the TEM table.
     * Logs whether all rows have the same filter value as the first row.
     * @param ColumnName The name of the filter column to check (e.g., "Filter", "Filter Type").
     * @param test ExtentTest for logging
     */
    public void verifyFilterValueAppliedToAllRows(String ColumnName, ExtentTest test) {
        List<WindowsElement> rows = cocDriver.findElementsByXPath(TEM_TABLE_BASE + "//ListItem");
        int rowCount = rows.size();
        if (rowCount == 0) {
            test.fail("No rows found in the TEM table");
            System.out.println("No rows found in the TEM table");
            return;
        }

        // Select the cell in the first row and open dropdown
        String firstRowCellXPath = TEM_TABLE_BASE + "//ListItem[@Name='Row 1']//DataItem[@Name='" + ColumnName + " row 1']";
        WindowsElement firstRowCell = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(firstRowCellXPath)));
        clickElement(firstRowCell);
        pause(120);

        // Dropdown should open automatically, so just select the second value
        List<WindowsElement> options = cocDriver.findElementsByXPath(
                "//Table[@Name='MainView']//ListItem[contains(@Name,'Row ')]//DataItem[starts-with(@Name,'Name row')]");
        if (options != null && options.size() >= 2) {
            WindowsElement first = options.get(0); // zero-based index -> first item
            try {
                org.openqa.selenium.remote.RemoteWebElement parentListItem = first
                        .findElementByXPath("ancestor::ListItem");
                if (parentListItem != null) {
                    clickElement(parentListItem);
                } else {
                    clickElement(first);
                }
            } catch (Exception e) {
                clickElement(first);
            }
            pause(500);

            // Press ENTER to commit the value
            new org.openqa.selenium.interactions.Actions(cocDriver).sendKeys(org.openqa.selenium.Keys.ENTER).perform();
            pause(200);
            // Click a neutral cell (first cell of first row) to force focus out
            String neutralCellXPath = TEM_TABLE_BASE + "//ListItem[@Name='Row 1']//DataItem[1]";
            WindowsElement neutralCell = (WindowsElement) cocWait.until(
                    ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(neutralCellXPath)));
            clickElement(neutralCell);
            pause(400);
        }

        // Get the filter value from the first row after selection
        firstRowCell = cocDriver.findElementByXPath(firstRowCellXPath);
        String firstValue = getElementValue(firstRowCell);

        boolean allMatch = true;
        for (int i = 2; i <= rowCount; i++) {
            String cellXPath = TEM_TABLE_BASE + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='" + ColumnName + " row " + i + "']";
            WindowsElement cell = cocDriver.findElementByXPath(cellXPath);
            String value = getElementValue(cell);
            if (!firstValue.equals(value)) {
                allMatch = false;
                String msg = "Row " + i + " filter value ('" + value + "') does not match first row value ('" + firstValue + "')";
                test.fail(msg);
                System.out.println(msg);
            }
        }

        if (allMatch) {
            String msg = "All rows have the same filter value ('" + firstValue + "') for the selected value ('" + firstValue + "') on the first row on column '" + ColumnName + "'.";
            test.pass(msg);
            System.out.println(msg);
        }
    }
}
