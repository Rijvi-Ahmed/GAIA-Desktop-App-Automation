package gaia.utils.Driver;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.Socket;

public class DriverManager {
    
    private static WindowsDriver<WindowsElement> driver;
    private static WindowsDriver<WindowsElement> cocDriver;
    private static WindowsDriver<WindowsElement> batchRunDriver;
    private static WebDriverWait wait;
    private static WebDriverWait cocWait;
    private static WebDriverWait batchRunWait;
    
    // Paths - defined as constants
    private static final String WIN_APP_DRIVER_PATH = "C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe";
    private static final String GAIA_APP_PATH = "C:\\Users\\BS451\\AppData\\Local\\Apps\\2.0\\HQ18D2J7.N71\\G8H7KOQ2.72Q\\gaia..tion_7a24f77242120513_0000.0001_d5d36cc29ad5ae3e\\GAIA.exe";

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

        // Attach to Batch Run window once
        DesiredCapabilities brCap = new DesiredCapabilities();
        brCap.setCapability("appTopLevelWindow", driver.getWindowHandle());
        batchRunDriver = new WindowsDriver<>(URI.create("http://127.0.0.1:4723").toURL(), brCap);
        batchRunWait = new WebDriverWait(batchRunDriver, 15);

    }

    public static void quitDrivers() {
        if (cocDriver != null) {
            cocDriver.quit();
        }
        if (batchRunDriver != null) {
            batchRunDriver.quit();
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

    public static WindowsDriver<WindowsElement> getBatchRunDriver() {
        return batchRunDriver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public static WebDriverWait getCocWait() {
        return cocWait;
    }

    public static WebDriverWait getBatchRunWait() {
        return batchRunWait;
    }


    // Start WinAppDriver
    private static void startWinAppDriver(String path) throws IOException {
        File file = new File(path);
        if (!file.exists())
            throw new IOException("WinAppDriver not found");

        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "start", "\"\"", "\"" + path + "\"");
        pb.directory(file.getParentFile());
        pb.start();

        // Wait up to ~3s for WinAppDriver to listen on 4723 without hard sleeping
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 3000) {
            try (Socket s = new Socket("127.0.0.1", 4723)) {
                break; // port is open
            } catch (IOException ignored) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
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
