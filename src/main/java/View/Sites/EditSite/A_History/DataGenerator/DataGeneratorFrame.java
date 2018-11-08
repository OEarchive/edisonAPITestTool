package View.Sites.EditSite.A_History.DataGenerator;

import Model.DataModels.Datapoints.simulator.DGArgs;
import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.Datapoints.EnumResolutions;
import Model.DataModels.Datapoints.simulator.EnumPattern;
import Model.DataModels.Datapoints.simulator.EnumPeriod;
import Model.DataModels.Datapoints.simulator.EnumPointType;
import Model.DataModels.Datapoints.simulator.XLSPointRow;
import Model.DataModels.Datapoints.simulator.XLSReader;
import Model.DataModels.Graph.EdisonNode;
import Model.DataModels.Graph.EnumGraphNodeTypes;
import Model.DataModels.Sites.SiteDPConfig.EnumGroupTypes;
import Model.DataModels.Sites.SiteDPConfig.SiteDPConfigPoint;
import Model.DataModels.Stations.HistoryPushObject;
import Model.DataModels.Stations.HistoryPushPoint;
import Model.PropertyChangeNames;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.TableColumn;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;

public class DataGeneratorFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static DataGeneratorFrame thisInstance;
    private final OptiCxAPIController controller;
    private final EnumGraphNodeTypes scope;
    private final String sid;
    private final Map<String, Map<String, List<String>>> rulesInfo;
    private final DGArgs args;
    private final List<XLSPointRow> xlsRows;
    private List<DGTableRow> dgTableRows;
    private final Map<String, List<SiteDPConfigPoint>> configGroups;
    private final Map<String, DatapointMetadata> nameToMetadataMap;
    private List<DatapointMetadata> pointMetaDataList;

    private Timer lapsedTimeTimer;
    private ActionListener lapsedTimeUpdater;
    private DateTime historyPushTimerStart;

    private String filter;
    private boolean useRegEx;
    
    //there are some things here that need to be sorted out.
    //times like these are craz

    public static DataGeneratorFrame getInstance(
            final OptiCxAPIController controller,
            EnumGraphNodeTypes scope,
            String sid,
            Map<String, Map<String, List<String>>> rulesInfo,
            Map<String, List<SiteDPConfigPoint>> configGroups,
            Map<String, DatapointMetadata> nameToMetadataMap,
            DGArgs args
    ) {
        if (thisInstance == null) {
            thisInstance = new DataGeneratorFrame(
                    controller,
                    scope,
                    sid,
                    rulesInfo,
                    configGroups,
                    nameToMetadataMap,
                    args);
        }
        return thisInstance;
    }

    @Override
    public void dispose() {
        if (lapsedTimeTimer != null) {
            lapsedTimeTimer.stop();
            lapsedTimeTimer = null;
        }
        controller.removePropChangeListener(this);
        thisInstance = null;
        super.dispose();
    }

    private DataGeneratorFrame(
            OptiCxAPIController controller,
            EnumGraphNodeTypes scope,
            String sid,
            Map<String, Map<String, List<String>>> rulesInfo,
            Map<String, List<SiteDPConfigPoint>> configGroups,
            Map<String, DatapointMetadata> nameToMetadataMap,
            DGArgs args) {

        initComponents();

        lapsedTimeUpdater = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                DateTime historyPusTimerEnd = DateTime.now();
                Period period = new Period(historyPushTimerStart, historyPusTimerEnd);
                String lapsedTimeString = String.format("%03d %02d:%02d:%02d", period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds());
                jLabelLapsedTime.setText(lapsedTimeString);
                lapsedTimeTimer.restart();

            }
        };

        this.controller = controller;
        this.scope = scope;
        this.sid = sid;
        this.rulesInfo = rulesInfo;
        this.configGroups = configGroups;
        this.nameToMetadataMap = nameToMetadataMap;

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        this.jTextFieldStartTime.setText(args.getStartDate().toString(fmt));
        this.jTextFieldEndTime.setText(args.getEndDate().toString(fmt));

        this.jLabelStationName.setText(args.getStationName());
        this.jLabelStationID.setText(args.getStationId());
        this.jLabelStationSid.setText(args.getSendingSid());

        fillResolutionsDropdown();

        this.filter = "";
        this.jTextFieldFilter.setText("");
        this.jTextFieldFilter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                filter = jTextFieldFilter.getText();
                fillDatapointsTable(dgTableRows, filter, useRegEx);
            }
        });

        useRegEx = false;
        this.jCheckRegEx.setSelected(useRegEx);
        this.jCheckRegEx.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                useRegEx = jCheckRegEx.isSelected();
                fillDatapointsTable(dgTableRows, filter, useRegEx);
            }
        });

        jButtonStart.setEnabled(false);

        xlsRows = new XLSReader("ConfigPoints.xlsx").readFile();

        fillInValuesFromXLS(scope, sid, nameToMetadataMap);
        this.args = args;
        fillDatapointsTable(dgTableRows, filter, useRegEx);
        fillRulesDropdown(rulesInfo);
    }

    private void fillInValuesFromXLS(EnumGraphNodeTypes scope, String sid, Map<String, DatapointMetadata> nameToMetadataMap) {
        dgTableRows = new ArrayList<>();
        pointMetaDataList = new ArrayList<>();

        for (String name : nameToMetadataMap.keySet()) {
            DatapointMetadata point = nameToMetadataMap.get(name);
            dgTableRows.addAll(getDGTableRowsFromXLS(scope, sid, point));
            pointMetaDataList.add(point);
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
            List<SiteDPConfigPoint> configPoints = configGroups.get(groupEnumValue.getGroupName());

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

    private void fillRulesDropdown(final Map<String, Map<String, List<String>>> rulesInfo) {

        if (rulesInfo == null) {
            jComboBoxRules.setModel(new DefaultComboBoxModel());
            return;
        }

        final String noSelection = "No select";

        List<String> cpDropdownNames = new ArrayList<>();
        cpDropdownNames.add(noSelection);

        for (String ruleName : rulesInfo.keySet()) {
            cpDropdownNames.add(ruleName);
        }

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(cpDropdownNames.toArray());
        this.jComboBoxRules.setModel(comboBoxModel);
        this.jComboBoxRules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String ruleName = (String) combo.getSelectedItem();
                if (ruleName.compareTo(noSelection) != 0) {
                    //TODO: controller.getDatapointMetadataMultipleSids(rulesInfo.get(ruleName));
                } else {
                    fillInValuesFromXLS(scope, sid, nameToMetadataMap);
                    fillDatapointsTable(dgTableRows, filter, useRegEx);
                }
            }
        });

        this.jComboBoxRules.setSelectedIndex(0);
        this.jComboBoxRules.setEnabled(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelOptions = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldStartTime = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldEndTime = new javax.swing.JTextField();
        jComboBoxResolution = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabelStationID = new javax.swing.JLabel();
        jLabelStationName = new javax.swing.JLabel();
        jLabelStationSid = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxRules = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldFilter = new javax.swing.JTextField();
        jCheckRegEx = new javax.swing.JCheckBox();
        jCheckBoxSingleValuePush = new javax.swing.JCheckBox();
        jPanelPointValues = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePointValues = new javax.swing.JTable();
        jPanelProgress = new javax.swing.JPanel();
        jProgressBar = new javax.swing.JProgressBar();
        jButtonStart = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabelLapsedTime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("History Generator");

        jPanelOptions.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Options"));

        jLabel1.setText("Start Time:");

        jTextFieldStartTime.setText("jTextField1");

        jLabel2.setText("End Time:");

        jTextFieldEndTime.setText("jTextField2");

        jComboBoxResolution.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Res:");

        jLabelStationID.setText("*station_ID*");

        jLabelStationName.setText("*station_Name*");

        jLabelStationSid.setText("*sending_sid*");

        jLabel7.setText("Station Name:");

        jLabel8.setText("Station ID:");

        jLabel9.setText("Sending Sid:");

        jLabel4.setText("Rule:");

        jComboBoxRules.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Filter:");

        jTextFieldFilter.setText("jTextField1");

        jCheckRegEx.setText("Reg Ex");

        jCheckBoxSingleValuePush.setText("SingleValue Push");

        javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
        jPanelOptions.setLayout(jPanelOptionsLayout);
        jPanelOptionsLayout.setHorizontalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxResolution, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxRules, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelOptionsLayout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldEndTime))
                                .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelStationName))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelStationID))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelStationSid))))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckRegEx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxSingleValuePush)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelOptionsLayout.setVerticalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabelStationName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStationID)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStationSid)
                    .addComponent(jLabel9)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxRules, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jCheckRegEx)
                    .addComponent(jCheckBoxSingleValuePush))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanelPointValues.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Point Values"));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTablePointValues.setModel(new javax.swing.table.DefaultTableModel(
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
        jTablePointValues.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTablePointValues.setShowGrid(true);
        jTablePointValues.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTablePointValuesMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePointValues);

        javax.swing.GroupLayout jPanelPointValuesLayout = new javax.swing.GroupLayout(jPanelPointValues);
        jPanelPointValues.setLayout(jPanelPointValuesLayout);
        jPanelPointValuesLayout.setHorizontalGroup(
            jPanelPointValuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPointValuesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanelPointValuesLayout.setVerticalGroup(
            jPanelPointValuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPointValuesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelProgress.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Progress"));

        jProgressBar.setStringPainted(true);

        jButtonStart.setText("Make History");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Close");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabelLapsedTime.setText("000 00:00:00");

        javax.swing.GroupLayout jPanelProgressLayout = new javax.swing.GroupLayout(jPanelProgress);
        jPanelProgress.setLayout(jPanelProgressLayout);
        jPanelProgressLayout.setHorizontalGroup(
            jPanelProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProgressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLapsedTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancel)
                .addContainerGap())
        );
        jPanelProgressLayout.setVerticalGroup(
            jPanelProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProgressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelLapsedTime))
                    .addComponent(jButtonStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelPointValues, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelProgress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPointValues, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed

        if (this.jTablePointValues.getSelectedRowCount() > 0) {

            boolean singleValuePush = jCheckBoxSingleValuePush.isSelected();

            if (singleValuePush) {

                HistoryPushObject hpo = new HistoryPushObject();
                hpo.setSendingStationName(args.getStationName());

                String startDateString = jTextFieldStartTime.getText();
                DateTime startDate = DateTime.parse(startDateString).withZone(DateTimeZone.UTC);
                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                String timestampString = startDate.toString(fmt);

                hpo.setTimestamp(timestampString);
                hpo.setLastSuccessTimestamp(timestampString);

                hpo.setStationName(args.getStationName());
                hpo.setStationId(args.getStationId());

                List<HistoryPushPoint> listOfPoints = new ArrayList<>();

                int[] selectedRowsIndicies = this.jTablePointValues.getSelectedRows();
                DGPointTableModel model = (DGPointTableModel) this.jTablePointValues.getModel();
                for (int index : selectedRowsIndicies) {
                    int modelIndex = jTablePointValues.convertRowIndexToModel(index);
                    DGTableRow row = model.getRowFromTable(modelIndex);
                    String name = row.getPointName();

                    HistoryPushPoint hpp = new HistoryPushPoint();
                    hpp.setName(name);
                    hpp.setPath("zippy/" + name);

                    EnumPointType pt = row.getPointType();
                    hpp.setPointType(pt.getEdisonName());

                    List<String> timestamps = new ArrayList<>();
                    timestamps.add(timestampString);
                    hpp.setTimestamps(timestamps);

                    List<Object> vals = new ArrayList<>();
                    vals.add(row.getMinValue());
                    hpp.setValues(vals);

                    listOfPoints.add(hpp);

                }
                hpo.setPoints(listOfPoints);
                controller.postDatapointHistory(hpo);

            } else {

                String startDateString = jTextFieldStartTime.getText();
                String endDateString = jTextFieldEndTime.getText();

                DateTime startDate = DateTime.parse(startDateString).withZone(DateTimeZone.UTC);
                DateTime endDate = DateTime.parse(endDateString).withZone(DateTimeZone.UTC);;

                args.setStartDate(startDate);
                args.setEndDate(endDate);

                String resString = (String) this.jComboBoxResolution.getSelectedItem();
                EnumResolutions res = EnumResolutions.getResolutionFromName(resString);
                args.setRes(res);

                jProgressBar.setMaximum(args.getNumberOfHours());
                jProgressBar.setValue(0);
                jProgressBar.setStringPainted(true);

                Map<String, DGTableRow> tableRowsMap = new HashMap<>();
                int[] selectedRowsIndicies = this.jTablePointValues.getSelectedRows();
                DGPointTableModel model = (DGPointTableModel) this.jTablePointValues.getModel();
                for (int index : selectedRowsIndicies) {
                    int modelIndex = jTablePointValues.convertRowIndexToModel(index);
                    DGTableRow row = model.getRowFromTable(modelIndex);
                    String name = row.getPointName();
                    tableRowsMap.put(name, row);
                }

                this.jButtonStart.setEnabled(false);

                historyPushTimerStart = DateTime.now();

                lapsedTimeTimer = new Timer(1000, lapsedTimeUpdater);
                lapsedTimeTimer.start();

                controller.pushHistoryForPeriod(scope, args, tableRowsMap);
            }
        }

    }//GEN-LAST:event_jButtonStartActionPerformed

    private void jTablePointValuesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePointValuesMousePressed
        this.jButtonStart.setEnabled(this.jTablePointValues.getSelectedRowCount() > 0);
    }//GEN-LAST:event_jTablePointValuesMousePressed

    private void fillDatapointsTable(List<DGTableRow> rows, String filter, boolean useRegEx) {

        Pattern regExPattern = null;
        if (useRegEx) {
            regExPattern = Pattern.compile(filter);
        }

        List<DGTableRow> visibleRows = new ArrayList<>();
        for (DGTableRow row : rows) {
            if (filter.length() <= 0) {
                visibleRows.add(row);
            } else if (useRegEx) {
                Matcher matcher = regExPattern.matcher(row.getPointName());
                if (matcher.find()) {
                    visibleRows.add(row);
                }
            } else if (row.getPointName().contains(filter)) {
                visibleRows.add(row);
            }
        }

        List<String> patternNames = new ArrayList<>();
        for (EnumPattern enumPattern : EnumPattern.values()) {
            patternNames.add(enumPattern.getName());
        }

        List<String> periodNames = new ArrayList<>();
        for (EnumPeriod periodName : EnumPeriod.values()) {
            periodNames.add(periodName.getName());
        }

        this.jTablePointValues.setRowSorter(null);
        DGPointTableModel tm = new DGPointTableModel(visibleRows);
        this.jTablePointValues.setModel(tm);

        int patternColumnNumber = EnumDGTableColumns.Pattern.getColumnNumber();
        TableColumn patternColumn = jTablePointValues.getColumnModel().getColumn(patternColumnNumber);
        JComboBox patternComboBox = new JComboBox(patternNames.toArray());
        patternColumn.setCellEditor(new DefaultCellEditor(patternComboBox));

        int periodColumnNumber = EnumDGTableColumns.Period.getColumnNumber();
        TableColumn periodColumn = jTablePointValues.getColumnModel().getColumn(periodColumnNumber);
        JComboBox periodComboBox = new JComboBox(periodNames.toArray());
        periodColumn.setCellEditor(new DefaultCellEditor(periodComboBox));

        this.jTablePointValues.setDefaultRenderer(Object.class, new DGTableCellRenderer());
        this.jTablePointValues.setAutoCreateRowSorter(true);
        fixColumnWidths(jTablePointValues);
    }

    private void fixColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        int scopeLen = Math.max(EnumDGTableColumns.Scope.getFriendlyName().length(), this.scope.getEdisonName().length());
        int sidLen = this.sid.length();

        for (int i = 0; i < t.getColumnCount(); i++) {

            column = t.getColumnModel().getColumn(i);

            EnumDGTableColumns enumCol = EnumDGTableColumns.getColumnFromColumnNumber(i);
            switch (enumCol) {
                case Scope:
                    column.setPreferredWidth(10 * scopeLen);
                    break;
                case Sid:
                    column.setPreferredWidth(7 * sidLen);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JCheckBox jCheckBoxSingleValuePush;
    private javax.swing.JCheckBox jCheckRegEx;
    private javax.swing.JComboBox jComboBoxResolution;
    private javax.swing.JComboBox jComboBoxRules;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelLapsedTime;
    private javax.swing.JLabel jLabelStationID;
    private javax.swing.JLabel jLabelStationName;
    private javax.swing.JLabel jLabelStationSid;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JPanel jPanelPointValues;
    private javax.swing.JPanel jPanelProgress;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePointValues;
    private javax.swing.JTextField jTextFieldEndTime;
    private javax.swing.JTextField jTextFieldFilter;
    private javax.swing.JTextField jTextFieldStartTime;
    // End of variables declaration//GEN-END:variables

    public final void fillResolutionsDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumResolutions.getNames().toArray());
        this.jComboBoxResolution.setModel(comboBoxModel);
        this.jComboBoxResolution.setSelectedIndex(EnumResolutions.MINUTE5.getDropDownIndex());
        this.jComboBoxResolution.setEnabled(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.DatapointMetadataMultipleSidsReturned.getName())) {

            Map<String, List<DatapointMetadata>> map = (Map<String, List<DatapointMetadata>>) evt.getNewValue();
            dgTableRows = new ArrayList<>();
            for (String sid : map.keySet()) {
                for (DatapointMetadata metaData : map.get(sid)) {
                    //dgTableRows.add(new DGTableRow(sid, metaData));
                    dgTableRows.addAll(getDGTableRowsFromXLS(scope, sid, metaData));
                }
            }
            fillDatapointsTable(dgTableRows, filter, useRegEx);

        } else if (propName.equals(PropertyChangeNames.StationDatapointHistoryOneHourPushed.getName())) {
            int count = jProgressBar.getValue();
            count++;
            jProgressBar.setValue(Math.min(count, jProgressBar.getMaximum()));

        } else if (propName.equals(PropertyChangeNames.StationHistoryForPeriodPushed.getName())) {
            jProgressBar.setBackground(Color.GREEN);
            jProgressBar.invalidate();
            jProgressBar.repaint();

            if (lapsedTimeTimer != null) {
                lapsedTimeTimer.stop();
            }

            DateTime historyPusTimerEnd = DateTime.now();
            Period period = new Period(historyPushTimerStart, historyPusTimerEnd);
            System.out.println("lapsed time: " + PeriodFormat.getDefault().print(period));
            System.out.println(String.format("%02d:%02d:%02d", period.getHours(), period.getMinutes(), period.getSeconds()));

            String lapsedTimeString = String.format("%03d %02d:%02d:%02d", period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds());
            jLabelLapsedTime.setText(lapsedTimeString);

            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    String.format("Lapsed time: %03d days %02d:%02d:%02d", period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds()),
                    "Done!",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            this.dispose();
        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }
}
