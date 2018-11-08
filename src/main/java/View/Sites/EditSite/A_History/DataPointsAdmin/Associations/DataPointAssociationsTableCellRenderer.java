
package View.Sites.EditSite.A_History.DataPointsAdmin.Associations;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class DataPointAssociationsTableCellRenderer extends DefaultTableCellRenderer {

    public DataPointAssociationsTableCellRenderer( ) {

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
