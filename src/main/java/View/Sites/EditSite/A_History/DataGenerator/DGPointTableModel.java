package View.Sites.EditSite.A_History.DataGenerator;

import Model.DataModels.Datapoints.simulator.EnumPattern;
import Model.DataModels.Datapoints.simulator.EnumPeriod;
import Model.DataModels.Datapoints.simulator.EnumPointType;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class DGPointTableModel extends AbstractTableModel {

    private final List<DGTableRow> listOfPoints;

    public DGPointTableModel(List<DGTableRow> listOfPoints) {
        super();
        this.listOfPoints = listOfPoints;

    }
    
    public DGTableRow getRowFromTable( int rowIndex ){
        return listOfPoints.get(rowIndex);
    }
    
    
    public List<DGTableRow> getRows(){
        return this.listOfPoints;
    }

    
    @Override
    public Class getColumnClass(int column) {

        if (getValueAt(0, column) == null) {
            return String.class;
        }
        return getValueAt(0, column).getClass();

    }

    public DGTableRow getSiteDatapoint(int rowNumber) {
        return listOfPoints.get(rowNumber);
    }

    @Override
    public int getRowCount() {
        return listOfPoints.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumDGTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumDGTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        DGTableRow point = this.listOfPoints.get(rowIndex);

        EnumDGTableColumns colEnum = EnumDGTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case Scope:
                val = point.getScope().getEdisonName();
                break;
                
            case Sid:
                val = point.getSid();
                break;
            case PointName:
                val = point.getPointName();
                break;
            case StAttrName:
                val = point.getStAttrName();
                break;
            case GraphAttrName:
                val = point.getGraphAttrName();
                break;
            case PointType:
                val = point.getPointType();
                break;
            case UOM:
                val = point.getUOM();
                break;
            case MinValue:
                val = point.getMinValue();
                break;
            case MaxValue:
                val = point.getMaxValue();
                break;
            case Pattern:
                val = point.getPattern().getName();
                break;
            case Period:
                val = point.getPeriod().getName();
                break;
            case Offset:
                if (point.getPointType() == EnumPointType.booleanType) {
                    val = "Ignored";
                } else {
                    val = point.getOffset();
                }
                break;

        }
        return val;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        EnumDGTableColumns colEnum = EnumDGTableColumns.getColumnFromColumnNumber(columnIndex);
        return colEnum == EnumDGTableColumns.MinValue
                || colEnum == EnumDGTableColumns.MaxValue
                || colEnum == EnumDGTableColumns.Pattern
                || colEnum == EnumDGTableColumns.Period
                || colEnum == EnumDGTableColumns.Offset;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        DGTableRow pointRow = listOfPoints.get(rowIndex);

        EnumDGTableColumns colEnum = EnumDGTableColumns.getColumnFromColumnNumber(columnIndex);

        switch (colEnum) {
            case MinValue:
                Double min;
                if (aValue instanceof Double) {
                    min = (Double) aValue;
                } else {
                    try {
                        min = Double.parseDouble((String) aValue);
                    } catch (Exception ex) {
                        min = 0.0;
                    }
                }
                pointRow.setMinValue(min);
                break;
            case MaxValue:
                Double max;
                if (aValue instanceof Double) {
                    max = (Double) aValue;
                } else {
                    try {
                        max = Double.parseDouble((String) aValue);
                    } catch (Exception ex) {
                        max = 0.0;
                    }
                }
                pointRow.setMaxValue(max);
                break;
            case Pattern:
                String patternNameStr = (String) aValue;
                EnumPattern pattern = EnumPattern.getEnumFromName(patternNameStr);
                pointRow.setPattern(pattern);
                break;
            case Period:
                String periodNameStr = (String) aValue;
                EnumPeriod period = EnumPeriod.getEnumFromName(periodNameStr);
                pointRow.setPeriod(period);
                break;
            case Offset:
                Double offset;
                if (aValue instanceof Double) {
                    offset = (Double) aValue;
                } else {
                    try {
                        offset = Double.parseDouble((String) aValue);
                    } catch (Exception ex) {
                        offset = 0.0;
                    }
                }
                pointRow.setOffset(offset);
                break;

        }

        fireTableDataChanged();
    }
}
