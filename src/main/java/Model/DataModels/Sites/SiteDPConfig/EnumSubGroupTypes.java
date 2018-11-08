package Model.DataModels.Sites.SiteDPConfig;

import java.util.ArrayList;
import java.util.List;

public enum EnumSubGroupTypes {
    all("All", 0),
    chiller("Chiller", 1),
    coolTower("Cooling Tower", 2),
    condenser("Condenser Water Pump", 3),
    prime("Primary Chilled Water Pump", 4),
    second("Secondary Chilled Water Pump", 5),
    heat("Heat Exchanger", 6),
    basSystem("BAS System", 7),
    basDetermined("BAS Determined", 8),
    optimization("Optimization", 9);

    private final String friendlyName;
    private final int dropDownIndex;

    EnumSubGroupTypes(String name, int dropDownIndex) {
        this.friendlyName = name;
        this.dropDownIndex = dropDownIndex;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    static public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (EnumSubGroupTypes enumVal : EnumSubGroupTypes.values()) {
            names.add(enumVal.getFriendlyName());
        }
        return names;
    }

    static public EnumSubGroupTypes getEnumFromFriendlyName(String name) {
        for (EnumSubGroupTypes enumVal : EnumSubGroupTypes.values()) {
            if (enumVal.getFriendlyName().compareTo(name) == 0) {
                return enumVal;
            }
        }
        return null;
    }

}
