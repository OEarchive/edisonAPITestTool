
package View.Sites.EditSite.A_History.PushLiveData;

import java.util.ArrayList;
import java.util.List;


public enum EnumPushLiveDataTableColumns {

    PointName(0, "PointName"),
    PointType(1, "PointType"),
    PointValue(2, "PointValue");

    private final String friendlyName;
    private final int columnNumber;

    EnumPushLiveDataTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumPushLiveDataTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumPushLiveDataTableColumns v : EnumPushLiveDataTableColumns.values()) {
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

        for (EnumPushLiveDataTableColumns v : EnumPushLiveDataTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}
