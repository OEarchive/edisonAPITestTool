
package View.Sites.EditSite.A_History.ReportVerification.A_RVQueryResultsTable;


import Model.DataModels.ReportVerification.PostProcess.CalculatedBucketList;
import View.Sites.EditSite.EnumTZOffsets;
import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class RVQueryResultsTableCellRenderer extends DefaultTableCellRenderer {

    final CalculatedBucketList bucketList;
    final int prec;
    final DateTimeZone siteTimeZone;
    final Color limeGreen;
    final DateTimeFormatter toFormat;

    public RVQueryResultsTableCellRenderer(int prec, DateTimeZone siteTimeZone, CalculatedBucketList bucketList) {
        this.bucketList = bucketList;
        this.prec = prec;
        this.siteTimeZone = siteTimeZone;
        limeGreen = new Color(204, 255, 204);
        toFormat = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ssZZ");
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
        this.setHorizontalAlignment((column == 0) ? JLabel.LEFT : JLabel.RIGHT);
        
        int bucketNumber = bucketList.getBucketNumberFromRowIndex(row);

        if (column == 0) {
            DateTime temp = ((DateTime)value).withZoneRetainFields(siteTimeZone);
            value = temp.toString(toFormat);
            color = (bucketNumber % 2 == 0)? limeGreen : Color.WHITE;
        } else if (value == null) {
            color = Color.lightGray;
            value = "---";
        } else if (value instanceof String) {
            color = Color.orange;
            String temp = (String) value;
            if (temp.compareTo("NaN") == 0) {
                value = "(s)NaN";
            }
        } else if (value instanceof Double) {
            try {
                String precFormatString = "#0";
                String stringOfZeros = "000000";
                if (prec > 0) {
                    precFormatString += ".";
                    precFormatString = precFormatString.concat(stringOfZeros.substring(0, prec));
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

        setBackground(isSelected ? color : color);
        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}
