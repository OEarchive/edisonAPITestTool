package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WizardEquipment {

    @JsonProperty("commumication_protocol")
    private String commumication_protocol;

    @JsonProperty("chillers")
    private List<WizardChiller> chillers;

    @JsonProperty("cooling_towers")
    private List<WizardPump> cooling_towers;

    @JsonProperty("condenser_water_pumps")
    private List<WizardPump> condenser_water_pumps;

    @JsonProperty("primary_chilled_water_pumps")
    private List<WizardPump> primary_chilled_water_pumps;

    @JsonProperty("secondary_chilled_water_pumps")
    private List<WizardPump> secondary_chilled_water_pumps;

    @JsonProperty("heat_exchangers")
    private List<WizardHeatExchanger> heat_exchangers;

    public String getCommProtocol() {
        return commumication_protocol;
    }

    public  List<WizardChiller> getChillers() {
        return chillers;
    }

    public List<WizardPump> getCoolingTowers() {
        return cooling_towers;
    }
    
        public List<WizardPump> getCondenserWaterPumps() {
        return condenser_water_pumps;
    }

    public List<WizardPump> getPrimaryChilledWaterPumps() {
        return primary_chilled_water_pumps;
    }

    public List<WizardPump> getSecondaryChilledWaterPumps() {
        return secondary_chilled_water_pumps;
    }
    
    
    public List<WizardHeatExchanger> getHeatExchangers() {
        return heat_exchangers;
    }
}
