package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StationAlarmPushObject {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("alarmTimestamp")
    private String alarmTimestamp;

    @JsonProperty("name")
    private String name;

    @JsonProperty("stationId")
    private String stationId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("alarmContextId")
    private String alarmContextId;

    @JsonProperty("stationName")
    private String stationName;

    @JsonProperty("sendingStationName")
    private String sendingStationName;

    @JsonProperty("source")
    private String source;

    @JsonProperty("respush")
    private boolean repush;

    public String getSid() {
        return sid;
    }

    @JsonIgnore
    public void setSid(String sid) {
        this.sid = sid;
    }

    public boolean getIsActive() {
        return isActive;
    }

    @JsonIgnore
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getAlarmTimestamp() {
        return alarmTimestamp;
    }

    @JsonIgnore
    public void setAlarmTimestamp(String alarmTimestamp) {
        this.alarmTimestamp = alarmTimestamp;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public String getStationId() {
        return stationId;
    }

    @JsonIgnore
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getMessage() {
        return message;
    }

    @JsonIgnore
    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlarmContextId() {
        return alarmContextId;
    }

    @JsonIgnore
    public void setAlarmContextId(String alarmContextId) {
        this.alarmContextId = alarmContextId;
    }

    public String getStationName() {
        return stationName;
    }

    @JsonIgnore
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getSendingStationName() {
        return sendingStationName;
    }

    @JsonIgnore
    public void setSendingStationName(String sendingStationName) {
        this.sendingStationName = sendingStationName;
    }

    public String getSource() {
        return source;
    }

    @JsonIgnore
    public void setSource(String source) {
        this.source = source;
    }

    public boolean getRepush() {
        return repush;
    }

    @JsonIgnore
    public void setRepush(boolean repush) {
        this.repush = repush;
    }

}
