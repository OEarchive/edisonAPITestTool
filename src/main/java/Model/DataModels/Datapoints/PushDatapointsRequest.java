package Model.DataModels.Datapoints;

import Model.DataModels.Stations.HistoryPushPoint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class PushDatapointsRequest {



    @JsonProperty("timestamp")
    private String timestamp;
    
    @JsonProperty("points")
    private List<HistoryPushPoint> points;

    @JsonProperty("lastSuccessTimestamp")
    private String lastSuccessTimestamp;

    @JsonProperty("sendingSid")
    private String sendingSid;
    

    public  List<HistoryPushPoint> getPoints() {
        return points;
    }
    
    @JsonIgnore
    public void setPoints( List<HistoryPushPoint> points ){
        this.points = points;
    }
    
 
    public String getTimestamp() {
        return timestamp;
    }
    
    @JsonIgnore
    public void setTimestamp( String timestamp ){
        this.timestamp = timestamp;
    }

    public String getLastSuccessTimestamp() {
        return lastSuccessTimestamp;
    }
    
    @JsonIgnore
    public void setLastSuccessTimestamp( String lastSuccessTimestamp ){
        this.lastSuccessTimestamp = lastSuccessTimestamp;
    }

    public String getSendingSid() {
        return sendingSid;
    }
    
        @JsonIgnore
    public void setSendingSid( String sendingSid ){
        this.sendingSid = sendingSid;
    }



}
