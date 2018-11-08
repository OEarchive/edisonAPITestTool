
package Model.DataModels.Auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PostForgotPasswordRequest {
    
    @JsonProperty("email_address")
    private String email_address;

    @JsonProperty("redirect_url")
    private String redirect_url;

    public String getEmail() {
        return email_address;
    }
    
    @JsonIgnore
    public void setEmail( String email_address ){
        this.email_address = email_address;
    }

    public String getRedirectURL() {
        return redirect_url;
    }
    
    @JsonIgnore
    public void setRedirectURL( String redirect_url ){
        this.redirect_url = redirect_url;
    }

}
