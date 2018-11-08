
package View.Sites.EditSite.J_Contacts.Phones;

import java.util.ArrayList;
import java.util.List;


public enum EnumContactPhoneTableColumns {

    PhoneType(0, "PhoneType"),
    Phone(1,"Phone");
    
    private final String friendlyName;
    private final int columnNumber;

    EnumContactPhoneTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumContactPhoneTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumContactPhoneTableColumns v : EnumContactPhoneTableColumns.values()) {
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

        for (EnumContactPhoneTableColumns v : EnumContactPhoneTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}
