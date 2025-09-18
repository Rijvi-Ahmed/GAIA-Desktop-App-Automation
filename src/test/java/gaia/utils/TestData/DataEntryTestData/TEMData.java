package gaia.utils.TestData.DataEntryTestData;

import java.util.Arrays;
import java.util.List;

public class TEMData {

    //Tem section test data
    public static class TEMTestData {
        public static final String AIR_VOLUME = "0";
        public static final String WIPE_AREA = "0";
        public static final String CUSTOMER_ID_PREFIX_FILL = "C";
        public static final List<String> COLUMNS_TO_CHECK = Arrays.asList("Customer ID", "Air Volume (L)", "Filter", "Filter Type", "Grid Opening Area (mm²)", "Wipe Area (cm²)");

    }

        // Test scenarios
        public static class TEMTestScenarios {
            public static final String TEST1_NAME = "GAIA - Validate all fields are properly filled of TEM Data section";
            public static final String TEST2_NAME = "GAIA - Validate customer ID of TEM Data table is equal to customer ID of Sample table";
            public static final String TEST3_NAME = "GAIA - Vaildate if customer ID is removed from TEM Data table, then it also removes from Sample table";
            public static final String TEST4_NAME = "GAIA - Vailidate first selected filter value is saved to all rows on this column in the TEM Data table";
        }
    
}
