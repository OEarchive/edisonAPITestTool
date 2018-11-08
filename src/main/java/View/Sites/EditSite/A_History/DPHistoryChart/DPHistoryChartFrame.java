package View.Sites.EditSite.A_History.DPHistoryChart;

import Controller.OptiCxAPIController;
import Model.PropertyChangeNames;
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

public class DPHistoryChartFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private final OptiCxAPIController controller;
    private final int maxPoints;
    private final DateTimeZone timeZone;

    public DPHistoryChartFrame(OptiCxAPIController controller, StampsAndPoints sp, int maxPoints, DateTimeZone timeZone) {
        initComponents();
        this.controller = controller;
        this.maxPoints = maxPoints;
        this.timeZone = timeZone;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ChartPanel CP = getChart(sp.getTimeStamps(), sp.getPointsMap());
        this.setContentPane(CP);
        //this.add(CP);
        //this.setVisible(true);
    }

    private void resetChart(List<String> historyTimestamps, Map historyPoints) {
        ChartPanel CP = getChart(historyTimestamps, historyPoints);
        this.setContentPane(CP);

        CP.revalidate();
        //this.invalidate();
    }

    private ChartPanel getChart(List<String> historyTimestamps, Map historyPoints) {

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

    private DefaultCategoryDataset getDataSet(Map historyPoints, List<String> historyTimestamps) {

        List<String> pointNames = new ArrayList<>();
        for (Object key : historyPoints.keySet()) {
            pointNames.add((String) key);
        }

        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        DateTimeFormatter fromStringFmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTimeFormatter toStringFmt = DateTimeFormat.forPattern("MM/dd HH:mm");
        int maxPointsInBucket = Math.max(1, historyTimestamps.size() / maxPoints);
        double numOfPointsInCurrentBucket = 0;
        int tsIndex = 0;

        for (String ts : historyTimestamps) {
            numOfPointsInCurrentBucket += 1;
            tsIndex++;
            if (numOfPointsInCurrentBucket >= maxPointsInBucket || tsIndex >= historyTimestamps.size()) {
                numOfPointsInCurrentBucket = 0;

                DateTime localEnd = DateTime.parse(ts).withZone(timeZone);
                String tsString = localEnd.toString(toStringFmt);
                for (String dpName : pointNames) {

                    List<Object> vals = (List<Object>) historyPoints.get(dpName);

                    if (vals != null && tsIndex-1 < vals.size() ) {
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

        jPanelMainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanelMainPanelLayout = new javax.swing.GroupLayout(jPanelMainPanel);
        jPanelMainPanel.setLayout(jPanelMainPanelLayout);
        jPanelMainPanelLayout.setHorizontalGroup(
            jPanelMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );
        jPanelMainPanelLayout.setVerticalGroup(
            jPanelMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelMainPanel;
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
