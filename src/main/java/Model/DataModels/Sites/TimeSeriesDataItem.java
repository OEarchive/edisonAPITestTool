package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TimeSeriesDataItem {

    @JsonProperty("uom")
    private String uom;

    @JsonProperty("measure")
    private String measure;

    @JsonProperty("name")
    private String name;

    @JsonProperty("stationName")
    private String stationName;

    @JsonProperty("label")
    private String label;

    @JsonProperty("live")
    private Boolean live;

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("values")
    private List<Object> values;

    public String getUom() {
        return uom;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }

    public String getStationName() {
        return stationName;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getLive() {
        return live;
    }

    public String getSid() {
        return sid;
    }

    public List<Object> getValues() {
        return values;
    }

}
