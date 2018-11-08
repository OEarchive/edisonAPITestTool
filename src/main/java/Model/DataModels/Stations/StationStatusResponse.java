package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class StationStatusResponse {

    @JsonProperty("ctx")
    private String ctx;
       
    @JsonProperty("payload")
    private String payload;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("messages")
    private List<Object> messages;

    @JsonProperty("result")
    private Object result;
    
    @JsonProperty("success")
    private Object success;
    
    @JsonProperty("message")
    private Object message;

    public List<Object> getMessages() {
        return messages;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPayload() {
        return payload;
    }

    public Object getResult() {
        return result;
    }
    
    @JsonIgnore
    public void setResult( Object result ){
        this.result = result;
    }
    
    public String getCTX(){
        return ctx;
    }
    
    public Object getSuccess() {
        return success;
    }
    
    @JsonIgnore
    public void setSuccess( Boolean success ){
        this.success = success;
    }
    
    public Object getMessage(){
        return message;
    }

}
