
package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class StationLicense {
    
    @JsonProperty("licenseType")
    private String licenseType;

    @JsonProperty("softwareEnabledAt")
    private String softwareEnabledAt;
    
    @JsonProperty("activatedAt")
    private String activatedAt;

    @JsonProperty("expiresAt")
    private String expiresAt;
    
   public StationLicense(){
       
   }
   
   @JsonIgnore
   public StationLicense(String licenseType, String softwareEnabledAt, String activatedAt, String expiresAt){
       this.licenseType = licenseType;
       this.softwareEnabledAt = softwareEnabledAt;
       this.activatedAt = activatedAt;
       this.expiresAt = expiresAt;
   }
   
    public String getLicenseType() {
        return licenseType;
    }

    public String getSoftwareEnabledAt() {
        return softwareEnabledAt;
    }
    
    public String getActivateAt() {
        return activatedAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }


    
}
