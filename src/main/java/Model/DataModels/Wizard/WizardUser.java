
package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;


public class WizardUser {

    @JsonProperty("username")
    private String username;

    @JsonProperty("currentPassword")
    private String currentPassword;

    @JsonProperty("newPassword")
    private String newPassword;

    public String getUsername() {
        return username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
