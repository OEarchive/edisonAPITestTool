package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StationAuditLogData {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("logName")
    private String logName;

    @JsonProperty("severity")
    private int severity;

    @JsonProperty("message")
    private String message;

    @JsonProperty("exception")
    private String exception;


    public String getTimestamp() {
        return timestamp;
    }
    @JsonIgnore
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLogName() {
        return logName;
    }

    @JsonIgnore
    public void setLogName(String logName) {
        this.logName = logName;
    }

    public int getSeverity() {
        return severity;
    }

    @JsonIgnore
    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    @JsonIgnore
    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    @JsonIgnore
    public void setException(String exception) {
        this.exception = exception;
    }

  
}
