
package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CurrentUserTerms {
    @JsonProperty("accepted")
    private String accepted;
    
    public String getAccepted() {
        return this.accepted;
    }
    
    @JsonIgnore
    public void setAccepted( String accepted ){
        this.accepted = accepted;
    }

}
