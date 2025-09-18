package gaia.utils.TestData.DataEntryTestData;

public class BatchRunData {
    public String projectName;
    public String analysis;
    public String status;
    public String purchaseOrder;
    public String archiveBin;
    public String notes;

    public BatchRunData(String projectName, String analysis, String status, String purchaseOrder, String archiveBin, String notes) {
        this.projectName = projectName;
        this.analysis = analysis;
        this.status = status;
        this.purchaseOrder = purchaseOrder;
        this.archiveBin = archiveBin;
        this.notes = notes;
    }

    public static BatchRunData defaultData() {
        return new BatchRunData(
            "Test Analysis",
            "PLM",
            "Created",
            "PO-12",
            "AB-12",
            "Note is added successfully"
        );
    }

      // Test scenarios
      public static class BatchRunTestScenarios {
        public static final String TEST1_NAME = "GAIA - Create a batch run";
    //     public static final String TEST2_NAME = "GAIA - Sample creation with applying autobase number to verify customer ID";
    //     public static final String TEST3_NAME = "GAIA - Sample creation without applying autobase value to verify customer ID";
    //     public static final String TEST4_NAME = "GAIA - Layer creation for a selected sample";
    //     public static final String TEST5_NAME = "GAIA - Layer creation for a slected sample whose customer ID is blank";
    //     public static final String TEST6_NAME = "GAIA - Refresh samples list";
    //     public static final String TEST7_NAME = "GAIA - Delete some samples from list (not all)";
    //     public static final String TEST8_NAME = "GAIA - Import samples from file";
     }
}
