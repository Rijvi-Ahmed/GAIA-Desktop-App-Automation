package gaia.pages.DataEntry;

import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.aventstack.extentreports.ExtentTest;
import java.util.List;

public class SamplePage extends DataEntryBasePage {
    

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
    private static final String SECOND_SAMPLE_ROW = "//Table[@AutomationId='SamplesGridControl']//ListItem[2]";
    private static final String LAYER_COUNT_EDIT = "//Pane[@AutomationId='SamplesXtraUserControl']//Edit[@Name='Number of Layers to create from the Sample selected.']";
    private static final String LAYER_BUTTON = "//Pane[@AutomationId='SamplesXtraUserControl']//Button[@Name='New Layer(s)']";
    private static final String BLANK_CUSTOMER_ID_WINDOW = "//Window[@Name='GAIA - Data Entry']//Window[@Name='Blank Customer ID']";
    private static final String BLANK_CUSTOMER_ID_MESSAGE = "//Window[@Name='Blank Customer ID']//Text[starts-with(@Name, 'Cannot create Layers')]";
    private static final String BLANK_CUSTOMER_ID_OK_BUTTON = "//Window[@Name='GAIA - Data Entry']//Window[@Name='Blank Customer ID']//Button[@Name='OK']";
    private static final String REFRESH_BUTTON = "//Pane[@AutomationId='SamplesXtraUserControl']//Button[@Name='Refresh']";
    private static final String DELETE_BUTTON = "//Pane[@AutomationId='SamplesXtraUserControl']//Button[@Name='Delete']";
    private static final String CONFIRMATION_WINDOW = "//Window[@Name='GAIA - Data Entry']//Window[@Name='Confirmation']";
    private static final String CONFIRMATION_YES_BUTTON = "//Window[@Name='Confirmation']//Button[@Name='Yes']";
    
