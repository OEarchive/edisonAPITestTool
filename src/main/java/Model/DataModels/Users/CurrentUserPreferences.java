package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentUserPreferences {

    @JsonProperty("terms")
    private CurrentUserTerms terms;

    @JsonProperty("display")
    private CurrentUserDisplay display;

    public CurrentUserTerms getTerms() {
        return this.terms;
    }
    
    @JsonIgnore
    public void setTerms(CurrentUserTerms terms) {
        this.terms = terms;
    }

    public CurrentUserDisplay getDisplay() {
        return this.display;
    }
    
    @JsonIgnore
    public void setDisplay(CurrentUserDisplay display) {
        this.display = display;
    }

}
