
package Model.DataModels.Live.Subscriptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;


public class CreateSubscriptionRequest {
    
    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("points")
    private Map<String, List<String>> points;
    
    @JsonIgnore
    public CreateSubscriptionRequest( String timestamp, Map<String, List<String>> points  ){
        this.timestamp = timestamp;
        this.points = points;
        
    }


    public String getTimestamp() {
        return timestamp;
    }

    //@JsonIgnore
    //public void setTimestamp(String timestamp) {
    //    this.timestamp = timestamp;
    //}

    public Map<String, List<String>> getPoints() {
        return points;
    }

    //@JsonIgnore
    // public void setPoints(Map<String, List<String>> points) {
    //    this.points = points;
    //}

    
}
