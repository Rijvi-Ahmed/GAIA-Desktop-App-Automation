package gaia.tests.DataEntry;

import org.testng.annotations.BeforeClass;
import gaia.pages.DataEntryPage;
import gaia.tests.BaseTest;
import gaia.utils.Driver.DriverManager;

public class DataEntryBaseTest extends BaseTest {

    protected DataEntryPage dataEntryPage;

    @BeforeClass
    public void initializeDataEntrySessionOnce() throws Exception {
        if (dataEntryPage == null) {
            dataEntryPage = new DataEntryPage();
        }
        if (DriverManager.getCocDriver() == null) {
            DriverManager.attachCocToMainWindow();
        }
        dataEntryPage.setupDataEntrySession();
    }

}


