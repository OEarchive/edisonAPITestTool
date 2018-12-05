package View.Sites.EditSite.A_History.GenTeslaSiteFromEdison;

import Controller.OptiCxAPIController;
import Model.DataModels.Sites.EnumProducts;
import Model.DataModels.Sites.Site;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.EnumEdisonGroupTypes;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaGenEquipment;
import Model.DataModels.Views.EnumPageViewTypes;
import Model.DataModels.Views.ItemGroup;
import Model.DataModels.Views.PageView;
import Model.DataModels.Views.ViewItem;
import Model.PropertyChangeNames;
import View.Sites.EditSite.A_History.GenTeslaSiteFromEdison.TeslaEquipmentTable.TeslaEquipTableCellRenderer;
import View.Sites.EditSite.A_History.GenTeslaSiteFromEdison.TeslaEquipmentTable.TeslaEquipTableModel;
import View.Sites.EditSite.EditSiteDetailsFrame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class GenTeslaSiteFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static GenTeslaSiteFrame thisInstance;
    private final OptiCxAPIController controller;

    private List<TeslaGenEquipment> equipList;

    public static GenTeslaSiteFrame getInstance(
            final OptiCxAPIController controller,
            Site edisonSite) {

        if (thisInstance == null) {
            thisInstance = new GenTeslaSiteFrame(controller, edisonSite);
        }
        return thisInstance;
    }

    private GenTeslaSiteFrame(final OptiCxAPIController controller, Site edisonSite) {
        initComponents();

        this.controller = controller;

        jTextFieldCustomerName.setText("");
        jTextFieldSalesForceID.setText(edisonSite.getExtSFID());
        jTextFieldSiteName.setText(edisonSite.getName());
        jTextFieldSiteShortName.setText("");

        controller.getUIMetaData(EnumProducts.edge.getName(), edisonSite.getSid(), EnumPageViewTypes.PlantOverview.getEdisonName());
    }

    private void setTeslaEquipList(PageView pageView) {

        equipList = new ArrayList<>();
        List<ItemGroup> itemGroups = pageView.getGroups();

        for (ItemGroup ig : itemGroups) {
            String typeName = ig.getTypeName();
            EnumEdisonGroupTypes groupType = EnumEdisonGroupTypes.getEnumFromTypeName(typeName);
            for (ViewItem vi : ig.getItems()) {
                equipList.add(new TeslaGenEquipment(vi, groupType, "generic", "generic"));
            }
        }
    }

    private void fillEquipTable() {
        this.jTableEquipment.setDefaultRenderer(Object.class, new TeslaEquipTableCellRenderer());
        this.jTableEquipment.setModel(new TeslaEquipTableModel(equipList));
        this.jTableEquipment.setAutoCreateRowSorter(true);
        fixEquipColumnWidths(jTableEquipment);
    }

    public void fixEquipColumnWidths(JTable t) {

        for (int i = 0; i < t.getColumnCount(); i++) {
            TableColumn column = t.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(200);
                    break;
                case 1:
                    column.setPreferredWidth(200);
                    break;
                default:
                    column.setPreferredWidth(100);
                    break;
            }
        }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldSiteName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldCustomerName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSalesForceID = new javax.swing.JTextField();
        jTextFieldSiteShortName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEquipment = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Customer Name:");

        jLabel3.setText("Site Name:");

        jTextFieldSiteName.setText("jTextField3");

        jLabel4.setText("Site Short Name:");

        jTextFieldCustomerName.setText("jTextField1");

        jLabel2.setText("Sales Force ID:");

        jTextFieldSalesForceID.setText("jTextField2");

        jTextFieldSiteShortName.setText("jTextField4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSiteName))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSalesForceID, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 198, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSiteShortName, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldSalesForceID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldSiteShortName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Generate Site");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tesla Site Equipment"));

        jTableEquipment.setAutoCreateRowSorter(true);
        jTableEquipment.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableEquipment.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableEquipment.setShowGrid(true);
        jScrollPane1.setViewportView(jTableEquipment);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        /*
    public void postCustomer(TeslaPostCustomer postCustomer) {
    public void postSite(String customerId, TeslaPostSite postSite) {
    public void postStation(String siteId, TeslaPostStation postStation) {
    public void postEquipmentList(final String stationId, final List<TeslaGenEquipment> equipList) {
 
         */
 /*
    TeslaCustomerCreated("TeslaCustomerCreated"),
    TeslaSiteCreated("TeslaSiteCreated"),
    TeslaStationCreated("TeslaStationCreated"),
    TeslaEquipmentCreated("TeslaEquipmentCreated");
         */

    }//GEN-LAST:event_jButton2ActionPerformed

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.UIMetaDataReturned.getName())) {
            try {
                PageView pageView = (PageView) evt.getNewValue();
                setTeslaEquipList(pageView);
                fillEquipTable();
            } catch (Exception ex) {
                Logger.getLogger(EditSiteDetailsFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } else if (propName.equals(PropertyChangeNames.TeslaCustomerCreated.getName())) {
        } else if (propName.equals(PropertyChangeNames.TeslaSiteCreated.getName())) {
        } else if (propName.equals(PropertyChangeNames.TeslaStationCreated.getName())) {
        } else if (propName.equals(PropertyChangeNames.TeslaEquipmentCreated.getName())) {

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEquipment;
    private javax.swing.JTextField jTextFieldCustomerName;
    private javax.swing.JTextField jTextFieldSalesForceID;
    private javax.swing.JTextField jTextFieldSiteName;
    private javax.swing.JTextField jTextFieldSiteShortName;
    // End of variables declaration//GEN-END:variables
}
