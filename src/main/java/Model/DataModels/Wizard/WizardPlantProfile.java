package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class WizardPlantProfile {

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private Map address;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("enhancements")
    private List<Map> enhancements;

    @JsonProperty("subscription")
    private Map subscription;

    @JsonProperty("contacts")
    private Map contacts;

    @JsonProperty("ext_sf_id")
    private String ext_sf_id;

    @JsonProperty("equipment")
    private WizardEquipment equipment;

    @JsonProperty("last_updated")
    private String last_updated;

    public String getName() {
        return name;
    }

    public Map getAddress() {
        return address;
    }

    public String getTimezone() {
        return timezone;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<Map> getEnhancements() {
        return enhancements;
    }

    public Map getSubscription() {
        return subscription;
    }

    public Map getContacts() {
        return contacts;
    }

    public String getextSFID() {
        return ext_sf_id;
    }

    public WizardEquipment getEquipment() {
        return equipment;
    }

    public String getLastUpdate() {
        return last_updated;
    }
}
