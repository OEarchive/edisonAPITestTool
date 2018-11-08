package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WizardStep {

    @JsonProperty("stepId")
    private int stepId;

    @JsonProperty("stepName")
    private String stepName;

    @JsonProperty("complete")
    private Boolean complete;

    public int getStepId() {
        return stepId;
    }

    public String getStepName() {
        return stepName;
    }

    public Boolean getComplete() {
        return complete;
    }
}
