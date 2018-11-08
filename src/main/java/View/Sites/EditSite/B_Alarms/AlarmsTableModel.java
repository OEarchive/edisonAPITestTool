
package View.Sites.EditSite.B_Alarms;

import Model.DataModels.Sites.Alarm;
import View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmHistoryTable.EnumAlarmHistoryTableColumns;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AlarmsTableModel extends AbstractTableModel {

    private List<Alarm> tableData;

    public AlarmsTableModel(List<Alarm> tableData) {
        super();
        this.tableData = tableData;
        
        if( this.tableData == null){
            this.tableData = new ArrayList<>();
        }
    }

    @Override
    public int getRowCount() {
        return tableData.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumAlarmHistoryTableColumns.getEnumFromColNumber(col).getColName();
    }

    @Override
    public int getColumnCount() {
        return EnumAlarmTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Alarm alarm = tableData.get(rowIndex);

        EnumAlarmTableColumns colEnum = EnumAlarmTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case Sid:
                val = alarm.getAlarmSid();
                break;
            case Name:
                val = alarm.getName();
                break;
            case State:
                val = alarm.getState();
                break;
            case LastReceived:
                val = alarm.getLastReceivedTimestamp();
                break;
        }
        return val;
    }

}