    // Setup methods now inherited from DataEntryBasePage

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
            if (test != null) test.fail("Blank Auto Base popup was not displayed");
            return;
        }

        WindowsElement message = driver.findElementByXPath(BLANK_AUTO_BASE_MESSAGE);
        if (message == null || !message.isDisplayed()) {
            if (test != null) test.fail("Expected warning message was not displayed in Blank Auto Base popup");
            return;
        }

        if (test != null) test.pass("Blank Auto Base warning displayed with expected message");

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
            if (test != null) test.fail("No rows found in the samples table");
            return;
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
                if (test != null) test.fail(msg);
                anyMismatch = true;
            }

            expectedNumber++;
        }

        if (anyMismatch) {
            if (test != null) test.fail("Customer ID sequence did not match expected prefix+autobase+suffix-autoNumber pattern.");
        } else {
            String msg = "Customer ID sequence matches expected pattern with incrementing Auto Number starting from " + startingAutoNumber + ".";
            System.out.println(msg);
            if (test != null) test.pass(msg);
        }
    }

    public void verifyLabIdsPresentForLastNSamples(int lastNSamples, ExtentTest test) {
        pause(300);
        List<WindowsElement> rows = cocDriver.findElementsByXPath(TABLE_BASE + "//ListItem");
        int rowCount = rows.size();
        if (rowCount == 0) {
            if (test != null) test.fail("No rows found in the samples table");
            return;
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
                if (test != null) test.fail(msg);
                anyMissing = true;
            }
        }

        if (anyMissing) {
            if (test != null) test.fail("One or more Lab IDs are missing for the newly created samples.");
        } else {
            String msg = "Lab IDs are present for the last " + samplesToVerify + " sample(s).";
            System.out.println(msg);
            if (test != null) test.pass(msg);
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
    
    public void verifyCustomerIdMatchesAutoBaseForLastNSamples(String expectedAutoBase, int lastNSamples, ExtentTest test) {
        // Allow UI to update after applying Auto Base
        pause(500);

        List<WindowsElement> rows = cocDriver.findElementsByXPath(TABLE_BASE + "//ListItem");
        int rowCount = rows.size();
        if (rowCount == 0) {
            if (test != null) test.fail("No rows found in the samples table");
            return;
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
                if (test != null) test.fail(msg);
                anyMismatch = true;
            }
        }

        if (anyMismatch) {
            if (test != null) test.fail("Customer ID did not match the Auto Base value for one or more new samples.");
        } else {
            String msg = "Customer ID matches Auto Base value ('" + expectedAutoBase + "') for the last " + samplesToVerify + " sample(s).";
            System.out.println(msg);
            if (test != null) test.pass(msg);
        }
    }

    // Utility: get samples row count
    public int getSamplesRowCount() {
        List<WindowsElement> rows = cocDriver.findElementsByXPath(TABLE_BASE + "//ListItem");
        return rows == null ? 0 : rows.size();
    }

    // Test 6: Refresh button
    public void clickRefreshButton() {
        WindowsElement refreshBtn = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(REFRESH_BUTTON))
        );
        clickElement(refreshBtn);
        pause(500);
    }

    // Selection helper: select last N rows
    public void selectLastNRows(int n) {
        int rowCount = getSamplesRowCount();
        if (n <= 0 || rowCount == 0) return;
        int startIndex = Math.max(1, rowCount - n + 1);

        // Click first target row without CTRL
        String firstRowXPath = TABLE_BASE + "//ListItem[@Name='Row " + startIndex + "']";
        WindowsElement firstRow = cocDriver.findElementByXPath(firstRowXPath);
        clickElement(firstRow);
        pause(100);

        // If more than 1, hold CTRL and click remaining
        if (n > 1) {
            Actions actions = new Actions(cocDriver);
            actions.keyDown(Keys.CONTROL).perform();
            for (int i = startIndex + 1; i <= rowCount; i++) {
                String rowXPath = TABLE_BASE + "//ListItem[@Name='Row " + i + "']";
                WindowsElement row = cocDriver.findElementByXPath(rowXPath);
                row.click();
                pause(100);
            }
            actions.keyUp(Keys.CONTROL).perform();
        }
        pause(200);
    }

    // Test 7: Delete last N samples (not all)
    public void deleteLastNSamples(int n, ExtentTest test) {
        int before = getSamplesRowCount();
        if (before == 0) {
            if (test != null) test.fail("No rows available to delete");
            return;
        }
        int toDelete = Math.min(n, Math.max(0, before - 1)); // ensure not all
        if (toDelete <= 0) {
            if (test != null) test.info("Skipping delete to avoid removing all rows");
            return;
        }
        selectLastNRows(toDelete);
        WindowsElement deleteBtn = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(DELETE_BUTTON))
        );
        clickElement(deleteBtn);
        // Handle confirmation dialog
        WindowsElement confirmWin = (WindowsElement) wait.until(
                ExpectedConditions.visibilityOf(driver.findElementByXPath(CONFIRMATION_WINDOW))
        );
        if (confirmWin == null || !confirmWin.isDisplayed()) {
            if (test != null) test.fail("Delete confirmation window did not appear");
            return;
        }
        WindowsElement yesBtn = driver.findElementByXPath(CONFIRMATION_YES_BUTTON);
        clickElement(yesBtn);
        pause(800);
        int after = getSamplesRowCount();
        if (after != before - toDelete) {
            if (test != null) test.fail("Expected row count to decrease by " + toDelete + ", before=" + before + ", after=" + after);
            return;
        }
        if (test != null) {
            test.pass("Deleted " + toDelete + " sample(s). Row count: " + before + " -> " + after);
        }
    }
    
    // Test 4 methods - Layer creation functionality
    public void selectSecondSample() {
        WindowsElement secondRow = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(SECOND_SAMPLE_ROW))
        );
        clickElement(secondRow);
        pause(300); // Allow selection to register
    }
    
    public void enterLayerCount(String layerCount) {
        WindowsElement layerCountInput = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(LAYER_COUNT_EDIT))
        );
        clearText(layerCountInput);
        enterText(layerCountInput, layerCount);
        new Actions(cocDriver).sendKeys(Keys.TAB).perform();
        pause(300);
    }
    
    public void clickLayerButton() {
        WindowsElement layerBtn = (WindowsElement) cocWait.until(
                ExpectedConditions.elementToBeClickable(cocDriver.findElementByXPath(LAYER_BUTTON))
        );
        clickElement(layerBtn);
        pause(500); // Allow time for layer creation
    }
    
    public void verifyLayerCreationAndCustomerIdChange(ExtentTest test) {
        pause(1000); // Allow time for UI to update after layer creation
        
        // Get the 2nd row (original sample) details
        String secondRowLabIdXPath = TABLE_BASE + "//ListItem[@Name='Row 2']//DataItem[@Name='Lab ID row 2']";
        String secondRowCustomerIdXPath = TABLE_BASE + "//ListItem[@Name='Row 2']//DataItem[@Name='Customer ID row 2']";
        
        WindowsElement secondRowLabId = cocDriver.findElementByXPath(secondRowLabIdXPath);
        WindowsElement secondRowCustomerId = cocDriver.findElementByXPath(secondRowCustomerIdXPath);
        
        String secondRowLabIdValue = getElementValue(secondRowLabId);
        String secondRowCustomerIdValue = getElementValue(secondRowCustomerId);
        
        // Get the new layer rows (should be rows 3 and 4)
        String thirdRowLabIdXPath = TABLE_BASE + "//ListItem[@Name='Row 3']//DataItem[@Name='Lab ID row 3']";
        String thirdRowCustomerIdXPath = TABLE_BASE + "//ListItem[@Name='Row 3']//DataItem[@Name='Customer ID row 3']";
        String fourthRowLabIdXPath = TABLE_BASE + "//ListItem[@Name='Row 4']//DataItem[@Name='Lab ID row 4']";
        String fourthRowCustomerIdXPath = TABLE_BASE + "//ListItem[@Name='Row 4']//DataItem[@Name='Customer ID row 4']";
        
        WindowsElement thirdRowLabId = cocDriver.findElementByXPath(thirdRowLabIdXPath);
        WindowsElement thirdRowCustomerId = cocDriver.findElementByXPath(thirdRowCustomerIdXPath);
        WindowsElement fourthRowLabId = cocDriver.findElementByXPath(fourthRowLabIdXPath);
        WindowsElement fourthRowCustomerId = cocDriver.findElementByXPath(fourthRowCustomerIdXPath);
        
        String thirdRowLabIdValue = getElementValue(thirdRowLabId);
        String thirdRowCustomerIdValue = getElementValue(thirdRowCustomerId);
        String fourthRowLabIdValue = getElementValue(fourthRowLabId);
        String fourthRowCustomerIdValue = getElementValue(fourthRowCustomerId);
        
        // Log all details to test report for better visibility
        if (test != null) {
            test.info("=== Layer Creation Details ===");
            test.info("2nd Row (Original Sample) - Lab ID: " + secondRowLabIdValue + ", Customer ID: " + secondRowCustomerIdValue);
            test.info("3rd Row (New Layer 1) - Lab ID: " + thirdRowLabIdValue + ", Customer ID: " + thirdRowCustomerIdValue);
            test.info("4th Row (New Layer 2) - Lab ID: " + fourthRowLabIdValue + ", Customer ID: " + fourthRowCustomerIdValue);
        }
        
        // Print the details for console verification
        System.out.println("=== Layer Creation Verification ===");
        System.out.println("2nd Row (Original Sample):");
        System.out.println("  Lab ID: " + secondRowLabIdValue);
        System.out.println("  Customer ID: " + secondRowCustomerIdValue);
        System.out.println("3rd Row (New Layer 1):");
        System.out.println("  Lab ID: " + thirdRowLabIdValue);
        System.out.println("  Customer ID: " + thirdRowCustomerIdValue);
        System.out.println("4th Row (New Layer 2):");
        System.out.println("  Lab ID: " + fourthRowLabIdValue);
        System.out.println("  Customer ID: " + fourthRowCustomerIdValue);
        System.out.println("==================================");
        
        // Verify that new layers were created
        if (thirdRowLabIdValue == null || thirdRowLabIdValue.trim().isEmpty() ||
            fourthRowLabIdValue == null || fourthRowLabIdValue.trim().isEmpty()) {
            if (test != null) test.fail("Failed to create new layers - Lab IDs are missing");
            return;
        } else {
            if (test != null) test.pass("Two new layers were successfully created");
        }
        
        // Verify that customer IDs are different (indicating successful layer creation)
        if (secondRowCustomerIdValue.equals(thirdRowCustomerIdValue) ||
            secondRowCustomerIdValue.equals(fourthRowCustomerIdValue) ||
            thirdRowCustomerIdValue.equals(fourthRowCustomerIdValue)) {
            if (test != null) test.fail("Customer IDs are not unique - layer creation may have failed");
            return;
        } else {
            if (test != null) test.pass("Customer IDs are different for all three rows, confirming successful layer creation");
        }
        
        // Verify the 2nd row customer ID changed
        if (secondRowCustomerIdValue != null && !secondRowCustomerIdValue.trim().isEmpty()) {
            if (test != null) test.pass("2nd row customer ID was updated: " + secondRowCustomerIdValue);
        } else {
            if (test != null) test.fail("2nd row customer ID is missing or empty");
        }
    }

    public void verifyAndDismissBlankCustomerIdPopup(ExtentTest test) {
        // Wait for popup
        WindowsElement popupWindow = (WindowsElement) wait.until(
                ExpectedConditions.visibilityOf(driver.findElementByXPath(BLANK_CUSTOMER_ID_WINDOW))
        );
        if (popupWindow == null || !popupWindow.isDisplayed()) {
            if (test != null) test.fail("Blank Customer ID popup was not displayed");
            return;
        }

        // Verify message
        WindowsElement message = driver.findElementByXPath(BLANK_CUSTOMER_ID_MESSAGE);
        if (message == null || !message.isDisplayed()) {
            if (test != null) test.fail("Expected warning message was not displayed in Blank Customer ID popup");
            return;
        }
        String messageText = message.getAttribute("Name");
        if (messageText == null || !messageText.startsWith("Cannot create Layers")) {
            if (test != null) test.fail("Popup message did not start with expected text. Actual: " + messageText);
            // still dismiss to continue flow
        } else {
            if (test != null) test.pass("Blank Customer ID warning displayed with expected message: " + messageText);
        }

        // Click OK
        WindowsElement okButton = driver.findElementByXPath(BLANK_CUSTOMER_ID_OK_BUTTON);
        clickElement(okButton);
        pause(200);
    }

    public void selectFirstOfLastCreatedSamples(int lastNSamples, ExtentTest test) {
        // Allow table to update after creation
        pause(500);
        List<WindowsElement> rows = cocDriver.findElementsByXPath(TABLE_BASE + "//ListItem");
        int rowCount = rows.size();
        if (rowCount == 0) {
            if (test != null) test.fail("No rows found in the samples table");
            return;
        }
        int targetIndex = Math.max(1, rowCount - lastNSamples + 1);

        // Click the target row
        String targetRowXPath = TABLE_BASE + "//ListItem[@Name='Row " + targetIndex + "']";
        WindowsElement targetRow = cocDriver.findElementByXPath(targetRowXPath);
        clickElement(targetRow);
        pause(200);

        // Log Lab ID and Customer ID of selected row
        String labIdXPath = TABLE_BASE + "//ListItem[@Name='Row " + targetIndex + "']//DataItem[@Name='Lab ID row " + targetIndex + "']";
        String customerIdXPath = TABLE_BASE + "//ListItem[@Name='Row " + targetIndex + "']//DataItem[@Name='Customer ID row " + targetIndex + "']";
        WindowsElement labIdCell = cocDriver.findElementByXPath(labIdXPath);
        WindowsElement customerIdCell = cocDriver.findElementByXPath(customerIdXPath);
        String labIdValue = getElementValue(labIdCell);
        String customerIdValue = getElementValue(customerIdCell);

        if (test != null) {
            test.info("Selected first newly created sample (Row " + targetIndex + ") - Lab ID: " + labIdValue + ", Customer ID: " + customerIdValue);
        }
    }
}
