
package Model.DataModels.Stations;

import Model.DataModels.Wizard.WizardSteps;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;



public class WizardStationStatus {
    
    @JsonProperty("wizard")
    private WizardSteps wizard;
    

    
    public WizardSteps getWizardStatus(){
        return wizard;
    }
    @JsonIgnore
    public void setWizardStatus( WizardSteps wizard){
        this.wizard = wizard;
    }
    

    
}