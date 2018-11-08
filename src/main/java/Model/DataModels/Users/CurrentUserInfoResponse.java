package Model.DataModels.Users;

import Model.DataModels.Sites.Phone;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class CurrentUserInfoResponse {

    @JsonProperty("userId")
    private String userID;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstName")
    private String first_name;

    @JsonProperty("lastName")
    private String last_name;

    @JsonProperty("lockedUntil")
    private String locked_until;

    @JsonProperty("extSfId")
    private String ext_sf_id;

    @JsonProperty("passwordExpires")
    private String password_expires;

    @JsonProperty("phones")
    private List<Phone> phones;

    @JsonProperty("preferences")
    private CurrentUserPreferences preferences;

    @JsonProperty("roles")
    private List<RoleItem> roles;

    @JsonProperty("permissions")
    private List<UserPerms> permissions;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("createdAt")
    private String created_at;

    @JsonProperty("modifiedAt")
    private String modified_at;

    @JsonProperty("initialLogin")
    private String initial_login;
    
    @JsonProperty("notifications")
    private List<Object> notifications;
    
    @JsonProperty("updatedAt")
    private String updated_at;

    public String getUserID() {
        return this.userID;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getExtSfId() {
        return ext_sf_id;
    }

    public String getDateCreated() {
        return created_at;
    }

    public String getDateModified() {
        return modified_at;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPasswordExpires() {
        return this.password_expires;
    }

    public String getLockedUntil() {
        return this.locked_until;
    }

    public List<Phone> getPhones() {
        return this.phones;
    }

    public CurrentUserPreferences getPreferences() {
        return this.preferences;
    }

    public List<RoleItem> getRoles() {
        return this.roles;
    }

    public List<UserPerms> getPermissions() {
        return this.permissions;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getInitialLogin() {
        return this.initial_login;
    }
    
    public List<Object> getNotifications(){
        return notifications;
    }
    
    public String getUpdatedAt() {
        return updated_at;
    }

}
