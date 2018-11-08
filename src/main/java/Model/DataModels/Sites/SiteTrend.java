package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SiteTrend {

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("start")
    private String start;
    @JsonProperty("end")
    private String end;
    @JsonProperty("overview")
    private List<Object> overview;
    @JsonProperty("timestamps")
    private List<String> timestamps;
    @JsonProperty("resolution")
    private String resolution;
    @JsonProperty("sid")
    private String sid;

    @JsonProperty("optimization")
    private TrendOptimizationGraph optimization;
    
    public String getTimezone(){
        return timezone;
    }
    
    public String getResolution(){
        return resolution;     
    }

    public String getSid() {
        return sid;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public List<Object> getOverview() {
        return overview;
    }

    public List<String> getTimestamps() {
        return timestamps;
    }

    public TrendOptimizationGraph getOptimization() {
        return optimization;
    }

}
