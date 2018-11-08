
package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkAvailabilityResponse {
   
    @JsonProperty("server_time")
    private String serverTime;

    public String getServerTime() {
        return serverTime;
    }

}