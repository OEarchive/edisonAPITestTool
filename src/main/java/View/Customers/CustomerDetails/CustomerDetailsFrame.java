package View.Customers.CustomerDetails;

import Controller.OptiCxAPIController;
import Model.DataModels.Customers.Customer;
import Model.PropertyChangeNames;
import View.Users.AddOrUpdateEnum;
import View.CommonLibs.MapTableCellRenderer;
import View.CommonLibs.MapTableModel;
import View.CommonLibs.PopupMenuForMapTables;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class CustomerDetailsFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private final OptiCxAPIController controller;
    private Customer customer;
    private AddOrUpdateEnum addOrUpdate;

    public CustomerDetailsFrame(OptiCxAPIController controller, Customer customer, AddOrUpdateEnum addOrUpdate) {
        initComponents();

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.controller = controller;
        this.customer = customer;
        this.addOrUpdate = addOrUpdate;

        fillFields(customer);

        if (addOrUpdate == AddOrUpdateEnum.ADD) {
            this.jButtonUpdateCustomer.setText("Add Customer");
        } else {
            this.jButtonUpdateCustomer.setText("Update Customer");
        }
    }

    private void fillFields(Customer customer) {
        jLabelSid.setText(customer.getSid());
        jTextFieldName.setText(customer.getCustomerName());
        jTextFieldExtSFID.setText(customer.getExtSfId());
        fillAddressTable(customer);
    }

    private void fillAddressTable(Customer customer) {

        if (customer.getAddress() == null) {
            customer.setAddress(new HashMap<>());
        }

        String[] addressFields = {"street", "city", "state", "country", "postCode"};
        for (String f : addressFields) {
            Map addressMap = customer.getAddress();
            if (!addressMap.containsKey(f)) {
                addressMap.put(f, null);
            }
        }

        jTableAddress.setDefaultRenderer(Object.class, new MapTableCellRenderer());
        jTableAddress.setModel(new MapTableModel(customer.getAddress()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabelSid = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldExtSFID = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAddress = new javax.swing.JTable();
        jButtonCancel = new javax.swing.JButton();
        jButtonUpdateCustomer = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Customer Details");

        jLabel1.setText("Sid:");

        jLabelSid.setText("*sid*");

        jLabel3.setText("Name:");

        jTextFieldName.setText("jTextField1");

        jLabel4.setText("Ext SF ID:");

        jTextFieldExtSFID.setText("jTextField2");

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
        jTableAddress.setCellSelectionEnabled(true);
        jTableAddress.setShowGrid(true);
        jTableAddress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableAddressMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAddressMouseClicked(evt);
            }
        });
        jTableAddress.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTableAddressInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        jTableAddress.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTableAddressPropertyChange(evt);
            }
        });
        jTableAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableAddressKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableAddress);

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonUpdateCustomer.setText("Update");
        jButtonUpdateCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateCustomerActionPerformed(evt);
            }
        });

        jLabel2.setText("Address:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 208, Short.MAX_VALUE)
                        .addComponent(jButtonUpdateCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldName))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldExtSFID))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelSid)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelSid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldExtSFID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonUpdateCustomer))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonUpdateCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateCustomerActionPerformed

        customer.setCustomerName(jTextFieldName.getText());
        customer.setExtSfId(jTextFieldExtSFID.getText());

        if (addOrUpdate == AddOrUpdateEnum.UPDATE) {
            controller.updateCustomer(customer);
        } else {
            controller.addCustomer(customer);
        }
        
        controller.removePropChangeListener(this);
        this.dispose();
    }//GEN-LAST:event_jButtonUpdateCustomerActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        controller.removePropChangeListener(this);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jTableAddressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAddressMouseClicked

    }//GEN-LAST:event_jTableAddressMouseClicked

    private void jTableAddressInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTableAddressInputMethodTextChanged

    }//GEN-LAST:event_jTableAddressInputMethodTextChanged

    private void jTableAddressPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTableAddressPropertyChange

    }//GEN-LAST:event_jTableAddressPropertyChange

    private void jTableAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableAddressKeyPressed

    }//GEN-LAST:event_jTableAddressKeyPressed

    private void jTableAddressMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAddressMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForMapTables popup = new PopupMenuForMapTables(evt, jTableAddress);
        }
    }//GEN-LAST:event_jTableAddressMousePressed

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            controller.removePropChangeListener(this);
            this.dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonUpdateCustomer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelSid;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAddress;
    private javax.swing.JTextField jTextFieldExtSFID;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
