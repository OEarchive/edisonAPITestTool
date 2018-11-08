package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class DatapointsAndMetadataResponse {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("name")
    private String name;
    
    @JsonProperty("label")
    private String label;

    @JsonProperty("value")
    private Object value;

    @JsonProperty("uom")
    private String uom;

    @JsonProperty("measure")
    private String measure;
    
    @JsonProperty("datapointAssociations")
    private List<Map> datapointAssociations;
    
    @JsonProperty("isCalculated")
    private boolean isCalculated;
    
    public String getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }
    
    public String getLabel() {
        return label;
    }

    public Object getValue() {
        return value;
    }

    public String getUOM() {
        return uom;
    }

    public String getMeasure() {
        return measure;
    }
    
    public List<Map> getDatapointAssociations() {
        return datapointAssociations;
    }
    
    public boolean getIsCalculated() {
        return isCalculated;
    }
}
