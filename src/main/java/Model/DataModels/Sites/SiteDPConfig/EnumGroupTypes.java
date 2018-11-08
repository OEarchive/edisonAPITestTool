
package Model.DataModels.Sites.SiteDPConfig;

import java.util.ArrayList;
import java.util.List;


public enum EnumGroupTypes {
    equipment("equipment", 0),
    basSystem("basSystem", 1),
    basDetermined("basDetermined", 2),
    optimization("optimization", 3),
    summary("summary", 4);

    private final String name;
    private final int dropDownIndex;

    EnumGroupTypes(String name, int dropDownIndex) {
        this.name = name;
        this.dropDownIndex = dropDownIndex;
    }

    public String getGroupName() {
        return this.name;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    static public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (EnumGroupTypes enumVal : EnumGroupTypes.values()) {
            names.add(enumVal.getGroupName());
        }
        return names;
    }

    static public EnumGroupTypes getEnumFromName(String name) {
        for (EnumGroupTypes enumVal : EnumGroupTypes.values()) {
            if (enumVal.getGroupName().compareTo(name) == 0) {
                return enumVal;
            }
        }
        return null;
    }
}
