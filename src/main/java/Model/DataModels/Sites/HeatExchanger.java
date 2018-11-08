
package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HeatExchanger {
    
    @JsonProperty("name")
    private String name;
    
    @JsonIgnore
    public HeatExchanger( String name ){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    @JsonIgnore
    public void setName( String name){
        this.name = name;
    }
    
    
}
