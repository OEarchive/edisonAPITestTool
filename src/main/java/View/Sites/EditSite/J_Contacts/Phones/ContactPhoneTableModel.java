package View.Sites.EditSite.J_Contacts.Phones;

import Model.DataModels.Sites.Phone;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ContactPhoneTableModel extends AbstractTableModel {

    private List<Phone> phones;

    public ContactPhoneTableModel(List<Phone> phones) {
        super();
        this.phones = phones;

    }

    public Phone getPhoneAtRow(int row) {
        return phones.get(row);
    }

    @Override
    public int getRowCount() {
        return phones.size();
    }

    public String getColumnName(int col) {
        return EnumContactPhoneTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int getColumnCount() {
        return EnumContactPhoneTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Phone phone = phones.get(rowIndex);

        EnumContactPhoneTableColumns colEnum = EnumContactPhoneTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case PhoneType:
                val = phone.getPhoneType();
                break;
            case Phone:
                val = phone.getPhone();
                break;


        }

        return val;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        Phone phone = phones.get(rowIndex);

        EnumContactPhoneTableColumns colEnum = EnumContactPhoneTableColumns.getColumnFromColumnNumber(columnIndex);

        switch (colEnum) {
            case PhoneType:
                phone.setPhoneType((String) aValue);
                break;
            case Phone:
                phone.setPhone((String) aValue);
                break;
        }

        fireTableDataChanged();
    }

    public void removeRow(int rowNumber) {
        phones.remove(rowNumber);
        fireTableDataChanged();
    }

    public void addRow() {
        phones.add(new Phone());
        fireTableDataChanged();
    }

}
