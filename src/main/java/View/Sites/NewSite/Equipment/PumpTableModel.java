package View.Sites.NewSite.Equipment;

import Model.DataModels.Sites.EnumPumpTypes;
import Model.DataModels.Sites.PlantEquipment;
import Model.DataModels.Sites.Pump;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PumpTableModel extends AbstractTableModel {

    private List<Pump> tableData;

    public PumpTableModel(EnumPumpTypes pumpType, PlantEquipment equipment) {
        super();

        switch (pumpType) {
            case Condenser:
                if (equipment.getCondenserWaterPumps() == null) {
                    equipment.setCondenserWaterPumps(new ArrayList<Pump>());
                }
                tableData = equipment.getCondenserWaterPumps();
                break;

            case CoolingTower:
                if (equipment.getCoolingTowers() == null) {
                    equipment.setCoolingTowers(new ArrayList<Pump>());
                }
                tableData = equipment.getCoolingTowers();
                break;
            case PrimaryChilledWater:
                if (equipment.getPrimaryWaterPumps() == null) {
                    equipment.setPrimaryWaterPumps(new ArrayList<Pump>());
                }
                tableData = equipment.getPrimaryWaterPumps();
                break;

            case SecondaryChilledWater:
                if (equipment.getSecondaryWaterPumps() == null) {
                    equipment.setSecondaryWaterPumps(new ArrayList<Pump>());
                }
                tableData = equipment.getSecondaryWaterPumps();
                break;
        }

        if (this.tableData.size() <= 0) {
            this.tableData.add(new Pump("?", 0, 0));
        }
    }

    @Override
    public int getRowCount() {
        return tableData.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumPumpTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumPumpTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Pump pump = tableData.get(rowIndex);

        EnumPumpTableColumns colEnum = EnumPumpTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case Name:
                val = pump.getName();
                break;
            case KWRating:
                val = pump.getKWRating();
                break;
            case GPMRating:
                val = pump.getGPMRating();
                break;
        }
        return val;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Pump row = (Pump) this.tableData.get(rowIndex);
        EnumPumpTableColumns colEnum = EnumPumpTableColumns.getColumnFromColumnNumber(columnIndex);
        switch (colEnum) {
            case Name:
                row.setName((String) aValue);
                break;
            case KWRating:
                double num = 0.0;
                try {
                    num = Double.parseDouble((String) aValue);
                } catch (Exception ex) {
                    System.out.println("oops...can set kw rating with: " + aValue);
                }
                row.setKWRating(num);
                break;
            case GPMRating:
                double gpm = 0.0;
                try {
                    gpm = Double.parseDouble((String) aValue);
                } catch (Exception ex) {
                    System.out.println("oops...can set kw rating with: " + aValue);
                }
                row.setGPMRating(gpm);
                break;
        }
        fireTableDataChanged();
    }

    public void removeRow(int rowNumber) {
        this.tableData.remove(rowNumber);

        if (this.tableData.size() <= 0) {
            this.tableData.add(new Pump());
        }

        fireTableDataChanged();
    }

    public void addBlankRow() {
        tableData.add(new Pump());
        fireTableDataChanged();
    }

    public List<Pump> getTableAsList() {
        List<Pump> pumps = new ArrayList<>();

        int numRows = getRowCount();

        for (int row = 0; row < numRows; row++) {
            String name = (String) getValueAt(row, 0);
            double kw_rating = (double) getValueAt(row, 1);
            double gpm_rating = (double) getValueAt(row, 1);
            if (name.length() > 0) {
                Pump p = new Pump();
                p.setName(name);
                p.setKWRating(kw_rating);
                p.setGPMRating(gpm_rating);
                pumps.add(p);
            }
        }
        return pumps;
    }

}
