
package View.Sites.EditSite.F_SiteTrend;

import Controller.OptiCxAPIController;
import Model.DataModels.Sites.SiteTrendKPI;
import Model.DataModels.Sites.TimeSeriesDataItem;
import Model.PropertyChangeNames;
import View.Sites.EditSite.A_History.DPHistoryChart.SparselyLabeledCategoryAxis;
import View.Sites.EditSite.A_History.DPHistoryChart.StampsAndPoints;
import View.Sites.EditSite.EnumTZOffsets;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class SiteTrendChartFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private final OptiCxAPIController controller;
    private final SiteTrendKPI siteTrendKPI;
    private final int maxPoints;
    private final EnumTZOffsets timeZone;
    

    public SiteTrendChartFrame( OptiCxAPIController controller, SiteTrendKPI siteTrendKPI, int maxPoints, EnumTZOffsets timeZone) {
        initComponents();
        
        this.controller = controller;
        this.siteTrendKPI = siteTrendKPI;
        this.maxPoints = maxPoints;
        this.timeZone = timeZone;
    }
    
    private void resetChart(List<String> historyTimestamps, Map historyPoints) {
        ChartPanel CP = getChart(historyTimestamps, historyPoints);
        this.setContentPane(CP);
        
        CP.revalidate();
        //this.invalidate();
    }

    private ChartPanel getChart(List<String> historyTimestamps, Map historyPoints) {

        DefaultCategoryDataset ds = getDataSet();

        JFreeChart chart = ChartFactory.createLineChart(
                "Datapoint Values",
                "Date",
                "Values",
                ds,
                PlotOrientation.VERTICAL,
                true, true, false);

        chart.setBackgroundPaint(Color.white);
        chart.getCategoryPlot().setDomainAxis(new SparselyLabeledCategoryAxis(15));
        CategoryAxis domainAxis = chart.getCategoryPlot().getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        ChartPanel CP = new ChartPanel(chart);
        return CP;
    }

    private DefaultCategoryDataset getDataSet() {

        
        
        List<String> pointNames = new ArrayList<>();
        Map<String,TimeSeriesDataItem> valuesMap = new HashMap<>();
        for ( TimeSeriesDataItem tsDataItem : siteTrendKPI.getSeries()) {
            pointNames.add(tsDataItem.getName());
            valuesMap.put( tsDataItem.getName(), tsDataItem );
        }

        

        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        DateTimeFormatter fromStringFmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTimeFormatter toStringFmt = DateTimeFormat.forPattern("MM/dd HH:mm");
        int maxPointsInBucket = Math.max(1, siteTrendKPI.getTimestamps().size() / maxPoints);
        double numOfPointsInCurrentBucket = 0;
        int tsIndex = 0;

        for (String ts : siteTrendKPI.getTimestamps()) {
            numOfPointsInCurrentBucket += 1;
            tsIndex++;
            if (numOfPointsInCurrentBucket >= maxPointsInBucket || tsIndex >= siteTrendKPI.getTimestamps().size()) {
                numOfPointsInCurrentBucket = 0;

                DateTime localEnd = DateTime.parse(ts).withZone(DateTimeZone.forID( timeZone.getZoneName()));
                String tsString = localEnd.toString(toStringFmt);
                for (String dpName : pointNames) {

                    TimeSeriesDataItem tsdi = valuesMap.get( dpName );
                    List<Object> vals = tsdi.getValues();

                    if (vals != null) {
                        Object val = vals.get(tsIndex - 1);
                        if (val instanceof Double) {
                            ds.addValue((Double) val, dpName, tsString);
                        } else if (val instanceof Integer) {
                            ds.addValue((Integer) val, dpName, tsString);
                        } else if (val instanceof Boolean) {
                            ds.addValue(((Boolean) val) ? 1.0 : 0.0, dpName, tsString);
                        } else if (val == null) {
                            ds.addValue((Double) 0.0, dpName, tsString);
                        }
                    }
                }
            }
        }
        return ds;
    }

    @Override
    public void dispose() {
        controller.removePropChangeListener(this);
        super.dispose();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
                String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.UpdateHistoryChart.getName())) {
            StampsAndPoints newStampsAndValues = (StampsAndPoints) evt.getNewValue();
            resetChart(newStampsAndValues.getTimeStamps(), newStampsAndValues.getPointsMap());

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            dispose();
        }
    }
}
