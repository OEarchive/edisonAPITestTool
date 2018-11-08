package Model.DataModels.Live.PostLiveData;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PostLiveDataResponseMessage {

    @JsonProperty("stationId")
    private String stationId;

    @JsonProperty("pointScope")
    private String pointScope;

    @JsonProperty("pointsList")
    private List<String> pointsList;

    @JsonProperty("refreshRate")
    private int refreshRate;

    @JsonProperty("expirationTime")
    private int expirationTime;

    public String getStationId() {
        return stationId;
    }

    public String getPointScope() {
        return pointScope;
    }

    public List<String> getPointsList() {
        return pointsList;
    }

    //TODO : add these to the UI table
    public int getRefreshRate() {
        return refreshRate;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

}
