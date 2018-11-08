
package View.Sites.EditSite.B_Alarms;

import java.util.ArrayList;
import java.util.List;


public enum EnumAlarmTableColumns {
    
    Sid(0, "Sid"),
    Name(1, "Name"),
    State(2,"State"),
    LastReceived(3,"LastReceived");
    
    private final String friendlyName;
    private final int columnNumber;

    EnumAlarmTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumAlarmTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumAlarmTableColumns v : EnumAlarmTableColumns.values()) {
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
        
        for (EnumAlarmTableColumns v : EnumAlarmTableColumns.values()) {
            
            names.add(v.getFriendlyName() );

        }
        return names;
    }
}