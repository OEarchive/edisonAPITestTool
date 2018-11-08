package View.Sites.EditSite.A_History.ReportVerification.A_RVQueryResultsTable;

import Model.DataModels.ReportVerification.PostProcess.CalculatedBucketList;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import org.joda.time.DateTime;

public class RVQueryResultsTableModel extends AbstractTableModel {

    private final CalculatedBucketList buckets;
    List<DateTime> bucketDates;

    public RVQueryResultsTableModel(CalculatedBucketList buckets) {
        super();

        this.buckets = buckets;
    }

    public List<String> getPointNameAndSids() {
        return this.buckets.getUNames();
    }

    public CalculatedBucketList getBuckets() {
        return this.buckets;
    }

    @Override
    public int getRowCount() {
        return buckets.getTotalNumOfRows();
    }

    @Override
    public String getColumnName(int col) {
        if (col == 0) {
            return "Date";
        }

        return buckets.getUNames().get(col - 1);

    }

    @Override
    public int getColumnCount() {
        return buckets.getUNames().size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        if (columnIndex == 0) {
            val = buckets.getTimestampAt(rowIndex);
        } else {
            String uName = buckets.getUNames().get(columnIndex - 1);
            Map<String, Object> values = buckets.getValuesAt(rowIndex);
            val = values.get(uName);
        }

        return val;
    }

}
