package Model.DataModels.Reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MonthlyReportItem {

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

    @JsonProperty("createdDate")
    private String createdDate;

    @JsonProperty("updatedDate")
    private String updatedDate;

    @JsonProperty("id")
    private String id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("label")
    private String label;

    public String getEndDate() {
        return endDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public String getID() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getLabel() {
        return label;
    }

}
