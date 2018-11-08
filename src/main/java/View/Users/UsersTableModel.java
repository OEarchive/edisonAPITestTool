package View.Users;

import Model.DataModels.Users.UserInfo;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class UsersTableModel extends AbstractTableModel {

    private final List<UserInfo> users;

    public UsersTableModel(List<UserInfo> users) {
        super();
        this.users = users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    public String getColumnName(int col) {
        return EnumUsersTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumUsersTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        UserInfo user = this.users.get(rowIndex);

        EnumUsersTableColumns colEnum = EnumUsersTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case UserId:
                val = user.getUserID();
                break;
            case ExtSFID:
                val = user.getExtSFID();
                break;
            case UserName:
                val = user.getUserName();
                break;
            case Email:
                val = user.getEmail();
                break;
            case FirstName:
                val = user.getFirstName();
                break;
            case LastName:
                val = user.getLastName();
                break;
            case LockedUntil:
                val = user.getLockedUntil();
                break;
            case PassExpires:
                val = user.getPassExpires();
                break;
            case InitLogin:
                val = user.getInitialLogin();
                break;
            case CreatedAt:
                val = user.getCreatedAt();
                break;
            case ModifiedAt:
                val = user.getModifiedAt();
                break;

        }
        
        return val;
    }
    
    public UserInfo getUserAtRow( int row ){
        return this.users.get(row);
    }

}
