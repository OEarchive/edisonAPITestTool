package Model.DataModels.Sites;

import Model.DataModels.Sites.SiteDatapoints.SiteDatapoint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class Site {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("currencyCode")
    private String currencyCode;

    @JsonProperty("stationId")
    private String stationId;

    @JsonProperty("stationHostId")
    private String stationHostId;

    @JsonProperty("activationCode")
    private String activationCode;

    @JsonProperty("productType")
    private String product;

    @JsonProperty("sfOpportunity")
    private String sfOpportunity;
    
    @JsonProperty("edgePlus")
    private boolean edgePlus;

    @JsonProperty("extSfId")
    private String extSfId;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("enhancements")
    private List<Enhancement> enhancements;

    @JsonProperty("contacts")
    private List<Contact> contacts;

    @JsonProperty("subscription")
    private Map subscription;

    @JsonProperty("stationLicense")
    private StationLicense stationLicense;

    @JsonProperty("alarms")
    private List<Alarm> alarms;
    
    @JsonProperty("commissionDate")
    private String commissionDate;
    
    @JsonProperty("datapoints")
    private List<SiteDatapoint> datapoints;

    @JsonProperty("plantDetails")
    private Map plantDetails;

    @JsonProperty("edge")
    private EdgePlantInfo edge;

    @JsonProperty("equipment")
    private PlantEquipment equipment;

    //TODO : Get rid of these
    @JsonProperty("latitude")
    private int latitude;

    @JsonProperty("longitude")
    private int longitude;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("label")
    private String label;

    @JsonProperty("id")
    private String id;

    // =======
    public String getSid() {
        return sid;
    }

    @JsonIgnore
    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    @JsonIgnore
    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Enhancement> getEnhancements() {
        return enhancements;
    }

    @JsonIgnore
    public void setEnhancements(List<Enhancement> enhancements) {
        this.enhancements = enhancements;
    }

    public String getStationID() {
        return stationId;
    }

    @JsonIgnore
    public void setStationID(String station_id) {
        this.stationId = station_id;
    }

    public String getStationHostID() {
        return stationHostId;
    }

    @JsonIgnore
    public void setStationHostID(String stationHostId) {
        this.stationHostId = stationHostId;
    }

    public String getActivationCode() {
        return activationCode;
    }

    @JsonIgnore
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Map getSubscription() {
        return subscription;
    }

    @JsonIgnore
    public void setSubscription(Map subscription) {
        this.subscription = subscription;
    }

    public StationLicense getStationLicense() {
        return this.stationLicense;
    }

    @JsonIgnore
    public void setStationLicense(StationLicense stationLicense) {
        this.stationLicense = stationLicense;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    @JsonIgnore
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getSFOppotunity() {
        return sfOpportunity;
    }

    @JsonIgnore
    public void setSFOppotunity(String sf_opportunity) {
        this.sfOpportunity = sf_opportunity;
    }
    
    
    public boolean getEdgePlus() {
        return edgePlus;
    }

    @JsonIgnore
    public void setEdgePlus(boolean edgePlus) {
        this.edgePlus = edgePlus;
    }

    public String getExtSFID() {
        return extSfId;
    }

    @JsonIgnore
    public void setExtSFID(String ext_sf_id) {
        this.extSfId = ext_sf_id;
    }

    public String getProduct() {
        return product;
    }

    @JsonIgnore
    public void setProduct(String product) {
        this.product = product;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    @JsonIgnore
    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
    
    public String getCommissionDate() {
        return commissionDate;
    }
    
    @JsonIgnore
    public void setCommissionDate(String commissionDate) {
        this.commissionDate = commissionDate;
    }

    public List<SiteDatapoint> getDatapoints() {
        return datapoints;
    }
    
    @JsonIgnore
    public void setDatapoints(List<SiteDatapoint> datapoints) {
        this.datapoints = datapoints;
    }

    public EdgePlantInfo getEdge() {
        return edge;
    }

    @JsonIgnore
    public void setEdge(EdgePlantInfo edge) {
        this.edge = edge;
    }

    public PlantEquipment getEquipment() {
        return equipment;
    }

    @JsonIgnore
    public void setEquipment(PlantEquipment equipment) {
        this.equipment = equipment;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    @JsonIgnore
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Map getPlantDetails() {
        return this.plantDetails;
    }

    @JsonIgnore
    public void setPlantDetails(Map plantDetails) {
        this.plantDetails = plantDetails;
    }

}
