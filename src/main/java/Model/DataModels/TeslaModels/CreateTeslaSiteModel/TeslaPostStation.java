package Model.DataModels.TeslaModels.CreateTeslaSiteModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeslaPostStation {

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("baselineEnabled")
    private boolean baselineEnabled;

    @JsonProperty("siteID")
    private String siteID;

    @JsonProperty("commissionedAt")
    private String commissionedAt;

    @JsonProperty("plantId")
    private String plantId;

    @JsonProperty("regenerationAllowed")
    private boolean regenerationAllowed;

    @JsonProperty("productType")
    private String productType;

    @JsonProperty("atomEnabled")
    private boolean atomEnabled;

    @JsonProperty("address")
    private String address;

    @JsonProperty("time_zone")
    private String time_zone;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    // =======
    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    @JsonIgnore
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean getbaselineEnabled() {
        return baselineEnabled;
    }

    @JsonIgnore
    public void setbaselineEnabled(boolean baselineEnabled) {
        this.baselineEnabled = baselineEnabled;
    }

    public String getSiteID() {
        return siteID;
    }

    @JsonIgnore
    public void setSiteId(String siteID) {
        this.siteID = siteID;
    }

    public String getCommissionedAt() {
        return commissionedAt;
    }

    @JsonIgnore
    public void setCommissionedAt(String commissionedAt) {
        this.commissionedAt = commissionedAt;
    }

    public String getPlantId() {
        return plantId;
    }

    @JsonIgnore
    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public boolean getRegenerationAllowed() {
        return regenerationAllowed;
    }

    @JsonIgnore
    public void setRegenerationAllowed(boolean regenerationAllowed) {
        this.regenerationAllowed = regenerationAllowed;
    }

    public String getProductType() {
        return productType;
    }

    @JsonIgnore
    public void setProductType(String productType) {
        this.productType = productType;
    }

    public boolean getAtomEnabled() {
        return this.atomEnabled;
    }

    @JsonIgnore
    public void setAtomEnabled(boolean atomEnabled) {
        this.atomEnabled = atomEnabled;
    }

    public String getaddress() {
        return address;
    }

    @JsonIgnore
    public void setaddress(String address) {
        this.address = address;
    }

    public String getTimeZone() {
        return time_zone;
    }

    @JsonIgnore
    public void setTimeZone(String time_zone) {
        this.time_zone = time_zone;
    }

    public double getLatitude() {
        return latitude;
    }

    @JsonIgnore
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLlongitude() {
        return longitude;
    }

    @JsonIgnore
    public void setlongitude(double longitude) {
        this.longitude = longitude;
    }
}
