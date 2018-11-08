package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SiteAlarm {

    @JsonProperty("name")
    private String name;
    
    @JsonProperty("description")
    private String description;

    @JsonProperty("state")
    private String state;

    @JsonProperty("alarmSid")
    private String alarmSid;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("enabled")
    private Boolean enabled;

    @JsonProperty("severity")
    private String severity;

    @JsonProperty("sortOrder")
    private int sortOrder;

    @JsonProperty("recommendation")
    private String recommendation;

    @JsonProperty("lastMessage")
    private String lastMessage;
    
    @JsonProperty("associatedNodes")
    private List<Object> associatedNodes;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("lastValue")
    private String lastValue;

    @JsonProperty("lastTriggered")
    private String lastTriggered;

    @JsonProperty("lastCleared")
    private String lastCleared;

    @JsonProperty("lastAcknowledged")
    private String lastAcknowledged;

    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public String getAlarmSid() {
        return alarmSid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getSeverity() {
        return severity;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public String getLastMessage() {
        return lastMessage;
    }
    
    public List<Object> getAssociatedNodes(){
        return this.associatedNodes;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getLastValue() {
        return lastValue;
    }

    public String getLastTriggered() {
        return lastTriggered;
    }

    public String getLastCleared() {
        return lastCleared;
    }

    public String getLastAcknowledged() {
        return lastAcknowledged;
    }

}
