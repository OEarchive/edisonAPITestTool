package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivationCodeResponse {

    @JsonProperty("activationCode")
    private String activationCode;

    public String getActivationCode() {
        return this.activationCode;
    }

}
