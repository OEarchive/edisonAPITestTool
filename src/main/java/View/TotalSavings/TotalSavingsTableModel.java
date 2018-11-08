package View.TotalSavings;

import Model.DataModels.TotalSavings.EnumTotalSavingsTypes;
import Model.DataModels.TotalSavings.TotalSavings;
import javax.swing.table.AbstractTableModel;

public class TotalSavingsTableModel extends AbstractTableModel {

    private final TotalSavings totalSavings;

    private final String colNames[] = {"SavingsType", "RateOfChange", "Value"};

    public TotalSavingsTableModel(TotalSavings totalSavings) {
        super();
        this.totalSavings = totalSavings;

    }

    @Override
    public int getRowCount() {
        return 4;
    }

    @Override
    public String getColumnName(int col) {
        return colNames[col];
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        String metric;
        double rateOfChange;
        double v;

        switch (rowIndex) {
            case 0:
                rateOfChange = totalSavings.getRateOfChange(EnumTotalSavingsTypes.H2O);
                v = totalSavings.getValue(EnumTotalSavingsTypes.H2O);
                metric = "H2O";
                break;
            case 1:
                rateOfChange = totalSavings.getRateOfChange(EnumTotalSavingsTypes.CO2);
                v = totalSavings.getValue(EnumTotalSavingsTypes.CO2);
                metric = "CO2";
                break;
            case 2:
                rateOfChange = totalSavings.getRateOfChange(EnumTotalSavingsTypes.KWH);
                v = totalSavings.getValue(EnumTotalSavingsTypes.KWH);
                metric = "KWH";
                break;
            default:
                rateOfChange = totalSavings.getRateOfChange(EnumTotalSavingsTypes.DOLLARS);
                v = totalSavings.getValue(EnumTotalSavingsTypes.DOLLARS);
                metric = "Dollars";
                break;
        }
        
        Object val;
        switch( columnIndex ){
            case 0 :
                val = metric;
                break;
            case 1 :
                val = rateOfChange;
                break;
            default:
                val = v;
                break;     
        }
        
        return val;
    }

}
