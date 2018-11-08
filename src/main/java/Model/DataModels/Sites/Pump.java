package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pump {

    @JsonProperty("name")
    private String name;

    @JsonProperty("kwRating")
    private double kwRating;

    @JsonProperty("GPMRating")
    private double GPMRating;

    public Pump() {

    }

    @JsonIgnore
    public Pump(String name, double kwRating, double GPMRating) {
        this.name = name;
        this.kwRating = kwRating;
        this.GPMRating = GPMRating;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public double getKWRating() {
        return kwRating;
    }

    @JsonIgnore
    public void setKWRating(double kwRating) {
        this.kwRating = kwRating;
    }

    public double getGPMRating() {
        return GPMRating;
    }

    @JsonIgnore
    public void setGPMRating(double GPMRating) {
        this.GPMRating = GPMRating;
    }

}
