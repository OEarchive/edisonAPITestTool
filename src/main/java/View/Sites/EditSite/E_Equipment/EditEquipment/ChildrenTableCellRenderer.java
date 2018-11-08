
package View.Sites.EditSite.E_Equipment.EditEquipment;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class ChildrenTableCellRenderer extends DefaultTableCellRenderer {

    public ChildrenTableCellRenderer( ) {

    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}
