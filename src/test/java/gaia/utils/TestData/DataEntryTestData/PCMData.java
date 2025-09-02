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
        //public static final String TEM_CUSTOMER_ID_SUFFIX = "Test";
        // public static final String TEM_AUTO_BASE_NUMBER = "TemDataBase";
        // public static final String TEM_AUTO_NUMBER = "100";

    }

    // Test scenarios
    public static class PCMTestScenarios {
        public static final String TEST1_NAME = "GAIA - Validate all fields of PCM Data section";
        // public static final String TEST2_NAME = "GAIA - Sample creation with applying
        // autobase number to verify customer ID";
    }
}
