package Model.DataModels.Sites;

import java.util.ArrayList;
import java.util.List;

public enum EnumKPI {

    plant("plant", 0),
    energy("energy", 1),
    money("money", 2),
    co2("co2", 3),
    water("water", 4),
    chiller("chiller", 5),
    optimization("optimization", 6);

    private String name;
    private int dropDownIndex;

    EnumKPI(String name, int dropDownIndex) {
        this.name = name;
        this.dropDownIndex = dropDownIndex;

    }

    public String getName() {
        return this.name;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    static public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (EnumKPI res : EnumKPI.values()) {
            names.add(res.getName());
        }
        return names;
    }

    static public EnumKPI getEnumFromName(String name) {
        for (EnumKPI res : EnumKPI.values()) {
            if (res.getName().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }

}
