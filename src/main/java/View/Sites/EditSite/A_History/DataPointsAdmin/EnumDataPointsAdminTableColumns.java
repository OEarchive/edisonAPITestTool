
package View.Sites.EditSite.A_History.DataPointsAdmin;

import java.util.ArrayList;
import java.util.List;


public enum EnumDataPointsAdminTableColumns {

    PointName(0, "PointName"),
    PointType(1,"PointType"),
    CurrentValue(2, "CurrentValue"),
    CurrentTimestamp(3, "CurrentTimestamp"),
    PointID(4, "PointID");

    private final String friendlyName;
    private final int columnNumber;

    EnumDataPointsAdminTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumDataPointsAdminTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumDataPointsAdminTableColumns v : EnumDataPointsAdminTableColumns.values()) {
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

        for (EnumDataPointsAdminTableColumns v : EnumDataPointsAdminTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}


