
package View.Users.UserDetails.Notifications;

import java.util.ArrayList;
import java.util.List;

public enum NotificationsTableColumns {
    
    Sid(0, "Sid"),
    EventName(1, "EventName"),
    TargetType(2,"TargetType"),
    JobType(3,"JobType");
    
    private final String friendlyName;
    private final int columnNumber;

    NotificationsTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static NotificationsTableColumns getColumnFromColumnNumber(int colNumber) {

        for (NotificationsTableColumns v : NotificationsTableColumns.values()) {
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
        
        for (NotificationsTableColumns v : NotificationsTableColumns.values()) {
            
            names.add(v.getFriendlyName() );

        }
        return names;
    }
}