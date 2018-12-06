
package View.Sites.EditSite.A_History.Tesla.PushToTesla.MappingTable;

import java.util.ArrayList;
import java.util.List;


public enum EnumDatpointsTableColumns {

    MapStatus(0, "MapStatus"),
    EdisonName(1,"EdisonName"),
    EdsionSid(2,"Sid"),
    TeslaName(3, "TeslaName"),
    TeslaType(4, "TeslaType"),
    TeslaID(5, "TesalID");

    private final String friendlyName;
    private final int columnNumber;

    EnumDatpointsTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumDatpointsTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumDatpointsTableColumns v : EnumDatpointsTableColumns.values()) {
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
        for (EnumDatpointsTableColumns v : EnumDatpointsTableColumns.values()) {
            names.add(v.getFriendlyName());
        }
        return names;
    }
}
