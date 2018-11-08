package View.Sites.EditSite.A_History.PushLiveData;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.GenerateLiveDataPostRequest;
import Model.DataModels.Live.PostLiveData.PostLiveDataRequest;
import Model.PropertyChangeNames;
import View.Sites.EditSite.A_History.DPHistoryTableCellRenderer;
import View.Sites.EditSite.A_History.DPHistoryTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushLiveDataFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static PushLiveDataFrame thisInstance;
    private final OptiCxAPIController controller;
    private final Map<String, DatapointMetadata> metaData;
    private final String sendingSid;
    private final String stationId;
    private final String token;
    
    List<PushLiveDataTableRow> listOfPoints;

    public static PushLiveDataFrame getInstance(
            final OptiCxAPIController controller,
            final Map<String, DatapointMetadata> metaData,
            final String sendingSid,
            final String stationId,
            final String token) {
        if (thisInstance == null) {
            thisInstance = new PushLiveDataFrame(controller, metaData, sendingSid, stationId, token);
        }
        return thisInstance;

    }

    private PushLiveDataFrame(final OptiCxAPIController controller, final Map<String, DatapointMetadata> metaData,
            final String sendingSid, final String stationId, final String token) {
        initComponents();

        this.controller = controller;
        this.metaData = metaData;
        this.sendingSid = sendingSid;
        this.stationId = stationId;
        this.token = token;
        
        
        listOfPoints = new ArrayList<>();
        for( String pointName : metaData.keySet() ){
            listOfPoints.add( new PushLiveDataTableRow(metaData.get(pointName)));
        }
        
        
        this.jTableLiveData.setModel(new PushLiveDataTableModel(listOfPoints));
        this.jTableLiveData.setDefaultRenderer(Object.class, new PushLiveDataTableCellRenderer());
        this.jTableLiveData.setAutoCreateRowSorter(true);
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
        jLabelStationID = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLiveData = new javax.swing.JTable();
        jButtonClose = new javax.swing.JButton();
        jButtonPush = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Station ID:");

        jLabelStationID.setText("jLabel2");

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
        jScrollPane1.setViewportView(jTableLiveData);

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButtonPush.setText("Push");
        jButtonPush.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPushActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonPush)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
                        .addComponent(jButtonClose))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelStationID)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelStationID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonPush))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPushActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPushActionPerformed

        Map<String, Object> map = new HashMap();

        for (PushLiveDataTableRow tableRow : listOfPoints) {
            DatapointMetadata pointMetadata = metaData.get(tableRow.getPointName());
            String jaceName = (String) pointMetadata.getAssociations().get(sendingSid);

            Object val = tableRow.getVal();
            
            
            
            
            map.put(jaceName, val);
        }

        GenerateLiveDataPostRequest genPostReq = new GenerateLiveDataPostRequest(sendingSid, stationId, map);
        PostLiveDataRequest req = genPostReq.getPostLiveDataRequest();
        controller.postLiveData(token, stationId, req);
         

    }//GEN-LAST:event_jButtonPushActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.DatapointMetadataMultipleSidsReturned.getName())) {

            //Map<String, List<DatapointMetadata>> map = (Map<String, List<DatapointMetadata>>) evt.getNewValue();
        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonPush;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelStationID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableLiveData;
    // End of variables declaration//GEN-END:variables
}
