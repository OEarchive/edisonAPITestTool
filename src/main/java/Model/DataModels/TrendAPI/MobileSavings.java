package Model.DataModels.TrendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileSavings {

    @JsonProperty("co2Saved")
    private double co2Saved;

    @JsonProperty("moneySaved")
    private double moneySaved;

    @JsonProperty("energySaved")
    private double energySaved;

    @JsonProperty("status")
    private String status;

    @JsonProperty("since")
    private String since;

    public double getCO2Saved() {
        return this.co2Saved;
    }

    public double getMoneySaved() {
        return this.moneySaved;
    }

    public double getEnergySaved() {
        return this.energySaved;
    }

    public String getStatus() {
        return this.status;
    }

    public String getSince() {
        return this.since;
    }
}
