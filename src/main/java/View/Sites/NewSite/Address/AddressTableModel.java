
package View.Sites.NewSite.Address;

import Model.DataModels.Sites.Address;
import Model.DataModels.Sites.EdgePlantInfo;
import View.Sites.EditSite.D_PlantInfo.old_EnumPlantInfoRows;
import javax.swing.table.AbstractTableModel;


public class AddressTableModel extends AbstractTableModel {

    private final Address address;

    public AddressTableModel(Address address) {
        super();

        if( address != null ){
        this.address = address;
        }
        else{
            this.address = new Address();
        }

    }

    @Override
    public int getRowCount() {
        return EnumAddressTableRows.values().length;
    }

    public String getColumnName(int col) {

        if (col == 0) {
            return "Name";
        }

        return "Value";

    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        EnumAddressTableRows addrField = EnumAddressTableRows.getEnumFromRowNumber(rowIndex);

        Object val = "?";
        if (columnIndex == 0) {

            val = addrField.getName();

        } else {

            switch (addrField) {

                case Street:
                    val = address.getStreet();
                    break;

                case City:
                    val = address.getCity();
                    break;
                case State:
                    val = address.getState();
                    break;
                case Country:
                    val = address.getCountry();
                    break;
                case PostCode:
                    val = address.getPostCode();
                    break;
                case Timezone:
                    val = address.getTimezone();
                    break;
                case Latitude:
                    val = address.getLatitude();
                    break;
                case Longitude:
                    val = address.getLongitude();
                    break;
                
                default:
                    val = "?";

            }

        }

        return val;

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if( columnIndex == 0 ){
            return false;
        }
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
        if( columnIndex == 0 ){
            return;
        }
        
        EnumAddressTableRows addrField = EnumAddressTableRows.getEnumFromRowNumber(rowIndex);

        switch (addrField) {
                case Street:
                    address.setStreet( (String)aValue );
                    break;

                case City:
                    address.setCity((String)aValue );
                    break;
                case State:
                    address.setState((String)aValue );
                    break;
                case Country:
                    address.setCountry((String)aValue);
                    break;
                case PostCode:
                    address.setPostCode((String)aValue);
                    break;
                case Timezone:
                    address.setTimezone((String)aValue);
                    break;
                case Latitude:
                    address.setLatitude(Integer.parseInt((String)aValue));
                    break;
                case Longitude:
                    address.setLongitude(Integer.parseInt((String)aValue));
                    break;
                
        }
        fireTableDataChanged();
    }


    public Address getTableData() {
        return this.address;
    }

}
