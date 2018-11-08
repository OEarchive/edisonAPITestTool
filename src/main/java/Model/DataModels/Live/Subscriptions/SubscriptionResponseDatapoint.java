package Model.DataModels.Live.Subscriptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionResponseDatapoint {

    @JsonProperty("name")
    private String name;

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("value")
    private Object value;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("uom")
    private String uom;
    
    @JsonProperty("isRequested")
    private boolean isRequested;

    public String getName() {
        return name;
    }

    public String getSid() {
        return sid;
    }

    public Object getValue() {
        return value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUOM() {
        return uom;
    }
    
    public boolean getIsRequested(){
        return isRequested;
    }
}
