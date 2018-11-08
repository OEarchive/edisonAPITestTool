package View.Sites.EditSite.A_History.ReportVerification.ReportDates;

import java.util.ArrayList;
import java.util.List;

public enum EnumReportMonths {
    Jan("Jan", 0),
    Feb("Feb", 1),
    Mar("Mar", 2),
    Apr("Apr", 3),
    May("May", 4),
    Jun("Jun", 5),
    Jul("Jul", 6),
    Aug("Aug", 7),
    Sep("Sep", 8),
    Oct("Oct", 9),
    Nov("Nov", 10),
    Dec("Dec", 11);

    private String name;
    private int dropDownIndex;

    EnumReportMonths(String name, int dropDownIndex) {
        this.name = name;
        this.dropDownIndex = dropDownIndex;
    }

    public String getName() {
        return this.name;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    public int getMonthNumber(){
        return this.dropDownIndex;
    }
    
    
    static public List<String> getMonthNames() {
        List<String> names = new ArrayList<>();
        for (EnumReportMonths month : EnumReportMonths.values()) {
            names.add(month.getName());
        }
        return names;
    }

    static public EnumReportMonths getMonthFromName(String name) {
        for (EnumReportMonths res : EnumReportMonths.values()) {
            if (res.getName().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }
}
