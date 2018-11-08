
package Model.DataModels.TrendAPI.MobileTrends.SpecificTrends;

import Model.DataModels.TrendAPI.MobileTrends.MobileGraph;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MobilePlantEfficiency {
    @JsonProperty("avgEntirePlant")
    private double avgEntirePlant;

    @JsonProperty("avgLoad")
    private double avgLoad;
   
    @JsonProperty("goalMet")
    private double goalMet;
    
    @JsonProperty("efficiencyGraph")
    private MobileGraph efficiencyGraph;

    public double getAvgEntirePlant() {
        return this.avgEntirePlant;
    }
    
    public double getAvgLoad() {
        return this.avgLoad;
    }

    public double getGoalMet() {
        return this.goalMet;
    }
    
    public MobileGraph getEfficiencyGraph(){
        return this.efficiencyGraph;
    }
}
