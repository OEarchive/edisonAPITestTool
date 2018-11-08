package View.Sites.EditSite.E_Views.ViewLiveData;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.Live.GetLiveData.GetLiveDataResponse;
import Model.DataModels.Live.Subscriptions.CreateSubscriptionRequest;
import Model.DataModels.Live.Subscriptions.CreateSubscriptionResponse;
import Model.DataModels.Live.Subscriptions.SubscriptionResponseDatapoint;
import Model.DataModels.Views.ItemGroup;
import Model.DataModels.Views.PageView;
import Model.DataModels.Views.Point;
import Model.DataModels.Views.PointGroup;
import Model.DataModels.Views.ViewItem;
import Model.PropertyChangeNames;
import View.Sites.EditSite.C_DatapointConfig.PopupMenuDPConfigTable;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ViewLiveDataFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static ViewLiveDataFrame thisInstance;
    private final OptiCxAPIController controller;

    private int redThresh;
    private int defaultRedThresh = 30;
    private int yellowThresh;
    private int defaultYellowThresh = 10;

    private DateTime staleStartTime;

    private final Map<String, List<Point>> map;
    private List<ViewLiveDataTableRow> tableRows;

    private Timer timer = null;
    private String subscriptionId;

    private ViewLiveDataFrame(final OptiCxAPIController controller, PageView pageView) {
        initComponents();

        this.controller = controller;

        map = pageView.getDatapointList();

        tableRows = new ArrayList<>();

        for (String sid : map.keySet()) {

            List<Point> points = (List<Point>) map.get(sid);

            for (Point p : points) {
                ViewLiveDataTableRow tableRow = new ViewLiveDataTableRow(sid, p);
                tableRows.add(tableRow);
            }
        }

        this.jLabelSubId.setText("no subsr");
        this.jLabelStalenessStartTime.setText("");

        this.jTextFieldYellowThresh.setText(Integer.toString(defaultYellowThresh));
        this.jTextFieldRedThresh.setText(Integer.toString(defaultRedThresh));

        this.jTextFieldFilter.setText("");
        /*
        this.jTextFieldFilter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshTable();
            }
            
            public void refreshTable(){
                fillTable();
                
            }
            
        });
         */

        this.jCheckBoxUseRegEx.setSelected(false);
        this.jCheckBoxUseRegEx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String filter = jTextFieldFilter.getText();
                //Boolean
                fillTable(filter);
            }
        });

        fillTable("");

    }

    public static ViewLiveDataFrame getInstance(final OptiCxAPIController controller, PageView pageView) {

        if (thisInstance == null) {
            thisInstance = new ViewLiveDataFrame(controller, pageView);
        }

        return thisInstance;
    }

    @Override
    public void dispose() {

        if( timer != null ){
            timer.cancel();
        }
        controller.removePropChangeListener(this);
        thisInstance = null;
        super.dispose();
    }

    public void fixLiveDataTableColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        for (int i = 0; i < t.getColumnCount(); i++) {
            column = t.getColumnModel().getColumn(i);
            if (i < 2) {
                column.setPreferredWidth(250);
            } else {
                column.setPreferredWidth(100);
            }
        }

    }

    private void fillTable(String filter) {

        Boolean useRegEx = this.jCheckBoxUseRegEx.isSelected();

        List<ViewLiveDataTableRow> filteredList = new ArrayList<>();

        for (ViewLiveDataTableRow tableRow : tableRows) {

            if (filter.length() == 0) {
                filteredList.add(tableRow);
            } else if (!useRegEx && tableRow.getStationName() != null && tableRow.getStationName().contains(filter)) {
                filteredList.add(tableRow);

            } else if (useRegEx) {
                Pattern r = Pattern.compile(filter);

                if (tableRow.getStationName() != null) {
                    Matcher m = r.matcher(tableRow.getStationName());
                    if (m.find()) {
                        filteredList.add(tableRow);
                    }
                }
            }
        }

        this.jTableLiveData.setModel(new ViewLiveDataTableModel(filteredList, defaultYellowThresh, defaultRedThresh));
        this.jTableLiveData.setDefaultRenderer(Object.class, new ViewLiveDataTableCellRenderer());
        this.jTableLiveData.setAutoCreateRowSorter(true);
        fixLiveDataTableColumnWidths(jTableLiveData);

    }

    private void updateTableLiveValues(List<SubscriptionResponseDatapoint> points) {

        try {
            redThresh = Integer.parseInt(jTextFieldRedThresh.getText());
        } catch (Exception ex) {
            redThresh = defaultRedThresh;
            jTextFieldRedThresh.setText(Integer.toString(redThresh));
        }

        try {
            yellowThresh = Integer.parseInt(jTextFieldYellowThresh.getText());
        } catch (Exception ex) {
            yellowThresh = defaultYellowThresh;
            jTextFieldYellowThresh.setText(Integer.toString(yellowThresh));
        }

        ViewLiveDataTableModel mod = (ViewLiveDataTableModel) this.jTableLiveData.getModel();
        mod.updateValues(points, staleStartTime, yellowThresh, redThresh);

    }

    private void createLiveDataRequest() {

        if (tableRows == null || tableRows.isEmpty()) {
            return;
        }

        Map<String, List<String>> sidAndPointNameMap = new HashMap<>();

        for (String sid : map.keySet()) {

            if (!sidAndPointNameMap.containsKey(sid)) {
                sidAndPointNameMap.put(sid, new ArrayList<String>());
            }

            List<Point> points = map.get(sid);

            //add station points
            for (Point p : points) {
                String stationPointName = p.getStationName();
                if (stationPointName == null || stationPointName.isEmpty()) {
                    sidAndPointNameMap.get(sid).add(p.getName());
                } else {
                    sidAndPointNameMap.get(sid).add(stationPointName);
                }
            }

        }

        DateTime ts = DateTime.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        String timeStampString = ts.toString(fmt) + ".000Z";
        CreateSubscriptionRequest req = new CreateSubscriptionRequest(timeStampString, sidAndPointNameMap);
        controller.postNewSubscription(req);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLiveData = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldFilter = new javax.swing.JTextField();
        jCheckBoxUseRegEx = new javax.swing.JCheckBox();
        jLabelSubId = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();
        jButtonCreateSubscription = new javax.swing.JButton();
        jToggleButtonPollLiveData = new javax.swing.JToggleButton();
        jButtonPushValues = new javax.swing.JButton();
        jTextFieldYellowThresh = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldRedThresh = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelStalenessStartTime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Subscription ID:");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Live Data"));

        jTableLiveData.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableLiveData.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableLiveData.setShowGrid(true);
        jTableLiveData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableLiveDataMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableLiveData);

        jLabel6.setText("Station Name Filter:");

        jTextFieldFilter.setText("jTextField1");
        jTextFieldFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFilterActionPerformed(evt);
            }
        });

        jCheckBoxUseRegEx.setText("Use RegEx");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxUseRegEx)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxUseRegEx))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelSubId.setText("*subid*");

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButtonCreateSubscription.setText("Create Sub");
        jButtonCreateSubscription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateSubscriptionActionPerformed(evt);
            }
        });

        jToggleButtonPollLiveData.setText("Poll Live Data");
        jToggleButtonPollLiveData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonPollLiveDataActionPerformed(evt);
            }
        });

        jButtonPushValues.setText("Push Values");
        jButtonPushValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPushValuesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonCreateSubscription)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButtonPollLiveData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPushValues)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonClose)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonCreateSubscription)
                    .addComponent(jToggleButtonPollLiveData)
                    .addComponent(jButtonPushValues))
                .addContainerGap())
        );

        jTextFieldYellowThresh.setText("jTextField1");

        jLabel2.setText("Yellow after:");

        jLabel3.setText("(secs)   Red after:");

        jTextFieldRedThresh.setText("jTextField2");

        jLabel4.setText("(secs)");

        jLabel5.setText("Staleness Starttime:");

        jLabelStalenessStartTime.setText("jLabel6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSubId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelStalenessStartTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldYellowThresh, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldRedThresh, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelSubId)
                    .addComponent(jTextFieldYellowThresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldRedThresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabelStalenessStartTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jToggleButtonPollLiveDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonPollLiveDataActionPerformed
        if (jToggleButtonPollLiveData.isSelected()) {

            this.staleStartTime = DateTime.now().withZone(DateTimeZone.UTC);
            DateTimeFormatter toFormatter = DateTimeFormat.forPattern("HH:mm:ss.SSS");
            this.jLabelStalenessStartTime.setText(staleStartTime.toString(toFormatter));

            long startDelay = 0;
            long interval = 5000;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        controller.getLiveData(subscriptionId);
                    } catch (Exception ex) {
                        System.out.println("oops. something went wrong with the timer");
                    }
                }
            }, startDelay, interval);

            //jSpinnerPollInterval.setEnabled(false);
        } else {
            if (timer != null) {
                timer.cancel();
            }
            //jSpinnerPollInterval.setEnabled(true);
        }
    }//GEN-LAST:event_jToggleButtonPollLiveDataActionPerformed

    private void jButtonCreateSubscriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateSubscriptionActionPerformed
        createLiveDataRequest();
    }//GEN-LAST:event_jButtonCreateSubscriptionActionPerformed

    private void jButtonPushValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPushValuesActionPerformed

        Map< String, Map<String, Object>> sidsAndPointValues = new HashMap();
        ViewLiveDataTableModel mod = (ViewLiveDataTableModel) this.jTableLiveData.getModel();

        for (ViewLiveDataTableRow tableRow : mod.getRowsTable()) {
            if (tableRow.getPushValue() != null) {
                if (!sidsAndPointValues.containsKey(tableRow.getSid())) {
                    sidsAndPointValues.put(tableRow.getSid(), new HashMap<String, Object>());
                }
                String jaceName = tableRow.getStationName();
                Object val = tableRow.getPushValue();
                Map<String, Object> pointAndValueMap = sidsAndPointValues.get(tableRow.getSid());
                pointAndValueMap.put(jaceName, val);
            }
        }

        for (String key : sidsAndPointValues.keySet()) {
            Map<String, Object> pointNamesAndValues = sidsAndPointValues.get(key);
            //TODO:
            //GenerateLiveDataPostRequest genPostReq = new GenerateLiveDataPostRequest(sendingSid, stationId, pointNamesAndValues);
            //PostLiveDataRequest req = genPostReq.getPostLiveDataRequest();
            //controller.postLiveData(token, stationId, req);
        }

    }//GEN-LAST:event_jButtonPushValuesActionPerformed

    private void jTextFieldFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFilterActionPerformed

        fillTable(this.jTextFieldFilter.getText());
    }//GEN-LAST:event_jTextFieldFilterActionPerformed

    private void jTableLiveDataMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLiveDataMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuLiveDataTable popup = new PopupMenuLiveDataTable(evt, jTableLiveData);
        }
    }//GEN-LAST:event_jTableLiveDataMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonCreateSubscription;
    private javax.swing.JButton jButtonPushValues;
    private javax.swing.JCheckBox jCheckBoxUseRegEx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelStalenessStartTime;
    private javax.swing.JLabel jLabelSubId;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableLiveData;
    private javax.swing.JTextField jTextFieldFilter;
    private javax.swing.JTextField jTextFieldRedThresh;
    private javax.swing.JTextField jTextFieldYellowThresh;
    private javax.swing.JToggleButton jToggleButtonPollLiveData;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.SubscriptionCreated.getName())) {
            CreateSubscriptionResponse subscriptionRequestWithID = (CreateSubscriptionResponse) evt.getNewValue();
            subscriptionId = subscriptionRequestWithID.getSubsciptionID();
            this.jLabelSubId.setText(subscriptionId);

        } else if (propName.equals(PropertyChangeNames.LiveDataReturned.getName())) {
            GetLiveDataResponse resp = (GetLiveDataResponse) evt.getNewValue();
            List<SubscriptionResponseDatapoint> points = resp.getPoints();
            updateTableLiveValues(points);

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }
}
