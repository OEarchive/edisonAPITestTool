
package View.Users.UserDetails.Roles;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


public class RolesTableCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {
 
    private RoleName roleName;
    private List<RoleName> listOfRoleNames;

     
    public RolesTableCellEditor(List<RoleName> roleNames) {
        this.listOfRoleNames = roleNames;
    }
     
    @Override
    public Object getCellEditorValue() {
        return this.roleName;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof RoleName) {
            this.roleName = (RoleName) value;
        }
         
        JComboBox<RoleName> comboRoleName = new JComboBox<RoleName>();
         
        for (RoleName aRoleName : listOfRoleNames) {
            comboRoleName.addItem( aRoleName);
        }
         
        
        comboRoleName.addActionListener(this);
         
        if (isSelected) {
            comboRoleName.setBackground(table.getSelectionBackground());
        } else {
            comboRoleName.setBackground(table.getSelectionForeground());
        }
        
        comboRoleName.getModel().setSelectedItem( roleName );
        
        return comboRoleName;
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox<RoleName> comboRoleNames = (JComboBox<RoleName>) event.getSource();
        this.roleName = (RoleName) comboRoleNames.getSelectedItem();
    }

 
}