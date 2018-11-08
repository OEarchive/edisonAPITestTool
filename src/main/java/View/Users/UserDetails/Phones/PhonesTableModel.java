
package View.Users.UserDetails.Phones;

import Model.DataModels.Sites.Phone;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class PhonesTableModel extends AbstractTableModel {
    

    private final String[] colNames = {"Number", "Type"};
    private final List<Phone> phones;

    public PhonesTableModel(List<Phone> phones) {
        super();
        this.phones = phones;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int getRowCount() {
        return phones.size();
    }

    public String getColumnName(int col) {
        return colNames[col];
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Phone phone = phones.get(rowIndex);
        switch (columnIndex) {
            case 0:
                phone.setPhone((String) aValue);
                break;
            case 1:
                phone.setPhoneType( (String)aValue );
                break;
        }
        
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {


        Phone phone = phones.get(rowIndex);

        Object val = null;

        switch (columnIndex) {
            case 0:
                val = phone.getPhone();
                break;
            case 1:
                val = phone.getPhoneType();
                break;
        }
        return val;
    }
    
    public Phone getPhone( int modelIndex ){
        return phones.get( modelIndex);
    }

    public void addBlankPhone() {
        Phone p = new Phone();
        p.setPhone("?");
        p.setPhoneType("?");
        phones.add(p);
        fireTableDataChanged();
    }
    
    public void deletePhone( int modelIndex ) {
        phones.remove(modelIndex);
        fireTableDataChanged();
    }

}