package View.Sites.EditSite.C_DatapointConfig;

import java.util.ArrayList;
import java.util.List;

public enum EnumDPConfigTableColumns {

    SubGroup(0, "SubGroup"),
    EquipNumber(1, "EquipNumber"),
    StPointName(2, "StPointName"),
    StAtrrName(3, "StAtrrName"),
    GraphAttrName(4, "GraphAttrName"),
    Unit(5, "Unit"),
    ValueType(6, "ValueType"),
    Address(7, "Address"),
    PointType(8, "PointType"),
    DisplayName(9, "DisplayName");

    private final String friendlyName;
    private final int columnNumber;

    EnumDPConfigTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumDPConfigTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumDPConfigTableColumns v : EnumDPConfigTableColumns.values()) {
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

        for (EnumDPConfigTableColumns v : EnumDPConfigTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}
