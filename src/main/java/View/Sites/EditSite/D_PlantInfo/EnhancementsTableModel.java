package View.Sites.EditSite.D_PlantInfo;

import Model.DataModels.Sites.Enhancement;
import Model.DataModels.Sites.Site;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public final class EnhancementsTableModel extends AbstractTableModel {

    private final String[] colNames = {"name", "enabled"};
    private final List<Enhancement> data;

    public EnhancementsTableModel(Site site) {
        super();
        
        if( site.getEnhancements() == null ){
            site.setEnhancements( new ArrayList<Enhancement>());
        }
        
        this.data = site.getEnhancements();
        if( this.data.size() <= 0 ){
            addBlankRow();
        }
    }


    @Override
    public Class getColumnClass(int column) {
        if( column == 1 ){
            return Boolean.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return colNames[col];
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Enhancement enhancement = this.data.get(rowIndex);
        String name = enhancement.getName();
        boolean enabledFlag = enhancement.getEnabled();

        if( columnIndex == 0 ){
            return name;
        }
        return enabledFlag;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Enhancement enhancement = this.data.get(rowIndex);
        
        if( columnIndex == 0){
            enhancement.setName((String)aValue);
        }
        else{
            enhancement.setEnabled((Boolean)aValue);
        }
        fireTableDataChanged();
    }

    public void removeRow(int rowNumber) {
        this.data.remove(rowNumber);
        if( data.size() <= 0 ){
            addBlankRow();
        }
        fireTableDataChanged();
    }

    public void addBlankRow() {

  
        this.data.add( new Enhancement("?", false) );

        fireTableDataChanged();
    }
    

}
