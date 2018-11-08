
package View.Sites.EditSite.B_Alarms.SiteAlarms.PushAlarmChanges;

import Controller.OptiCxAPIController;
import Model.DataModels.Alarms.AlarmListEntry;
import Model.DataModels.Stations.StationAlarmPushObject;
import Model.PropertyChangeNames;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class PushAlarmChangesFrame extends javax.swing.JFrame  implements PropertyChangeListener {

    private static PushAlarmChangesFrame thisInstance;
    private final OptiCxAPIController controller;
    //private final AlarmListEntry alarmListEntry;
    private final String stationId;

    private PushAlarmChangesFrame( OptiCxAPIController controller, String stationId, AlarmListEntry alarmListEntry) {
        initComponents();
        
        this.controller = controller;
        //this.alarmListEntry = alarmListEntry;
        this.stationId = stationId;
        
        this.jLabelAlarmSid.setText(alarmListEntry.getSid());
        this.jCheckBoxIsActive.setSelected(true);
        
        DateTimeFormatter apiFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTime ts = DateTime.now();
        String timeStampString = ts.toString(apiFormat);
        
        this.jTextFieldTimestamp.setText( timeStampString );
       
        this.jTextFieldName.setText(alarmListEntry.getName());
        this.jTextFieldStationId.setText( stationId);
        this.jTextFieldMessage.setText("");
        this.jTextFieldContextId.setText( "");
        this.jTextFieldStationName.setText(stationId);
        this.jTextFieldSendingStationName.setText("");
        this.jTextFieldSource.setText(stationId);
        this.jCheckBoxIsRespush.setSelected(false);
       
    }
    
    public static PushAlarmChangesFrame getInstance(OptiCxAPIController controller, String stationId, AlarmListEntry alarmListEntry) {
        if (thisInstance == null) {
            thisInstance = new PushAlarmChangesFrame(controller, stationId, alarmListEntry);
        }
        return thisInstance;
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

        jLabel1 = new javax.swing.JLabel();
        jLabelAlarmSid = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldTimestamp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldStationId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldMessage = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldContextId = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldStationName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldSendingStationName = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldSource = new javax.swing.JTextField();
        jButtonCancel = new javax.swing.JButton();
        jButtonPush = new javax.swing.JButton();
        jCheckBoxIsActive = new javax.swing.JCheckBox();
        jCheckBoxIsRespush = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Push Alarm Changes");
        setAlwaysOnTop(true);

        jLabel1.setText("Alarm Sid:");

        jLabelAlarmSid.setText("*alarmsid*");

        jLabel4.setText("Timestamp:");

        jTextFieldTimestamp.setText("jTextField1");

        jLabel5.setText("Name:");

        jTextFieldName.setText("jTextField1");

        jLabel6.setText("Station Id:");

        jTextFieldStationId.setText("jTextField1");

        jLabel7.setText("Message:");

        jTextFieldMessage.setText("jTextField1");

        jLabel8.setText("AlarmContextId:");

        jTextFieldContextId.setText("jTextField1");

        jLabel9.setText("Station Name:");

        jTextFieldStationName.setText("jTextField1");

        jLabel10.setText("Sending Station Name:");

        jTextFieldSendingStationName.setText("jTextField1");

        jLabel11.setText("Source:");

        jTextFieldSource.setText("jTextField1");

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonPush.setText("Push");
        jButtonPush.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPushActionPerformed(evt);
            }
        });

        jCheckBoxIsActive.setText("Is Active");

        jCheckBoxIsRespush.setText("Is Respush");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldTimestamp, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldStationId, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldContextId, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldStationName, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSendingStationName, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSource, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonPush)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelAlarmSid))
                            .addComponent(jCheckBoxIsActive)
                            .addComponent(jCheckBoxIsRespush))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelAlarmSid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxIsActive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldTimestamp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldStationId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldContextId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldStationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldSendingStationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxIsRespush)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPush)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonPushActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPushActionPerformed
        StationAlarmPushObject pushObject = new StationAlarmPushObject();
        
        pushObject.setSid( this.jLabelAlarmSid.getText());
        pushObject.setIsActive(jCheckBoxIsActive.isSelected());
        pushObject.setAlarmTimestamp(this.jTextFieldTimestamp.getText());
        pushObject.setName(this.jTextFieldName.getText());
        pushObject.setStationId(this.jTextFieldStationId.getText());
        pushObject.setSource(this.jTextFieldSource.getText());
        pushObject.setRepush(this.jCheckBoxIsRespush.isSelected());
       
        controller.pushAlarmChanges(pushObject);
        
    }//GEN-LAST:event_jButtonPushActionPerformed

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();
        
        if (propName.equals(PropertyChangeNames.StationAlarmChangesPushed.getName())) {
            
            /*
            listOfAlarms = (List<AlarmListEntry>) evt.getNewValue();

            jTableSiteAlarms.setModel(new AlarmListTableModel(listOfAlarms));
            jTableSiteAlarms.setDefaultRenderer(Object.class, new AlarmListTableCellRenderer());
            jTableSiteAlarms.setRowSelectionInterval(0, 0);
            jTableSiteAlarms.setAutoCreateRowSorter(true);
            fixAlarmListColumnWidths(jTableSiteAlarms);
            */


        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonPush;
    private javax.swing.JCheckBox jCheckBoxIsActive;
    private javax.swing.JCheckBox jCheckBoxIsRespush;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAlarmSid;
    private javax.swing.JTextField jTextFieldContextId;
    private javax.swing.JTextField jTextFieldMessage;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldSendingStationName;
    private javax.swing.JTextField jTextFieldSource;
    private javax.swing.JTextField jTextFieldStationId;
    private javax.swing.JTextField jTextFieldStationName;
    private javax.swing.JTextField jTextFieldTimestamp;
    // End of variables declaration//GEN-END:variables
}
