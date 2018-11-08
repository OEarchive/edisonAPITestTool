
package Model.DataModels.Live.GetLiveData;

import com.fasterxml.jackson.annotation.JsonProperty;


//TODO : not used anymore...
public class GetLiveDataResponsePointInfo {
        
    @JsonProperty("value")
    private Object pointValue;

    @JsonProperty("timestamp")
    private String timestamp;
    
    @JsonProperty("uom")
    private String uom;


    public Object getPointValue() {
        return pointValue;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public String getUOM() {
        return uom;
    }
}
