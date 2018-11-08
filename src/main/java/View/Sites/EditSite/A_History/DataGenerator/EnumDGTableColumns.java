package View.Sites.EditSite.A_History.DataGenerator;

import java.util.ArrayList;
import java.util.List;

public enum EnumDGTableColumns {

    Scope(0, "Scope"),
    Sid(1,"Sid"),
    PointName(2, "PointName"),
    MinValue(3, "MinValue"),
    MaxValue(4, "MaxValue"),
    Pattern(5, "Pattern"),
    Period(6, "Period"),
    Offset(7, "Offset"),
    PointType(8, "PointType"),
    UOM(9, "UOM"),
    StAttrName(10, "StAttrName"),
    GraphAttrName(11, "GraphAttrName");

    private final String friendlyName;
    private final int columnNumber;

    EnumDGTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumDGTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumDGTableColumns v : EnumDGTableColumns.values()) {
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

        for (EnumDGTableColumns v : EnumDGTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}
