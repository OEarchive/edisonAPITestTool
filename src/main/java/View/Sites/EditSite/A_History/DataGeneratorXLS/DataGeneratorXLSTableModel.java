package View.Sites.EditSite.A_History.DataGeneratorXLS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class DataGeneratorXLSTableModel extends AbstractTableModel {

    private final List<String> timestamps;
    private final Map<String, List<Object>> pointsAndValues;
    
    private List<String> pointNames;

    public DataGeneratorXLSTableModel(
            List<String> timestamps,
            Map<String, List<Object>> pointsAndValues
    ) {
        super();
        this.timestamps = timestamps;
        this.pointsAndValues = pointsAndValues;

        pointNames = new ArrayList<>();
        for( String pointName : pointsAndValues.keySet() ){
            pointNames.add( pointName );
        }
    }



    @Override
    public int getRowCount() {
        return timestamps.size();
    }

    @Override
    public String getColumnName(int col) {
        if( col == 0 ){
            return "timeStamp";
        }
        return pointNames.get(col-1);
    }

    @Override
    public int getColumnCount() {
        return pointNames.size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if( columnIndex == 0 ){
            return timestamps.get(rowIndex);
        }
        
        String pointName = this.pointNames.get(columnIndex-1);
        
        return pointsAndValues.get(pointName).get(rowIndex);
     
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
