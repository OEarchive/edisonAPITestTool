package Model.DataModels.TrendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileOverview {

    @JsonProperty("efficiencyAverage")
    private double efficiencyAverage;

    @JsonProperty("efficiencyAverageDelta")
    private double efficiencyAverageDelta;

    @JsonProperty("savings")
    private MobileSavings savings;

    @JsonProperty("optimizationGraph")
    private MobileOptimizationGraph optimizationGraph;

    public double getEfficiencyAverage() {
        return this.efficiencyAverage;
    }

    public double getEfficiencyAverageDelta() {
        return this.efficiencyAverageDelta;
    }

    public MobileSavings getSavings() {
        return this.savings;
    }

    public MobileOptimizationGraph getOptimizationGraph() {
        return this.optimizationGraph;
    }

}
