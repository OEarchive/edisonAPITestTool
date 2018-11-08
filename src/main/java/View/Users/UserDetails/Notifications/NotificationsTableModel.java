package View.Users.UserDetails.Notifications;

import Model.DataModels.Users.NotificationSetting;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class NotificationsTableModel extends AbstractTableModel {

    private final List<NotificationSetting> notifications;

    public NotificationsTableModel(List<NotificationSetting> notifications) {
        super();
        this.notifications = notifications;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int getRowCount() {
        return notifications.size();
    }

    public String getColumnName(int col) {
        return NotificationsTableColumns.getColumnNames().get(col);
    }

    @Override
    public int getColumnCount() {
        return NotificationsTableColumns.getColumnNames().size();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        NotificationSetting notification = notifications.get(rowIndex);
        NotificationsTableColumns colEnum = NotificationsTableColumns.getColumnFromColumnNumber(columnIndex);

        switch (colEnum) {
            case Sid:
                notification.setSid((String) aValue);
                break;
            case EventName:
                notification.setEventName((String) aValue);
                break;
            case TargetType:
                notification.setTargetType((String) aValue);
                break;
            case JobType:
                notification.setJobType((String) aValue);
                break;
        }

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        NotificationSetting notification = notifications.get(rowIndex);

        NotificationsTableColumns colEnum = NotificationsTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = null;

        switch (colEnum) {
            case Sid:
                val = notification.getSid();
                break;
            case EventName:
                val = notification.getEventName();
                break;
            case TargetType:
                val = notification.getTargetType();
                break;
            case JobType:
                val = notification.getJobType();
                break;
        }
        return val;
    }
    
    
    public List<NotificationSetting> getNotifications(){
        return notifications;
    }

    public NotificationSetting getNotification(int modelIndex) {
        return notifications.get(modelIndex);
    }
    
    public void addBlankNote() {
        NotificationSetting n = new NotificationSetting();
        n.setSid("");
        n.setEventName("");
        n.setTargetType("");
        n.setJobType("");
        notifications.add(n);
        fireTableDataChanged();
    }

    public void deleteNotification(int modelIndex) {
        notifications.remove(modelIndex);
        fireTableDataChanged();
    }

}
