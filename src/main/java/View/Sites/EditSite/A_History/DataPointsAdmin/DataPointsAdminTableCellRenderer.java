
package View.Sites.EditSite.A_History.DataPointsAdmin;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class DataPointsAdminTableCellRenderer extends DefaultTableCellRenderer {

    public DataPointsAdminTableCellRenderer( ) {

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
