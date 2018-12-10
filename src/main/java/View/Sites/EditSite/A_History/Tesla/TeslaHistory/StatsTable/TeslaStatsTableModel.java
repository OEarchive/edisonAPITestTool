
package View.Sites.EditSite.A_History.Tesla.TeslaHistory.StatsTable;

import Model.DataModels.TeslaModels.TeslaHistoryStats;
import View.Sites.EditSite.A_History.AveragesTable.EnumDPHistoryAveragesTableRows;
import javax.swing.table.AbstractTableModel;


public class TeslaStatsTableModel extends AbstractTableModel {

    private final TeslaHistoryStats statistics;

    public TeslaStatsTableModel(TeslaHistoryStats statistics) {
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


        return statistics.getPointNames().get(col - 1);
    }

    @Override
    public int getColumnCount() {
        return statistics.getPointNames().size() + 1; //add 1 for the stat heading column
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        EnumTeslaStatsTableRows statType = EnumTeslaStatsTableRows.getEnumFromRowNumber(rowIndex);

        Object val = "?";
        if (columnIndex == 0 ) {

            val = statType.getName();
        }
        else {

            String uName = statistics.getPointNames().get(columnIndex - 1);

            switch (statType) {

                case AVERAGE:
                    val = statistics.getDataPointStatistics(uName).getMean();
                    break;
                    
                case  STDDEV:
                    val = statistics.getDataPointStatistics(uName).getStdDev();
                    break;

                case COUNT:
                    val = statistics.getDataPointStatistics(uName).getCount();
                    break;

                case MAX:
                    val = statistics.getDataPointStatistics(uName).getMax();
                    break;

                case MIN:
                    val = statistics.getDataPointStatistics(uName).getMin();
                    break;

                case SUM:
                    val = statistics.getDataPointStatistics(uName).getSum();
                    break;

                case TOTALROWS:
                    val = statistics.getDataPointStatistics(uName).getTotalCount();
                    break;

                default:
                    val = "?";
            }
        }
        return val;

    }
    
}

