package View.Sites.EditSite.A_History.GenTeslaSiteFromEdison.TeslaEquipmentTable;

import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaGenEquipment;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostEquipResponse;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TeslaEquipTableModel extends AbstractTableModel {

    private final List<TeslaGenEquipment> equipList;

    public TeslaEquipTableModel(List<TeslaGenEquipment> equipList) {
        super();

        this.equipList = equipList;

    }

    @Override
    public int getRowCount() {
        return equipList.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumTeslaEquipTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumTeslaEquipTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        TeslaGenEquipment equip = this.equipList.get(rowIndex);

        EnumTeslaEquipTableColumns colEnum = EnumTeslaEquipTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case Name:
                val = equip.getName();
                break;
            case EquipType:
                val = equip.getEquipmentType();
                break;
            case ShortName:
                val = equip.getShortName();
                break;
            case Make:
                val = equip.getMake();
                break;
            case Model:
                val = equip.getModel();
                break;
            case ID:
                val = equip.getId();
                break;
        }

        return val;
    }

    public TeslaGenEquipment getEquipAtIndex(int modelIndex) {
        return equipList.get(modelIndex);
    }
    
    public void updateEquipWithIds( List<TeslaPostEquipResponse> equipResponses ){
        
        for( TeslaGenEquipment equip : equipList){
            for( TeslaPostEquipResponse resp : equipResponses ){
                if( resp.getShortName().contentEquals(equip.getShortName())){
                    equip.setId( resp.getId());
                    break;
                }
            }
        }
        
        fireTableDataChanged();
    }

}
