package View.Sites.EditSite.A_History.Tesla.TeslaHistory;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointHistoriesQueryParams;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.Datapoints.EnumAggregationType;
import Model.DataModels.TeslaModels.ComboHistories.ComboHistories;
import Model.DataModels.TeslaModels.EnumComboResolutions;
import Model.DataModels.TeslaModels.EnumTeslaBaseURLs;
import Model.DataModels.TeslaModels.EnumTeslaUsers;
import Model.DataModels.TeslaModels.MappingTableRow;
import Model.DataModels.TeslaModels.TeslaDPServiceDatapoint;
import Model.DataModels.TeslaModels.TeslaHistoryRequest;
import Model.DataModels.TeslaModels.TeslaHistoryStats;
import Model.DataModels.TeslaModels.TeslaStampsAndPoints;
import Model.DataModels.TeslaModels.TeslaStationInfo;
import Model.PropertyChangeNames;
import View.Sites.EditSite.A_History.Tesla.PushToTesla.MappingTable.DataPointsTableCellRenderer;
import View.Sites.EditSite.A_History.Tesla.PushToTesla.MappingTable.DataPointsTableModel;
import View.Sites.EditSite.A_History.Tesla.PushToTesla.MappingTable.EnumDatpointsTableColumns;
import View.Sites.EditSite.A_History.Tesla.PushToTesla.MappingTable.PopupMenuForDataPointsTable;
import View.Sites.EditSite.A_History.Tesla.TeslaHistory.HistoryTable.HistoryTableCellRenderer;
import View.Sites.EditSite.A_History.Tesla.TeslaHistory.HistoryTable.HistoryTableModel;
import View.Sites.EditSite.A_History.Tesla.TeslaHistory.HistoryTable.PopupMenuForHistoryTable;
import View.Sites.EditSite.A_History.Tesla.TeslaHistory.StatsTable.PopupMenuForStatsTable;
import View.Sites.EditSite.A_History.Tesla.TeslaHistory.StatsTable.TeslaStatsTableCellRenderer;
import View.Sites.EditSite.A_History.Tesla.TeslaHistory.StatsTable.TeslaStatsTableModel;
import View.Sites.EditSite.A_History.Tesla.TeslaHistory.TeslaChartFrame.TeslaChartFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class TeslaHistoryFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static TeslaHistoryFrame thisInstance;
    private final OptiCxAPIController controller;

    private EnumTeslaBaseURLs selectedBaseURL;
    private EnumTeslaUsers selectedUser;

    private DateTime startTime;
    private DateTime endTime;

    private final List<DatapointsAndMetadataResponse> edisonPoints;
    private String selectedSid;

    private List<TeslaDPServiceDatapoint> listOfStationDatapoints;

    private ComboHistories historyResults;
    private TeslaHistoryStats historyStats;
    private String filter = "";

    private DateTimeFormatter zzFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");

    public static TeslaHistoryFrame getInstance(
            final OptiCxAPIController controller, DateTime startTime, DateTime endTime, List<DatapointsAndMetadataResponse> edisonPoints) {

        if (thisInstance == null) {
            thisInstance = new TeslaHistoryFrame(controller, startTime, endTime, edisonPoints);
        }
        return thisInstance;

    }

    private TeslaHistoryFrame(OptiCxAPIController controller, DateTime startTime, DateTime endTime, List<DatapointsAndMetadataResponse> edisonPoints) {
        initComponents();

        this.controller = controller;

        this.startTime = startTime;
        this.endTime = endTime;
        this.edisonPoints = edisonPoints;

        fillQueryParametersPanel();
        fillEdisonSidsDropdown();

        selectedBaseURL = EnumTeslaBaseURLs.Prod;
        selectedUser = EnumTeslaUsers.ProdUser;
        fillTeslasHostsDropdown();
        fillUsersDropdown();

        createAllTeslaCheckBoxListener();
        createIgnoreGarbageCheckBoxListener();

    }

    @Override
    public void dispose() {
        controller.removePropChangeListener(this);
        thisInstance = null;
        super.dispose();
    }

    public void fillQueryParametersPanel() {
        this.filter = "";
        this.jTextFieldFilter.setText(filter);

        fillResolutionDropdown();

        //String timeZone = "America/Los_Angeles";
        String timeZone = "UTC";
        jTextFieldTimeZone.setText(timeZone);

        endTime = DateTime.now().withZone(DateTimeZone.UTC);
        endTime = endTime.minusMillis(endTime.getMillisOfDay());
        startTime = endTime.plusMonths(-1);
        this.jTextFieldStartDate.setText(startTime.toString(zzFormat));
        this.jTextFieldEndDate.setText(endTime.toString(zzFormat));

        SpinnerNumberModel spinModel = new SpinnerNumberModel(3, 0, 6, 1);
        this.jSpinnerPrec.setModel(spinModel);
        jSpinnerPrec.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                if (historyResults != null && historyResults.getTimestamps().size() > 0) {
                    int prec = (int) jSpinnerPrec.getModel().getValue();
                    fillHistoryTable(prec);
                    fillTeslaStatsTable(prec);
                }
            }
        });

    }

    private void fillResolutionDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumComboResolutions.getNames().toArray());
        EnumComboResolutions res = EnumComboResolutions.FIVEMINUTE;
        this.jComboBoxResolution.setModel(comboBoxModel);
        this.jComboBoxResolution.setSelectedIndex(res.getDropDownIndex());
        this.jComboBoxResolution.setEnabled(true);
    }

    // =============
    public void fillTeslasHostsDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        this.jComboBoxTeslaHosts.setModel(comboBoxModel);

        for (String url : EnumTeslaBaseURLs.getURLs()) {
            jComboBoxTeslaHosts.addItem(url);
        }
        this.jComboBoxTeslaHosts.setSelectedItem(selectedBaseURL.getURL());

        this.jComboBoxTeslaHosts.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                selectedBaseURL = EnumTeslaBaseURLs.getHostFromName(name);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //clearStationsTable();
                        //clearDatapointsTable();
                    }
                });

            }
        });
    }

    public void fillUsersDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        this.jComboTeslaUsers.setModel(comboBoxModel);
        for (String user : EnumTeslaUsers.getUsernames()) {
            //EnumTeslaUsers.getUsernames().toArray()
            jComboTeslaUsers.addItem(user);
        }
        this.jComboTeslaUsers.setSelectedItem(selectedUser.name());

        this.jComboTeslaUsers.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                selectedUser = EnumTeslaUsers.getUserFromName(name);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //clearStationsTable();
                        //clearDatapointsTable();
                    }
                });

            }
        });

    }

    private void initTeslaModel(EnumTeslaBaseURLs baseURL, EnumTeslaUsers selectedUser) {
        controller.teslaLogin(baseURL, selectedUser);
    }

    public void clearTeslaSitesDropdown() {
        for (ActionListener oldListener : jComboBoxTeslaStations.getActionListeners()) {
            this.jComboBoxTeslaStations.removeActionListener(oldListener);
        }
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        this.jComboBoxTeslaStations.setModel(comboBoxModel);
    }

    public void fillTeslasStationsDropdown(final List<TeslaStationInfo> stations) {

        for (ActionListener oldListener : jComboBoxTeslaStations.getActionListeners()) {
            this.jComboBoxTeslaStations.removeActionListener(oldListener);
        }
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        this.jComboBoxTeslaStations.setModel(comboBoxModel);
        final Map< String, String> stationNameToStationIdMap = new HashMap<>();

        for (TeslaStationInfo station : stations) {
            jComboBoxTeslaStations.addItem(station.getName());
            stationNameToStationIdMap.put(station.getName(), station.getStationId());
        }

        this.jComboBoxTeslaStations.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent event) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        clearPointsTable();
                        clearHistoryTable();
                        JComboBox<String> combo = (JComboBox<String>) event.getSource();
                        final String name = (String) combo.getSelectedItem();
                        controller.getTeslaStationDatapoints(stationNameToStationIdMap.get(name));
                    }
                });
            }
        });

        jComboBoxTeslaStations.setSelectedIndex(0);

    }

    public void createAllTeslaCheckBoxListener() {

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                //AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                //showAllTesla = abstractButton.getModel().isSelected();
                fillPointsTable(filter);
            }
        };
        this.jCheckBoxShowAllTesla.addActionListener(actionListener);
    }

    public void createIgnoreGarbageCheckBoxListener() {

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                //AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                //ignoreGarbage = abstractButton.getModel().isSelected();
                fillPointsTable(filter);
            }
        };
        this.jCheckBoxIngoreGarbage.addActionListener(actionListener);
    }

    public void fillEdisonSidsDropdown() {

        List<String> edisonSids = new ArrayList<>();
        for (DatapointsAndMetadataResponse edisonPoint : edisonPoints) {
            if (!edisonSids.contains(edisonPoint.getSid())) {
                edisonSids.add(edisonPoint.getSid());
            }
        }

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        this.jComboBoxEdisonSids.setModel(comboBoxModel);

        jComboBoxEdisonSids.addItem("All");
        for (String sid : edisonSids) {
            jComboBoxEdisonSids.addItem(sid);
        }

        this.jComboBoxEdisonSids.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                selectedSid = (String) combo.getSelectedItem();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        fillPointsTable(filter);
                    }
                });
            }
        });

        jComboBoxEdisonSids.setSelectedIndex(0);

    }

    private void clearPointsTable() {

        this.jTableDataPoints.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
        this.jTableDataPoints.setModel(new DefaultTableModel());

    }

    private void fillPointsTable(String filter) {

        this.jTableDataPoints.setDefaultRenderer(Object.class, new DataPointsTableCellRenderer());
        this.jTableDataPoints.setModel(new DataPointsTableModel(
                edisonPoints,
                selectedSid,
                listOfStationDatapoints,
                this.jCheckBoxShowAllTesla.isSelected(),
                this.jCheckBoxIngoreGarbage.isSelected(),
                this.jCheckBoxUseRegEx.isSelected(),
                filter)
        );
        this.jTableDataPoints.setAutoCreateRowSorter(true);
        fixPointsTableColumnWidths(jTableDataPoints);
        setPointCounts();

    }

    private List<String> getSpecialPointNames() {

        return Arrays.asList(new String[]{
            "CDWSTSPNotOptimized",
            "CHWDPSPNotOptimized",
            "CH1CHWSTSPNotOptimized",
            "CH2CHWSTSPNotOptimized",
            "CH3CHWSTSPNotOptimized",
            "CT1SPDNotOptimized",
            "CT2SPDNotOptimized",
            "PCHWP1SPDNotOptimized",
            "PCHWP2SPDNotOptimized",
            "PCHWP3SPDNotOptimized",
            "SCHWP1SPDNotOptimized",
            "SCHWP2SPDNotOptimized",
            "SCHWP3SPDNotOptimized",
            "CDWP1SPDNotOptimized",
            "CDWP2SPDNotOptimized",
            "CDWP3SPDNotOptimized"});

    }

    private void fixPointsTableColumnWidths(JTable t) {
        for (int i = 0; i < t.getColumnCount(); i++) {
            EnumDatpointsTableColumns colEnum = EnumDatpointsTableColumns.getColumnFromColumnNumber(i);
            TableColumn column = t.getColumnModel().getColumn(i);
            column.setPreferredWidth(colEnum.getWidth());
        }
    }

    private void setPointCounts() {
        String msg = String.format("num points: %d (%d selected)", jTableDataPoints.getRowCount(), jTableDataPoints.getSelectedRowCount());
        jLabelPointCounts.setText(msg);
    }

    public void clearHistoryTable() {
        this.jTableHistory.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
        this.jTableHistory.setModel(new DefaultTableModel());
        this.jTableHistory.setAutoCreateRowSorter(true);
    }

    private void fillHistoryTable(int prec) {
        this.jTableHistory.setDefaultRenderer(Object.class, new HistoryTableCellRenderer(prec));
        this.jTableHistory.setModel(new HistoryTableModel(historyResults));
        this.jTableHistory.setAutoCreateRowSorter(true);
        fixHistoryTableColumnWidths(jTableHistory);
    }

    public void fixHistoryTableColumnWidths(JTable t) {
        for (int i = 0; i < t.getColumnCount(); i++) {
            TableColumn column = t.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(250);
            } else {
                column.setPreferredWidth(150);
            }
        }
    }

    private void fillTeslaStatsTable(int prec) {
        this.jTableStatistics.setDefaultRenderer(Object.class, new TeslaStatsTableCellRenderer(prec));
        this.jTableStatistics.setModel(new TeslaStatsTableModel(historyStats));
        this.jTableStatistics.setAutoCreateRowSorter(true);
        fixHistoryTableColumnWidths(jTableStatistics);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldStartDate = new javax.swing.JTextField();
        jTextFieldEndDate = new javax.swing.JTextField();
        jComboBoxResolution = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jButtonQuery = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldTimeZone = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableHistory = new javax.swing.JTable();
        jSpinnerPrec = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableStatistics = new javax.swing.JTable();
        jButtonChart = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jComboBoxTeslaHosts = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxTeslaStations = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboTeslaUsers = new javax.swing.JComboBox<>();
        jButtonLogin = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDataPoints = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldFilter = new javax.swing.JTextField();
        jCheckBoxUseRegEx = new javax.swing.JCheckBox();
        jButtonClearFilter = new javax.swing.JButton();
        jComboBoxEdisonSids = new javax.swing.JComboBox<>();
        jCheckBoxIngoreGarbage = new javax.swing.JCheckBox();
        jCheckBoxShowAllTesla = new javax.swing.JCheckBox();
        jButtonSpecialSelect = new javax.swing.JButton();
        jLabelPointCounts = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tesla Historical Data");
        setAlwaysOnTop(true);

        jSplitPane1.setDividerLocation(800);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "History"));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Query Parameters"));

        jLabel2.setText("Start:");

        jLabel3.setText("End:");

        jTextFieldStartDate.setText("jTextField1");

        jTextFieldEndDate.setText("jTextField2");

        jComboBoxResolution.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Reso:");

        jButtonQuery.setText("Query");
        jButtonQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQueryActionPerformed(evt);
            }
        });

        jLabel6.setText("TZ:");

        jTextFieldTimeZone.setText("jTextField1");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jTextFieldEndDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldTimeZone, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonQuery)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButtonQuery)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldTimeZone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Query Results"));

        jTableHistory.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableHistory.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableHistory.setShowGrid(true);
        jTableHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableHistoryMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTableHistory);

        jLabel1.setText("Precision:");

        jTableStatistics.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableStatistics.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableStatistics.setShowGrid(true);
        jTableStatistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableStatisticsMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTableStatistics);

        jButtonChart.setText("Chart");
        jButtonChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jButtonChart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerPrec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jSpinnerPrec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonChart)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Tesla Host & Site"));

        jComboBoxTeslaHosts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Host:");

        jLabel8.setText("Station");

        jComboBoxTeslaStations.setEnabled(false);

        jLabel9.setText("User:");

        jComboTeslaUsers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButtonLogin.setText("Login");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTeslaHosts, 0, 262, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboTeslaUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTeslaStations, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jComboBoxTeslaHosts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel7)
                .addComponent(jLabel8)
                .addComponent(jComboBoxTeslaStations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9)
                .addComponent(jComboTeslaUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButtonLogin))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datapoints"));

        jTableDataPoints.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableDataPoints.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableDataPoints.setShowGrid(true);
        jTableDataPoints.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableDataPointsMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDataPoints);

        jLabel5.setText("Filter:");

        jTextFieldFilter.setText("jTextField3");
        jTextFieldFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFilterActionPerformed(evt);
            }
        });

        jCheckBoxUseRegEx.setText("RegEx");

        jButtonClearFilter.setText("Clear Filter");
        jButtonClearFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearFilterActionPerformed(evt);
            }
        });

        jComboBoxEdisonSids.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCheckBoxIngoreGarbage.setSelected(true);
        jCheckBoxIngoreGarbage.setText("Ignore COV, etc.");

        jCheckBoxShowAllTesla.setText("Show All Tesla");

        jButtonSpecialSelect.setText("Special Select");
        jButtonSpecialSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSpecialSelectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFilter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClearFilter))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jComboBoxEdisonSids, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSpecialSelect))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jCheckBoxUseRegEx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxShowAllTesla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxIngoreGarbage)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClearFilter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxUseRegEx)
                    .addComponent(jCheckBoxShowAllTesla)
                    .addComponent(jCheckBoxIngoreGarbage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxEdisonSids, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSpecialSelect))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelPointCounts.setText("*point counts*");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelPointCounts)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPointCounts))
        );

        jSplitPane1.setRightComponent(jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableDataPointsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDataPointsMousePressed

        setPointCounts();

        if (evt.isPopupTrigger()) {
            JTable jTablePushPoints;
            PopupMenuForDataPointsTable popup = new PopupMenuForDataPointsTable(evt, jTableDataPoints);
        }
    }//GEN-LAST:event_jTableDataPointsMousePressed

    private void jButtonQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQueryActionPerformed

        if (jTableDataPoints.getSelectedRowCount() > 0) {

            clearHistoryTable();

            DateTime startAt = DateTime.parse(jTextFieldStartDate.getText(), zzFormat).withZone(DateTimeZone.UTC);
            DateTime endAt = DateTime.parse(jTextFieldEndDate.getText(), zzFormat).withZone(DateTimeZone.UTC);

            List<String> ids = new ArrayList<>();

            //****************
            List<MappingTableRow> listOfPoints = new ArrayList<>(); //jListHistoryDatapoints.getSelectedValuesList();
            DataPointsTableModel tableModel = (DataPointsTableModel) (jTableDataPoints.getModel());
            int[] selectedRowNumbers = jTableDataPoints.getSelectedRows();
            for (int selectedRowNumber : selectedRowNumbers) {
                int modelRowNumber = jTableDataPoints.convertRowIndexToModel(selectedRowNumber);
                MappingTableRow dataRow = tableModel.getRow(modelRowNumber);
                listOfPoints.add(dataRow);
            }

            Map<String, List<String>> pointsToQuery = new HashMap<>();
            for (MappingTableRow pointInfo : listOfPoints) {
                if (!pointsToQuery.containsKey(pointInfo.getEdsionSid())) {
                    pointsToQuery.put(pointInfo.getEdsionSid(), new ArrayList<String>());
                }
                pointsToQuery.get(pointInfo.getEdsionSid()).add(pointInfo.getEdsionShortName());
            }

            if (pointsToQuery.size() > 0) {
                String resString = (String) jComboBoxResolution.getSelectedItem();
                EnumComboResolutions res = EnumComboResolutions.getResolutionFromName(resString);

                EnumAggregationType aggType = EnumAggregationType.NORMAL;
                //if (jCheckBoxSum.isSelected()) {
                //    aggType = EnumAggregationType.SUM;
                //} else if (jCheckBoxAvg.isSelected()) {
                //   aggType = EnumAggregationType.AVG;
                //} else if (jCheckBoxCount.isSelected()) {
                //   aggType = EnumAggregationType.COUNT;
                //}

                List<DatapointHistoriesQueryParams> listOfParams = new ArrayList<>();
                for (String sid : pointsToQuery.keySet()) {
                    List<String> listOfPointNames = pointsToQuery.get(sid);
                    DatapointHistoriesQueryParams params = new DatapointHistoriesQueryParams(
                            sid, startAt, endAt, res.getEdisonResolution(), false, listOfPointNames, aggType); //true = sparse data flag
                    listOfParams.add(params);
                }

                //******************
                DataPointsTableModel model = (DataPointsTableModel) jTableDataPoints.getModel();
                int[] selectedRows = jTableDataPoints.getSelectedRows();
                for (int selectedRowNumber : selectedRows) {
                    int modelIndex = jTableDataPoints.convertRowIndexToModel(selectedRowNumber);
                    MappingTableRow dpServicePoint = model.getRow(modelIndex);
                    ids.add(dpServicePoint.getTeslaID());
                }

                String timeZone = jTextFieldTimeZone.getText();

                TeslaHistoryRequest historyRequest = new TeslaHistoryRequest(ids, startAt, endAt, res.getTeslaResolution().getName(), timeZone);

                controller.getTeslaAndEdisonHistory(listOfParams, historyRequest);

            }
        }
    }//GEN-LAST:event_jButtonQueryActionPerformed


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextFieldFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFilterActionPerformed
        this.filter = jTextFieldFilter.getText();
        fillPointsTable(this.filter);
    }//GEN-LAST:event_jTextFieldFilterActionPerformed

    private void jButtonClearFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearFilterActionPerformed
        this.filter = "";
        this.jTextFieldFilter.setText(this.filter);
        fillPointsTable(this.filter);
    }//GEN-LAST:event_jButtonClearFilterActionPerformed

    private void jButtonChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChartActionPerformed

        if (historyResults != null) {

            DateTimeZone siteTimeZone = DateTimeZone.forID(this.jTextFieldTimeZone.getText());
            int maxPoints = 20;

            TeslaStampsAndPoints sp = new TeslaStampsAndPoints(historyResults);
            TeslaChartFrame chartFrame = new TeslaChartFrame(controller, sp, maxPoints, siteTimeZone);
            controller.addModelListener(chartFrame);
            chartFrame.pack();
            chartFrame.setLocationRelativeTo(this);
            chartFrame.setVisible(true);

        }
    }//GEN-LAST:event_jButtonChartActionPerformed

    private void jTableHistoryMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableHistoryMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForHistoryTable popup = new PopupMenuForHistoryTable(evt, jTableHistory);
        }
    }//GEN-LAST:event_jTableHistoryMousePressed

    private void jTableStatisticsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableStatisticsMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForStatsTable popup = new PopupMenuForStatsTable(evt, jTableStatistics);
        }
    }//GEN-LAST:event_jTableStatisticsMousePressed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        clearTeslaSitesDropdown();
        jComboBoxTeslaStations.setEnabled(false);
        clearHistoryTable();
        clearPointsTable();
        initTeslaModel(selectedBaseURL, selectedUser);
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonSpecialSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSpecialSelectActionPerformed

        List<String> specialPoints = getSpecialPointNames();

        DataPointsTableModel tableModel = (DataPointsTableModel) (jTableDataPoints.getModel());

        for (int row = 0; row < jTableDataPoints.getRowCount(); row++) {
            int modelRowNumber = jTableDataPoints.convertRowIndexToModel(row);
            MappingTableRow dataRow = tableModel.getRow(modelRowNumber);
            if (specialPoints.contains(dataRow.getTeslaName())) {
                jTableDataPoints.addRowSelectionInterval(row, row);
            }
        }
    }//GEN-LAST:event_jButtonSpecialSelectActionPerformed

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.TeslaLoginResponseReturned.getName())) {
            controller.getTeslaStations();

        } else if (propName.equals(PropertyChangeNames.TeslaStationsListReturned.getName())) {
            jComboBoxTeslaStations.setEnabled(true);
            List<TeslaStationInfo> stations = (List<TeslaStationInfo>) evt.getNewValue();
            fillTeslasStationsDropdown(stations);

        } else if (propName.equals(PropertyChangeNames.TeslaStationDatapointsReturned.getName())) {
            this.listOfStationDatapoints = (List<TeslaDPServiceDatapoint>) evt.getNewValue();
            fillPointsTable(filter);

        } else if (propName.equals(PropertyChangeNames.TeslaEdisonHistoryReturned.getName())) {

            historyResults = (ComboHistories) evt.getNewValue();
            historyStats = new TeslaHistoryStats(historyResults);
            int prec = (int) jSpinnerPrec.getModel().getValue();
            fillHistoryTable(prec);
            fillTeslaStatsTable(prec);

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonChart;
    private javax.swing.JButton jButtonClearFilter;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonQuery;
    private javax.swing.JButton jButtonSpecialSelect;
    private javax.swing.JCheckBox jCheckBoxIngoreGarbage;
    private javax.swing.JCheckBox jCheckBoxShowAllTesla;
    private javax.swing.JCheckBox jCheckBoxUseRegEx;
    private javax.swing.JComboBox<String> jComboBoxEdisonSids;
    private javax.swing.JComboBox<String> jComboBoxResolution;
    private javax.swing.JComboBox<String> jComboBoxTeslaHosts;
    private javax.swing.JComboBox<String> jComboBoxTeslaStations;
    private javax.swing.JComboBox<String> jComboTeslaUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelPointCounts;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinnerPrec;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableDataPoints;
    private javax.swing.JTable jTableHistory;
    private javax.swing.JTable jTableStatistics;
    private javax.swing.JTextField jTextFieldEndDate;
    private javax.swing.JTextField jTextFieldFilter;
    private javax.swing.JTextField jTextFieldStartDate;
    private javax.swing.JTextField jTextFieldTimeZone;
    // End of variables declaration//GEN-END:variables
}
