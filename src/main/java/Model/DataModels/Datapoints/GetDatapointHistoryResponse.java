package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetDatapointHistoryResponse {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("start")
    private String start;

    @JsonProperty("end")
    private String end;

    @JsonProperty("timestamps")
    private List<String> timestamps;

    @JsonProperty("series")
    private List<HistoryQueryDataPointValues> series;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("resolution")
    private String resolution;

    @JsonProperty("timezone")
    private String timezone;

    public String getSid() {
        return this.sid;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public List<String> getTimestamps() {
        return timestamps;
    }

    public List<HistoryQueryDataPointValues> getDatapoints() {
        return series;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getResolution() {
        return this.resolution;
    }

    public String getTimezone() {
        return this.timezone;
    }

    @JsonIgnore
    public List<String> getDataPointNames() {
        List<String> pointNames = new ArrayList<>();
        for (HistoryQueryDataPointValues dpv : series) {
            pointNames.add((String) dpv.getName());
        }
        return pointNames;
    }

    @JsonIgnore
    public List<Object> getValues(String pointName) {
        for (HistoryQueryDataPointValues dpv : series) {
            if( dpv.getName().compareTo(pointName) == 0 ){
                return dpv.getValues();
            }
        }
        return new ArrayList<>();
    }

}
