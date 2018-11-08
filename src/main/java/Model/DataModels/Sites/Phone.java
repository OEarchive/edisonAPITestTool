package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Phone {

    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("phoneType")
    private String phoneType;


    public String getPhone() {
        return phone;
    }

    @JsonIgnore
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneType() {
        return phoneType;
    }

    @JsonIgnore
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

}
