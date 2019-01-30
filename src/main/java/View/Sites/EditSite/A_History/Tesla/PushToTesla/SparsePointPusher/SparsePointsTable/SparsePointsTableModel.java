
package View.Sites.EditSite.A_History.Tesla.PushToTesla.SparsePointPusher.SparsePointsTable;

import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.TeslaModels.TeslaDPServiceDatapoint;
import Model.DataModels.TeslaModels.TeslaDataPointUpsert;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class SparsePointsTableModel extends AbstractTableModel {

    private final List<SparseTableRow> mappingTableRows;

    public SparsePointsTableModel(List<DatapointsAndMetadataResponse> edisonPoints, List<TeslaDPServiceDatapoint> listOfStationDatapoints) {
        super();

        mappingTableRows = new ArrayList<>();

        for (DatapointsAndMetadataResponse edisonPoint : edisonPoints) {
            if( EnumSparsePointNames.getEnumFromEdisonName(edisonPoint.getName()) != null ){
            SparseTableRow row = new SparseTableRow( EnumSparsePointNames.getEnumFromEdisonName(edisonPoint.getName()) );
            
            row.setEdisonSid(edisonPoint.getSid());
            row.setPointValue(edisonPoint.getValue());
            mappingTableRows.add(row);
            }
        }

        if (listOfStationDatapoints != null) {
            for (TeslaDPServiceDatapoint teslaPoint : listOfStationDatapoints) {
                setMappingTableRow(teslaPoint);
            }

        }

    }
    
    
    private void setMappingTableRow(TeslaDPServiceDatapoint teslaPoint) {

        for (SparseTableRow mtr : mappingTableRows) {
            if (mtr.getSparsePointNameEnum().getTeslaName().compareToIgnoreCase(teslaPoint.getShortName()) == 0) {
                mtr.setTeslaID(teslaPoint.getId());
                mtr.setTeslaType(teslaPoint.getPointType());
                return;
            }
        }

        if( EnumSparsePointNames.getEnumFromTeslaName(teslaPoint.getName()) != null ){
            SparseTableRow row = new SparseTableRow( EnumSparsePointNames.getEnumFromTeslaName(teslaPoint.getName() ));
            row.setTeslaID(teslaPoint.getId());
            row.setTeslaType(teslaPoint.getPointType());
            row.setEdisonSid("?");
            row.setPointValue("?");
            
            mappingTableRows.add(row);
            
        }

    }

    public SparseTableRow getRow(int modelIndex) {
        return mappingTableRows.get(modelIndex);
    }
    
    public List<SparseTableRow> getRows(){
        return mappingTableRows;
    }
    
    
    @Override
    public int getRowCount() {
        return mappingTableRows.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumSparsePointsTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumSparsePointsTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        EnumSparsePointsTableColumns enumCol = EnumSparsePointsTableColumns.getColumnFromColumnNumber(columnIndex);

        SparseTableRow dataRow = mappingTableRows.get(rowIndex);

        switch (enumCol) {

            case EdisonName:
                val = dataRow.getSparsePointNameEnum().getEdisonName();
                break;
            case EdsionSid:
                val = dataRow.getEdsionSid();
                break;
            case TeslaName:
                val = dataRow.getSparsePointNameEnum().getTeslaName();
                break;
            case TeslaType:
                val = dataRow.getTeslaType();
                break;
            case TeslaID:
                val = dataRow.getTeslaID();
                break;
            case PointValue:
                val = dataRow.getPointValue();
                break;

        }

        return val;
    }
}
