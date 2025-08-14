package gaia.utils.Driver;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class DriverManager {
    
    private static WindowsDriver<WindowsElement> driver;
    private static WindowsDriver<WindowsElement> cocDriver;
    private static WebDriverWait wait;
    private static WebDriverWait cocWait;
    
    // Paths - defined as constants
    private static final String WIN_APP_DRIVER_PATH = "C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe";
    private static final String GAIA_APP_PATH = "C:\\Users\\BS451\\AppData\\Local\\Apps\\2.0\\J0N77Q73.BQO\\AWX4TPKD.0XL\\gaia..tion_c0da4153e47775bf_0000.0001_ae9c7aa7ec161f36\\GAIA.exe";

    public static void initializeDrivers() throws Exception {
        // Start WinAppDriver once
        startWinAppDriver(WIN_APP_DRIVER_PATH);

        // Launch GAIA app once
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Windows");
        cap.setCapability("deviceName", "WindowsPC");
        cap.setCapability("app", GAIA_APP_PATH);
        driver = new WindowsDriver<>(URI.create("http://127.0.0.1:4723").toURL(), cap);
        wait = new WebDriverWait(driver, 15);

        // Attach to COC window once
        DesiredCapabilities cocCap = new DesiredCapabilities();
        cocCap.setCapability("appTopLevelWindow", driver.getWindowHandle());
        cocDriver = new WindowsDriver<>(URI.create("http://127.0.0.1:4723").toURL(), cocCap);
        cocWait = new WebDriverWait(cocDriver, 15);
    }

    public static void quitDrivers() {
        if (cocDriver != null) {
            cocDriver.quit();
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public static WindowsDriver<WindowsElement> getDriver() {
        return driver;
    }

    public static WindowsDriver<WindowsElement> getCocDriver() {
        return cocDriver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public static WebDriverWait getCocWait() {
        return cocWait;
    }

    public static void attachCocToMainWindow() throws Exception {
        if (driver == null) {
            throw new IllegalStateException("Main driver is not initialized");
        }
        DesiredCapabilities cocCap = new DesiredCapabilities();
        cocCap.setCapability("appTopLevelWindow", driver.getWindowHandle());
        cocDriver = new WindowsDriver<>(URI.create("http://127.0.0.1:4723").toURL(), cocCap);
        cocWait = new WebDriverWait(cocDriver, 15);
    }

    // Start WinAppDriver
    private static void startWinAppDriver(String path) throws IOException {
        File file = new File(path);
        if (!file.exists())
            throw new IOException("WinAppDriver not found");

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
    public static void stopWinAppDriver() {
        try {
            new ProcessBuilder("taskkill", "/F", "/IM", "WinAppDriver.exe").start().waitFor();
        } catch (Exception e) {
            System.err.println("Error stopping WinAppDriver: " + e.getMessage());
        }
    }

    // Kill GAIA process
    public static void killProcess(String processName) {
        try {
            new ProcessBuilder("taskkill", "/F", "/IM", processName + ".exe").start().waitFor();
        } catch (Exception e) {
            System.err.println("Error killing process " + processName + ": " + e.getMessage());
        }
    }
}
