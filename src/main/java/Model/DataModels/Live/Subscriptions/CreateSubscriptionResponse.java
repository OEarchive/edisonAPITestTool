
package Model.DataModels.Live.Subscriptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;


public class CreateSubscriptionResponse {
    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("datapoints")
    private List<SubscriptionResponseDatapoint> datapoints;
    
    @JsonProperty("subscriptionId")
    private String subscriptionId;


    public String getTimestamp() {
        return timestamp;
    }
    
    public List<SubscriptionResponseDatapoint> getPoints() {
        return datapoints;
    }
    
    public String getSubsciptionID() {
        return subscriptionId;
    }


}
