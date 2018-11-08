
package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ResendInviteRequest {
    @JsonProperty("invitationMessage")
    private String invitationMessage;

   
    @JsonIgnore
    public ResendInviteRequest( String invitationMessage) {

        this.invitationMessage = invitationMessage;
    }

    public String getInviteMessags() {
        return invitationMessage;
    }

    @JsonIgnore
    public void setInviteMessage(String invitationMessage) {
        this.invitationMessage = invitationMessage;
    }
}
