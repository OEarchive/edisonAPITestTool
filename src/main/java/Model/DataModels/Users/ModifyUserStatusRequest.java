
package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyUserStatusRequest {
    @JsonProperty("disable")
    private Boolean disable;

   
    @JsonIgnore
    public ModifyUserStatusRequest( Boolean disable) {

        this.disable = disable;
    }

    public Boolean getDisableFlag() {
        return disable;
    }

    @JsonIgnore
    public void setDisableFlag(Boolean disable) {
        this.disable = disable;
    }
}

    