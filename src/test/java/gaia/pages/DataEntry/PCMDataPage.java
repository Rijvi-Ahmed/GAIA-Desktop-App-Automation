package gaia.pages.DataEntry;

import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.windows.WindowsElement;

public class PCMDataPage extends DataEntryBasePage {
    
    private static final String PCMDATA_TAB = "//TabItem[@Name='PCM Data']";

    // Navigate the TEM section
    public void navigateToPCMDataTab() {
        WindowsElement PCMDataTab = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(PCMDATA_TAB)));
        clickElement(PCMDataTab);
    }
}
