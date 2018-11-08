package View.Sites.EditSite.T_Reports.ReportsListTable;

import java.util.ArrayList;
import java.util.List;

public enum EnumReportsListTableColums {

    ID(0, "id"),
    EndDate(1, "endDate"),
    CreatedBy(2, "createdBy"),
    UpdatedBy(3, "updatedBy"),
    CreatedDate(4, "createdDate"),
    UpdatedDate(5, "updatedDate"),
    Status(6, "status"),
    ReportLabel(7, "label");

    private final String friendlyName;
    private final int columnNumber;

    EnumReportsListTableColums(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumReportsListTableColums getColumnFromColumnNumber(int colNumber) {

        for (EnumReportsListTableColums v : EnumReportsListTableColums.values()) {
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
        for (EnumReportsListTableColums v : EnumReportsListTableColums.values()) {
            names.add(v.getFriendlyName());
        }
        return names;
    }
}
