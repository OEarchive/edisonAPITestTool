package Model.DataModels.Live.PostLiveData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PostLiveDataRequestDebugInfo {

    @JsonProperty("stalePointsCount")
    private int stalePointsCount;

    @JsonProperty("stringPointsCount")
    private int stringPointsCount;

    @JsonProperty("errorPoints")
    private String errorPoints;

    @JsonProperty("totalPointsCount")
    private int totalPointsCount;

    public int getStalePointsCount() {
        return stalePointsCount;
    }

    @JsonIgnore
    public void setStalePointsCount(int stalePointsCount) {
        this.stalePointsCount = stalePointsCount;
    }

    public int getStringPointsCount() {
        return stringPointsCount;
    }

    @JsonIgnore
    public void setStringPointsCount(int stringPointsCount) {
        this.stringPointsCount = stringPointsCount;
    }

    public String getErrorPoints() {
        return errorPoints;
    }

    @JsonIgnore
    public void setErrorPoints(String errorPoints) {
        this.errorPoints = errorPoints;
    }

    public int getTotalPointsCount() {
        return totalPointsCount;
    }

    @JsonIgnore
    public void setTotalPointsCount(int totalPointsCount) {
        this.totalPointsCount = totalPointsCount;
    }

}
