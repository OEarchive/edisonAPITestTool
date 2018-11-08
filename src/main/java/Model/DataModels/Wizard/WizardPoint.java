package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WizardPoint {

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private int address;

    @JsonProperty("value")
    private double value;

    @JsonProperty("verified")
    private Boolean verified;

    @JsonProperty("notUsed")
    private Boolean notUsed;

    public String getName() {
        return name;
    }

    public int getAddress() {
        return address;
    }

    public double getValue() {
        return value;
    }

    public Boolean getVerified() {
        return verified;
    }

    public Boolean getNotUsed() {
        return notUsed;
    }

}
