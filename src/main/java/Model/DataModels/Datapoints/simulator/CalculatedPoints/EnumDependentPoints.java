package Model.DataModels.Datapoints.simulator.CalculatedPoints;

import java.util.ArrayList;
import java.util.List;

public enum EnumDependentPoints {

    BaselinekW("BaselinekW", 1),
    BaselinekWh("BaselinekWh", 2),
    BaselinekWTon("BaselinekWTon", 3),
    BlendedUtilityRate("BlendedUtilityRate", 4),
    ChilledWaterFlow("ChilledWaterFlow", 5),
    ChilledWaterReturnTemperature("ChilledWaterReturnTemperature", 6),
    ChilledWaterSupplyTemperature("ChilledWaterSupplyTemperature", 7),
    ChillerkW("ChillerkW", 8),
    CO2EmissionFactor("CO2EmissionFactor", 9),
    kW("kW", 10),
    kWDelta("kWDelta", 11),
    kWh("kWh", 12),
    kWhDelta("kWhDelta", 13),
    kWTon("kWTon", 14),
    MinimumChilledWaterFlow("MinimumChilledWaterFlow", 15),
    OptimizationStatusEnum("OptimizationStatusEnum", 16),
    Status("Status", 17),
    Ton("Ton", 18),
    TotalCapacity("TotalCapacity", 19),
    TotalkW("TotalkW", 20),
    TotalkWh("TotalkWh", 21),
    TotalTon("TotalTon", 22);

    private final String friendlyName;
    private final int dropDownIndex;

    EnumDependentPoints(String friendlyName, int dropDownIndex) {
        this.friendlyName = friendlyName;

        this.dropDownIndex = dropDownIndex;
    }

    public String getFreindlyName() {
        return this.friendlyName;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    static public List<String> getFreindlyNames() {
        List<String> friendlyNames = new ArrayList<>();
        for (EnumDependentPoints res : EnumDependentPoints.values()) {
            friendlyNames.add(res.getFreindlyName());
        }
        return friendlyNames;
    }

    static public EnumDependentPoints getEnumFromFriendlyName(String friendlyName) {
        for (EnumDependentPoints res : EnumDependentPoints.values()) {
            if (res.getFreindlyName().compareTo(friendlyName) == 0) {
                return res;
            }
        }
        return null;
    }

    //returns true if this point is not a calulated point
    public Boolean isPurePoint() {
        for (EnumCalcPoints res : EnumCalcPoints.values()) {
            if (res.getFreindlyName().compareTo(friendlyName) == 0) {
                return false;
            }
        }
        return true;

    }
}
