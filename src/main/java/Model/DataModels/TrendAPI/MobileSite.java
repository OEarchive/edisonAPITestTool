
package Model.DataModels.TrendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileSite {

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("longName")
    private String longName;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("weather")
    private MobileWeather weather;

    @JsonProperty("efficiencyAverage")
    private double efficiencyAverage;

    @JsonProperty("efficiencyAverageDelta")
    private double efficiencyAverageDelta;

    @JsonProperty("savings")
    private double savings;

    @JsonProperty("optimizationStatus")
    private String optimizationStatus;

    public String getName() {
        return this.name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getLongName() {
        return this.longName;
    }

    public String getUUID() {
        return this.uuid;
    }

    public MobileWeather getWeather() {
        return this.weather;
    }

    public double getEfficiencyAverage() {
        return this.efficiencyAverage;
    }

    public double getEfficiencyAverageDelta() {
        return this.efficiencyAverageDelta;
    }

    public double getSavings() {
        return this.savings;
    }

    public String getOptimizationStatus() {
        return this.optimizationStatus;
    }
}
