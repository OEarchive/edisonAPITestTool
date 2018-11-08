
package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserPerms {
    
    @JsonProperty("target")
    private String target;
    
    @JsonProperty("grants")
    private List grants;
    
    public String getTarget(){
        return target;
    }
    
    @JsonIgnore
    public void setTarget( String target ){
        this.target = target;
    }
    
    public List<String> getGrants(){
        return this.grants;
    }
    
    @JsonIgnore
    public void setGrants( ArrayList<Map> grants ){
        this.grants = grants;
    }
    
}
