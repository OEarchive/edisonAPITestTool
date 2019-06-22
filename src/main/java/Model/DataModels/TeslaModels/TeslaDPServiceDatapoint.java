package Model.DataModels.TeslaModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeslaDPServiceDatapoint {

    @JsonProperty("id")
    private String id;

    @JsonProperty("siteId")
    private String siteId;

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("stationId")
    private String stationId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("calculation")
    private String calculation;
    
    @JsonProperty("editable")
    private Boolean editable;

    @JsonProperty("minimumResolution")
    private String minimumResolution;

    @JsonProperty("type")
    private String pointType;

    @JsonProperty("unitOfMeasurement")
    private String unitOfMeasurement;

    @JsonProperty("rollupAggregation")
    private String rollupAggregation;

    @JsonIgnore
    private boolean subscribedFlag;

    @JsonIgnore
    private Object liveDataValue;

    public String getId() {
        return id;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getStationId() {
        return stationId;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public Object getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public String getPointType() {
        return pointType;
    }

    public String getCalculation() {
        return calculation;
    }
    
    public Boolean getEditable() {
        return editable;
    }

    public String getMinimumResolution() {
        return minimumResolution;
    }

    public String getRollupAggregation() {
        return rollupAggregation;
    }

    public void setSubScribedFlag(boolean subscribedFlag) {
        this.subscribedFlag = subscribedFlag;
    }

    public boolean getSubscribedFlag() {
        return subscribedFlag;
    }

    public void setLiveDataValue(Object liveDataValue) {
        this.liveDataValue = liveDataValue;
    }

    public Object getLiveDataValue() {
        return liveDataValue;
    }
}
