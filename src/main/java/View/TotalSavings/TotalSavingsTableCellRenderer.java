package View.TotalSavings;

import Model.DataModels.TotalSavings.TotalSavings;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class TotalSavingsTableCellRenderer extends DefaultTableCellRenderer {

    private final TotalSavings totalSavings;

    public TotalSavingsTableCellRenderer(TotalSavings totalSavings) {
        this.totalSavings = totalSavings;
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        NumberFormat threePlacesFmt = new DecimalFormat("#0.000");
        NumberFormat dollarsFmt = new DecimalFormat("#0.00");

        if (column == 2 && row == 3) {
            value = dollarsFmt.format(value);
        } else if (column > 0) {
            value = threePlacesFmt.format(value);
        }

        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }

}
