package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlarmHistoryEntry {

    @JsonProperty("alarmId")
    private String alarmId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("state")
    private String state;

    public String getAlarmId() {
        return alarmId;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getendDate() {
        return endDate;
    }

    public String getState() {
        return state;
    }

}
