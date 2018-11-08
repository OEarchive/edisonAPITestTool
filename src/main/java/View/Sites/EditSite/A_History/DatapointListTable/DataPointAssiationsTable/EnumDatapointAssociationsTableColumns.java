
package View.Sites.EditSite.A_History.DatapointListTable.DataPointAssiationsTable;

import java.util.ArrayList;
import java.util.List;


public enum EnumDatapointAssociationsTableColumns {

    Name(0,"Name"),
    Sid(1,"Sid");

    private final String friendlyName;
    private final int columnNumber;

    EnumDatapointAssociationsTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumDatapointAssociationsTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumDatapointAssociationsTableColumns v : EnumDatapointAssociationsTableColumns.values()) {
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
        for (EnumDatapointAssociationsTableColumns v : EnumDatapointAssociationsTableColumns.values()) {
            names.add(v.getFriendlyName());
        }
        return names;
    }
}