
package View.Sites.EditSite.A_History.ReportVerification.ReportDates;

import java.util.ArrayList;
import java.util.List;


public enum EnumDateTypes {
    MNTH("MNTH", 0),
    YTD("YTD", 1),
    LM("LM", 2),
    L12("L12", 3);

    private String name;
    private int dropDownIndex;

    EnumDateTypes(String name, int dropDownIndex) {
        this.name = name;
        this.dropDownIndex = dropDownIndex;
    }

    public String getName() {
        return this.name;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    static public List<String> getDateTypes() {
        List<String> names = new ArrayList<>();
        for (EnumDateTypes month : EnumDateTypes.values()) {
            names.add(month.getName());
        }
        return names;
    }

    static public EnumDateTypes getDateTypeFromName(String name) {
        for (EnumDateTypes res : EnumDateTypes.values()) {
            if (res.getName().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }
}

