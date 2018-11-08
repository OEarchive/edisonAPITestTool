package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAlarmResponse {

    @JsonProperty("payload")
    private String payload;

    @JsonProperty("ctx")
    private String ctx;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private String timestamp;

    public String getPayload() {
        return payload;
    }

    public String getCTX() {
        return ctx;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
