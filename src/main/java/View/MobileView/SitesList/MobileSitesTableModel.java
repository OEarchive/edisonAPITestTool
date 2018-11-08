package View.MobileView.SitesList;

import Model.DataModels.TrendAPI.MobileSite;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MobileSitesTableModel extends AbstractTableModel {

    List<MobileSite> siteList;

    public MobileSitesTableModel(List<MobileSite> siteList) {
        super();

        this.siteList = siteList;
    }

    public MobileSite getSiteAtRow(int modelNumber) {
        return siteList.get(modelNumber);
    }

    @Override
    public int getRowCount() {
        return siteList.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumSitesTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumSitesTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        EnumSitesTableColumns enumCol = EnumSitesTableColumns.getColumnFromColumnNumber(columnIndex);

        MobileSite mobileSite = siteList.get(rowIndex);

        switch (enumCol) {
            case Name:
                val = mobileSite.getName();
                break;
            case ShortName:
                val = mobileSite.getShortName();
                break;
            case LingName:
                val = mobileSite.getLongName();
                break;
            case UUID:
                val = mobileSite.getUUID();
                break;
            case WeatherCondition:
                val = mobileSite.getWeather().getCondition();
                break;
            case WeatherTemperature:
                val = mobileSite.getWeather().getTemperature();
                break;
            case EfficiencyAverage:
                val = mobileSite.getEfficiencyAverage();
                break;
            case EfficiencyAverageDelta:
                val = mobileSite.getEfficiencyAverageDelta();
                break;
            case Savings:
                val = mobileSite.getSavings();
                break;
            case OptimizationStatus:
                val = mobileSite.getOptimizationStatus();
                break;

        }

        return val;
    }

}
