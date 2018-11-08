
package View.MobileView.SitesList;

import java.util.ArrayList;
import java.util.List;


public enum EnumSitesTableColumns {

    Name(0,"Name"),
    ShortName(1,"ShortName"),
    LingName(2,"LingName"),
    UUID(3,"UUID"),
    WeatherCondition(4,"Condition"),
    WeatherTemperature(5,"Temp"),
    EfficiencyAverage(6,"Eff"),
    EfficiencyAverageDelta(7,"Delta"),
    Savings(8,"Savings"),
    OptimizationStatus(9,"OptStatus");

    private final String friendlyName;
    private final int columnNumber;

    EnumSitesTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumSitesTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumSitesTableColumns v : EnumSitesTableColumns.values()) {
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
        for (EnumSitesTableColumns v : EnumSitesTableColumns.values()) {
            names.add(v.getFriendlyName());
        }
        return names;
    }
}
