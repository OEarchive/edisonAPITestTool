package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class StationsHeartbeat {

    /*
     @JsonProperty("nodeSid")
     private String nodeSid;

     @JsonProperty("originTimestamp")
     private String originTimestamp;

     @JsonProperty("receiveTimestamp")
     private String receiveTimestamp;

     @JsonProperty("sender")
     private String sender;

     @JsonProperty("metadata")
     private Map metadata;
     */
    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("points")
    private Map points;

    @JsonProperty("stationId")
    private String stationId;

    @JsonIgnore
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Map getPoints() {
        return points;
    }

    @JsonIgnore
    public void setPoints(Map points) {
        this.points = points;
    }

    public String getStationId() {
        return stationId;
    }

    @JsonIgnore
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

}
