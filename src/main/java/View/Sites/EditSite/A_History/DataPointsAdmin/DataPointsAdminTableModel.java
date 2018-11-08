
package View.Sites.EditSite.A_History.DataPointsAdmin;

import Model.DataModels.Datapoints.DatapointMetadata;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class DataPointsAdminTableModel extends AbstractTableModel {

    private final List<DatapointMetadata> listOfMetadata;

    public DataPointsAdminTableModel(List<DatapointMetadata> listOfMetadata) {
        super();

        this.listOfMetadata = listOfMetadata;

    }
    
    public DatapointMetadata getDatapointFromTable( int row ){
        return listOfMetadata.get(row);
    }

    @Override
    public int getRowCount() {
        return listOfMetadata.size();
    }

    public String getColumnName(int col) {

        return EnumDataPointsAdminTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumDataPointsAdminTableColumns.getColumnNames().size(); 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        EnumDataPointsAdminTableColumns colEnum = EnumDataPointsAdminTableColumns.getColumnFromColumnNumber(columnIndex);
        
        DatapointMetadata dp = listOfMetadata.get( rowIndex);

        Object val = "?";


            switch (colEnum) {
                
                case PointName:
                    if( dp.getMetadata() != null ){
                    val = dp.getMetadata().get("name");
                    }
                    else{
                        val = "???";
                    }
                    break;

                case PointID:
                    val = dp.getId();
                    break;
                    
                case  PointType:
                    val = dp.getPointType();
                    break;

                case CurrentValue:
                    val = dp.getCurrentValue();
                    break;

                case CurrentTimestamp:
                    val = dp.getCurrentTimestamp();
                    break;
                    
                default:
                    val = "?";
            }

        return val;

    }
    
}