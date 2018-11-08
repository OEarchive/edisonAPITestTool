package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class CreateSiteRequest {

    @JsonProperty("customerSid")
    private String customerSid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private Address address;
    
    @JsonProperty("currencyCode")
    private String currencyCode;

    @JsonProperty("enhancements")
    private List<Enhancement> enhancements;
    
    @JsonProperty("subscription")
    private Map subscription;

    @JsonProperty("stationLicense")
    private StationLicense stationLicense;

    @JsonProperty("contacts")  
    private List<Contact> contacts;

    @JsonProperty("productType")
    private String product;
    
    @JsonProperty("sfOpportunity")
    private String sfOpportunity;

    //@JsonProperty("extSfId")
    //private String extSfId;
    
    @JsonProperty("edge")
    private EdgePlantInfo edge;
        
    @JsonProperty("equipment")
    private PlantEquipment equipment;


    @JsonIgnore
    public CreateSiteRequest(Site site) {
        this.customerSid = site.getSid();
        this.name = site.getName();
        this.address = site.getAddress();
        this.currencyCode = site.getCurrencyCode();
        this.enhancements = site.getEnhancements();
        this.subscription = site.getSubscription();
        this.stationLicense = site.getStationLicense();
        this.contacts = site.getContacts();
        this.product = site.getProduct();
        this.sfOpportunity = site.getSFOppotunity();
        //this.extSfId = site.getExtSFID();
        this.edge = site.getEdge();
        this.equipment = site.getEquipment();
    }

    //=====================
    public String getCustomerSid() {
        return customerSid;
    }

    @JsonIgnore
    public void setCustomerSid(String customer_sid) {
        this.customerSid = customer_sid;
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


    public StationLicense getStationLicense(){
        return stationLicense;
    }
    
    @JsonIgnore
    public void setStationLicense( StationLicense stationLicense ){
        
        this.stationLicense = stationLicense;
    }
    
    public Map getSubscription() {
        return subscription;
    }

    @JsonIgnore
    public void setSubscription(Map subscription) {
        this.subscription = subscription;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    @JsonIgnore
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getProduct() {
        return product;
    }

    @JsonIgnore
    public void setProduct(String product) {
        this.product = product;
    }

    public String getSFOpportunity() {
        return sfOpportunity;
    }

    @JsonIgnore
    public void setSFOpportunity(String sf_opportunity) {
        this.sfOpportunity = sf_opportunity;
    }


    public EdgePlantInfo getEdgePlantInfo(){
        return edge;
    }
    
    @JsonIgnore
    public void setEdgePlantInfo( EdgePlantInfo edge){
        this.edge = edge;
    } 
        
    
    public PlantEquipment getPlantEquipment(){
        return this.equipment;
    }
    
        
    @JsonIgnore
    public void setPlantEquipment( PlantEquipment equipment ){
         this.equipment = equipment;
    }

}

