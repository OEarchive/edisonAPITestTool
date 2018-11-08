
package Model.DataModels.Sites;

import java.util.ArrayList;
import java.util.List;


public enum EnumLicenseTypes {
    subscription("subscription", 0),
    perpetual("perpetual", 1);

    private String name;
    private int dropDownIndex;

    EnumLicenseTypes(String name, int dropDownIndex) {
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
        for (EnumLicenseTypes res : EnumLicenseTypes.values()) {
            names.add(res.getName());
        }
        return names;
    }

    static public EnumLicenseTypes getEnumFromName(String name) {
        for (EnumLicenseTypes res : EnumLicenseTypes.values()) {
            if (res.getName().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }
}
