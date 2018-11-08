package Model.DataModels.Live.GetLiveData;

import Model.DataModels.Live.Subscriptions.SubscriptionResponseDatapoint;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class GetLiveDataResponse {

    @JsonProperty("subscriptionId")
    private String subscriptionId;

    @JsonProperty("datapoints")
    private List<SubscriptionResponseDatapoint> datapoints;

    @JsonProperty("timestamp")
    private String timestamp;

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
