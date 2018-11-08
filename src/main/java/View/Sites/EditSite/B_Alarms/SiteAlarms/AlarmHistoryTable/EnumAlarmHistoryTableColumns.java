
package View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmHistoryTable;

import java.util.ArrayList;
import java.util.List;


public enum EnumAlarmHistoryTableColumns {

    AlarmId(0, "AlarmId"),
    Name(1,"Name"),
    StartDate(2, "StartDate"),
    EndDate(3, "EndDate"),
    State(4, "State");

    private final String colName;
    private final int colNumber;

    EnumAlarmHistoryTableColumns(int columnNumber, String colName) {
        this.colName = colName;
        this.colNumber = columnNumber;
    }

    public static EnumAlarmHistoryTableColumns getEnumFromColNumber(int colNumber) {
        for (EnumAlarmHistoryTableColumns v : EnumAlarmHistoryTableColumns.values()) {
            if (v.getColNumber() == colNumber) {
                return v;
            }
        }
        return null;
    }

    public int getColNumber() {
        return this.colNumber;
    }

    public String getColName() {
        return this.colName;
    }

    public static List<String> getColNames() {
        List<String> names = new ArrayList<>();
        for (EnumAlarmHistoryTableColumns v : EnumAlarmHistoryTableColumns.values()) {
            names.add(v.getColName());
        }
        return names;
    }
}