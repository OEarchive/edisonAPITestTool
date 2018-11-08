
package View.Users;

import java.util.ArrayList;
import java.util.List;

public enum EnumUsersTableColumns {
    
    FirstName( 0, "FirstName"),
    LastName(1,"LastName"),
    UserName(2, "UserName"),
    Email(3,"Email"),
    UserId(4, "UserId"),
    ExtSFID(5, "ExtSFID"),
    LockedUntil(6,"LockedUntil"),
    PassExpires(7, "PassExpires"),
    InitLogin(8, "InitLogin"),
    CreatedAt(9, "CreatedAt"),
    ModifiedAt(10, "ModifiedAt");
    
    private final String friendlyName;
    private final int columnNumber;

    EnumUsersTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumUsersTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumUsersTableColumns v : EnumUsersTableColumns.values()) {
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
        
        for (EnumUsersTableColumns v : EnumUsersTableColumns.values()) {
            
            names.add(v.getFriendlyName() );

        }
        return names;
    }
}