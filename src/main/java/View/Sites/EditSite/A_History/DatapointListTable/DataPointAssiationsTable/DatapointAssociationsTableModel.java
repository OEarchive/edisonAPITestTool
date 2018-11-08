
package View.Sites.EditSite.A_History.DatapointListTable.DataPointAssiationsTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;


public class DatapointAssociationsTableModel extends AbstractTableModel {

    List<Map> listOfAssociations;

    public DatapointAssociationsTableModel(List<Map> listOfAssociations) {
        super();
        
        if( listOfAssociations == null ){
            this.listOfAssociations = new ArrayList<>();
        }

        else {
            this.listOfAssociations = listOfAssociations;
        }
    }
    
    public Map<String,String> getRow( int modelNumber ){
        return listOfAssociations.get(modelNumber);
    }

    @Override
    public int getRowCount() {
        return listOfAssociations.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumDatapointAssociationsTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumDatapointAssociationsTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        
        EnumDatapointAssociationsTableColumns enumCol = EnumDatapointAssociationsTableColumns.getColumnFromColumnNumber(columnIndex);
        
        Map<String,String> map = listOfAssociations.get(rowIndex);
        
        switch( enumCol ){
            case Name:
                val = map.get("name");
                break;
            case Sid:
                val = map.get("sid");
                break;
        }

        return val;
    }

   
}
