package View.Sites.EditSite.A_History.PushLiveData;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PushLiveDataTableModel extends AbstractTableModel {

    private final List<PushLiveDataTableRow> listOfPoints;

    public PushLiveDataTableModel(List<PushLiveDataTableRow> listOfPoints) {
        super();
        this.listOfPoints = listOfPoints;

    }

    public PushLiveDataTableRow getRowFromTable(int rowIndex) {
        return listOfPoints.get(rowIndex);
    }

    /*
     @Override
     public Class getColumnClass(int column) {

     if (getValueAt(0, column) == null) {
     return String.class;
     }
     return getValueAt(0, column).getClass();

     }
     */
    public PushLiveDataTableRow getSiteDatapoint(int rowNumber) {
        return listOfPoints.get(rowNumber);
    }

    @Override
    public int getRowCount() {
        return listOfPoints.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumPushLiveDataTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumPushLiveDataTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        PushLiveDataTableRow point = this.listOfPoints.get(rowIndex);

        EnumPushLiveDataTableColumns colEnum = EnumPushLiveDataTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case PointName:
                val = point.getPointName();
                break;

            case PointType:
                val = point.getPointType().getEdisonName();
                break;

            case PointValue:
                val = point.getVal();
                break;

        }
        return val;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        EnumPushLiveDataTableColumns colEnum = EnumPushLiveDataTableColumns.getColumnFromColumnNumber(columnIndex);
        return colEnum == EnumPushLiveDataTableColumns.PointValue;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        PushLiveDataTableRow pointRow = listOfPoints.get(rowIndex);

        EnumPushLiveDataTableColumns colEnum = EnumPushLiveDataTableColumns.getColumnFromColumnNumber(columnIndex);

        if (colEnum == EnumPushLiveDataTableColumns.PointValue) {

            switch (pointRow.getPointType()) {

                case booleanType:
                    aValue = (((String) aValue).equalsIgnoreCase("true"));
                    break;

                case stringType:
                    break;

                case numericType:
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
                    break;
            }
            
            pointRow.setVal(aValue);

        }

        fireTableDataChanged();
    }
}
