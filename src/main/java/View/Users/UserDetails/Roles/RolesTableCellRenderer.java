package View.Users.UserDetails.Roles;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RolesTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        if (value instanceof RoleName) {
            RoleName roleName = (RoleName) value;
            setText(roleName.getRoleName());
        }

        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getSelectionForeground());
        }

        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}
