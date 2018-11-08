
package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class WizardSteps {

    @JsonProperty("currentStep")
    private WizardStep currentStep;

    @JsonProperty("allSteps")
    private List<WizardStep> allSteps;

    public WizardStep getCurrentStep() {
        return currentStep;
    }

    public List<WizardStep> getAllSteps() {
        return allSteps;
    }

}
