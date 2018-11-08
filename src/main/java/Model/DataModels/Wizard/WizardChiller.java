
package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;


public class WizardChiller {

    @JsonProperty("order")
    private int order;

    @JsonProperty("name")
    private String name;

    @JsonProperty("year")
    private int year;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    @JsonProperty("capacity_tons")
    private int capacity_tons;

    @JsonProperty("kw_rating")
    private int kw_rating;

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getCapcityTons() {
        return capacity_tons;
    }

    public int getKWRating() {
        return kw_rating;
    }
}
