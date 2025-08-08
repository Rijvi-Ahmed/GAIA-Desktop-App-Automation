package gaia;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DataEntryReports {

    @Test
    public void runDataEntryTest() throws Exception {
        long startTime = System.currentTimeMillis();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/GAIA_Report.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        ExtentTest test = extent.createTest("GAIA Desktop Automation Test");

        String winAppDriverPath = "C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe";
        String gaiaAppPath = "C:\\Users\\BS451\\AppData\\Local\\Apps\\2.0\\J0N77Q73.BQO\\AWX4TPKD.0XL\\gaia..tion_b38d56f1d736aedc_0000.0001_1538b525e5562486\\GAIA.exe"; // Replace with full path

        WindowsDriver<WindowsElement> driver = null;
        WindowsDriver<WindowsElement> cocDriver = null;

        try {
            // Start WinAppDriver
            startWinAppDriver(winAppDriverPath);
            test.pass("WinAppDriver started");

            // Launch GAIA App
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("platformName", "Windows");
            cap.setCapability("deviceName", "WindowsPC");
            cap.setCapability("app", gaiaAppPath);

            driver = new WindowsDriver<>(URI.create("http://127.0.0.1:4723").toURL(), cap);
            WebDriverWait wait = new WebDriverWait(driver, 15);

            // Click Data Entry
            WindowsElement dataEntry = (WindowsElement) wait.until(ExpectedConditions.elementToBeClickable(
                    driver.findElementByXPath("//List[@AutomationId='tileControl']//ListItem[@Name='Data Entry']")));
            Assert.assertTrue(dataEntry.isDisplayed(), "Data Entry button should be visible");
            dataEntry.click();
            pause(1000);
            test.pass("Clicked 'Data Entry'");

            // Click New
            WindowsElement newBtn = (WindowsElement) wait.until(ExpectedConditions.elementToBeClickable(
                    driver.findElementByXPath("//Button[@Name='New']")));
            Assert.assertTrue(newBtn.isEnabled(), "New button should be enabled");
            newBtn.click();
            pause(1000);
            test.pass("Clicked 'New'");

            // Attach to COC window
            DesiredCapabilities cocCap = new DesiredCapabilities();
            cocCap.setCapability("appTopLevelWindow", driver.getWindowHandle());
            cocDriver = new WindowsDriver<>(URI.create("http://127.0.0.1:4723").toURL(), cocCap);
            WebDriverWait cocWait = new WebDriverWait(cocDriver, 15);

            // Open Customer Dropdown
            WindowsElement openDropdown = (WindowsElement) cocWait.until(ExpectedConditions.elementToBeClickable(
                    cocDriver.findElementByXPath("//Edit[@AutomationId='CustomerLookUpEdit']//Button[@Name='Open']")));
            openDropdown.click();
            pause(1000);
            test.pass("Opened customer dropdown");

            // Select Customer
            WindowsElement customer = (WindowsElement) cocWait.until(ExpectedConditions.elementToBeClickable(
                    cocDriver.findElementByXPath("//Pane[@AutomationId='SearchEditLookUpPopup']//ListItem[@Name='Row 3']")));
            customer.click();
            pause(1000);
            test.pass("Selected 'Row 3' customer");

            // Save
            WindowsElement saveBtn = cocDriver.findElementByXPath("//Button[@Name='Save']");
            Assert.assertTrue(saveBtn.isDisplayed(), "Save button should be visible");
            saveBtn.click();
            pause(1000);
            test.pass("Clicked 'Save'");

            // Go to Samples tab
            WindowsElement samplesTab = cocDriver.findElementByXPath("//TabItem[@Name='Samples']");
            samplesTab.click();
            pause(1000);
            test.pass("Navigated to 'Samples' tab");

            // Enter sample count
            WindowsElement sampleInput = cocDriver.findElementByXPath("//Edit[@Name='Number of samples to create.']");
            sampleInput.click();
            new Actions(cocDriver).sendKeys("3").perform();
            pause(1000);
            test.pass("Entered sample quantity");

            // Click New Sample(s)
            WindowsElement newSamplesBtn = cocDriver.findElementByXPath("//Button[@Name='New Sample(s)']");
            newSamplesBtn.click();
            pause(1000);
            test.pass("Clicked 'New Sample(s)'");

            // Close COC
            WindowsElement closeCOC = cocDriver.findElementByXPath("//TitleBar[@AutomationId='TitleBar']//Button[@AutomationId='Maximize-Restore']");
            closeCOC.click();
            pause(1000);
            cocDriver.quit();
            test.pass("Closed COC window");

            // Close GAIA app
            WindowsElement closeMain = (WindowsElement) wait.until(ExpectedConditions.elementToBeClickable(
                    driver.findElementByXPath("//Button[@Name='Close']")));
            closeMain.click();
            pause(1000);
            WindowsElement confirmExit = (WindowsElement) wait.until(ExpectedConditions.elementToBeClickable(
                    driver.findElementByXPath("//Window[contains(@Name,'Exit') or contains(@Name,'GAIA')]/Button[@Name='Yes']")));
            confirmExit.click();
            pause(1000);        
            driver.quit();
            test.pass("Closed GAIA app");

            // Cleanup
            killProcess("GAIA");
            stopWinAppDriver();
            test.pass("Stopped WinAppDriver and killed GAIA process");

        } catch (Exception e) {
            String screenshotPath = captureScreenshot(driver != null ? driver : cocDriver, "Failure_" + System.currentTimeMillis());
            if (screenshotPath != null) {
                test.fail("Test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else {
                test.fail("Test failed (screenshot unavailable): " + e.getMessage());
            }

            if (driver != null) driver.quit();
            if (cocDriver != null) cocDriver.quit();
            stopWinAppDriver();
            killProcess("GAIA");
            throw e;
        } finally {
            extent.flush();
            System.out.println("Report generated at: test-output/GAIA_Report.html");

            // Automatically open report in default browser
            try {
                File htmlFile = new File("test-output/GAIA_Report.html");
                if (htmlFile.exists()) {
                    java.awt.Desktop.getDesktop().browse(htmlFile.toURI());
                } else {
                    System.err.println("Report file not found: " + htmlFile.getAbsolutePath());
                }
            } catch (IOException e) {
                    System.err.println("Failed to open report in browser: " + e.getMessage());
                }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Test completed in " + (endTime - startTime) / 1000.0 + " seconds");
    }

    // Start WinAppDriver
    private void startWinAppDriver(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) throw new IOException("WinAppDriver not found");

        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "start", "\"\"", "\"" + path + "\"");
        pb.directory(file.getParentFile());
        pb.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Stop WinAppDriver
    private void stopWinAppDriver() {
        try {
            new ProcessBuilder("taskkill", "/F", "/IM", "WinAppDriver.exe").start().waitFor();
        } catch (Exception e) {
            System.err.println("Error stopping WinAppDriver: " + e.getMessage());
        }
    }

    // Kill GAIA process
    private void killProcess(String processName) {
        try {
            new ProcessBuilder("taskkill", "/F", "/IM", processName + ".exe").start().waitFor();
        } catch (Exception e) {
            System.err.println("Error killing process " + processName + ": " + e.getMessage());
        }
    }

    // Capture Screenshot
    private String captureScreenshot(WindowsDriver<?> driver, String screenshotName) {
        try {
            File src = driver.getScreenshotAs(OutputType.FILE);
            String dest = "test-output/screenshots/" + screenshotName + ".png";
            File target = new File(dest);
            target.getParentFile().mkdirs(); // Create folders if needed
            Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return dest;
        } catch (IOException e) {
            System.err.println("Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }

    //Time need for each script
    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}
