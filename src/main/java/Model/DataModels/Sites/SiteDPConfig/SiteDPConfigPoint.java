package Model.DataModels.Sites.SiteDPConfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteDPConfigPoint {

    @JsonProperty("group")
    private String subGroup;

    @JsonProperty("equipmentNumber")
    private String equipmentNumber;

    @JsonProperty("stationPointName")
    private String stationPointName;

    @JsonProperty("stationAttributeName")
    private String stationAttributeName;

    @JsonProperty("graphAttributeName")
    private String graphAttributeName;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("valueType")
    private String valueType;

    @JsonProperty("address")
    private int address;

    @JsonProperty("pointType")
    private String pointType;

    @JsonProperty("displayName")
    private String displayName;
    
    public SiteDPConfigPoint(){
        
    }
    
    @JsonIgnore
    public SiteDPConfigPoint( SiteDPConfigPoint copyThis ){
        
    this.subGroup = copyThis.subGroup;
    this.equipmentNumber = copyThis.equipmentNumber;
    this.stationPointName = copyThis.stationPointName;
    this.stationAttributeName = copyThis.stationAttributeName;
    this.graphAttributeName = copyThis.graphAttributeName;
    this.unit = copyThis.unit;
    this.valueType = copyThis.valueType;
    this.address = copyThis.address;
    this.pointType = copyThis.pointType;
    this.displayName = copyThis.displayName;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    @JsonIgnore
    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getStationPointName() {
        return stationPointName;
    }

    @JsonIgnore
    public void setStationPointName(String stationPointName) {
        this.stationPointName = stationPointName;
    }

    public String getStationAttributeName() {
        return stationAttributeName;
    }

    public String getGraphAttributeName() {
        return graphAttributeName;
    }

    public String getUnit() {
        return unit;
    }

    public String getValueType() {
        return valueType;
    }

    public int getAddress() {
        return address;
    }

    public String getPointType() {
        return pointType;
    }

    public String getDisplayName() {
        return displayName;
    }
}
