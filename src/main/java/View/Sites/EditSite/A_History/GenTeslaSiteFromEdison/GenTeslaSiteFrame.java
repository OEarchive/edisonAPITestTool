package View.Sites.EditSite.A_History.GenTeslaSiteFromEdison;

import Controller.OptiCxAPIController;
import Model.DataModels.Sites.Chiller;
import Model.DataModels.Sites.EdgePlantInfo;
import Model.DataModels.Sites.EnumProducts;
import Model.DataModels.Sites.HeatExchanger;
import Model.DataModels.Sites.PlantEquipment;
import Model.DataModels.Sites.Pump;
import Model.DataModels.Sites.Site;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.EnumEdisonGroupTypes;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaGenEquipment;
import Model.DataModels.Views.EnumPageViewTypes;
import Model.DataModels.Views.ItemGroup;
import Model.DataModels.Views.PageView;
import Model.DataModels.Views.PointGroup;
import Model.DataModels.Views.ViewItem;
import Model.DataModels.Views.ViewSite;
import Model.PropertyChangeNames;
import View.Sites.EditSite.EditSiteDetailsFrame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenTeslaSiteFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static GenTeslaSiteFrame thisInstance;
    private final OptiCxAPIController controller;
    private final Site edisonSite;

    private PageView pageView;

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
        this.edisonSite = edisonSite;

        jTextFieldCustomerName.setText("");
        jTextFieldSalesForceID.setText(edisonSite.getExtSFID());
        jTextFieldSiteName.setText(edisonSite.getName());
        jTextFieldSiteShortName.setText("");

        //List<TeslaGenEquipment> equipList = getTeslaEquipList( edisonSite.getEquipment() );
        controller.getUIMetaData(EnumProducts.edge.getName(), edisonSite.getSid(), EnumPageViewTypes.Optimization.getEdisonName());

    }

    List<TeslaGenEquipment> getTeslaEquipList(PageView pageView) {
        List<TeslaGenEquipment> equipList = new ArrayList<>();

        List<ItemGroup> itemGroups = pageView.getGroups();

        for (ItemGroup ig : itemGroups) {
            String typeName = ig.getTypeName();

            EnumEdisonGroupTypes groupType = EnumEdisonGroupTypes.getEnumFromTypeName(typeName);
            switch (groupType) {
                case Chiller:
                    
            List<ViewItem> chillerItems = ig.getItems();
            for( ViewItem chillerViewItem : chillerItems ){
                
                TeslaGenEquipment equip = new TeslaGenEquipment();
                //equip.getName( chillerViewItem.getName());
                
                //zzz.
            }
                    
                    break;
                case PCWP:
                    break;
                case SCWP:
                    break;
                case CWP:
                    break;
                case CT:
                    break;
                case HX:
                    break;
            }

        }

        //List<Chiller> chillers = pe.getChillers();
        //List<Pump> condensers = pe.getCondenserWaterPumps();
        //List<Pump> cts = pe.getCoolingTowers();
        //List<HeatExchanger> hes = pe.getHeatExchangers();
        //List<Pump> pwps = pe.getPrimaryWaterPumps();
        //List<Pump> swps = pe.getSecondaryWaterPumps();

        //for (Chiller chiller : chillers) {
        //    TeslaGenEquipment equip = new TeslaGenEquipment();
        //    //equip.setName(chiller.);
        //}

        return equipList;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEquipment = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

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
                .addContainerGap(50, Short.MAX_VALUE))
        );

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
        jScrollPane1.setViewportView(jTableEquipment);

        jButton1.setText("Close");

        jButton2.setText("Generate Site");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.UIMetaDataReturned.getName())) {
            try {
                this.pageView = (PageView) evt.getNewValue();
                //fillPageViewTree();

            } catch (Exception ex) {
                Logger.getLogger(EditSiteDetailsFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } else if (propName.equals(PropertyChangeNames.TeslaStationsListReturned.getName())) {
            //List<TeslaStationInfo> stations = (List<TeslaStationInfo>) evt.getNewValue();
            //fillTeslasSitesDropdown(stations);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEquipment;
    private javax.swing.JTextField jTextFieldCustomerName;
    private javax.swing.JTextField jTextFieldSalesForceID;
    private javax.swing.JTextField jTextFieldSiteName;
    private javax.swing.JTextField jTextFieldSiteShortName;
    // End of variables declaration//GEN-END:variables
}
