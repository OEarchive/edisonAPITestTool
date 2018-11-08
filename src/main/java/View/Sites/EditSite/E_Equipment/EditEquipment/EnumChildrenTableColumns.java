
package View.Sites.EditSite.E_Equipment.EditEquipment;

import java.util.ArrayList;
import java.util.List;


public enum EnumChildrenTableColumns {

    Name(0, "Name"),
    Sid(1, "Sid"),
    nodeType(2, "NodeType");

    private final String friendlyName;
    private final int columnNumber;

    EnumChildrenTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumChildrenTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumChildrenTableColumns v : EnumChildrenTableColumns.values()) {
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

        for (EnumChildrenTableColumns v : EnumChildrenTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}