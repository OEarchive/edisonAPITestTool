
package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;


public class DatapointHistoriesResponse {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("name")
    private String name;
    
    @JsonProperty("uom")
    private String uom;
    
    @JsonProperty("resolution")
    private String resolution;
    
    @JsonProperty("measure")
    private String measure;


    @JsonProperty("values")
    private List<Object> values;
    
    
    @JsonProperty("timestamps")
    private List<String> timestamps;
    
    @JsonProperty("datapointAssociations")
    private List<Map> datapointAssociations; 
    
    @JsonProperty("label")
    private String label;
    

    public String getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public List<Object> getValues() {
        return values;
    }
    
    public List<String> getTimestamps() {
        return timestamps;
    }

    public String getUOM() {
        return uom;
    }

    public String getMeasure() {
        return measure;
    }
    
    public String getResolution() {
        return resolution;
    }
    
    public List<Map> getDatapointAssociations(){
        return datapointAssociations;
    }
    
    public String getLabel() {
        return label;
    }
}