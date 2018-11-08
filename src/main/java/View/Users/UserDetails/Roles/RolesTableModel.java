package View.Users.UserDetails.Roles;

import Controller.OptiCxAPIController;
import Model.DataModels.Users.EnumUserRoles;
import Model.DataModels.Users.RoleItem;
import Model.DataModels.Users.UserInfo;
import View.Users.AddOrUpdateEnum;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class RolesTableModel extends AbstractTableModel {
    
    private final OptiCxAPIController controller;
    private final UserInfo user;
    private final AddOrUpdateEnum addOrUpdate;

    private final String[] colNames = {"sid", "roleName"};
    private final List<RoleItem> roles;

    public RolesTableModel(List<RoleItem> roles, OptiCxAPIController controller, UserInfo user, AddOrUpdateEnum addOrUpdate) {
        super();

        this.roles = roles;
        this.controller = controller;
        this.user = user;
        this.addOrUpdate = addOrUpdate;
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int getRowCount() {
        return roles.size();
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
        RoleItem role = roles.get(rowIndex);
        switch (columnIndex) {
            case 0:
                role.setSid((String) aValue);
                break;
            case 1:
                RoleName roleName = (RoleName) aValue;
                role.setRoleName( roleName.getRoleName() );
                break;
        }
        
        /*
        if( addOrUpdate == AddOrUpdateEnum.UPDATE){
            UpdateRolesRequest updateRolesReq = new UpdateRolesRequest();
            updateRolesReq.setSid( role.getSid());
            updateRolesReq.setRoleName(role.getRoleName());
            controller.modifySpecificUserRoles(user.getUserID(), updateRolesReq);
        }
                */
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {


        RoleItem role = roles.get(rowIndex);

        Object val = null;

        switch (columnIndex) {
            case 0:
                val = role.getSid();
                break;
            case 1:
                val = new RoleName(role.getRoleName());
                break;
        }
        return val;
    }
    
    public RoleItem getRoleItem( int modelIndex ){
        return roles.get( modelIndex);
    }

    public void addBlankRole() {
        String sid = "?";
        RoleItem r = new RoleItem();
        r.setSid("?");
        r.setRoleName(EnumUserRoles.OEUser.getEdisonRoleName());
        roles.add(r);
        fireTableDataChanged();
    }
    
    public void deleteRole( RoleItem roleToDelete ) {
        roles.remove(roleToDelete);
        fireTableDataChanged();
    }

}
