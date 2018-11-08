package View.Sites.EditSite.E_Views.ViewLiveData;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ViewLiveDataTableCellRenderer extends DefaultTableCellRenderer {

    public ViewLiveDataTableCellRenderer() {

    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        Color color = Color.WHITE;
        
        this.setHorizontalAlignment((column == 0) ? JLabel.LEFT : JLabel.RIGHT);

        int modelIndex = table.convertRowIndexToModel(row);
        ViewLiveDataTableModel tableModel = (ViewLiveDataTableModel) table.getModel();
        ViewLiveDataTableRow dataRow = tableModel.getRowsTable().get(modelIndex);
        DateTimeFormatter toFormatter = DateTimeFormat.forPattern("HH:mm:ss.SSS");
        EnumViewLiveDataTableColumns enumCol = EnumViewLiveDataTableColumns.getColumnFromColumnNumber(column);
        switch (enumCol) {
            case LastUpdated:
                color = dataRow.getLastUpdateStaleness().getColor();
                if (value != null) {
                    value = ((DateTime) value).toString(toFormatter);
                }
                break;
            case LastChanged:
                color = dataRow.getLastChangedStaleness().getColor();
                if (value != null) {
                    value = ((DateTime) value).toString(toFormatter);
                }
                break;
        }
        
        setBackground(isSelected ? color : color);
        
        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}
