package View.Sites.EditSite.J_Contacts;

import Model.DataModels.Sites.Contact;
import Model.DataModels.Sites.Phone;
import Model.DataModels.Sites.Site;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ContactsTableModel extends AbstractTableModel {

    private List<Contact> contacts;

    public ContactsTableModel(Site site) {
        super();

        if (site.getContacts() == null) {
            site.setContacts(new ArrayList<Contact>());
        }

        this.contacts = site.getContacts();
    }
    
    public Contact getContactAtRow( int row ){
        return contacts.get(row);
    }
    
    public List<Contact> getContactsFromTable(){
        return contacts;
    }

    @Override
    public int getRowCount() {
        return contacts.size();
    }

    public String getColumnName(int col) {
        return EnumContactsTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int getColumnCount() {
        return EnumContactsTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Contact contact = contacts.get(rowIndex);

        EnumContactsTableColumns colEnum = EnumContactsTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case Role:
                val = contact.getRole();
                break;
            case Name:
                val = contact.getName();
                break;
            case FirstName:
                val = contact.getFirstName();
                break;
            case LastName:
                val = contact.getLastName();
                break;
            case Username:
                val = contact.getUsername();
                break;
            case Email:
                val = contact.getEmail();
                break;
            case Phone:
                val = contact.getPhones();
                break;

        }

        return val;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        Contact contact = contacts.get(rowIndex);

        EnumContactsTableColumns colEnum = EnumContactsTableColumns.getColumnFromColumnNumber(columnIndex);

        switch (colEnum) {
            case Role:
                contact.setRole((String) aValue);
                break;
            case Name:
                contact.setName((String) aValue);
                break;
            case FirstName:
                contact.setFirstName((String) aValue);
                break;
            case LastName:
                contact.setLastName((String) aValue);
                break;
            case Username:
                contact.setUsername((String) aValue);
                break;
            case Email:
                contact.setEmail((String) aValue);
                break;
            case Phone:

                //tableEntry.getContact().setPhones(List<Phone> aValue);
                break;
        }

        fireTableDataChanged();
    }

    public void removeRow(int rowNumber) {

        contacts.remove(rowNumber);

        if (contacts.size() <= 0) {
            Contact c = new Contact();
            contacts.add(c);
        }

        fireTableDataChanged();
    }

    public void addRow() {

        Phone p = new Phone();
        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(p);
        
        Contact c = new Contact();
        c.setPhones(phoneList);
        contacts.add(c);
        fireTableDataChanged();
    }

}
