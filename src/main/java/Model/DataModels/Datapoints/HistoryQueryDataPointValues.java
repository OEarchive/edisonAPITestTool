package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class HistoryQueryDataPointValues {

    @JsonProperty("uom")
    private String uom;

    @JsonProperty("measure")
    private String measure;

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("values")
    private List<Object> values;

    public String getSid() {
        return this.sid;
    }

    public String getName() {
        return name;
    }

    public List<Object> getValues() {
        return values;
    }

    public String getUOM() {
        return uom;
    }

    public String getMeasure() {
        return measure;
    }
}
