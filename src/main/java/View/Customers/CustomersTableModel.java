
package View.Customers;

import Model.DataModels.Customers.Customer;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CustomersTableModel extends AbstractTableModel {

    private final List<Customer> customers;

    public CustomersTableModel(List<Customer> customers) {
        super();
        this.customers = customers;
    }

    @Override
    public int getRowCount() {
        return customers.size();
    }

    public String getColumnName(int col) {
        return EnumCustomersTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumCustomersTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Customer customer = this.customers.get(rowIndex);

        EnumCustomersTableColumns colEnum = EnumCustomersTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case sid:
                val = customer.getSid();
                break;
            case name:
                val = customer.getCustomerName();
                break;
            case address:
                val = customer.getAddress();
                break;
            case extSfId:
                val = customer.getExtSfId();
                break;
        }
        
        return val;
    }
    
    public Customer getCustomerAtRow( int row ){
        return customers.get(row);
    }

}