package gaia.utils.TestData;

import java.util.Arrays;
import java.util.List;

public class DataEntryTestData {
    
    // Sample creation test data
    public static class SampleTestData {
        public static final String SAMPLE_COUNT = "2";
        public static final String CUSTOMER_ID_PREFIX = "prefix";
        public static final String CUSTOMER_ID_SUFFIX = "suffix";
        public static final String AUTO_BASE_NUMBER = "Test";
        public static final String AUTO_NUMBER_TEST1 = "1";
        
        public static final List<String> COLUMNS_TO_CHECK = Arrays.asList("Customer ID", "Description", "Lab Notes");
        public static final String CUSTOMER_ID_PREFIX_FILL = "C";
        public static final String DESCRIPTION_PREFIX_FILL = "D";
        public static final String LAB_NOTES_PREFIX_FILL = "L";
        public static final String TEMDATA_SAMPLE_COUNT = null;
    }
    
    // Test scenarios
    public static class TestScenarios {
        public static final String TEST1_NAME = "GAIA - Sample creation with applying all available data";
        public static final String TEST2_NAME = "GAIA - Sample creation with applying autobase number to verify customer ID";
        public static final String TEST3_NAME = "GAIA - Sample creation without applying autobase value to verify customer ID";
        // public static final String TEMDATA_SCENARIO1_NAME = "GAIA - TemData Test Scenario 1";
        // public static final String TEMDATA_SCENARIO2_NAME = "GAIA - TemData Test Scenario 2";
    }
    
    // TemData test data (for future use)
    // public static class TemDataTestData {
    //     // Add TemData specific test data here
    //     public static final String TEMDATA_SAMPLE_COUNT = "3";
    //     public static final String TEMDATA_CUSTOMER_ID_PREFIX = "TemData";
    //     public static final String TEMDATA_CUSTOMER_ID_SUFFIX = "Test";
    //     public static final String TEMDATA_AUTO_BASE_NUMBER = "TemDataBase";
    //     public static final String TEMDATA_AUTO_NUMBER = "100";
        
    //     // Add more TemData test data as needed
    // }
}
