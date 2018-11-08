package View.Sites.NewSite.Equipment;

import java.util.ArrayList;
import java.util.List;

public enum EnumChillerTableColumns {

    Name(0, "Name"),
    Make(1, "Make"),
    Model(2, "Model"),
    Year(3, "Year"),
    Capacity(4, "Capacity (tons)"),
    KWRating(5, "KW rating"),
    DeltaT(6, "DeltaT");

    private final String friendlyName;
    private final int columnNumber;

    EnumChillerTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumChillerTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumChillerTableColumns v : EnumChillerTableColumns.values()) {
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

        for (EnumChillerTableColumns v : EnumChillerTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}
