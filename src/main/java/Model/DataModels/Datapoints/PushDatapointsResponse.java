package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushDatapointsResponse {

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

    public String getPayload() {
        return this.payload;
    }

    public String getCTX() {
        return this.ctx;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
