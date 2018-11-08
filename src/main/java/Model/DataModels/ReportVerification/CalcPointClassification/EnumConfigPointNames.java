
package Model.DataModels.ReportVerification.CalcPointClassification;

import java.util.ArrayList;
import java.util.List;


public enum EnumConfigPointNames {

       TotalCapacity("TotalCapacity"),
        MinimumChilledWaterFlow("MinimumChilledWaterFlow"),
        BlendedUtilityRate("BlendedUtilityRate"),
        CO2EmissionFactor("CO2EmissionFactor");
    
    
    private final String configPointName;

   
    EnumConfigPointNames(String configPointName ) {
        this.configPointName = configPointName;

        
    }

    public String getConfigPointName() {
        return this.configPointName;
    }

    static public List<String> getPointNames() {
        List<String> pointNames = new ArrayList<>();
        for (EnumConfigPointNames cp : EnumConfigPointNames.values()) {
            pointNames.add(cp.getConfigPointName());
        }
        return pointNames;
    }

    static public EnumConfigPointNames getEnumFromPointName(String pointName) {
        for (EnumConfigPointNames cp : EnumConfigPointNames.values()) {
            if (cp.getConfigPointName().compareTo(pointName) == 0) {
                return cp;
            }
        }
        throw new NoSuchFieldError("no config point for: " + pointName);

    }

}