package Model.DataModels.Users;

import Model.DataModels.Sites.Phone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfo {

    @JsonProperty("userId")
    private String userID;
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("lockedUntil")
    private String lockedUntil;
    @JsonProperty("passwordExpires")
    private String passExpires;
    @JsonProperty("initialLogin")
    private String initialLogin;
    @JsonProperty("phones")
    private List<Phone> phones;
    @JsonProperty("roles")
    private List<RoleItem> roles;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("modifiedAt")
    private String modifiedAt;
    @JsonProperty("disabledAt")
    private String disabledAt;
    @JsonProperty("extSfId")
    private String extSfId;
    @JsonProperty("notifications")
    private List<NotificationSetting> notifications;

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return username;
    }

    @JsonIgnore
    public void setUserName(String str) {
        this.username = str;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public void setEmail(String str) {
        this.email = str;
    }

    public String getFirstName() {
        return firstName;
    }

    @JsonIgnore
    public void setFirstName(String str) {
        this.firstName = str;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonIgnore
    public void setLastName(String str) {
        this.lastName = str;
    }

    public String getLockedUntil() {
        return lockedUntil;
    }

    @JsonIgnore
    public void setLockedUntil(String str) {
        this.lockedUntil = str;
    }

    public String getPassExpires() {
        return passExpires;
    }

    @JsonIgnore
    public void setPassExpires(String str) {
        this.passExpires = str;
    }

    public String getInitialLogin() {
        return initialLogin;
    }

    @JsonIgnore
    public void setInitialLogin(String str) {
        this.initialLogin = str;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    @JsonIgnore
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<RoleItem> getRoles() {
        return roles;
    }

    @JsonIgnore
    public void setRoles(List<RoleItem> roles) {
        this.roles = roles;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }
    
    public String getDisabledAt() {
        return disabledAt;
    }
   
    public String getExtSFID() {
        return extSfId;
    }
    
    public List<NotificationSetting> getNotifications(){
        return this.notifications;
    }


    @JsonIgnore
    public void setExtSFID(String extSfId) {
        this.extSfId = extSfId;
    }

    @JsonIgnore
    public String getUpdatePayload() throws JsonProcessingException {

        Map<String, Object> keyPairs = new HashMap<>();

        if (getUserName().length() > 0) {
            keyPairs.put("username", getUserName());
        }

        if (getEmail().length() > 0) {
            keyPairs.put("email", getEmail());
        }

        if (getFirstName().length() > 0) {
            keyPairs.put("first_name", getFirstName());
        }

        if (getLastName().length() > 0) {
            keyPairs.put("last_name", getLastName());
        }

        if (getPhones().size() > 0) {
            keyPairs.put("phones", getPhones());
        }
        
        if( getRoles().size() > 0 ){
            keyPairs.put("roles", getRoles());
        }
        
        if( getExtSFID().length()> 0 ){
            keyPairs.put("extSfId", getExtSFID());
        }
        
        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(keyPairs);

        return payload;
    }

}
