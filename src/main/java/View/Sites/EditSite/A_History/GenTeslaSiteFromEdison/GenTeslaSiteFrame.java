package View.Sites.EditSite.A_History.GenTeslaSiteFromEdison;

import Controller.OptiCxAPIController;
import Model.DataModels.Sites.EnumProducts;
import Model.DataModels.Sites.Site;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.EnumEdisonGroupTypes;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaGenEquipment;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostCustomer;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostEquipResponse;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostSite;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostStation;
import Model.DataModels.TeslaModels.EnumTeslaBaseURLs;
import Model.DataModels.Views.EnumPageViewTypes;
import Model.DataModels.Views.ItemGroup;
import Model.DataModels.Views.PageView;
import Model.DataModels.Views.ViewItem;
import Model.PropertyChangeNames;
import View.Sites.EditSite.A_History.GenTeslaSiteFromEdison.TeslaEquipmentTable.TeslaEquipTableCellRenderer;
import View.Sites.EditSite.A_History.GenTeslaSiteFromEdison.TeslaEquipmentTable.TeslaEquipTableModel;
import View.Sites.EditSite.EditSiteDetailsFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

public final class GenTeslaSiteFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static GenTeslaSiteFrame thisInstance;
    private final OptiCxAPIController controller;

    private List<TeslaGenEquipment> equipList;

    private final String toBeGenerated = "*to be generated*";

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

        fillTeslasHostsDropdown();

        initCustomerPanel(edisonSite.getExtSFID());
        initSitePanel(edisonSite.getName());
        initStationPanel();

        controller.getUIMetaData(EnumProducts.edge.getName(), edisonSite.getSid(), EnumPageViewTypes.PlantOverview.getEdisonName());
    }

    public void fillTeslasHostsDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        this.jComboBoxTeslaHosts.setModel(comboBoxModel);

        for (String url : EnumTeslaBaseURLs.getURLs()) {
            jComboBoxTeslaHosts.addItem(url);
        }

        this.jComboBoxTeslaHosts.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                final String name = (String) combo.getSelectedItem();

                final EnumTeslaBaseURLs teslaHost = EnumTeslaBaseURLs.getHostFromName(name);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.resetTeslaClient(teslaHost);
                    }
                });
            }
        });

        jComboBoxTeslaHosts.setSelectedIndex(0);

    }

    private void initCustomerPanel(String sfId) {
        jTextFieldCustomerName.setText("");
        jTextFieldSalesForceID.setText(sfId);
        jLabelCustId.setText(toBeGenerated);
    }

    private void initSitePanel(String siteName) {
        jTextFieldSiteName.setText(siteName);
        jTextFieldSiteShortName.setText("");
        jLabelSiteId.setText(toBeGenerated);
    }

    private void initStationPanel() {
        jTextFieldStationName.setText("");
        jCheckBoxBaslineEnabled.setSelected(false);
        jLabelStationSiteId.setText(toBeGenerated);
        jTextFieldStationCommissionedAt.setText("2018-11-16");
        jTextFieldStationPlantId.setText("plant id");
        jCheckBoxRegenerationAllowed.setSelected(true);
        jTextFieldStationProductType.setText("edge");
        jCheckBoxAtomEnabled.setSelected(false);
        jTextFieldStationAddress.setText("1 Center Ct, Cleveland, OH, 44115");
        jTextFieldStationTimeZone.setText("America/New_York");

        jLabelStationId.setText(toBeGenerated);

    }

    private void setTeslaEquipList(PageView pageView) {

        equipList = new ArrayList<>();
        List<ItemGroup> itemGroups = pageView.getGroups();

        for (ItemGroup ig : itemGroups) {
            String typeName = ig.getTypeName();
            EnumEdisonGroupTypes groupType = EnumEdisonGroupTypes.getEnumFromTypeName(typeName);
            for (ViewItem vi : ig.getItems()) {
                equipList.add(new TeslaGenEquipment(vi, groupType, "generic", "generic", toBeGenerated));
            }
        }
    }

    private void updateEquipWithIds(List<TeslaPostEquipResponse> equipResponses) {
        TeslaEquipTableModel model = (TeslaEquipTableModel) jTableEquipment.getModel();
        model.updateEquipWithIds(equipResponses);
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
                case 5:
                    column.setPreferredWidth(250);
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
        jLabel3 = new javax.swing.JLabel();
        jTextFieldSiteName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldSiteShortName = new javax.swing.JTextField();
        jLabelSiteId = new javax.swing.JLabel();
        jButtonClose = new javax.swing.JButton();
        jButtonGenerateSite = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEquipment = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldStationName = new javax.swing.JTextField();
        jLabelStationId = new javax.swing.JLabel();
        jCheckBoxBaslineEnabled = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldStationCommissionedAt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldStationPlantId = new javax.swing.JTextField();
        jCheckBoxRegenerationAllowed = new javax.swing.JCheckBox();
        jLabelStationSiteId = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldStationProductType = new javax.swing.JTextField();
        jCheckBoxAtomEnabled = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldStationAddress = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldStationTimeZone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxTeslaHosts = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldCustomerName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSalesForceID = new javax.swing.JTextField();
        jLabelCustId = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Site Info"));

        jLabel3.setText("Site Name:");

        jTextFieldSiteName.setText("jTextField3");

        jLabel4.setText("Site Short Name:");

        jTextFieldSiteShortName.setText("jTextField4");

        jLabelSiteId.setText("*siteId*");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSiteShortName, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSiteId)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldSiteShortName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSiteId)
                .addContainerGap())
        );

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButtonGenerateSite.setText("Generate Site");
        jButtonGenerateSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerateSiteActionPerformed(evt);
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Station Details"));

        jLabel7.setText("Name:");

        jTextFieldStationName.setText("jTextField1");

        jLabelStationId.setText("*stationId*");

        jCheckBoxBaslineEnabled.setText("Baseline Enabled");

        jLabel6.setText("siteId:");

        jTextFieldStationCommissionedAt.setText("jTextField1");

        jLabel9.setText("commissionedAt:");

        jLabel10.setText("plantID:");

        jTextFieldStationPlantId.setText("jTextField2");

        jCheckBoxRegenerationAllowed.setText("Regeneration Allowed");

        jLabelStationSiteId.setText("jLabel11");

        jLabel12.setText("Product Type:");

        jTextFieldStationProductType.setText("jTextField3");

        jCheckBoxAtomEnabled.setText("Atom Enabled");

        jLabel13.setText("Address:");

        jTextFieldStationAddress.setText("jTextField4");

        jLabel14.setText("Time Zone:");

        jTextFieldStationTimeZone.setText("jTextField5");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldStationPlantId, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldStationTimeZone, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 471, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBoxRegenerationAllowed)
                                    .addComponent(jCheckBoxBaslineEnabled)
                                    .addComponent(jCheckBoxAtomEnabled)))
                            .addComponent(jLabelStationId, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldStationName, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelStationSiteId))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldStationAddress))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldStationProductType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldStationCommissionedAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(56, 56, 56))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldStationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jTextFieldStationTimeZone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jTextFieldStationPlantId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabelStationSiteId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldStationProductType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldStationCommissionedAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldStationAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jCheckBoxRegenerationAllowed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxBaslineEnabled)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxAtomEnabled)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelStationId)
                .addContainerGap())
        );

        jLabel5.setText("Host:");

        jComboBoxTeslaHosts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Customer Info"));

        jLabel1.setText("Customer Name:");

        jTextFieldCustomerName.setText("jTextField1");

        jLabel2.setText("Sales Force ID:");

        jTextFieldSalesForceID.setText("jTextField2");

        jLabelCustId.setText("*custId*");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSalesForceID, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelCustId)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldSalesForceID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelCustId)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonGenerateSite)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClose))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxTeslaHosts, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxTeslaHosts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonGenerateSite))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonGenerateSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerateSiteActionPerformed

        TeslaPostCustomer postCustomer = new TeslaPostCustomer(
                jTextFieldCustomerName.getText(),
                jTextFieldSalesForceID.getText()
        );

        controller.postCustomer(postCustomer);
    }//GEN-LAST:event_jButtonGenerateSiteActionPerformed

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
            Map<String, Object> map = (Map<String, Object>) evt.getNewValue();
            String customerId = (String) map.get("id");
            jLabelCustId.setText(customerId);

            TeslaPostSite postSite = new TeslaPostSite(jTextFieldSiteName.getText(), jTextFieldSiteShortName.getText());
            controller.postSite(customerId, postSite);

        } else if (propName.equals(PropertyChangeNames.TeslaSiteCreated.getName())) {

            Map<String, Object> map = (Map<String, Object>) evt.getNewValue();
            String siteID = (String) map.get("id");
            jLabelSiteId.setText(siteID);
            jLabelStationSiteId.setText(siteID);

            TeslaPostStation postStation = new TeslaPostStation();
            postStation.setName(jTextFieldStationName.getText());
            postStation.setShortName(jTextFieldStationPlantId.getText());
            postStation.setbaselineEnabled(jCheckBoxBaslineEnabled.isSelected());
            postStation.setSiteId(siteID);
            postStation.setCommissionedAt(jTextFieldStationCommissionedAt.getText());
            postStation.setPlantId(jTextFieldStationPlantId.getText());
            postStation.setRegenerationAllowed(jCheckBoxRegenerationAllowed.isSelected());
            postStation.setProductType(jTextFieldStationProductType.getText());
            postStation.setAtomEnabled(jCheckBoxAtomEnabled.isSelected());
            postStation.setaddress(jTextFieldStationAddress.getText());
            postStation.setTimeZone(jTextFieldStationTimeZone.getText());

            controller.postStation(siteID, postStation);

        } else if (propName.equals(PropertyChangeNames.TeslaStationCreated.getName())) {

            Map<String, Object> map = (Map<String, Object>) evt.getNewValue();
            String stationID = (String) map.get("id");
            jLabelStationId.setText(stationID);

            controller.postEquipmentList(stationID, equipList);

        } else if (propName.equals(PropertyChangeNames.TeslaEquipmentCreated.getName())) {
            List<TeslaPostEquipResponse> equipResponses = (List<TeslaPostEquipResponse>) evt.getNewValue();
            updateEquipWithIds(equipResponses);

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonGenerateSite;
    private javax.swing.JCheckBox jCheckBoxAtomEnabled;
    private javax.swing.JCheckBox jCheckBoxBaslineEnabled;
    private javax.swing.JCheckBox jCheckBoxRegenerationAllowed;
    private javax.swing.JComboBox<String> jComboBoxTeslaHosts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCustId;
    private javax.swing.JLabel jLabelSiteId;
    private javax.swing.JLabel jLabelStationId;
    private javax.swing.JLabel jLabelStationSiteId;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEquipment;
    private javax.swing.JTextField jTextFieldCustomerName;
    private javax.swing.JTextField jTextFieldSalesForceID;
    private javax.swing.JTextField jTextFieldSiteName;
    private javax.swing.JTextField jTextFieldSiteShortName;
    private javax.swing.JTextField jTextFieldStationAddress;
    private javax.swing.JTextField jTextFieldStationCommissionedAt;
    private javax.swing.JTextField jTextFieldStationName;
    private javax.swing.JTextField jTextFieldStationPlantId;
    private javax.swing.JTextField jTextFieldStationProductType;
    private javax.swing.JTextField jTextFieldStationTimeZone;
    // End of variables declaration//GEN-END:variables
}
