
package Model.DataModels.TeslaModels.CreateTeslaSiteModel;

import java.util.ArrayList;
import java.util.List;

public enum EnumEdisonGroupTypes {
    
    Chiller("Chillers", "chiller", "chiller"),
    PCWP("Primary Chilled Water Pumps", "primary-chilled-water-pump", "primaryChilledWaterPump"),
    SCWP("Secondary Chilled Water Pumps", "secondary-chilled-water-pump", "secondaryChilledWaterPump"),
    CWP("Condenser Water Pumps", "condenser-water-pump", "condenserWaterPump"),
    CT("Cooling Towers", "cooling-tower", "coolingTower"),
    HX("Heat Exchangers", "heat-exchanger", "heatExchanger");

    private final String friendlyName;
    private final String typeName;
    private final String teslaName;

    EnumEdisonGroupTypes(String friendlyName, String typeName, String teslaName) {
        this.friendlyName = friendlyName;
        this.typeName = typeName;
        this.teslaName = teslaName;

    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public String getTypeName() {
        return this.typeName;
    }
    
    public String getTeslaName(){
        return this.teslaName;
    }

    static public List<String> getFriendlyNames() {
        List<String> names = new ArrayList<>();
        for (EnumEdisonGroupTypes groupType : EnumEdisonGroupTypes.values()) {
            names.add(groupType.getFriendlyName());
        }
        return names;
    }
    
    static public List<String> getTypeNames() {
        List<String> names = new ArrayList<>();
        for (EnumEdisonGroupTypes res : EnumEdisonGroupTypes.values()) {
            names.add(res.getTypeName());
        }
        return names;
    }

    static public EnumEdisonGroupTypes getEnumFromTypeName(String name) {
        for (EnumEdisonGroupTypes groupType : EnumEdisonGroupTypes.values()) {
            if (groupType.getTypeName().compareTo(name) == 0) {
                return groupType;
            }
        }
        return null;
    }
}
