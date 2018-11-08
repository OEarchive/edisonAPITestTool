
package View.Sites.EditSite.A_History.AveragesTable;

import Model.DataModels.Live.Subscriptions.SubscriptionResponseDatapoint;
import Model.DataModels.Sites.SiteDatapoints.DatapointStatistics;
import Model.DataModels.Sites.SiteDatapoints.Statistics;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class DPHistoryAveragesTableModel extends AbstractTableModel {

    private final Statistics statistics;

    public DPHistoryAveragesTableModel(Statistics statistics) {
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


        return statistics.getUNames().get(col - 1);
    }

    @Override
    public int getColumnCount() {
        return statistics.getUNames().size() + 1; //add 1 for the stat heading column
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        EnumDPHistoryAveragesTableRows statType = EnumDPHistoryAveragesTableRows.getEnumFromRowNumber(rowIndex);

        Object val = "?";
        if (columnIndex == 0 ) {

            val = statType.getName();
        }
        else {

            String uName = statistics.getUNames().get(columnIndex - 1);

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
    
    public void appendLiveData( List<SubscriptionResponseDatapoint> livePoints ){
        for(  SubscriptionResponseDatapoint livePoint : livePoints ){
            String uName = livePoint.getSid() + "." + livePoint.getName();
            //String livePointName = livePoint.getName();
            DatapointStatistics pointStats = statistics.getDataPointStatistics(uName);
            pointStats.recomputeStatsWithAddtionalValue(livePoint.getValue());
        }
        fireTableDataChanged();
    }
}
