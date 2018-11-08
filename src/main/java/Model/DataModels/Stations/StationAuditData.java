package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StationAuditData {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("target")
    private String target;

    @JsonProperty("slotName")
    private String slotName;

    @JsonProperty("oldValue")
    private String oldValue;

    @JsonProperty("value")
    private String value;

    @JsonProperty("userName")
    private String userName;

    public String getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOperation() {
        return operation;
    }

    @JsonIgnore
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getTarget() {
        return target;
    }

    @JsonIgnore
    public void setTarget(String target) {
        this.target = target;
    }

    public String getSlotName() {
        return slotName;
    }

    @JsonIgnore
    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getOldValue() {
        return oldValue;
    }

    @JsonIgnore
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getValue() {
        return value;
    }

    @JsonIgnore
    public void setValue(String value) {
        this.value = value;
    }

    public String getUserName() {
        return userName;
    }

    @JsonIgnore
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
