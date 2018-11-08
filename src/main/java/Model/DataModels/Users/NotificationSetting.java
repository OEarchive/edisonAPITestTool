package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationSetting {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("eventName")
    private String eventName;

    @JsonProperty("targetType")
    private String targetType;

    @JsonProperty("jobType")
    private String jobType;

    public String getSid() {
        return this.sid;
    }
    
    @JsonIgnore
    public void setSid( String sid ){
        this.sid = sid;
    }

    public String getEventName() {
        return this.eventName;
    }
    
        @JsonIgnore
    public void setEventName( String eventName ){
        this.eventName = eventName;
    }

    public String getTargetType() {
        return targetType;
    }
    
        @JsonIgnore
    public void setTargetType( String targetType ){
        this.targetType = targetType;
    }

    public String getJobType() {
        return jobType;
    }
    
        @JsonIgnore
    public void setJobType( String jobType ){
        this.jobType = jobType;
    }

}
