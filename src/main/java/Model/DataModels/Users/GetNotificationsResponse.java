package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNotificationsResponse {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("eventName")
    private String eventName;

    @JsonProperty("targetType")
    private String targetType;

    @JsonProperty("jobType")
    private String jobType;

    public String getSid() {
        return sid;
    }

    public String getEventName() {
        return eventName;
    }

    public String getTargetType() {
        return targetType;
    }

    public String getJobType() {
        return jobType;
    }

}
