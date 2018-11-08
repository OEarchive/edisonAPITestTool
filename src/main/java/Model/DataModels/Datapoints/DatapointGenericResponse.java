package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DatapointGenericResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("success")
    private Boolean success;

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Boolean getSuccess() {
        return success;
    }
    
    @JsonIgnore
    public void setMessage( String message){
        this.message = message;
    }
    
        
    @JsonIgnore
    public void setTimestamp( String timestamp){
        this.timestamp = timestamp;
    }
    
        
    @JsonIgnore
    public void setSuccess( Boolean success){
        this.success = success;
    }

}
