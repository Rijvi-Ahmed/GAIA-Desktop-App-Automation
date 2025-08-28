package gaia.pages.DataEntry;

import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

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


