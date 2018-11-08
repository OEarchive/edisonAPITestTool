
package View.Sites.NewSite.Equipment;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class HeatExchangersTableCellRenderer extends DefaultTableCellRenderer {

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