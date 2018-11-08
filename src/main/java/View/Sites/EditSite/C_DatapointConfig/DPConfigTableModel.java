package View.Sites.EditSite.C_DatapointConfig;

import Model.DataModels.Sites.SiteDPConfig.SiteDPConfigPoint;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class DPConfigTableModel extends AbstractTableModel {

    private final List<SiteDPConfigPoint> listOfPoints;

    public DPConfigTableModel(List<SiteDPConfigPoint> resp) {
        super();
        
        if( resp == null ){
            this.listOfPoints = new ArrayList<>();
        }
        else
            this.listOfPoints = resp;
        

    }
    
    public SiteDPConfigPoint getSiteDatapoint( int rowNumber ){
        return listOfPoints.get(rowNumber);
    }

    @Override
    public int getRowCount() {
        return listOfPoints.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumDPConfigTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumDPConfigTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        SiteDPConfigPoint point = this.listOfPoints.get(rowIndex);

        EnumDPConfigTableColumns colEnum = EnumDPConfigTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case SubGroup:
                val = point.getSubGroup();
                break;
            case EquipNumber:
                val = point.getEquipmentNumber();
                break;
            case StPointName:
                val = point.getStationPointName();
                break;
            case StAtrrName:
                val = point.getStationAttributeName();
                break;
            case GraphAttrName:
                val = point.getGraphAttributeName();
                break;
            case Unit:
                val = point.getUnit();
                break;
            case ValueType:
                val = point.getValueType();
                break;
            case Address:
                val = point.getAddress();
                break;
            case PointType:
                val = point.getPointType();
                break;
            case DisplayName:
                val = point.getDisplayName();
                break;
        }
        return val;
    }
}
