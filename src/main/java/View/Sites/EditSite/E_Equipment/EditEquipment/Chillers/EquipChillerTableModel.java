package View.Sites.EditSite.E_Equipment.EditEquipment.Chillers;

import Model.DataModels.Graph.EdisonNode;
import Model.DataModels.Graph.GetChildrenResponse;
import Model.DataModels.Sites.Chiller;
import View.Sites.NewSite.Equipment.EnumChillerTableColumns;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class EquipChillerTableModel extends AbstractTableModel {

    private List<Chiller> tableData;
    private List<EdisonNode> edisonNodes;

    public EquipChillerTableModel(GetChildrenResponse children) {
        super();

        this.edisonNodes = children.getNodes();

        if (edisonNodes == null) {
            edisonNodes = new ArrayList<EdisonNode>();
        }

        this.tableData = new ArrayList<>();
        
        for( EdisonNode en : edisonNodes ){
            Chiller c = new Chiller();
            c.setName(en.getName());
            c.setMake((String)(en.getProperties().get("make")));
            c.setModel((String)(en.getProperties().get("model")));
            c.setYear((int)(en.getProperties().get("year")));
            c.setCapacityTons((int)(en.getProperties().get("capacityTons")));
            c.setKWRating((int)(en.getProperties().get("kwRating")));
            c.setDeltaT(-1);
            this.tableData.add(c); 
            
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
