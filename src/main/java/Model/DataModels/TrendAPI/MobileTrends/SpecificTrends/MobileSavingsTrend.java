
package Model.DataModels.TrendAPI.MobileTrends.SpecificTrends;

import Model.DataModels.TrendAPI.MobileTrends.MobileGraph;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileSavingsTrend {
    @JsonProperty("kwhSaved")
    private double kwhSaved;

    @JsonProperty("moneySaved")
    private double moneySaved;
   
    @JsonProperty("kwhUnoptimized")
    private double kwhUnoptimized;
    
    @JsonProperty("moneyGoalMet")
    private double moneyGoalMet;
    
    @JsonProperty("energyGraph")
    private MobileGraph energyGraph;
    
    @JsonProperty("savingsGraph")
    private MobileGraph savingsGraph;

    public double getKWHSaved() {
        return this.kwhSaved;
    }
    
    public double getMoneySaved() {
        return this.moneySaved;
    }

    public double getKWHUnoptimized() {
        return this.kwhUnoptimized;
    }
    
    public double getMoneyGoalMet() {
        return this.moneyGoalMet;
    }
    
    public MobileGraph getEnergyGraph(){
        return this.energyGraph;
    } 
    
    public MobileGraph getSavingsGraph(){
        return this.savingsGraph;
    } 
}
