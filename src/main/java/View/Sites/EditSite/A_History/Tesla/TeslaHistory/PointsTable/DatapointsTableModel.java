
package View.Sites.EditSite.A_History.Tesla.TeslaHistory.PointsTable;

import Model.DataModels.TeslaModels.TeslaDPServiceDatapoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;


public class DatapointsTableModel extends AbstractTableModel {

    private final List<TeslaDPServiceDatapoint> datapointList;
    private Map<String, TeslaDPServiceDatapoint> idToDatapointMap;

    public DatapointsTableModel(List<TeslaDPServiceDatapoint> listOfMetadata) {
        super();

        this.datapointList = listOfMetadata;
        idToDatapointMap = new HashMap<>();
        for (TeslaDPServiceDatapoint dp : datapointList) {
            idToDatapointMap.put(dp.getId(), dp);
        }

    }

    public TeslaDPServiceDatapoint getRow(int idx) {
        return datapointList.get(idx);
    }

    @Override
    public int getRowCount() {
        return datapointList.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumDatapointsTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumDatapointsTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        EnumDatapointsTableColumns enumCol = EnumDatapointsTableColumns.getColumnFromColumnNumber(columnIndex);

        TeslaDPServiceDatapoint datapoint = datapointList.get(rowIndex);

        switch (enumCol) {

            case ID:
                val = datapoint.getId();
                break;
            case PointType:
                val = datapoint.getPointType();
                break;
            case MinReso:
                val = datapoint.getMinimumResolution();
                break;
            case Rollup:
                val = datapoint.getRollupAggregation();
                break;
            case Name:
                val = datapoint.getName();
                break;
            case ShortName:
                val = datapoint.getShortName();
                break;
        }

        return val;
    }


}


