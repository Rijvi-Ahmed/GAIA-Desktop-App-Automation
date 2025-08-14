# GAIA Application - Page Object Model (POM) Structure

## Overview
This project has been refactored to use the Page Object Model (POM) design pattern for better maintainability, reusability, and organization of test automation code.

## Project Structure

```
src/test/java/gaia/
├── utils/                         # Utility classes
│   ├── DriverManager.java         # Driver initialization and management
│   ├── ReportManager.java         # ExtentReports management                                 
│   └── TestData/                  # Test data constants
│       └── CustomerTestData.java
│       └── DataEntryTestData.java                                
├── pages/                         # Page Object classes
│   ├── BasePage.java              # Common functionality for all pages
│   ├── DataEntryPage.java         # Data Entry page objects
│   └── CustomerPage.java          # Customer page objects
├── tests/                         # Test classes
│   ├── BaseTest.java              # Common setup/teardown for all tests
│   ├── DataEntry/                 # Data Entry test cases
│      └── sampleTest.java
│      └── DataEntryBaseTest.java     
│   └── Customers/                 # Customer test package
│       └── CustomerTest.java      # Customer test cases

## Key Features

### 1. Global Driver Management
- `driver` and `cocDriver` are managed globally through `DriverManager`
- Single session shared across all test methods
- Automatic cleanup in `@AfterClass`

### 2. Single Session Setup
- GAIA & WinAppDriver start once in `@BeforeClass`
- All `@Test` methods share the same session
- Faster execution and consistent state

### 3. Page Object Model
- **BasePage**: Common utility methods (click, enter text, screenshot, etc.)
- **DataEntryPage**: All data entry functionality encapsulated
- **CustomerPage**: Customer-related functionality (for future use)

### 4. Utility Classes
- **DriverManager**: Handles driver initialization, cleanup, and process management
- **ReportManager**: Manages ExtentReports initialization and reporting
- **TestData**: Centralized test data constants

### 5. Test Organization
- **BaseTest**: Common setup/teardown logic
- **DataEntryTest**: Contains the two data entry test cases
- **CustomerTest**: Customer-related tests (for future use)

## Test Cases

### Data Entry Tests
1. **runDataEntryTest1**: Sample creation with applying all available data
   - Uses auto number "1" + auto base "Test"
2. **runDataEntryTest2**: Sample creation with applying autobase number only
   - Uses empty auto number + auto base "Test"

Both tests:
- Fill Customer ID, Description, and Lab Notes columns
- Validate that all fields are properly filled
- Generate detailed ExtentReports

## Running Tests

### Prerequisites
- Maven installed
- WinAppDriver installed at: `C:\Program Files (x86)\Windows Application Driver\WinAppDriver.exe`
- GAIA application installed at the specified path

### Command Line Execution

#### Run All Tests
```bash
mvn test -DsuiteXmlFile=testng.xml
```

#### Run Data Entry Tests Only
```bash
mvn test -DsuiteXmlFile=testng-dataentry.xml
```

#### Run Customer Tests Only
```bash
mvn test -DsuiteXmlFile=testng-customers.xml
```

#### Run Specific Test Class
```bash
mvn test -Dtest=DataEntryTest
```

#### Run Specific Test Method
```bash
mvn test -Dtest=DataEntryTest#runDataEntryTest1
```

### IDE Execution
- Right-click on any test class and select "Run as TestNG Test"
- Right-click on any test method and select "Run as TestNG Test"
- Use TestNG plugin to run specific test suites

## TestNG XML Files

### testng.xml (Main Suite)
- Runs all tests (Data Entry + Customer)
- Use for full regression testing

### testng-dataentry.xml
- Runs only Data Entry tests
- Use for focused data entry testing

### testng-customers.xml
- Runs only Customer tests
- Use for customer functionality testing

## Benefits of POM Structure

### 1. Maintainability
- Page elements are centralized in page classes
- Changes to UI elements require updates in only one place
- Easy to locate and modify test logic

### 2. Reusability
- Page methods can be reused across multiple test cases
- Common functionality is shared through BasePage
- Test data is centralized in TestData class

### 3. Readability
- Test methods are clean and focused on business logic
- Page methods have descriptive names
- Clear separation of concerns

### 4. Scalability
- Easy to add new page objects for new functionality
- Simple to extend existing page objects
- Modular structure supports team development

## Adding New Test Categories

### 1. Create Page Object
```java
// src/test/java/gaia/pages/NewFeaturePage.java
public class NewFeaturePage extends BasePage {
    // Define page elements
    // Implement page methods
}
```

### 2. Create Test Class
```java
// src/test/java/gaia/tests/NewFeature/NewFeatureTest.java
public class NewFeatureTest extends BaseTest {
    private NewFeaturePage newFeaturePage;
    
    @Test
    public void testNewFeature() {
        // Test implementation
    }
}
```

### 3. Create TestNG XML
```xml
<!-- src/test/resources/testng-newfeature.xml -->
<suite name="GAIA New Feature Test Suite">
    <test name="New Feature Tests">
        <classes>
            <class name="gaia.tests.NewFeature.NewFeatureTest"/>
        </classes>
    </test>
</suite>
```

### 4. Add Test Data
```java
// src/test/java/gaia/utils/TestData.java
public static class NewFeatureData {
    // Add test data constants
}
```

## Reporting

### ExtentReports
- HTML reports generated in `test-output/GAIA_Report.html`
- Reports automatically open in browser after test completion
- Detailed test steps and screenshots on failure

### Screenshots
- Automatic screenshots on test failure
- Stored in `test-output/screenshots/`
- Named with timestamp for easy identification

## Troubleshooting

### Common Issues
1. **WinAppDriver not found**: Verify installation path in `DriverManager.java`
2. **GAIA app not found**: Update path in `DriverManager.java`
3. **Element not found**: Check XPath locators in page classes
4. **Test timeout**: Increase wait time in `DriverManager.java`

### Debug Mode
- Add `System.out.println()` statements in page methods
- Use ExtentReports for detailed logging
- Check screenshots for visual verification

## Migration from Old Structure

The old `SampleTest.java` has been deprecated. All functionality has been moved to the new POM structure:

- **Old**: Single large test class with all logic
- **New**: Organized POM structure with separate concerns

Benefits of migration:
- Better code organization
- Easier maintenance
- Improved reusability
- Enhanced scalability
