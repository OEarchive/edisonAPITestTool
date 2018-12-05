package View.Sites.EditSite.A_History.GenTeslaSiteFromEdison.TeslaEquipmentTable;

import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaGenEquipment;
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

        }

        return val;
    }

    public TeslaGenEquipment getEquipAtIndex(int modelIndex) {
        return equipList.get(modelIndex);
    }

}
