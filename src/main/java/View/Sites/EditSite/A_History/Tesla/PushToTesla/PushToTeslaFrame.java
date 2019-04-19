package View.Sites.EditSite.A_History.Tesla.PushToTesla;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.TeslaModels.EnumTeslaBaseURLs;
import Model.DataModels.TeslaModels.EnumTeslaUsers;
import Model.DataModels.TeslaModels.TeslaStationInfo;
import Model.PropertyChangeNames;
import View.Sites.EditSite.A_History.Tesla.PushToTesla.MappingTable.DataPointsTableCellRenderer;
import View.Sites.EditSite.A_History.Tesla.PushToTesla.MappingTable.DataPointsTableModel;
import Model.DataModels.TeslaModels.MappingTableRow;
import Model.DataModels.TeslaModels.TeslaDPServiceDatapoint;
import View.Sites.EditSite.A_History.Tesla.PushToTesla.MappingTable.EnumMapStatus;
import View.Sites.EditSite.A_History.Tesla.PushToTesla.MappingTable.PopupMenuForDataPointsTable;
import View.Sites.EditSite.A_History.Tesla.PushToTesla.SparsePointPusher.SparsePointPusherFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.TableColumn;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;

public final class PushToTeslaFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static PushToTeslaFrame thisInstance;
    private final OptiCxAPIController controller;

    private EnumTeslaBaseURLs selectedBaseURL;
    private EnumTeslaUsers selectedUser;

    private final List<DatapointsAndMetadataResponse> edisonPoints;
    private List<TeslaDPServiceDatapoint> listOfStationDatapoints;
    private String selectedSid = null;
    private boolean showAllTesla = false;
    private boolean ignoreGarbage = true;

    private String filter = "";

    //private final String edisonQuerySid;
    private Timer lapsedTimeTimer;
    private ActionListener lapsedTimeUpdater;
    private DateTime teslaPushTimerStartTime;
    private int completedBatches = 0;
    private int totalBatchesToPush = 0;

    private DateTimeFormatter zzFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");

    public static PushToTeslaFrame getInstance(
            final OptiCxAPIController controller,
            DateTime startDate,
            DateTime endDate,
            List<DatapointsAndMetadataResponse> edisonPoints) {

        if (thisInstance == null) {
            thisInstance = new PushToTeslaFrame(
                    controller,
                    startDate,
                    endDate,
                    edisonPoints);
        }
        return thisInstance;

    }

    private PushToTeslaFrame(final OptiCxAPIController controller,
            DateTime startDate,
            DateTime endDate,
            List<DatapointsAndMetadataResponse> edisonPoints) {
        initComponents();

        this.controller = controller;
        this.edisonPoints = edisonPoints;
        listOfStationDatapoints = new ArrayList<>();

        //DateTime pushEndTime = DateTime.now().withZone(DateTimeZone.UTC);
        //pushEndTime = pushEndTime.minusMillis(pushEndTime.getMillisOfDay());
        //DateTime pushStartTime = pushEndTime.plusMonths(-1);
        this.jTextFieldEdisonFilter.setText("");
        this.filter = "";

        endDate = DateTime.now();
        endDate = endDate.minusMillis(endDate.getMillisOfDay());
        startDate = endDate.minusMonths(12);

        this.jTextFieldStartDate.setText(startDate.toString(zzFormat));
        this.jTextFieldEndDate.setText(endDate.toString(zzFormat));

        this.jTextFieldMaxNumHoursPerPush.setText("12");
        this.jTextFieldMaxNumPointsPerPush.setText("20");

        selectedBaseURL = EnumTeslaBaseURLs.Prod;
        selectedUser = EnumTeslaUsers.ProdUser;
        fillTeslasHostsDropdown();
        fillUsersDropdown();

        createAllTeslaCheckBoxListener();
        createIgnoreGarbageCheckBoxListener();
        fillEdisonSidsDropdown();

        lapsedTimeUpdater = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                DateTime tempTeslaPushTimerEndTime = DateTime.now();
                Period period = new Period(teslaPushTimerStartTime, tempTeslaPushTimerEndTime);
                String lapsedTimeString = String.format("%03d %02d:%02d:%02d", period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds());
                jLabelLapsedTime.setText(lapsedTimeString);
                lapsedTimeTimer.restart();
            }
        };

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

    public void fillTeslasHostsDropdown() {

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        this.jComboBoxTeslaHosts.setModel(comboBoxModel);

        for (String url : EnumTeslaBaseURLs.getURLs()) {
            jComboBoxTeslaHosts.addItem(url);
        }
        jComboBoxTeslaHosts.setSelectedItem(this.selectedBaseURL.getURL());

    }

    public void fillUsersDropdown() {

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumTeslaUsers.getUsernames().toArray());
        this.jComboBoxTeslaUsers.setModel(comboBoxModel);
        this.jComboBoxTeslaUsers.setSelectedItem(selectedUser.name());
    }

    private void initTeslaModel(EnumTeslaBaseURLs baseURL, EnumTeslaUsers selectedUser) {
        controller.teslaLogin(baseURL, selectedUser);
    }

    public void clearTeslaSitesDropdown() {
        for (ActionListener oldListener : jComboBoxTeslaStations.getActionListeners()) {
            this.jComboBoxTeslaStations.removeActionListener(oldListener);
        }
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        this.jComboBoxTeslaStations.setModel(comboBoxModel);

    }

    public void fillTeslasStationsDropdown(final List<TeslaStationInfo> stations) {

        for (ActionListener oldListener : jComboBoxTeslaStations.getActionListeners()) {
            this.jComboBoxTeslaStations.removeActionListener(oldListener);
        }
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        this.jComboBoxTeslaStations.setModel(comboBoxModel);
        final Map< String, String> stationNameToStationIdMap = new HashMap<>();

        for (TeslaStationInfo station : stations) {
            jComboBoxTeslaStations.addItem(station.getName());
            stationNameToStationIdMap.put(station.getName(), station.getStationId());
        }

        this.jComboBoxTeslaStations.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent event) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JComboBox<String> combo = (JComboBox<String>) event.getSource();
                        final String name = (String) combo.getSelectedItem();
                        controller.getTeslaStationDatapoints(stationNameToStationIdMap.get(name));
                    }
                });
            }
        });

        if (stations != null && stations.size() > 0) {
            jComboBoxTeslaStations.setSelectedIndex(0);
        }
    }

    public void createAllTeslaCheckBoxListener() {

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                showAllTesla = abstractButton.getModel().isSelected();
                fillPointsTable(filter);
            }
        };
        this.jCheckBoxShowAllTesla.addActionListener(actionListener);
    }

    public void createIgnoreGarbageCheckBoxListener() {

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                ignoreGarbage = abstractButton.getModel().isSelected();
                fillPointsTable(filter);
            }
        };
        this.jCheckBoxIgnoreGarbage.addActionListener(actionListener);
    }

    public void fillEdisonSidsDropdown() {

        List<String> edisonSids = new ArrayList<>();
        for (DatapointsAndMetadataResponse edisonPoint : edisonPoints) {
            if (!edisonSids.contains(edisonPoint.getSid())) {
                edisonSids.add(edisonPoint.getSid());
            }
        }

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        this.jComboBoxEdisonSids.setModel(comboBoxModel);

        jComboBoxEdisonSids.addItem("All");
        for (String sid : edisonSids) {
            jComboBoxEdisonSids.addItem(sid);
        }

        this.jComboBoxEdisonSids.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                selectedSid = (String) combo.getSelectedItem();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        fillPointsTable(filter);
                    }
                });
            }
        });

        jComboBoxEdisonSids.setSelectedIndex(0);

    }

    private void fillPointsTable(String filter) {
        this.jTablePushPoints.setDefaultRenderer(Object.class, new DataPointsTableCellRenderer());
        this.jTablePushPoints.setModel(new DataPointsTableModel(
                edisonPoints, selectedSid, listOfStationDatapoints, showAllTesla,
                ignoreGarbage, jCheckBoxUseRegEx.isSelected(), filter));
        this.jTablePushPoints.setAutoCreateRowSorter(true);
        fixPointsTableColumnWidths(jTablePushPoints);
        setPointCounts();
    }

    private void setPointCounts() {
        String msg = String.format("num points: %d (%d selected)", listOfStationDatapoints.size(), jTablePushPoints.getSelectedRowCount());
        jLabelPointCounts.setText(msg);
    }

    public void fixPointsTableColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        for (int i = 0; i < t.getColumnCount(); i++) {
            column = t.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(50);
                    break;
                case 1:
                    column.setPreferredWidth(100);
                    break;
                case 4:
                    column.setPreferredWidth(20);
                    break;
                default:
                    column.setPreferredWidth(200);
                    break;
            }
        }

    }

    private boolean isValidPointToPush(MappingTableRow mappedRow) {

        if (mappedRow.getMapStatus() == EnumMapStatus.Overridden && mappedRow.getTeslaName().contentEquals("Ignore")) {
            return false;
        }

        if (mappedRow.getMapStatus() == EnumMapStatus.NoTeslaInfo) {
            return false;
        }
        
        if( mappedRow.getTeslaType().contentEquals("calculated")){
            return false;
        }

        if (mappedRow.getTeslaID() == null || mappedRow.getTeslaID().contentEquals("?)")) {
            return false;
        }

        return true;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxTeslaStations = new javax.swing.JComboBox<>();
        jComboBoxEdisonSids = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jCheckBoxShowAllTesla = new javax.swing.JCheckBox();
        jComboBoxTeslaHosts = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldEdisonFilter = new javax.swing.JTextField();
        jCheckBoxUseRegEx = new javax.swing.JCheckBox();
        jCheckBoxIgnoreGarbage = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldStartDate = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldEndDate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldMaxNumHoursPerPush = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldMaxNumPointsPerPush = new javax.swing.JTextField();
        jButtonPushSparsePoints = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxTeslaUsers = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePushPoints = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabelPointCounts = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonStart = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButtonQuit = new javax.swing.JButton();
        jLabelLapsedTime = new javax.swing.JLabel();
        jProgressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Push Edison To Tesla");

        jLabel2.setText("Station:");

        jComboBoxTeslaStations.setEnabled(false);

        jComboBoxEdisonSids.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("EdSid:");

        jCheckBoxShowAllTesla.setText("Show All Tesla Points");

        jComboBoxTeslaHosts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Tesla Host:");

        jLabel5.setText("Filer:");

        jTextFieldEdisonFilter.setText("jTextField1");
        jTextFieldEdisonFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEdisonFilterActionPerformed(evt);
            }
        });

        jCheckBoxUseRegEx.setText("Use RegEx");

        jCheckBoxIgnoreGarbage.setSelected(true);
        jCheckBoxIgnoreGarbage.setText("Ignore COV, etc.");

        jLabel7.setText("Start:");

        jTextFieldStartDate.setText("jTextField1");

        jLabel8.setText("End:");

        jTextFieldEndDate.setText("jTextField2");

        jLabel1.setText("Hours/Push:");

        jTextFieldMaxNumHoursPerPush.setText("100");

        jLabel9.setText("# Pts/Push");

        jTextFieldMaxNumPointsPerPush.setText("500");

        jButtonPushSparsePoints.setText("Push Sparse Point Values");
        jButtonPushSparsePoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPushSparsePointsActionPerformed(evt);
            }
        });

        jLabel10.setText("User:");

        jComboBoxTeslaUsers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMaxNumHoursPerPush, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMaxNumPointsPerPush, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonPushSparsePoints))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxTeslaHosts, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxEdisonSids, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldEdisonFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBoxUseRegEx)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBoxIgnoreGarbage))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxTeslaUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxTeslaStations, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxShowAllTesla)
                        .addGap(35, 282, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxUseRegEx)
                    .addComponent(jTextFieldEdisonFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxEdisonSids, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jCheckBoxIgnoreGarbage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxTeslaHosts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxTeslaStations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBoxTeslaUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxShowAllTesla, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldMaxNumHoursPerPush, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldMaxNumPointsPerPush, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPushSparsePoints))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datapoints"));

        jTablePushPoints.setModel(new javax.swing.table.DefaultTableModel(
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
        jTablePushPoints.setShowGrid(true);
        jTablePushPoints.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTablePushPointsMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePushPoints);

        jLabelPointCounts.setText("*pointCounts*");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPointCounts)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelPointCounts)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Status"));

        jButtonStart.setText("Pull & Push");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        jLabel6.setText("Progress");

        jButtonQuit.setText("Close");
        jButtonQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQuitActionPerformed(evt);
            }
        });

        jLabelLapsedTime.setText("*status*");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLapsedTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonQuit)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonQuit)
                        .addComponent(jLabelLapsedTime))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonStart)
                        .addComponent(jLabel6))
                    .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed

        if (jTablePushPoints.getSelectedRowCount() > 0) {

            int maxHoursPerPush = Integer.parseInt(this.jTextFieldMaxNumHoursPerPush.getText());
            int maxPointsPerPush = Integer.parseInt(this.jTextFieldMaxNumPointsPerPush.getText());

            DataPointsTableModel model = (DataPointsTableModel) this.jTablePushPoints.getModel();
            int[] rowNumbers = jTablePushPoints.getSelectedRows();
            List<MappingTableRow> mappedRows = new ArrayList<>();
            for (int selectedRowNumber : rowNumbers) {
                int modelIndex = jTablePushPoints.convertRowIndexToModel(selectedRowNumber);
                MappingTableRow mappedRow = model.getRow(modelIndex);

                if (isValidPointToPush(mappedRow)) {
                    mappedRows.add(mappedRow);
                }
            }

            DateTime pushStartTime = DateTime.parse(jTextFieldStartDate.getText(), zzFormat).withZone(DateTimeZone.UTC);
            DateTime pushEndTime = DateTime.parse(jTextFieldEndDate.getText(), zzFormat).withZone(DateTimeZone.UTC);

            //endOfPeriod is the number of whole hours between the startDate and the endDate.
            Hours hours = Hours.hoursBetween(pushStartTime, pushEndTime);
            int totalNumberOfHoursToPush = hours.getHours();
            int totalNumberOfPointsToPush = mappedRows.size();
            maxPointsPerPush = Math.min(maxPointsPerPush, mappedRows.size());

            int numHourGroups = (totalNumberOfHoursToPush + 1) / maxHoursPerPush;
            int numPointGroups = (totalNumberOfPointsToPush + 1) / maxPointsPerPush;

            totalBatchesToPush = numHourGroups * numPointGroups;

            jProgressBar.setMaximum(100);
            jProgressBar.setValue(0);
            jProgressBar.setStringPainted(true);

            this.jButtonStart.setEnabled(false);
            teslaPushTimerStartTime = DateTime.now();
            lapsedTimeTimer = new Timer(1000, lapsedTimeUpdater);
            lapsedTimeTimer.start();
            controller.setEdisonClient();
            controller.pullFromEdisonPushToTesla(selectedSid, pushStartTime, pushEndTime, mappedRows, maxHoursPerPush, maxPointsPerPush);
        }

    }//GEN-LAST:event_jButtonStartActionPerformed

    private void jButtonQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQuitActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonQuitActionPerformed

    private void jTablePushPointsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePushPointsMousePressed

        setPointCounts();

        if (evt.isPopupTrigger()) {
            PopupMenuForDataPointsTable popup = new PopupMenuForDataPointsTable(evt, jTablePushPoints);
        }
    }//GEN-LAST:event_jTablePushPointsMousePressed

    private void jTextFieldEdisonFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEdisonFilterActionPerformed
        this.filter = jTextFieldEdisonFilter.getText();
        fillPointsTable(this.filter);
    }//GEN-LAST:event_jTextFieldEdisonFilterActionPerformed

    private void jButtonPushSparsePointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPushSparsePointsActionPerformed

        String stationName = (String) (jComboBoxTeslaStations.getSelectedItem());
        if (edisonPoints != null && edisonPoints.size() > 0 && listOfStationDatapoints != null && listOfStationDatapoints.size() > 0) {

            DateTime pushStartTime = DateTime.parse(jTextFieldStartDate.getText(), zzFormat);
            SparsePointPusherFrame frame = SparsePointPusherFrame.getInstance(
                    controller, stationName, pushStartTime, edisonPoints, listOfStationDatapoints);

            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            controller.addModelListener(frame);
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }
    }//GEN-LAST:event_jButtonPushSparsePointsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jComboBoxTeslaStations.setEnabled(false);
        initTeslaModel(selectedBaseURL, selectedUser);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonPushSparsePoints;
    private javax.swing.JButton jButtonQuit;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JCheckBox jCheckBoxIgnoreGarbage;
    private javax.swing.JCheckBox jCheckBoxShowAllTesla;
    private javax.swing.JCheckBox jCheckBoxUseRegEx;
    private javax.swing.JComboBox<String> jComboBoxEdisonSids;
    private javax.swing.JComboBox<String> jComboBoxTeslaHosts;
    private javax.swing.JComboBox<String> jComboBoxTeslaStations;
    private javax.swing.JComboBox<String> jComboBoxTeslaUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelLapsedTime;
    private javax.swing.JLabel jLabelPointCounts;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePushPoints;
    private javax.swing.JTextField jTextFieldEdisonFilter;
    private javax.swing.JTextField jTextFieldEndDate;
    private javax.swing.JTextField jTextFieldMaxNumHoursPerPush;
    private javax.swing.JTextField jTextFieldMaxNumPointsPerPush;
    private javax.swing.JTextField jTextFieldStartDate;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.TeslaLoginResponseReturned.getName())) {
            controller.getTeslaStations();

        } else if (propName.equals(PropertyChangeNames.TeslaStationsListReturned.getName())) {
            jComboBoxTeslaStations.setEnabled(true);
            List<TeslaStationInfo> stations = (List<TeslaStationInfo>) evt.getNewValue();
            fillTeslasStationsDropdown(stations);

        } else if (propName.equals(PropertyChangeNames.TeslaStationDatapointsReturned.getName())) {
            this.listOfStationDatapoints = (List<TeslaDPServiceDatapoint>) evt.getNewValue();
            fillPointsTable(filter);
            this.jLabelLapsedTime.setText("complete");

        } else if (propName.equals(PropertyChangeNames.TeslaIntervalPushed.getName())) {
            completedBatches += 1;
            double percComplete = (double) completedBatches / (double) totalBatchesToPush;
            percComplete *= 100;

            int count = (int) percComplete;

            jProgressBar.setValue(Math.min(count, jProgressBar.getMaximum()));

        } else if (propName.equals(PropertyChangeNames.TeslaPushComplete.getName())) {
            jProgressBar.setBackground(Color.GREEN);
            jProgressBar.invalidate();
            jProgressBar.repaint();

            if (lapsedTimeTimer != null) {
                lapsedTimeTimer.stop();
            }

            DateTime historyPusTimerEnd = DateTime.now();
            Period period = new Period(teslaPushTimerStartTime, historyPusTimerEnd);
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

            this.dispose();

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            this.dispose();
        }
    }
}
