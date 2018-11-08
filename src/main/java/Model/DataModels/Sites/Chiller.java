package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Chiller {

    @JsonProperty("name")
    private String name;

    @JsonProperty("year")
    private int year;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    @JsonProperty("capacityTons")
    private int capacityTons;

    @JsonProperty("kwRating")
    private double kwRating;
    
    @JsonProperty("deltaT")
    private double deltaT;
    
    public Chiller(){
        
    }
    
    @JsonIgnore
    public Chiller( String name, String make, String model, int year, int capacityTons, double kwRating, double deltaT ){
        this.name = name;
        this.make = make;
        this.model = model;
        this.year = year;
        this.capacityTons = capacityTons;
        this.kwRating = kwRating;
        this.deltaT = deltaT;
    }

    public String getName() {
        return this.name;
    }
    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return this.year;
    }
    @JsonIgnore
    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return this.make;
    }
    @JsonIgnore
    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }
    @JsonIgnore
    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacityTons() {
        return this.capacityTons;
    }
    @JsonIgnore
    public void setCapacityTons(int capacityTons) {
        this.capacityTons = capacityTons;
    }
    
    public double getKWRating() {
        return this.kwRating;
    }
    @JsonIgnore
    public void setKWRating(double kwRating) {
        this.kwRating = kwRating;
    }
    
    public double getDeltaT() {
        return this.deltaT;
    }
    @JsonIgnore
    public void setDeltaT(double deltaT) {
        this.deltaT = deltaT;
    }

}
