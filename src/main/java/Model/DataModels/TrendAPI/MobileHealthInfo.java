package Model.DataModels.TrendAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileHealthInfo {

    @JsonProperty("AuthEndpoint")
    public String AuthEndpoint;

    @JsonProperty("ServiceEndpoint")
    public String ServiceEndpoint;

    @JsonProperty("Issuer")
    public String Issuer;

    @JsonProperty("Audience")
    public String Audience;
    
    @JsonProperty("E3osUri")
    public String E3osUri;
    
    @JsonProperty("IssuerThumbprint")
    public String IssuerThumbprint;
    
    @JsonProperty("AudienceThumbprint")
    public String AudienceThumbprint;
    
    @JsonProperty("status")
    public String status;

    @JsonIgnore
    private EnumMobileHealthStatus healthStatus;

    public String getAuthEndpoint() {
        return this.AuthEndpoint;
    }

    public String getServiceEndpoint() {
        return this.ServiceEndpoint;
    }

    public String getIssuer() {
        return this.Issuer;
    }

    public String getAudience() {
        return this.Audience;
    }

    public String getE3osUri() {
        return this.E3osUri;
    }

    public String getIssuerThumbprint() {
        return this.IssuerThumbprint;
    }

    public String getAudienceThumbprint() {
        return this.AudienceThumbprint;
    }

    public EnumMobileHealthStatus getHealthStatus() {
        return this.healthStatus;
    }
}
