
package Model.DataModels.TrendAPI.MobileTrends.SpecificTrends;

import Model.DataModels.TrendAPI.MobileTrends.MobileGraph;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MobileOptimizationTrend {
    @JsonProperty("totalHoursOptimized")
    private double totalHoursOptimized;

    @JsonProperty("totalHoursPartiallyOptimized")
    private double totalHoursPartiallyOptimized;
   
    @JsonProperty("totalHoursNotOptimized")
    private double totalHoursNotOptimized;
    
    @JsonProperty("totalHoursOffline")
    private double totalHoursOffline;
    
    @JsonProperty("savingsGraph")
    private MobileGraph savingsGraph;

    public double getTotalHoursOptimized() {
        return this.totalHoursOptimized;
    }
    
    public double getTotalHoursPartiallyOptimized() {
        return this.totalHoursPartiallyOptimized;
    }

    public double getTotalHoursNotOptimized() {
        return this.totalHoursNotOptimized;
    }
    
    public double getTotalHoursOffline() {
        return this.totalHoursOffline;
    }
    
    public MobileGraph getSavingsGraph(){
        return this.savingsGraph;
    } 
    
}
