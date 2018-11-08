
package View.Sites.NewSite.Equipment;

import Model.DataModels.Sites.HeatExchanger;
import Model.DataModels.Sites.PlantEquipment;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class HeatExchangersTableModel extends AbstractTableModel {

    private final String[] colNames = {"name"};
    private final List<HeatExchanger> data;

    public HeatExchangersTableModel(PlantEquipment equip) {
        super();

        if( equip.getHeatExchangers() == null){
            
            equip.setHeatExchangers(new ArrayList<HeatExchanger>());
        }
        
        this.data = equip.getHeatExchangers();
        
        if( this.data.size() <= 0 ){
            HeatExchanger he = new HeatExchanger("?");
            this.data.add( he );
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
        return data.size();
    }

    public String getColumnName(int col) {
        return colNames[col];
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data.get(rowIndex).getName();

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        this.data.get(rowIndex).setName( (String)aValue );
        fireTableDataChanged();
    }

    public void removeRow(int rowNumber) {
        this.data.remove(rowNumber);
        fireTableDataChanged();
    }

    public void addBlankRow() {
        this.data.add( new HeatExchanger("?") );

        fireTableDataChanged();
    }

}
