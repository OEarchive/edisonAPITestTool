package Model.DataModels.TrendAPI.SiteInfo;

import Model.DataModels.TrendAPI.MobileSavings;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileSiteInfo {

    @JsonProperty("name")
    private String name;

    @JsonProperty("plantEfficiency")
    private double plantEfficiency;

    @JsonProperty("chillerEfficiency")
    private double chillerEfficiency;

    @JsonProperty("tonHoursProduced")
    private double tonHoursProduced;

    @JsonProperty("energyConsumed")
    private double energyConsumed;

    @JsonProperty("savings")
    private MobileSavings savings;

    @JsonProperty("avgTemperature")
    private double avgTemperature;

    @JsonProperty("peakConsumption")
    private double peakConsumption;

    @JsonProperty("moneySpentUnoptimized")
    private double moneySpentUnoptimized;

    @JsonProperty("isChillerDiagnosticSite")
    private boolean isChillerDiagnosticSite;

    @JsonProperty("optimizationGraph")
    private MobileSiteInfoOptGraph optimizationGraph;

    public String getName() {
        return this.name;
    }

    public double getPlantEfficiency() {
        return this.plantEfficiency;
    }

    public double getChillerEfficiency() {
        return this.chillerEfficiency;
    }

    public double getTonHoursProduced() {
        return this.tonHoursProduced;
    }

    public double getEnergyConsumed() {
        return this.energyConsumed;
    }

    public MobileSavings getSavings() {
        return this.savings;
    }

    public double getavgTemperature() {
        return this.avgTemperature;
    }

    public double getPeakConsumption() {
        return this.peakConsumption;
    }

    public double getMoneySpentUnoptimized() {
        return this.moneySpentUnoptimized;
    }

    public boolean getIsChillerDiagnosticSite() {
        return this.isChillerDiagnosticSite;
    }

    public MobileSiteInfoOptGraph getOptimizationGraph() {
        return this.optimizationGraph;
    }

}
