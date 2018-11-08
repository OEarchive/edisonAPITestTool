
package View.Sites.EditSite.D_PlantInfo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class EnhancementsTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        
        String keyValue = (String)(table.getModel().getValueAt(row, 0));

        if (keyValue.length() <= 0) {
            setBackground(isSelected ? Color.lightGray : Color.lightGray);
        }
        else{
            setBackground(isSelected ? Color.white : Color.white);
        }

        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}