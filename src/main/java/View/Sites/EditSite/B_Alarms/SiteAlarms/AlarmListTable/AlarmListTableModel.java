package View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmListTable;

import Model.DataModels.Alarms.AlarmListEntry;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AlarmListTableModel extends AbstractTableModel {

    private List<AlarmListEntry> alarmList;

    public AlarmListTableModel(List<AlarmListEntry> alarmList) {
        super();

        if (alarmList.isEmpty()) {
            this.alarmList = new ArrayList<>();
        } else {
            this.alarmList = alarmList;
        }

    }

    @Override
    public int getRowCount() {
        return alarmList.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumSiteAlarmsTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumSiteAlarmsTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        AlarmListEntry alarm = alarmList.get(rowIndex);

        EnumSiteAlarmsTableColumns colEnum = EnumSiteAlarmsTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case Sid:
                val = alarm.getSid();
                break;
            case Name:
                val = alarm.getName();
                break;
            case State:
                val = alarm.getState();
                break;
            case StartDate:
                val = alarm.getStartDate();
                break;
            case AckDate:
                val = alarm.getAcknowledgedDate();
                break;
            case EndDate:
                val = alarm.getEndDate();
                break;
           
        }
        return val;
    }

}
