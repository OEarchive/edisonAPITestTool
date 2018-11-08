
package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class DatapointAttribute {

    @JsonProperty("uom")
    private String uom;

    @JsonProperty("default_resolution")
    private String default_resolution;

    @JsonProperty("other_attributes")
    private Map other_attributes;

    public String getUOM(){
        return uom;
    }
    
    public String getDefaultResolution(){
        return default_resolution;
    }
    
    public Map getOtherAttributes(){
        return other_attributes;
    }
    
    public String getPostBody() throws JsonProcessingException{
        Map<String, Object> keyPairs = new HashMap<>();

        keyPairs.put("uom", getUOM());
        keyPairs.put("default_resolution", getDefaultResolution());
        keyPairs.put("other_attributes", getOtherAttributes());

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(keyPairs);

        return payload;
    }
    
}
