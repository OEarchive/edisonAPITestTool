
package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class SetSiteActivationCodeRequest {
    @JsonProperty("user_id")
    private String userId;
    
    @JsonProperty("activation_code")
    private String activationCode;

 
    public String getUserId() {
        return userId;
    }

    @JsonIgnore
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getArtivationCode() {
        return activationCode;
    }

    @JsonIgnore
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
