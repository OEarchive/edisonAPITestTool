package View.Sites.EditSite.A_History.ReportVerification.Chart;

import Controller.OptiCxAPIController;
import View.Sites.EditSite.A_History.DPHistoryChart.SparselyLabeledCategoryAxis;
import View.Sites.EditSite.A_History.DPHistoryChart.StampsAndPoints;
import View.Sites.EditSite.EnumTZOffsets;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
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

public class ReportVerificationChartFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private final OptiCxAPIController controller;
    private final int maxPoints;
    private final DateTimeZone siteTimeZone;

    public ReportVerificationChartFrame(OptiCxAPIController controller, StampsAndPointsForChart sp, int maxPoints, DateTimeZone siteTimeZone) {
        initComponents();
        this.controller = controller;
        this.maxPoints = maxPoints;
        this.siteTimeZone = siteTimeZone;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ChartPanel CP = getChart(sp.getTimeStamps(), sp.getPointsMap());
        this.setContentPane(CP);
    }

    private void resetChart(List<DateTime> historyTimestamps, Map historyPoints) {
        ChartPanel CP = getChart(historyTimestamps, historyPoints);
        this.setContentPane(CP);

        CP.revalidate();
        //this.invalidate();
    }

    private ChartPanel getChart(List<DateTime> historyTimestamps, Map historyPoints) {

        DefaultCategoryDataset ds = getDataSet(historyPoints, historyTimestamps);

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

    private DefaultCategoryDataset getDataSet(Map historyPoints, List<DateTime> historyTimestamps) {

        List<String> pointNames = new ArrayList<>();
        for (Object key : historyPoints.keySet()) {
            pointNames.add((String) key);
        }

        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        DateTimeFormatter fromStringFmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        DateTimeFormatter toStringFmt = DateTimeFormat.forPattern("MM/dd HH:mm");
        int maxPointsInBucket = Math.max(1, historyTimestamps.size() / maxPoints);
        double numOfPointsInCurrentBucket = 0;
        int tsIndex = 0;

        for (DateTime ts : historyTimestamps) {
            numOfPointsInCurrentBucket += 1;
            tsIndex++;
            if (numOfPointsInCurrentBucket >= maxPointsInBucket || tsIndex >= historyTimestamps.size()) {
                numOfPointsInCurrentBucket = 0;

                //DateTime localEnd = DateTime.parse(ts).withZoneRetainFields(siteTimeZone);
                DateTime localEnd = ts;
                String tsString = localEnd.toString(toStringFmt);
                for (String dpName : pointNames) {

                    List<Object> vals = (List<Object>) historyPoints.get(dpName);

                    if (vals != null && tsIndex - 1 < vals.size()) {
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //nop for now
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
