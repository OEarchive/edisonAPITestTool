package Model.DataModels.Datapoints.simulator.CalculatedPoints;

import java.util.ArrayList;
import java.util.List;

public enum EnumCalcPoints {

    BASCommunicationFailure("BASCommunicationFailure", 1),
    BaselineCO2Produced("BaselineCO2Produced", 2),
    BaselineDollarsCost("BaselineDollarsCost", 3),
    BaselinekW("BaselinekW", 4),
    BaselinekWh("BaselinekWh", 5),
    BaselinekWTon_Weight("BaselinekWTon_Weight", 6),
    BaselinekWTon("BaselinekWTon", 7),
    BlendedUtilityRate("BlendedUtilityRate", 8),
    ChillerkW("ChillerkW", 9),
    ChillerkWTon("ChillerkWTon", 10),
    CO2EmissionFactor("CO2EmissionFactor", 11),
    CO2Produced("CO2Produced", 12),
    CO2Saved("CO2Saved", 13),
    DollarsCost("DollarsCost", 14),
    DollarsSaved("DollarsSaved", 15),
    kWDelta("kWDelta", 16),
    kWh("kWh", 17),
    kWhDelta("kWhDelta", 18),
    kWTon_Weight("kWTon_Weight", 19),
    kWTon("kWTon", 20),
    MinimumChilledWaterFlow("MinimumChilledWaterFlow", 21),
    OptimizationDisabled("OptimizationDisabled", 22),
    OptimizationStatus("OptimizationStatus", 23),
    Optimized("Optimized", 24),
    PartiallyOptimized("PartiallyOptimized", 25),
    PercentBASCommunicationFailure("PercentBASCommunicationFailure", 26),
    PercentOptimizationDisabled("PercentOptimizationDisabled", 27),
    PercentOptimized("PercentOptimized", 28),
    PercentPartiallyOptimized("PercentPartiallyOptimized", 29),
    PercentPlantOff("PercentPlantOff", 30),
    PlantOff("PlantOff", 31),
    Ton("Ton", 32),
    TonHours("TonHours", 33),
    TotalCapacity("TotalCapacity", 34),
    TotalkW("TotalkW", 35),
    TotalkWh("TotalkWh", 36),
    TotalTon("TotalTon", 37);

    private final String friendlyName;
    private final int dropDownIndex;

    EnumCalcPoints(String friendlyName, int dropDownIndex) {
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
        for (EnumCalcPoints res : EnumCalcPoints.values()) {
            friendlyNames.add(res.getFreindlyName());
        }
        return friendlyNames;
    }

    static public EnumCalcPoints getEnumFromFriendlyName(String friendlyName) {
        for (EnumCalcPoints res : EnumCalcPoints.values()) {
            if (res.getFreindlyName().compareTo(friendlyName) == 0) {
                return res;
            }
        }
        return null;
    }

    static public Boolean isCalculatedPoint(String friendlyName) {
        for (EnumCalcPoints res : EnumCalcPoints.values()) {
            if (res.getFreindlyName().compareTo(friendlyName) == 0) {
                return true;
            }
        }
        return false;

    }

}
