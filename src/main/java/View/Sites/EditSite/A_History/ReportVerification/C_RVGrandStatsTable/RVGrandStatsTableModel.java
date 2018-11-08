package View.Sites.EditSite.A_History.ReportVerification.C_RVGrandStatsTable;

import View.Sites.EditSite.A_History.AveragesTable.EnumDPHistoryAveragesTableRows;
import Model.DataModels.ReportVerification.PostProcess.BucketListStats;
import javax.swing.table.AbstractTableModel;

public class RVGrandStatsTableModel extends AbstractTableModel {

    private final BucketListStats statistics;

    public RVGrandStatsTableModel(BucketListStats statistics) {
        super();

        this.statistics = statistics;

    }

    @Override
    public int getRowCount() {
        return EnumDPHistoryAveragesTableRows.values().length;
    }

    public String getColumnName(int col) {

        if (col == 0) {
            return "Statistic";
        }

        return statistics.getDataPointNamesAndSids().get(col - 1);
    }

    @Override
    public int getColumnCount() {
        return statistics.getDataPointNamesAndSids().size() + 1; //add 1 for the stat heading column
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        EnumGrandStatsTableRows statType = EnumGrandStatsTableRows.getEnumFromRowNumber(rowIndex);

        Object val = "?";
        if (columnIndex == 0) {

            val = statType.getName();
        } else {

 
            String uName = statistics.getDataPointNamesAndSids().get(columnIndex - 1);

            switch (statType) {

                case AVERAGE:
                    val = statistics.getMean(uName);
                    break;

                case STDDEV:
                    val = statistics.getStdDev(uName);
                    break;

                case COUNT:
                    val = statistics.getCountNotNull(uName);
                    break;

                case MAX:
                    val = statistics.getMax(uName);
                    break;

                case MIN:
                    val = statistics.getMin(uName);
                    break;

                case SUM:
                    val = statistics.getSum(uName);
                    break;

                case TOTALROWS:
                    val = statistics.getTotalCount(uName);
                    break;

                default:
                    val = "?";
            }
        }
        return val;

    }

}
