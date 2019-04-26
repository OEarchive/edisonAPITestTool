
package View.Sites.EditSite.A_History.Tesla.TeslaHistory.HistoryTable;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class HistoryTableCellRenderer extends DefaultTableCellRenderer {

    final int prec;
    private final DateTimeFormatter zzFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");

    final Color pastelBlue = new Color(190,242,244);
    final Color pastelYellow = new Color(247,247,32);
    final Color pastelGreen = new Color(188,239,93);
    final Color pastelRed = new Color(239,155,155);
    final Color pastelGrey = new Color(15,11,12);
    
    public HistoryTableCellRenderer( int prec ) {
        this.prec = prec;
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

        if (value == null) {
            color = pastelGrey;
            value = "-null ret-";
        } else if (value instanceof String) {
            color = Color.WHITE;
            String temp = (String) value;
            if( temp.contentEquals("nodata")){
                color = pastelYellow;
            }
            else if (temp.compareTo("NaN") == 0) {
                value = "'NaN'";
                 color = pastelRed;
            }
            value = temp;
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
                value = "bad value";
            }
        } else if (value instanceof Boolean) {
            boolean b = (Boolean) value;
            color = (b) ? pastelGreen : pastelBlue;

        } else if (value instanceof DateTime ){
            DateTime ts = (DateTime)value;
            String dateStr = ts.toString( zzFormat );
            value = dateStr;
        }

        setBackground(isSelected ? color : color);
        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}
