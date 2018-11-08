
package View.Sites.EditSite.K_RulesInfo;

import java.util.ArrayList;
import java.util.List;


public enum EnumRulesListTableColumns {

    TableIndex(0, "TableIndex"),
    RuleId(1,"RuleId"),
    RuleName(2, "RuleName"),
    RuleOwner(3, "RuleOwner"),
    TimeStamp(4, "TimeStamp");

    private final String friendlyName;
    private final int columnNumber;

    EnumRulesListTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumRulesListTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumRulesListTableColumns v : EnumRulesListTableColumns.values()) {
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

        for (EnumRulesListTableColumns v : EnumRulesListTableColumns.values()) {

            names.add(v.getFriendlyName());

        }
        return names;
    }
}

