package Model.DataModels.Datapoints.Rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rule {

    private final String ruleName;
    private final List<Rule> subRules;

    public Rule(String ruleName) {
        this.ruleName = ruleName;
        this.subRules = new ArrayList<>();
    }

    public Boolean getIsLeaf() {
        return this.subRules == null || this.subRules.size() <= 0;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void addSubRule(Rule subRule) {
        subRules.add(subRule);
    }

    public List<Rule> getSubRules() {
        return this.subRules;
    }

    public List<String> getListOfLeafNames() {
        return this.getListOfLeafNamesRec(this);
    }

    private List<String> getListOfLeafNamesRec(Rule rule) {
        List<String> leaves = new ArrayList<>();

        if (rule.getIsLeaf()) {
            if (!leaves.contains(rule.getRuleName())) {
                leaves.add(rule.getRuleName());
            }
            return leaves;
        }

        for (Rule subRule : rule.getSubRules()) {
            for (String name : subRule.getListOfLeafNames()) {
                if (!leaves.contains(name)) {
                    leaves.add(name);
                }
            }
        }

        return leaves;

    }
}
