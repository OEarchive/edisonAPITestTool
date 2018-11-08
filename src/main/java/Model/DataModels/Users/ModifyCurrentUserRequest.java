package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyCurrentUserRequest {

    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("preferences")
    private CurrentUserPreferences preferences;
    @JsonProperty("current_password")
    private String currentPassword;
    @JsonProperty("password")
    private String password;

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    @JsonIgnore
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonIgnore
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CurrentUserPreferences getPreferences() {
        return preferences;
    }

    @JsonIgnore
    public void setPreferences(CurrentUserPreferences preferences) {
        this.preferences = preferences;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    @JsonIgnore
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public void setPassword(String password) {
        this.password = password;
    }

}
