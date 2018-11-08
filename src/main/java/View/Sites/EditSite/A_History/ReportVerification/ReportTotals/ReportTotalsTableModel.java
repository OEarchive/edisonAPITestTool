
package View.Sites.EditSite.A_History.ReportVerification.ReportTotals;

import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;


public class ReportTotalsTableModel extends AbstractTableModel {

    private final Map<String, Object> nameValuePairs;
    
    private List<String> pointNames;

    public ReportTotalsTableModel(List<String> pointNames, Map<String, Object> nameValuePairs ) {
        super();

        this.pointNames = pointNames;
        this.nameValuePairs = nameValuePairs;
        
    }

    @Override
    public int getRowCount() {
        return nameValuePairs.keySet().size();
    }

    public String getColumnName(int col) {

        if (col == 0) {
            return "Metric";
        }
        else {
            return "Value";
        }
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object val = "?";
        if (columnIndex == 0) {
            val = pointNames.get(rowIndex);
        } else {
            val = nameValuePairs.get(pointNames.get(rowIndex));
        }
        return val;
    }
}