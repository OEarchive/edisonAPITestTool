package Model.DataModels.TeslaModels.CreateTeslaSiteModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeslaGenEquipment {

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("type")
    private String equipmentType;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;
    
    
    @JsonIgnore
    public void setName( String name ){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setShortName( String shortName ){
        this.shortName = shortName;
    }
    public String getShortName() {
        return shortName;
    }

    @JsonIgnore
    public void setEquipmentType( String equipmentType ){
        this.equipmentType = equipmentType;
    }
    public Object getEquipmentType() {
        return equipmentType;
    }

    @JsonIgnore
    public void setMake( String make ){
        this.make = make;
    }
    public String getMake() {
        return make;
    }

    @JsonIgnore
    public void setModel( String model ){
        this.model = model;
    }
    public String getModel() {
        return model;
    }

}
