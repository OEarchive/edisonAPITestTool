package View.Sites.NewSite;

import Controller.OptiCxAPIController;
import Model.DataModels.Sites.Address;
import Model.DataModels.Sites.Chiller;
import Model.DataModels.Sites.CreateSiteRequest;
import Model.DataModels.Sites.EdgePlantInfo;
import Model.DataModels.Sites.EnumCommProtocols;
import Model.DataModels.Sites.EnumLicenseTypes;
import Model.DataModels.Sites.EnumProducts;
import Model.DataModels.Sites.EnumPumpTypes;
import Model.DataModels.Sites.PlantEquipment;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.SitePlainTemplate;
import Model.DataModels.Sites.SiteTemplate;
import Model.DataModels.Sites.StationLicense;
import Model.PropertyChangeNames;
import View.CommonLibs.PopupMenuOnTables;
import View.Sites.EditSite.J_Contacts.ContactsTableCellRenderer;
import View.Sites.EditSite.J_Contacts.ContactsTableModel;
import View.Sites.NewSite.Equipment.ChillerTableCellRenderer;
import View.Sites.NewSite.Equipment.ChillerTableModel;
import View.Sites.NewSite.Equipment.HeatExchangersTableCellRenderer;
import View.Sites.NewSite.Equipment.HeatExchangersTableModel;
import View.Sites.NewSite.Equipment.PopUpMenuChillerTable;
import View.Sites.NewSite.Equipment.PopUpMenuHeatExchangers;
import View.Sites.NewSite.Equipment.PopUpMenuPumpTables;
import View.Sites.NewSite.Equipment.PumpTableCellRenderer;
import View.Sites.NewSite.Equipment.PumpTableModel;
import View.Sites.EditSite.D_PlantInfo.EnhancementsTableCellRenderer;
import View.Sites.EditSite.D_PlantInfo.EnhancementsTableModel;
import View.Sites.EditSite.D_PlantInfo.old_PlantInfoTableCellRenderer;
import View.Sites.EditSite.D_PlantInfo.old_PlantInfoTableModel;
import View.Sites.NewSite.Address.AddressTableCellRenderer;
import View.Sites.NewSite.Address.AddressTableModel;
import View.CommonLibs.MapTableCellRenderer;
import View.CommonLibs.MapTableModel;
import View.CommonLibs.PopupMenuForMapTables;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class NewSiteDetailsFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private final OptiCxAPIController controller;
    private Site site;
    private String customerSid;

    public NewSiteDetailsFrame(OptiCxAPIController controller, String customerSid) {
        initComponents();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.controller = controller;
        this.customerSid = customerSid;

        site = new SitePlainTemplate(customerSid).getSite();

        this.jLabelCustomerSid.setText(customerSid);
        this.jTextFieldExtSFID.setText("");
        this.jTextFieldSiteName.setText("");
        this.jTextFieldCurrencyCode.setText("");

        this.jTextFieldEnabledAt.setText("");
        this.jTextFieldActivateAt.setText("");
        this.jTextFieldExpiresAt.setText("");

        this.fillFields(site);
        setVisible(true);
    }

    private void fillFields(Site site) {

        fillProductsDropdown();
        fillLicenseTypesDropdown();

        jTextFieldSFOpportunity.setText(site.getSFOppotunity());
        jTextFieldCurrencyCode.setText(site.getCurrencyCode() );

        fillSubscriptionTable(site);
        fillStationLicenseInfo(site);
        fillEnhancementsTable(site);
        fillAddressTable(site);
        fillContactsTable(site);
        fillPlantInfoTable(site);
        PlantEquipment equipment = site.getEquipment();
        fillProtocolsDropdown();
        fillChillerTable(equipment);
        fillCoolingTowerTable(equipment);
        fillCNDRWaterPumpTable(equipment);
        fillPrimaryCWPumpTable(equipment);
        fillSecondaryCWPumpTable(equipment);
        fillHeatExchangersTable(equipment);

    }

    private void setSiteFromFields() {

        site.setSid(jLabelCustomerSid.getText());
        site.setName(jTextFieldSiteName.getText());

        site.setProduct((String) this.jComboBoxProductTypes.getSelectedItem());

        site.setStationLicense(getStationLicenseFromFields());

        site.setSFOppotunity(jTextFieldSFOpportunity.getText());
        site.setExtSFID(jTextFieldExtSFID.getText());

        site.setProduct((String) this.jComboBoxProductTypes.getSelectedItem());
        site.getEquipment().setCommProtocol((String) this.jComboBoxProtocols.getSelectedItem());

    }

    public void fillLicenseTypesDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumLicenseTypes.getNames().toArray());
        this.jComboBoxLicenseType.setModel(comboBoxModel);
        this.jComboBoxLicenseType.setSelectedIndex(EnumLicenseTypes.subscription.getDropDownIndex());
        this.jComboBoxLicenseType.setEnabled(true);
    }

    public void fillProductsDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumProducts.getNames().toArray());
        this.jComboBoxProductTypes.setModel(comboBoxModel);
        this.jComboBoxProductTypes.setSelectedIndex(EnumProducts.edge.getDropDownIndex());
        this.jComboBoxProductTypes.setEnabled(true);
    }

    public void fillProtocolsDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumCommProtocols.getNames().toArray());
        this.jComboBoxProtocols.setModel(comboBoxModel);
        this.jComboBoxProtocols.setSelectedIndex(EnumCommProtocols.bacnet.getDropDownIndex());
        this.jComboBoxProtocols.setEnabled(true);
    }

    private void fillStationLicenseInfo(Site site) {
        if (site.getStationLicense() != null) {
            StationLicense lic = site.getStationLicense();
            String licTypeName = lic.getLicenseType();
            EnumLicenseTypes enumLicType = EnumLicenseTypes.getEnumFromName(licTypeName);
            this.jComboBoxLicenseType.setSelectedIndex(enumLicType.getDropDownIndex());
            this.jTextFieldEnabledAt.setText(lic.getSoftwareEnabledAt());
            this.jTextFieldActivateAt.setText(lic.getActivateAt());
            this.jTextFieldExpiresAt.setText(lic.getExpiresAt());

        } else {
            this.jComboBoxLicenseType.setSelectedIndex(EnumLicenseTypes.subscription.getDropDownIndex());
            this.jTextFieldEnabledAt.setText("");
            this.jTextFieldActivateAt.setText("");
            this.jTextFieldExpiresAt.setText("");

        }
    }

    private StationLicense getStationLicenseFromFields() {

        String licenseTypeName = (String) this.jComboBoxLicenseType.getSelectedItem();
        EnumLicenseTypes licenseType = EnumLicenseTypes.getEnumFromName(licenseTypeName);

        String enabledAt = this.jTextFieldEnabledAt.getText();
        String activatedAt = this.jTextFieldActivateAt.getText();
        String expiresAt = this.jTextFieldExpiresAt.getText();

        StationLicense sl = new StationLicense(licenseType.getName(), enabledAt, activatedAt, expiresAt);

        return sl;

    }

    private void fillSubscriptionTable(Site site) {
        if (site.getSubscription() == null) {
            site.setSubscription(new HashMap<>());
        }

        Map subscriptions = site.getSubscription();
        if (!subscriptions.containsKey("activatedAt")) {
            subscriptions.put("activatedAt", "2016-05-18T16:50:49.738Z");
        }
        if (!subscriptions.containsKey("expiresAt")) {
            subscriptions.put("expiresAt", "2016-05-18T16:50:49.738Z");
        }

        this.jTableSubscriptions.setModel(new MapTableModel(subscriptions));
        this.jTableSubscriptions.setDefaultRenderer(Object.class, new MapTableCellRenderer());
    }

    private void fillEnhancementsTable(Site site) {
        this.jTableEnhancements.setModel(new EnhancementsTableModel(site));
        this.jTableEnhancements.setDefaultRenderer(Object.class, new EnhancementsTableCellRenderer());
    }

    private void fillAddressTable(Site site) {
        if (site.getAddress() == null) {
            site.setAddress(new Address());
        }
        this.jTableAddress.setModel(new AddressTableModel(site.getAddress()));
        this.jTableAddress.setDefaultRenderer(Object.class, new AddressTableCellRenderer());
    }

    private void fillContactsTable(Site site) {
        jTableContacts.setModel(new ContactsTableModel(site));
        jTableContacts.setDefaultRenderer(Object.class, new ContactsTableCellRenderer());
    }

    private void fillPlantInfoTable(Site site) {
        if (site.getEdge() == null) {
            site.setEdge(new EdgePlantInfo());
        }
        jTablePlantInfo.setModel(new old_PlantInfoTableModel(site.getEdge()));
        jTablePlantInfo.setDefaultRenderer(Object.class, new old_PlantInfoTableCellRenderer());
    }

    private void fillChillerTable(PlantEquipment equipment) {
        if (equipment.getChillers() == null) {
            equipment.setChillers(new ArrayList<Chiller>());
        }
        jTableChillers.setModel(new ChillerTableModel(equipment.getChillers()));
        jTableChillers.setDefaultRenderer(Object.class, new ChillerTableCellRenderer());
    }

    private void fillCoolingTowerTable(PlantEquipment equipment) {
        jTableCoolingTowers.setModel(new PumpTableModel(EnumPumpTypes.CoolingTower, equipment));
        jTableCoolingTowers.setDefaultRenderer(Object.class, new PumpTableCellRenderer());
    }

    private void fillCNDRWaterPumpTable(PlantEquipment equipment) {
        jTableCNDRWaterPumps.setModel(new PumpTableModel(EnumPumpTypes.Condenser, equipment));
        jTableCNDRWaterPumps.setDefaultRenderer(Object.class, new PumpTableCellRenderer());
    }

    private void fillPrimaryCWPumpTable(PlantEquipment equipment) {
        jTablePrimaryCWPumps.setModel(new PumpTableModel(EnumPumpTypes.PrimaryChilledWater, equipment));
        jTablePrimaryCWPumps.setDefaultRenderer(Object.class, new PumpTableCellRenderer());
    }

    private void fillSecondaryCWPumpTable(PlantEquipment equipment) {
        jTableSecondaryCWPumps.setModel(new PumpTableModel(EnumPumpTypes.SecondaryChilledWater, equipment));
        jTableSecondaryCWPumps.setDefaultRenderer(Object.class, new PumpTableCellRenderer());
    }

    public void fillHeatExchangersTable(PlantEquipment equipment) {
        jTableHeatExchangersTable.setModel(new HeatExchangersTableModel(equipment));
        jTableHeatExchangersTable.setDefaultRenderer(Object.class, new HeatExchangersTableCellRenderer());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPaneSiteInfo = new javax.swing.JTabbedPane();
        jPanelSiteInfo = new javax.swing.JPanel();
        jPanelEnhancements = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableEnhancements = new javax.swing.JTable();
        jPanelSubscriptions = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSubscriptions = new javax.swing.JTable();
        jPanelLicense = new javax.swing.JPanel();
        jTextFieldExpiresAt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldEnabledAt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxLicenseType = new javax.swing.JComboBox<>();
        jTextFieldActivateAt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanelDetails = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldCurrencyCode = new javax.swing.JTextField();
        jTextFieldSFOpportunity = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxProductTypes = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanelContactInfo = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableAddress = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableContacts = new javax.swing.JTable();
        jPanelPlantInfo = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTablePlantInfo = new javax.swing.JTable();
        jPanelEquipmentInfo = new javax.swing.JPanel();
        jPanelChillers = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableChillers = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanelPrimaryChilledWaterPumps = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTablePrimaryCWPumps = new javax.swing.JTable();
        jPanelHeatExchangers = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTableHeatExchangersTable = new javax.swing.JTable();
        jPanelSecondaryChilledWaterPumps = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTableSecondaryCWPumps = new javax.swing.JTable();
        jPanelCondenserWaterPumps = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTableCNDRWaterPumps = new javax.swing.JTable();
        jPanelCoolingTowers = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableCoolingTowers = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jComboBoxProtocols = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jButtonUpdate = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonLoadTemplate = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelSiteOrCustomerLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldSiteName = new javax.swing.JTextField();
        jTextFieldExtSFID = new javax.swing.JTextField();
        jLabelCustomerSid = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Site Details");
        setMinimumSize(new java.awt.Dimension(1024, 768));

        jTabbedPaneSiteInfo.setPreferredSize(new java.awt.Dimension(750, 714));

        jPanelEnhancements.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Enhancements"));

        jTableEnhancements.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableEnhancements.setShowGrid(true);
        jScrollPane3.setViewportView(jTableEnhancements);

        javax.swing.GroupLayout jPanelEnhancementsLayout = new javax.swing.GroupLayout(jPanelEnhancements);
        jPanelEnhancements.setLayout(jPanelEnhancementsLayout);
        jPanelEnhancementsLayout.setHorizontalGroup(
            jPanelEnhancementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEnhancementsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanelEnhancementsLayout.setVerticalGroup(
            jPanelEnhancementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEnhancementsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelSubscriptions.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Subscriptions"));

        jTableSubscriptions.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableSubscriptions.setShowGrid(true);
        jTableSubscriptions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableSubscriptionsMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSubscriptions);

        javax.swing.GroupLayout jPanelSubscriptionsLayout = new javax.swing.GroupLayout(jPanelSubscriptions);
        jPanelSubscriptions.setLayout(jPanelSubscriptionsLayout);
        jPanelSubscriptionsLayout.setHorizontalGroup(
            jPanelSubscriptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSubscriptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanelSubscriptionsLayout.setVerticalGroup(
            jPanelSubscriptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSubscriptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelLicense.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "License"));

        jTextFieldExpiresAt.setText("jTextField3");

        jLabel3.setText("Enabled At:");

        jLabel5.setText("Activated At:");

        jTextFieldEnabledAt.setText("jTextField2");

        jLabel4.setText("Expires At:");

        jComboBoxLicenseType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextFieldActivateAt.setText("jTextField1");

        jLabel1.setText("LicenseType:");

        javax.swing.GroupLayout jPanelLicenseLayout = new javax.swing.GroupLayout(jPanelLicense);
        jPanelLicense.setLayout(jPanelLicenseLayout);
        jPanelLicenseLayout.setHorizontalGroup(
            jPanelLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLicenseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelLicenseLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldExpiresAt, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelLicenseLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxLicenseType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelLicenseLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldActivateAt)
                            .addComponent(jTextFieldEnabledAt, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLicenseLayout.setVerticalGroup(
            jPanelLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLicenseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxLicenseType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldEnabledAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldActivateAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldExpiresAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Misc"));

        jLabel7.setText("SF Opportunity: ");

        jTextFieldCurrencyCode.setText("jTextField1");

        jTextFieldSFOpportunity.setText("jTextField1");

        jLabel8.setText("Currency Code:");

        jComboBoxProductTypes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        jComboBoxProductTypes.setPreferredSize(new java.awt.Dimension(100, 27));
        jComboBoxProductTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProductTypesActionPerformed(evt);
            }
        });

        jLabel6.setText("Product Type:");

        javax.swing.GroupLayout jPanelDetailsLayout = new javax.swing.GroupLayout(jPanelDetails);
        jPanelDetails.setLayout(jPanelDetailsLayout);
        jPanelDetailsLayout.setHorizontalGroup(
            jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetailsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDetailsLayout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldCurrencyCode))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDetailsLayout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBoxProductTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelDetailsLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSFOpportunity, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanelDetailsLayout.setVerticalGroup(
            jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxProductTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCurrencyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldSFOpportunity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelSiteInfoLayout = new javax.swing.GroupLayout(jPanelSiteInfo);
        jPanelSiteInfo.setLayout(jPanelSiteInfoLayout);
        jPanelSiteInfoLayout.setHorizontalGroup(
            jPanelSiteInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSiteInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSiteInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSiteInfoLayout.createSequentialGroup()
                        .addComponent(jPanelDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelLicense, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanelSubscriptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelEnhancements, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelSiteInfoLayout.setVerticalGroup(
            jPanelSiteInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSiteInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSiteInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelLicense, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelEnhancements, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelSubscriptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneSiteInfo.addTab("Site Info", jPanelSiteInfo);

        jLabel9.setText("Address:");

        jTableAddress.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableAddress.setShowGrid(true);
        jTableAddress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableAddressMousePressed(evt);
            }
        });
        jScrollPane7.setViewportView(jTableAddress);

        jLabel11.setText("Contacts:");

        jTableContacts.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableContacts.setShowGrid(true);
        jTableContacts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableContactsMousePressed(evt);
            }
        });
        jScrollPane8.setViewportView(jTableContacts);

        javax.swing.GroupLayout jPanelContactInfoLayout = new javax.swing.GroupLayout(jPanelContactInfo);
        jPanelContactInfo.setLayout(jPanelContactInfoLayout);
        jPanelContactInfoLayout.setHorizontalGroup(
            jPanelContactInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContactInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelContactInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
                    .addGroup(jPanelContactInfoLayout.createSequentialGroup()
                        .addGroup(jPanelContactInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane8))
                .addContainerGap())
        );
        jPanelContactInfoLayout.setVerticalGroup(
            jPanelContactInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContactInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
        );

        jTabbedPaneSiteInfo.addTab("Contact Info", jPanelContactInfo);

        jTablePlantInfo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane13.setViewportView(jTablePlantInfo);

        javax.swing.GroupLayout jPanelPlantInfoLayout = new javax.swing.GroupLayout(jPanelPlantInfo);
        jPanelPlantInfo.setLayout(jPanelPlantInfoLayout);
        jPanelPlantInfoLayout.setHorizontalGroup(
            jPanelPlantInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPlantInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelPlantInfoLayout.setVerticalGroup(
            jPanelPlantInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPlantInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneSiteInfo.addTab("Plant Info", jPanelPlantInfo);

        jPanelChillers.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chillers"));

        jTableChillers.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableChillers.setShowGrid(true);
        jTableChillers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableChillersMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTableChillers);

        javax.swing.GroupLayout jPanelChillersLayout = new javax.swing.GroupLayout(jPanelChillers);
        jPanelChillers.setLayout(jPanelChillersLayout);
        jPanelChillersLayout.setHorizontalGroup(
            jPanelChillersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChillersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanelChillersLayout.setVerticalGroup(
            jPanelChillersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChillersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelPrimaryChilledWaterPumps.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Primary Chilled Water Pumps"));

        jTablePrimaryCWPumps.setModel(new javax.swing.table.DefaultTableModel(
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
        jTablePrimaryCWPumps.setShowGrid(true);
        jTablePrimaryCWPumps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTablePrimaryCWPumpsMousePressed(evt);
            }
        });
        jScrollPane9.setViewportView(jTablePrimaryCWPumps);

        javax.swing.GroupLayout jPanelPrimaryChilledWaterPumpsLayout = new javax.swing.GroupLayout(jPanelPrimaryChilledWaterPumps);
        jPanelPrimaryChilledWaterPumps.setLayout(jPanelPrimaryChilledWaterPumpsLayout);
        jPanelPrimaryChilledWaterPumpsLayout.setHorizontalGroup(
            jPanelPrimaryChilledWaterPumpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrimaryChilledWaterPumpsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelPrimaryChilledWaterPumpsLayout.setVerticalGroup(
            jPanelPrimaryChilledWaterPumpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrimaryChilledWaterPumpsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelHeatExchangers.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Heat Exchangers"));

        jTableHeatExchangersTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableHeatExchangersTable.setShowGrid(true);
        jTableHeatExchangersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableHeatExchangersTableMousePressed(evt);
            }
        });
        jScrollPane12.setViewportView(jTableHeatExchangersTable);

        javax.swing.GroupLayout jPanelHeatExchangersLayout = new javax.swing.GroupLayout(jPanelHeatExchangers);
        jPanelHeatExchangers.setLayout(jPanelHeatExchangersLayout);
        jPanelHeatExchangersLayout.setHorizontalGroup(
            jPanelHeatExchangersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeatExchangersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelHeatExchangersLayout.setVerticalGroup(
            jPanelHeatExchangersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeatExchangersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelSecondaryChilledWaterPumps.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Secondary Chilled Water Pumps"));

        jTableSecondaryCWPumps.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableSecondaryCWPumps.setShowGrid(true);
        jTableSecondaryCWPumps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableSecondaryCWPumpsMousePressed(evt);
            }
        });
        jScrollPane11.setViewportView(jTableSecondaryCWPumps);

        javax.swing.GroupLayout jPanelSecondaryChilledWaterPumpsLayout = new javax.swing.GroupLayout(jPanelSecondaryChilledWaterPumps);
        jPanelSecondaryChilledWaterPumps.setLayout(jPanelSecondaryChilledWaterPumpsLayout);
        jPanelSecondaryChilledWaterPumpsLayout.setHorizontalGroup(
            jPanelSecondaryChilledWaterPumpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanelSecondaryChilledWaterPumpsLayout.setVerticalGroup(
            jPanelSecondaryChilledWaterPumpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSecondaryChilledWaterPumpsLayout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanelCondenserWaterPumps.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "CondenserWaterPumps"));

        jTableCNDRWaterPumps.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCNDRWaterPumps.setShowGrid(true);
        jTableCNDRWaterPumps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableCNDRWaterPumpsMousePressed(evt);
            }
        });
        jScrollPane10.setViewportView(jTableCNDRWaterPumps);

        javax.swing.GroupLayout jPanelCondenserWaterPumpsLayout = new javax.swing.GroupLayout(jPanelCondenserWaterPumps);
        jPanelCondenserWaterPumps.setLayout(jPanelCondenserWaterPumpsLayout);
        jPanelCondenserWaterPumpsLayout.setHorizontalGroup(
            jPanelCondenserWaterPumpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCondenserWaterPumpsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelCondenserWaterPumpsLayout.setVerticalGroup(
            jPanelCondenserWaterPumpsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCondenserWaterPumpsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanelCoolingTowers.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Cooling Towers"));

        jTableCoolingTowers.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCoolingTowers.setCellSelectionEnabled(true);
        jTableCoolingTowers.setShowGrid(true);
        jTableCoolingTowers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableCoolingTowersMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCoolingTowersMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableCoolingTowers);

        javax.swing.GroupLayout jPanelCoolingTowersLayout = new javax.swing.GroupLayout(jPanelCoolingTowers);
        jPanelCoolingTowers.setLayout(jPanelCoolingTowersLayout);
        jPanelCoolingTowersLayout.setHorizontalGroup(
            jPanelCoolingTowersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCoolingTowersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelCoolingTowersLayout.setVerticalGroup(
            jPanelCoolingTowersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCoolingTowersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelHeatExchangers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanelCoolingTowers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelCondenserWaterPumps, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanelPrimaryChilledWaterPumps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelSecondaryChilledWaterPumps, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCondenserWaterPumps, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelCoolingTowers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelPrimaryChilledWaterPumps, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelSecondaryChilledWaterPumps, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelHeatExchangers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel17.setText("Communication Protocol:");

        jComboBoxProtocols.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxProtocols.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProtocolsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxProtocols, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jComboBoxProtocols, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelEquipmentInfoLayout = new javax.swing.GroupLayout(jPanelEquipmentInfo);
        jPanelEquipmentInfo.setLayout(jPanelEquipmentInfoLayout);
        jPanelEquipmentInfoLayout.setHorizontalGroup(
            jPanelEquipmentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEquipmentInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEquipmentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEquipmentInfoLayout.createSequentialGroup()
                        .addGroup(jPanelEquipmentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanelChillers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12))
                    .addGroup(jPanelEquipmentInfoLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanelEquipmentInfoLayout.setVerticalGroup(
            jPanelEquipmentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEquipmentInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelChillers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPaneSiteInfo.addTab("Equipment Info", jPanelEquipmentInfo);

        jButtonUpdate.setText("Add Site");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonLoadTemplate.setText("Load Template");
        jButtonLoadTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadTemplateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonLoadTemplate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCancel)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLoadTemplate)
                    .addComponent(jButtonUpdate)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        jLabelSiteOrCustomerLabel.setText("Customer Sid:");

        jLabel13.setText("External Sales Force ID:");

        jTextFieldSiteName.setText("jTextField2");

        jTextFieldExtSFID.setText("jTextField2");

        jLabelCustomerSid.setText("*customerSid*");

        jLabel2.setText("Site Name:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldExtSFID, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSiteOrCustomerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCustomerSid)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldExtSFID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabelSiteOrCustomerLabel)
                    .addComponent(jLabelCustomerSid))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPaneSiteInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneSiteInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        controller.removePropChangeListener(this);
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jTableContactsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableContactsMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuOnTables popup = new PopupMenuOnTables(evt, jTableContacts);
        }
    }//GEN-LAST:event_jTableContactsMousePressed

    private void jTableSubscriptionsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSubscriptionsMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForMapTables popup = new PopupMenuForMapTables(evt, jTableSubscriptions);
        }
    }//GEN-LAST:event_jTableSubscriptionsMousePressed

    private void jTableAddressMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAddressMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForMapTables popup = new PopupMenuForMapTables(evt, jTableAddress);
        }
    }//GEN-LAST:event_jTableAddressMousePressed

    private void jTablePrimaryCWPumpsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePrimaryCWPumpsMousePressed
        if (evt.isPopupTrigger()) {
            PopUpMenuPumpTables popup = new PopUpMenuPumpTables(evt, jTablePrimaryCWPumps);
        }
    }//GEN-LAST:event_jTablePrimaryCWPumpsMousePressed

    private void jTableSecondaryCWPumpsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSecondaryCWPumpsMousePressed
        if (evt.isPopupTrigger()) {
            PopUpMenuPumpTables popup = new PopUpMenuPumpTables(evt, jTableSecondaryCWPumps);
        }
    }//GEN-LAST:event_jTableSecondaryCWPumpsMousePressed

    private void jTableChillersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableChillersMousePressed
        if (evt.isPopupTrigger()) {
            PopUpMenuChillerTable popup = new PopUpMenuChillerTable(evt, jTableChillers);
        }
    }//GEN-LAST:event_jTableChillersMousePressed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed

        setSiteFromFields();
        CreateSiteRequest csr = new CreateSiteRequest(site);
        controller.createSite(csr);
        controller.removePropChangeListener(this);
        dispose();

    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jTableHeatExchangersTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableHeatExchangersTableMousePressed
        if (evt.isPopupTrigger()) {
            PopUpMenuHeatExchangers popup = new PopUpMenuHeatExchangers(evt, jTableHeatExchangersTable);
        }
    }//GEN-LAST:event_jTableHeatExchangersTableMousePressed

    private void jComboBoxProductTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProductTypesActionPerformed

    }//GEN-LAST:event_jComboBoxProductTypesActionPerformed

    private void jComboBoxProtocolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProtocolsActionPerformed

    }//GEN-LAST:event_jComboBoxProtocolsActionPerformed

    private void jButtonLoadTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadTemplateActionPerformed
        if (this.jTextFieldSiteName != null && this.jTextFieldSiteName.getText().length() > 0 ) {
            this.site = (new SiteTemplate(this.customerSid, this.jTextFieldExtSFID.getText(),
                    this.jTextFieldSiteName.getText())).getSite();
            this.fillFields(site);
        }
    }//GEN-LAST:event_jButtonLoadTemplateActionPerformed

    private void jTableCNDRWaterPumpsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCNDRWaterPumpsMousePressed
        if (evt.isPopupTrigger()) {
            PopUpMenuPumpTables popup = new PopUpMenuPumpTables(evt, jTableCNDRWaterPumps);
        }
    }//GEN-LAST:event_jTableCNDRWaterPumpsMousePressed

    private void jTableCoolingTowersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCoolingTowersMouseClicked

    }//GEN-LAST:event_jTableCoolingTowersMouseClicked

    private void jTableCoolingTowersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCoolingTowersMousePressed
        if (evt.isPopupTrigger()) {
            PopUpMenuPumpTables popup = new PopUpMenuPumpTables(evt, jTableCoolingTowers);
        }
    }//GEN-LAST:event_jTableCoolingTowersMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonLoadTemplate;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JComboBox<String> jComboBoxLicenseType;
    private javax.swing.JComboBox<String> jComboBoxProductTypes;
    private javax.swing.JComboBox<String> jComboBoxProtocols;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCustomerSid;
    private javax.swing.JLabel jLabelSiteOrCustomerLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelChillers;
    private javax.swing.JPanel jPanelCondenserWaterPumps;
    private javax.swing.JPanel jPanelContactInfo;
    private javax.swing.JPanel jPanelCoolingTowers;
    private javax.swing.JPanel jPanelDetails;
    private javax.swing.JPanel jPanelEnhancements;
    private javax.swing.JPanel jPanelEquipmentInfo;
    private javax.swing.JPanel jPanelHeatExchangers;
    private javax.swing.JPanel jPanelLicense;
    private javax.swing.JPanel jPanelPlantInfo;
    private javax.swing.JPanel jPanelPrimaryChilledWaterPumps;
    private javax.swing.JPanel jPanelSecondaryChilledWaterPumps;
    private javax.swing.JPanel jPanelSiteInfo;
    private javax.swing.JPanel jPanelSubscriptions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPaneSiteInfo;
    private javax.swing.JTable jTableAddress;
    private javax.swing.JTable jTableCNDRWaterPumps;
    private javax.swing.JTable jTableChillers;
    private javax.swing.JTable jTableContacts;
    private javax.swing.JTable jTableCoolingTowers;
    private javax.swing.JTable jTableEnhancements;
    private javax.swing.JTable jTableHeatExchangersTable;
    private javax.swing.JTable jTablePlantInfo;
    private javax.swing.JTable jTablePrimaryCWPumps;
    private javax.swing.JTable jTableSecondaryCWPumps;
    private javax.swing.JTable jTableSubscriptions;
    private javax.swing.JTextField jTextFieldActivateAt;
    private javax.swing.JTextField jTextFieldCurrencyCode;
    private javax.swing.JTextField jTextFieldEnabledAt;
    private javax.swing.JTextField jTextFieldExpiresAt;
    private javax.swing.JTextField jTextFieldExtSFID;
    private javax.swing.JTextField jTextFieldSFOpportunity;
    private javax.swing.JTextField jTextFieldSiteName;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            controller.removePropChangeListener(this);
            this.dispose();
        }
    }
}
