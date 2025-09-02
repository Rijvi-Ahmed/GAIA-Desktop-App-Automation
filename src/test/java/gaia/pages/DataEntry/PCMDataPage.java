package gaia.pages.DataEntry;

import java.util.List;
 

import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.windows.WindowsElement;
import com.aventstack.extentreports.ExtentTest;

public class PCMDataPage extends DataEntryBasePage {
    
    private static final String PCMDATA_TAB = "//TabItem[@Name='PCM Data']";
    private static final String PCM_TABLE_BASE = "//Table[@AutomationId='PCMDataGridControl']";

    // Navigate the PCM section
    public void navigateToPCMDataTab() {
        WindowsElement PCMDataTab = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(PCMDATA_TAB)));
        clickElement(PCMDataTab);
    }

    public void fillColumnIfEmptyInPcm(String columnName, String prefix) {
        fillColumnIfEmptyFromBase(PCM_TABLE_BASE, columnName, prefix);
    }

    public void CustomerIdMatchingBetweenPcmAndSample(ExtentTest test){
        CustomerIdMatchingFromDifferentTable(test);
    }

    public void clearPcmCustomerIdAndValidateCustomerIdBlankOnSample(ExtentTest test){
        clearCustomerIdAndValidateCustomerIdBlankOnSample(test);
    }


    public void selectRandomVolumeUnitForAllRows(ExtentTest test) {
        selectRandomFromDropdownColumnForAllRows(PCM_TABLE_BASE, "Volume Unit", test);
    }

    public void selectRandomTimeUnitForAllRows(ExtentTest test) {
        selectRandomFromDropdownColumnForAllRows(PCM_TABLE_BASE, "Time Unit", test);
    }

    public void validateTableFilledonPcm(List<String> columnsToCheck, ExtentTest test) {
        validateTableFilled(PCM_TABLE_BASE, columnsToCheck, test);
    }

}
