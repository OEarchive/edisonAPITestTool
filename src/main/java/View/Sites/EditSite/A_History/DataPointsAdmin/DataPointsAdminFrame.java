package View.Sites.EditSite.A_History.DataPointsAdmin;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointMetadata;
import Model.PropertyChangeNames;
import View.Sites.EditSite.A_History.DataPointsAdmin.Associations.DataPointAssociationTableModel;
import View.Sites.EditSite.A_History.DataPointsAdmin.Associations.DataPointAssociationsTableCellRenderer;
import View.Sites.EditSite.A_History.DataPointsAdmin.Metadata.DataPointMetaDataTableCellRenderer;
import View.Sites.EditSite.A_History.DataPointsAdmin.Metadata.DataPointMetaDataTableModel;
import View.Sites.EditSite.A_History.DataPointsAdmin.Tags.DataPointTagsTableCellRenderer;
import View.Sites.EditSite.A_History.DataPointsAdmin.Tags.DataPointTagsTableModel;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class DataPointsAdminFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static DataPointsAdminFrame thisInstance;
    private final OptiCxAPIController controller;
    private final List<DatapointMetadata> listOfMetadata;

    public static DataPointsAdminFrame getInstance(OptiCxAPIController controller, String sid, List<DatapointMetadata> listOfMetadata) {

        if (thisInstance == null) {
            thisInstance = new DataPointsAdminFrame(controller, sid, listOfMetadata);
        }

        return thisInstance;

    }

    private DataPointsAdminFrame(final OptiCxAPIController controller, String sid, List<DatapointMetadata> listOfMetadata) {
        initComponents();
        this.controller = controller;
        this.listOfMetadata = listOfMetadata;

        this.jLabelSid.setText(sid);

        fillTable(listOfMetadata);
    }

    @Override
    public void dispose() {
        controller.removePropChangeListener(this);
        thisInstance = null;
        super.dispose();
    }

    private void fillTable(List<DatapointMetadata> listOfMetadata) {

        this.jTableDataPoints.setModel(new DataPointsAdminTableModel(listOfMetadata));
        this.jTableDataPoints.setDefaultRenderer(Object.class, new DataPointsAdminTableCellRenderer());
        this.jTableDataPoints.setAutoCreateRowSorter(true);
        fixDataPointTableColumnWidths( jTableDataPoints );

    }

    public void fixDataPointTableColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        for (int i = 0; i < t.getColumnCount(); i++) {

            column = t.getColumnModel().getColumn(i);

            EnumDataPointsAdminTableColumns colEnum = EnumDataPointsAdminTableColumns.getColumnFromColumnNumber(i);

            switch (colEnum) {
                case PointName:
                    column.setPreferredWidth(200);
                    break;
                case PointType:
                    column.setPreferredWidth(100);
                    break;
                case CurrentValue:
                    column.setPreferredWidth(100);
                    break;
                case CurrentTimestamp:
                    column.setPreferredWidth(100);
                    break;
                case PointID:
                    column.setPreferredWidth(300);
                    break;

            }

        }

    }

    private void setAssociations(DatapointMetadata dp) {
        this.jTableAssociations.setModel(new DataPointAssociationTableModel(dp.getAssociations()));
        this.jTableAssociations.setDefaultRenderer(Object.class, new DataPointAssociationsTableCellRenderer());
        this.jTableAssociations.setAutoCreateRowSorter(true);
    }

    private void setTags(DatapointMetadata dp) {
        this.jTableTags.setModel(new DataPointTagsTableModel(dp.getTags()));
        this.jTableTags.setDefaultRenderer(Object.class, new DataPointTagsTableCellRenderer());
        this.jTableTags.setAutoCreateRowSorter(true);
    }

    private void setMetadata(DatapointMetadata dp) {
        this.jTableMetaData.setModel(new DataPointMetaDataTableModel(dp.getMetadata()));
        this.jTableMetaData.setDefaultRenderer(Object.class, new DataPointMetaDataTableCellRenderer());
        this.jTableMetaData.setAutoCreateRowSorter(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanelDataPointsTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDataPoints = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableMetaData = new javax.swing.JTable();
        jButtonAddKey = new javax.swing.JButton();
        jButtonDelKey = new javax.swing.JButton();
        jButtonUpdateDatapoint = new javax.swing.JButton();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAssociations = new javax.swing.JTable();
        jButtonDelAssociation = new javax.swing.JButton();
        jButtonAddAssociation = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableTags = new javax.swing.JTable();
        jButtonAddTag = new javax.swing.JButton();
        jButtonDelTag = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabelSid = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();
        jButtonAddDatapoint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanelDataPointsTable.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datapoints"));

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

        javax.swing.GroupLayout jPanelDataPointsTableLayout = new javax.swing.GroupLayout(jPanelDataPointsTable);
        jPanelDataPointsTable.setLayout(jPanelDataPointsTableLayout);
        jPanelDataPointsTableLayout.setHorizontalGroup(
            jPanelDataPointsTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDataPointsTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1172, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDataPointsTableLayout.setVerticalGroup(
            jPanelDataPointsTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDataPointsTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setTopComponent(jPanelDataPointsTable);

        jSplitPane2.setDividerLocation(500);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Metadata"));

        jTableMetaData.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableMetaData.setShowGrid(true);
        jScrollPane6.setViewportView(jTableMetaData);

        jButtonAddKey.setText("Add Key");

        jButtonDelKey.setText("Delete Key");

        jButtonUpdateDatapoint.setText("Update Datapoint");
        jButtonUpdateDatapoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateDatapointActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jButtonAddKey)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonUpdateDatapoint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDelKey)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddKey)
                    .addComponent(jButtonDelKey)
                    .addComponent(jButtonUpdateDatapoint))
                .addContainerGap())
        );

        jSplitPane2.setLeftComponent(jPanel11);

        jSplitPane3.setDividerLocation(150);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Associations"));

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
        jScrollPane2.setViewportView(jTableAssociations);

        jButtonDelAssociation.setText("Delete Association");

        jButtonAddAssociation.setText("Add Association");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonAddAssociation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDelAssociation)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDelAssociation)
                    .addComponent(jButtonAddAssociation)))
        );

        jSplitPane3.setTopComponent(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tags"));

        jTableTags.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableTags.setShowGrid(true);
        jScrollPane3.setViewportView(jTableTags);

        jButtonAddTag.setText("Add Tag");

        jButtonDelTag.setText("Delete Tag");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonAddTag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDelTag)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddTag)
                    .addComponent(jButtonDelTag))
                .addContainerGap())
        );

        jSplitPane3.setRightComponent(jPanel2);

        jSplitPane2.setRightComponent(jSplitPane3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel4);

        jLabelSid.setText("*sid*");

        jLabel1.setText("Sid:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSid)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelSid))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButtonAddDatapoint.setText("Add Datapoint");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAddDatapoint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonClose)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonAddDatapoint))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jTableDataPointsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDataPointsMousePressed
        int row = jTableDataPoints.rowAtPoint(evt.getPoint());
        int modelIndex = jTableDataPoints.convertRowIndexToModel(row);
        DataPointsAdminTableModel mod = (DataPointsAdminTableModel) jTableDataPoints.getModel();
        DatapointMetadata dp = mod.getDatapointFromTable(modelIndex);
        setAssociations(dp);
        setTags(dp);
        setMetadata(dp);
    }//GEN-LAST:event_jTableDataPointsMousePressed

    private void jButtonUpdateDatapointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateDatapointActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUpdateDatapointActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddAssociation;
    private javax.swing.JButton jButtonAddDatapoint;
    private javax.swing.JButton jButtonAddKey;
    private javax.swing.JButton jButtonAddTag;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonDelAssociation;
    private javax.swing.JButton jButtonDelKey;
    private javax.swing.JButton jButtonDelTag;
    private javax.swing.JButton jButtonUpdateDatapoint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelSid;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelDataPointsTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTable jTableAssociations;
    private javax.swing.JTable jTableDataPoints;
    private javax.swing.JTable jTableMetaData;
    private javax.swing.JTable jTableTags;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.DatapointMetadataMultipleSidsReturned.getName())) {

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }
}
