
package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleItem {
    
    @JsonProperty("sid")
    private String sid;

    @JsonProperty("rolename")
    private String rolename;
    
    public String getSid() {
        return this.sid;
    }
    
    @JsonIgnore
    public void setSid( String sid){
        this.sid = sid;
    }

    public String getRoleName() {
        return this.rolename;
    }
    
    @JsonIgnore
    public void setRoleName( String rolename ){
        this.rolename = rolename;
    }
} 
