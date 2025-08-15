package gaia.tests.DataEntry;

import org.testng.annotations.BeforeClass;

import gaia.pages.DataEntry.SamplePage;
import gaia.pages.DataEntry.DataEntryBasePage;
import gaia.tests.Global.BaseTest;
import gaia.utils.Driver.DriverManager;

public class DataEntryBaseTest extends BaseTest {

	protected SamplePage dataEntryPage;
	protected DataEntryBasePage dataEntryBasePage;

	@BeforeClass
	public void initializeDataEntrySessionOnce() throws Exception {
		if (dataEntryBasePage == null) {
			dataEntryBasePage = new DataEntryBasePage();
		}
		if (dataEntryPage == null) {
			dataEntryPage = new SamplePage();
		}
		if (DriverManager.getCocDriver() == null) {
			DriverManager.attachCocToMainWindow();
		}
		//when run individual test case, need to comment this line
		dataEntryBasePage.setupDataEntrySession();
	}

}


