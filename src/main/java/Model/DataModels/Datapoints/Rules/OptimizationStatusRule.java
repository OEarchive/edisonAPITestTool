package Model.DataModels.Datapoints.Rules;

import java.util.List;

public class OptimizationStatusRule extends Rule {

    public OptimizationStatusRule() {
        super("OptimizationStatusRule");
        super.addSubRule(new Rule("BASCommunicationFailure"));
        super.addSubRule(new Rule("OptimumControl"));
        super.addSubRule(new PartiallyOptimizedRule());
        super.addSubRule(new FullyOptimized());
      
        }
    }
