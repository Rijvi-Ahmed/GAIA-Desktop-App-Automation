package gaia.utils.Reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.io.IOException;

public class ReportManager {
    
    private static ExtentReports extent;
    
    public static void initializeReports() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/GAIA_Report.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }
    
    public static ExtentReports getExtent() {
        return extent;
    }
    
    public static ExtentTest createTest(String testName) {
        return extent.createTest(testName);
    }
    
    public static void flushReports() {
        if (extent != null) {
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
    }
}
