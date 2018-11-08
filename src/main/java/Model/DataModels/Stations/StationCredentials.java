
package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class StationCredentials {
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("password")
    private String password;
    
    public StationCredentials(){
        
    }
    
    @JsonIgnore
    public StationCredentials( String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public String getName(){
        return username;
    }
    @JsonIgnore
    public void setName( String username){
        this.username = username;
    }
    
    public String getPassword(){
        return password;
    }
    @JsonIgnore
    public void setPassword( String password){
        this.password = password;
    }
    
    
}
