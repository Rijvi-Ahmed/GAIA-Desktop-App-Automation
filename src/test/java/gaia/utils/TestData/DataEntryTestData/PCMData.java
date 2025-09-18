package gaia.utils.TestData.DataEntryTestData;

import java.util.Arrays;
import java.util.List;

public class PCMData {

    // Pcm section test data
    public static class PCMTestData {
        public static final String PCM_PUMP_RATE = "0";
        public static final String PCM_PUMP_TIME = "0";
        public static final String CUSTOMER_ID_PREFIX_FILL = "C";
        public static final List<String> COLUMNS_TO_CHECK = Arrays.asList("Customer ID", "Pump Rate (LPM) / Volume (L)", "Pump Time (min.)", "Volume Unit", "Time Unit");


    }

    // Test scenarios
    public static class PCMTestScenarios {
        public static final String TEST1_NAME = "GAIA - Validate all fields are properly filled of PCM Data section";
        public static final String TEST2_NAME = "GAIA - Validate customer ID of PCM Data table is equal to customer ID of Sample table";
        public static final String TEST3_NAME = "GAIA - Vaildate if customer ID is removed from PCM Data table, then it also removes from Sample table";
    }
}
