package View.Wizard;

import Controller.OptiCxAPIController;
import Model.DataModels.Wizard.WizardStatusResponse;
import Model.PropertyChangeNames;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class WizardFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private final OptiCxAPIController controller;
    public WizardFrame( OptiCxAPIController controller ) {
        initComponents();
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        this.controller = controller;
        
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabelCompileStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Get Compile Status");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabelCompileStatus.setText("*compileStatus*");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCompileStatus)
                .addContainerGap(123, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabelCompileStatus))
                .addContainerGap(265, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        controller.getCompileStatus();
    }//GEN-LAST:event_jButton1ActionPerformed

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();
        
        /*
    WizardCloudActivationStatusRetunred("WizardCloudActivationStatusRetunred"),
    WizardActivatePosted("WizardActivatePosted"),
    WizardAuthenticationReturned("WizardAuthenticationReturned"),
    WizardAuthenticationPosted("WizardAuthenticationPosted"),
    WizardConnectionStatusReturned("WizardConnectionStatusReturned"),
    WizardConnectionPinged("WizardConnectionPinged"),
    WizardConfigFilesStatusReturned("WizardConfigFilesStatusReturned"),
    WizardConfigFilesDownloadStarted("WizardConfigFilesDownloadStarted"),
    WizardBogFileStatusReturned("WizardBogFileStatusReturned"),
    WizardBogFileDownloadStarted("WizardBogFileDownloadStarted"),
    WizardOptimizationStatusReturned("WizardOptimizationStatusReturned"),
        */

        if (propName.equals(PropertyChangeNames.WizardCloudActivationStatusRetunred.getName())) {
            WizardStatusResponse wizardStatus  = (WizardStatusResponse) evt.getNewValue();

            this.jLabelCompileStatus.setText(wizardStatus.getStatusName());
        }

        if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabelCompileStatus;
    // End of variables declaration//GEN-END:variables
}
