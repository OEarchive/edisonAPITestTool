
package View.Sites.EditSite.A_History.Tesla.PushToTesla.SparsePointPusher.SparsePointsTable;

import java.util.ArrayList;
import java.util.List;


public enum EnumSparsePointsTableColumns {

    EdisonName(0,"EdisonName"),
    EdsionSid(1,"Sid"),
    TeslaName(2, "TeslaName"),
    TeslaType(3, "TeslaType"),
    TeslaID(4, "TesalID"),
    PointValue(5, "PointValue");

    private final String friendlyName;
    private final int columnNumber;

    EnumSparsePointsTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumSparsePointsTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumSparsePointsTableColumns v : EnumSparsePointsTableColumns.values()) {
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
        for (EnumSparsePointsTableColumns v : EnumSparsePointsTableColumns.values()) {
            names.add(v.getFriendlyName());
        }
        return names;
    }
}

