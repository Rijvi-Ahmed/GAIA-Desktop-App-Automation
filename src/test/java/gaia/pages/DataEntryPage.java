package gaia.pages;

import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;
import java.util.List;

public class DataEntryPage extends BasePage {
    
    // Page elements - XPath locators
    private static final String MAXIMIZE_BUTTON = "//Window[@Name='GAIA - Main Menu']//Pane[@AutomationId='ribbonControl']//Button[@Name='Maximize']";
    private static final String DATA_ENTRY_BUTTON = "//List[@AutomationId='tileControl']//ListItem[@Name='Data Entry']";
    private static final String NEW_BUTTON = "//Button[@Name='New']";
    private static final String CUSTOMER_DROPDOWN_BUTTON = "//Edit[@AutomationId='CustomerLookUpEdit']//Button[@Name='Open']";
    private static final String CUSTOMER_ROW_3 = "//Pane[@AutomationId='SearchEditLookUpPopup']//ListItem[@Name='Row 3']";
    private static final String SAVE_BUTTON = "//Button[@Name='Save']";
    private static final String SAMPLES_TAB = "//TabItem[@Name='Samples']";
    private static final String SAMPLE_COUNT_INPUT = "//Edit[@Name='Number of samples to create.']";
    private static final String CUSTOMER_ID_PREFIX_INPUT = "//Edit[@Name='Customer ID Prefix']";
    private static final String CUSTOMER_ID_SUFFIX_INPUT = "//Edit[@Name='Customer ID Suffix']";
    private static final String AUTO_NUMBER_INPUT = "//Window[@Name='GAIA - Data Entry']//Edit[@Name='Auto number for the Customer ID']";
    private static final String AUTO_BASE_NUMBER_INPUT = "//Window[@Name='GAIA - Data Entry']//Edit[starts-with(@Name,'Auto base for Customer ID with mmDDyyyy structure')]";
    private static final String NEW_SAMPLES_BUTTON = "//Button[@Name='New Sample(s)']";
    private static final String TABLE_BASE = "//Table[@AutomationId='SamplesGridControl']";
    private static final String AUTO_BASE_BUTTON = "//Pane[@AutomationId='SamplesXtraUserControl']//Button[@Name='Auto Base:']";
    private static final String FIRST_LAB_ID = "//Pane[@AutomationId='OrderCollectionView']//DataItem[@Name='Lab Order row 1']";
    private static final String BLANK_AUTO_BASE_WINDOW = "//Window[contains(@Name, 'Blank Auto Base')]";
    private static final String BLANK_AUTO_BASE_OK_BUTTON = "//Window[contains(@Name, 'Blank Auto Base')]//Button[@Name='OK']";
    private static final String BLANK_AUTO_BASE_MESSAGE = "//Text[@Name='Enter valid Auto Base value to generate Customer ID']";
    
