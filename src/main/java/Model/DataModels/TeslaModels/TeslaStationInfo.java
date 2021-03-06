package Model.DataModels.TeslaModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TeslaStationInfo {

    @JsonProperty("id")
    private String stationId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("activationCode")
    private String activationCode;

    @JsonProperty("activatedAt")
    private String activatedAt;

    @JsonProperty("expiresAt")
    private String expiresAt;

    @JsonProperty("siteId")
    private String siteId;

    @JsonProperty("salesforceId")
    private String salesforceId;

    @JsonProperty("commissionedAt")
    private String commissionedAt;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("plantId")
    private String plantId;

    @JsonProperty("baselineEnabled")
    private boolean baselineEnabled;

    @JsonProperty("regenerationAllowed")
    private boolean regenerationAllowed;

    @JsonProperty("atomEnabled")
    private boolean atomEnabled;

    @JsonProperty("productType")
    private String productType;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("address")
    private String address;

    @JsonProperty("timeZone")
    private String timeZone;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("longitude")
    private String longitude;

    @JsonProperty("dataPoints")
    private List<TeslaStationInfoDataPoint> datapoints;

    @JsonProperty("equipments")
    private List<TeslaEquipment> equipments;

    public String getStationId() {
        return stationId;
    }

    public String getName() {
        return name;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public String getActivatedAt() {
        return activatedAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getSalesforceId() {
        return salesforceId;
    }

    public String getcommissionedAt() {
        return commissionedAt;
    }

    public String getShortName() {
        return shortName;
    }

    public String getPlantID() {
        return plantId;
    }

    public boolean getBaselineEnabled() {
        return baselineEnabled;
    }

    public boolean getRegenerationAllowed() {
        return regenerationAllowed;
    }

    public boolean getAtomEnabled() {
        return atomEnabled;
    }

    public String getProductType() {
        return productType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getAddress() {
        return address;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public List<TeslaStationInfoDataPoint> getDatapoints() {
        return datapoints;
    }

    public List<TeslaEquipment> getequipments() {
        return equipments;

    }
}
