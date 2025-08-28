package gaia.utils.TestData.DataEntryTestData;

import java.util.Arrays;
import java.util.List;

public class SampleData {
    
    // Sample creation test data
    public static class SampleTestData {
        public static final String SAMPLE_COUNT = "2";
        public static final String CUSTOMER_ID_PREFIX = "prefix";
        public static final String CUSTOMER_ID_SUFFIX = "suffix";
        public static final String AUTO_BASE_NUMBER = "Test";
        public static final String AUTO_NUMBER_TEST1 = "1";
        public static final String LAYER_COUNT = "2";
        public static final String DELETE_COUNT = "1";
        public static final List<String> COLUMNS_TO_CHECK = Arrays.asList("Customer ID", "Description", "Lab Notes");
        public static final String CUSTOMER_ID_PREFIX_FILL = "C";
        public static final String DESCRIPTION_PREFIX_FILL = "D";
        public static final String LAB_NOTES_PREFIX_FILL = "L";
    }
    
    // Test scenarios
    public static class SampleTestScenarios {
        public static final String TEST1_NAME = "GAIA - Sample creation with applying all available data";
        public static final String TEST2_NAME = "GAIA - Sample creation with applying autobase number to verify customer ID";
        public static final String TEST3_NAME = "GAIA - Sample creation without applying autobase value to verify customer ID";
        public static final String TEST4_NAME = "GAIA - Layer creation for a selected sample";
        public static final String TEST5_NAME = "GAIA - Layer creation for a slected sample whose customer ID is blank";
        public static final String TEST6_NAME = "GAIA - Refresh samples list";
        public static final String TEST7_NAME = "GAIA - Delete some samples from list (not all)";
        public static final String TEST8_NAME = "GAIA - Import samples from file";
    }
    
    
}
