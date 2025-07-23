package gaia;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppTest {
    public static void main(String[] args) throws Exception {
        // Step 1: Launch the EXE
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\BS451\\Desktop\\GAIA Publish\\setup.exe");
        pb.start();

        // Step 2: Wait for the installer to launch
        Thread.sleep(5000); // Adjust if needed

        // Step 3: Connect to Desktop session
        DesiredCapabilities rootCapabilities = new DesiredCapabilities();
        rootCapabilities.setCapability("app", "Root");
        WindowsDriver<WindowsElement> rootDriver = new WindowsDriver<WindowsElement>(new URL("http://127.0.0.1:4723"), rootCapabilities);
        rootDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Step 4: Find your window by its name
        // You may need to inspect the actual title with WinSpy or inspect.exe
        String windowTitle = "setup"; // Replace with actual title
        WindowsElement  windowElement = rootDriver.findElementByName(windowTitle);
        String nativeWindowHandle = windowElement.getAttribute("NativeWindowHandle");

        // Convert the hex handle to decimal (WinAppDriver requires decimal)
        int hwnd = Integer.parseInt(nativeWindowHandle);
        String hexHandle = Integer.toHexString(hwnd);

        // Step 5: Attach to that window
        DesiredCapabilities appCapabilities = new DesiredCapabilities();
        appCapabilities.setCapability("appTopLevelWindow", hexHandle);
        WindowsDriver<WindowsElement> driver = new WindowsDriver<WindowsElement>(new URL("http://127.0.0.1:4723"), appCapabilities);

        // Now you're attached to the running installer window
        Thread.sleep(10000); // Demo purpose
        driver.quit();
    }
}
