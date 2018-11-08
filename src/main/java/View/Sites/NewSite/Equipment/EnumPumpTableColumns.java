
package View.Sites.NewSite.Equipment;

import java.util.ArrayList;
import java.util.List;


public enum EnumPumpTableColumns {

    Name(0, "Name"),
    KWRating(1, "KW rating"),
    GPMRating(2, "GPM Rating");

    private final String friendlyName;
    private final int columnNumber;

    EnumPumpTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumPumpTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumPumpTableColumns v : EnumPumpTableColumns.values()) {
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

        for (EnumPumpTableColumns v : EnumPumpTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}