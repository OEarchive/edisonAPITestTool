package View.Sites.EditSite.D_PlantInfo;

import Model.DataModels.Sites.EdgePlantInfo;
import javax.swing.table.AbstractTableModel;

public class old_PlantInfoTableModel extends AbstractTableModel {

    private final EdgePlantInfo pi;

    public old_PlantInfoTableModel(EdgePlantInfo pi) {
        super();

        if (pi != null) {
            this.pi = pi;
        } else {
            this.pi = new EdgePlantInfo();
        }

    }

    @Override
    public int getRowCount() {
        return old_EnumPlantInfoRows.values().length;
    }

    public String getColumnName(int col) {

        if (col == 0) {
            return "Name";
        }

        return "Value";

    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        old_EnumPlantInfoRows piField = old_EnumPlantInfoRows.getEnumFromRowNumber(rowIndex);

        Object val = "?";
        if (columnIndex == 0) {

            val = piField.getName();

        } else {

            switch (piField) {

                case BlendedUtilityRate:
                    val = pi.getBlendedUtilityRate();
                    break;

                case chillerPlantCoolingCapacity:
                    val = pi.getChillerPlantCoolingCapacity();
                    break;
                case minimumChilledWaterFlow:
                    val = pi.getMinimumChilledWaterFlow();
                    break;
                case chilledWaterDistType:
                    val = pi.getChilledWaterDistType();
                    break;
                case condenserWaterDistType:
                    val = pi.getCondenserWaterDistType();
                    break;

                case currentDpSetPoint:
                    val = pi.getCurrentDpSetPoint();
                    break;

                case chilledWaterMinimumDp:
                    val = pi.getChilledWaterMinimumDp();
                    break;
                case chilledWaterMaximumDp:
                    val = pi.getChilledWaterMaximumDp();
                    break;
                case chilledWaterDpOsaMin:
                    val = pi.getChilledWaterDpOsaMin();
                    break;
                case chilledWaterDpOsaMax:
                    val = pi.getChilledWaterDpOsaMax();
                    break;
                case chilledWaterStResetEnable:
                    val = pi.getChilledWaterStResetEnable();
                    break;

                case chilledWaterSupplyTempMin:
                    val = pi.getChilledWaterSupplyTempMin();
                    break;
                case chilledWaterSupplyTempMax:
                    val = pi.getChilledWaterSupplyTempMax();
                    break;
                case primaryChilledWaterPumpMinSpeed:
                    val = pi.getPrimaryChilledWaterPumpMinSpeed();
                    break;
                case secondaryChilledWaterPumpMinSpeed:
                    val = pi.getSecondaryChilledWaterPumpMinSpeed();
                    break;
                case currentChilledWaterStSetPoint:
                    val = pi.getCurrentChilledWaterStSetPoint();
                    break;

                case currentEnteringCondenserWaterSetPoint:
                    val = pi.getCurrentEnteringCondenserWaterSetPoint();
                    break;
                case minimumCondWaterEnteringTemp:
                    val = pi.getMinimumCondWaterEnteringTemp();
                    break;
                case maximumCondWaterEnteringTemp:
                    val = pi.getMaximumCondWaterEnteringTemp();
                    break;
                case condWaterPumpMinSpeed:
                    val = pi.getCondWaterPumpMinSpeed();
                    break;
                case co2EmissionFactor:
                    val = pi.getCo2EmissionFactor();
                    break;

                default:
                    val = "?";

            }

        }

        return val;

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        if (columnIndex == 0) {
            return;
        }

        old_EnumPlantInfoRows piField = old_EnumPlantInfoRows.getEnumFromRowNumber(rowIndex);

        switch (piField) {
            case BlendedUtilityRate:
                pi.setBlendedUtilityRate(Double.parseDouble((String) aValue));
                break;
            case chillerPlantCoolingCapacity:
                pi.setChillerPlantCoolingCapacity(Double.parseDouble((String) aValue));
                break;
            case minimumChilledWaterFlow:
                pi.setMinimumChilledWaterFlow(Double.parseDouble((String) aValue));
                break;
            case chilledWaterDistType:
                pi.setChilledWaterDistType((String) aValue);
                break;
            case condenserWaterDistType:
                pi.setCondenserWaterDistType((String) aValue);
                break;

            case currentDpSetPoint:
                pi.setCurrentDpSetPoint(Double.parseDouble((String) aValue));
                break;

            case chilledWaterMinimumDp:
                pi.setChilledWaterMinimumDp(Double.parseDouble((String) aValue));
                break;
            case chilledWaterMaximumDp:
                pi.setChilledWaterMaximumDp(Double.parseDouble((String) aValue));
                break;
            case chilledWaterDpOsaMin:
                pi.setChilledWaterDpOsaMin(Double.parseDouble((String) aValue));
                break;
            case chilledWaterDpOsaMax:
                pi.setChilledWaterDpOsaMax(Double.parseDouble((String) aValue));
                break;
            case chilledWaterStResetEnable:
                String tOrf = (String) aValue;
                boolean flag = (tOrf.compareTo("true") == 0) ? true : false;
                pi.setChilledWaterStResetEnable(flag);
                break;

            case chilledWaterSupplyTempMin:
                pi.setChilledWaterSupplyTempMin(Double.parseDouble((String) aValue));
                break;
            case chilledWaterSupplyTempMax:
                pi.setChilledWaterSupplyTempMax(Double.parseDouble((String) aValue));
                break;
            case primaryChilledWaterPumpMinSpeed:
                pi.setPrimaryChilledWaterPumpMinSpeed(Double.parseDouble((String) aValue));
                break;
            case secondaryChilledWaterPumpMinSpeed:
                pi.setSecondaryChilledWaterPumpMinSpeed(Double.parseDouble((String) aValue));
                break;
            case currentChilledWaterStSetPoint:
                pi.setCurrentEnteringCondenserWaterSetPoint(Double.parseDouble((String) aValue));
                break;

            case currentEnteringCondenserWaterSetPoint:
                pi.setCurrentEnteringCondenserWaterSetPoint(Double.parseDouble((String) aValue));
                break;
            case minimumCondWaterEnteringTemp:
                pi.setMinimumCondWaterEnteringTemp(Double.parseDouble((String) aValue));
                break;
            case maximumCondWaterEnteringTemp:
                pi.setMaximumCondWaterEnteringTemp(Double.parseDouble((String) aValue));
                break;
            case condWaterPumpMinSpeed:
                pi.setCondWaterPumpMinSpeed(Double.parseDouble((String) aValue));
                break;
            case co2EmissionFactor:
                pi.setCO2EmissionFactor(Double.parseDouble((String) aValue));
                break;
        }
        fireTableDataChanged();
    }

    public EdgePlantInfo getTableData() {
        return this.pi;
    }

}
