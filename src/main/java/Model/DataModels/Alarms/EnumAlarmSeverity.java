package Model.DataModels.Alarms;

import java.util.ArrayList;
import java.util.List;

public enum EnumAlarmSeverity {
    Info("Info", 0),
    Low("Low", 1),
    Normal("Normal", 2),
    High("High", 3),
    Critical("Critical", 4),
    NotSet("null", 5);

    private String dropDownName;
    private int dropDownIndex;

    EnumAlarmSeverity(String dropDownName, int dropDownIndex) {
        this.dropDownName = dropDownName;
        this.dropDownIndex = dropDownIndex;

    }

    public String getDropDownName() {
        return this.dropDownName;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    static public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (EnumAlarmSeverity res : EnumAlarmSeverity.values()) {
            names.add(res.getDropDownName());
        }
        return names;
    }

    static public EnumAlarmSeverity getEnumFromName(String name) {
        for (EnumAlarmSeverity res : EnumAlarmSeverity.values()) {
            if (res.getDropDownName().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }

}
