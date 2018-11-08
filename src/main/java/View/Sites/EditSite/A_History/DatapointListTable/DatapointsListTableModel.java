package View.Sites.EditSite.A_History.DatapointListTable;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class DatapointsListTableModel extends AbstractTableModel {

    private final List<DatapointsAndMetadataResponse> listOfMetadata;
    private final Map<String,DatapointsAndMetadataResponse> uNameToPointMap;
    private final List<String>uNames;

    public DatapointsListTableModel(List<DatapointsAndMetadataResponse> listOfMetadata) {
        super();

        this.listOfMetadata = listOfMetadata;
        uNameToPointMap = new HashMap<>();
        uNames = new ArrayList<>();
        for( DatapointsAndMetadataResponse resp : listOfMetadata  ){
            String uName = getUName( resp );
            uNameToPointMap.put(uName, resp);
            uNames.add(uName);
        }
    }
    
    private String getUName( DatapointsAndMetadataResponse resp ){
        String sid = resp.getSid();
        String uName = resp.getName();
        String[] pieces = sid.split("\\.");
        
        if( pieces.length > 2 ){
            uName = pieces[2]  + "." + uName;
        }
        return uName;   
    }

    public DatapointsAndMetadataResponse getRow(int modelNumber) {
        
        return uNameToPointMap.get( uNames.get(modelNumber));
        //return listOfMetadata.get(modelNumber);
    }

    @Override
    public int getRowCount() {
        return uNames.size();
        //return listOfMetadata.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumDatpointListTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumDatpointListTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        EnumDatpointListTableColumns enumCol = EnumDatpointListTableColumns.getColumnFromColumnNumber(columnIndex);

       //DatapointsAndMetadataResponse dataRow = listOfMetadata.get(rowIndex);
       String uName = uNames.get(rowIndex);
        DatapointsAndMetadataResponse dataRow = uNameToPointMap.get( uName);

        switch (enumCol) {
            case Sid:
                val = dataRow.getSid();
                break;
            case Name:
                //val = dataRow.getName();
                val = uName;
                break;
            case Label:
                val = dataRow.getLabel();
                break;
            case UOM:
                val = dataRow.getUOM();
                break;
            case Measure:
                val = dataRow.getMeasure();
                break;
            case Value:
                val = dataRow.getValue();
                break;
        }

        return val;
    }

}
