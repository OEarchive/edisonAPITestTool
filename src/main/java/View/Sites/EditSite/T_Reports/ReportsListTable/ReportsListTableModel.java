package View.Sites.EditSite.T_Reports.ReportsListTable;

import Model.DataModels.Reports.MonthlyReportItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class ReportsListTableModel extends AbstractTableModel {

    List<MonthlyReportItem> reportItems;

    public ReportsListTableModel(List<MonthlyReportItem> listOfAssociations) {
        super();

        if (listOfAssociations == null) {
            this.reportItems = new ArrayList<>();
        } else {
            this.reportItems = listOfAssociations;
        }
    }

    public MonthlyReportItem getRow(int modelNumber) {
        return reportItems.get(modelNumber);
    }

    @Override
    public int getRowCount() {
        return reportItems.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumReportsListTableColums.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumReportsListTableColums.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        EnumReportsListTableColums enumCol = EnumReportsListTableColums.getColumnFromColumnNumber(columnIndex);

        MonthlyReportItem report = reportItems.get(rowIndex);

        switch (enumCol) {
            case ID:
                val = report.getID();
                break;
            case EndDate:
                val = report.getEndDate();
                break;
            case CreatedBy:
                val = report.getCreatedBy();
                break;
            case UpdatedBy:
                val = report.getUpdatedBy();
                break;
            case CreatedDate:
                val = report.getCreatedDate();
                break;
            case UpdatedDate:
                val = report.getUpdatedDate();
                break;
            case Status:
                val = report.getStatus();
                break;
            case ReportLabel:
                val = report.getLabel();
                break;
        }

        return val;
    }

}
