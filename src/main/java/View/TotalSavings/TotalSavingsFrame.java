package View.TotalSavings;

import Controller.OptiCxAPIController;
import Model.DataModels.TotalSavings.TotalSavings;
import Model.PropertyChangeNames;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TotalSavingsFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private final OptiCxAPIController controller;

    public TotalSavingsFrame(OptiCxAPIController controller) {
        initComponents();

        this.controller = controller;
    }

    public void fillTree(TotalSavings totalSavings) {

        this.jTableTotalSavings.setDefaultRenderer(Object.class, new TotalSavingsTableCellRenderer(totalSavings));
        this.jTableTotalSavings.setModel(new TotalSavingsTableModel(totalSavings));

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.TotalSavingsReturned.getName())) {

            TotalSavings savings = (TotalSavings) evt.getNewValue();
            fillTree(savings);
        }

        if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            controller.removePropChangeListener(this);
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OK = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableTotalSavings = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Total Savings");

        OK.setText("OK");
        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKActionPerformed(evt);
            }
        });

        jTableTotalSavings.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableTotalSavings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableTotalSavingsMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableTotalSavingsMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableTotalSavings);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(348, Short.MAX_VALUE)
                .addComponent(OK))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(OK)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKActionPerformed
        controller.removePropChangeListener(this);
        this.dispose();
    }//GEN-LAST:event_OKActionPerformed

    private void jTableTotalSavingsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTotalSavingsMousePressed
        if (evt.isPopupTrigger()) {
            PopUpMenuCopyTotalSavingsTableToClipboard popup = new PopUpMenuCopyTotalSavingsTableToClipboard(evt, jTableTotalSavings);
        }
    }//GEN-LAST:event_jTableTotalSavingsMousePressed

    private void jTableTotalSavingsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTotalSavingsMouseReleased
        if (evt.isPopupTrigger()) {
            PopUpMenuCopyTotalSavingsTableToClipboard popup = new PopUpMenuCopyTotalSavingsTableToClipboard(evt, jTableTotalSavings);
        }
    }//GEN-LAST:event_jTableTotalSavingsMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OK;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableTotalSavings;
    // End of variables declaration//GEN-END:variables

}
