package gaia.pages.Global;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import gaia.utils.Driver.DriverManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class BasePage {
    
    protected WindowsDriver<WindowsElement> driver;
    protected WindowsDriver<WindowsElement> cocDriver;
    protected WindowsDriver<WindowsElement> batchRunDriver;
    protected WebDriverWait wait;
    protected WebDriverWait cocWait;
    protected WebDriverWait batchRunWait;
    
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.cocDriver = DriverManager.getCocDriver();
        this.wait = DriverManager.getWait();
        this.cocWait = DriverManager.getCocWait();
        this.batchRunDriver = DriverManager.getBatchRunDriver();
        this.batchRunWait = DriverManager.getBatchRunWait();
    }
    
    // Common utility methods
    protected void pause(long millis) {
        long capped = Math.min(millis, 100); // cap pauses to 100ms to speed tests
        if (capped <= 0) return;
        try {
            Thread.sleep(capped);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    protected void clickElement(WindowsElement element) {
        element.click();
    }

    protected void clickElement(RemoteWebElement element) {
        element.click();
    }

    protected void doubleClickElement(WindowsElement element) {
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
    }
    
    protected void enterText(WindowsElement element, String text) {
        element.click();
        new Actions(cocDriver).sendKeys(text).perform();
    }
    
    protected void clearText(WindowsElement element) {
        element.click();
        new Actions(cocDriver)
            .keyDown(Keys.CONTROL)
            .sendKeys("a")
            .keyUp(Keys.CONTROL)
            .sendKeys(Keys.DELETE)
            .perform();
    }
    
    protected String getElementValue(WindowsElement element) {
        return ((RemoteWebElement) element).getAttribute("Value.Value");
    }
    
    public String captureScreenshot(String screenshotName) {
        try {
            WindowsDriver<?> driverForShot = this.cocDriver != null ? this.cocDriver : this.driver;
            if (driverForShot == null) {
                System.err.println("Screenshot skipped: both drivers are null");
                return null;
            }
            File src = driverForShot.getScreenshotAs(OutputType.FILE);
            String dest = "test-output/screenshots/" + screenshotName + ".png";
            File target = new File(dest);
            target.getParentFile().mkdirs(); // Create folders if needed
            Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return dest;
        } catch (Exception e) {
            System.err.println("Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }
}
