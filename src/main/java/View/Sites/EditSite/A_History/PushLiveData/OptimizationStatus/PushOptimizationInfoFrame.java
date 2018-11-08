package View.Sites.EditSite.A_History.PushLiveData.OptimizationStatus;

import Controller.OptiCxAPIController;
import Model.PropertyChangeNames;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PushOptimizationInfoFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static PushOptimizationInfoFrame thisInstance;
    private final OptiCxAPIController controller;

    public static PushOptimizationInfoFrame getInstance(OptiCxAPIController controller) {

        if (thisInstance == null) {
            thisInstance = new PushOptimizationInfoFrame(controller);
        }

        return thisInstance;
    }

    private PushOptimizationInfoFrame(final OptiCxAPIController controller) {
        initComponents();

        this.controller = controller;
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
        jComboBoxChillers = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabelSiteSid = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Chiller:");

        jComboBoxChillers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Site:");

        jLabelSiteSid.setText("*siteSid*");

        jLabel2.setText("Color Status:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSiteSid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 212, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxChillers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxChillers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelSiteSid)
                    .addComponent(jLabel3))
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addContainerGap(459, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBoxChillers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelSiteSid;
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
