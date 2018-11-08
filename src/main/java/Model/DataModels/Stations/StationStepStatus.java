
package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class StationStepStatus {
    
    @JsonProperty("stepName")
    private String stepName;
    
    @JsonProperty("complete")
    private String complete;
    
    public String getStepName(){
        return stepName;
    }
    @JsonIgnore
    public void setStepName( String stepName){
        this.stepName = stepName;
    }
    
    public String getComplete(){
        return complete;
    }
    @JsonIgnore
    public void setComplete( String complete){
        this.complete = complete;
    }
     
}