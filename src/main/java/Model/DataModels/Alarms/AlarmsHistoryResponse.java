package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AlarmsHistoryResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("histories")
    private List<AlarmListEntry> histories;

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<AlarmListEntry> getAlarmHistoryEntries() {
        return histories;
    }

}
