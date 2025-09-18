// package gaia.tests.Customers;

// import com.aventstack.extentreports.ExtentTest;
// import org.testng.annotations.Test;
// import gaia.pages.CustomerPage;
// import gaia.tests.BaseTest;

// public class CustomerTest extends BaseTest {
    
//     private CustomerPage customerPage;
    
//     public CustomerTest() {
//         customerPage = new CustomerPage();
//     }
    
//     @Test
//     public void navigateToCustomerMenu() throws Exception {
//         long startTime = System.currentTimeMillis();
//         ExtentTest test = createTest("GAIA - Navigate to Customer Menu");

//         try {
//             test.pass("Using existing GAIA session");
            
//             // Navigate to customer menu
//             customerPage.navigateToCustomerMenu();
//             test.pass("Successfully navigated to Customer Menu");
            
//             test.pass("Customer navigation test completed successfully");

//         } catch (Exception e) {
//             // Capture screenshot
//             customerPage.captureScreenshot("Failure_" + System.currentTimeMillis());
//             // Log the error in the report
//             test.fail("Test failed: " + e.getMessage());
//             throw e;
//         }

//         long endTime = System.currentTimeMillis();
//         System.out.println("Customer navigation test completed in " + (endTime - startTime) / 1000.0 + " seconds");
//     }
    
//     // Add more customer-related test methods as needed
//     // Example test methods:
//     // @Test
//     // public void createCustomer() { ... }
//     // @Test
//     // public void searchCustomer() { ... }
//     // @Test
//     // public void editCustomer() { ... }
//     // @Test
//     // public void deleteCustomer() { ... }
// }
