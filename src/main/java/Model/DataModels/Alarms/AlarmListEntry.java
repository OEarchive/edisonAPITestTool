
package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlarmListEntry {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("state")
    private String state;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("acknowledgedDate")
    private String acknowledgedDate;

    @JsonProperty("endDate")
    private String endDate;



    public String getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getAcknowledgedDate() {
        return acknowledgedDate;
    }

    public String getEndDate() {
        return endDate;
    }
}