
package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;


public class WizardStatusResponse {
    @JsonProperty("ctx")
    private String statusName;

    @JsonProperty("success")
    private Boolean complete;

    @JsonProperty("message")
    private String message;

    public String getStatusName() {
        return statusName;
    }

    public Boolean getComplete() {
        return complete;
    }

    public String getMessage() {
        return message;
    }

}
