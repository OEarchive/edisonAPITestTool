package View.Sites.EditSite.A_History.PushToTesla.MappingTable;

import Model.DataModels.TeslaModels.MappingTableRow;
import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DataPointsTableCellRenderer extends DefaultTableCellRenderer {

    final Color limeGreen;

    public DataPointsTableCellRenderer() {

        limeGreen = new Color(204, 255, 204);

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
        setBackground(isSelected ? color : color);
        this.setHorizontalAlignment(JLabel.LEFT);

        EnumDatpointsTableColumns enumColumn = EnumDatpointsTableColumns.getColumnFromColumnNumber(column);
        DataPointsTableModel model = (DataPointsTableModel) table.getModel();
        int modelIndex = table.convertRowIndexToModel(row);
        MappingTableRow mappedRow = model.getRow(modelIndex);

        if (column == 0) {
            if (mappedRow.getMapStatus() != EnumMapStatus.Mapped) {
                color = Color.PINK;
            }
        } else {
            if (value == null) {
                color = Color.lightGray;
                value = "---";
            } else if (value instanceof String) {
                color = Color.WHITE;
                String temp = (String) value;
                if (temp.compareTo("NaN") == 0) {
                    value = "'NaN'";
                }
            } else if (value instanceof Double) {
                try {
                    String precFormatString = "#0";
                    String stringOfZeros = "000000";
                    if (3 > 0) {
                        precFormatString += ".";
                        precFormatString = precFormatString.concat(stringOfZeros.substring(0, 3));
                    }
                    NumberFormat formatter = new DecimalFormat(precFormatString);
                    value = formatter.format(value);
                } catch (Exception ex) {
                    color = Color.pink;
                    value = "oops";
                }
            } else if (value instanceof Boolean) {
                color = limeGreen;
            }
        }

        setBackground(isSelected ? color : color);
        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}
