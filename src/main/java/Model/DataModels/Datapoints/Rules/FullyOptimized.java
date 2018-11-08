package Model.DataModels.Datapoints.Rules;

public class FullyOptimized extends Rule {

    public FullyOptimized() {
        super("FullyOptimized");

        super.addSubRule(new Rule("OptimumControl"));
        super.addSubRule(new Rule("NotOptimizedRollup"));
        super.addSubRule(new Rule("CHWDPSPNotOptimized"));
        super.addSubRule(new Rule("CHWSTSPNotOptimized"));
        super.addSubRule(new Rule("CDWSTSPNotOptimized"));

    }
}
