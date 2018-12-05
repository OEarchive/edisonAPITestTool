package Model.DataModels.TeslaModels.CreateTeslaSiteModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeslaPostEquipResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("type")
    private String equipmentType;

    @JsonProperty("stationId")
    private String stationId;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public Object getEquipmentType() {
        return equipmentType;
    }

    public String getStationId() {
        return stationId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }
}
