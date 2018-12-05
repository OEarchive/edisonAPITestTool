
package View.Sites.EditSite.A_History.GenTeslaSiteFromEdison.TeslaEquipmentTable;

import java.util.ArrayList;
import java.util.List;

public enum EnumTeslaEquipTableColumns {
    Name(0, "Name"),
    EquipType(1,"EquipType"),
    ShortName(2,"ShortName"),
    Make(3, "Make"),
    Model(4, "Model");

    private final String friendlyName;
    private final int columnNumber;

    EnumTeslaEquipTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumTeslaEquipTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumTeslaEquipTableColumns v : EnumTeslaEquipTableColumns.values()) {
            if (v.getColumnNumber() == colNumber) {
                return v;
            }
        }
        return null;
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public static List<String> getColumnNames() {
        List<String> names = new ArrayList<>();
        for (EnumTeslaEquipTableColumns v : EnumTeslaEquipTableColumns.values()) {
            names.add(v.getFriendlyName());
        }
        return names;
    }
}
