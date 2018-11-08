
package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyCurrentUserPasswordResponse {
    
    @JsonProperty("message")
    private String message;

    public String getMessage() {
        return message;
    }

}