    // Setup methods
    public void maximizeWindow() {
        WindowsElement maximize = (WindowsElement) wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElementByXPath(MAXIMIZE_BUTTON)));
        clickElement(maximize);
    }
    
    public void clickDataEntry() {
        WindowsElement dataEntry = (WindowsElement) wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElementByXPath(DATA_ENTRY_BUTTON)));
        Assert.assertTrue(dataEntry.isDisplayed(), "Data Entry button should be visible");
        clickElement(dataEntry);
    }
    
    public void clickNew() {
        WindowsElement newBtn = (WindowsElement) wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElementByXPath(NEW_BUTTON)));
        Assert.assertTrue(newBtn.isEnabled(), "New button should be enabled");
        clickElement(newBtn);
    }
    
    public void selectCustomer() {
        // Open Customer Dropdown
        WindowsElement openDropdown = (WindowsElement) cocWait.until(ExpectedConditions.elementToBeClickable(
                cocDriver.findElementByXPath(CUSTOMER_DROPDOWN_BUTTON)));
        clickElement(openDropdown);
        
        // Select Customer
        WindowsElement customer = (WindowsElement) cocWait.until(ExpectedConditions.elementToBeClickable(
                cocDriver.findElementByXPath(CUSTOMER_ROW_3)));
        clickElement(customer);
    }
    
    public void save() {
        WindowsElement saveBtn = cocDriver.findElementByXPath(SAVE_BUTTON);
        Assert.assertTrue(saveBtn.isDisplayed(), "Save button should be visible");
        clickElement(saveBtn);
    }

    public void firstLabID() {
        WindowsElement firstRow = (WindowsElement) wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElementByXPath(FIRST_LAB_ID)));
            doubleClickElement(firstRow);
    }
    
    // Sample creation methods
    public void navigateToSamplesTab() {
        WindowsElement samplesTab = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(SAMPLES_TAB))
        );
        clickElement(samplesTab);
    }
    
    public void enterSampleCount(String count) {
        WindowsElement sampleInput = cocDriver.findElementByXPath(SAMPLE_COUNT_INPUT);
        enterText(sampleInput, count);
    }
    
    public void clearSampleCount() {
        WindowsElement sampleInput = cocDriver.findElementByXPath(SAMPLE_COUNT_INPUT);
        clearText(sampleInput);
    }
    
    public void enterCustomerIdPrefix(String prefix) {
        WindowsElement customerIdPrefix = cocDriver.findElementByXPath(CUSTOMER_ID_PREFIX_INPUT);
        enterText(customerIdPrefix, prefix);
    }
    
    public void clearCustomerIdPrefix() {
        WindowsElement customerIdPrefix = cocDriver.findElementByXPath(CUSTOMER_ID_PREFIX_INPUT);
        clearText(customerIdPrefix);
    }
    
    public void enterCustomerIdSuffix(String suffix) {
        WindowsElement sampleIdSuffix = cocDriver.findElementByXPath(CUSTOMER_ID_SUFFIX_INPUT);
        enterText(sampleIdSuffix, suffix);
    }
    
    public void clearCustomerIdSuffix() {
        WindowsElement sampleIdSuffix = cocDriver.findElementByXPath(CUSTOMER_ID_SUFFIX_INPUT);
        clearText(sampleIdSuffix);
    }
    
    public void enterAutoNumber(String autoNumber) {
        WindowsElement autoNumberInput = cocDriver.findElementByXPath(AUTO_NUMBER_INPUT);
        enterText(autoNumberInput, autoNumber);
    }
    
    public void clearAutoNumber() {
        WindowsElement autoNumberInput = cocDriver.findElementByXPath(AUTO_NUMBER_INPUT);
        clearText(autoNumberInput);
    }
    
    public void enterAutoBaseNumber(String autoBaseNumber) {
        WindowsElement autoBaseNumberInput = cocDriver.findElementByXPath(AUTO_BASE_NUMBER_INPUT);
        enterText(autoBaseNumberInput, autoBaseNumber);
    }
    
    public void clearAutoBaseNumber() {
        WindowsElement autoBaseNumberInput = cocDriver.findElementByXPath(AUTO_BASE_NUMBER_INPUT);
        clearText(autoBaseNumberInput);
    }

    public void clearAllSampleInputs() {
        clearSampleCount();
        clearCustomerIdPrefix();
        clearCustomerIdSuffix();
        clearAutoNumber();
        clearAutoBaseNumber();
    }

    public void verifyAndDismissBlankAutoBasePopup(ExtentTest test) {
        pause(300);
        WindowsElement popupWindow = (WindowsElement) wait.until(
                ExpectedConditions.visibilityOf(driver.findElementByXPath(BLANK_AUTO_BASE_WINDOW))
        );
        if (popupWindow == null || !popupWindow.isDisplayed()) {
            Assert.fail("Blank Auto Base popup was not displayed");
        }

        WindowsElement message = driver.findElementByXPath(BLANK_AUTO_BASE_MESSAGE);
        if (message == null || !message.isDisplayed()) {
            Assert.fail("Expected warning message was not displayed in Blank Auto Base popup");
        }

        test.pass("Blank Auto Base warning displayed with expected message");

        WindowsElement okButton = driver.findElementByXPath(BLANK_AUTO_BASE_OK_BUTTON);
        clickElement(okButton);
        pause(200);
    }

    public void verifyCustomerIdSequentialWithAutoNumber(
            String customerIdPrefix,
            String autoBase,
            String customerIdSuffix,
            int startingAutoNumber,
            int lastNSamples,
            ExtentTest test
    ) {
        pause(300);
        List<WindowsElement> rows = cocDriver.findElementsByXPath(TABLE_BASE + "//ListItem");
        int rowCount = rows.size();
        if (rowCount == 0) {
            Assert.fail("No rows found in the samples table");
        }

        int samplesToVerify = Math.min(lastNSamples, rowCount);
        int startIndex = Math.max(1, rowCount - samplesToVerify + 1);

        boolean anyMismatch = false;
        int expectedNumber = startingAutoNumber;

        for (int i = startIndex; i <= rowCount; i++) {
            String customerIdXPath = TABLE_BASE + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='Customer ID row " + i + "']";
            WindowsElement customerIdCell = cocDriver.findElementByXPath(customerIdXPath);
            String actualCustomerId = getElementValue(customerIdCell);
            if (actualCustomerId == null) actualCustomerId = "";

            String expectedCustomerId = customerIdPrefix + autoBase + customerIdSuffix + "-" + expectedNumber;

            if (!actualCustomerId.equals(expectedCustomerId)) {
                String msg = "Row " + i + " - Customer ID sequence mismatch. Expected: '" + expectedCustomerId + "', Actual: '" + actualCustomerId + "'";
                System.out.println(msg);
                test.fail(msg);
                anyMismatch = true;
            }

            expectedNumber++;
        }

        if (anyMismatch) {
            Assert.fail("Customer ID sequence did not match expected prefix+autobase+suffix-autoNumber pattern.");
        } else {
            String msg = "Customer ID sequence matches expected pattern with incrementing Auto Number starting from " + startingAutoNumber + ".";
            System.out.println(msg);
            test.pass(msg);
        }
    }

    public void verifyLabIdsPresentForLastNSamples(int lastNSamples, ExtentTest test) {
        pause(300);
        List<WindowsElement> rows = cocDriver.findElementsByXPath(TABLE_BASE + "//ListItem");
        int rowCount = rows.size();
        if (rowCount == 0) {
            Assert.fail("No rows found in the samples table");
        }

        int samplesToVerify = Math.min(lastNSamples, rowCount);
        int startIndex = Math.max(1, rowCount - samplesToVerify + 1);

        boolean anyMissing = false;

        for (int i = startIndex; i <= rowCount; i++) {
            String labIdXPath = TABLE_BASE + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='Lab ID row " + i + "']";
            WindowsElement labIdCell = cocDriver.findElementByXPath(labIdXPath);
            String labIdValue = getElementValue(labIdCell);

            if (labIdValue == null || labIdValue.trim().isEmpty()) {
                String msg = "Row " + i + " - Lab ID is missing after sample creation";
                System.out.println(msg);
                test.fail(msg);
                anyMissing = true;
            }
        }

        if (anyMissing) {
            Assert.fail("One or more Lab IDs are missing for the newly created samples.");
        } else {
            String msg = "Lab IDs are present for the last " + samplesToVerify + " sample(s).";
            System.out.println(msg);
            test.pass(msg);
        }
    }

    public void clickAutoBaseNumber() {
        WindowsElement autoBaseButtonClick = cocDriver.findElementByXPath(AUTO_BASE_BUTTON);
        clickElement(autoBaseButtonClick);
    }
    
    public void clickNewSamples() {
        WindowsElement newSamplesBtn = cocDriver.findElementByXPath(NEW_SAMPLES_BUTTON);
        clickElement(newSamplesBtn);
    }
    
    // Table manipulation methods
    public void fillColumnIfEmpty(String columnName, String prefix) {
        List<WindowsElement> rows = cocDriver.findElementsByXPath(TABLE_BASE + "//ListItem");
        int rowCount = rows.size();
        int counter = 1;

        for (int i = 1; i <= rowCount; i++) {
            String cellXPath = TABLE_BASE + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='"
                    + columnName + " row " + i + "']";
            WindowsElement cell = cocDriver.findElementByXPath(cellXPath);

            // Try getting the value without edit mode
            String currentValue = getElementValue(cell);
            if (currentValue == null || currentValue.trim().isEmpty()) {
                cell.click();
                new Actions(cocDriver).sendKeys(prefix + counter++).perform();
                pause(300);

                // If last row, move focus out to commit value
                if (i == rowCount) {
                    // Press Tab to move to next cell or first cell
                    new Actions(cocDriver).sendKeys(Keys.TAB).perform();
                    pause(200);
                }
            }
        }
    }
    
    public void validateTableFilled(List<String> columnsToCheck, ExtentTest test) {
        // Get all rows
        List<WindowsElement> rows = cocDriver.findElementsByXPath(TABLE_BASE + "//ListItem");
        int rowCount = rows.size();
        boolean anyBlank = false;

        for (int i = 1; i <= rowCount; i++) {
            // Get Lab ID for this row
            String labIdXPath = TABLE_BASE + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='Lab ID row " + i + "']";
            WindowsElement labIdCell = cocDriver.findElementByXPath(labIdXPath);
            String labId = getElementValue(labIdCell);

            // Loop through all columns to check
            for (String columnName : columnsToCheck) {
                String cellXPath = TABLE_BASE + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='"
                        + columnName + " row " + i + "']";
                WindowsElement cell = cocDriver.findElementByXPath(cellXPath);
                String value = getElementValue(cell);

                if (value == null || value.trim().isEmpty()) {
                    String msg = "Row " + i + " (Lab ID: " + labId + ") - Column '" + columnName + "' is blank!";
                    System.out.println(msg);
                    test.fail(msg);
                    anyBlank = true;
                }
            }
        }
        
        if (anyBlank) {
            Assert.fail("Some columns are blank. See report for details.");
        }

        if (!anyBlank) {
            String msg = "All fields in the table are successfully filled!";
            System.out.println(msg);
            test.pass(msg);
        }
    }
    
    public void verifyCustomerIdMatchesAutoBaseForLastNSamples(String expectedAutoBase, int lastNSamples, ExtentTest test) {
        // Allow UI to update after applying Auto Base
        pause(500);

        List<WindowsElement> rows = cocDriver.findElementsByXPath(TABLE_BASE + "//ListItem");
        int rowCount = rows.size();
        if (rowCount == 0) {
            Assert.fail("No rows found in the samples table");
        }

        int samplesToVerify = Math.min(lastNSamples, rowCount);
        int startIndex = Math.max(1, rowCount - samplesToVerify + 1);

        boolean anyMismatch = false;

        for (int i = startIndex; i <= rowCount; i++) {
            String customerIdXPath = TABLE_BASE + "//ListItem[@Name='Row " + i + "']//DataItem[@Name='Customer ID row " + i + "']";
            WindowsElement customerIdCell = cocDriver.findElementByXPath(customerIdXPath);
            String customerIdValue = getElementValue(customerIdCell);

            if (customerIdValue == null) {
                customerIdValue = "";
            }

            if (!customerIdValue.equals(expectedAutoBase)) {
                String msg = "Row " + i + " - Customer ID mismatch. Expected: '" + expectedAutoBase + "', Actual: '" + customerIdValue + "'";
                System.out.println(msg);
                test.fail(msg);
                anyMismatch = true;
            }
        }

        if (anyMismatch) {
            Assert.fail("Customer ID did not match the Auto Base value for one or more new samples.");
        } else {
            String msg = "Customer ID matches Auto Base value ('" + expectedAutoBase + "') for the last " + samplesToVerify + " sample(s).";
            System.out.println(msg);
            test.pass(msg);
        }
    }
    
    // Complete workflow methods
    public void setupDataEntrySession() {
        maximizeWindow();
        clickDataEntry();
        clickNew();
        selectCustomer();
        save();
    }
}
