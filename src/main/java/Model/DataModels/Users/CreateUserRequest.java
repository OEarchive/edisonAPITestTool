package Model.DataModels.Users;

import Model.DataModels.Sites.Phone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CreateUserRequest {

    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("phones")
    private List<Phone> phones;
    @JsonProperty("roles")
    private List<RoleItem> roles;
    @JsonProperty("invitationMessage")
    private String invitation_message;
    @JsonProperty("noInvite")
    private Boolean no_invite;
    @JsonProperty("extSfId")
    private String extSfId;

    public CreateUserRequest() {

    }

    @JsonIgnore
    public CreateUserRequest(UserInfo user, String invitationMessage, Boolean noInvite) {
        this.username = user.getUserName();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phones = user.getPhones();
        this.roles = user.getRoles();
        this.invitation_message = invitationMessage;
        this.no_invite = noInvite;
        this.extSfId = user.getExtSFID();
    }

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
    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonIgnore
    public void setLastname(String lastName) {
        this.lastName = lastName;
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

    public String getInvitationMessage() {
        return invitation_message;
    }

    @JsonIgnore
    public void setInvitationMessage(String invitation_message) {
        this.invitation_message = invitation_message;
    }

    public Boolean getNoInvite() {
        return no_invite;
    }

    @JsonIgnore
    public void setNoInvite(Boolean no_invite) {
        this.no_invite = no_invite;
    }

    public String getExtSFID() {
        return extSfId;
    }

    @JsonIgnore
    public void setExtSFID(String extSfId) {
        this.extSfId = extSfId;
    }

}
