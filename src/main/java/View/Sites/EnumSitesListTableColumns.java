
package View.Sites;

import java.util.ArrayList;
import java.util.List;


public enum EnumSitesListTableColumns {
    
    Sid(0, "Site Sid"),
    Name(1, "Site Name"),
    Product(2,"Product");
    
    private final String friendlyName;
    private final int columnNumber;

    EnumSitesListTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumSitesListTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumSitesListTableColumns v : EnumSitesListTableColumns.values()) {
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
    
    public static List<String> getColumnNames(){
        List<String> names = new ArrayList<>();
        
        for (EnumSitesListTableColumns v : EnumSitesListTableColumns.values()) {
            
            names.add(v.getFriendlyName() );

        }
        return names;
    }
}