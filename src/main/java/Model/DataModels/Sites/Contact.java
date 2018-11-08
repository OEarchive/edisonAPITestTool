package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Contact {

    @JsonProperty("role")
    private String role;
    
    @JsonProperty("name")
    private String name; 
    
    @JsonProperty("email")
    private String email;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("phones")
    private List<Phone> phones;

    public Contact() {

    }

    @JsonIgnore
    public Contact(String role, String email, String name, String firstName, String lastName, String username, List<Phone> phones) {

        this.role = role;
        this.email = email;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phones = phones;
    }

    public String getRole() {
        return role;
    }

    @JsonIgnore
    public void setRole(String role) {
        this.role = role;
    }


    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public void setEmail(String email) {
        this.email = email;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    @JsonIgnore
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
    
    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
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

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public void setUsername(String username) {
        this.username = username;
    }
}
