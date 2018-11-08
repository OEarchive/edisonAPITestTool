package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class UpdateSiteRequest {

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

    @JsonProperty("sfOpportunity")
    private String sfOpportunity;

    @JsonProperty("extSfId")
    private String extSfId;
    
    @JsonProperty("edge")
    private EdgePlantInfo edge;

    //@JsonProperty("equipment")
    //private PlantEquipment equipment;


    @JsonIgnore
    public UpdateSiteRequest(Site site) {
        this.name = site.getName();
        this.address = site.getAddress();
        this.currencyCode = site.getCurrencyCode();
        this.stationLicense = site.getStationLicense();
        this.enhancements = site.getEnhancements();
        this.subscription = site.getSubscription();
        this.contacts = site.getContacts();
        this.sfOpportunity = site.getSFOppotunity();
        this.extSfId = site.getExtSFID();
        this.edge = site.getEdge();
        //this.equipment = site.getEquipment();
    }
}
