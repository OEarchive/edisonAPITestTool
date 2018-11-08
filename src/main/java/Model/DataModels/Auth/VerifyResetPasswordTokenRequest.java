package Model.DataModels.Auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyResetPasswordTokenRequest {

    @JsonProperty("resetCode")
    private String resetCode;

    public String getResetCode() {
        return resetCode;
    }

    @JsonIgnore
    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

}
