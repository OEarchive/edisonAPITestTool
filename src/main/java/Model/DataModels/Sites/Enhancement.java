package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class Enhancement {

    @JsonProperty("name")
    private String name;

    @JsonProperty("enabled")
    private Boolean enabled;
    
    @JsonProperty("attributes")
    private Map attributes;

    public Enhancement(){
        
    }

    @JsonIgnore
    public Enhancement(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    @JsonIgnore
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public Map getAttributes() {
        return attributes;
    }

    @JsonIgnore
    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

}
