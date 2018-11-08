
package View.Sites.EditSite.A_History.ReportVerification.D_ReportPointsList;

import Model.DataModels.ReportVerification.PreProcess.UnCalculatedPoint;
import View.Sites.EditSite.A_History.DatapointListTable.EnumDatpointListTableColumns;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class ReportPointsTableModel extends AbstractTableModel {

    private final List<UnCalculatedPoint> listOfUncalcedPoints;
    private final List<String> uNames;

    public ReportPointsTableModel(List<String> uNames, List<UnCalculatedPoint> uncalcedPoints) {
        super();

        this.uNames = uNames;
        this.listOfUncalcedPoints = uncalcedPoints;
    }

    public UnCalculatedPoint getRow(int modelNumber) {
        return listOfUncalcedPoints.get(modelNumber);
    }
    
    public List<UnCalculatedPoint> getRows() {
        return listOfUncalcedPoints;
    }

    @Override
    public int getRowCount() {
        return listOfUncalcedPoints.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumDatpointListTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumDatpointListTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        EnumDatpointListTableColumns enumCol = EnumDatpointListTableColumns.getColumnFromColumnNumber(columnIndex);

        UnCalculatedPoint dataRow = listOfUncalcedPoints.get(rowIndex);

        switch (enumCol) {
            case Sid:
                val = dataRow.getSid();
                break;
            case Name:
                try{
                val = uNames.get( rowIndex );
                }
                catch(Exception ex){
                    System.out.println("could not get value - reportPointsTableModel:62");
                }
                break;
            case Label:
                val = dataRow.getLabel();
                break;
            case UOM:
                val = dataRow.getUOM();
                break;
            case Measure:
                val = dataRow.getMeasure();
                break;
            case Value:
                val = dataRow.getValue();
                break;
        }

        return val;
    }

}
