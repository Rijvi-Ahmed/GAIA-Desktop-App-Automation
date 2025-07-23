package gaia;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class calculator {
    public static void main(String[] args) throws Exception {
        String appPath = "C:\\Users\\BS451\\AppData\\Local\\Apps\\2.0\\J0N77Q73.BQO\\AWX4TPKD.0XL\\gaia..tion_b38d56f1d736aedc_0000.0001_1538b525e5562486\\GAIA.exe";

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Windows");
        cap.setCapability("deviceName", "WindowsPC");
        cap.setCapability("app", appPath);

        WindowsDriver<WindowsElement> driver = new WindowsDriver<>(new URL("http://127.0.0.1:4723"), cap);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Thread.sleep(5000);

        // Click the Close button
        WindowsElement closeButton = driver.findElementByXPath("//Window[@Name=\"GAIA - Main Menu\"]//Button[@Name=\"Close\"]");
        closeButton.click();

        // Wait for the confirmation dialog to appear
        Thread.sleep(1000);

        // Click the Yes button to confirm close
        WindowsElement yesButton = driver.findElementByXPath("//Button[@Name='Yes']");
        yesButton.click();

        // Close the driver after confirmation
        driver.quit();
    }
}
