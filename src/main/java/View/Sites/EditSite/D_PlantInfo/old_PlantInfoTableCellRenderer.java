
package View.Sites.EditSite.D_PlantInfo;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class old_PlantInfoTableCellRenderer extends DefaultTableCellRenderer {

    public old_PlantInfoTableCellRenderer( ) {

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