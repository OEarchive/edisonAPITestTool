
package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PostDatapointRequest {

    @JsonProperty("pointType")
    private String pointType;

    @JsonProperty("name")
    private String name;

    @JsonProperty("attrs")
    private List<DatapointAttribute> attrs;

    public String getPointType(){
        return pointType;
    }
    
    public String getName(){
        return name;
    }
    
    public List<DatapointAttribute>  getAttributes(){
        return attrs;
    }
    
    
}