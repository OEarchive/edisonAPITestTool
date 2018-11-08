
package View.Sites.EditSite.A_History.DataPointsAdmin.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class DataPointTagsTableModel extends AbstractTableModel {
    private List<String> tags;

    public DataPointTagsTableModel(List<String> tags) {
        super();
        this.tags = tags;
    }
    
    public List<String> getTags(){
        return tags;
    }

    @Override
    public int getRowCount() {
        return tags.size();
    }

    @Override
    public String getColumnName(int col) {
        return "Tags";
    }

    @Override
    public int getColumnCount() {
        return 1;
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String tag = tags.get(rowIndex);
        return tag;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        tags.set(rowIndex, (String)aValue);
        fireTableDataChanged();
    }

    public void removeRow(int rowNumber) {
        tags.remove( rowNumber );
        fireTableDataChanged();
    }

    public void addRow() {
        tags.add("?");
        fireTableDataChanged();
    }

}
