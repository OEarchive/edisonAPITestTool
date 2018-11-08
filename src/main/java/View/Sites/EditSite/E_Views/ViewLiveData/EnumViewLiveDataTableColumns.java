package View.Sites.EditSite.E_Views.ViewLiveData;

import java.util.ArrayList;
import java.util.List;

public enum EnumViewLiveDataTableColumns {

    Sid(0, "Sid"),
    Name(1, "Name"),
    StationName(2, "StationName"),
    UOM(3, "UOM"),
    Measure(4, "Measure"),
    PushValue(5, "PushValue"),
    LiveValue(6, "LiveValue"),
    LastUpdated(7, "LastUpdated"),
    LastChanged(8, "LastChanged");

    private final String friendlyName;
    private final int columnNumber;

    EnumViewLiveDataTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumViewLiveDataTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumViewLiveDataTableColumns v : EnumViewLiveDataTableColumns.values()) {
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

        for (EnumViewLiveDataTableColumns v : EnumViewLiveDataTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}
