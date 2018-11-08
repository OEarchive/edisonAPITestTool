package Model.DataModels.Stations;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class HistoryPushObject {
    
    @JsonProperty("timestamp")
    private String timestamp;
    
    @JsonProperty("points")
    private List<HistoryPushPoint> points;
    
    @JsonProperty("stationId")
    private String stationId;
    
    @JsonProperty("stationName")
    private String stationName;

    @JsonProperty("sendingStationName")
    private String sendingStationName;

    @JsonProperty("lastSuccessTimestamp")
    private String lastSuccessTimestamp;
    
    //List<DatapointHistoriesResponse>
    @JsonIgnore
    public HistoryPushObject(){
        
    }
    
    @JsonIgnore
    public HistoryPushObject( String timeStamp, String stationId, List<DatapointHistoriesResponse> oldHistory ){
        
        this.timestamp = timeStamp;
        this.stationId = stationId;
        this.stationName = stationId;
        this.sendingStationName = stationId;
        
        this.lastSuccessTimestamp = timeStamp;
        
        this.points = new ArrayList<>();
        for( DatapointHistoriesResponse oldHist : oldHistory ){
            
            HistoryPushPoint hpp = new HistoryPushPoint( oldHist );
            points.add(hpp);
        }
    }

    public String getSendingStationName() {
        return sendingStationName;
    }

    @JsonIgnore
    public void setSendingStationName(String sendingStationName) {
        this.sendingStationName = sendingStationName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
        public String getLastSuccessTimestamp() {
        return lastSuccessTimestamp;
    }

    @JsonIgnore
    public void setLastSuccessTimestamp(String lastSuccessTimestamp) {
        this.lastSuccessTimestamp = lastSuccessTimestamp;
    }
    
        public String getStationName() {
        return stationName;
    }

    @JsonIgnore
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }


    public List<HistoryPushPoint> getPoints() {
        return points;
    }

    @JsonIgnore
    public void setPoints(List<HistoryPushPoint> points) {
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
