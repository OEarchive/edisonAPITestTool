
package Model.DataModels.TeslaModels.CreateTeslaSiteModel;

import java.util.ArrayList;
import java.util.List;

public enum EnumEdisonGroupTypes {
    
    Chiller("Chillers", "chiller"),
    PCWP("Primary Chilled Water Pumps", "primary-chilled-water-pump"),
    SCWP("Secondary Chilled Water Pumps", "secondary-chilled-water-pump"),
    CWP("Condenser Water Pumps", "condenser-water-pump"),
    CT("Cooling Towers", "cooling-tower"),
    HX("Heat Exchangers", "heat-exchanger");

    private final String friendlyName;
    private final String typeName;

    EnumEdisonGroupTypes(String friendlyName, String typeName) {
        this.friendlyName = friendlyName;
        this.typeName = typeName;

    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public String getTypeName() {
        return this.typeName;
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
