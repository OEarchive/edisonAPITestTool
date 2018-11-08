package View.Sites.EditSite.J_Contacts;

import java.util.ArrayList;
import java.util.List;

public enum EnumContactsTableColumns {

    Role(0, "Role"),
    Name(1,"name"),
    FirstName(2, "firstName"),
    LastName(3, "lastName"),
    Username(4, "username"),
    Email(5, "Email"),
    Phone(6, "Phone");

    private final String friendlyName;
    private final int columnNumber;

    EnumContactsTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumContactsTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumContactsTableColumns v : EnumContactsTableColumns.values()) {
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

        for (EnumContactsTableColumns v : EnumContactsTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}
