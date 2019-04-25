package View.Sites.EditSite.A_History.Tesla.TeslaHistory.HistoryTable;

import Model.DataModels.TeslaModels.ComboHistories.ComboHistories;
import Model.DataModels.TeslaModels.TeslaHistoryResults;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.joda.time.DateTime;

public class HistoryTableModel extends AbstractTableModel {

    private final ComboHistories historyResults;

    public HistoryTableModel(ComboHistories historyResults) {
        super();
        this.historyResults = historyResults;
    }

    @Override
    public int getRowCount() {
        return historyResults.getTimestamps().size();
    }

    @Override
    public String getColumnName(int col) {
        if (col == 0) {
            return "TimeStamp";
        }

        int index = col - 1;
        
        String colName = historyResults.getFlatPointNames().get(col - 1);
        return colName;

    }

    @Override
    public int getColumnCount() {
        return historyResults.getFlatPointNames().size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        DateTime timeStamp = historyResults.getTimestamps().get(rowIndex);

        if (columnIndex == 0) {
            val = timeStamp;
        } else {

            List< Object> values = (List< Object>) historyResults.getFlatTimestampsToValuesMap().get(timeStamp);
            val = values.get(columnIndex - 1);
        }

        return val;
    }

}
