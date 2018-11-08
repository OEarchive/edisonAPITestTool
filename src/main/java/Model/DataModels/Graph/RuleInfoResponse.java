package Model.DataModels.Graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RuleInfoResponse {

    @JsonProperty("@class")
    private String ruleClass;

    @JsonProperty("rules")
    private List<RuleInRulesResponse> rules;
    
    @JsonProperty("ctx")
    private String ctx;

    public String getRuleClass() {
        return ruleClass;
    }

    public List<RuleInRulesResponse> getRules() {
        return rules;
    }
    
    public String getCTX() {
        return ctx;
    }
}
