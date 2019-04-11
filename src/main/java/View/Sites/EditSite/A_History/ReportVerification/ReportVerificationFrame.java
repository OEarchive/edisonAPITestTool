package View.Sites.EditSite.A_History.ReportVerification;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointHistoriesQueryParams;
import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.Datapoints.EnumAggregationType;
import Model.DataModels.Datapoints.EnumEdisonResolutions;
import Model.DataModels.Graph.EnumGraphNodeTypes;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointFilter;
import Model.DataModels.ReportVerification.CalcPointClassification.AssociatedPoints;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointMinimumResolution;
import Model.DataModels.ReportVerification.CalcPointClassification.Equipment.EquipmentInfo;
import Model.DataModels.ReportVerification.PostProcess.CalculatedBucket;
import Model.DataModels.ReportVerification.PostProcess.CalculatedBucketList;
import Model.DataModels.ReportVerification.PreProcess.UnCalculatedBucketList;
import Model.DataModels.ReportVerification.PreProcess.PreProcessMaps;
import Model.DataModels.ReportVerification.PreProcess.UnCalculatedPoint;

import Model.PropertyChangeNames;
import View.Sites.EditSite.A_History.DataPointsAdmin.Associations.DataPointAssociationsTableCellRenderer;
import View.Sites.EditSite.A_History.DatapointListTable.DataPointAssiationsTable.DatapointAssociationsTableModel;
import Model.DataModels.ReportVerification.ReportHistoryQueryParams;
import View.Sites.EditSite.A_History.ReportVerification.A_RVQueryResultsTable.RVQueryResultsTableCellRenderer;
import View.Sites.EditSite.A_History.ReportVerification.A_RVQueryResultsTable.RVQueryResultsTableModel;
import View.Sites.EditSite.A_History.ReportVerification.C_RVGrandStatsTable.RVGrandStatsTableCellRenderer;
import View.Sites.EditSite.A_History.ReportVerification.C_RVGrandStatsTable.RVGrandStatsTableModel;
import View.Sites.EditSite.A_History.ReportVerification.D_ReportPointsList.PopupMenuForPointsTable;
import View.Sites.EditSite.A_History.ReportVerification.D_ReportPointsList.RVHistoriesResponseComparator;
import View.Sites.EditSite.A_History.ReportVerification.D_ReportPointsList.ReportPointsTableCellRenderer;
import View.Sites.EditSite.A_History.ReportVerification.D_ReportPointsList.ReportPointsTableModel;
import Model.DataModels.ReportVerification.PostProcess.BucketListStats;
import Model.DataModels.ReportVerification.PostProcess.ReportTotalsCalculator;
import View.Sites.EditSite.A_History.ReportVerification.A_RVQueryResultsTable.PopupMenuForRVHistory;
import View.Sites.EditSite.A_History.ReportVerification.Chart.ReportVerificationChartFrame;
import View.Sites.EditSite.A_History.ReportVerification.Chart.StampsAndPointsForChart;
import View.Sites.EditSite.A_History.ReportVerification.ReportDates.EnumDateTypes;
import View.Sites.EditSite.A_History.ReportVerification.ReportDates.EnumReportMonths;
import View.Sites.EditSite.A_History.ReportVerification.ReportDates.EnumReportYears;
import View.Sites.EditSite.A_History.ReportVerification.ReportTotals.ReportTotalsTableCellRenderer;
import View.Sites.EditSite.A_History.ReportVerification.ReportTotals.ReportTotalsTableModel;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class ReportVerificationFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private DateTimeFormatter zzFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
    private DateTimeFormatter utcLabelFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

    private static ReportVerificationFrame thisInstance;
    private final OptiCxAPIController controller;
    private final String siteSid;
    private final DateTimeZone siteTimeZone;

    private List<DatapointsAndMetadataResponse> datapointsForReports;
    private List<String> allUNames;

    private Map<String, UnCalculatedPoint> uNameToDatapointMap;
    private AssociatedPoints associatedPointsInfo;

    private DateTime siteLocalStart;
    private DateTime siteLocalEnd;
    private DateTime utcStart;
    private DateTime utcEnd;

    private CalculatedBucketList calculatedBucketList;
    private BucketListStats bucketListStats;
    private ReportTotalsCalculator reportTotalsCalculator;

    private EnumCalcPointFilter selectedCalculatedPointEnum;

    private int currentSelectedBucketNumber = 0;

    private EquipmentInfo equipInfo;

    private EnumReportMonths reportMonth;
    private EnumReportYears reportYear;
    private EnumDateTypes reportDateType;

    public static ReportVerificationFrame getInstance(final OptiCxAPIController controller, String siteSid, DateTimeZone siteTimeZone, List<DatapointsAndMetadataResponse> listOfMetadata) {

        if (thisInstance == null) {
            thisInstance = new ReportVerificationFrame(controller, siteSid, siteTimeZone, listOfMetadata);
        }
        return thisInstance;

    }

    private ReportVerificationFrame(final OptiCxAPIController controller, String siteSid, DateTimeZone siteTimeZone, List<DatapointsAndMetadataResponse> initialPoints) {
        initComponents();

        this.controller = controller;
        this.siteSid = siteSid;
        this.siteTimeZone = siteTimeZone;

        jLabelSitetimeZone.setText("SiteTZ: " + this.siteTimeZone.getID());

        fillHistoryResolutionDropdown();
        SpinnerNumberModel spinModel = new SpinnerNumberModel(3, 0, 6, 1);
        this.jSpinnerPrec.setModel(spinModel);
        jSpinnerPrec.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                if (calculatedBucketList.getBucketDates().size() > 0) {
                    int prec = (int) jSpinnerPrec.getModel().getValue();
                    fillRVQueryResultsTable(prec);
                    fillRVGrandStatsTable(prec);
                    fillReportTotalsTable(prec);
                }
            }
        });

        reportMonth = EnumReportMonths.Jan;
        reportYear = EnumReportYears.y2018;
        reportDateType = EnumDateTypes.MNTH;

        setStartAndEndDates(reportMonth, reportYear, reportDateType);

        fillReportMonthDropDown();
        fillReportYearDropDown();
        fillReportDateTypesDropDown();

        fillCalcPointDropdown();
        this.jButtonViewChart.setEnabled(false);

        this.jCheckBoxSparse.setSelected(true);

        getInitialPoints(initialPoints);

    }

    private void fillReportMonthDropDown() {

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumReportMonths.getMonthNames().toArray());
        this.jComboBoxReportMonth.setModel(comboBoxModel);
        this.jComboBoxReportMonth.setSelectedIndex(EnumReportMonths.Jan.getDropDownIndex());
        this.jComboBoxReportMonth.setEnabled(true);

        this.jComboBoxReportMonth.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                reportMonth = EnumReportMonths.getMonthFromName(name);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setStartAndEndDates(reportMonth, reportYear, reportDateType);
                    }
                });

            }
        });
    }

    private void fillReportYearDropDown() {

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumReportYears.getYearNames().toArray());
        this.jComboBoxReportYear.setModel(comboBoxModel);
        this.jComboBoxReportYear.setSelectedIndex(EnumReportYears.y2018.getDropDownIndex());
        this.jComboBoxReportYear.setEnabled(true);

        this.jComboBoxReportYear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                reportYear = EnumReportYears.getYearFromName(name);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setStartAndEndDates(reportMonth, reportYear, reportDateType);
                    }
                });

            }
        });
    }

    private void fillReportDateTypesDropDown() {

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumDateTypes.getDateTypes().toArray());
        this.jComboBoxDateType.setModel(comboBoxModel);
        this.jComboBoxDateType.setSelectedIndex(EnumDateTypes.MNTH.getDropDownIndex());
        this.jComboBoxDateType.setEnabled(true);

        this.jComboBoxDateType.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                reportDateType = EnumDateTypes.getDateTypeFromName(name);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setStartAndEndDates(reportMonth, reportYear, reportDateType);
                    }
                });

            }
        });
    }

    private void setStartAndEndDates(EnumReportMonths month, EnumReportYears year, EnumDateTypes dateType) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month.getMonthNumber());
        cal.set(Calendar.YEAR, year.getYearNumber());
        int numOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        switch (dateType) {
            case MNTH: {
                siteLocalStart = new DateTime(year.getYearNumber(), month.getMonthNumber() + 1, 1, 0, 0, siteTimeZone);
                siteLocalEnd = siteLocalStart.plusDays(numOfDaysInMonth);
            }
            break;
            case LM: {
                siteLocalStart = new DateTime(year.getYearNumber(), month.getMonthNumber() + 1, 1, 0, 0, siteTimeZone);
                siteLocalStart = siteLocalStart.minusMonths(1);
                siteLocalEnd = siteLocalStart.plusDays(numOfDaysInMonth);
            }
            break;
            case YTD: {
                siteLocalStart = new DateTime(year.getYearNumber(), 1, 1, 0, 0, siteTimeZone);
                siteLocalEnd = new DateTime(year.getYearNumber(), month.getMonthNumber() + 1, 1, 0, 0, siteTimeZone);
                siteLocalEnd = siteLocalEnd.plusDays(numOfDaysInMonth);
            }
            break;
            case L12: {
                siteLocalEnd = new DateTime(year.getYearNumber(), month.getMonthNumber() + 1, 1, 0, 0, siteTimeZone);
                siteLocalEnd = siteLocalEnd.plusDays(numOfDaysInMonth);
                siteLocalStart = siteLocalEnd.minusMonths(12);
            }
            break;
        }

        this.jTextFieldSiteLocalStartDate.setText(siteLocalStart.toString());
        this.jTextFieldSiteLocalEndDate.setText(siteLocalEnd.toString());

        utcStart = siteLocalStart.withZone(DateTimeZone.UTC);
        utcEnd = siteLocalEnd.withZone(DateTimeZone.UTC);
        jLabelUTCStart.setText(utcStart.toString(utcLabelFormat)+ " (UTC)");
        jLabelUTCEnd.setText(utcEnd.toString(utcLabelFormat) + " (UTC)");

    }

    private String getUName(String sid, String name) {
        String uName = name;
        String[] pieces = sid.split("\\.");

        if (pieces.length > 2) {
            uName = pieces[2] + "." + uName;
        }
        return uName;
    }

    private void fillCalcPointDropdown() {

        selectedCalculatedPointEnum = EnumCalcPointFilter.kWh;
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumCalcPointFilter.getPointNames().toArray());
        this.jComboBoxCalculatedPoints.setModel(comboBoxModel);
        this.jComboBoxCalculatedPoints.setSelectedIndex(selectedCalculatedPointEnum.getDropDownIndex());
        this.jComboBoxCalculatedPoints.setEnabled(true);

        this.jComboBoxCalculatedPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                selectedCalculatedPointEnum = EnumCalcPointFilter.getEnumFromPointName(name);
                //selectDependentPoints(calcPointType);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        fillReportPointsTable(selectedCalculatedPointEnum);
                        jButtonViewChart.setEnabled(false);
                        clearRVQueryResultsTable();
                    }
                });

            }
        });
    }

    private void getInitialPoints(List<DatapointsAndMetadataResponse> initialPoints) {
        this.equipInfo = new EquipmentInfo(initialPoints);

        Map< String, List<String>> mapOfSidsAndPoints = new HashMap<>();

        mapOfSidsAndPoints.put(this.siteSid, new ArrayList<String>());
        mapOfSidsAndPoints.put(this.siteSid + ".st:1", new ArrayList<String>());

        for (EnumGraphNodeTypes eType : EnumGraphNodeTypes.values()) {
            List<String> eSids = equipInfo.getEquipTypeToSidsMap().get(eType);
            if (eSids != null) {
                for (String sid : eSids) {
                    mapOfSidsAndPoints.put(sid, new ArrayList<String>());
                }
            }
        }
        controller.getDatapointsUnionForReports(mapOfSidsAndPoints);
    }

    private void setCalcPointInfoAndPointNameMapping() {

        associatedPointsInfo = new AssociatedPoints(equipInfo, datapointsForReports);

        this.allUNames = new ArrayList<>();
        this.uNameToDatapointMap = new HashMap<>();
        for (DatapointsAndMetadataResponse resp : datapointsForReports) {

            String uName = getUName(resp.getSid(), resp.getName());
            allUNames.add(uName);
            this.uNameToDatapointMap.put(uName, new UnCalculatedPoint(resp));

        }
    }

    @Override
    public void dispose() {
        controller.removePropChangeListener(this);
        thisInstance = null;
        super.dispose();
    }

    private void fillHistoryResolutionDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumEdisonResolutions.getNames().toArray());
        EnumEdisonResolutions res = EnumEdisonResolutions.MINUTE5;
        this.jComboBoxResolution.setModel(comboBoxModel);
        this.jComboBoxResolution.setSelectedIndex(res.getDropDownIndex());
        this.jComboBoxResolution.setEnabled(true);
    }

    private void fillReportPointsTable(EnumCalcPointFilter calcPointType) {

        List<String> filteredUNames = new ArrayList<>();
        List<UnCalculatedPoint> filteredListUnCalcedPoints = new ArrayList<>();

        for (String uName : allUNames) {

            //if( uName.contains("ChilledWaterFlo")){
            //    System.out.println("found chilled water flow");
            //}
            UnCalculatedPoint uncalcdPoint = uNameToDatapointMap.get(uName);
            if (calcPointType == null) {
                filteredListUnCalcedPoints.add(uNameToDatapointMap.get(uName));
                filteredUNames.add(uName);
            } else if (associatedPointsInfo.isDependent(calcPointType, uncalcdPoint)) {
                filteredListUnCalcedPoints.add(uNameToDatapointMap.get(uName));
                filteredUNames.add(uName);
            }
        }

        this.jTablePointsList.setDefaultRenderer(Object.class, new ReportPointsTableCellRenderer());
        this.jTablePointsList.setModel(new ReportPointsTableModel(filteredUNames, filteredListUnCalcedPoints));
        this.jTablePointsList.setAutoCreateRowSorter(true);

        setPointCounts();

    }

    private void setPointCounts() {

        ReportPointsTableModel mod = (ReportPointsTableModel) this.jTablePointsList.getModel();
        int numOfPoints = mod.getRowCount();
        jLabelNumOfPoints.setText(Integer.toString(numOfPoints));

    }

    public void clearRVQueryResultsTable() {

        jTableQueryResults.setModel(new DefaultTableModel());
        jTableQueryResults.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

    }

    public void fillRVQueryResultsTable(int prec) {

        RVQueryResultsTableModel tableModel = new RVQueryResultsTableModel(calculatedBucketList);
        jTableQueryResults.setModel(tableModel);
        jTableQueryResults.setDefaultRenderer(Object.class, new RVQueryResultsTableCellRenderer(prec, siteTimeZone, calculatedBucketList));
        jTableQueryResults.setAutoCreateRowSorter(true);
        fixHistoryQueryResultsTableColumnWidths(jTableQueryResults);
    }

    public void fillRVGrandStatsTable(int prec) {
        this.jTableGrandStats.setModel(new RVGrandStatsTableModel(bucketListStats));
        this.jTableGrandStats.setDefaultRenderer(Object.class, new RVGrandStatsTableCellRenderer(prec));
        this.jTableGrandStats.setAutoCreateRowSorter(true);
        fixHistoryQueryResultsTableColumnWidths(jTableGrandStats);
    }

    public void fillReportTotalsTable(int prec) {
        this.jTableReportTotals.setModel(new ReportTotalsTableModel(reportTotalsCalculator.getNamesInOrder(), reportTotalsCalculator.getKeyValuePairs()));
        this.jTableReportTotals.setDefaultRenderer(Object.class, new ReportTotalsTableCellRenderer(prec));
        this.jTableReportTotals.setAutoCreateRowSorter(true);

    }

    public void fixHistoryQueryResultsTableColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        for (int i = 0; i < t.getColumnCount(); i++) {
            column = t.getColumnModel().getColumn(i);
            if (i < 2) {
                column.setPreferredWidth(200);
            } else {
                column.setPreferredWidth(100);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxResolution = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jButtonRunQuery = new javax.swing.JButton();
        jTextFieldSiteLocalStartDate = new javax.swing.JTextField();
        jTextFieldSiteLocalEndDate = new javax.swing.JTextField();
        jLabelUTCStart = new javax.swing.JLabel();
        jLabelUTCEnd = new javax.swing.JLabel();
        jLabelSitetimeZone = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButtonViewChart = new javax.swing.JButton();
        jSpinnerPrec = new javax.swing.JSpinner();
        jComboBoxReportYear = new javax.swing.JComboBox<>();
        jComboBoxReportMonth = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jCheckBoxSparse = new javax.swing.JCheckBox();
        jComboBoxDateType = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableQueryResults = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableGrandStats = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableAssociations = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableReportTotals = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTablePointsList = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxCalculatedPoints = new javax.swing.JComboBox<>();
        jLabelNumOfPoints = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButtonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Report Verification");
        setAutoRequestFocus(false);

        jSplitPane1.setDividerLocation(750);
        jSplitPane1.setDividerSize(10);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Query Parameters"));

        jLabel1.setText("Start Date:");

        jLabel2.setText("EndDate:");

        jComboBoxResolution.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Res:");

        jButtonRunQuery.setText("Run Query");
        jButtonRunQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunQueryActionPerformed(evt);
            }
        });

        jTextFieldSiteLocalStartDate.setText("jTextField1");
        jTextFieldSiteLocalStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSiteLocalStartDateActionPerformed(evt);
            }
        });

        jTextFieldSiteLocalEndDate.setText("jTextField2");
        jTextFieldSiteLocalEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSiteLocalEndDateActionPerformed(evt);
            }
        });

        jLabelUTCStart.setText("*utcStart*");

        jLabelUTCEnd.setText("*utcEnd*");

        jLabelSitetimeZone.setText("*sitetimezone*");

        jLabel4.setText("Prec:");

        jButtonViewChart.setText("Chart");
        jButtonViewChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewChartActionPerformed(evt);
            }
        });

        jComboBoxReportYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxReportMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Report:");

        jCheckBoxSparse.setText("Sparse?");

        jComboBoxDateType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldSiteLocalEndDate))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldSiteLocalStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUTCStart)
                            .addComponent(jLabelUTCEnd)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSitetimeZone, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBoxSparse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRunQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerPrec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonViewChart))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxReportMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxReportYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxDateType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldSiteLocalStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUTCStart)
                    .addComponent(jButtonRunQuery)
                    .addComponent(jCheckBoxSparse))
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldSiteLocalEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUTCEnd)
                    .addComponent(jButtonViewChart)
                    .addComponent(jLabel4)
                    .addComponent(jSpinnerPrec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSitetimeZone)
                    .addComponent(jComboBoxReportYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxReportMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxDateType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Query Results"));

        jTableQueryResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableQueryResults.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableQueryResults.setShowGrid(true);
        jTableQueryResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableQueryResultsMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableQueryResultsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableQueryResults);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTableGrandStats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableGrandStats.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableGrandStats.setShowGrid(true);
        jScrollPane3.setViewportView(jTableGrandStats);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Points List"));

        jSplitPane2.setDividerLocation(400);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Point Associations"));

        jTableAssociations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableAssociations.setShowGrid(true);
        jScrollPane5.setViewportView(jTableAssociations);

        jScrollPane7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Report Totals"));

        jTableReportTotals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableReportTotals.setShowGrid(true);
        jScrollPane7.setViewportView(jTableReportTotals);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setBottomComponent(jPanel5);

        jTablePointsList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTablePointsList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTablePointsList.setShowGrid(true);
        jTablePointsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTablePointsListMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePointsListMouseClicked(evt);
            }
        });
        jTablePointsList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTablePointsListPropertyChange(evt);
            }
        });
        jTablePointsList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTablePointsListKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablePointsListKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(jTablePointsList);

        jLabel5.setText("Calc Point:");

        jComboBoxCalculatedPoints.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelNumOfPoints.setText("*numOfPoints*");

        jLabel6.setText("Num of Points:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelNumOfPoints)
                        .addGap(0, 452, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxCalculatedPoints, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxCalculatedPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNumOfPoints)
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        jSplitPane2.setLeftComponent(jPanel6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClose))
                    .addComponent(jSplitPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClose)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablePointsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePointsListMouseClicked
        if (evt.isPopupTrigger()) {
            PopupMenuForPointsTable popup = new PopupMenuForPointsTable(evt, jTablePointsList);
        }

    }//GEN-LAST:event_jTablePointsListMouseClicked

    private void jTablePointsListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePointsListMousePressed
        //setPointCounts();

        int row = jTablePointsList.rowAtPoint(evt.getPoint());
        int modelIndex = jTablePointsList.convertRowIndexToModel(row);
        ReportPointsTableModel mod = (ReportPointsTableModel) jTablePointsList.getModel();
        UnCalculatedPoint resp = mod.getRow(modelIndex);

        jTableAssociations.setModel(new DatapointAssociationsTableModel(resp.getDatapointAssociations()));
        jTableAssociations.setDefaultRenderer(Object.class, new DataPointAssociationsTableCellRenderer());

    }//GEN-LAST:event_jTablePointsListMousePressed

    private void jTablePointsListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTablePointsListPropertyChange
        //String propName = evt.getPropertyName();
        //setPointCounts();
    }//GEN-LAST:event_jTablePointsListPropertyChange

    private void jTablePointsListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablePointsListKeyReleased
        //setPointCounts();
    }//GEN-LAST:event_jTablePointsListKeyReleased

    private void jTablePointsListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablePointsListKeyPressed
        //setPointCounts();
    }//GEN-LAST:event_jTablePointsListKeyPressed

    private void jButtonRunQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunQueryActionPerformed
        jTableQueryResults.setModel(new DefaultTableModel());
        jTableQueryResults
                .setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

        jTableGrandStats.setModel(new DefaultTableModel());
        jTableReportTotals.setModel(new DefaultTableModel());

        String calcPointName = selectedCalculatedPointEnum.getCalculatedPointName();
        ReportPointsTableModel tableModel = (ReportPointsTableModel) (jTablePointsList.getModel());

        Map<String, List<String>> mainPointsToQuery = new HashMap<>();
        Map<String, List<String>> pointsToQuery = new HashMap<>();
        for (UnCalculatedPoint pointInfo : tableModel.getRows()) {

            if (pointInfo.getName().contentEquals(calcPointName)) {
                List<String> mainPointNames = new ArrayList<>();
                mainPointNames.add(calcPointName);
                mainPointsToQuery.put(pointInfo.getSid(), mainPointNames);
            } else {
                if (!pointsToQuery.containsKey(pointInfo.getSid())) {
                    pointsToQuery.put(pointInfo.getSid(), new ArrayList<String>());
                }
                pointsToQuery.get(pointInfo.getSid()).add(pointInfo.getName());
            }
        }

        if (pointsToQuery.size() > 0 || mainPointsToQuery.size() > 0) {

            //TODO: Check this w/ the date range
            //EnumCalcPointMinimumResolution rrr = selectedCalculatedPointEnum.getMinResolution();
            String resString = (String) jComboBoxResolution.getSelectedItem();
            EnumEdisonResolutions res = EnumEdisonResolutions.getResolutionFromName(resString);

            ReportHistoryQueryParams reportHistoryParams = new ReportHistoryQueryParams();
            reportHistoryParams.addQuery(getHistoryParams(mainPointsToQuery, res, EnumAggregationType.NORMAL));
            reportHistoryParams.addQuery(getHistoryParams(pointsToQuery, EnumEdisonResolutions.MINUTE5, EnumAggregationType.NORMAL));

            controller.getHistoryForReportVerification(reportHistoryParams);
            //this.jButtonHistoryChart.setEnabled(true);
        } else {
            //this.jButtonHistoryChart.setEnabled(false);
        }

    }//GEN-LAST:event_jButtonRunQueryActionPerformed

    private List<DatapointHistoriesQueryParams> getHistoryParams(Map<String, List<String>> pointsToQuery, EnumEdisonResolutions res, EnumAggregationType aggType) {

        boolean showSparseDate = this.jCheckBoxSparse.isSelected();

        String startStr = jTextFieldSiteLocalStartDate.getText();
        String endStr = jTextFieldSiteLocalEndDate.getText();
        siteLocalStart = new DateTime(startStr).withZone(siteTimeZone);
        siteLocalEnd = new DateTime(endStr).withZone(siteTimeZone);

        List<DatapointHistoriesQueryParams> listOfParams = new ArrayList<>();
        for (String sid : pointsToQuery.keySet()) {
            List<String> listOfPointNames = pointsToQuery.get(sid);
            DatapointHistoriesQueryParams params = new DatapointHistoriesQueryParams(
                    sid, siteLocalStart, siteLocalEnd, res, showSparseDate, listOfPointNames, aggType);
            listOfParams.add(params);
        }

        return listOfParams;
    }
    
    /*
    private double getDouble(Object v) {

        if (v instanceof Double) {
            return (double) v;
        } else if (v instanceof Integer) {

            return (int) v;
        } else if (v instanceof Boolean) {

            Boolean flag = (Boolean) v;
            double temp = (flag) ? 1.0 : 0;
            return temp;
        } else {
            return 0.0;
        }
    }
    */


    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jTableQueryResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableQueryResultsMouseClicked

        int row = jTableQueryResults.rowAtPoint(evt.getPoint());
        int modelIndex = jTableQueryResults.convertRowIndexToModel(row);
        RVQueryResultsTableModel mod = (RVQueryResultsTableModel) jTableQueryResults.getModel();

        currentSelectedBucketNumber = calculatedBucketList.getBucketNumberFromRowIndex(modelIndex);

        int rowIndex = calculatedBucketList.getFirstRowIndexOfBucket(currentSelectedBucketNumber);
        jTableQueryResults.scrollRectToVisible(new Rectangle(jTableQueryResults.getCellRect(rowIndex, 0, true)));
        jTableQueryResults.getSelectionModel().setSelectionInterval(row, row);

        int prec = (int) this.jSpinnerPrec.getModel().getValue();
        CalculatedBucket currBucket = calculatedBucketList.getBucketFromBucketNumber(currentSelectedBucketNumber);

        if (evt.isPopupTrigger()) {
            PopupMenuForRVHistory popup = new PopupMenuForRVHistory(evt, jTableQueryResults);
        }


    }//GEN-LAST:event_jTableQueryResultsMouseClicked

    private void jTextFieldSiteLocalStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSiteLocalStartDateActionPerformed

        String dateString = this.jTextFieldSiteLocalStartDate.getText();
        siteLocalStart = DateTime.parse(dateString, zzFormat);
        utcStart = siteLocalStart.withZone(DateTimeZone.UTC);
        jLabelUTCStart.setText(utcStart.toString(utcLabelFormat)+ " (UTC)");
    }//GEN-LAST:event_jTextFieldSiteLocalStartDateActionPerformed

    private void jTextFieldSiteLocalEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSiteLocalEndDateActionPerformed

        String dateString = this.jTextFieldSiteLocalEndDate.getText();
        siteLocalEnd = DateTime.parse(dateString, zzFormat);
        utcEnd = siteLocalEnd.withZone(DateTimeZone.UTC);
        jLabelUTCEnd.setText(utcEnd.toString(utcLabelFormat)+ " (UTC)");
    }//GEN-LAST:event_jTextFieldSiteLocalEndDateActionPerformed

    private void jButtonViewChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewChartActionPerformed

        int maxPoints = 20;

        StampsAndPointsForChart sp = new StampsAndPointsForChart(calculatedBucketList);
        ReportVerificationChartFrame chartFrame = new ReportVerificationChartFrame(controller, sp, maxPoints, siteTimeZone);
        controller.addModelListener(chartFrame);
        chartFrame.pack();
        chartFrame.setLocationRelativeTo(this);
        chartFrame.setVisible(true);

    }//GEN-LAST:event_jButtonViewChartActionPerformed

    private void jTableQueryResultsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableQueryResultsMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForRVHistory popup = new PopupMenuForRVHistory(evt, jTableQueryResults);
        }
    }//GEN-LAST:event_jTableQueryResultsMousePressed

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        //Points list changed
        if (propName.equals(PropertyChangeNames.DatapointUnionForReportsReturned.getName())) {
            datapointsForReports = (List<DatapointsAndMetadataResponse>) evt.getNewValue();
            setCalcPointInfoAndPointNameMapping();

            this.allUNames = new ArrayList<>();
            for (DatapointsAndMetadataResponse resp : datapointsForReports) {
                String uName = getUName(resp.getSid(), resp.getName());
                allUNames.add(uName);
            }

            fillReportPointsTable(selectedCalculatedPointEnum);

        } else if (propName.equals(PropertyChangeNames.ReportVerificationHistoryReturned.getName())) {
            List<DatapointHistoriesResponse> datapointHistoriesResponse = (List<DatapointHistoriesResponse>) evt.getNewValue();

            if (jComboBoxCalculatedPoints.getSelectedItem() != null) {
                String name = (String) jComboBoxCalculatedPoints.getSelectedItem();
                EnumCalcPointFilter calcPointType = EnumCalcPointFilter.getEnumFromPointName(name);
                Collections.sort(datapointHistoriesResponse, new RVHistoriesResponseComparator(calcPointType));
            }

            int prec = (int) this.jSpinnerPrec.getModel().getValue();

            PreProcessMaps preMaps = new PreProcessMaps(datapointHistoriesResponse, uNameToDatapointMap);
            
            /*
            Map<String, Object> aaa = preMaps.getUNameToTimestampsAndVaulesMap().get("TotalkWh");
            if( aaa != null ){
                int count = 0;
                double sum = 0.0;
                for( String zzz : aaa.keySet() ){
                    count++;
                    Object v = aaa.get(zzz);
                    if( v != null ){
                    sum += getDouble(v);
                    }
                }
                
                String msg = String.format("Count = %d, Sum = %f", count, sum);
                System.out.println(msg);
            }
            */

            String bucketTypeName = (String) jComboBoxCalculatedPoints.getSelectedItem();
            EnumCalcPointFilter bucketListType = EnumCalcPointFilter.getEnumFromPointName(bucketTypeName);

            String resString = (String) jComboBoxResolution.getSelectedItem();
            EnumEdisonResolutions res = EnumEdisonResolutions.getResolutionFromName(resString);

            UnCalculatedBucketList uncalculatedBucketList = new UnCalculatedBucketList(
                    bucketListType,
                    res,
                    Days.daysBetween(siteLocalStart, siteLocalEnd).getDays(),
                    siteTimeZone,
                    preMaps);

            calculatedBucketList = new CalculatedBucketList(
                    associatedPointsInfo,
                    uNameToDatapointMap,
                    uncalculatedBucketList);

            fillRVQueryResultsTable(prec);

            currentSelectedBucketNumber = 0;

            bucketListStats = new BucketListStats(calculatedBucketList, associatedPointsInfo.getConfigPoints());
            fillRVGrandStatsTable(prec);

            reportTotalsCalculator = new ReportTotalsCalculator(selectedCalculatedPointEnum, bucketListStats);
            fillReportTotalsTable(prec);

            this.jButtonViewChart.setEnabled(true);

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            dispose();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonRunQuery;
    private javax.swing.JButton jButtonViewChart;
    private javax.swing.JCheckBox jCheckBoxSparse;
    private javax.swing.JComboBox<String> jComboBoxCalculatedPoints;
    private javax.swing.JComboBox<String> jComboBoxDateType;
    private javax.swing.JComboBox<String> jComboBoxReportMonth;
    private javax.swing.JComboBox<String> jComboBoxReportYear;
    private javax.swing.JComboBox<String> jComboBoxResolution;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelNumOfPoints;
    private javax.swing.JLabel jLabelSitetimeZone;
    private javax.swing.JLabel jLabelUTCEnd;
    private javax.swing.JLabel jLabelUTCStart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSpinner jSpinnerPrec;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTable jTableAssociations;
    private javax.swing.JTable jTableGrandStats;
    private javax.swing.JTable jTablePointsList;
    private javax.swing.JTable jTableQueryResults;
    private javax.swing.JTable jTableReportTotals;
    private javax.swing.JTextField jTextFieldSiteLocalEndDate;
    private javax.swing.JTextField jTextFieldSiteLocalStartDate;
    // End of variables declaration//GEN-END:variables
}
