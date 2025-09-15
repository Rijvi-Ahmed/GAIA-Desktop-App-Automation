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
            "Test-analysis type which I set",
            "PLM",
            "Created",
            "PO-12",
            "AB-12",
            "Note is added successfully"
        );
    }
}
