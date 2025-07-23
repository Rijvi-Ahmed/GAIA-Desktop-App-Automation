package gaia;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.net.URI;
public class DataEntry {
    public static void main(String[] args) throws Exception {
        String appPath = "C:\\Users\\BS451\\AppData\\Local\\Apps\\2.0\\J0N77Q73.BQO\\AWX4TPKD.0XL\\gaia..tion_b38d56f1d736aedc_0000.0001_1538b525e5562486\\GAIA.exe";

        // Launch GAIA
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Windows");
        cap.setCapability("deviceName", "WindowsPC");
        cap.setCapability("app", appPath);

        WindowsDriver<WindowsElement> driver = new WindowsDriver<>(URI.create("http://127.0.0.1:4723").toURL(), cap);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Click the Data Entry button
        wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElementByXPath("//List[@AutomationId=\"tileControl\"]//ListItem[@Name=\"Data Entry\"]"))).click();

        // Click the New button
        wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElementByXPath("//Button[@Name=\"New\"]"))).click();

        // Attach to the COC window
        DesiredCapabilities cocCap = new DesiredCapabilities();
        cocCap.setCapability("appTopLevelWindow", driver.getWindowHandle());
        WindowsDriver<WindowsElement> cocDriver = new WindowsDriver<>(URI.create("http://127.0.0.1:4723").toURL(), cocCap);
        WebDriverWait cocWait = new WebDriverWait(cocDriver, 10);

        // Open customer dropdown
        cocWait.until(ExpectedConditions.elementToBeClickable(
                cocDriver.findElementByXPath("//Edit[@AutomationId=\"CustomerLookUpEdit\"]//Button[@Name=\"Open\"]"))).click();

        // Select a customer
        cocWait.until(ExpectedConditions.elementToBeClickable(
                cocDriver.findElementByXPath("//Pane[@AutomationId=\"SearchEditLookUpPopup\"]//ListItem[@Name=\"Row 3\"]//DataItem[@Name=\"Customer row 3\"]"))).click();

        // Click Save
        cocDriver.findElementByXPath("//Button[@Name=\"Save\"]").click();

        // Go to Samples tab
        cocDriver.findElementByXPath("//TabItem[@Name='Samples']").click();

        // Enter number of samples
        WindowsElement sampleInput = cocDriver.findElementByXPath("//Edit[@Name='Number of samples to create.']");
        sampleInput.click();
        new Actions(cocDriver).sendKeys("3").perform();

        // Create samples
        cocDriver.findElementByXPath("//Button[@Name='New Sample(s)']").click();

        // Close the COC window (using maximize/restore as close workaround)
        cocDriver.findElementByXPath("//TitleBar[@AutomationId=\"TitleBar\"]//Button[@AutomationId=\"Maximize-Restore\"]").click();
        cocDriver.quit(); // End child window session

        // Back to GAIA Main window - Close app
        wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElementByXPath("//Button[@Name=\"Close\"]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElementByXPath("//Window[contains(@Name,'Exit') or contains(@Name,'GAIA')]/Button[@Name='Yes']"))).click();

        driver.quit(); // End GAIA main session
    }
}
