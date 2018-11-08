package Model.DataModels.ReportVerification.CalcPointClassification;

import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointMinimumResolution;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum EnumCalcPointFilter {

    kWTon("kWTon", 0, EnumCalcPointMinimumResolution.INSTANT, true  ),
    kWTon_Weight("kWTon_Weight", 1, EnumCalcPointMinimumResolution.INSTANT, true ),
    ChillerkWTon("ChillerkWTon", 2, EnumCalcPointMinimumResolution.INSTANT, true ),
    ChillerkW("ChillerkW", 3, EnumCalcPointMinimumResolution.INSTANT, false ),
    kWh("kWh", 4, EnumCalcPointMinimumResolution.HOURLY, true ),
    TotalkWh("TotalkWh", 5, EnumCalcPointMinimumResolution.HOURLY, true ),
    TotalkW("TotalkW", 6, EnumCalcPointMinimumResolution.INSTANT, false ),
    TotalTon("TotalTon", 7, EnumCalcPointMinimumResolution.INSTANT, true ),
    Ton("Ton", 8, EnumCalcPointMinimumResolution.INSTANT, true ),
    TonHours("TonHours", 9, EnumCalcPointMinimumResolution.HOURLY, true ),
    BaselinekW("BaselinekW", 10, EnumCalcPointMinimumResolution.INSTANT, true ),
    BaselinekWh("BaselinekWh", 11, EnumCalcPointMinimumResolution.HOURLY, true ),
    BaselinekWTon("BaselinekWTon", 12, EnumCalcPointMinimumResolution.INSTANT, true ),
    BaselinekWTon_Weight("BaselinekWTon_Weight", 13, EnumCalcPointMinimumResolution.INSTANT, true ),
    BaselineDollarsCost("BaselineDollarsCost", 14, EnumCalcPointMinimumResolution.HOURLY, true ),
    DollarsCost("DollarsCost", 15, EnumCalcPointMinimumResolution.HOURLY, false ),
    DollarsSaved("DollarsSaved", 16, EnumCalcPointMinimumResolution.HOURLY, true ),
    BaselineCO2Produced("BaselineCO2Produced", 17, EnumCalcPointMinimumResolution.HOURLY, true ),
    CO2Produced("CO2Produced", 18, EnumCalcPointMinimumResolution.HOURLY, false ),
    CO2Saved("CO2Saved", 19, EnumCalcPointMinimumResolution.HOURLY, true ),
    kWDelta("kWDelta", 20, EnumCalcPointMinimumResolution.INSTANT, true ),
    kWhDelta("kWhDelta", 21, EnumCalcPointMinimumResolution.HOURLY, true ),
    PercentOptimized("PercentOptimized", 22, EnumCalcPointMinimumResolution.INSTANT, true ),
    PercentPartiallyOptimized("PercentPartiallyOptimized", 23, EnumCalcPointMinimumResolution.INSTANT, true ),
    PercentOptimizationDisabled("PercentOptimizationDisabled", 24, EnumCalcPointMinimumResolution.INSTANT, true ),
    PercentPlantOff("PercentPlantOff", 25, EnumCalcPointMinimumResolution.INSTANT, true ),
    PercentBASCommunicationFailure("PercentBASCommunicationFailure", 26, EnumCalcPointMinimumResolution.INSTANT, true ),
    Optimized("Optimized", 27, EnumCalcPointMinimumResolution.INSTANT, true ),
    PartiallyOptimized("PartiallyOptimized", 28, EnumCalcPointMinimumResolution.INSTANT, true ),
    OptimizationDisabled("OptimizationDisabled", 29, EnumCalcPointMinimumResolution.INSTANT, true ),
    PlantOff("PlantOff", 30, EnumCalcPointMinimumResolution.INSTANT, true ),
    BASCommunicationFailure("BASCommunicationFailure", 31, EnumCalcPointMinimumResolution.INSTANT, true ),
    OptimizationStatus("OptimizationStatus", 32, EnumCalcPointMinimumResolution.INSTANT, true ),
    PlantCOP("PlantCOP", 33, EnumCalcPointMinimumResolution.INSTANT, true ),
    PlantCOP_Weight("PlantCOP_Weight", 34, EnumCalcPointMinimumResolution.INSTANT, true ),
    ChillersRunning("ChillersRunning", 35, EnumCalcPointMinimumResolution.INSTANT, false ),
    PercentTotalLoad("PercentTotalLoad", 36, EnumCalcPointMinimumResolution.INSTANT, false ),
    ChillerkWh("ChillerkWh", 37, EnumCalcPointMinimumResolution.HOURLY, true ),
    ChillerRunHours("ChillerRunHours", 38, EnumCalcPointMinimumResolution.HOURLY, false ),
    PercentMissingData("PercentMissingData", 39, EnumCalcPointMinimumResolution.INSTANT, false);
    
    private final String calculatedPointName;
    private final int dropDownIndex;
    private final EnumCalcPointMinimumResolution minResolution;
    private final boolean requiresAllValues;
   
    EnumCalcPointFilter(String calculatedPointName, int dropDownIndex, EnumCalcPointMinimumResolution minResolution, boolean requiresAllValues ) {
        this.calculatedPointName = calculatedPointName;
        this.dropDownIndex = dropDownIndex;
        this.minResolution = minResolution;
        this.requiresAllValues = requiresAllValues;
        
    }

    public String getCalculatedPointName() {
        return this.calculatedPointName;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }
    
    public EnumCalcPointMinimumResolution getMinResolution(){
        return this.minResolution;
    }
    
    public boolean getRequiresAllValues(){
        return this.requiresAllValues;
    }
    
    static public List<String> getPointNames() {
        List<String> pointNames = new ArrayList<>();
        for (EnumCalcPointFilter cp : EnumCalcPointFilter.values()) {
            pointNames.add(cp.getCalculatedPointName());
        }
        
        Collections.sort(pointNames);
        return pointNames;
    }

    static public EnumCalcPointFilter getEnumFromPointName(String pointName) {
        for (EnumCalcPointFilter cp : EnumCalcPointFilter.values()) {
            if (cp.getCalculatedPointName().compareTo(pointName) == 0) {
                return cp;
            }
        }
        throw new NoSuchFieldError("no calcluated point for: " + pointName);

    }

    static public Boolean isCalculatedPoint(String pointName) {
        for (EnumCalcPointFilter cp : EnumCalcPointFilter.values()) {
            if (cp.getCalculatedPointName().compareTo(pointName) == 0) {
                return true;
            }
        }
        return false;

    }

}
