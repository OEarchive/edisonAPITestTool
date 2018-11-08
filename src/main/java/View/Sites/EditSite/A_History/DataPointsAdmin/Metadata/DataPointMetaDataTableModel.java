
package View.Sites.EditSite.A_History.DataPointsAdmin.Metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;


public class DataPointMetaDataTableModel extends AbstractTableModel {

    private final String[] colNames = {"key", "value"};
    private final Map<String, Object> tableMapData;

    public DataPointMetaDataTableModel(Map<String, Object> map) {
        super();
        
        if( map == null ){
            this.tableMapData = new HashMap<>();
        }

        else {
            this.tableMapData = map;
        }

    }

    @Override
    public Class getColumnClass(int column) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int getRowCount() {
        return tableMapData.size();
    }

    @Override
    public String getColumnName(int col) {
        return colNames[col];
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    public List<List<Object>> getMapAsList() {
        List<List<Object>> list = new ArrayList<>();
        for (String s : tableMapData.keySet()) {
            List<Object> row = new ArrayList<>();
            row.add(s);
            row.add(tableMapData.get(s));
            list.add(row);
        }
        return list;
    }

    public void setMapFromList(List<List<Object>> rows) {
        tableMapData.clear();
        for (List<Object> row : rows) {
            String key = (String)row.get(0);
            Object v = row.get(1);
            tableMapData.put(key, v);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Object> row = getMapAsList().get(rowIndex);
        return row.get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        List<List<Object>> allRows = getMapAsList();
        allRows.get( rowIndex).set(columnIndex, (String) aValue);
        setMapFromList(allRows);
        fireTableDataChanged();
    }

    public void removeRow(int rowNumber) {
        List<List<Object>> allRows = getMapAsList();
        allRows.remove(rowNumber);

        setMapFromList(allRows);
        fireTableDataChanged();

    }

    public void addBlankRow() {
        List<Object> aRow = new ArrayList<>();
        aRow.add("?");

        List<List<Object>> allRows = getMapAsList();
        allRows.add(aRow);
        setMapFromList(allRows);
        fireTableDataChanged();
    }
}
