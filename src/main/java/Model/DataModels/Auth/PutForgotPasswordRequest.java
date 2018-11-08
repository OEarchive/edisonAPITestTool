package Model.DataModels.Auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PutForgotPasswordRequest {

    @JsonProperty("reset_code")
    private String reset_code;

    @JsonProperty("new_password")
    private String new_password;

    @JsonProperty("confirm")
    private String confirm;

    public String getResetCode() {
        return reset_code;
    }

    @JsonIgnore
    public void setRestCode(String reset_code) {
        this.reset_code = reset_code;
    }

    public String getNewPassword() {
        return new_password;
    }

    @JsonIgnore
    public void setPassword(String new_password) {
        this.new_password = new_password;
    }

    public String getConfirm() {
        return confirm;
    }

    @JsonIgnore
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
