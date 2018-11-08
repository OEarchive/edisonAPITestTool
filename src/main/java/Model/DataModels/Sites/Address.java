package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country;

    @JsonProperty("postCode")
    private String postCode;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("latitude")
    private int latitude;

    @JsonProperty("longitude")
    private int longitude;

    public String getStreet() {
        return street;
    }

    @JsonIgnore
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    @JsonIgnore
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    @JsonIgnore
    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    @JsonIgnore
    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    @JsonIgnore
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTimezone() {
        return timezone;
    }

    @JsonIgnore
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getLatitude() {
        return latitude;
    }

    @JsonIgnore
    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    @JsonIgnore
    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

}
