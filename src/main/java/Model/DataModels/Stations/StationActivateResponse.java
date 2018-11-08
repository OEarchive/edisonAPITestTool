package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationActivateResponse {

    @JsonProperty("stationId")
    private String stationId;

    @JsonProperty("credentials")
    private StationCredentials credentials;
    
    
    public String getStationId() {
        return stationId;
    }

    public StationCredentials getStationCredentials() {
        return credentials;
    }

}
