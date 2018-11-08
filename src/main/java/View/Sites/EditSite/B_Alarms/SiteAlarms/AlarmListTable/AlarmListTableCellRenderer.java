
package View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmListTable;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class AlarmListTableCellRenderer extends DefaultTableCellRenderer {

    public AlarmListTableCellRenderer( ) {

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