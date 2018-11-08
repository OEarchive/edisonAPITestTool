
package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigInfoFromValidate {
    
    @JsonProperty("different")
    private String different;
    
    @JsonProperty("lastUpdate")
    private String lastUpdate;
    
    public String getDifferent(){
        return different;
    }
    @JsonIgnore
    public void setDifferent( String different){
        this.different = different;
    }
    
    public String getLastUpdate(){
        return lastUpdate;
    }
    @JsonIgnore
    public void setLastUpdate( String lastUpdate){
        this.lastUpdate = lastUpdate;
    }
    
    
}