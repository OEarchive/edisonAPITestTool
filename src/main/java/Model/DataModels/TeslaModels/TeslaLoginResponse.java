
package Model.DataModels.TeslaModels;

import com.fasterxml.jackson.annotation.JsonProperty;


public class TeslaLoginResponse {

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("tokenType")
    private String tokenType;

    @JsonProperty("expiresIn")
    private int expiresIn;

    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("createdAt")
    private long createdAt;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
