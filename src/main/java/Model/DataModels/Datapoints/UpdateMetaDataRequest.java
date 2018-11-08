
package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UpdateMetaDataRequest {

    @JsonProperty("new_name")
    private String new_name;

    @JsonProperty("attrs")
    private DatapointAttribute attrs;

    public String getNewName(){
        return new_name;
    }
    
    public DatapointAttribute  getAttribute(){
        return attrs;
    }
 
}