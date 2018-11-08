package Model.DataModels.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class E3OSOACIEResponse {

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("expires_in")
    private int expires_in;

    @JsonProperty("refresh_token")
    private String refresh_token;

    @JsonProperty("permissions")
    private List<Permission> permissions;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return access_token;
    }

    public int getExpiresIn() {
        return expires_in;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public List<Permission> getPermission() {
        return permissions;
    }

}
