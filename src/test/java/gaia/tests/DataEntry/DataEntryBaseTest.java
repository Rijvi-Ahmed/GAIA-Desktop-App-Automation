package gaia.tests.DataEntry;



import gaia.pages.DataEntry.SamplePage;

import org.testng.annotations.BeforeClass;
import gaia.pages.DataEntry.DataEntryBasePage;
import gaia.pages.DataEntry.TEMDataPage;
import gaia.tests.Global.BaseTest;
import gaia.utils.Driver.DriverManager;

public class DataEntryBaseTest extends BaseTest {

	protected SamplePage samplePage;
	protected DataEntryBasePage dataEntryBasePage;
	protected TEMDataPage temDataPage;
	private static boolean sessionInitialized = false;

	@BeforeClass 
	public void initializeDataEntrySessionOnce() throws Exception {
		if (dataEntryBasePage == null) {
			dataEntryBasePage = new DataEntryBasePage();
		}
		if (samplePage == null) {
			samplePage = new SamplePage();
		}
		if (temDataPage == null) {
			temDataPage = new TEMDataPage();
		}
		if (DriverManager.getCocDriver() == null) {
			DriverManager.attachCocToMainWindow();
		}
		//when run individual test case, need to comment this line
		if (!sessionInitialized) {
            dataEntryBasePage.setupDataEntrySession();
            sessionInitialized = true;
        }
	}

}


