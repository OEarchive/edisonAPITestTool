package View.Sites.EditSite.A_History.DataPointsList.EditPoints;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.EnumEdisonResolutions;
import View.CommonLibs.MapTableCellRenderer;
import View.CommonLibs.MapTableModel;
import View.CommonLibs.TagsTableCellRenderer;
import View.CommonLibs.TagsTableModel;
import View.Users.UserDetails.Phones.PhonesTableCellRenderer;
import View.Users.UserDetails.Phones.PhonesTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class EditPointFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static EditPointFrame thisInstance;
    private final OptiCxAPIController controller;
    private final DatapointMetadata pointMetadata;
    
    private final String nameKey = "name";
    private final String uomKey = "uom";

    public static EditPointFrame getInstance(final OptiCxAPIController controller, DatapointMetadata pointMetadata) {
        if (thisInstance == null) {
            thisInstance = new EditPointFrame(controller, pointMetadata);
        }
        return thisInstance;
    }

    private EditPointFrame(OptiCxAPIController controller, DatapointMetadata pointMetadata) {
        initComponents();
        this.controller = controller;
        this.pointMetadata = pointMetadata;

        this.jLabelPointID.setText(pointMetadata.getId());
        this.jLabelPointType.setText(pointMetadata.getPointType());
        this.jLabelCurrentValue.setText(pointMetadata.getCurrentValue());
        this.jLabelTimestamp.setText(pointMetadata.getCurrentTimestamp());
        
        this.jTextFieldDatapointName.setText( (String)pointMetadata.getMetadata().get(nameKey));
        this.jTextFieldUnitOfMeasure.setText( (String)pointMetadata.getMetadata().get(uomKey));

        fillResolutionDropdown();
        fillTags();
        fillAssociationsTable();
        fillAttributesTable();
        
    }
    
    @Override
    public void dispose() {
        controller.removePropChangeListener(this);
        thisInstance = null;
        super.dispose();
    }
    
    private void fillResolutionDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumEdisonResolutions.getNames().toArray());
        EnumEdisonResolutions res = EnumEdisonResolutions.MINUTE5;
        this.jComboBoxDefaultRes.setModel(comboBoxModel);
        this.jComboBoxDefaultRes.setSelectedIndex(res.getDropDownIndex());
        this.jComboBoxDefaultRes.setEnabled(true);
    }

    private void fillTags() {
        this.jTableTags.setDefaultRenderer(Object.class, new TagsTableCellRenderer());
        this.jTableAssociations.setModel(new TagsTableModel(pointMetadata.getTags()));
    }

    private void fillAssociationsTable() {
        this.jTableAssociations.setDefaultRenderer(Object.class, new MapTableCellRenderer());
        this.jTableAssociations.setModel(new MapTableModel(pointMetadata.getAssociations()));
    }

    private void fillAttributesTable() {
        this.jTableAttributes.setDefaultRenderer(Object.class, new MapTableCellRenderer());
        this.jTableAttributes.setModel(new MapTableModel(pointMetadata.getMetadata()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelProperties = new javax.swing.JPanel();
        jLabelCurrentValue = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelPointID = new javax.swing.JLabel();
        jLabelPointType = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabelTimestamp = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanelAssociations = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAssociations = new javax.swing.JTable();
        jButtonAddAssoc = new javax.swing.JButton();
        jButtonRemoveAssoc = new javax.swing.JButton();
        jPanelMetadata = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldDatapointName = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableAttributes = new javax.swing.JTable();
        jButtonAddMetadata = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldUnitOfMeasure = new javax.swing.JTextField();
        jComboBoxDefaultRes = new javax.swing.JComboBox();
        jPanelTags = new javax.swing.JPanel();
        jButtonAddTag = new javax.swing.JButton();
        jButtonRemoveTag = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableTags = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelProperties.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelCurrentValue.setText("*current_value*");

        jLabel2.setText("PointType:");

        jLabel1.setText("Point ID:");

        jLabelPointID.setText("*pointId*");

        jLabelPointType.setText("*pointType*");

        jLabel9.setText("Current Value:");

        jLabelTimestamp.setText("*timestamp*");

        jLabel7.setText("Timestamp:");

        javax.swing.GroupLayout jPanelPropertiesLayout = new javax.swing.GroupLayout(jPanelProperties);
        jPanelProperties.setLayout(jPanelPropertiesLayout);
        jPanelPropertiesLayout.setHorizontalGroup(
            jPanelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPropertiesLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelPointType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTimestamp))
                    .addGroup(jPanelPropertiesLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelPointID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelCurrentValue)))
                .addContainerGap())
        );
        jPanelPropertiesLayout.setVerticalGroup(
            jPanelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelPointID)
                    .addComponent(jLabelCurrentValue)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabelPointType)
                    .addComponent(jLabelTimestamp)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonClose)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jButtonClose))
        );

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanelAssociations.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Associations"));

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
        jScrollPane1.setViewportView(jTableAssociations);

        jButtonAddAssoc.setText("Add");

        jButtonRemoveAssoc.setText("Remove");

        javax.swing.GroupLayout jPanelAssociationsLayout = new javax.swing.GroupLayout(jPanelAssociations);
        jPanelAssociations.setLayout(jPanelAssociationsLayout);
        jPanelAssociationsLayout.setHorizontalGroup(
            jPanelAssociationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAssociationsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAssociationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addGroup(jPanelAssociationsLayout.createSequentialGroup()
                        .addComponent(jButtonAddAssoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoveAssoc)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelAssociationsLayout.setVerticalGroup(
            jPanelAssociationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAssociationsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAssociationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddAssoc)
                    .addComponent(jButtonRemoveAssoc))
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanelAssociations);

        jPanelMetadata.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Metadata"));

        jLabel4.setText("Name:");

        jTextFieldDatapointName.setText("jTextField1");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Attributes"));

        jTableAttributes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTableAttributes);

        jButtonAddMetadata.setText("Add");

        jButtonDelete.setText("Delete");

        jButton1.setText("Update Metadata");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButtonAddMetadata)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddMetadata)
                    .addComponent(jButtonDelete)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        jLabel5.setText("UOM:");

        jLabel6.setText("Default Resolution:");

        jTextFieldUnitOfMeasure.setText("jTextField2");

        jComboBoxDefaultRes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanelMetadataLayout = new javax.swing.GroupLayout(jPanelMetadata);
        jPanelMetadata.setLayout(jPanelMetadataLayout);
        jPanelMetadataLayout.setHorizontalGroup(
            jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelMetadataLayout.createSequentialGroup()
                        .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDatapointName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldUnitOfMeasure))
                            .addGroup(jPanelMetadataLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxDefaultRes, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 74, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelMetadataLayout.setVerticalGroup(
            jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMetadataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldDatapointName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldUnitOfMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxDefaultRes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanelMetadata);

        jPanelTags.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tags"));

        jButtonAddTag.setText("Add");

        jButtonRemoveTag.setText("Remove");

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
        jScrollPane5.setViewportView(jTableTags);

        javax.swing.GroupLayout jPanelTagsLayout = new javax.swing.GroupLayout(jPanelTags);
        jPanelTags.setLayout(jPanelTagsLayout);
        jPanelTagsLayout.setHorizontalGroup(
            jPanelTagsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTagsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTagsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanelTagsLayout.createSequentialGroup()
                        .addGroup(jPanelTagsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonRemoveTag)
                            .addGroup(jPanelTagsLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButtonAddTag)))
                        .addGap(0, 109, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTagsLayout.setVerticalGroup(
            jPanelTagsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTagsLayout.createSequentialGroup()
                .addComponent(jScrollPane5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAddTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRemoveTag)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelProperties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSplitPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelTags, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelProperties, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1)
                    .addComponent(jPanelTags, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAddAssoc;
    private javax.swing.JButton jButtonAddMetadata;
    private javax.swing.JButton jButtonAddTag;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonRemoveAssoc;
    private javax.swing.JButton jButtonRemoveTag;
    private javax.swing.JComboBox jComboBoxDefaultRes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCurrentValue;
    private javax.swing.JLabel jLabelPointID;
    private javax.swing.JLabel jLabelPointType;
    private javax.swing.JLabel jLabelTimestamp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelAssociations;
    private javax.swing.JPanel jPanelMetadata;
    private javax.swing.JPanel jPanelProperties;
    private javax.swing.JPanel jPanelTags;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableAssociations;
    private javax.swing.JTable jTableAttributes;
    private javax.swing.JTable jTableTags;
    private javax.swing.JTextField jTextFieldDatapointName;
    private javax.swing.JTextField jTextFieldUnitOfMeasure;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
