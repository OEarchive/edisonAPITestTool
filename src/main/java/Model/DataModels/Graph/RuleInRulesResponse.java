package Model.DataModels.Graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RuleInRulesResponse {

    @JsonProperty("ruleId")
    private String ruleId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("definition")
    private RuleDefinition definition;

    @JsonProperty("assignments")
    private List<RuleAssignment> assignments;

    public String getRuleId() {
        return ruleId;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public RuleDefinition getDefinition() {
        return definition;
    }

    public List<RuleAssignment> getAssignments() {
        return assignments;
    }

}
