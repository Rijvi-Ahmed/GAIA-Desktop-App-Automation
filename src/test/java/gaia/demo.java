package gaia;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class demo {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("http://www.youtube.com");
        System.out.println("Hello, Gaia Group ID!");
        // This is a simple demo class to illustrate the package structure.
        // You can add more functionality here as needed.
        driver.quit();

        /*
        // Switch to the confirmation dialog window
        DesiredCapabilities confirmCap = new DesiredCapabilities();
        confirmCap.setCapability("appTopLevelWindow", driver.getWindowHandle());
        WindowsDriver<WindowsElement> confirmationDialog = new WindowsDriver<>(new URL("http://127.0.0.1:4723"),
                confirmCap); */
    }

}
