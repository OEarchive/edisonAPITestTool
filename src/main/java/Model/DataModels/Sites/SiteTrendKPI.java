package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SiteTrendKPI {

    @JsonProperty("sid")
    private String sid;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("start")
    private String start;
    @JsonProperty("end")
    private String end;
    @JsonProperty("resolution")
    private String resolution;
    @JsonProperty("timestamps")
    private List<String> timestamps;
    @JsonProperty("series")
    private List<TimeSeriesDataItem> series;

    public String getSid() {
        return sid;
    }
    
    public String getTimezone(){
        return timezone;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
    
    public String getResolution(){
        return resolution;
    }

    public List<String> getTimestamps() {
        return timestamps;
    }

    public List<TimeSeriesDataItem> getSeries() {
        return series;
    }

}
