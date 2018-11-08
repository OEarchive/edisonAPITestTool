package Model.DataModels.ReportVerification.CalcPointClassification;

import java.util.ArrayList;
import java.util.List;

public enum EnumDependentPointFilter {

    TotalCapacity("TotalCapacity", 0, "", ""),
    MinimumChilledWaterFlow("MinimumChilledWaterFlow", 1, "", ""),
    BlendedUtilityRate("BlendedUtilityRate", 2, "", ""),
    CO2EmissionFactor("CO2EmissionFactor", 3, "", ""),
    filter_chiller_kw("xxxx", 4, "e:chiller", "kW"),
    filter_all_kw("xxxx", 5, "e:", "kW"),
    filter_chiller_kWh("kWh", 6, "chiller", "kWh"),
    filter_all_kWh("kWh", 7, "e:", "kWh"),
    ChilledWaterFlow("ChilledWaterFlow", 8, "SITE_ONLY", "ChilledWaterFlow"),
    ChilledWaterReturnTemperature("ChilledWaterReturnTemperature", 9, "SITE_ONLY", ""),
    ChilledWaterSupplyTemperature("ChilledWaterSupplyTemperature",10, "SITE_ONLY", ""),
    BaselinekWTon("BaselinekWTon", 11, "", ""),
    BaselinekW("BaselinekW", 12, "", ""),
    kWTon("kWTon", 13, "", ""),
    BaselinekWh("BaselinekWh", 14, "", ""),
    kWhDelta("kWhDelta", 15, "", ""),
    OptimizationStatusEnum("OptimizationStatusEnum", 16, "", ""),
    Status("Status", 17, "", ""),
    ChillerStatus("Status", 18, "chiller", "Status"),
    filter_samples("filter_samples", 19, "", "");

    private final String dependentPointName;
    private final int dropDownIndex;
    private final String sidFilter;
    private final String associationFilter;

    EnumDependentPointFilter(String dependentPointName, int dropDownIndex, String sidFilter, String associationFilter) {
        this.dependentPointName = dependentPointName;
        this.dropDownIndex = dropDownIndex;
        this.sidFilter = sidFilter;
        this.associationFilter = associationFilter;

    }

    public String getDependentPointName() {
        return this.dependentPointName;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    public String getSidFilter(){
        return this.sidFilter;
    }
    
    public String getAssociationFilter() {
        return associationFilter;
    }

    static public List<String> getCalcPointNames() {
        List<String> friendlyNames = new ArrayList<>();
        for (EnumDependentPointFilter res : EnumDependentPointFilter.values()) {
            friendlyNames.add(res.getDependentPointName());
        }
        return friendlyNames;
    }

    static public EnumDependentPointFilter getEnumFromPointName(String pointName) {
        for (EnumDependentPointFilter res : EnumDependentPointFilter.values()) {
            if (res.getDependentPointName().compareTo(pointName) == 0) {
                return res;
            }
        }
        throw new NoSuchFieldError("no dependent point for: " + pointName);
    }

    /*
    static public boolean isDependentPoint(String pointName) {
        for (EnumRVDependentPoints res : EnumRVDependentPoints.values()) {
            if (res.getDependentPointName().compareTo(pointName) == 0) {
                return true;
            }
        }
        return false;

    }
    */

}
