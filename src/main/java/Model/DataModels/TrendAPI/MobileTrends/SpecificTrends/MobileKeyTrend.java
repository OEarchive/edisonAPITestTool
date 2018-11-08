package Model.DataModels.TrendAPI.MobileTrends.SpecificTrends;

import Model.DataModels.TrendAPI.MobileTrends.MobileGraph;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileKeyTrend {

    @JsonProperty("avgEnergyConsumed")
    private double avgEnergyConsumed;

    @JsonProperty("avgTemp")
    private double avgTemp;

    @JsonProperty("avgTonHoursProduced")
    private double avgTonHoursProduced;

    @JsonProperty("peakConsumption")
    private double peakConsumption;

    @JsonProperty("keyGraph")
    private MobileGraph keyGraph;

    public double getAvgEnergyConsumed() {
        return this.avgEnergyConsumed;
    }

    public double getAvgTemp() {
        return this.avgTemp;
    }

    public double getAvgTonHoursProduced() {
        return this.avgTonHoursProduced;
    }

    public double getPeakConsumption() {
        return this.peakConsumption;
    }

    public MobileGraph getKeyGraph() {
        return this.keyGraph;
    }
}
