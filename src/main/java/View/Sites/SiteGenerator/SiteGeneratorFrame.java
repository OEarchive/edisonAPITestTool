package View.Sites.SiteGenerator;

import Controller.OptiCxAPIController;
import Model.DataModels.Sites.CreateSiteRequest;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.TestSiteTemplates.EnumTestSites;
import Model.DataModels.Sites.TestSiteTemplates.SpecificSites.Carnot.CarnotFour_SiteFour_Template;
import Model.DataModels.Sites.TestSiteTemplates.SpecificSites.Carnot.CarnotFour_SiteOne_Template;
import Model.DataModels.Sites.TestSiteTemplates.SpecificSites.Carnot.CarnotFour_SiteThree_Template;
import Model.DataModels.Sites.TestSiteTemplates.SpecificSites.Carnot.CarnotFour_SiteTwo_Template;
import Model.DataModels.Sites.TestSiteTemplates.SpecificSites.CullenOne.Cullen_One_SiteTemplate;
import Model.DataModels.Sites.TestSiteTemplates.SpecificSites.Perkins.Perkins_SiteOne_Template;
import Model.DataModels.Sites.TestSiteTemplates.SpecificSites.Perkins.Perkins_SiteThree_Template;
import Model.DataModels.Sites.TestSiteTemplates.SpecificSites.Perkins.Perkins_SiteTwo_Template;
import Model.PropertyChangeNames;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class SiteGeneratorFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static SiteGeneratorFrame thisInstance;
    final OptiCxAPIController controller;

    public static SiteGeneratorFrame getInstance(final OptiCxAPIController controller) {
        if (thisInstance == null) {
            thisInstance = new SiteGeneratorFrame(controller);
        }
        return thisInstance;
    }

    private SiteGeneratorFrame(final OptiCxAPIController controller) {
        initComponents();
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        this.controller = controller;

        this.jTextFieldCustomerSid.setText("");
        this.jTextFieldSiteName.setText("");
        this.jTextFieldExtSFId.setText("");
        fillHistoryResolutionDropdown();
    }

    private void fillHistoryResolutionDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumTestSites.getTemplateNames().toArray());
        EnumTestSites siteTemplateType = EnumTestSites.Cullen;
        this.jComboBoxSiteTypes.setModel(comboBoxModel);
        this.jComboBoxSiteTypes.setSelectedIndex(siteTemplateType.getDropDownIndex());
        this.jComboBoxSiteTypes.setEnabled(true);
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
        jTextFieldCustomerSid = new javax.swing.JTextField();
        jComboBoxSiteTypes = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jButtonGenSite = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldSiteName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldExtSFId = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Site Generator");

        jLabel1.setText("Customer Sid: ");

        jTextFieldCustomerSid.setText("jTextField1");

        jComboBoxSiteTypes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Site Type:");

        jButtonGenSite.setText("Gen Site");
        jButtonGenSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenSiteActionPerformed(evt);
            }
        });

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jLabel3.setText("Site Name:");

        jTextFieldSiteName.setText("jTextField1");

        jLabel4.setText("External SF Id: ");

        jTextFieldExtSFId.setText("jTextField2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonGenSite)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClose))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSiteName))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxSiteTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldCustomerSid, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldExtSFId, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 80, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCustomerSid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldExtSFId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxSiteTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGenSite)
                    .addComponent(jButtonClose))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGenSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenSiteActionPerformed

        Site site = null;
        String custSid = jTextFieldCustomerSid.getText();
        String siteName = jTextFieldSiteName.getText();
        String extSfId = jTextFieldExtSFId.getText();

        if (custSid.length() > 0 && siteName.length() > 0) {
            String templateName = (String) jComboBoxSiteTypes.getSelectedItem();
            EnumTestSites templateType = EnumTestSites.getEnumFromName(templateName);

            switch (templateType) {
                case Cullen:
                    site = new Cullen_One_SiteTemplate(custSid, extSfId, siteName).getSiteTemplate();
                    break;
                case Carnot_1:
                    site = new CarnotFour_SiteOne_Template(custSid, extSfId, siteName).getSiteTemplate();
                    break;
                case Carnot_2:
                    site = new CarnotFour_SiteTwo_Template(custSid, extSfId, siteName).getSiteTemplate();
                    break;
                case Carnot_3:
                    site = new CarnotFour_SiteThree_Template(custSid, extSfId, siteName).getSiteTemplate();
                    break;
                case Carnot_4:
                    site = new CarnotFour_SiteFour_Template(custSid, extSfId, siteName).getSiteTemplate();
                    break;
                case Perkins_1:
                    site = new Perkins_SiteOne_Template(custSid, extSfId, siteName).getSiteTemplate();
                    break;
                case Perkins_2:
                    site = new Perkins_SiteTwo_Template(custSid, extSfId, siteName).getSiteTemplate();
                    break;
                case Perkins_3:
                    site = new Perkins_SiteThree_Template(custSid, extSfId, siteName).getSiteTemplate();
                    break;
            }
        }

        CreateSiteRequest csr = new CreateSiteRequest(site);
        controller.createSite(csr);

    }//GEN-LAST:event_jButtonGenSiteActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonGenSite;
    private javax.swing.JComboBox jComboBoxSiteTypes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextFieldCustomerSid;
    private javax.swing.JTextField jTextFieldExtSFId;
    private javax.swing.JTextField jTextFieldSiteName;
    // End of variables declaration//GEN-END:variables
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.SiteCreated.getName())) {
            Site newSite = (Site) evt.getNewValue();
            
            String templateName = (String) jComboBoxSiteTypes.getSelectedItem();
            EnumTestSites templateType = EnumTestSites.getEnumFromName(templateName);
            
            controller.createSiteUsersAndOrSetRoles(templateType, newSite);
            System.out.println("Site Created");

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }
}
