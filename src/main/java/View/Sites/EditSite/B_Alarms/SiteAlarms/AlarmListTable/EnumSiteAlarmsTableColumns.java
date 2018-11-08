package View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmListTable;

import java.util.ArrayList;
import java.util.List;

public enum EnumSiteAlarmsTableColumns {

    Sid(0, "sid"),
    Name(1,"name"),
    State(2, "state"),
    StartDate(3, "startDate"),
    AckDate(4, "ackDate"),
    EndDate(5, "endDate");

    private final String friendlyName;

private final int columnNumber;

    EnumSiteAlarmsTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumSiteAlarmsTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumSiteAlarmsTableColumns v : EnumSiteAlarmsTableColumns.values()) {
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
    
    public static List<String> getColumnNames(){
        List<String> names = new ArrayList<>();
        
        for (EnumSiteAlarmsTableColumns v : EnumSiteAlarmsTableColumns.values()) {
            
            names.add(v.getFriendlyName() );

        }
        return names;
    }
}
