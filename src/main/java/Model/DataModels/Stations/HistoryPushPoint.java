package Model.DataModels.Stations;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class HistoryPushPoint {

    @JsonProperty("name")
    private String name;

    @JsonProperty("path")
    private String path;

    @JsonProperty("pointType")
    private String pointType;

    @JsonProperty("timestamps")
    private List<String> timestamps;

    @JsonProperty("values")
    private List<Object> values;

    @JsonIgnore
    public HistoryPushPoint() {

    }

    @JsonIgnore
    public HistoryPushPoint(DatapointHistoriesResponse oldHist) {
        this.name = oldHist.getName();
        this.path = "repush/data";
        this.pointType = "numeric";

        this.timestamps = new ArrayList<>();
        this.values = new ArrayList<>();

        int timeStampIndex = 0;
        boolean pointTypeSet = false;

        for (String oldTimeStamp : oldHist.getTimestamps()) {

            Object oldValue = oldHist.getValues().get(timeStampIndex);
            timeStampIndex++;

            //if the old value is null, just ignore this point at this timestamp
            if (oldValue == null) {
                continue;
            }

            //set the point type to the type of the first non-null value
            if (!pointTypeSet) {

                pointTypeSet = true;

                if (oldValue instanceof Integer) {
                    this.pointType = "numeric";

                } else if (oldValue instanceof Double) {
                    this.pointType = "numeric";

                } else if (oldValue instanceof Boolean) {
                    this.pointType = "boolean";

                } else {
                    this.pointType = "string";
                }
            }

            //now add the timestamp and the non-null value
            timestamps.add(oldTimeStamp);
            values.add(oldValue);
        }

    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    @JsonIgnore
    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getTimestamps() {
        return timestamps;
    }

    @JsonIgnore
    public void setTimestamps(List<String> timestamps) {
        this.timestamps = timestamps;
    }

    public List<Object> getValues() {
        return values;
    }

    @JsonIgnore
    public void setValues(List<Object> values) {
        this.values = values;
    }

    public String getPointType() {
        return pointType;
    }

    @JsonIgnore
    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

}
