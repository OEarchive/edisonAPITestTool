
package Model.DataModels.Reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class ReportsList {


    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("reports")
    private List<Object> reports;


    public String getTimestamp() {
        return timestamp;
    }

    public List<Object> getReports() {
        return reports;
    }
}

