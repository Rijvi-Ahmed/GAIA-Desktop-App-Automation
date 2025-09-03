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
}
