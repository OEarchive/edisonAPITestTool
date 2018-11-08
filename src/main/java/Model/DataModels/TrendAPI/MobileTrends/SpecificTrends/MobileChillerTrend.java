
package Model.DataModels.TrendAPI.MobileTrends.SpecificTrends;

import Model.DataModels.TrendAPI.MobileTrends.MobileGraph;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MobileChillerTrend {
    @JsonProperty("avgAllChillers")
    private double avgAllChillers;

    @JsonProperty("avgLoad")
    private double avgLoad;
   
    @JsonProperty("goalMet")
    private double goalMet;
    
    @JsonProperty("efficiencyGraph")
    private MobileGraph efficiencyGraph;

    public double getAvgAllChillers() {
        return this.avgAllChillers;
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
