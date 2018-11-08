
package Model.DataModels.Graph;

import java.util.ArrayList;
import java.util.List;

public enum EnumEdsionEdgeTypes {

    hierarchy("hierarchy", 0),
    weather_association("weather_association", 1);

    private String edisonName;
    private int dropDownIndex;

    EnumEdsionEdgeTypes(String name, int dropDownIndex) {
        this.edisonName = name;
        this.dropDownIndex = dropDownIndex;

    }

    public String getEdisonName() {
        return this.edisonName;
    }

    static public List<String> getDropdownNames() {
        List<String> dropdownNames = new ArrayList<>();
        for (EnumGraphNodeTypes et : EnumGraphNodeTypes.values()) {
            dropdownNames.add(et.name());
        }
        return dropdownNames;
    }

    public int getDropdownIndex() {
        return this.dropDownIndex;
    }

    static public EnumEdsionEdgeTypes getEdgeTypeFromDropDownName(String name) {

        for (EnumEdsionEdgeTypes et : EnumEdsionEdgeTypes.values()) {
            if (et.name().compareTo(name) == 0) {
                return et;
            }
        }
        return null;
    }

    static public EnumEdsionEdgeTypes getEdgeTypeFromEdisonNodeName(String name) {

        for (EnumEdsionEdgeTypes et : EnumEdsionEdgeTypes.values()) {
            if (et.getEdisonName().compareTo(name) == 0) {
                return et;
            }
        }
        return null;
    }

}
