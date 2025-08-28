package gaia.pages.DataEntry;

import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.windows.WindowsElement;

public class TEMDataPage extends DataEntryBasePage {


    private static final String TEMDATA_TAB = "//TabItem[@Name='TEM Data']";

    // Navigate the TEM section
    public void navigateToTEMDataTab() {
        WindowsElement TEMDataTab = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(TEMDATA_TAB)));
        clickElement(TEMDataTab);
    }
}
