package View.Sites.EditSite.A_History.CalclulatedPointsPusher.Ton;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.EnumAggregationType;
import Model.DataModels.Datapoints.EnumResolutions;
import Model.DataModels.Datapoints.GetDatapointHistoryResponse;
import Model.DataModels.Datapoints.HistoryQueryParams;
import Model.DataModels.Datapoints.simulator.DGArgs;
import Model.DataModels.Datapoints.simulator.EnumPattern;
import Model.DataModels.Datapoints.simulator.EnumPeriod;
import Model.DataModels.Datapoints.simulator.EnumPointType;
import Model.DataModels.Datapoints.simulator.XLSPointRow;
import Model.DataModels.Datapoints.simulator.XLSReader;
import Model.DataModels.Graph.EnumGraphNodeTypes;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.SiteDPConfig.EnumGroupTypes;
import Model.DataModels.Sites.SiteDPConfig.SiteDPConfigPoint;
import Model.DataModels.Sites.SiteDatapoints.Statistics;
import Model.DataModels.Stations.HistoryPushObject;
import Model.DataModels.Stations.HistoryPushPoint;
import Model.PropertyChangeNames;
import Model.RestClient.OEResponse;
import View.Sites.EditSite.A_History.AveragesTable.DPHistoryAveragesTableCellRenderer;
import View.Sites.EditSite.A_History.AveragesTable.DPHistoryAveragesTableModel;
import View.Sites.EditSite.A_History.DPHistoryTableCellRenderer;
import View.Sites.EditSite.A_History.DPHistoryTableModel;
import View.Sites.EditSite.A_History.DataGenerator.DGPointTableModel;
import View.Sites.EditSite.A_History.DataGenerator.DGTableCellRenderer;
import View.Sites.EditSite.A_History.DataGenerator.DGTableRow;
import View.Sites.EditSite.A_History.DataGenerator.EnumDGTableColumns;
import View.Sites.EditSite.EnumTZOffsets;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.omg.CORBA.Environment;

