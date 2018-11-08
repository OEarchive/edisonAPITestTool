
package Model.DataModels.Graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;


public class RuleDefinition {
    @JsonProperty("@class")
    private String ruleClass;
    
    @JsonProperty("method")
    private String method;

    @JsonProperty("configuration")
    private Map configuration;
    
    
    public String getRuleClass() {
        return ruleClass;
    }
    
    public String getMethod(){
        return method;
    }

    public Map getConfiguration() {
        return configuration;
    }
}
