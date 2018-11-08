
package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;


public class Datapoint {

    @JsonProperty("id")
    private String id;

    @JsonProperty("pointType")
    private String pointType;

    @JsonProperty("associations")
    private Map associations;

    @JsonProperty("metadata")
    private Map metadata;

    @JsonProperty("currentValue")
    private String currentValue;

    @JsonProperty("currentTimestamp")
    private String currentTimestamp;

    public String getId(){
        return id;
    }
    
    public String getPointType(){
        return pointType;
    }
    
    public Map getAssociations(){
        return associations;
    }
    
    public Map getMetadata(){
        return metadata;
    }
    
    public String getCurrentValue(){
        return currentValue;
    }
    
    public String getCurrentTimestamp(){
        return currentTimestamp;
    }
}