package View.Sites.EditSite.B_Alarms.SiteAlarms;

import View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmListTable.AlarmListTableCellRenderer;
import View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmListTable.AlarmListTableModel;
import View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmListTable.EnumSiteAlarmsTableColumns;
import Controller.OptiCxAPIController;
import Model.DataModels.Alarms.AlarmHistoryEntry;
import Model.DataModels.Alarms.AlarmListEntry;
import Model.DataModels.Alarms.AlarmListRequest;
import Model.PropertyChangeNames;
import View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmHistoryTable.AlarmHistoryTableCellRenderer;
import View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmHistoryTable.AlarmHistoryTableModel;
import View.Sites.EditSite.B_Alarms.SiteAlarms.AlarmHistoryTable.EnumAlarmHistoryTableColumns;
import View.Sites.EditSite.B_Alarms.SiteAlarms.PushAlarmChanges.PushAlarmChangesFrame;
import static View.Sites.EditSite.J_Contacts.EnumContactType.site;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SiteAlarmsFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static SiteAlarmsFrame thisInstance;
    private final OptiCxAPIController controller;
    private final String siteSid;
    private final String stationId;

    private List<AlarmListEntry> listOfAlarms;
    private List<AlarmHistoryEntry> siteAlarmHistores;
    private AlarmListEntry selectedAlarm;

    private final DateTimeFormatter uiFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter apiFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private SiteAlarmsFrame(OptiCxAPIController controller, String siteSid, String stationId) {
        initComponents();
        this.controller = controller;
        this.siteSid = siteSid;
        this.stationId = stationId;

        this.jLabelSiteId.setText(siteSid);

        DateTime endDate = new DateTime();

        jTextFieldHistoryEndDate.setText(endDate.toString(uiFormat));

        DateTime startDate = endDate.minusDays(60);
        jTextFieldHistoryStartDate.setText(startDate.toString(uiFormat));

        RunQuery();

        //String queryStart = startDate.toString( apiFormat );
        //String queryEnd = endDate.toString( apiFormat );
        //controller.getAlarmHistory(siteSid, queryStart, queryEnd);
    }

    public static SiteAlarmsFrame getInstance(OptiCxAPIController controller, String siteSid, String stationId) {
        if (thisInstance == null) {
            thisInstance = new SiteAlarmsFrame(controller, siteSid, stationId);
        }
        return thisInstance;
    }

    @Override
    public void dispose() {
        controller.removePropChangeListener(this);
        thisInstance = null;
        super.dispose();
    }

    private void RunQuery() {
        DateTime startDate = DateTime.parse(jTextFieldHistoryStartDate.getText(), uiFormat);
        DateTime endDate = DateTime.parse(jTextFieldHistoryEndDate.getText(), uiFormat);

        String queryStart = startDate.toString(apiFormat);
        String queryEnd = endDate.toString(apiFormat);

        controller.getAlarmHistory(siteSid, queryStart, queryEnd);
    }

    public void fixAlarmListColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        for (int i = 0; i < t.getColumnCount(); i++) {
            column = t.getColumnModel().getColumn(i);
            EnumSiteAlarmsTableColumns colEnum = EnumSiteAlarmsTableColumns.getColumnFromColumnNumber(i);
            switch (colEnum) {
                case Sid:
                    column.setPreferredWidth(350);
                    break;
                case Name:
                    column.setPreferredWidth(200);
                    break;
                case State:
                    column.setPreferredWidth(100);
                    break;
                case StartDate:
                case AckDate:
                case EndDate:
                    column.setPreferredWidth(200);
                    break;
            }
        }
    }

    public void fixAlarmHistoryColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        for (int i = 0; i < t.getColumnCount(); i++) {
            column = t.getColumnModel().getColumn(i);
            EnumAlarmHistoryTableColumns colEnum = EnumAlarmHistoryTableColumns.getEnumFromColNumber(i);
            switch (colEnum) {
                case AlarmId:
                    column.setPreferredWidth(350);
                    break;
                case Name:
                    column.setPreferredWidth(200);
                    break;
                case StartDate:
                    column.setPreferredWidth(200);
                    break;
                case EndDate:
                    column.setPreferredWidth(200);
                    break;
                case State:
                    column.setPreferredWidth(100);
                    break;
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.AlarmListReturned.getName())) {
            listOfAlarms = (List<AlarmListEntry>) evt.getNewValue();

            jTableSiteAlarms.setModel(new AlarmListTableModel(listOfAlarms));
            jTableSiteAlarms.setDefaultRenderer(Object.class, new AlarmListTableCellRenderer());
            jTableSiteAlarms.setRowSelectionInterval(0, 0);
            jTableSiteAlarms.setAutoCreateRowSorter(true);
            fixAlarmListColumnWidths(jTableSiteAlarms);

        } else if (propName.equals(PropertyChangeNames.SiteAlarmHistoryReturned.getName())) {

            try {
                siteAlarmHistores = (List<AlarmHistoryEntry>) evt.getNewValue();

                jTableAlarmHistories.setModel(new AlarmHistoryTableModel(siteAlarmHistores));
                jTableAlarmHistories.setDefaultRenderer(Object.class, new AlarmHistoryTableCellRenderer());

                if (siteAlarmHistores.size() > 0) {
                    jTableAlarmHistories.setRowSelectionInterval(0, 0);
                }
                jTableAlarmHistories.setAutoCreateRowSorter(true);
                fixAlarmHistoryColumnWidths(jTableAlarmHistories);

            } catch (Exception ex) {
                Logger.getLogger(SiteAlarmsFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTopStuff = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelSiteId = new javax.swing.JLabel();
        jPanelBottomPanel = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSiteAlarms = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldHistoryStartDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldHistoryEndDate = new javax.swing.JTextField();
        jButtonQuery = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAlarmHistories = new javax.swing.JTable();
        jPanelButtons = new javax.swing.JPanel();
        jButtonOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Edit Alarms");
        setMinimumSize(new java.awt.Dimension(876, 745));

        jLabel1.setText("SiteID:");

        jLabelSiteId.setText("*siteId*");

        javax.swing.GroupLayout jPanelTopStuffLayout = new javax.swing.GroupLayout(jPanelTopStuff);
        jPanelTopStuff.setLayout(jPanelTopStuffLayout);
        jPanelTopStuffLayout.setHorizontalGroup(
            jPanelTopStuffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopStuffLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSiteId)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTopStuffLayout.setVerticalGroup(
            jPanelTopStuffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopStuffLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTopStuffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelSiteId))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.5);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Alarm List"));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        jTableSiteAlarms.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableSiteAlarms.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableSiteAlarms.setShowGrid(true);
        jTableSiteAlarms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSiteAlarmsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSiteAlarms);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 925, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 137, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jSplitPane1.setTopComponent(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Alarm Histories"));

        jLabel2.setText("Start:");

        jTextFieldHistoryStartDate.setText("jTextField1");

        jLabel3.setText("End:");

        jTextFieldHistoryEndDate.setText("jTextField2");

        jButtonQuery.setText("Query");
        jButtonQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQueryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldHistoryStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldHistoryEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                .addComponent(jButtonQuery)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldHistoryStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldHistoryEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonQuery)))
        );

        jTableAlarmHistories.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableAlarmHistories.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(jTableAlarmHistories);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout jPanelBottomPanelLayout = new javax.swing.GroupLayout(jPanelBottomPanel);
        jPanelBottomPanel.setLayout(jPanelBottomPanelLayout);
        jPanelBottomPanelLayout.setHorizontalGroup(
            jPanelBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        jPanelBottomPanelLayout.setVerticalGroup(
            jPanelBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonOK.setText("OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelButtonsLayout = new javax.swing.GroupLayout(jPanelButtons);
        jPanelButtons.setLayout(jPanelButtonsLayout);
        jPanelButtonsLayout.setHorizontalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOK)
                .addContainerGap())
        );
        jPanelButtonsLayout.setVerticalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOK))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTopStuff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelBottomPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTopStuff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed

        this.dispose();
    }//GEN-LAST:event_jButtonOKActionPerformed

    private void jTableSiteAlarmsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSiteAlarmsMouseClicked
        if (evt.getClickCount() >= 1) {
            int row = jTableSiteAlarms.rowAtPoint(evt.getPoint());

            AlarmListTableModel mod = (AlarmListTableModel) this.jTableSiteAlarms.getModel();
            int modelIndex = jTableSiteAlarms.convertRowIndexToModel(row);
            selectedAlarm = listOfAlarms.get(modelIndex);

            PushAlarmChangesFrame frame = PushAlarmChangesFrame.getInstance(controller, stationId, selectedAlarm);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            controller.addModelListener(frame);

            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);

        }
    }//GEN-LAST:event_jTableSiteAlarmsMouseClicked

    private void jButtonQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQueryActionPerformed
        RunQuery();

    }//GEN-LAST:event_jButtonQueryActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonOK;
    private javax.swing.JButton jButtonQuery;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelSiteId;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelBottomPanel;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelTopStuff;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableAlarmHistories;
    private javax.swing.JTable jTableSiteAlarms;
    private javax.swing.JTextField jTextFieldHistoryEndDate;
    private javax.swing.JTextField jTextFieldHistoryStartDate;
    // End of variables declaration//GEN-END:variables

}
