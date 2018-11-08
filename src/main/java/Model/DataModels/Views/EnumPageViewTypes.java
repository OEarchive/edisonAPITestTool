
package Model.DataModels.Views;

import java.util.ArrayList;
import java.util.List;


public enum EnumPageViewTypes {
    PlantOverview("plant_overview"),
    Optimization("optimization");

    private String edisonName;


    EnumPageViewTypes(String name) {
        this.edisonName = name;

    }

    public String getEdisonName() {
        return this.edisonName;
    }
    
    static public List<String> getDropdownNames() {
        List<String> names = new ArrayList<>();
        for (EnumPageViewTypes res : EnumPageViewTypes.values()) {
            names.add(res.name());
        }
        return names;
    }


    static public List<String> getEdisonNames() {
        List<String> names = new ArrayList<>();
        for (EnumPageViewTypes res : EnumPageViewTypes.values()) {
            names.add(res.getEdisonName());
        }
        return names;
    }

    static public EnumPageViewTypes getEnumFromName(String name) {
        for (EnumPageViewTypes res : EnumPageViewTypes.values()) {
            if (res.getEdisonName().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }
    
    static public EnumPageViewTypes getEnumFromDropdownName(String name) {
        for (EnumPageViewTypes res : EnumPageViewTypes.values()) {
            if (res.name().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }
}