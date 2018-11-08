package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TriggerAlarmResponse {

    //{"payload":null,"ctx":"db0d79ad-4d39-4878-bbbf-6e8c2b53242c@","success":true,"timestamp":"2016-06-07T21:52:43.990Z","message":""}
    @JsonProperty("payload")
    private String payload;

    @JsonProperty("ctx")
    private String ctx;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("message")
    private String message;

    public String getPyaload() {
        return this.payload;
    }

    public String getCTX() {
        return this.ctx;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getMessage() {
        return this.message;
    }

}
