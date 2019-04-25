
package View.Sites.EditSite.A_History.Tesla.TeslaHistory.HistoryPoints;

import java.util.ArrayList;
import java.util.List;


public enum EnumHistoryPointsTableColumns {

    MapStatus(0, "MapStatus", 100),
    EdisonName(1,"EdisonName", 200),
    EdsionSid(2,"Sid", 250),
    TeslaName(3, "TeslaName", 200),
    TeslaType(4, "TeslaType", 50),
    TeslaID(5, "TesalID", 250);

    private final String friendlyName;
    private final int columnNumber;
    private final int width;

    EnumHistoryPointsTableColumns(int columnNumber, String name, int width) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
        this.width = width;
    }

    public static EnumHistoryPointsTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumHistoryPointsTableColumns v : EnumHistoryPointsTableColumns.values()) {
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
    
    public int getWidth(){
        return this.width;
    }

    public static List<String> getColumnNames() {
        List<String> names = new ArrayList<>();
        for (EnumHistoryPointsTableColumns v : EnumHistoryPointsTableColumns.values()) {
            names.add(v.getFriendlyName());
        }
        return names;
    }
}