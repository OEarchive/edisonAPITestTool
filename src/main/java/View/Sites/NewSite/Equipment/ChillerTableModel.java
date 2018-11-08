package View.Sites.NewSite.Equipment;

import Model.DataModels.Sites.Chiller;
import Model.DataModels.Sites.PlantEquipment;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ChillerTableModel extends AbstractTableModel {

    private List<Chiller> tableData;

    public ChillerTableModel(List<Chiller> tableData) {
        super();

        this.tableData = tableData;

        if (this.tableData.size() <= 0) {
            Chiller c = new Chiller();
            c.setName("?????");
            c.setMake("?????");
            c.setModel("?????");
            c.setYear(2000);
            c.setCapacityTons(500);
            c.setKWRating(250);
            c.setDeltaT(100);
            this.tableData.add(new Chiller());
        }
    }

    @Override
    public int getRowCount() {
        return tableData.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumChillerTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumChillerTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Chiller chiller = tableData.get(rowIndex);

        EnumChillerTableColumns colEnum = EnumChillerTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case Name:
                val = chiller.getName();
                break;
            case Make:
                val = chiller.getMake();
                break;
            case Model:
                val = chiller.getModel();
                break;
            case Year:
                val = chiller.getYear();
                break;
            case Capacity:
                val = chiller.getCapacityTons();
                break;
            case KWRating:
                val = chiller.getKWRating();
                break;
            case DeltaT:
                val = chiller.getDeltaT();
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
        Chiller chiller = (Chiller) this.tableData.get(rowIndex);
        EnumChillerTableColumns colEnum = EnumChillerTableColumns.getColumnFromColumnNumber(columnIndex);
        switch (colEnum) {
            case Name:
                chiller.setName((String) aValue);
                break;
            case Year:
                int year = 0;
                try {
                    year = Integer.parseInt((String) aValue);
                } catch (Exception ex) {
                    System.out.println("oops...that's not an integer");
                }
                chiller.setYear(year);
                break;
            case Make:
                chiller.setMake((String) aValue);
                break;
            case Model:
                chiller.setModel((String) aValue);
                break;
            case Capacity:
                int tons = 0;
                try {
                    tons = Integer.parseInt((String) aValue);
                } catch (Exception ex) {
                    System.out.println("oops...that's not an integer");
                }
                chiller.setCapacityTons(tons);
                break;
            case KWRating:
                double num = 0.0;
                try {
                    num = Double.parseDouble((String) aValue);
                } catch (Exception ex) {
                    System.out.println("oops...can set kw rating with: " + aValue);
                }
                chiller.setKWRating(num);
                break;
        }
        fireTableDataChanged();
    }

    public void removeRow(int rowNumber) {
        this.tableData.remove(rowNumber);

        if (this.tableData.size() <= 0) {
            this.tableData.add(new Chiller());
        }

        fireTableDataChanged();
    }

    public void addBlankRow() {
        tableData.add(new Chiller());
        fireTableDataChanged();
    }

}