public class CalcPointPushFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static CalcPointPushFrame thisInstance;
    private final OptiCxAPIController controller;

    private final Site site;
    private final DateTime startDate;
    private final DateTime endDate;
    private final Map<String, List<SiteDPConfigPoint>> groups;
    private final List< DatapointMetadata> listOfPointMetaData;

    private List<XLSPointRow> xlsRows;
    private List<DGTableRow> dgTableRows;
    
    private Timer lapsedTimeTimer;
    private ActionListener lapsedTimeUpdater;
    private DateTime historyPushTimerStart;

    private CalcPointPushFrame(
            OptiCxAPIController controller,
            Site site, DateTime startDate,
            DateTime endDate,
            Map<String, List<SiteDPConfigPoint>> groups,
            List< DatapointMetadata> listOfPointMetaData) {
        initComponents();
        this.controller = controller;

        this.site = site;
        this.startDate = startDate;
        this.endDate = endDate;
        this.groups = groups;
        this.listOfPointMetaData = listOfPointMetaData;

        fillParametersPanel();
    }

    public static CalcPointPushFrame getInstance(
            OptiCxAPIController controller,
            Site site, DateTime startDate, DateTime endDate,
            Map<String, List<SiteDPConfigPoint>> groups,
            List< DatapointMetadata> listOfPointMetaData) {
        if (thisInstance == null) {
            thisInstance = new CalcPointPushFrame(controller, site, startDate, endDate, groups, listOfPointMetaData);
        }

        return thisInstance;
    }

    private void fillParametersPanel() {

        this.jLabelSiteSid.setText(site.getSid());
        this.jTextPaneStartDate.setText(startDate.toString());
        this.jTextPaneEndDate.setText(endDate.toString());

        xlsRows = new XLSReader("ConfigPoints.xlsx").readFile();
        fillInValuesFromXLS(EnumGraphNodeTypes.SITE, this.site.getSid(), listOfPointMetaData);

        fillResolutionDropdown();

        fillDatapointsTable(dgTableRows);
    }

    private void fillResolutionDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumResolutions.getNames().toArray());
        EnumResolutions res = EnumResolutions.MINUTE5;
        this.jComboBoxResolutions.setModel(comboBoxModel);
        this.jComboBoxResolutions.setSelectedIndex(res.getDropDownIndex());
        this.jComboBoxResolutions.setEnabled(true);
    }

    private void fillInValuesFromXLS(EnumGraphNodeTypes scope, String sid, List<DatapointMetadata> metaDataList) {
        dgTableRows = new ArrayList<>();
        //listOfPointMetata = new ArrayList<>();

        for (DatapointMetadata md : metaDataList) {
            dgTableRows.addAll(getDGTableRowsFromXLS(scope, sid, md));
            //listOfPointMetata.add(point);
        }

    }

    private List<DGTableRow> getDGTableRowsFromXLS(EnumGraphNodeTypes scope, String sid, DatapointMetadata point) {
        List<DGTableRow> tableRows = new ArrayList<>();
        //look up the point
        Boolean foundInXLS = false;
        for (EnumGroupTypes groupEnumValue : EnumGroupTypes.values()) {
            if (foundInXLS) {
                break;
            }
            List<SiteDPConfigPoint> configPoints = groups.get(groupEnumValue.getGroupName());

            for (SiteDPConfigPoint configPoint : configPoints) {
                if (configPoint.getStationPointName().compareTo((String) point.getMetadata().get("name")) == 0) {
                    String xlsLookup = configPoint.getStationAttributeName();
                    for (XLSPointRow xlsRow : xlsRows) {
                        if (xlsRow.getStAttrName().compareTo(xlsLookup) == 0) {
                            foundInXLS = true;
                            DGTableRow row = new DGTableRow(scope, sid, point);
                            row.setGraphAttrName(xlsRow.getGraphAttrName());
                            row.setStAttrName(xlsLookup);
                            row.setMinValue(xlsRow.getMin());
                            row.setMaxValue(xlsRow.getMax());
                            row.setPattern(((xlsRow.getPattern()) == null ? EnumPattern.notSpecified : xlsRow.getPattern()));
                            row.setPeriod(((xlsRow.getPeriod()) == null ? EnumPeriod.notSpecified : xlsRow.getPeriod()));
                            row.setOffset(xlsRow.getOffset());
                            tableRows.add(row);
                            break;
                        }
                    }
                }
            }
        }
        if (!foundInXLS) {
            tableRows.add(new DGTableRow(scope, sid, point));
        }

        return tableRows;
    }

    private void fillDatapointsTable(List<DGTableRow> rows) {

        List<String> patternNames = new ArrayList<>();
        for (EnumPattern enumPattern : EnumPattern.values()) {
            patternNames.add(enumPattern.getName());
        }

        List<String> periodNames = new ArrayList<>();
        for (EnumPeriod periodName : EnumPeriod.values()) {
            periodNames.add(periodName.getName());
        }

        this.jTableDependentPoints.setRowSorter(null);
        DGPointTableModel tm = new DGPointTableModel(rows);
        this.jTableDependentPoints.setModel(tm);

        int patternColumnNumber = EnumDGTableColumns.Pattern.getColumnNumber();
        TableColumn patternColumn = jTableDependentPoints.getColumnModel().getColumn(patternColumnNumber);
        JComboBox patternComboBox = new JComboBox(patternNames.toArray());
        patternColumn.setCellEditor(new DefaultCellEditor(patternComboBox));

        int periodColumnNumber = EnumDGTableColumns.Period.getColumnNumber();
        TableColumn periodColumn = jTableDependentPoints.getColumnModel().getColumn(periodColumnNumber);
        JComboBox periodComboBox = new JComboBox(periodNames.toArray());
        periodColumn.setCellEditor(new DefaultCellEditor(periodComboBox));

        this.jTableDependentPoints.setDefaultRenderer(Object.class, new DGTableCellRenderer());
        this.jTableDependentPoints.setAutoCreateRowSorter(true);
        fixColumnWidths(jTableDependentPoints);
    }

    private void fixColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        //int scopeLen = Math.max(EnumDGTableColumns.Scope.getFriendlyName().length(), this.scope.getEdisonName().length());
        //int sidLen = this.sid.length();
        for (int i = 0; i < t.getColumnCount(); i++) {

            column = t.getColumnModel().getColumn(i);

            EnumDGTableColumns enumCol = EnumDGTableColumns.getColumnFromColumnNumber(i);
            switch (enumCol) {
                case Scope:
                    column.setPreferredWidth(100);
                    break;
                case Sid:
                    column.setPreferredWidth(100);
                    break;
                case PointName:
                    column.setPreferredWidth(150);
                    break;
                case MinValue:
                case MaxValue:
                    column.setPreferredWidth(75);
                    break;
                case GraphAttrName:
                    column.setPreferredWidth(200);
                default:
                    column.setPreferredWidth(100);
            }
        }
    }

    private void fillQueryResultsTable(GetDatapointHistoryResponse historyQueryResponse, int prec, DateTimeZone tz) {
        //this.jTableQueryResults.setModel(new DPHistoryTableModel(tz, historyQueryResponse));
        this.jTableQueryResults.setDefaultRenderer(Object.class, new DPHistoryTableCellRenderer(prec, tz));
        this.jTableQueryResults.setAutoCreateRowSorter(true);
        fixHistoryQueryResultsTableColumnWidths(jTableQueryResults);
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
    
    public void fillDatapointsAveragesTable(Statistics stat, int prec) {
        this.jTableStats.setModel(new DPHistoryAveragesTableModel(stat));
        this.jTableStats.setDefaultRenderer(Object.class, new DPHistoryAveragesTableCellRenderer(prec));
        this.jTableStats.setAutoCreateRowSorter(true);
        fixHistoryQueryResultsTableColumnWidths(jTableStats);
    }

    @Override
    public void dispose() {
        controller.removePropChangeListener(this);
        thisInstance = null;
        super.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableQueryResults = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDependentPoints = new javax.swing.JTable();
        jPanelParameters = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelCaluclatedPointName = new javax.swing.JLabel();
        jLabelSiteSid = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPaneStartDate = new javax.swing.JTextPane();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPaneEndDate = new javax.swing.JTextPane();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxResolutions = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jSpinnerPrecision = new javax.swing.JSpinner();
        jButtonRunQuery = new javax.swing.JButton();
        jCheckBoxSingleValuePush = new javax.swing.JCheckBox();
        jPanelMakeHistory = new javax.swing.JPanel();
        jButtonMakeHistory = new javax.swing.JButton();
        jProgressBarStatus = new javax.swing.JProgressBar();
        jButtonClose = new javax.swing.JButton();
        jLabelLapsedTime = new javax.swing.JLabel();
        jPanelTotals = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableStats = new javax.swing.JTable();

        jScrollPane1.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Query Results"));

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
        jScrollPane3.setViewportView(jTableQueryResults);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Dependent Points List"));

        jTableDependentPoints.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableDependentPoints.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableDependentPoints.setShowGrid(true);
        jScrollPane2.setViewportView(jTableDependentPoints);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setTopComponent(jPanel1);

        jPanelParameters.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "History Push Parameters"));

        jLabel1.setText("Calculated Point Name:");

        jLabelCaluclatedPointName.setText("*point name*");

        jLabelSiteSid.setText("*siteSid*");

        jLabel4.setText("Sid:");

        jLabel5.setText("Start Date:");

        jScrollPane4.setViewportView(jTextPaneStartDate);

        jLabel6.setText("End Date:");

        jScrollPane5.setViewportView(jTextPaneEndDate);

        jLabel7.setText("Resolution:");

        jComboBoxResolutions.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Precision:");

        jButtonRunQuery.setText("Run Query");
        jButtonRunQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunQueryActionPerformed(evt);
            }
        });

        jCheckBoxSingleValuePush.setText("Push Single Value");

        javax.swing.GroupLayout jPanelParametersLayout = new javax.swing.GroupLayout(jPanelParameters);
        jPanelParameters.setLayout(jPanelParametersLayout);
        jPanelParametersLayout.setHorizontalGroup(
            jPanelParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelParametersLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelCaluclatedPointName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSiteSid, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelParametersLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxResolutions, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerPrecision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRunQuery))
                    .addGroup(jPanelParametersLayout.createSequentialGroup()
                        .addGroup(jPanelParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelParametersLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelParametersLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBoxSingleValuePush)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelParametersLayout.setVerticalGroup(
            jPanelParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelCaluclatedPointName)
                    .addComponent(jLabelSiteSid)
                    .addComponent(jLabel4))
                .addGroup(jPanelParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelParametersLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanelParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckBoxSingleValuePush))
                        .addGap(6, 6, 6)
                        .addGroup(jPanelParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBoxResolutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jSpinnerPrecision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelParametersLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRunQuery)
                        .addContainerGap())))
        );

        jPanelMakeHistory.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Push History"));

        jButtonMakeHistory.setText("Make History");
        jButtonMakeHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMakeHistoryActionPerformed(evt);
            }
        });

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jLabelLapsedTime.setText("00:00:00");

        javax.swing.GroupLayout jPanelMakeHistoryLayout = new javax.swing.GroupLayout(jPanelMakeHistory);
        jPanelMakeHistory.setLayout(jPanelMakeHistoryLayout);
        jPanelMakeHistoryLayout.setHorizontalGroup(
            jPanelMakeHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMakeHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonMakeHistory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBarStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLapsedTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClose)
                .addContainerGap())
        );
        jPanelMakeHistoryLayout.setVerticalGroup(
            jPanelMakeHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMakeHistoryLayout.createSequentialGroup()
                .addGroup(jPanelMakeHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jLabelLapsedTime))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelMakeHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMakeHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonMakeHistory)
                    .addComponent(jProgressBarStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelTotals.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Totals"));

        jTableStats.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableStats.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableStats.setShowGrid(true);
        jScrollPane6.setViewportView(jTableStats);

        javax.swing.GroupLayout jPanelTotalsLayout = new javax.swing.GroupLayout(jPanelTotals);
        jPanelTotals.setLayout(jPanelTotalsLayout);
        jPanelTotalsLayout.setHorizontalGroup(
            jPanelTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTotalsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        jPanelTotalsLayout.setVerticalGroup(
            jPanelTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTotalsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelParameters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelMakeHistory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSplitPane1)
                    .addComponent(jPanelTotals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelParameters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelTotals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMakeHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonRunQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunQueryActionPerformed
        jTableQueryResults.setModel(new DefaultTableModel());
        jTableQueryResults
                .setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

        List<String> listOfPoints = new ArrayList<>();

        for (DatapointMetadata metaData : listOfPointMetaData) {
            listOfPoints.add((String) metaData.getMetadata().get("name"));
        }

        if (listOfPoints.size() > 0) {
            String resString = (String) jComboBoxResolutions.getSelectedItem();
            EnumResolutions res = EnumResolutions.getResolutionFromName(resString);

            /*
             EnumAggregationType aggType = EnumAggregationType.NORMAL;
             if (jCheckBoxSum.isSelected()) {
             aggType = EnumAggregationType.SUM;
             } else if (jCheckBoxAvg.isSelected()) {
             aggType = EnumAggregationType.AVG;
             } else if (jCheckBoxCount.isSelected()) {
             aggType = EnumAggregationType.COUNT;
             }
             */
            String startDate = this.jTextPaneStartDate.getText();
            String endDate = this.jTextPaneEndDate.getText();

            DateTimeFormatter utcFmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            DateTime start = DateTime.parse(startDate, utcFmt);
            DateTime end = DateTime.parse(endDate, utcFmt);

            HistoryQueryParams params = new HistoryQueryParams(start, end, res, listOfPoints, EnumAggregationType.NORMAL);

            String sidForURL = site.getSid() + ".st:1";

            //TODO: controller.getHistory(sidForURL, params);

            //this.jButtonHistoryChart.setEnabled(true);
        } else {
            //this.jButtonHistoryChart.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonRunQueryActionPerformed

    private void jButtonMakeHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMakeHistoryActionPerformed
        if (this.listOfPointMetaData.size() > 0) {

            boolean singleValuePush = jCheckBoxSingleValuePush.isSelected();

            if (singleValuePush) {

                HistoryPushObject hpo = new HistoryPushObject();
                hpo.setSendingStationName( site.getSid() + ".st:1");

                String startDateString = jTextPaneStartDate.getText();
                DateTime startDate = DateTime.parse(startDateString).withZone(DateTimeZone.UTC);
                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                String timestampString = startDate.toString(fmt);

                hpo.setTimestamp(timestampString);
                hpo.setLastSuccessTimestamp(timestampString);

                hpo.setStationName(site.getStationID());
                hpo.setStationId(site.getStationID());

                List<HistoryPushPoint> listOfPoints = new ArrayList<>();

                DGPointTableModel model = (DGPointTableModel) this.jTableDependentPoints.getModel();
                for (  DGTableRow tableRow : dgTableRows) {
                    
                    DatapointMetadata md = tableRow.getMetadata();
                    
                    
                    String name = (String)md.getMetadata().get("name");

                    HistoryPushPoint hpp = new HistoryPushPoint();
                    hpp.setName(name);
                    hpp.setPath("zippy/" + name);

                    EnumPointType pt = EnumPointType.getTypeFromName(md.getPointType() );
                    hpp.setPointType(pt.getEdisonName());

                    List<String> timestamps = new ArrayList<>();
                    timestamps.add(timestampString);
                    hpp.setTimestamps(timestamps);

                    List<Object> vals = new ArrayList<>();
                    vals.add(tableRow.getMinValue());
                    hpp.setValues(vals);

                    listOfPoints.add(hpp);

                }
                hpo.setPoints(listOfPoints);
                controller.postDatapointHistory(hpo);

            } else {

                String startDateString = jTextPaneStartDate.getText();
                String endDateString = jTextPaneEndDate.getText();

                DateTime start = DateTime.parse(startDateString).withZone(DateTimeZone.UTC);
                DateTime end = DateTime.parse(endDateString).withZone(DateTimeZone.UTC);
                
                String resString = (String) this.jComboBoxResolutions.getSelectedItem();
                EnumResolutions res = EnumResolutions.getResolutionFromName(resString);
                
                DGArgs args = new DGArgs(start, end, site.getStationID(), site.getStationID(), site.getSid() + ".st:1", res);
                
                args.setRes(res);

                jProgressBarStatus.setMaximum(args.getNumberOfHours());
                jProgressBarStatus.setValue(0);
                jProgressBarStatus.setStringPainted(true);

                Map<String, DGTableRow> tableRowsMap = new HashMap<>();
                DGPointTableModel model = (DGPointTableModel) this.jTableDependentPoints.getModel();
                
                for ( DGTableRow row : model.getRows()) {
                    String name = row.getPointName();
                    tableRowsMap.put(name, row);
                }
                
                this.jButtonMakeHistory.setEnabled(false);
                historyPushTimerStart = DateTime.now();
                lapsedTimeTimer = new Timer(1000, lapsedTimeUpdater);
                lapsedTimeTimer.start();

                controller.pushHistoryForPeriod(EnumGraphNodeTypes.station, args, tableRowsMap);
            }
        }

    }//GEN-LAST:event_jButtonMakeHistoryActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonMakeHistory;
    private javax.swing.JButton jButtonRunQuery;
    private javax.swing.JCheckBox jCheckBoxSingleValuePush;
    private javax.swing.JComboBox jComboBoxResolutions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelCaluclatedPointName;
    private javax.swing.JLabel jLabelLapsedTime;
    private javax.swing.JLabel jLabelSiteSid;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelMakeHistory;
    private javax.swing.JPanel jPanelParameters;
    private javax.swing.JPanel jPanelTotals;
    private javax.swing.JProgressBar jProgressBarStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSpinner jSpinnerPrecision;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableDependentPoints;
    private javax.swing.JTable jTableQueryResults;
    private javax.swing.JTable jTableStats;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPaneEndDate;
    private javax.swing.JTextPane jTextPaneStartDate;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String propName = evt.getPropertyName();

        //SITE DETAILS
        if (propName.equals(PropertyChangeNames.DatapointsHistoryReturned.getName())) {
            GetDatapointHistoryResponse historyQueryResponse = (GetDatapointHistoryResponse) evt.getNewValue();
            //historyTimestamps = resp.getTimestamps();
            //historyPoints = resp.getDatapoints();
            int prec = (int) this.jSpinnerPrecision.getModel().getValue();


            //String tzName = (String) jComboBoxTZOffsets.getSelectedItem();
            fillQueryResultsTable(historyQueryResponse, prec, DateTimeZone.UTC);

            //String sid = this.jTextFieldSidInURL.getText();
            //Statistics stat = new Statistics(historyQueryResponse);
            //fillDatapointsAveragesTable(stat, prec);
            
            
        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            dispose();
        }
    }
}
