package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WizardHeatExchanger {

    @JsonProperty("order")
    private int order;

    @JsonProperty("name")
    private String name;

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

}
