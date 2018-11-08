package View.MobileView.MobileTrendChart;

import Controller.OptiCxAPIController;
import Model.DataModels.TrendAPI.MobileTrends.SpecificTrends.MobilePlantEfficiency;
import Model.DataModels.TrendAPI.MobileTrends.MobileTrendSeries;
import Model.DataModels.TrendAPI.MobileTrends.SpecificTrends.MobileChillerTrend;
import Model.DataModels.TrendAPI.MobileTrends.SpecificTrends.MobileKeyTrend;
import Model.DataModels.TrendAPI.MobileTrends.SpecificTrends.MobileOptimizationTrend;
import Model.DataModels.TrendAPI.MobileTrends.SpecificTrends.MobileSavingsTrend;
import Model.DataModels.TrendAPI.SiteInfo.EnumMobileTrendTypes;
import Model.PropertyChangeNames;
import View.Sites.EditSite.A_History.DPHistoryChart.SparselyLabeledCategoryAxis;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class MobileTrendChartFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private final OptiCxAPIController controller;
    private final EnumMobileTrendTypes trendType;

    public MobileTrendChartFrame(OptiCxAPIController controller, String selectedMobileSiteUUID, EnumMobileTrendTypes trendType, String timeFrame) {
        initComponents();
        
        //this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.controller = controller;
        this.trendType = trendType;

        controller.getMobileTrend(selectedMobileSiteUUID, trendType, timeFrame);
    }

    @Override
    public void dispose() {
        controller.removePropChangeListener(this);
        super.dispose();
    }

    private void resetChart(MobileSavingsTrend mobileTrend) {

        List<MobileTrendSeries> series = mobileTrend.getSavingsGraph().getSeries();
        List<String> categories = mobileTrend.getSavingsGraph().getXAxis().getCategories();

        //this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ChartPanel CP = getChart(categories, series);
        this.setContentPane(CP);

        CP.revalidate();
        //this.invalidate();
    }

    private void resetChart(MobilePlantEfficiency mobileTrend) {

        List<MobileTrendSeries> series = mobileTrend.getEfficiencyGraph().getSeries();
        List<String> categories = mobileTrend.getEfficiencyGraph().getXAxis().getCategories();

        //this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ChartPanel CP = getChart(categories, series);
        this.setContentPane(CP);

        CP.revalidate();
        //this.invalidate();
    }
      
    private void resetChart(MobileOptimizationTrend mobileTrend) {

        List<MobileTrendSeries> series = mobileTrend.getSavingsGraph().getSeries();
        List<String> categories = mobileTrend.getSavingsGraph().getXAxis().getCategories();

        //this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ChartPanel CP = getChart(categories, series);
        this.setContentPane(CP);

        CP.revalidate();
        //this.invalidate();
    }
    
    private void resetChart(MobileChillerTrend mobileTrend) {

        List<MobileTrendSeries> series = mobileTrend.getEfficiencyGraph().getSeries();
        List<String> categories = mobileTrend.getEfficiencyGraph().getXAxis().getCategories();

        //this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ChartPanel CP = getChart(categories, series);
        this.setContentPane(CP);

        CP.revalidate();
        //this.invalidate();
    }
    
    private void resetChart(MobileKeyTrend mobileTrend) {

        List<MobileTrendSeries> series = mobileTrend.getKeyGraph().getSeries();
        List<String> categories = mobileTrend.getKeyGraph().getXAxis().getCategories();

        //this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ChartPanel CP = getChart(categories, series);
        this.setContentPane(CP);

        CP.revalidate();
        //this.invalidate();
    }

    private ChartPanel getChart(List<String> categories, List<MobileTrendSeries> series) {

        DefaultCategoryDataset ds = getDataSet(categories, series);

        JFreeChart chart = ChartFactory.createLineChart(
                trendType.getFriendlyName(),
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

    private DefaultCategoryDataset getDataSet(List<String> categories, List<MobileTrendSeries> series) {

        DefaultCategoryDataset ds = new DefaultCategoryDataset();

        int index = 0;
        for (String catName : categories) {
            for (MobileTrendSeries s : series) {
                ds.addValue(s.getData().get(index), s.getName(), catName);
            }
            index++;

        }

        return ds;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 707, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 491, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        /*
        MobileSavingsTrendReturned("MobileSavingsTrendReturned"),
        MobilePlantEfficiencyTrendReturned("MobilePlantEfficiencyTrendReturned"),
        MobileOptimizationTrendReturned("MobileOptimizationTrendReturned"),
        MobileChillerTrendReturned("MobileChillerTrendReturned"),
        MobileKeyTrendReturned("MobileKeyTrendReturned");
         */
        if (propName.equals(PropertyChangeNames.MobileSavingsTrendReturned.getName()) && trendType == EnumMobileTrendTypes.savings) {
            resetChart((MobileSavingsTrend) evt.getNewValue());

        } else if (propName.equals(PropertyChangeNames.MobilePlantEfficiencyTrendReturned.getName()) && trendType == EnumMobileTrendTypes.plant) {
            resetChart((MobilePlantEfficiency) evt.getNewValue());
         
        } else if (propName.equals(PropertyChangeNames.MobileOptimizationTrendReturned.getName()) && trendType == EnumMobileTrendTypes.optimization) {
            resetChart((MobileOptimizationTrend) evt.getNewValue());
         
        } else if (propName.equals(PropertyChangeNames.MobileChillerTrendReturned.getName()) && trendType == EnumMobileTrendTypes.chiller) {
            resetChart((MobileChillerTrend) evt.getNewValue());
            
        } else if (propName.equals(PropertyChangeNames.MobileKeyTrendReturned.getName()) && trendType == EnumMobileTrendTypes.key) {
            resetChart((MobileKeyTrend) evt.getNewValue());

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            dispose();
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
