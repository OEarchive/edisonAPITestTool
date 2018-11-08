package View.Sites.EditSite.A_History.DataGeneratorXLS;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.simulator.CalcPointsXLSReader.UICalcPointXLSReader;
import Model.PropertyChangeNames;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.TableColumn;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;

public class DataGeneratorXLSFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private final String xlsFileName = "UICalcPointHistory.xlsx";

    private static DataGeneratorXLSFrame thisInstance;
    private final OptiCxAPIController controller;
    private final String sid;
    private final Map<String, DatapointMetadata> nameToMetadataMap;

    private List<String> timeStamps;
    private Map<String, List<Object>> pointsAndValues;

    private Timer lapsedTimeTimer;
    private ActionListener lapsedTimeUpdater;
    private DateTime historyPushTimerStart;

    public static DataGeneratorXLSFrame getInstance(
            final OptiCxAPIController controller,
            String sid,
            Map<String, DatapointMetadata> nameToMetadataMap
    ) {
        if (thisInstance == null) {
            thisInstance = new DataGeneratorXLSFrame(
                    controller,
                    sid, nameToMetadataMap);
        }
        return thisInstance;
    }

    private DataGeneratorXLSFrame(
            final OptiCxAPIController controller,
            String sid,
            Map<String, DatapointMetadata> nameToMetadataMap) {
        initComponents();

        this.controller = controller;
        this.sid = sid;
        this.nameToMetadataMap = nameToMetadataMap;

        lapsedTimeUpdater = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                DateTime historyPusTimerEnd = DateTime.now();
                Period period = new Period(historyPushTimerStart, historyPusTimerEnd);
                String lapsedTimeString = String.format("%03d %02d:%02d:%02d", period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds());
                jLabelLapsedTime.setText(lapsedTimeString);
                lapsedTimeTimer.restart();

            }
        };

        UICalcPointXLSReader xlsReader = new UICalcPointXLSReader(xlsFileName);

        timeStamps = xlsReader.getTimestamps();
        pointsAndValues = xlsReader.getPointsAndValues();

        this.jLabelSid.setText(sid);

        this.jTableCalcPointValues.setModel(new DataGeneratorXLSTableModel(timeStamps, pointsAndValues));
        this.jTableCalcPointValues.setDefaultRenderer(Object.class, new DataGeneratorXLSTableCellRenderer());
        this.jTableCalcPointValues.setAutoCreateRowSorter(true);
        fixColumnWidths(jTableCalcPointValues);

    }

    @Override
    public void dispose() {

        if (lapsedTimeTimer != null) {
            lapsedTimeTimer.stop();
            lapsedTimeTimer = null;
        }

        controller.removePropChangeListener(this);
        thisInstance = null;
        super.dispose();
    }

    private void fixColumnWidths(JTable t) {

        TableColumn column = null;

        for (int i = 0; i < t.getColumnCount(); i++) {

            column = t.getColumnModel().getColumn(i);

            if (i == 0) {
                column.setPreferredWidth(200);
            } else {
                column.setPreferredWidth(100);
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButtonMakeHistory = new javax.swing.JButton();
        jProgressBar = new javax.swing.JProgressBar();
        jButtonClose = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCalcPointValues = new javax.swing.JTable();
        jLabelSid = new javax.swing.JLabel();
        jLabelLapsedTime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Push Calc Point Values");

        jLabel1.setText("Sid:");

        jButtonMakeHistory.setText("Make History");
        jButtonMakeHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMakeHistoryActionPerformed(evt);
            }
        });

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Calculated Point Values"));

        jTableCalcPointValues.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCalcPointValues.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTableCalcPointValues);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLabelSid.setText("*sid*");

        jLabelLapsedTime.setText("000 00:00:00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonMakeHistory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelLapsedTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClose))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSid)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelSid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonMakeHistory, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonClose)
                        .addComponent(jLabelLapsedTime)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonMakeHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMakeHistoryActionPerformed
        if (this.jTableCalcPointValues != null) {

            jProgressBar.setMaximum(timeStamps.size());
            jProgressBar.setValue(0);
            jProgressBar.setStringPainted(true);

            this.jButtonMakeHistory.setEnabled(false);

            historyPushTimerStart = DateTime.now();

            lapsedTimeTimer = new Timer(1000, lapsedTimeUpdater);
            lapsedTimeTimer.start();

            /*
            controller.putHistoryFromXLS(
                    sid,
                    timeStamps,
                    pointsAndValues,
                    nameToMetadataMap);
            */

        }
    }//GEN-LAST:event_jButtonMakeHistoryActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonMakeHistory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelLapsedTime;
    private javax.swing.JLabel jLabelSid;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableCalcPointValues;
    // End of variables declaration//GEN-END:variables

  
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.DatapointsCalcRowPushed.getName())) {
            int count = jProgressBar.getValue();
            count++;
            jProgressBar.setValue(Math.min(count, jProgressBar.getMaximum()));

        } else if (propName.equals(PropertyChangeNames.DatapointsCalcValuesPushed.getName())) {
            jProgressBar.setBackground(Color.GREEN);
            jProgressBar.invalidate();
            jProgressBar.repaint();

            lapsedTimeTimer.stop();

            DateTime historyPusTimerEnd = DateTime.now();
            Period period = new Period(historyPushTimerStart, historyPusTimerEnd);
            System.out.println("lapsed time: " + PeriodFormat.getDefault().print(period));
            System.out.println(String.format("%02d:%02d:%02d", period.getHours(), period.getMinutes(), period.getSeconds()));

            String lapsedTimeString = String.format("%03d %02d:%02d:%02d", period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds());
            jLabelLapsedTime.setText(lapsedTimeString);

            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    String.format("Lapsed time: %03d days %02d:%02d:%02d", period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds()),
                    "Done!",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            //this.dispose();
            jProgressBar.setValue(0);
            jProgressBar.setStringPainted(true);
            
        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }
}
