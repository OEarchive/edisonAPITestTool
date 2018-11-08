package View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmHistoryTable;

import Model.DataModels.Alarms.AlarmHistoryEntry;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AlarmHistoryTableModel extends AbstractTableModel {

    private List<AlarmHistoryEntry> alarmHistories;

    public AlarmHistoryTableModel(List<AlarmHistoryEntry> tableData) {
        super();
        this.alarmHistories = tableData;

        if (this.alarmHistories == null) {
            this.alarmHistories = new ArrayList<>();
        }
    }

    @Override
    public int getRowCount() {
        return alarmHistories.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumAlarmHistoryTableColumns.getEnumFromColNumber(col).getColName();
    }

    @Override
    public int getColumnCount() {
        return EnumAlarmHistoryTableColumns.getColNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        AlarmHistoryEntry historyEntry = alarmHistories.get(rowIndex);

        EnumAlarmHistoryTableColumns colEnum = EnumAlarmHistoryTableColumns.getEnumFromColNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case AlarmId:
                val = historyEntry.getAlarmId();
                break;
            case Name:
                val = historyEntry.getName();
                break;
            case StartDate:
                val = historyEntry.getStartDate();
                break;
            case EndDate:
                val = historyEntry.getendDate();
                break;
            case State:
                val = historyEntry.getState();
                break;
        }
        return val;
    }

}
