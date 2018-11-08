package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PlantEquipment {

    @JsonProperty("communicationProtocol")
    private String communicationProtocol;

    @JsonProperty("chillers")
    private List<Chiller> chillers;

    @JsonProperty("coolingTowers")
    private List<Pump> coolingTowers;

    @JsonProperty("condenserWaterPumps")
    private List<Pump> condenserWaterPumps;

    @JsonProperty("primaryChilledWaterPumps")
    private List<Pump> primaryChilledWaterPumps;

    @JsonProperty("secondaryChilledWaterPumps")
    private List<Pump> secondaryChilledWaterPumps;

    @JsonProperty("heatExchangers")
    private List<HeatExchanger> heatExchangers;

    public String getCommProtocol() {
        return this.communicationProtocol;
    }

    @JsonIgnore
    public void setCommProtocol(String communication_protocol) {
        this.communicationProtocol = communication_protocol;
    }

    
    
    public List<Chiller> getChillers() {
        return this.chillers;
    }

    @JsonIgnore
    public void setChillers(List<Chiller> chillers) {
        this.chillers = chillers;
    }

    public List<Pump> getCoolingTowers() {
        return this.coolingTowers;
    }

    @JsonIgnore
    public void setCoolingTowers(List<Pump> cooling_towers) {
        this.coolingTowers = cooling_towers;
    }

    public List<Pump> getCondenserWaterPumps() {
        return this.condenserWaterPumps;
    }

    @JsonIgnore
    public void setCondenserWaterPumps(List<Pump> condenser_water_pumps) {
        this.condenserWaterPumps = condenser_water_pumps;
    }

    public List<Pump> getPrimaryWaterPumps() {
        return this.primaryChilledWaterPumps;
    }

    @JsonIgnore
    public void setPrimaryWaterPumps(List<Pump> primary_chilled_water_pumps) {
        this.primaryChilledWaterPumps = primary_chilled_water_pumps;
    }

    public List<Pump> getSecondaryWaterPumps() {
        return this.secondaryChilledWaterPumps;
    }

    @JsonIgnore
    public void setSecondaryWaterPumps(List<Pump> secondary_chilled_water_pumps) {
        this.secondaryChilledWaterPumps = secondary_chilled_water_pumps;
    }

    public List<HeatExchanger> getHeatExchangers() {
        return this.heatExchangers;
    }

    @JsonIgnore
    public void setHeatExchangers(List<HeatExchanger> heat_exchangers) {
        this.heatExchangers = heat_exchangers;
    }

}
