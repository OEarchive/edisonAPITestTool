package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WizardActivationPostRequest {

    @JsonProperty("activation_code")
    private String activation_code;

    public String getActivationCode() {
        return activation_code;
    }

}
