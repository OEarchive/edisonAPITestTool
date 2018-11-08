package Model.DataModels.Live.PostLiveData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PostLiveDataRequestData {

    @JsonProperty("sid")
    private String sid;
    
    @JsonProperty("stationId")
    private String stationId;

    @JsonProperty("pointScope")
    private String pointScope;

    @JsonProperty("pointValues")
    private List<Object> pointValues;

    @JsonProperty("pointNames")
    private List<String> pointNames;

    @JsonProperty("debugInfo")
    private PostLiveDataRequestDebugInfo debugInfo;

    public String getSid() {
        return sid;
    }

    @JsonIgnore
    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStationId() {
        return stationId;
    }

    @JsonIgnore
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
    
    
    
    public String getPointScope() {
        return pointScope;
    }

    @JsonIgnore
    public void setPointScope(String pointScope) {
        this.pointScope = pointScope;
    }

    public List<Object> getPointValues() {
        return pointValues;
    }

    @JsonIgnore
    public void setPointValues(List<Object> pointValues) {
        this.pointValues = pointValues;
    }

    public List<String> getPointNames() {
        return pointNames;
    }

    @JsonIgnore
    public void setPointNames(List<String> pointNames) {
        this.pointNames = pointNames;
    }

    public PostLiveDataRequestDebugInfo getDebugInfo() {
        return debugInfo;
    }

    @JsonIgnore
    public void setDebugInfo(PostLiveDataRequestDebugInfo debugInfo) {
        this.debugInfo = debugInfo;
    }

}
