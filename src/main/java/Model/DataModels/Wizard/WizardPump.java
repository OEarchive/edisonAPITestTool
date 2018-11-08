package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WizardPump {

    @JsonProperty("order")
    private int order;

    @JsonProperty("name")
    private String name;

    @JsonProperty("kw")
    private int kw;

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public int getKW() {
        return kw;
    }

}
