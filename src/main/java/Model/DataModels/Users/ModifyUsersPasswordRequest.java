
package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ModifyUsersPasswordRequest {
    
    @JsonProperty("current_password")
    private String current_password;

    @JsonProperty("password")
    private String password;

    @JsonProperty("confirm")
    private String confirm;

    public String getCurrentPassword() {
        return current_password;
    }

    @JsonIgnore
    public void setCurrentPassword(String current_password) {
        this.current_password = current_password;
    }

    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    @JsonIgnore
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}