package View.Customers;

import java.util.ArrayList;
import java.util.List;

public enum EnumCustomersTableColumns {
    
    sid(0, "Customer Sid"),
    name(1, "Customer Name"),
    address(2,"Address"),
    extSfId( 3, "External SalesForce ID");

    private final String friendlyName;
    private final int columnNumber;

    EnumCustomersTableColumns(int columnNumber, String name) {
        this.friendlyName = name;
        this.columnNumber = columnNumber;
    }

    public static EnumCustomersTableColumns getColumnFromColumnNumber(int colNumber) {

        for (EnumCustomersTableColumns v : EnumCustomersTableColumns.values()) {
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
        
        for (EnumCustomersTableColumns v : EnumCustomersTableColumns.values()) {
            
            names.add(v.getFriendlyName() );

        }
        return names;
    }
}