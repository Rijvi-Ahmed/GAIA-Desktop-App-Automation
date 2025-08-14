## GAIA Application Test Suite

This project automates GAIA desktop UI validation with WinAppDriver, TestNG, and the Page Object Model.

### Structure
- `src/test/java/gaia/tests/BaseTest.java`
  - Suite-level setup/teardown.
  - Initializes ExtentReports and WinAppDriver-driven sessions (`DriverManager.initializeDrivers()`), flushes reports, and cleans up processes.

- `src/test/java/gaia/tests/DataEntry/DataEntryBaseTest.java`
  - Test-class-level initialization for Data Entry scenarios.
  - Creates a shared `DataEntryPage` once per class and calls `dataEntryPage.setupDataEntrySession()` to prepare the GAIA Data Entry screen with a selected customer.
  - Intended for reuse by Data Entry-related test classes.

- `src/test/java/gaia/tests/DataEntry/SampleTest.java`
  - Extends `DataEntryBaseTest` and contains three test cases:
    - Test 1: Full sample creation; verifies Lab IDs exist, fields are filled, and `Customer ID` follows `prefix + autobase + suffix + '-' + autoNumber` with auto number incrementing by 1 per row.
    - Test 2: Auto Base propagation; verifies the newly created samples have `Customer ID` equal to the entered Auto Base value.
    - Test 3: Blank Auto Base warning; verifies the warning dialog appears after clicking Auto Base with no value and dismisses it.

### Page Objects
- `src/test/java/gaia/pages/BasePage.java`
  - Common driver access (`driver`, `cocDriver`) and actions (`clickElement`, `enterText`, `clearText`, `captureScreenshot`, etc.).

- `src/test/java/gaia/pages/DataEntryPage.java`
  - Encapsulates the Data Entry UI: navigation, input, table interactions, and validations.
  - Key methods used by tests:
    - Session setup: `setupDataEntrySession()`.
    - Samples tab: `navigateToSamplesTab()`, `enterSampleCount`, `enterCustomerIdPrefix`, `enterCustomerIdSuffix`, `enterAutoNumber`, `enterAutoBaseNumber`, `clickNewSamples`, `clickAutoBaseNumber`.
    - Table helpers: `fillColumnIfEmpty`, `validateTableFilled`.
    - Validations:
      - `verifyLabIdsPresentForLastNSamples(lastN, test)`
      - `verifyCustomerIdSequentialWithAutoNumber(prefix, autoBase, suffix, startAutoNum, lastN, test)`
      - `verifyCustomerIdMatchesAutoBaseForLastNSamples(expectedAutoBase, lastN, test)`
      - `verifyAndDismissBlankAutoBasePopup(test)`
    - Utilities: `clearAllSampleInputs()` to blank Samples tab inputs before a test flow.

### Test Data
- `src/test/java/gaia/utils/TestData/DataEntryTestData.java`
  - Holds constants for Sample tests (counts, prefixes/suffixes, Auto Base/Number, and columns to validate).

### Drivers and Sessions
- `src/test/java/gaia/utils/Driver/DriverManager.java`
  - Starts WinAppDriver, launches the GAIA app, attaches to the main window (driver) and the child COC window (cocDriver), and provides `WebDriverWait`s.
  - Paths are configured via constants. Verify these for your environment:
    - `WIN_APP_DRIVER_PATH`
    - `GAIA_APP_PATH`

### Running Tests
- Run all tests:
```bash
mvn -q clean test
```

- Run a single test method:
```bash
mvn -q clean test -Dtest="gaia.tests.DataEntry.SampleTest#runSampleTest1"
mvn -q clean test -Dtest="gaia.tests.DataEntry.SampleTest#runSampleTest2"
mvn -q clean test -Dtest="gaia.tests.DataEntry.SampleTest#runSampleTest3"
```

### Reports
- Extent report generated at: `test-output/GAIA_Report.html`

### Notes and Tips
- Ensure WinAppDriver is installed and the configured path is correct.
- If `cocDriver` detaches (rare in long runs), `DriverManager.attachCocToMainWindow()` reattaches.
- If your environment uses different control names, adjust the XPath constants in `DataEntryPage` accordingly.


