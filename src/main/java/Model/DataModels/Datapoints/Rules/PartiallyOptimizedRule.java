package Model.DataModels.Datapoints.Rules;

public class PartiallyOptimizedRule extends Rule {

    public PartiallyOptimizedRule() {
        super("PartiallyOptimized");
        super.addSubRule(new Rule("OptimumControl"));
        super.addSubRule(new FullyOptimized() );
    }
}
