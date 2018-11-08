package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationActivateRequest {

    @JsonProperty("activationCode")
    private String activationCode;

    @JsonProperty("stationHostId")
    private String stationHostId;

    public StationActivateRequest(String activationCode, String stationHostId) {
        this.activationCode = activationCode;
        this.stationHostId = stationHostId;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public String getStationHostID() {
        return stationHostId;
    }

}
