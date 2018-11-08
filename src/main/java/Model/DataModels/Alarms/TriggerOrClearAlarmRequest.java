
package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TriggerOrClearAlarmRequest {
    
    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("action")
    private String action;

    @JsonProperty("by")
    private String by;
    
    @JsonProperty("value")
    private Object alarmValue;
    
    @JsonProperty("message")
    private String message;

    public String getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    @JsonIgnore
    public void setAction(String action) {
        this.action = action;
    }

    public String getBy() {
        return by;
    }

    @JsonIgnore
    public void setBy(String by) {
        this.by = by;
    }
    
    public Object getAlarmValue() {
        return alarmValue;
    }

    @JsonIgnore
    public void setAlarmValue(Object alarmValue) {
        this.alarmValue = alarmValue;
    }
    
    public String getMessage() {
        return message;
    }

    @JsonIgnore
    public void setMessage(String message) {
        this.message = message;
    }
}
