package View.Sites.EditSite.E_Views.ViewLiveData;

import Model.DataModels.Live.Subscriptions.SubscriptionResponseDatapoint;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ViewLiveDataTableModel extends AbstractTableModel {

    private final List<ViewLiveDataTableRow> tableRows;
    private final DateTime startStaleTime;
    private int yellowThresh;
    private int redThresh;

    public ViewLiveDataTableModel(List<ViewLiveDataTableRow> tableRows, int yellowThresh, int redThresh) {
        super();

        if (tableRows.isEmpty()) {
            this.tableRows = new ArrayList<>();
        } else {
            this.tableRows = tableRows;
        }
        this.startStaleTime = null;

    }

    public List<ViewLiveDataTableRow> getRowsTable() {
        return tableRows;
    }

    public EnumStaleValues getStaleness(int modelRowIndex, int modelColIndex) {

        EnumViewLiveDataTableColumns enumCol = EnumViewLiveDataTableColumns.getColumnFromColumnNumber(modelColIndex);

        if (modelRowIndex > tableRows.size()) {
            return EnumStaleValues.Green;
        }

        ViewLiveDataTableRow tableRow = tableRows.get(modelRowIndex);

        switch (enumCol) {
            case LastUpdated:
                return EnumStaleValues.getEnumValueFromThreshAndDate(yellowThresh, redThresh, tableRow.getLastUpdated());
            case LastChanged:
                return EnumStaleValues.getEnumValueFromThreshAndDate(yellowThresh, redThresh, tableRow.getLastChanged());
        }
        return EnumStaleValues.Green;
    }

    @Override
    public int getRowCount() {
        return tableRows.size();
    }

    @Override
    public String getColumnName(int col) {

        return EnumViewLiveDataTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumViewLiveDataTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        EnumViewLiveDataTableColumns colEnum = EnumViewLiveDataTableColumns.getColumnFromColumnNumber(columnIndex);

        ViewLiveDataTableRow tableRow = tableRows.get(rowIndex);

        Object val = "?";

        switch (colEnum) {

            case Sid:
                val = tableRow.getSid();
                break;

            case Name:
                val = tableRow.getName();
                break;

            case StationName:
                val = tableRow.getStationName();
                break;

            case UOM:
                val = tableRow.getUOM();
                break;

            case Measure:
                val = tableRow.getMeasure();
                break;

            case PushValue:
                val = tableRow.getPushValue();
                break;

            case LiveValue:
                val = tableRow.getLiveValue();
                break;

            case LastUpdated:
                val = tableRow.getLastUpdated();
                break;

            case LastChanged:
                val = tableRow.getLastChanged();
                break;

            default:
                val = "?";
        }

        return val;

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        EnumViewLiveDataTableColumns colEnum = EnumViewLiveDataTableColumns.getColumnFromColumnNumber(columnIndex);
        return colEnum == EnumViewLiveDataTableColumns.PushValue;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        ViewLiveDataTableRow tableRow = tableRows.get(rowIndex);

        EnumViewLiveDataTableColumns colEnum = EnumViewLiveDataTableColumns.getColumnFromColumnNumber(columnIndex);

        if (colEnum == EnumViewLiveDataTableColumns.PushValue) {

            if (tableRow.getUOM().compareTo("boolean") == 0) {
                aValue = (((String) aValue).equalsIgnoreCase("true"));
            } else {

                Double tempDouble;
                if (aValue instanceof Double) {
                    tempDouble = (Double) aValue;
                } else {
                    try {
                        tempDouble = Double.parseDouble((String) aValue);
                    } catch (Exception ex) {
                        tempDouble = 0.0;
                    }
                }
                aValue = tempDouble;

            }
            tableRow.setPushValue(aValue);
        }

        fireTableDataChanged();
    }

    public boolean valueIsDifferent(Object oldValue, Object liveValue) {

        if (oldValue instanceof String && ((String) oldValue).length() == 0) {
            return true;
        }

        if (liveValue instanceof Integer) {
            return Math.abs((Integer) oldValue - (Integer) liveValue) > 0;

        } else if (liveValue instanceof Double) {
            return Math.abs((Double) oldValue - (Double) liveValue) > 0.001;

        } else if (liveValue instanceof Boolean) {
            return (Boolean) oldValue != (Boolean) liveValue;
        }

        return ((String) oldValue).compareTo((String) liveValue) == 0;

    }

    public void updateValues(List<SubscriptionResponseDatapoint> points, DateTime startStaleTime, int yellowThresh, int redThresh) {

        this.yellowThresh = yellowThresh;
        this.redThresh = redThresh;

        for (SubscriptionResponseDatapoint dp : points) {

            for (ViewLiveDataTableRow tableRow : tableRows) {
                try {
                    
                    if (tableRow.getStationName() == null && tableRow.getName().compareTo(dp.getName()) != 0) {
                       continue;
                    }
                    
                    
                    if (tableRow.getStationName() != null && tableRow.getStationName().compareTo(dp.getName()) != 0) {
                        continue;
                    }

                    Object newLiveValue = dp.getValue();
                    if (newLiveValue == null) {
                        newLiveValue = "?";
                    }

                    boolean valueChanged = valueIsDifferent(tableRow.getLiveValue(), newLiveValue);

                    tableRow.setLiveValue(newLiveValue);

                    DateTimeFormatter fromFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    DateTime currentTime = DateTime.parse(dp.getTimestamp(), fromFormatter);

                    tableRow.setLastUpdateStaleness(EnumStaleValues.getEnumValueFromThreshAndDate(yellowThresh, redThresh, currentTime));
                    tableRow.setLastUpdated(currentTime);

                    if (valueChanged) {
                        tableRow.setLastChanged(currentTime);
                    }

                    tableRow.setLastChangedStaleness(EnumStaleValues.getEnumValueFromThreshAndDate(yellowThresh, redThresh, tableRow.getLastChanged()));

                } catch (Exception ex) {
                    System.out.println("oops...can't update live table values");
                }

            }
        }

        fireTableDataChanged();

    }

}
