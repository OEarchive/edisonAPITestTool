package Model.DataModels.TrendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileWeather {

    @JsonProperty("condition")
    private String condition;

    @JsonProperty("temperature")
    private double temperature;

    public String getCondition() {
        return this.condition;
    }

    public double getTemperature() {
        return this.temperature;
    }

}
