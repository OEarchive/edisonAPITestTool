package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EdgePlantInfo {

    @JsonProperty("blendedUtilityRate")
    private double blendedUtilityRate;

    @JsonProperty("chillerPlantCoolingCapacity")
    private double chillerPlantCoolingCapacity;

    @JsonProperty("minimumChilledWaterFlow")
    private double minimumChilledWaterFlow;

    @JsonProperty("chilledWaterDistType")
    private String chilledWaterDistType;

    @JsonProperty("condenserWaterDistType")
    private String condenserWaterDistType;

    @JsonProperty("currentDpSetPoint")
    private double currentDpSetPoint;

    //
    @JsonProperty("chilledWaterMinimumDp")
    private double chilledWaterMinimumDp;

    @JsonProperty("chilledWaterMaximumDp")
    private double chilledWaterMaximumDp;

    @JsonProperty("chilledWaterDpOsaMin")
    private double chilledWaterDpOsaMin;

    @JsonProperty("chilledWaterDpOsaMax")
    private double chilledWaterDpOsaMax;

    @JsonProperty("chilledWaterStResetEnable")
    private boolean chilledWaterStResetEnable;

    //
    @JsonProperty("chilledWaterSupplyTempMin")
    private double chilledWaterSupplyTempMin;

    @JsonProperty("chilledWaterSupplyTempMax")
    private double chilledWaterSupplyTempMax;

    @JsonProperty("secondaryChilledWaterPumpMinSpeed")
    private double secondaryChilledWaterPumpMinSpeed;

    @JsonProperty("primaryChilledWaterPumpMinSpeed")
    private double primaryChilledWaterPumpMinSpeed;

    @JsonProperty("currentChilledWaterStSetPoint")
    private double currentChilledWaterStSetPoint;

    //
    @JsonProperty("currentEnteringCondenserWaterSetPoint")
    private double currentEnteringCondenserWaterSetPoint;

    @JsonProperty("minimumCondWaterEnteringTemp")
    private double minimumCondWaterEnteringTemp;

    @JsonProperty("maximumCondWaterEnteringTemp")
    private double maximumCondWaterEnteringTemp;

    @JsonProperty("condWaterPumpMinSpeed")
    private double condWaterPumpMinSpeed;

    @JsonProperty("co2EmissionFactor")
    private double co2EmissionFactor;

    //
    public double getBlendedUtilityRate() {
        return this.blendedUtilityRate;
    }

    @JsonIgnore
    public void setBlendedUtilityRate(double blendedUtilityRate) {
        this.blendedUtilityRate = blendedUtilityRate;
    }

    public double getChillerPlantCoolingCapacity() {
        return this.chillerPlantCoolingCapacity;
    }

    @JsonIgnore
    public void setChillerPlantCoolingCapacity(double chillerPlantCoolingCapacity) {
        this.chillerPlantCoolingCapacity = chillerPlantCoolingCapacity;
    }

    public double getMinimumChilledWaterFlow() {
        return this.minimumChilledWaterFlow;
    }

    @JsonIgnore
    public void setMinimumChilledWaterFlow(double minimumChilledWaterFlow) {
        this.minimumChilledWaterFlow = minimumChilledWaterFlow;
    }

    public String getChilledWaterDistType() {
        return this.chilledWaterDistType;
    }

    @JsonIgnore
    public void setChilledWaterDistType(String chilledWaterDistType) {
        this.chilledWaterDistType = chilledWaterDistType;
    }

    public String getCondenserWaterDistType() {
        return this.condenserWaterDistType;
    }

    public void setCondenserWaterDistType(String condenserWaterDistType) {
        this.condenserWaterDistType = condenserWaterDistType;
    }

    public double getCurrentDpSetPoint() {
        return this.currentDpSetPoint;
    }

    @JsonIgnore
    public void setCurrentDpSetPoint(double currentDpSetPoint) {
        this.currentDpSetPoint = currentDpSetPoint;
    }

    public double getChilledWaterMinimumDp() {
        return this.chilledWaterMinimumDp;
    }

    @JsonIgnore
    public void setChilledWaterMinimumDp(double chilledWaterMinimumDp) {
        this.chilledWaterMinimumDp = chilledWaterMinimumDp;
    }

    public double getChilledWaterMaximumDp() {
        return this.chilledWaterMaximumDp;
    }

    @JsonIgnore
    public void setChilledWaterMaximumDp(double chilledWaterMaximumDp) {
        this.chilledWaterMaximumDp = chilledWaterMaximumDp;
    }

    public double getChilledWaterDpOsaMin() {
        return this.chilledWaterDpOsaMin;
    }

    @JsonIgnore
    public void setChilledWaterDpOsaMin(double chilledWaterDpOsaMin) {
        this.chilledWaterDpOsaMin = chilledWaterDpOsaMin;
    }

    public double getChilledWaterDpOsaMax() {
        return this.chilledWaterDpOsaMax;
    }

    @JsonIgnore
    public void setChilledWaterDpOsaMax(double chilledWaterDpOsaMax) {
        this.chilledWaterDpOsaMax = chilledWaterDpOsaMax;
    }

    public boolean getChilledWaterStResetEnable() {
        return this.chilledWaterStResetEnable;
    }

    @JsonIgnore
    public void setChilledWaterStResetEnable(boolean chilledWaterStResetEnable) {
        this.chilledWaterStResetEnable = chilledWaterStResetEnable;
    }

    public double getChilledWaterSupplyTempMin() {
        return this.chilledWaterSupplyTempMin;
    }

    @JsonIgnore
    public void setChilledWaterSupplyTempMin(double chilledWaterSupplyTempMin) {
        this.chilledWaterSupplyTempMin = chilledWaterSupplyTempMin;
    }

    public double getChilledWaterSupplyTempMax() {
        return this.chilledWaterSupplyTempMax;
    }

    @JsonIgnore
    public void setChilledWaterSupplyTempMax(double chilledWaterSupplyTempMax) {
        this.chilledWaterSupplyTempMax = chilledWaterSupplyTempMax;
    }

    public double getSecondaryChilledWaterPumpMinSpeed() {
        return this.secondaryChilledWaterPumpMinSpeed;
    }

    @JsonIgnore
    public void setSecondaryChilledWaterPumpMinSpeed(double secondaryChilledWaterPumpMinSpeed) {
        this.secondaryChilledWaterPumpMinSpeed = secondaryChilledWaterPumpMinSpeed;
    }

    public double getPrimaryChilledWaterPumpMinSpeed() {
        return this.primaryChilledWaterPumpMinSpeed;
    }

    @JsonIgnore
    public void setPrimaryChilledWaterPumpMinSpeed(double primaryChilledWaterPumpMinSpeed) {
        this.primaryChilledWaterPumpMinSpeed = primaryChilledWaterPumpMinSpeed;
    }

    public double getCurrentChilledWaterStSetPoint() {
        return this.currentChilledWaterStSetPoint;
    }

    @JsonIgnore
    public void setCurrentChilledWaterStSetPoint(double currentChilledWaterStSetPoint) {
        this.currentChilledWaterStSetPoint = currentChilledWaterStSetPoint;
    }

    public double getCurrentEnteringCondenserWaterSetPoint() {
        return this.currentEnteringCondenserWaterSetPoint;
    }

    @JsonIgnore
    public void setCurrentEnteringCondenserWaterSetPoint(double currentEnteringCondenserWaterSetPoint) {
        this.currentEnteringCondenserWaterSetPoint = currentEnteringCondenserWaterSetPoint;
    }

    public double getMinimumCondWaterEnteringTemp() {
        return this.minimumCondWaterEnteringTemp;
    }

    @JsonIgnore
    public void setMinimumCondWaterEnteringTemp(double minimumCondWaterEnteringTemp) {
        this.minimumCondWaterEnteringTemp = minimumCondWaterEnteringTemp;
    }

    public double getMaximumCondWaterEnteringTemp() {
        return this.maximumCondWaterEnteringTemp;
    }

    @JsonIgnore
    public void setMaximumCondWaterEnteringTemp(double maximum_cond_water_entering_temp) {
        this.maximumCondWaterEnteringTemp = maximum_cond_water_entering_temp;
    }

    public double getCondWaterPumpMinSpeed() {
        return this.condWaterPumpMinSpeed;
    }

    @JsonIgnore
    public void setCondWaterPumpMinSpeed(double condWaterPumpMinSpeed) {
        this.condWaterPumpMinSpeed = condWaterPumpMinSpeed;
    }

    public double getCo2EmissionFactor() {
        return this.co2EmissionFactor;
    }

    @JsonIgnore
    public void setCO2EmissionFactor(double co2EmissionFactor) {
        this.co2EmissionFactor = co2EmissionFactor;
    }

}
