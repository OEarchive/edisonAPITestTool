
package View.Sites.EditSite.A_History.DatapointListTable;

import java.util.ArrayList;
import java.util.List;

public enum EnumDatpointListTableColumns {

    Name(0,"Name"),
    Label(1,"Label"),
    Sid(2,"Sid"),
    Value(3,"Value"),
    UOM(4,"UOM"),
    Measure(5,"Measure");

    private final String friendlyName;
    private final int columnNumber;

    EnumDatpointListTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumDatpointListTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumDatpointListTableColumns v : EnumDatpointListTableColumns.values()) {
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
        for (EnumDatpointListTableColumns v : EnumDatpointListTableColumns.values()) {
            names.add(v.getFriendlyName());
        }
        return names;
    }
}
