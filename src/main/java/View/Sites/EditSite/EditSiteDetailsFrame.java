package View.Sites.EditSite;

import Controller.OptiCxAPIController;
import Model.DataModels.Alarms.AlarmListRequest;
import Model.DataModels.Datapoints.DatapointHistoriesQueryParams;
import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.Datapoints.EnumAggregationType;
import Model.DataModels.Datapoints.EnumResolutions;
import Model.DataModels.Graph.EdisonNode;
import Model.DataModels.Graph.EnumGraphNodeTypes;
import Model.DataModels.Graph.GetChildrenResponse;
import Model.DataModels.Live.GetLiveData.GetLiveDataResponse;
import Model.DataModels.Live.Subscriptions.CreateSubscriptionRequest;
import Model.DataModels.Live.Subscriptions.SubscriptionResponseDatapoint;
import Model.DataModels.Sites.ActivationCodeResponse;
import Model.DataModels.Sites.Alarm;
import Model.DataModels.Sites.CheckForUpdatesResponse;
import Model.DataModels.Sites.EnumKPI;
import Model.DataModels.Sites.EnumLicenseTypes;
import Model.DataModels.Sites.EnumPointsListDownloadType;
import Model.DataModels.Sites.EnumProducts;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.SiteDPConfig.EnumSubGroupTypes;
import Model.DataModels.Sites.SiteDPConfig.SiteDPConfigPoint;
import Model.DataModels.Sites.SiteDPConfig.SiteDPConfigPoints;
import Model.DataModels.Sites.SiteDatapoints.Statistics;
import Model.DataModels.Sites.SiteTrend;
import Model.DataModels.Sites.SiteTrendAndKPIRequest;
import Model.DataModels.Sites.SiteTrendKPI;
import Model.DataModels.Sites.StationLicense;
import Model.DataModels.Stations.HistoryPushObject;
import Model.DataModels.Stations.NetworkAvailabilityResponse;
import Model.DataModels.Stations.StationActivateRequest;
import Model.DataModels.Stations.StationActivateResponse;
import Model.DataModels.Stations.StationAlarmPushObject;
import Model.DataModels.Stations.StationAuditHistory;
import Model.DataModels.Stations.StationCredentials;
import Model.DataModels.Stations.StationLogHistory;
import Model.DataModels.Stations.StationStatusResponse;
import Model.DataModels.Stations.WizardStationStatus;
import Model.DataModels.Stations.StationValidateQueryParams;
import Model.DataModels.Stations.StationsHeartbeat;
import Model.DataModels.Views.EnumPageViewTypes;
import Model.DataModels.Views.PageView;
import Model.PropertyChangeNames;
import Model.RestClient.LoginResponse;
import Model.RestClient.OEResponse;
import View.CommonLibs.PopupMenuOnTables;
import View.Sites.EditSite.C_DatapointConfig.DPConfigTableCellRenderer;
import View.Sites.EditSite.C_DatapointConfig.DPConfigTableModel;
import View.Sites.EditSite.C_DatapointConfig.PopupMenuDPConfigTable;
import View.Sites.EditSite.B_Alarms.AlarmsTableCellRenderer;
import View.Sites.EditSite.B_Alarms.AlarmsTableModel;
import View.Sites.EditSite.J_Contacts.ContactsTableCellRenderer;
import View.Sites.EditSite.J_Contacts.ContactsTableModel;
import Model.DataModels.Sites.SiteDPConfig.EnumGroupTypes;
import Model.DataModels.Graph.RuleInfoResponse;
import Model.DataModels.Graph.RuleInRulesResponse;
import Model.DataModels.Live.Subscriptions.CreateSubscriptionResponse;
import Model.DataModels.ReportVerification.CalcPointClassification.Equipment.EquipmentInfo;
import Model.DataModels.Reports.MonthlyReportItem;
import Model.DataModels.Reports.ReportsList;
import Model.DataModels.Sites.Address;
import Model.DataModels.Sites.Contact;
import Model.DataModels.Sites.Phone;
import Model.DataModels.Sites.UpdateSiteRequest;
import View.Sites.EditSite.B_Alarms.SiteAlarms.SiteAlarmsFrame;
import View.Sites.EditSite.A_History.AveragesTable.DPHistoryAveragesTableCellRenderer;
import View.Sites.EditSite.A_History.AveragesTable.DPHistoryAveragesTableModel;
import View.Sites.EditSite.A_History.DPHistoryTableCellRenderer;
import View.Sites.EditSite.A_History.DPHistoryTableModel;
import View.Sites.EditSite.A_History.DPHistoryChart.DPHistoryChartFrame;
import View.Sites.EditSite.A_History.PopUpMenuForHistoryResultsTable;
import View.Sites.EditSite.C_DatapointConfig.EnumDPConfigTableColumns;
import View.Sites.EditSite.H_StationInfo.TestLoginFrame;
import View.Sites.EditSite.D_PlantInfo.EnhancementsTableCellRenderer;
import View.Sites.EditSite.D_PlantInfo.EnhancementsTableModel;
import View.Sites.NewSite.Address.AddressTableCellRenderer;
import View.Sites.NewSite.Address.AddressTableModel;
import View.TreeInfo.CustomTreeNode;
import View.Users.PopupMenuForCopyingToClipboard;
import View.CommonLibs.MapTableCellRenderer;
import View.CommonLibs.MapTableModel;
import View.CommonLibs.PopupMenuForMapTables;
import View.Sites.EditSite.A_History.Atom.AtomFrame;
import View.Sites.EditSite.A_History.DPHistoryChart.StampsAndPoints;
import View.Sites.EditSite.A_History.DataGenerator.DataGeneratorFrame;
import View.Sites.EditSite.A_History.DataPointsAdmin.Associations.DataPointAssociationsTableCellRenderer;
import View.Sites.EditSite.A_History.DataPusher.DataPusherFrame;
import View.Sites.EditSite.A_History.DatapointListTable.DataPointAssiationsTable.DatapointAssociationsTableModel;
import View.Sites.EditSite.A_History.DatapointListTable.DatapointsListTableModel;
import View.Sites.EditSite.A_History.DatapointListTable.DatapointsListTableCellRenderer;
import View.Sites.EditSite.A_History.DatapointListTable.PopupMenuForDataPointsListTable;
import View.Sites.EditSite.A_History.PushLiveData.OptimizationStatus.PushOptimizationInfoFrame;
import View.Sites.EditSite.A_History.PushToTesla.PushToTeslaFrame;
import View.Sites.EditSite.A_History.ReportVerification.ReportDates.EnumDateTypes;
import View.Sites.EditSite.A_History.ReportVerification.ReportDates.EnumReportMonths;
import View.Sites.EditSite.A_History.ReportVerification.ReportDates.EnumReportYears;
import View.Sites.EditSite.A_History.ReportVerification.ReportVerificationFrame;
import View.Sites.EditSite.A_History.RepushEdisonData.RepushFrame;
import View.Sites.EditSite.E_Views.ViewLiveData.ViewLiveDataFrame;
import View.Sites.EditSite.F_SiteTrend.SiteTrendChartFrame;
import View.Sites.EditSite.J_Contacts.Phones.ContactPhoneTableCellRenderer;
import View.Sites.EditSite.J_Contacts.Phones.ContactPhoneTableModel;
import View.Sites.EditSite.J_Contacts.Phones.PopupMenuForContactPhones;
import View.Sites.EditSite.K_RulesInfo.RuleListTableModel;
import View.Sites.EditSite.K_RulesInfo.RulesListTableCellRenderer;
import View.Sites.EditSite.T_Reports.ReportsListTable.ReportsListTableCellRenderer;
import View.Sites.EditSite.T_Reports.ReportsListTable.ReportsListTableModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultTreeModel;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class EditSiteDetailsFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static EditSiteDetailsFrame thisInstance;

    private final OptiCxAPIController controller;
    private Site site;
    private DateTimeZone siteTimeZone;
    private SiteDPConfigPoints siteConfigPoints;
    private Boolean viewShortListOfConfigPoints;
    private PageView pageView;
    private CreateSubscriptionResponse createSubscriptionResponse;

    private List<DatapointsAndMetadataResponse> listOfMetadata;
    private boolean restOfThePointsReceived = false;
    private List<DatapointHistoriesResponse> listOfDataPointHistories;

    private String historyDatapointFilter;
    private DPHistoryChartFrame chartFrame;

    private SiteTrendKPI siteTrendKPI;

    private DateTime siteLocalStartDate;
    private DateTime siteLocalEndDate;
    private DateTime utcStartDate;
    private DateTime utcEndDate;

    private Statistics stat;
    private Timer timer = null;
    private String subscriptionId;

    private DateTimeFormatter zzFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
    private DateTimeFormatter utcLabelFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    
    private EnumReportMonths reportMonth;
    private EnumReportYears reportYear;
    private EnumDateTypes reportDateType;

    private EditSiteDetailsFrame(final OptiCxAPIController controller) {
        initComponents();

        this.controller = controller;

        SpinnerNumberModel spinModel = new SpinnerNumberModel(3, 0, 6, 1);
        this.jSpinnerHistoryPrec.setModel(spinModel);
        jSpinnerHistoryPrec.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                if (listOfDataPointHistories.size() > 0) {
                    int prec = (int) jSpinnerHistoryPrec.getModel().getValue();
                    fillDatapointsHistoryTable(prec);
                    fillDatapointsAveragesTable(prec);
                }
            }
        });

    }

    public static EditSiteDetailsFrame getInstance(final OptiCxAPIController controller) {
        if (thisInstance == null) {
            thisInstance = new EditSiteDetailsFrame(controller);
        }
        return thisInstance;
    }

    @Override
    public void dispose() {
        controller.removePropChangeListener(this);
        killLivePollingTimer();
        thisInstance = null;
        super.dispose();
    }

    private void fillEditSiteDetailsFrame(Site site) {

        String timeZoneStr = site.getAddress().getTimezone();

        if (timeZoneStr == null) {
            timeZoneStr = "America/Chicago";
        }

        TimeZone tz = TimeZone.getTimeZone(timeZoneStr);
        siteTimeZone = DateTimeZone.forTimeZone(tz);

        jLabelSiteSid.setText(site.getSid());
        jTextFieldSiteName.setText(site.getName());
        jLabelStationId.setText(site.getStationID());
        jLabelStationHostID.setText(site.getStationHostID());

        fillSiteHistoryFrame();
        fillAlarmsFrame();
        fillDPConfigFrame();
        fillGraphNodesFrame();
        fillSiteTrendFrame();
        fillViewsFrame();
        fillStationInfoFrame();
        fillSiteInfoFrame();
        fillContactsFrame();
        fillReportsFrame();
    }

    private void setSiteFromFields() {
        site.setSid(jLabelSiteSid.getText());
        site.setName(jTextFieldSiteName.getText());
        site.setStationLicense(getStationLicenseFromFields());
        site.setProduct((String) this.jComboBoxSiteInfoProducts.getSelectedItem());
        site.setSFOppotunity(jTextFieldSFOpportunity.getText());
        site.setEdgePlus(jCheckBoxEdgePlus.isSelected());
        site.setExtSFID(jTextFieldExtSFID.getText());
        site.setProduct((String) this.jComboBoxSiteInfoProducts.getSelectedItem());

        ContactsTableModel mod = (ContactsTableModel) jTableContacts.getModel();

        List<Contact> updatedContacts = mod.getContactsFromTable();

        site.setContacts(updatedContacts);
    }

    //===  HISTORY ===========
    public void selectAtomPoints() {

        //String ATOM_CHILLER_POINTS[] = { "CHSBoolean", "CHWFLO", "CHWRT", "CHWST", "CHkW" };
        List<String> chillerPoints = Arrays.asList(new String[]{"Status", "ChilledWaterFlow", "ChilledWaterReturnTemperature", "ChilledWaterSupplyTemperature", "kW"});
        List<String> stationPoints = Arrays.asList(new String[]{"OAWB"});

        DatapointsListTableModel mod = (DatapointsListTableModel) this.jTableDatapointsList.getModel();

        for (int rowNumber = 0; rowNumber < jTableDatapointsList.getRowCount(); rowNumber++) {
            int modelNumber = jTableDatapointsList.convertRowIndexToModel(rowNumber);
            DatapointsAndMetadataResponse pointAndMetaData = mod.getRow(modelNumber);

            List<Map> alpha = pointAndMetaData.getDatapointAssociations();
            for (Map nameAndSid : pointAndMetaData.getDatapointAssociations()) {
                for (Object key : nameAndSid.keySet()) {
                    String name = (String) key;
                    if (chillerPoints.contains(key) || stationPoints.contains(key)) {
                        jTableDatapointsList.getSelectionModel().addSelectionInterval(rowNumber, rowNumber);
                    }

                }
            }
        }

    }

    public boolean pointInClarkList(List<DatapointsAndMetadataResponse> listOfMetadata) {

        String clarksPoints[] = {"ACHWDPSP",
            "BASWATCHDOG",
            "CDWBPV",
            "CDWFLO",
            "CDWP1Failed",
            "CDWP1S",
            "CDWP1SPD",
            "CDWP1SS",
            "CDWP1kW",
            "CDWP2Failed",
            "CDWP2S",
            "CDWP2SPD",
            "CDWP2SS",
            "CDWP2kW",
            "CDWP3Failed",
            "CDWP3S",
            "CDWP3SPD",
            "CDWP3SS",
            "CDWP3kW",
            "CDWPSPD",
            "CDWRT",
            "CDWST",
            "CDWSTSP",
            "CH1AVAIL",
            "CH1CDP",
            "CH1CDT",
            "CH1CDWDP",
            "CH1CDWFLO",
            "CH1CDWRT",
            "CH1CDWST",
            "CH1CDWVLV",
            "CH1CHWDP",
            "CH1CHWFLO",
            "CH1CHWRT",
            "CH1CHWST",
            "CH1CHWSTSP",
            "CH1CHWVLV",
            "CH1COMDISCHT",
            "CH1CURRLMT",
            "CH1EVP",
            "CH1EVT",
            "CH1F",
            "CH1FLA",
            "CH1Failed",
            "CH1IGV",
            "CH1S",
            "CH1SPD",
            "CH1SS",
            "CH1SURGE",
            "CH1SURGECNT",
            "CH1kW",
            "CH2AVAIL",
            "CH2CDP",
            "CH2CDT",
            "CH2CDWDP",
            "CH2CDWFLO",
            "CH2CDWRT",
            "CH2CDWST",
            "CH2CDWVLV",
            "CH2CHWDP",
            "CH2CHWFLO",
            "CH2CHWRT",
            "CH2CHWST",
            "CH2CHWSTSP",
            "CH2CHWVLV",
            "CH2COMDISCHT",
            "CH2CURRLMT",
            "CH2EVP",
            "CH2EVT",
            "CH2F",
            "CH2FLA",
            "CH2Failed",
            "CH2IVG",
            "CH2S",
            "CH2SPD",
            "CH2SS",
            "CH2SURGECNT",
            "CH2kW",
            "CH3AVAIL",
            "CH3CDP",
            "CH3CDT",
            "CH3CDWDP",
            "CH3CDWFLO",
            "CH3CDWRT",
            "CH3CDWST",
            "CH3CDWVLV",
            "CH3CHWDP",
            "CH3CHWFLO",
            "CH3CHWRT",
            "CH3CHWST",
            "CH3CHWSTSP",
            "CH3CHWVLV",
            "CH3COMDISCHT",
            "CH3CURRLMT",
            "CH3EVP",
            "CH3EVT",
            "CH3F",
            "CH3FLA",
            "CH3Failed",
            "CH3IGV",
            "CH3S",
            "CH3SPD",
            "CH3SS",
            "CH3SURGE",
            "CH3SURGECNT",
            "CH3kW",
            "CHWBPV",
            "CHWDP",
            "CHWDPSP",
            "CHWFLO",
            "CHWFLO2",
            "CHWRP",
            "CHWRT",
            "CHWSP",
            "CHWST",
            "CHWSTSP",
            "CLGMODE",
            "COMLOSSBAS",
            "CT1EVLV",
            "CT1Failed",
            "CT1LVLV",
            "CT1S",
            "CT1SPD",
            "CT1SS",
            "CT1kW",
            "CT2EVLV",
            "CT2Failed",
            "CT2LVLV",
            "CT2S",
            "CT2SPD",
            "CT2SS",
            "CT2kW",
            "CTFANSPDMAX",
            "EDGEMODE",
            "EDGEREADY",
            "OAH",
            "OAT",
            "OAWB",
            "OEWATCHDOG",
            "PCHWP1Failed",
            "PCHWP1S",
            "PCHWP1SPD",
            "PCHWP1SS",
            "PCHWP1kW",
            "PCHWP2Failed",
            "PCHWP2S",
            "PCHWP2SPD",
            "PCHWP2SS",
            "PCHWP2kW",
            "PCHWP3Failed",
            "PCHWP3S",
            "PCHWP3SPD",
            "PCHWP3SS",
            "PCHWP3kW",
            "PCHWPSPD",
            "SCHWP1Failed",
            "SCHWP1S",
            "SCHWP1SPD",
            "SCHWP1SS",
            "SCHWP1kW",
            "SCHWP2Failed",
            "SCHWP2S",
            "SCHWP2SPD",
            "SCHWP2SS",
            "SCHWP2kW",
            "SCHWP3Failed",
            "SCHWP3S",
            "SCHWP3SPD",
            "SCHWP3SS",
            "SCHWP3kW"};

        for (String clarkPoint : clarksPoints) {
            boolean isValid = false;
            for (DatapointsAndMetadataResponse t : listOfMetadata) {
                if (clarkPoint.compareTo(t.getName()) == 0) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                System.out.println(clarkPoint + " is not valid");
            }

        }

        return false;
    }

    public void fillSiteHistoryFrame() {

        jButtonHistoryQuery.setEnabled(false);
        jButtonHistoryPushLiveData.setEnabled(false);
        jToggleHistoryPollLiveData.setEnabled(false);
        jButtonHistoryChart.setEnabled(false);

        historyDatapointFilter = "";
        this.jTextFieldHistoryDPFilter.setText(historyDatapointFilter);

        fillHistoryResolutionDropdown();

        if (siteTimeZone != null) {
            jLabelTimeZone.setText(siteTimeZone.getID());
        } else {
            jLabelTimeZone.setText("it's null");
        }

        configureAppendLiveDataCheckbox();
        jLabelHistoryLiveId.setText("");
        jTextFieldStaleThresh.setText("5");

        SpinnerNumberModel spinModel = new SpinnerNumberModel(5, 5, 20, 1);
        this.jSpinnerPollInterval.setModel(spinModel);

        this.jTextFieldHistoryMaxPoints.setText("100");

        /*
        DateTimeFormatter initDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        
        DateTime rightNow = DateTime.now();
        DateTime siteLocalEndDate = rightNow.minusDays( rightNow.getDayOfMonth() - 1 );
        DateTime siteLocalStartDate = siteLocalEndDate.minusMonths(1);
        //siteLocalStartDate = DateTime.parse("2017-09-01 00:00", initDateFormat).withZoneRetainFields(siteTimeZone);
        //siteLocalEndDate = DateTime.parse("2017-10-01 00:00", initDateFormat).withZoneRetainFields(siteTimeZone);

        //DateTimeFormatter displayDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm ZZ");
        this.jTextFieldHistoryStartDate.setText(siteLocalStartDate.toString());
        this.jTextFieldHistoryEndDate.setText(siteLocalEndDate.toString());

        //DateTimeFormatter uiDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        utcStartDate = siteLocalStartDate.withZone(DateTimeZone.UTC);
        utcEndDate = siteLocalEndDate.withZone(DateTimeZone.UTC);
        jLabelUTCStartDate.setText(utcStartDate.toString(zzFormat) + " (UTC)");
        jLabelUTCEndDate.setText(utcEndDate.toString(zzFormat) + " (UTC)");
        */
        
        reportMonth = EnumReportMonths.Jan;
        reportYear = EnumReportYears.y2018;
        reportDateType = EnumDateTypes.MNTH;

        setStartAndEndDates(reportMonth, reportYear, reportDateType);

        fillReportMonthDropDown();
        fillReportYearDropDown();
        fillReportDateTypesDropDown();
        
        

        jLabelHistoryQueryResultsSid.setText("");
        jLabelHistoryResultsTimezone.setText("");
        jLabelQueryResultsReso.setText("");

        Map< String, List<String>> mapOfSidsAndPoints = new HashMap<>();

        mapOfSidsAndPoints.put(this.site.getSid(), new ArrayList<String>());
        mapOfSidsAndPoints.put(this.site.getSid() + ".st:1", new ArrayList<String>());

        controller.getDatapointsUnion(mapOfSidsAndPoints);
    }
    
    private void fillReportMonthDropDown() {

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumReportMonths.getMonthNames().toArray());
        this.jComboBoxReportMonth.setModel(comboBoxModel);
        this.jComboBoxReportMonth.setSelectedIndex(EnumReportMonths.Jan.getDropDownIndex());
        this.jComboBoxReportMonth.setEnabled(true);

        this.jComboBoxReportMonth.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                reportMonth = EnumReportMonths.getMonthFromName(name);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setStartAndEndDates(reportMonth, reportYear, reportDateType);
                    }
                });

            }
        });
    }

    private void fillReportYearDropDown() {

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumReportYears.getYearNames().toArray());
        this.jComboBoxReportYear.setModel(comboBoxModel);
        this.jComboBoxReportYear.setSelectedIndex(EnumReportYears.y2018.getDropDownIndex());
        this.jComboBoxReportYear.setEnabled(true);

        this.jComboBoxReportYear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                reportYear = EnumReportYears.getYearFromName(name);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setStartAndEndDates(reportMonth, reportYear, reportDateType);
                    }
                });

            }
        });
    }

    private void fillReportDateTypesDropDown() {

        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumDateTypes.getDateTypes().toArray());
        this.jComboBoxDateType.setModel(comboBoxModel);
        this.jComboBoxDateType.setSelectedIndex(EnumDateTypes.MNTH.getDropDownIndex());
        this.jComboBoxDateType.setEnabled(true);

        this.jComboBoxDateType.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                reportDateType = EnumDateTypes.getDateTypeFromName(name);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setStartAndEndDates(reportMonth, reportYear, reportDateType);
                    }
                });

            }
        });
    }

    private void setStartAndEndDates(EnumReportMonths month, EnumReportYears year, EnumDateTypes dateType) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month.getMonthNumber());
        cal.set(Calendar.YEAR, year.getYearNumber());
        int numOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        switch (dateType) {
            case MNTH: {
                siteLocalStartDate = new DateTime(year.getYearNumber(), month.getMonthNumber() + 1, 1, 0, 0, siteTimeZone);
                siteLocalEndDate = siteLocalStartDate.plusDays(numOfDaysInMonth);
            }
            break;
            case LM: {
                siteLocalStartDate = new DateTime(year.getYearNumber(), month.getMonthNumber() + 1, 1, 0, 0, siteTimeZone);
                siteLocalStartDate = siteLocalStartDate.minusMonths(1);
                siteLocalEndDate = siteLocalStartDate.plusDays(numOfDaysInMonth);
            }
            break;
            case YTD: {
                siteLocalStartDate = new DateTime(year.getYearNumber(), 1, 1, 0, 0, siteTimeZone);
                siteLocalEndDate = new DateTime(year.getYearNumber(), month.getMonthNumber() + 1, 1, 0, 0, siteTimeZone);
                siteLocalEndDate = siteLocalEndDate.plusDays(numOfDaysInMonth);
            }
            break;
            case L12: {
                siteLocalEndDate = new DateTime(year.getYearNumber(), month.getMonthNumber() + 1, 1, 0, 0, siteTimeZone);
                siteLocalEndDate = siteLocalEndDate.plusDays(numOfDaysInMonth);
                siteLocalStartDate = siteLocalEndDate.minusMonths(12);
            }
            break;
        }

        this.jTextFieldHistoryStartDate.setText(siteLocalStartDate.toString());
        this.jTextFieldHistoryEndDate.setText(siteLocalEndDate.toString());

        utcStartDate = siteLocalStartDate.withZone(DateTimeZone.UTC);
        utcEndDate = siteLocalEndDate.withZone(DateTimeZone.UTC);
        jLabelUTCStartDate.setText(utcStartDate.toString(utcLabelFormat)+ " (UTC)");
        jLabelUTCEndDate.setText(utcEndDate.toString(utcLabelFormat) + " (UTC)");

    }


    public void getTheRestOfThePoints(List<DatapointsAndMetadataResponse> listOfMetadata) {
        EquipmentInfo equipInfo = new EquipmentInfo(listOfMetadata);

        Map< String, List<String>> mapOfSidsAndPoints = new HashMap<>();

        mapOfSidsAndPoints.put(site.getSid(), new ArrayList<String>());
        mapOfSidsAndPoints.put(site.getSid() + ".st:1", new ArrayList<String>());

        for (EnumGraphNodeTypes eType : EnumGraphNodeTypes.values()) {
            List<String> eSids = equipInfo.getEquipTypeToSidsMap().get(eType);
            if (eSids != null) {
                for (String sid : eSids) {
                    mapOfSidsAndPoints.put(sid, new ArrayList<String>());
                }
            }
        }

        controller.getDatapointsUnion(mapOfSidsAndPoints);
    }

    private void enableQueryButtons(Boolean flag) {
        if (flag) {
            jButtonHistoryQuery.setEnabled(true);
            jButtonHistoryPushLiveData.setEnabled(true);

        } else {
            jButtonHistoryQuery.setEnabled(false);
            jButtonHistoryPushLiveData.setEnabled(false);
        }
    }

    private void fillHistoryPointsTable(String filter) {

        List<DatapointsAndMetadataResponse> filteredList = new ArrayList<>();

        for (DatapointsAndMetadataResponse point : listOfMetadata) {

            String uName = getUName(point.getSid(), point.getName());

            if (filter.length() == 0) {
                filteredList.add(point);
            } else if (!this.jCheckBoxUseRegEx.isSelected() && uName.contains(filter)) {
                filteredList.add(point);
            } else if (this.jCheckBoxUseRegEx.isSelected()) {
                Pattern r = Pattern.compile(filter);
                Matcher m = r.matcher(uName);
                if (m.find()) {
                    filteredList.add(point);
                }
            }
        }

        this.jTableDatapointsList.setDefaultRenderer(Object.class, new DatapointsListTableCellRenderer());
        this.jTableDatapointsList.setModel(new DatapointsListTableModel(filteredList));
        this.jTableDatapointsList.setAutoCreateRowSorter(true);

        setPointCounts();

    }

    private String getUName(String sid, String name) {
        String uName = name;
        String[] pieces = sid.split("\\.");

        if (pieces.length > 2) {
            uName = pieces[2] + "." + uName;
        }
        return uName;
    }

    private void setPointCounts() {

        DatapointsListTableModel mod = (DatapointsListTableModel) this.jTableDatapointsList.getModel();
        int numOfPoints = mod.getRowCount();
        jLabelHistoryNumberPoints.setText(Integer.toString(numOfPoints));

        int numPointsSelected = jTableDatapointsList.getSelectedRowCount();
        jLabelNumHistoryPointsSelected.setText("(" + Integer.toString(numPointsSelected) + ")");

    }

    private void fillHistoryResolutionDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumResolutions.getNames().toArray());
        EnumResolutions res = EnumResolutions.MINUTE5;
        this.jComboBoxHistoryResolution.setModel(comboBoxModel);
        this.jComboBoxHistoryResolution.setSelectedIndex(res.getDropDownIndex());
        this.jComboBoxHistoryResolution.setEnabled(true);
    }

    public void fillDatapointsHistoryTable(int prec) {

        jTableHistoryQueryResults.setModel(new DPHistoryTableModel(listOfDataPointHistories));
        jTableHistoryQueryResults.setDefaultRenderer(Object.class, new DPHistoryTableCellRenderer(prec, siteTimeZone));
        jTableHistoryQueryResults.setAutoCreateRowSorter(true);
        fixHistoryQueryResultsTableColumnWidths(jTableHistoryQueryResults);
    }

    public void fillDatapointsAveragesTable(int prec) {
        this.jTableDPHistoryAverages.setModel(new DPHistoryAveragesTableModel(stat));
        this.jTableDPHistoryAverages.setDefaultRenderer(Object.class, new DPHistoryAveragesTableCellRenderer(prec));
        this.jTableDPHistoryAverages.setAutoCreateRowSorter(true);
        fixHistoryQueryResultsTableColumnWidths(jTableDPHistoryAverages);
    }

    private void configureAppendLiveDataCheckbox() {
        this.jCheckBoxAppendLiveData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                jToggleHistoryPollLiveData.setSelected(false);
                jToggleHistoryPollLiveData.setEnabled(false);
                killLivePollingTimer();
                jLabelHistoryLiveId.setText("");

                if (jTableDatapointsList.getSelectedRowCount() > 0 && jCheckBoxAppendLiveData.isSelected()) {
                    createLiveDataRequest();
                }
            }
        });
    }

    private void createLiveDataRequest() {

        if (jTableDatapointsList.getSelectedRows().length <= 0) {
            return;
        }
        Map<String, List<String>> sidAndNamesMap = new HashMap<>();

        for (int rowNumber : jTableDatapointsList.getSelectedRows()) {
            int modelNumber = jTableDatapointsList.convertRowIndexToModel(rowNumber);
            DatapointsListTableModel mod = (DatapointsListTableModel) jTableDatapointsList.getModel();
            DatapointsAndMetadataResponse respRow = mod.getRow(modelNumber);

            if (!sidAndNamesMap.containsKey(respRow.getSid())) {
                sidAndNamesMap.put(respRow.getSid(), new ArrayList<String>());
            }
            List<String> listOfPointsForThisSid = sidAndNamesMap.get(respRow.getSid());
            listOfPointsForThisSid.add(respRow.getName());
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        String timeStampString = DateTime.now().toString(fmt) + ".000Z";
        CreateSubscriptionRequest req = new CreateSubscriptionRequest(timeStampString, sidAndNamesMap);
        controller.postNewSubscription(req);
    }

    private void postLiveData() {

        /*
        if (jListHistoryDatapoints.getSelectedIndices().length <= 0) {
            return;
        }
        List<String> selectedPointsList = this.jListHistoryDatapoints.getSelectedValuesList();
        String sendingSid = jTextFieldSidInURL.getText();
        String stationId = jLabelStationId.getText();
        String token = jTextFieldStationUserToken.getText();
        PushLiveDataFrame frame = PushLiveDataFrame.getInstance(controller, selectedPointsMap, sendingSid, stationId, token);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
         */
    }

    private void killLivePollingTimer() {

        jSpinnerPollInterval.setEnabled(true);

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void fixHistoryQueryResultsTableColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        for (int i = 0; i < t.getColumnCount(); i++) {
            column = t.getColumnModel().getColumn(i);
            if (i < 2) {
                column.setPreferredWidth(200);
            } else {
                column.setPreferredWidth(100);
            }
        }

    }

    // ===== ALARMS =================
    private void fillAlarmsFrame() {
        if (site.getAlarms() == null) {
            site.setAlarms(new ArrayList< Alarm>());
        }
        jTableAlarms.setModel(new AlarmsTableModel(site.getAlarms()));
        jTableAlarms.setDefaultRenderer(Object.class, new AlarmsTableCellRenderer());
    }

    // ========  FILL DPCONFIG FRAME ===================================
    private void fillDPConfigFrame() {
        fillDPConfigGroupTypeDropdown();
        fillSubGroupTypeDropdown();

        this.jToggleButtonViewShortList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton abstractButton = (AbstractButton) e.getSource();
                viewShortListOfConfigPoints = abstractButton.getModel().isSelected();
                fillDPConfigTable(EnumGroupTypes.equipment, EnumSubGroupTypes.all, viewShortListOfConfigPoints);
            }
        });

    }

    private boolean pointIsInList(List<SiteDPConfigPoint> listpointsOfSubGroup, SiteDPConfigPoint groupPoint) {
        String stAttrName = groupPoint.getStationAttributeName();
        String graphAttrName = groupPoint.getGraphAttributeName();

        for (SiteDPConfigPoint p : listpointsOfSubGroup) {

            if (p.getGraphAttributeName().compareTo(graphAttrName) == 0
                    && p.getStationAttributeName().compareTo(stAttrName) == 0) {
                return true;
            }
        }

        return false;
    }

    private void fillDPConfigTable(EnumGroupTypes groupType, EnumSubGroupTypes subGroupType, Boolean viewShortList) {
        if (this.siteConfigPoints != null) {

            String name = groupType.name();

            this.jLabelCommProtocol.setText(this.siteConfigPoints.getCommProtocol());
            this.jLabelLastUpdated.setText(this.siteConfigPoints.getLastUpdated());
            List<SiteDPConfigPoint> listOfAllPointsOfGroup = this.siteConfigPoints.getGroups().get(name);

            List<SiteDPConfigPoint> listpointsOfSubGroup = new ArrayList<>();
            for (SiteDPConfigPoint groupPoint : listOfAllPointsOfGroup) {
                EnumSubGroupTypes sgt = EnumSubGroupTypes.getEnumFromFriendlyName(groupPoint.getSubGroup());

                if (subGroupType != EnumSubGroupTypes.all && subGroupType != sgt) {
                    continue;
                }

                if (viewShortList) {
                    if (pointIsInList(listpointsOfSubGroup, groupPoint)) {
                        continue;
                    } else {
                        SiteDPConfigPoint copyOfPoint = new SiteDPConfigPoint(groupPoint);
                        copyOfPoint.setEquipmentNumber("0");
                        copyOfPoint.setStationPointName("---");
                        listpointsOfSubGroup.add(copyOfPoint);
                    }

                } else {
                    listpointsOfSubGroup.add(groupPoint);
                }
            }

            this.jTableSiteDPConfig.setModel(new DPConfigTableModel(listpointsOfSubGroup));
            this.jTableSiteDPConfig.setDefaultRenderer(Object.class, new DPConfigTableCellRenderer());
            this.jTableSiteDPConfig.setAutoCreateRowSorter(true);
            fixDPConfigTableColumnWidths(jTableSiteDPConfig);

            this.jLabelDPConfigPointCount.setText(Integer.toString(listpointsOfSubGroup.size()));
        }
    }

    private void fixDPConfigTableColumnWidths(JTable tbl) {

        TableColumn column = null;

        for (int i = 0; i < tbl.getColumnCount(); i++) {

            column = tbl.getColumnModel().getColumn(i);

            EnumDPConfigTableColumns enumCol = EnumDPConfigTableColumns.getColumnFromColumnNumber(i);
            switch (enumCol) {
                case SubGroup:
                    column.setPreferredWidth(100);
                    break;
                case EquipNumber:
                    column.setPreferredWidth(100);
                    break;
                case StPointName:
                    column.setPreferredWidth(100);
                    break;
                case StAtrrName:
                    column.setPreferredWidth(100);
                    break;
                case GraphAttrName:
                    column.setPreferredWidth(300);
                    break;
                case Unit:
                    column.setPreferredWidth(100);
                    break;
                case Address:
                    column.setPreferredWidth(100);
                    break;
                case PointType:
                    column.setPreferredWidth(100);
                    break;
                case DisplayName:
                    column.setPreferredWidth(500);
                    break;
                default:
                    column.setPreferredWidth(100);
            }
        }
    }

    public void fillDPConfigGroupTypeDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumGroupTypes.getNames().toArray());
        this.jComboBoxDPConfigGroupType.setModel(comboBoxModel);
        this.jComboBoxDPConfigGroupType.setSelectedIndex(EnumGroupTypes.equipment.getDropDownIndex());
        this.jComboBoxDPConfigGroupType.setEnabled(true);

        this.jComboBoxDPConfigGroupType.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                EnumGroupTypes groupType = EnumGroupTypes.getEnumFromName(name);
                jComboBoxDPConfigSubGroup.setSelectedItem(EnumSubGroupTypes.all.getFriendlyName());
                //fillDPConfigFrame(groupType, EnumSubGroupTypes.all);
            }
        });
    }

    public void fillSubGroupTypeDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumSubGroupTypes.getNames().toArray());
        this.jComboBoxDPConfigSubGroup.setModel(comboBoxModel);
        this.jComboBoxDPConfigSubGroup.setSelectedIndex(EnumGroupTypes.equipment.getDropDownIndex());
        this.jComboBoxDPConfigSubGroup.setEnabled(true);

        this.jComboBoxDPConfigSubGroup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                EnumSubGroupTypes subGroup = EnumSubGroupTypes.getEnumFromFriendlyName(name);

                String groupName = (String) jComboBoxDPConfigGroupType.getSelectedItem();
                EnumGroupTypes groupType = EnumGroupTypes.getEnumFromName(groupName);

                fillDPConfigTable(groupType, subGroup, viewShortListOfConfigPoints);
            }
        });
    }

    //========  GRAPH NODES ====
    private void fillGraphNodesFrame() {
        this.jTextFieldNodeSid.setText(this.site.getSid());
    }

    public void fillEquipPropertyTree(EdisonNode edisonNode) throws JsonProcessingException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(edisonNode);
        JsonElement root = new JsonParser().parse(json);
        CustomTreeNode rootNode = new CustomTreeNode(null, -1, root);
        jTreeEquipProps.setModel(new DefaultTreeModel(rootNode));
    }

    private void queryGraphNodes() {

        if (this.jTextFieldNodeSid != null && this.jTextFieldNodeSid.getText().length() > 0) {
            String sid = this.jTextFieldNodeSid.getText();
            controller.getChildren(sid);
        }
    }

    public void fillGraphNodesTree(GetChildrenResponse children) throws JsonProcessingException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(children);
        JsonElement root = new JsonParser().parse(json);
        CustomTreeNode currentUserInfoTreeRootNode = new CustomTreeNode(null, -1, root);
        jTreeEquipProps.setModel(new DefaultTreeModel(currentUserInfoTreeRootNode));
    }

    //=== SITE TREND ==========
    private void fillSiteTrendFrame() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTime endDate = DateTime.now().minus(DateTime.now().getMillisOfDay()).plusMillis(1000);
        DateTime startDate = endDate.minusDays(1);
        String endDateString = endDate.toString(fmt);
        String startTimeString = startDate.toString(fmt);
        this.jTextFieldSiteTrendStartTime.setText(startTimeString + ".000Z");
        this.jTextFieldSiteTrendEndTime.setText(endDateString + ".000Z");
        this.jTextFieldStationUserToken.setText("");
        this.jTextFieldStationHostID.setText("");
        fillSiteTrendResolutionsDropdown();
        fillSiteTrendKPIDropdown();
    }

    public void fillSiteTrendResolutionsDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumResolutions.getNames().toArray());
        this.jComboBoxSiteTrendResolutions.setModel(comboBoxModel);
        this.jComboBoxSiteTrendResolutions.setSelectedIndex(EnumResolutions.MINUTE5.getDropDownIndex());
        this.jComboBoxSiteTrendResolutions.setEnabled(true);
    }

    public void fillSiteTrendKPIDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumKPI.getNames().toArray());
        this.jComboBoxSiteTrendKPI.setModel(comboBoxModel);

        this.jComboBoxSiteTrendKPI.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                EnumKPI kpi = EnumKPI.getEnumFromName(name);
                //<------------------------queryForSiteTrend(kpi);
            }
        });

        this.jComboBoxSiteTrendKPI.setSelectedIndex(EnumKPI.plant.getDropDownIndex());
        this.jComboBoxSiteTrendKPI.setEnabled(true);
    }

    public void fillSiteTrendTree(String json) throws JsonProcessingException, Exception {
        JsonElement root = new JsonParser().parse(json);
        CustomTreeNode currentUserInfoTreeRootNode = new CustomTreeNode(null, -1, root);
        jTreeSiteTrend.setModel(new DefaultTreeModel(currentUserInfoTreeRootNode));
    }

    // ====== VIEWS =========
    private void fillViewsFrame() {
        fillPageViewTypeDropdown();

    }

    public void fillPageViewTypeDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumPageViewTypes.getDropdownNames().toArray());
        this.jComboBoxPageViewTypes.setModel(comboBoxModel);
        this.jComboBoxPageViewTypes.setEnabled(true);

        this.jComboBoxPageViewTypes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();

                EnumPageViewTypes pageViewType = EnumPageViewTypes.getEnumFromDropdownName(name);
                controller.getUIMetaData(EnumProducts.edge.getName(), site.getSid(), pageViewType.getEdisonName());
            }
        });
        this.jComboBoxPageViewTypes.setSelectedIndex(0);
    }

    public void fillPageViewTree() throws JsonProcessingException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(pageView);
        JsonElement root = new JsonParser().parse(json);
        CustomTreeNode currentUserInfoTreeRootNode = new CustomTreeNode(null, -1, root);
        jTreeViewsPageData.setModel(new DefaultTreeModel(currentUserInfoTreeRootNode));
    }

    // =======STATION INFO ===============
    private void fillStationInfoFrame() {
        jLabelStationActivationCode.setText(site.getActivationCode());
        this.jLabelStationInfoUsername.setText("?");
        this.jLabelStationInfoPassword.setText("?");
    }

    public void fillStationConfigFileTree(String config) {
        JsonElement root = new JsonParser().parse(config);
        CustomTreeNode currentUserInfoTreeRootNode;
        try {
            currentUserInfoTreeRootNode = new CustomTreeNode(null, -1, root);
            jTreeStationConfigFile.setModel(new DefaultTreeModel(currentUserInfoTreeRootNode));
            //setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(EditSiteDetailsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ======= SITE INFO ==============
    private void fillSiteInfoFrame() {

        fillLicenseTypesDropdown();
        fillSiteInfoProductsDropdown();
        jTextFieldCurrencyCode.setText(site.getCurrencyCode());
        jTextFieldSFOpportunity.setText(site.getSFOppotunity());
        jTextFieldExtSFID.setText(site.getExtSFID());
        jCheckBoxEdgePlus.setSelected(site.getEdgePlus());
        jLabelCommissionDate.setText(site.getCommissionDate());

        fillSubscriptionTable(site);
        fillStationLicenseInfo(site);
        fillEnhancementsTable(site);
        fillPlantDetailsTable(site);
    }

    private StationLicense getStationLicenseFromFields() {

        String licenseTypeName = (String) this.jComboBoxLicenseTypes.getSelectedItem();
        EnumLicenseTypes licenseType = EnumLicenseTypes.getEnumFromName(licenseTypeName);

        String enabledAt = this.jTextFieldEnabledAt.getText();
        String activatedAt = this.jTextFieldActivatedAt.getText();
        String expiresAt = this.jTextFieldExpiresAt.getText();

        StationLicense sl = new StationLicense(licenseType.getName(), enabledAt, activatedAt, expiresAt);

        return sl;

    }

    private void fillSiteInfoProductsDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumProducts.getNames().toArray());
        this.jComboBoxSiteInfoProducts.setModel(comboBoxModel);
        this.jComboBoxSiteInfoProducts.setSelectedIndex(EnumProducts.edge.getDropDownIndex());
        this.jComboBoxSiteInfoProducts.setEnabled(true);
    }

    public void fillLicenseTypesDropdown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumLicenseTypes.getNames().toArray());
        this.jComboBoxLicenseTypes.setModel(comboBoxModel);
        this.jComboBoxLicenseTypes.setSelectedIndex(EnumLicenseTypes.subscription.getDropDownIndex());
        this.jComboBoxLicenseTypes.setEnabled(true);
    }

    private void fillStationLicenseInfo(Site site) {
        if (site.getStationLicense() != null) {
            StationLicense lic = site.getStationLicense();
            String licTypeName = lic.getLicenseType();
            if (licTypeName != null) {
                EnumLicenseTypes enumLicType = EnumLicenseTypes.getEnumFromName(licTypeName);
                this.jComboBoxLicenseTypes.setSelectedIndex(enumLicType.getDropDownIndex());
            }
            this.jTextFieldEnabledAt.setText(lic.getSoftwareEnabledAt());
            this.jTextFieldActivatedAt.setText(lic.getSoftwareEnabledAt());
            this.jTextFieldExpiresAt.setText(lic.getExpiresAt());

        } else {
            this.jComboBoxLicenseTypes.setSelectedIndex(EnumLicenseTypes.subscription.getDropDownIndex());
            this.jTextFieldEnabledAt.setText("");
            this.jTextFieldActivatedAt.setText("");
            this.jTextFieldExpiresAt.setText("");
        }
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

    private void fillPlantDetailsTable(Site site) {
        jTablePlantDetails.setModel(new MapTableModel(site.getPlantDetails()));
        jTablePlantDetails.setDefaultRenderer(Object.class, new MapTableCellRenderer());
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

    // ========= CONTACTS ===========
    private void fillContactsFrame() {
        fillAddressTable(site);
        fillContactsTable(site);
    }

    private void fillContactDetails(Contact contact) {
        if (contact.getPhones() == null) {
            contact.setPhones(new ArrayList<Phone>());
        }

        jTableContactPhones.setModel(new ContactPhoneTableModel(contact.getPhones()));
        jTableContactPhones.setDefaultRenderer(Object.class, new ContactPhoneTableCellRenderer());

    }

    // ========= RULES INFO ===========
    private void fillRulesTab(RuleInfoResponse resp) {
        this.jTableListOfRules.setModel(new RuleListTableModel(resp.getRules()));
        this.jTableListOfRules.setDefaultRenderer(Object.class, new RulesListTableCellRenderer());
        this.jTableListOfRules.setAutoCreateRowSorter(true);
    }

    private void fillRuleMetatDataPanel(RuleInRulesResponse ruleInfo) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String rulesResponseAsString = mapper.writeValueAsString(ruleInfo);
            JsonElement root = new JsonParser().parse(rulesResponseAsString);
            CustomTreeNode rulesInfoTree = new CustomTreeNode(null, -1, root);

            jTreeRulesInfo.setModel(new DefaultTreeModel(rulesInfoTree));
            for (int i = 0; i < jTreeRulesInfo.getRowCount(); i++) {
                jTreeRulesInfo.expandRow(i);
            }
        } catch (Exception ex) {
            Logger.getLogger(EditSiteDetailsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // ========= REPORTS ==========
    private void fillReportsFrame() {
        controller.getReportsList(site.getSid(), false); //TBD - Approved only flag being used????
    }

    private void fillReportsTable(List<MonthlyReportItem> reportItems) {
        this.jTableReportsList.setModel(new ReportsListTableModel(reportItems));
        this.jTableReportsList.setDefaultRenderer(Object.class, new ReportsListTableCellRenderer());
        this.jTableReportsList.setAutoCreateRowSorter(true);
    }

    private void fillReportsTree(String json) {
        ObjectMapper mapper = new ObjectMapper();
        //String json;
        try {
            //json = mapper.writeValueAsString(reportsList);
            JsonElement root = new JsonParser().parse(json);
            CustomTreeNode reportSchemaTreeNode = new CustomTreeNode(null, -1, root);
            jTreeReportSchema.setModel(new DefaultTreeModel(reportSchemaTreeNode));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(EditSiteDetailsFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EditSiteDetailsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanelTotalPanel = new javax.swing.JPanel();
        jPanelTopPanel = new javax.swing.JPanel();
        jLabelStationId = new javax.swing.JLabel();
        jTextFieldSiteName = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelSiteSid = new javax.swing.JLabel();
        jLabelSiteOrCustomerLabel = new javax.swing.JLabel();
        jTabbedPaneAllTabs = new javax.swing.JTabbedPane();
        jPanelHistory = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanelLeft = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanelQueryParameters = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldHistoryStartDate = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextFieldHistoryEndDate = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxHistoryResolution = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jSpinnerHistoryPrec = new javax.swing.JSpinner();
        jButtonHistoryQuery = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jLabelUTCStartDate = new javax.swing.JLabel();
        jLabelUTCEndDate = new javax.swing.JLabel();
        jCheckBoxAppendLiveData = new javax.swing.JCheckBox();
        jLabel36 = new javax.swing.JLabel();
        jSpinnerPollInterval = new javax.swing.JSpinner();
        jToggleHistoryPollLiveData = new javax.swing.JToggleButton();
        jLabelHistoryLiveId = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jTextFieldStaleThresh = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jCheckBoxSum = new javax.swing.JCheckBox();
        jCheckBoxAvg = new javax.swing.JCheckBox();
        jCheckBoxCount = new javax.swing.JCheckBox();
        jCheckBoxSparseData = new javax.swing.JCheckBox();
        jLabelTimeZone = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jComboBoxReportMonth = new javax.swing.JComboBox<>();
        jComboBoxReportYear = new javax.swing.JComboBox<>();
        jComboBoxDateType = new javax.swing.JComboBox<>();
        jPanelSytheticJaceSettings = new javax.swing.JPanel();
        jButtonHistoryToTesla = new javax.swing.JButton();
        jButtonHistoryChart = new javax.swing.JButton();
        jTextFieldHistoryMaxPoints = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jButtonHistoryPushLiveData = new javax.swing.JButton();
        jButtonPushXLS = new javax.swing.JButton();
        jButtonOptimizationLivePush = new javax.swing.JButton();
        jButtonPushDataForLineage = new javax.swing.JButton();
        jButtonReportVerification = new javax.swing.JButton();
        jPanelHistoryQueryResults = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTableHistoryQueryResults = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        jLabelHistoryQueryResultsSid = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabelHistoryResultsTimezone = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabelQueryResultsReso = new javax.swing.JLabel();
        jPanelHistoryStats = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTableDPHistoryAverages = new javax.swing.JTable();
        jPanelDatapointsList = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jTextFieldHistoryDPFilter = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jCheckBoxUseRegEx = new javax.swing.JCheckBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTableDatapointsList = new javax.swing.JTable();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTableDatapointsAssociations = new javax.swing.JTable();
        jComboBoxEquipType = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jButtonClearFilter = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabelHistoryNumberPoints = new javax.swing.JLabel();
        jLabelNumHistoryPointsSelected = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanelDPConfig = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabelCommProtocol = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabelLastUpdated = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jComboBoxDPConfigGroupType = new javax.swing.JComboBox();
        jLabel38 = new javax.swing.JLabel();
        jComboBoxDPConfigSubGroup = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabelDPConfigPointCount = new javax.swing.JLabel();
        jToggleButtonViewShortList = new javax.swing.JToggleButton();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTableSiteDPConfig = new javax.swing.JTable();
        jPanelGraphNodes = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jTextFieldNodeSid = new javax.swing.JTextField();
        jButtonNodeQuery = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTreeEquipProps = new javax.swing.JTree();
        jPanelAlarms = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableAlarms = new javax.swing.JTable();
        jButtonEditAlarms = new javax.swing.JButton();
        jPanelSiteTrend = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldSiteTrendStartTime = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldSiteTrendEndTime = new javax.swing.JTextField();
        jComboBoxSiteTrendResolutions = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jButtonSiteTrendQuery = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTreeSiteTrend = new javax.swing.JTree();
        jComboBoxSiteTrendKPI = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jButtonSiteTrendChart = new javax.swing.JButton();
        jPanelSiteInfo = new javax.swing.JPanel();
        jPanelBasicInfo = new javax.swing.JPanel();
        jTextFieldExtSFID = new javax.swing.JTextField();
        jLabelCheckForUpdatesMessage = new javax.swing.JLabel();
        jTextFieldSFOpportunity = new javax.swing.JTextField();
        jLabelCheckForUpdatesTimestamp = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxSiteInfoProducts = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldCurrencyCode = new javax.swing.JTextField();
        jButtonUpdate = new javax.swing.JButton();
        jButtonCheckForUpdates = new javax.swing.JButton();
        jCheckBoxForceUpdate = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jLabelStationHostID = new javax.swing.JLabel();
        jCheckBoxEdgePlus = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        jLabelCommissionDate = new javax.swing.JLabel();
        jPanelStationLicense = new javax.swing.JPanel();
        jTextFieldExpiresAt = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldActivatedAt = new javax.swing.JTextField();
        jComboBoxLicenseTypes = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldEnabledAt = new javax.swing.JTextField();
        jSplitPane3 = new javax.swing.JSplitPane();
        jSplitPaneSubAndEnhance = new javax.swing.JSplitPane();
        jPanelSubscriptions = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSubscriptions = new javax.swing.JTable();
        jPanelEnhancements = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableEnhancements = new javax.swing.JTable();
        jPanelPlantDetails = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTablePlantDetails = new javax.swing.JTable();
        jPanelContactInfo = new javax.swing.JPanel();
        jPanelAddress = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableAddress = new javax.swing.JTable();
        jPanelContacts = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableContacts = new javax.swing.JTable();
        jPanelSelectedContactDetails = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTableContactPhones = new javax.swing.JTable();
        jPanelStationInfo = new javax.swing.JPanel();
        jPanelDownloads = new javax.swing.JPanel();
        jButtonGetPointsConfig = new javax.swing.JButton();
        jButtonGetPortalParams = new javax.swing.JButton();
        jButtonGetConfigProfile = new javax.swing.JButton();
        jButtonGetConfigStatus = new javax.swing.JButton();
        jButtonGetStationBogFile = new javax.swing.JButton();
        jButtonGetDefaultParams = new javax.swing.JButton();
        jButtonGetDatapointsXLS = new javax.swing.JButton();
        jPanelActivation = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButtonCheckActivationStatus = new javax.swing.JButton();
        jLabelStationInfoUsername = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabelStationActivationCode = new javax.swing.JLabel();
        jLabelActivationAvailability = new javax.swing.JLabel();
        jButtonTestStationLogin = new javax.swing.JButton();
        jButtonActivateStation = new javax.swing.JButton();
        jButtonActivateSite = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabelStationInfoPassword = new javax.swing.JLabel();
        jTextFieldStationUserToken = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldStationHostID = new javax.swing.JTextField();
        jPanelUploads = new javax.swing.JPanel();
        jButtonPutAlarmChanges = new javax.swing.JButton();
        jButtonPutConfigStatus = new javax.swing.JButton();
        jButtonPostLogHistory = new javax.swing.JButton();
        jButtonPostStationsAuditLog = new javax.swing.JButton();
        jButtonValidateLocalFiles = new javax.swing.JButton();
        jButtonPutDatapointHistory = new javax.swing.JButton();
        jPanelJsonTree = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTreeStationConfigFile = new javax.swing.JTree();
        jPanelLiveData = new javax.swing.JPanel();
        jButtonPutHeartbeat = new javax.swing.JButton();
        jCheckBoxIncludeStationId = new javax.swing.JCheckBox();
        jCheckBoxUseStationToken = new javax.swing.JCheckBox();
        jPanelRulesInfo = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jSplitPane4 = new javax.swing.JSplitPane();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTreeRulesInfo = new javax.swing.JTree();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTableListOfRules = new javax.swing.JTable();
        jButtonRulesInfoRefresh = new javax.swing.JButton();
        jPanelViews = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jComboBoxPageViewTypes = new javax.swing.JComboBox<>();
        jPanelOuterPageViewPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeViewsPageData = new javax.swing.JTree();
        jButtonViewLiveData = new javax.swing.JButton();
        jPanelReportsTab = new javax.swing.JPanel();
        jSplitPane5 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableReportsList = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTreeReportSchema = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Edit Site Details");

        jLabelStationId.setText("*stationId*");

        jTextFieldSiteName.setText("jTextField2");

        jLabel26.setText("Station Id:");

        jLabel2.setText("Site Name:");

        jLabelSiteSid.setText("*SiteSid*");

        jLabelSiteOrCustomerLabel.setText("Site Sid:");

        javax.swing.GroupLayout jPanelTopPanelLayout = new javax.swing.GroupLayout(jPanelTopPanel);
        jPanelTopPanel.setLayout(jPanelTopPanelLayout);
        jPanelTopPanelLayout.setHorizontalGroup(
            jPanelTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSiteOrCustomerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSiteSid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelStationId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanelTopPanelLayout.setVerticalGroup(
            jPanelTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSiteOrCustomerLabel)
                    .addComponent(jLabelSiteSid)
                    .addComponent(jLabel26)
                    .addComponent(jLabelStationId)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldSiteName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane2.setResizeWeight(0.4);

        jPanelQueryParameters.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Query Parameters"));

        jLabel18.setText("Start Date:");

        jTextFieldHistoryStartDate.setText("1111-22-33T44:55:66.777-88:99");
        jTextFieldHistoryStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHistoryStartDateActionPerformed(evt);
            }
        });

        jLabel27.setText("End Date:");

        jTextFieldHistoryEndDate.setText("1111-22-33T44:55:66.777-88:99");
        jTextFieldHistoryEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHistoryEndDateActionPerformed(evt);
            }
        });

        jLabel14.setText("Reso:");

        jComboBoxHistoryResolution.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel19.setText("Prec:");

        jButtonHistoryQuery.setText("Query");
        jButtonHistoryQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistoryQueryActionPerformed(evt);
            }
        });

        jLabel40.setText("TZ:");

        jLabelUTCStartDate.setText("1111-22-33T44:55:666.Z (UTC)");

        jLabelUTCEndDate.setText("1111-22-33T44:55:666.Z (UTC)");

        jCheckBoxAppendLiveData.setText("Append Live");
        jCheckBoxAppendLiveData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAppendLiveDataActionPerformed(evt);
            }
        });

        jLabel36.setText("secs:");

        jToggleHistoryPollLiveData.setText("Poll");
        jToggleHistoryPollLiveData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleHistoryPollLiveDataActionPerformed(evt);
            }
        });

        jLabelHistoryLiveId.setText("*history_live_id*");

        jLabel35.setText("Stale Thresh:");

        jTextFieldStaleThresh.setText("jTextField1");

        jLabel43.setText("(sec)");

        jCheckBoxSum.setText("Sum");

        jCheckBoxAvg.setText("Avg");

        jCheckBoxCount.setText("Count");

        jCheckBoxSparseData.setText("SparseData?");

        jLabelTimeZone.setText("*timeZone*");

        jLabel37.setText("Report:");

        jComboBoxReportMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxReportYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxDateType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanelQueryParametersLayout = new javax.swing.GroupLayout(jPanelQueryParameters);
        jPanelQueryParameters.setLayout(jPanelQueryParametersLayout);
        jPanelQueryParametersLayout.setHorizontalGroup(
            jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                        .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelQueryParametersLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldHistoryEndDate))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelQueryParametersLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldHistoryStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUTCStartDate)
                            .addComponent(jLabelUTCEndDate)))
                    .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                        .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                                .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                                        .addComponent(jCheckBoxAppendLiveData)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jToggleHistoryPollLiveData))
                                    .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                                        .addComponent(jCheckBoxSum)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckBoxAvg)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckBoxCount)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSpinnerPollInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jCheckBoxSparseData)))
                            .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxHistoryResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinnerHistoryPrec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTimeZone, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                                .addComponent(jLabelHistoryLiveId)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonHistoryQuery))
                            .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                                .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldStaleThresh, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel43))
                                    .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBoxReportMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBoxReportYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBoxDateType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanelQueryParametersLayout.setVerticalGroup(
            jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQueryParametersLayout.createSequentialGroup()
                        .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextFieldHistoryStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelUTCStartDate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jTextFieldHistoryEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelUTCEndDate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jComboBoxHistoryResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jSpinnerHistoryPrec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40)
                            .addComponent(jLabelTimeZone)
                            .addComponent(jLabel37)
                            .addComponent(jComboBoxReportMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxReportYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxDateType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldStaleThresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43)
                            .addComponent(jLabel35)
                            .addComponent(jCheckBoxSum)
                            .addComponent(jCheckBoxAvg)
                            .addComponent(jCheckBoxCount)
                            .addComponent(jCheckBoxSparseData))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelQueryParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxAppendLiveData)
                            .addComponent(jLabel36)
                            .addComponent(jSpinnerPollInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleHistoryPollLiveData)
                            .addComponent(jLabelHistoryLiveId))
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelQueryParametersLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonHistoryQuery)))
                .addContainerGap())
        );

        jPanelSytheticJaceSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Push History"));

        jButtonHistoryToTesla.setText("Push to Tesla");
        jButtonHistoryToTesla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistoryToTeslaActionPerformed(evt);
            }
        });

        jButtonHistoryChart.setText("Chart");
        jButtonHistoryChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistoryChartActionPerformed(evt);
            }
        });

        jTextFieldHistoryMaxPoints.setText("jTextField1");
        jTextFieldHistoryMaxPoints.setPreferredSize(new java.awt.Dimension(100, 26));
        jTextFieldHistoryMaxPoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHistoryMaxPointsActionPerformed(evt);
            }
        });

        jLabel39.setText("Max pts:");
        jLabel39.setToolTipText("Plotting more timestamps (up the the max number of timestamps in the query results) slows the chart down but shows a smoother chart. Try a number around 100.");

        jButtonHistoryPushLiveData.setText("Live...");
        jButtonHistoryPushLiveData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistoryPushLiveDataActionPerformed(evt);
            }
        });

        jButtonPushXLS.setText("Calc...");
        jButtonPushXLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPushXLSActionPerformed(evt);
            }
        });

        jButtonOptimizationLivePush.setText("Opt...");
        jButtonOptimizationLivePush.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOptimizationLivePushActionPerformed(evt);
            }
        });

        jButtonPushDataForLineage.setText("Lineage...");
        jButtonPushDataForLineage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPushDataForLineageActionPerformed(evt);
            }
        });

        jButtonReportVerification.setText("ReportVer");
        jButtonReportVerification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReportVerificationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSytheticJaceSettingsLayout = new javax.swing.GroupLayout(jPanelSytheticJaceSettings);
        jPanelSytheticJaceSettings.setLayout(jPanelSytheticJaceSettingsLayout);
        jPanelSytheticJaceSettingsLayout.setHorizontalGroup(
            jPanelSytheticJaceSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSytheticJaceSettingsLayout.createSequentialGroup()
                .addComponent(jButtonPushXLS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonHistoryToTesla)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonHistoryPushLiveData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOptimizationLivePush)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPushDataForLineage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldHistoryMaxPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonHistoryChart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonReportVerification)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSytheticJaceSettingsLayout.setVerticalGroup(
            jPanelSytheticJaceSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSytheticJaceSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSytheticJaceSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonHistoryToTesla)
                    .addComponent(jButtonHistoryChart)
                    .addComponent(jTextFieldHistoryMaxPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(jButtonHistoryPushLiveData)
                    .addComponent(jButtonPushXLS)
                    .addComponent(jButtonOptimizationLivePush)
                    .addComponent(jButtonPushDataForLineage)
                    .addComponent(jButtonReportVerification))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelHistoryQueryResults.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "History Query Results"));

        jScrollPane16.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane16.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTableHistoryQueryResults.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableHistoryQueryResults.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableHistoryQueryResults.setShowGrid(true);
        jTableHistoryQueryResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableHistoryQueryResultsMousePressed(evt);
            }
        });
        jScrollPane16.setViewportView(jTableHistoryQueryResults);

        jLabel44.setText("Sid:");

        jLabelHistoryQueryResultsSid.setText("*historyResultsSid*");

        jLabel46.setText("TimeZone:");

        jLabelHistoryResultsTimezone.setText("*historyResultsTimezone*");

        jLabel45.setText("Res:");

        jLabelQueryResultsReso.setText("jLabel47");

        javax.swing.GroupLayout jPanelHistoryQueryResultsLayout = new javax.swing.GroupLayout(jPanelHistoryQueryResults);
        jPanelHistoryQueryResults.setLayout(jPanelHistoryQueryResultsLayout);
        jPanelHistoryQueryResultsLayout.setHorizontalGroup(
            jPanelHistoryQueryResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHistoryQueryResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHistoryQueryResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16)
                    .addGroup(jPanelHistoryQueryResultsLayout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelHistoryQueryResultsSid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelHistoryResultsTimezone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelQueryResultsReso)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelHistoryQueryResultsLayout.setVerticalGroup(
            jPanelHistoryQueryResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHistoryQueryResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHistoryQueryResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jLabelHistoryQueryResultsSid)
                    .addComponent(jLabel46)
                    .addComponent(jLabelHistoryResultsTimezone)
                    .addComponent(jLabel45)
                    .addComponent(jLabelQueryResultsReso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelHistoryStats.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTableDPHistoryAverages.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableDPHistoryAverages.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableDPHistoryAverages.setShowGrid(true);
        jScrollPane12.setViewportView(jTableDPHistoryAverages);

        javax.swing.GroupLayout jPanelHistoryStatsLayout = new javax.swing.GroupLayout(jPanelHistoryStats);
        jPanelHistoryStats.setLayout(jPanelHistoryStatsLayout);
        jPanelHistoryStatsLayout.setHorizontalGroup(
            jPanelHistoryStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHistoryStatsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12)
                .addContainerGap())
        );
        jPanelHistoryStatsLayout.setVerticalGroup(
            jPanelHistoryStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHistoryStatsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelHistoryStats, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelHistoryQueryResults, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelSytheticJaceSettings, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelQueryParameters, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanelQueryParameters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelSytheticJaceSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelHistoryQueryResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelHistoryStats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelLeftLayout = new javax.swing.GroupLayout(jPanelLeft);
        jPanelLeft.setLayout(jPanelLeftLayout);
        jPanelLeftLayout.setHorizontalGroup(
            jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLeftLayout.setVerticalGroup(
            jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLeftLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setLeftComponent(jPanelLeft);

        jPanelDatapointsList.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datapoints List"));
        jPanelDatapointsList.setMaximumSize(new java.awt.Dimension(302, 32767));
        jPanelDatapointsList.setPreferredSize(new java.awt.Dimension(350, 302));

        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 300));

        jTextFieldHistoryDPFilter.setText("jTextField1");
        jTextFieldHistoryDPFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHistoryDPFilterActionPerformed(evt);
            }
        });

        jLabel42.setText("Filter:");
        jLabel42.setToolTipText("<CR> to commit");

        jCheckBoxUseRegEx.setText("Use RegEx");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldHistoryDPFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBoxUseRegEx)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldHistoryDPFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(jCheckBoxUseRegEx)))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(118, 118, 118))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jTableDatapointsList.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableDatapointsList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableDatapointsList.setShowGrid(true);
        jTableDatapointsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableDatapointsListMousePressed(evt);
            }
        });
        jScrollPane14.setViewportView(jTableDatapointsList);

        jSplitPane1.setTopComponent(jScrollPane14);

        jTableDatapointsAssociations.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane19.setViewportView(jTableDatapointsAssociations);

        jSplitPane1.setRightComponent(jScrollPane19);

        jComboBoxEquipType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel33.setText("EquipType");

        jButtonClearFilter.setText("Clear Filter");
        jButtonClearFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addGap(13, 13, 13)
                                .addComponent(jComboBoxEquipType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonClearFilter)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxEquipType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jButtonClearFilter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel34.setText("#Pts: ");

        jLabelHistoryNumberPoints.setText("*numpts*");

        jLabelNumHistoryPointsSelected.setText("(#selctd)");

        jButton1.setText("Edit Points");
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
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelHistoryNumberPoints)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelNumHistoryPointsSelected, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelHistoryNumberPoints, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelNumHistoryPointsSelected)
                    .addComponent(jButton1)))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanelDatapointsListLayout = new javax.swing.GroupLayout(jPanelDatapointsList);
        jPanelDatapointsList.setLayout(jPanelDatapointsListLayout);
        jPanelDatapointsListLayout.setHorizontalGroup(
            jPanelDatapointsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatapointsListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDatapointsListLayout.setVerticalGroup(
            jPanelDatapointsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatapointsListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(jPanelDatapointsList);

        javax.swing.GroupLayout jPanelHistoryLayout = new javax.swing.GroupLayout(jPanelHistory);
        jPanelHistory.setLayout(jPanelHistoryLayout);
        jPanelHistoryLayout.setHorizontalGroup(
            jPanelHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1249, Short.MAX_VALUE)
        );
        jPanelHistoryLayout.setVerticalGroup(
            jPanelHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHistoryLayout.createSequentialGroup()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );

        jTabbedPaneAllTabs.addTab("History", jPanelHistory);

        jLabel20.setText("Comm Protocol:");

        jLabelCommProtocol.setText("*commProtocol*");

        jLabel22.setText("LastUpdated:");

        jLabelLastUpdated.setText("*lastUpdated*");

        jLabel24.setText("GroupType:");

        jComboBoxDPConfigGroupType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxDPConfigGroupType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDPConfigGroupTypeActionPerformed(evt);
            }
        });

        jLabel38.setText("SubGroup:");

        jComboBoxDPConfigSubGroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("Point Count:");

        jLabelDPConfigPointCount.setText("*dpConfigPointCount*");

        jToggleButtonViewShortList.setText("View Short List");
        jToggleButtonViewShortList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonViewShortListActionPerformed(evt);
            }
        });

        jScrollPane17.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane17.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTableSiteDPConfig.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableSiteDPConfig.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableSiteDPConfig.setShowGrid(true);
        jTableSiteDPConfig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableSiteDPConfigMousePressed(evt);
            }
        });
        jScrollPane17.setViewportView(jTableSiteDPConfig);

        javax.swing.GroupLayout jPanelDPConfigLayout = new javax.swing.GroupLayout(jPanelDPConfig);
        jPanelDPConfig.setLayout(jPanelDPConfigLayout);
        jPanelDPConfigLayout.setHorizontalGroup(
            jPanelDPConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDPConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDPConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDPConfigLayout.createSequentialGroup()
                        .addGroup(jPanelDPConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDPConfigLayout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelCommProtocol)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelLastUpdated))
                            .addGroup(jPanelDPConfigLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelDPConfigPointCount)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelDPConfigLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxDPConfigGroupType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxDPConfigSubGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButtonViewShortList))
                    .addComponent(jScrollPane17))
                .addContainerGap())
        );
        jPanelDPConfigLayout.setVerticalGroup(
            jPanelDPConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDPConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDPConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabelCommProtocol)
                    .addComponent(jLabel22)
                    .addComponent(jLabelLastUpdated))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDPConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jComboBoxDPConfigGroupType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(jComboBoxDPConfigSubGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonViewShortList))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDPConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabelDPConfigPointCount))
                .addContainerGap())
        );

        jTabbedPaneAllTabs.addTab("DP Config", jPanelDPConfig);

        jLabel32.setText("Sid:");

        jTextFieldNodeSid.setText("jTextField1");

        jButtonNodeQuery.setText("Graph Query");
        jButtonNodeQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNodeQueryActionPerformed(evt);
            }
        });

        jScrollPane15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nodes"));
        jScrollPane15.setViewportView(jTreeEquipProps);

        javax.swing.GroupLayout jPanelGraphNodesLayout = new javax.swing.GroupLayout(jPanelGraphNodes);
        jPanelGraphNodes.setLayout(jPanelGraphNodesLayout);
        jPanelGraphNodesLayout.setHorizontalGroup(
            jPanelGraphNodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGraphNodesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelGraphNodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane15)
                    .addGroup(jPanelGraphNodesLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNodeSid, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNodeQuery)
                        .addGap(0, 504, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelGraphNodesLayout.setVerticalGroup(
            jPanelGraphNodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGraphNodesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelGraphNodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jTextFieldNodeSid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNodeQuery))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneAllTabs.addTab("Graph Nodes", jPanelGraphNodes);

        jTableAlarms.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableAlarms.setShowGrid(true);
        jTableAlarms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableAlarmsMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableAlarmsMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(jTableAlarms);

        jButtonEditAlarms.setText("Edit Alarms");
        jButtonEditAlarms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditAlarmsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAlarmsLayout = new javax.swing.GroupLayout(jPanelAlarms);
        jPanelAlarms.setLayout(jPanelAlarmsLayout);
        jPanelAlarmsLayout.setHorizontalGroup(
            jPanelAlarmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlarmsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1237, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanelAlarmsLayout.createSequentialGroup()
                .addComponent(jButtonEditAlarms)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelAlarmsLayout.setVerticalGroup(
            jPanelAlarmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlarmsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEditAlarms))
        );

        jTabbedPaneAllTabs.addTab("Alarms", jPanelAlarms);

        jLabel4.setText("Start Time:");

        jTextFieldSiteTrendStartTime.setText("jTextField2");

        jLabel5.setText("End Time:");

        jTextFieldSiteTrendEndTime.setText("jTextField4");

        jComboBoxSiteTrendResolutions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxSiteTrendResolutions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSiteTrendResolutionsActionPerformed(evt);
            }
        });

        jLabel8.setText("Resolution:");

        jButtonSiteTrendQuery.setText("Query");
        jButtonSiteTrendQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSiteTrendQueryActionPerformed(evt);
            }
        });

        jScrollPane9.setViewportView(jTreeSiteTrend);

        jComboBoxSiteTrendKPI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("KPI:");

        jButtonSiteTrendChart.setText("Chart");
        jButtonSiteTrendChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSiteTrendChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSiteTrendLayout = new javax.swing.GroupLayout(jPanelSiteTrend);
        jPanelSiteTrend.setLayout(jPanelSiteTrendLayout);
        jPanelSiteTrendLayout.setHorizontalGroup(
            jPanelSiteTrendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSiteTrendLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSiteTrendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9)
                    .addGroup(jPanelSiteTrendLayout.createSequentialGroup()
                        .addGroup(jPanelSiteTrendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelSiteTrendLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldSiteTrendStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelSiteTrendLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldSiteTrendEndTime)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 573, Short.MAX_VALUE)
                        .addGroup(jPanelSiteTrendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSiteTrendLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxSiteTrendResolutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSiteTrendLayout.createSequentialGroup()
                                .addComponent(jButtonSiteTrendChart)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxSiteTrendKPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSiteTrendQuery)))))
                .addContainerGap())
        );
        jPanelSiteTrendLayout.setVerticalGroup(
            jPanelSiteTrendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSiteTrendLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSiteTrendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldSiteTrendStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxSiteTrendResolutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSiteTrendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldSiteTrendEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSiteTrendQuery)
                    .addComponent(jComboBoxSiteTrendKPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jButtonSiteTrendChart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneAllTabs.addTab("Site Trend", jPanelSiteTrend);

        jPanelBasicInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Basic Site Info"));

        jTextFieldExtSFID.setText("jTextField2");

        jLabelCheckForUpdatesMessage.setText("*message*");

        jTextFieldSFOpportunity.setText("jTextField1");

        jLabelCheckForUpdatesTimestamp.setText("*timestamp*");

        jLabel13.setText("External Sales Force ID:");

        jLabel1.setText("Check for Updates: ");

        jComboBoxSiteInfoProducts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        jComboBoxSiteInfoProducts.setPreferredSize(new java.awt.Dimension(100, 27));
        jComboBoxSiteInfoProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSiteInfoProductsActionPerformed(evt);
            }
        });

        jLabel25.setText("Message:");

        jLabel7.setText("SF Opportunity: ");

        jLabel6.setText("Product:");

        jLabel12.setText("Currency Code:");

        jTextFieldCurrencyCode.setText("jTextField1");

        jButtonUpdate.setText("Update Site");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonCheckForUpdates.setText("Check for Site Updates");
        jButtonCheckForUpdates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCheckForUpdatesActionPerformed(evt);
            }
        });

        jCheckBoxForceUpdate.setText("Force update");

        jLabel10.setText("Station Host ID:");

        jLabelStationHostID.setText("*serial_number*");

        jCheckBoxEdgePlus.setText("Edge Plus Site");

        jLabel17.setText("Commission Date:");

        jLabelCommissionDate.setText("*commissionDate*");

        javax.swing.GroupLayout jPanelBasicInfoLayout = new javax.swing.GroupLayout(jPanelBasicInfo);
        jPanelBasicInfo.setLayout(jPanelBasicInfoLayout);
        jPanelBasicInfoLayout.setHorizontalGroup(
            jPanelBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBasicInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBasicInfoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelBasicInfoLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldCurrencyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonCheckForUpdates)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBoxForceUpdate))
                            .addGroup(jPanelBasicInfoLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxSiteInfoProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelStationHostID, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCheckBoxEdgePlus)
                                .addContainerGap())
                            .addGroup(jPanelBasicInfoLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldExtSFID, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelCheckForUpdatesMessage))
                            .addGroup(jPanelBasicInfoLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldSFOpportunity, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelCheckForUpdatesTimestamp))))
                    .addGroup(jPanelBasicInfoLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelCommissionDate)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanelBasicInfoLayout.setVerticalGroup(
            jPanelBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBasicInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxSiteInfoProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabelStationHostID)
                    .addComponent(jCheckBoxEdgePlus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldSFOpportunity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelCheckForUpdatesTimestamp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldExtSFID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jLabelCheckForUpdatesMessage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldCurrencyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCheckForUpdates)
                    .addComponent(jButtonUpdate)
                    .addComponent(jCheckBoxForceUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabelCommissionDate))
                .addContainerGap())
        );

        jPanelStationLicense.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "StationLicense"));

        jTextFieldExpiresAt.setText("jTextField4");

        jLabel21.setText("LicenseType:");

        jTextFieldActivatedAt.setText("jTextField2");

        jComboBoxLicenseTypes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel31.setText("Activated At:");

        jLabel29.setText("Expires At:");

        jLabel23.setText("Enabled At:");

        jTextFieldEnabledAt.setText("jTextField2");

        javax.swing.GroupLayout jPanelStationLicenseLayout = new javax.swing.GroupLayout(jPanelStationLicense);
        jPanelStationLicense.setLayout(jPanelStationLicenseLayout);
        jPanelStationLicenseLayout.setHorizontalGroup(
            jPanelStationLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStationLicenseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStationLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStationLicenseLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxLicenseTypes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelStationLicenseLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldExpiresAt))
                    .addGroup(jPanelStationLicenseLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEnabledAt))
                    .addGroup(jPanelStationLicenseLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldActivatedAt)))
                .addContainerGap())
        );
        jPanelStationLicenseLayout.setVerticalGroup(
            jPanelStationLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStationLicenseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStationLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jComboBoxLicenseTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelStationLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextFieldEnabledAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelStationLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jTextFieldActivatedAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelStationLicenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jTextFieldExpiresAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setResizeWeight(0.5);

        jSplitPaneSubAndEnhance.setResizeWeight(0.5);

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelSubscriptionsLayout.setVerticalGroup(
            jPanelSubscriptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSubscriptionsLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPaneSubAndEnhance.setLeftComponent(jPanelSubscriptions);

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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelEnhancementsLayout.setVerticalGroup(
            jPanelEnhancementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEnhancementsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPaneSubAndEnhance.setRightComponent(jPanelEnhancements);

        jSplitPane3.setTopComponent(jSplitPaneSubAndEnhance);

        jPanelPlantDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Plant Details"));

        jTablePlantDetails.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane18.setViewportView(jTablePlantDetails);

        javax.swing.GroupLayout jPanelPlantDetailsLayout = new javax.swing.GroupLayout(jPanelPlantDetails);
        jPanelPlantDetails.setLayout(jPanelPlantDetailsLayout);
        jPanelPlantDetailsLayout.setHorizontalGroup(
            jPanelPlantDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPlantDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane18)
                .addContainerGap())
        );
        jPanelPlantDetailsLayout.setVerticalGroup(
            jPanelPlantDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPlantDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane3.setRightComponent(jPanelPlantDetails);

        javax.swing.GroupLayout jPanelSiteInfoLayout = new javax.swing.GroupLayout(jPanelSiteInfo);
        jPanelSiteInfo.setLayout(jPanelSiteInfoLayout);
        jPanelSiteInfoLayout.setHorizontalGroup(
            jPanelSiteInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSiteInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSiteInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane3)
                    .addGroup(jPanelSiteInfoLayout.createSequentialGroup()
                        .addComponent(jPanelBasicInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelStationLicense, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelSiteInfoLayout.setVerticalGroup(
            jPanelSiteInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSiteInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSiteInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelBasicInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelStationLicense, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane3)
                .addContainerGap())
        );

        jTabbedPaneAllTabs.addTab("Site Info", jPanelSiteInfo);

        jPanelAddress.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Address"));

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

        javax.swing.GroupLayout jPanelAddressLayout = new javax.swing.GroupLayout(jPanelAddress);
        jPanelAddress.setLayout(jPanelAddressLayout);
        jPanelAddressLayout.setHorizontalGroup(
            jPanelAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelAddressLayout.setVerticalGroup(
            jPanelAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelContacts.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Contacts"));

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

        javax.swing.GroupLayout jPanelContactsLayout = new javax.swing.GroupLayout(jPanelContacts);
        jPanelContacts.setLayout(jPanelContactsLayout);
        jPanelContactsLayout.setHorizontalGroup(
            jPanelContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContactsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8)
                .addContainerGap())
        );
        jPanelContactsLayout.setVerticalGroup(
            jPanelContactsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContactsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Phones"));

        jTableContactPhones.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableContactPhones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableContactPhonesMousePressed(evt);
            }
        });
        jScrollPane13.setViewportView(jTableContactPhones);

        javax.swing.GroupLayout jPanelSelectedContactDetailsLayout = new javax.swing.GroupLayout(jPanelSelectedContactDetails);
        jPanelSelectedContactDetails.setLayout(jPanelSelectedContactDetailsLayout);
        jPanelSelectedContactDetailsLayout.setHorizontalGroup(
            jPanelSelectedContactDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSelectedContactDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13)
                .addContainerGap())
        );
        jPanelSelectedContactDetailsLayout.setVerticalGroup(
            jPanelSelectedContactDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSelectedContactDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelContactInfoLayout = new javax.swing.GroupLayout(jPanelContactInfo);
        jPanelContactInfo.setLayout(jPanelContactInfoLayout);
        jPanelContactInfoLayout.setHorizontalGroup(
            jPanelContactInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContactInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelContactInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelContacts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelSelectedContactDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelContactInfoLayout.setVerticalGroup(
            jPanelContactInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContactInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelContacts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelSelectedContactDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneAllTabs.addTab("Contacts", jPanelContactInfo);

        jPanelDownloads.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Downloads"));

        jButtonGetPointsConfig.setText("Points Config");
        jButtonGetPointsConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetPointsConfigActionPerformed(evt);
            }
        });

        jButtonGetPortalParams.setText("Portal Parameters");
        jButtonGetPortalParams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetPortalParamsActionPerformed(evt);
            }
        });

        jButtonGetConfigProfile.setText("Config Profile");
        jButtonGetConfigProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetConfigProfileActionPerformed(evt);
            }
        });

        jButtonGetConfigStatus.setText("Config Status");
        jButtonGetConfigStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetConfigStatusActionPerformed(evt);
            }
        });

        jButtonGetStationBogFile.setText("Bog");
        jButtonGetStationBogFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetStationBogFileActionPerformed(evt);
            }
        });

        jButtonGetDefaultParams.setText("Default Params");
        jButtonGetDefaultParams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetDefaultParamsActionPerformed(evt);
            }
        });

        jButtonGetDatapointsXLS.setText("Datapoint XLS");
        jButtonGetDatapointsXLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetDatapointsXLSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDownloadsLayout = new javax.swing.GroupLayout(jPanelDownloads);
        jPanelDownloads.setLayout(jPanelDownloadsLayout);
        jPanelDownloadsLayout.setHorizontalGroup(
            jPanelDownloadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDownloadsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDownloadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDownloadsLayout.createSequentialGroup()
                        .addComponent(jButtonGetConfigStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonGetDatapointsXLS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonGetDefaultParams, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDownloadsLayout.createSequentialGroup()
                        .addComponent(jButtonGetStationBogFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonGetConfigProfile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonGetPointsConfig)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonGetPortalParams)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelDownloadsLayout.setVerticalGroup(
            jPanelDownloadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDownloadsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDownloadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGetStationBogFile)
                    .addComponent(jButtonGetConfigProfile)
                    .addComponent(jButtonGetPointsConfig)
                    .addComponent(jButtonGetPortalParams))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDownloadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGetDatapointsXLS)
                    .addComponent(jButtonGetConfigStatus)
                    .addComponent(jButtonGetDefaultParams, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanelActivation.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Station Activation"));

        jLabel3.setText("Activation Code:");

        jButtonCheckActivationStatus.setText("Check Activation Availability");
        jButtonCheckActivationStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCheckActivationStatusActionPerformed(evt);
            }
        });

        jLabelStationInfoUsername.setText("*station username*");

        jLabel30.setText("Password:");

        jLabelStationActivationCode.setText("*activation code*");

        jLabelActivationAvailability.setText("*server_time*");

        jButtonTestStationLogin.setText("Test Login");
        jButtonTestStationLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestStationLoginActionPerformed(evt);
            }
        });

        jButtonActivateStation.setText("Activate Station");
        jButtonActivateStation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActivateStationActionPerformed(evt);
            }
        });

        jButtonActivateSite.setText("Get New Activation Code");
        jButtonActivateSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActivateSiteActionPerformed(evt);
            }
        });

        jLabel28.setText("Username:");

        jLabelStationInfoPassword.setText("*station password*");

        jTextFieldStationUserToken.setText("*station user token*");

        jLabel16.setText("HostID:");

        jTextFieldStationHostID.setText("jTextField1");

        javax.swing.GroupLayout jPanelActivationLayout = new javax.swing.GroupLayout(jPanelActivation);
        jPanelActivation.setLayout(jPanelActivationLayout);
        jPanelActivationLayout.setHorizontalGroup(
            jPanelActivationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActivationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelActivationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelActivationLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelStationInfoUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelStationInfoPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonTestStationLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldStationUserToken, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelActivationLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelStationActivationCode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonActivateSite)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonActivateStation))
                    .addGroup(jPanelActivationLayout.createSequentialGroup()
                        .addComponent(jButtonCheckActivationStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelActivationAvailability, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldStationHostID, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelActivationLayout.setVerticalGroup(
            jPanelActivationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelActivationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelActivationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCheckActivationStatus)
                    .addComponent(jLabelActivationAvailability)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldStationHostID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelActivationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelStationActivationCode)
                    .addComponent(jButtonActivateSite)
                    .addComponent(jButtonActivateStation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelActivationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabelStationInfoUsername)
                    .addComponent(jLabelStationInfoPassword)
                    .addComponent(jLabel30)
                    .addComponent(jButtonTestStationLogin)
                    .addComponent(jTextFieldStationUserToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelUploads.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Uploads"));

        jButtonPutAlarmChanges.setText("Alarm Changes");
        jButtonPutAlarmChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPutAlarmChangesActionPerformed(evt);
            }
        });

        jButtonPutConfigStatus.setText("Config Status");
        jButtonPutConfigStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPutConfigStatusActionPerformed(evt);
            }
        });

        jButtonPostLogHistory.setText("Log History");
        jButtonPostLogHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPostLogHistoryActionPerformed(evt);
            }
        });

        jButtonPostStationsAuditLog.setText("Audit Logs");
        jButtonPostStationsAuditLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPostStationsAuditLogActionPerformed(evt);
            }
        });

        jButtonValidateLocalFiles.setText("Validate Files");
        jButtonValidateLocalFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValidateLocalFilesActionPerformed(evt);
            }
        });

        jButtonPutDatapointHistory.setText("Datapoint History");
        jButtonPutDatapointHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPutDatapointHistoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelUploadsLayout = new javax.swing.GroupLayout(jPanelUploads);
        jPanelUploads.setLayout(jPanelUploadsLayout);
        jPanelUploadsLayout.setHorizontalGroup(
            jPanelUploadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUploadsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonValidateLocalFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPutConfigStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPutAlarmChanges)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPostStationsAuditLog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPostLogHistory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPutDatapointHistory)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelUploadsLayout.setVerticalGroup(
            jPanelUploadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUploadsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUploadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonValidateLocalFiles)
                    .addComponent(jButtonPutConfigStatus)
                    .addComponent(jButtonPutAlarmChanges)
                    .addComponent(jButtonPostStationsAuditLog)
                    .addComponent(jButtonPostLogHistory)
                    .addComponent(jButtonPutDatapointHistory))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelJsonTree.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Info"));

        jScrollPane10.setViewportView(jTreeStationConfigFile);

        javax.swing.GroupLayout jPanelJsonTreeLayout = new javax.swing.GroupLayout(jPanelJsonTree);
        jPanelJsonTree.setLayout(jPanelJsonTreeLayout);
        jPanelJsonTreeLayout.setHorizontalGroup(
            jPanelJsonTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJsonTreeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10)
                .addContainerGap())
        );
        jPanelJsonTreeLayout.setVerticalGroup(
            jPanelJsonTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJsonTreeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelLiveData.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Heartbeat"));

        jButtonPutHeartbeat.setText("Post Heartbeat");
        jButtonPutHeartbeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPutHeartbeatActionPerformed(evt);
            }
        });

        jCheckBoxIncludeStationId.setText("Include stationId");

        jCheckBoxUseStationToken.setText("Use Station Token");

        javax.swing.GroupLayout jPanelLiveDataLayout = new javax.swing.GroupLayout(jPanelLiveData);
        jPanelLiveData.setLayout(jPanelLiveDataLayout);
        jPanelLiveDataLayout.setHorizontalGroup(
            jPanelLiveDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLiveDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLiveDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLiveDataLayout.createSequentialGroup()
                        .addComponent(jCheckBoxIncludeStationId)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelLiveDataLayout.createSequentialGroup()
                        .addComponent(jCheckBoxUseStationToken)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
                        .addComponent(jButtonPutHeartbeat)))
                .addContainerGap())
        );
        jPanelLiveDataLayout.setVerticalGroup(
            jPanelLiveDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLiveDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxIncludeStationId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLiveDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxUseStationToken)
                    .addComponent(jButtonPutHeartbeat))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelStationInfoLayout = new javax.swing.GroupLayout(jPanelStationInfo);
        jPanelStationInfo.setLayout(jPanelStationInfoLayout);
        jPanelStationInfoLayout.setHorizontalGroup(
            jPanelStationInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStationInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStationInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelActivation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelUploads, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelStationInfoLayout.createSequentialGroup()
                        .addComponent(jPanelDownloads, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelLiveData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanelJsonTree, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelStationInfoLayout.setVerticalGroup(
            jPanelStationInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStationInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelActivation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelStationInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelLiveData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelDownloads, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelUploads, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelJsonTree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPaneAllTabs.addTab("Station Info", jPanelStationInfo);

        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane4.setResizeWeight(0.5);

        jScrollPane21.setBorder(javax.swing.BorderFactory.createTitledBorder("Rule Info"));
        jScrollPane21.setViewportView(jTreeRulesInfo);

        jSplitPane4.setBottomComponent(jScrollPane21);

        jScrollPane20.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Rule List"));

        jTableListOfRules.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableListOfRules.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableListOfRulesMousePressed(evt);
            }
        });
        jScrollPane20.setViewportView(jTableListOfRules);

        jSplitPane4.setLeftComponent(jScrollPane20);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane4)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonRulesInfoRefresh.setText("Refresh");
        jButtonRulesInfoRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRulesInfoRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRulesInfoLayout = new javax.swing.GroupLayout(jPanelRulesInfo);
        jPanelRulesInfo.setLayout(jPanelRulesInfoLayout);
        jPanelRulesInfoLayout.setHorizontalGroup(
            jPanelRulesInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRulesInfoLayout.createSequentialGroup()
                .addGroup(jPanelRulesInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRulesInfoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRulesInfoRefresh))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelRulesInfoLayout.setVerticalGroup(
            jPanelRulesInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRulesInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRulesInfoRefresh)
                .addContainerGap())
        );

        jTabbedPaneAllTabs.addTab("RulesInfo", jPanelRulesInfo);

        jLabel15.setText("View Type:");

        jComboBoxPageViewTypes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxPageViewTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPageViewTypesActionPerformed(evt);
            }
        });

        jPanelOuterPageViewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Page Data"));

        jScrollPane2.setViewportView(jTreeViewsPageData);

        javax.swing.GroupLayout jPanelOuterPageViewPanelLayout = new javax.swing.GroupLayout(jPanelOuterPageViewPanel);
        jPanelOuterPageViewPanel.setLayout(jPanelOuterPageViewPanelLayout);
        jPanelOuterPageViewPanelLayout.setHorizontalGroup(
            jPanelOuterPageViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOuterPageViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanelOuterPageViewPanelLayout.setVerticalGroup(
            jPanelOuterPageViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOuterPageViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonViewLiveData.setText("See Live Data");
        jButtonViewLiveData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewLiveDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelViewsLayout = new javax.swing.GroupLayout(jPanelViews);
        jPanelViews.setLayout(jPanelViewsLayout);
        jPanelViewsLayout.setHorizontalGroup(
            jPanelViewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelViewsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelViewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelViewsLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxPageViewTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1068, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelViewsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonViewLiveData))
                    .addComponent(jPanelOuterPageViewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelViewsLayout.setVerticalGroup(
            jPanelViewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelViewsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelViewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBoxPageViewTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelOuterPageViewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonViewLiveData)
                .addContainerGap())
        );

        jTabbedPaneAllTabs.addTab("Views", jPanelViews);

        jSplitPane5.setDividerLocation(400);
        jSplitPane5.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Report List"));

        jTableReportsList.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableReportsList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableReportsList.setShowGrid(true);
        jTableReportsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableReportsListMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTableReportsList);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane5.setTopComponent(jPanel4);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Report Schema"));

        jScrollPane5.setViewportView(jTreeReportSchema);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane5.setRightComponent(jPanel10);

        javax.swing.GroupLayout jPanelReportsTabLayout = new javax.swing.GroupLayout(jPanelReportsTab);
        jPanelReportsTab.setLayout(jPanelReportsTabLayout);
        jPanelReportsTabLayout.setHorizontalGroup(
            jPanelReportsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReportsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane5)
                .addContainerGap())
        );
        jPanelReportsTabLayout.setVerticalGroup(
            jPanelReportsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReportsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane5)
                .addContainerGap())
        );

        jTabbedPaneAllTabs.addTab("Reports", jPanelReportsTab);

        javax.swing.GroupLayout jPanelTotalPanelLayout = new javax.swing.GroupLayout(jPanelTotalPanel);
        jPanelTotalPanel.setLayout(jPanelTotalPanelLayout);
        jPanelTotalPanelLayout.setHorizontalGroup(
            jPanelTotalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTotalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTotalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTotalPanelLayout.createSequentialGroup()
                        .addComponent(jPanelTopPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPaneAllTabs))
                .addContainerGap())
        );
        jPanelTotalPanelLayout.setVerticalGroup(
            jPanelTotalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTotalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTopPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneAllTabs)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTotalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTotalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableSubscriptionsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSubscriptionsMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForMapTables popup = new PopupMenuForMapTables(evt, jTableSubscriptions);
        }
    }//GEN-LAST:event_jTableSubscriptionsMousePressed

    private void jButtonActivateSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActivateSiteActionPerformed

        controller.callForNewActivationCode(site.getSid());
    }//GEN-LAST:event_jButtonActivateSiteActionPerformed

    private void jComboBoxSiteInfoProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSiteInfoProductsActionPerformed

    }//GEN-LAST:event_jComboBoxSiteInfoProductsActionPerformed

    private void jTableAddressMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAddressMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForMapTables popup = new PopupMenuForMapTables(evt, jTableAddress);
        }
    }//GEN-LAST:event_jTableAddressMousePressed

    private void jTableContactsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableContactsMousePressed

        int row = jTableContacts.rowAtPoint(evt.getPoint());
        int modelIndex = jTableContacts.convertRowIndexToModel(row);
        ContactsTableModel mod = (ContactsTableModel) jTableContacts.getModel();
        Contact contact = mod.getContactAtRow(modelIndex);

        fillContactDetails(contact);

        if (evt.isPopupTrigger()) {
            PopupMenuOnTables popup = new PopupMenuOnTables(evt, jTableContacts);
        }
    }//GEN-LAST:event_jTableContactsMousePressed

    private void jButtonGetConfigStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetConfigStatusActionPerformed

        controller.getConfigurationStatus(site.getStationID());

    }//GEN-LAST:event_jButtonGetConfigStatusActionPerformed

    private void jButtonCheckActivationStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCheckActivationStatusActionPerformed
        controller.getActivationAvaiablity();
    }//GEN-LAST:event_jButtonCheckActivationStatusActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed

        setSiteFromFields();
        boolean forceUpdate = jCheckBoxForceUpdate.isSelected();
        UpdateSiteRequest usr = new UpdateSiteRequest(site);
        controller.updateSite(site.getSid(), forceUpdate, usr);
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonGetStationBogFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetStationBogFileActionPerformed

        controller.getStationBogFile(site.getStationID());

    }//GEN-LAST:event_jButtonGetStationBogFileActionPerformed

    private void jButtonActivateStationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActivateStationActionPerformed

        if (this.jTextFieldStationHostID != null && this.jTextFieldStationHostID.getText() != null) {
            StationActivateRequest req = new StationActivateRequest(site.getActivationCode(), jTextFieldStationHostID.getText());
            controller.postActivate(req);
        }
    }//GEN-LAST:event_jButtonActivateStationActionPerformed

    private void jButtonGetConfigProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetConfigProfileActionPerformed

        controller.getConfigurationProfile(site.getStationID());
    }//GEN-LAST:event_jButtonGetConfigProfileActionPerformed

    private void jButtonGetPointsConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetPointsConfigActionPerformed

        controller.getPointsConfiguration(site.getStationID());

    }//GEN-LAST:event_jButtonGetPointsConfigActionPerformed

    private void jButtonGetDefaultParamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetDefaultParamsActionPerformed

        controller.getDefaultParameters(site.getStationID());

    }//GEN-LAST:event_jButtonGetDefaultParamsActionPerformed

    private void jButtonGetPortalParamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetPortalParamsActionPerformed

        controller.getPortalParameters(site.getStationID());

    }//GEN-LAST:event_jButtonGetPortalParamsActionPerformed

    private void jButtonValidateLocalFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonValidateLocalFilesActionPerformed

        String stationId = site.getStationID();
        String bog_hash = "";
        String profile_hash = "";
        String points_hash = "";
        String default_parameters_hash = "";
        String portal_parameters_hash = "";

        StationValidateQueryParams params = new StationValidateQueryParams(
                stationId,
                bog_hash,
                profile_hash,
                points_hash,
                default_parameters_hash,
                portal_parameters_hash
        );

        controller.validateConfiguration(params);

    }//GEN-LAST:event_jButtonValidateLocalFilesActionPerformed

    private void jButtonPutConfigStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPutConfigStatusActionPerformed

        WizardStationStatus status = new WizardStationStatus();
        //controller.pudhConfigurationStatus(site.getStationID(), status);

    }//GEN-LAST:event_jButtonPutConfigStatusActionPerformed

    private void jButtonPostStationsAuditLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPostStationsAuditLogActionPerformed
        StationAuditHistory auditHistory = new StationAuditHistory();
        //controller.pushAuditHistory(auditHistory);
    }//GEN-LAST:event_jButtonPostStationsAuditLogActionPerformed

    private void jButtonPostLogHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPostLogHistoryActionPerformed
        StationLogHistory auditLogHistory = new StationLogHistory();
        //controller.pushStationLog(auditLogHistory);
    }//GEN-LAST:event_jButtonPostLogHistoryActionPerformed

    private void jButtonPutDatapointHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPutDatapointHistoryActionPerformed
        HistoryPushObject history = new HistoryPushObject();
        //controller.pushDatapointHistory(history);
    }//GEN-LAST:event_jButtonPutDatapointHistoryActionPerformed

    private void jButtonPutAlarmChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPutAlarmChangesActionPerformed
        StationAlarmPushObject alarms = new StationAlarmPushObject();
        //controller.pushAlarmChanges(alarms);
    }//GEN-LAST:event_jButtonPutAlarmChangesActionPerformed

    private void jButtonPutHeartbeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPutHeartbeatActionPerformed

        DateTime triggerTimestamp = DateTime.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        String timestamp = triggerTimestamp.toString(fmt) + ".000Z";

        String hostID = "Qnx-TITAN-EFC2-EB11-0744-2145";

        StationsHeartbeat heartBeat = new StationsHeartbeat();

        heartBeat.setTimestamp(timestamp);

        String stationID = this.jLabelStationId.getText();
        heartBeat.setStationId(stationID);

        Map points = new HashMap<>();

        points.put("utcOffset", 0);
        points.put("niagaraVersion", "4.1.27.20");
        points.put("overallCpuUsage", 9.0);
        points.put("javaVmVersion", "25.33-b02");
        points.put("isJACE", false);
        points.put("javaVmName", "Java HotSpot(TM) Embedded Client VM");

        points.put("hostId", hostID);
        points.put("osName", "QNX");
        points.put("totalPhysicalMemory", "1024MB");
        points.put("localTime", "09-Jun-16 6:03 PM UTC");
        points.put("freePhysicalMemory", "91MB");
        points.put("osVersion", "6.5.0");
        points.put("currentCpuUsage", 2.0);
        points.put("model", "TITAN");
        points.put("driverModuleVersion", "0.0.1");
        heartBeat.setPoints(points);

        String stationId = "";
        if (jCheckBoxIncludeStationId.isSelected()) {
            stationId = this.jLabelStationId.getText();
        }

        String token = "";
        if (jCheckBoxUseStationToken.isSelected()) {
            token = jTextFieldStationUserToken.getText();
        }
        controller.pushHeartbeat(stationId, token, heartBeat);
    }//GEN-LAST:event_jButtonPutHeartbeatActionPerformed

    private void jButtonSiteTrendQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSiteTrendQueryActionPerformed

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String startDateString = this.jTextFieldSiteTrendStartTime.getText();
        String endDateString = this.jTextFieldSiteTrendEndTime.getText();

        EnumResolutions res = EnumResolutions.getResolutionFromName((String) this.jComboBoxSiteTrendResolutions.getSelectedItem());

        SiteTrendAndKPIRequest siteTrendReq = new SiteTrendAndKPIRequest(
                DateTime.parse(startDateString, fmt),
                DateTime.parse(endDateString, fmt),
                res);

        controller.querySiteTrend(site.getSid(), siteTrendReq);

    }//GEN-LAST:event_jButtonSiteTrendQueryActionPerformed

    private void jButtonCheckForUpdatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCheckForUpdatesActionPerformed
        controller.callCheckForUpdates(site.getSid());
    }//GEN-LAST:event_jButtonCheckForUpdatesActionPerformed

    private void jButtonEditAlarmsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditAlarmsActionPerformed

        SiteAlarmsFrame frame = SiteAlarmsFrame.getInstance(controller, site.getSid(), this.jLabelStationId.getText());
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        controller.addModelListener(frame);

        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);

        List<String> tags = new ArrayList<>();
        tags.add("NotOptimized");
        tags.add("ServerCommunicationFailure");

        //get all alarms independent of state
        AlarmListRequest req = new AlarmListRequest(true, false, tags);

        controller.getAlarms(site.getSid());


    }//GEN-LAST:event_jButtonEditAlarmsActionPerformed

    private void jTableAlarmsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAlarmsMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForCopyingToClipboard popup = new PopupMenuForCopyingToClipboard(evt, jTableAlarms);
        }
    }//GEN-LAST:event_jTableAlarmsMousePressed

    private void jTableAlarmsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAlarmsMouseReleased

    }//GEN-LAST:event_jTableAlarmsMouseReleased

    private void jComboBoxDPConfigGroupTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDPConfigGroupTypeActionPerformed

    }//GEN-LAST:event_jComboBoxDPConfigGroupTypeActionPerformed

    private void jButtonTestStationLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestStationLoginActionPerformed
        String username = jLabelStationInfoUsername.getText();
        String password = jLabelStationInfoPassword.getText();

        TestLoginFrame frame = new TestLoginFrame(controller);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);

        controller.getOauthToken(username, password);
    }//GEN-LAST:event_jButtonTestStationLoginActionPerformed

    private void jButtonGetDatapointsXLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetDatapointsXLSActionPerformed
        controller.getPointsConfig(site.getSid(), EnumPointsListDownloadType.XLS);
    }//GEN-LAST:event_jButtonGetDatapointsXLSActionPerformed

    private void jTableSiteDPConfigMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSiteDPConfigMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuDPConfigTable popup = new PopupMenuDPConfigTable(evt, controller, this.site.getSid(), this.jTableSiteDPConfig);
        }
    }//GEN-LAST:event_jTableSiteDPConfigMousePressed

    private void jComboBoxPageViewTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPageViewTypesActionPerformed

    }//GEN-LAST:event_jComboBoxPageViewTypesActionPerformed

    private void jTableHistoryQueryResultsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableHistoryQueryResultsMousePressed

        if (evt.isPopupTrigger()) {
            PopUpMenuForHistoryResultsTable popup = new PopUpMenuForHistoryResultsTable(evt, jTableHistoryQueryResults);
        }
    }//GEN-LAST:event_jTableHistoryQueryResultsMousePressed

    private void jButtonHistoryQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistoryQueryActionPerformed

        jTableHistoryQueryResults.setModel(new DefaultTableModel());
        jTableHistoryQueryResults
                .setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

        List<DatapointsAndMetadataResponse> listOfPoints = new ArrayList<>(); //jListHistoryDatapoints.getSelectedValuesList();
        DatapointsListTableModel tableModel = (DatapointsListTableModel) (jTableDatapointsList.getModel());
        int[] selectedRowNumbers = jTableDatapointsList.getSelectedRows();
        for (int selectedRowNumber : selectedRowNumbers) {
            int modelRowNumber = jTableDatapointsList.convertRowIndexToModel(selectedRowNumber);
            DatapointsAndMetadataResponse dataRow = tableModel.getRow(modelRowNumber);
            listOfPoints.add(dataRow);
        }

        Map<String, List<String>> pointsToQuery = new HashMap<>();
        for (DatapointsAndMetadataResponse pointInfo : listOfPoints) {
            if (!pointsToQuery.containsKey(pointInfo.getSid())) {
                pointsToQuery.put(pointInfo.getSid(), new ArrayList<String>());
            }
            pointsToQuery.get(pointInfo.getSid()).add(pointInfo.getName());
        }

        if (pointsToQuery.size() > 0) {
            String resString = (String) jComboBoxHistoryResolution.getSelectedItem();
            EnumResolutions res = EnumResolutions.getResolutionFromName(resString);

            EnumAggregationType aggType = EnumAggregationType.NORMAL;
            if (jCheckBoxSum.isSelected()) {
                aggType = EnumAggregationType.SUM;
            } else if (jCheckBoxAvg.isSelected()) {
                aggType = EnumAggregationType.AVG;
            } else if (jCheckBoxCount.isSelected()) {
                aggType = EnumAggregationType.COUNT;
            }

            List<DatapointHistoriesQueryParams> listOfParams = new ArrayList<>();
            for (String sid : pointsToQuery.keySet()) {
                List<String> listOfPointNames = pointsToQuery.get(sid);
                DatapointHistoriesQueryParams params = new DatapointHistoriesQueryParams(
                        sid, siteLocalStartDate, siteLocalEndDate, res, jCheckBoxSparseData.isSelected(), listOfPointNames, aggType);
                listOfParams.add(params);
            }
            controller.getComplexDatapointHistories(listOfParams);
            this.jButtonHistoryChart.setEnabled(true);
        } else {
            this.jButtonHistoryChart.setEnabled(false);
        }


    }//GEN-LAST:event_jButtonHistoryQueryActionPerformed

    private void jButtonHistoryToTeslaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistoryToTeslaActionPerformed

        if (listOfMetadata != null && listOfMetadata.size() > 0) {
            
            /* this was from the old repush stuff before changing button behavior to push to tesla
            String timestamp = DateTime.now().toString();
            RepushFrame frame = RepushFrame.getInstance(controller, timestamp, site.getStationID(), listOfDataPointHistories);

            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            controller.addModelListener(frame);
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
            */
            
            String timestamp = DateTime.now().toString();
            PushToTeslaFrame frame = PushToTeslaFrame.getInstance(controller, timestamp, site.getStationID(), listOfMetadata);

            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            controller.addModelListener(frame);
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }
    }//GEN-LAST:event_jButtonHistoryToTeslaActionPerformed

    private void jToggleHistoryPollLiveDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleHistoryPollLiveDataActionPerformed

        if (jToggleHistoryPollLiveData.isSelected()) {
            long sec = (int) jSpinnerPollInterval.getModel().getValue();
            long interval = 1000 * sec;
            long startDelay = 0;

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        controller.getLiveData(subscriptionId);
                    } catch (Exception ex) {
                        System.out.println("oops. something went wrong with the timer");
                    }
                }
            }, startDelay, interval);

            jSpinnerPollInterval.setEnabled(false);

        } else {
            if (timer != null) {
                timer.cancel();
            }
            jSpinnerPollInterval.setEnabled(true);
        }
    }//GEN-LAST:event_jToggleHistoryPollLiveDataActionPerformed

    private void jButtonHistoryPushLiveDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistoryPushLiveDataActionPerformed
        postLiveData();
    }//GEN-LAST:event_jButtonHistoryPushLiveDataActionPerformed

    private void jComboBoxSiteTrendResolutionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSiteTrendResolutionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxSiteTrendResolutionsActionPerformed

    private void jCheckBoxAppendLiveDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAppendLiveDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxAppendLiveDataActionPerformed

    private void jButtonHistoryChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistoryChartActionPerformed

        if (listOfDataPointHistories != null && this.jTextFieldHistoryMaxPoints.getText().length() > 0) {
            int maxPoints = 100;
            try {
                maxPoints = Integer.parseInt(this.jTextFieldHistoryMaxPoints.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "max points setting not valid int", "oops...", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (maxPoints <= 0) {
                JOptionPane.showMessageDialog(null, "max points can't be LT or EQ to zero", "oops...", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StampsAndPoints sp = new StampsAndPoints(listOfDataPointHistories);
            chartFrame = new DPHistoryChartFrame(controller, sp, maxPoints, siteTimeZone);
            controller.addModelListener(chartFrame);
            chartFrame.pack();
            chartFrame.setLocationRelativeTo(this);
            chartFrame.setVisible(true);

        }
    }//GEN-LAST:event_jButtonHistoryChartActionPerformed

    private void jTextFieldHistoryMaxPointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHistoryMaxPointsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldHistoryMaxPointsActionPerformed

    private void jToggleButtonViewShortListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonViewShortListActionPerformed


    }//GEN-LAST:event_jToggleButtonViewShortListActionPerformed

    private void jTextFieldHistoryStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHistoryStartDateActionPerformed

        String dateString = this.jTextFieldHistoryStartDate.getText();
        siteLocalStartDate = DateTime.parse(dateString, zzFormat);
        utcStartDate = siteLocalStartDate.withZone(DateTimeZone.UTC);
        jLabelUTCStartDate.setText(utcStartDate.toString(utcLabelFormat) + " (UTC)");

    }//GEN-LAST:event_jTextFieldHistoryStartDateActionPerformed

    private void jTextFieldHistoryEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHistoryEndDateActionPerformed

        String dateString = this.jTextFieldHistoryEndDate.getText();
        siteLocalEndDate = DateTime.parse(dateString, zzFormat);
        utcEndDate = siteLocalEndDate.withZone(DateTimeZone.UTC);
        jLabelUTCEndDate.setText(utcEndDate.toString(utcLabelFormat) + " (UTC)");

    }//GEN-LAST:event_jTextFieldHistoryEndDateActionPerformed

    private void jButtonPushXLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPushXLSActionPerformed

        /*
        if (jListHistoryDatapoints.getSelectedIndices().length > 0) {

            List<String> selectedPointsList = this.jListHistoryDatapoints.getSelectedValuesList();
            Map<String, DatapointMetadata> selectedPointsMap = new HashMap<>();
            for (String selectedPoint : selectedPointsList) {
                selectedPointsMap.put(selectedPoint, nameToMetadataMap.get(selectedPoint));
            }

            DataGeneratorXLSFrame frame = DataGeneratorXLSFrame.getInstance(controller, site.getSid(), selectedPointsMap);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            controller.addModelListener(frame);
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }
         */

    }//GEN-LAST:event_jButtonPushXLSActionPerformed

    private void jButtonSiteTrendChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSiteTrendChartActionPerformed

        if (siteTrendKPI != null) {
            int maxPoints = 100;
            try {
                maxPoints = Integer.parseInt(this.jTextFieldHistoryMaxPoints.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "max points setting not valid int", "oops...", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (maxPoints <= 0) {
                JOptionPane.showMessageDialog(null, "max points can't be LT or EQ to zero", "oops...", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            SiteTrendChartFrame cf = new SiteTrendChartFrame(controller, siteTrendKPI, maxPoints, EnumTZOffsets.Seattle);
            controller.addModelListener(cf);
            cf.pack();
            cf.setLocationRelativeTo(this);
            cf.setVisible(true);

        }


    }//GEN-LAST:event_jButtonSiteTrendChartActionPerformed

    private void jTableContactPhonesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableContactPhonesMousePressed
        if (evt.isPopupTrigger()) {
            PopupMenuForContactPhones popup = new PopupMenuForContactPhones(evt, jTableContactPhones);
        }
    }//GEN-LAST:event_jTableContactPhonesMousePressed

    private void jButtonRulesInfoRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRulesInfoRefreshActionPerformed
        controller.getRulesInfo(site.getSid());
    }//GEN-LAST:event_jButtonRulesInfoRefreshActionPerformed

    private void jTableListOfRulesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListOfRulesMousePressed
        int row = jTableListOfRules.rowAtPoint(evt.getPoint());
        int modelIndex = jTableListOfRules.convertRowIndexToModel(row);
        RuleListTableModel mod = (RuleListTableModel) jTableListOfRules.getModel();
        fillRuleMetatDataPanel(mod.getRuleFromTable(modelIndex));
    }//GEN-LAST:event_jTableListOfRulesMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        /*&
        List< DatapointMetadata> listOfMetadata = new ArrayList<>();
        for (String dpName : historyDatapointNames) {
            DatapointMetadata dp = nameToMetadataMap.get(dpName);
            listOfMetadata.add(dp);
        }

        DataPointsAdminFrame frame = DataPointsAdminFrame.getInstance(controller, site.getSid(), listOfMetadata);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
         */
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonOptimizationLivePushActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOptimizationLivePushActionPerformed
        PushOptimizationInfoFrame frame = PushOptimizationInfoFrame.getInstance(controller);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonOptimizationLivePushActionPerformed

    private void jButtonPushDataForLineageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPushDataForLineageActionPerformed

        /*
        List<String> selectedPointsList = this.jListHistoryDatapoints.getSelectedValuesList();
        Map<String, DatapointMetadata> selectedPointsMap = new HashMap<>();
        for (String selectedPoint : selectedPointsList) {
            DatapointMetadata dp = nameToMetadataMap.get(selectedPoint);
            if (dp.getTags().contains("calculated")) {
                selectedPointsMap.put(selectedPoint, nameToMetadataMap.get(selectedPoint));
            }
        }

        List< DatapointMetadata> listOfMetadata = new ArrayList<>();
        for (String key : selectedPointsMap.keySet()) {
            DatapointMetadata dp = nameToMetadataMap.get(key);
            listOfMetadata.addAll(getListOfDependentPoints(dp));
        }

        CalcPointPushFrame frame = CalcPointPushFrame.getInstance(
                controller,
                site,
                historyQueryStart,
                historyQueryEnd,
                siteConfigPoints.getGroups(),
                listOfMetadata);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        controller.addModelListener(frame);

        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true)
        ;
         */

    }//GEN-LAST:event_jButtonPushDataForLineageActionPerformed

    private void jButtonViewLiveDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewLiveDataActionPerformed

        ViewLiveDataFrame frame = ViewLiveDataFrame.getInstance(controller, pageView);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonViewLiveDataActionPerformed

    private void jTextFieldHistoryDPFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHistoryDPFilterActionPerformed
        this.historyDatapointFilter = jTextFieldHistoryDPFilter.getText();
        fillHistoryPointsTable(jTextFieldHistoryDPFilter.getText());
    }//GEN-LAST:event_jTextFieldHistoryDPFilterActionPerformed

    private void jTableDatapointsListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDatapointsListMousePressed

        if (evt.isPopupTrigger()) {
            PopupMenuForDataPointsListTable popup = new PopupMenuForDataPointsListTable(evt, jTableDatapointsList);
        }

        if (jTableDatapointsList.getSelectedRowCount() > 0) {
            enableQueryButtons(true);
        } else {
            enableQueryButtons(false);
        }

        //bbb
        int row = jTableDatapointsList.rowAtPoint(evt.getPoint());
        int modelIndex = jTableDatapointsList.convertRowIndexToModel(row);
        DatapointsListTableModel mod = (DatapointsListTableModel) jTableDatapointsList.getModel();
        DatapointsAndMetadataResponse resp = mod.getRow(modelIndex);

        jTableDatapointsAssociations.setModel(new DatapointAssociationsTableModel(resp.getDatapointAssociations()));
        jTableDatapointsAssociations.setDefaultRenderer(Object.class, new DataPointAssociationsTableCellRenderer());

        setPointCounts();


    }//GEN-LAST:event_jTableDatapointsListMousePressed

    private void jButtonNodeQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNodeQueryActionPerformed
        queryGraphNodes();
    }//GEN-LAST:event_jButtonNodeQueryActionPerformed

    private void jButtonReportVerificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReportVerificationActionPerformed

        TimeZone tz = TimeZone.getTimeZone(site.getAddress().getTimezone());
        DateTimeZone siteTimeZone = DateTimeZone.forTimeZone(tz);

        ReportVerificationFrame frame = ReportVerificationFrame.getInstance(controller, this.site.getSid(), siteTimeZone, listOfMetadata);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonReportVerificationActionPerformed

    private void jButtonClearFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearFilterActionPerformed
        ///filteredList = new ArrayList<>();
        this.historyDatapointFilter = "";
        jTextFieldHistoryDPFilter.setText("");
        fillHistoryPointsTable("");
    }//GEN-LAST:event_jButtonClearFilterActionPerformed

    private void jTableReportsListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableReportsListMousePressed
        int row = jTableReportsList.rowAtPoint(evt.getPoint());
        int modelIndex = jTableReportsList.convertRowIndexToModel(row);
        ReportsListTableModel mod = (ReportsListTableModel) jTableReportsList.getModel();

        MonthlyReportItem reportItem = mod.getRow(modelIndex);

        controller.getReportsSchema(reportItem.getID());
    }//GEN-LAST:event_jTableReportsListMousePressed

    @Override
    public void propertyChange(PropertyChangeEvent evt
    ) {
        String propName = evt.getPropertyName();

        //SITE DETAILS
        if (propName.equals(PropertyChangeNames.SelectedSiteInfoChanged.getName())) {
            OEResponse resp = (OEResponse) evt.getNewValue();

            if (resp.responseCode != 200) {
                controller.removePropChangeListener(this);
                this.dispose();
            } else {
                Site temp = (Site) resp.responseObject;
                if (site == null || site.getSid().compareTo(temp.getSid()) == 0) {
                    site = temp;
                    fillEditSiteDetailsFrame(site);
                }
            }

            //POINTS AND HISTORY
        } else if (propName.equals(PropertyChangeNames.DatapointsListReturned.getName())) {
            listOfMetadata = (List<DatapointsAndMetadataResponse>) evt.getNewValue();

            if (!restOfThePointsReceived) {
                restOfThePointsReceived = true;
                this.getTheRestOfThePoints(listOfMetadata);
            } else {
                fillHistoryPointsTable(jTextFieldHistoryDPFilter.getText());
                //check clarks list
                boolean clarksListIsValid = pointInClarkList(listOfMetadata);
            }

        } else if (propName.equals(PropertyChangeNames.DatapointHistoriesResponseReturned.getName())) {
            listOfDataPointHistories = (List<DatapointHistoriesResponse>) evt.getNewValue();

            int prec = (int) this.jSpinnerHistoryPrec.getModel().getValue();

            fillDatapointsHistoryTable(prec);

            DPHistoryTableModel mod = (DPHistoryTableModel) jTableHistoryQueryResults.getModel();
            stat = new Statistics(mod.getUNames(), listOfDataPointHistories);
            fillDatapointsAveragesTable(prec);

        } else if (propName.equals(PropertyChangeNames.GraphRulesInfoReturned.getName())) {

        } else if (propName.equals(PropertyChangeNames.DatapointMetadataReturned.getName())) {

            // DATAPOINT ADMINISTRATION & LIVE DATA
        } else if (propName.equals(PropertyChangeNames.SubscriptionCreated.getName())) {
            createSubscriptionResponse = (CreateSubscriptionResponse) evt.getNewValue();
            this.subscriptionId = createSubscriptionResponse.getSubsciptionID();
            this.jLabelHistoryLiveId.setText(subscriptionId);
            this.jLabelHistoryLiveId.setToolTipText(subscriptionId);
            jToggleHistoryPollLiveData.setEnabled(true);

        } else if (propName.equals(PropertyChangeNames.LiveDataReturned.getName())) {
            GetLiveDataResponse liveDataResponse = (GetLiveDataResponse) evt.getNewValue();
            List<SubscriptionResponseDatapoint> points = liveDataResponse.getPoints();

            if (jCheckBoxAppendLiveData.isSelected()) {

                int staleThreshold = Integer.parseInt(jTextFieldStaleThresh.getText());
                DPHistoryTableModel mod = (DPHistoryTableModel) jTableHistoryQueryResults.getModel();
                mod.appendLiveData(liveDataResponse.getTimestamp(), points, staleThreshold);

                DPHistoryAveragesTableModel avgs = (DPHistoryAveragesTableModel) jTableDPHistoryAverages.getModel();
                avgs.appendLiveData(points);

                if (subscriptionId == null || subscriptionId.compareTo(liveDataResponse.getSubsciptionID()) != 0) {
                    System.out.println("subscription id changed!");
                }
                subscriptionId = liveDataResponse.getSubsciptionID();
                this.jLabelHistoryLiveId.setText(subscriptionId);

            }

            //CONFIG POINTS
        } else if (propName.equals(PropertyChangeNames.SiteDPConfigPointsReturned.getName())) {
            this.siteConfigPoints = (SiteDPConfigPoints) evt.getNewValue();

            this.viewShortListOfConfigPoints = false;
            this.jToggleButtonViewShortList.setSelected(viewShortListOfConfigPoints);
            fillDPConfigTable(EnumGroupTypes.equipment, EnumSubGroupTypes.all, viewShortListOfConfigPoints);

        } else if (propName.equals(PropertyChangeNames.SiteNewActivationCodeReturned.getName())) {
            ActivationCodeResponse acr = (ActivationCodeResponse) evt.getNewValue();
            this.site.setActivationCode(acr.getActivationCode());
            this.jLabelStationActivationCode.setText(site.getActivationCode());

        } else if (propName.equals(PropertyChangeNames.SiteCheckForUpdates.getName())) {
            CheckForUpdatesResponse cur = (CheckForUpdatesResponse) evt.getNewValue();
            jLabelCheckForUpdatesTimestamp.setText(cur.getTimestamp());
            jLabelCheckForUpdatesMessage.setText(cur.getMessage());

            // GRAPH
        } else if (propName.equals(PropertyChangeNames.GraphChildrenReturned.getName())) {
            try {
                GetChildrenResponse children = (GetChildrenResponse) evt.getNewValue();
                this.fillGraphNodesTree(children);
            } catch (Exception ex) {
                Logger.getLogger(EditSiteDetailsFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (propName.equals(PropertyChangeNames.GraphElementDeleted.getName())) {
            queryGraphNodes();

        } else if (propName.equals(PropertyChangeNames.GraphElementSchemaReturned.getName())) {

            // TREND
        } else if (propName.equals(PropertyChangeNames.SiteTrendReturned.getName())) {
            SiteTrend siteTrend = (SiteTrend) evt.getNewValue();
            try {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(siteTrend);
                fillSiteTrendTree(json);

            } catch (Exception ex) {
                Logger.getLogger(EditSiteDetailsFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            // KPIS
            //ToDo : What's the status on these?
        } else if (propName.equals(PropertyChangeNames.SiteTrendKPIReturned.getName())) {
            siteTrendKPI = (SiteTrendKPI) evt.getNewValue();
            try {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(siteTrendKPI);
                fillSiteTrendTree(json);

            } catch (Exception ex) {
                Logger.getLogger(EditSiteDetailsFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            // VIEWS
        } else if (propName.equals(PropertyChangeNames.UIMetaDataReturned.getName())) {
            try {
                this.pageView = (PageView) evt.getNewValue();
                fillPageViewTree();

            } catch (Exception ex) {
                Logger.getLogger(EditSiteDetailsFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            //STATION
        } else if (propName.equals(PropertyChangeNames.StationCheckActivateAvaiability.getName())) {
            NetworkAvailabilityResponse networkStatus = (NetworkAvailabilityResponse) evt.getNewValue();
            this.jLabelActivationAvailability.setText(networkStatus.getServerTime());

        } else if (propName.equals(PropertyChangeNames.StationActivatated.getName())) {
            StationActivateResponse actResponse = (StationActivateResponse) evt.getNewValue();

            String newStationID = actResponse.getStationId();
            StationCredentials creds = actResponse.getStationCredentials();

            //site.setSid(newStationID);
            jLabelStationId.setText(newStationID);
            jLabelStationInfoUsername.setText(creds.getName());
            jLabelStationInfoPassword.setText(creds.getPassword());

        } else if (propName.equals(PropertyChangeNames.TestTokenReturned.getName())) {
            LoginResponse loginResp = (LoginResponse) evt.getNewValue();
            this.jTextFieldStationUserToken.setText(loginResp.getAccessToken());

        } else if (propName.equals(PropertyChangeNames.StationHeartbeatPushed.getName())) {
            StationStatusResponse resp = (StationStatusResponse) evt.getNewValue();

            try {
                String payload = new ObjectMapper().writeValueAsString(resp);
                fillStationConfigFileTree(payload);

            } catch (JsonProcessingException ex) {
                Logger.getLogger(EditSiteDetailsFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } else if (propName.equals(PropertyChangeNames.StationBogfileDownloaded.getName())) {
            fillStationConfigFileTree((String) evt.getNewValue());

        } else if (propName.equals(PropertyChangeNames.StationConfigurationFileDownloaded.getName())) {
            fillStationConfigFileTree((String) evt.getNewValue());

        } else if (propName.equals(PropertyChangeNames.StationPointsConfigurationDownloaded.getName())) {
            fillStationConfigFileTree((String) evt.getNewValue());

        } else if (propName.equals(PropertyChangeNames.StationDefaultParametersDownloaded.getName())) {
            fillStationConfigFileTree((String) evt.getNewValue());

        } else if (propName.equals(PropertyChangeNames.StationPortalParametersDownloaded.getName())) {
            fillStationConfigFileTree((String) evt.getNewValue());

        } else if (propName.equals(PropertyChangeNames.StationConfigurationStatusReturned.getName())) {

            WizardStationStatus status = (WizardStationStatus) evt.getNewValue();
            ObjectMapper mapper = new ObjectMapper();
            String json = "{\"oops\":\"could not parse json\"}";
            try {
                json = mapper.writeValueAsString(status);

            } catch (JsonProcessingException ex) {
                Logger.getLogger(EditSiteDetailsFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            fillStationConfigFileTree((String) json);

            //Reports
        } else if (propName.equals(PropertyChangeNames.ReportListReturned.getName())) {
            List<MonthlyReportItem> list = (List<MonthlyReportItem>) evt.getNewValue();
            fillReportsTable(list);

        } else if (propName.equals(PropertyChangeNames.ReportSchemaReturned.getName())) {
            fillReportsTree((String) evt.getNewValue());

            // Login
        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            dispose();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonActivateSite;
    private javax.swing.JButton jButtonActivateStation;
    private javax.swing.JButton jButtonCheckActivationStatus;
    private javax.swing.JButton jButtonCheckForUpdates;
    private javax.swing.JButton jButtonClearFilter;
    private javax.swing.JButton jButtonEditAlarms;
    private javax.swing.JButton jButtonGetConfigProfile;
    private javax.swing.JButton jButtonGetConfigStatus;
    private javax.swing.JButton jButtonGetDatapointsXLS;
    private javax.swing.JButton jButtonGetDefaultParams;
    private javax.swing.JButton jButtonGetPointsConfig;
    private javax.swing.JButton jButtonGetPortalParams;
    private javax.swing.JButton jButtonGetStationBogFile;
    private javax.swing.JButton jButtonHistoryChart;
    private javax.swing.JButton jButtonHistoryPushLiveData;
    private javax.swing.JButton jButtonHistoryQuery;
    private javax.swing.JButton jButtonHistoryToTesla;
    private javax.swing.JButton jButtonNodeQuery;
    private javax.swing.JButton jButtonOptimizationLivePush;
    private javax.swing.JButton jButtonPostLogHistory;
    private javax.swing.JButton jButtonPostStationsAuditLog;
    private javax.swing.JButton jButtonPushDataForLineage;
    private javax.swing.JButton jButtonPushXLS;
    private javax.swing.JButton jButtonPutAlarmChanges;
    private javax.swing.JButton jButtonPutConfigStatus;
    private javax.swing.JButton jButtonPutDatapointHistory;
    private javax.swing.JButton jButtonPutHeartbeat;
    private javax.swing.JButton jButtonReportVerification;
    private javax.swing.JButton jButtonRulesInfoRefresh;
    private javax.swing.JButton jButtonSiteTrendChart;
    private javax.swing.JButton jButtonSiteTrendQuery;
    private javax.swing.JButton jButtonTestStationLogin;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JButton jButtonValidateLocalFiles;
    private javax.swing.JButton jButtonViewLiveData;
    private javax.swing.JCheckBox jCheckBoxAppendLiveData;
    private javax.swing.JCheckBox jCheckBoxAvg;
    private javax.swing.JCheckBox jCheckBoxCount;
    private javax.swing.JCheckBox jCheckBoxEdgePlus;
    private javax.swing.JCheckBox jCheckBoxForceUpdate;
    private javax.swing.JCheckBox jCheckBoxIncludeStationId;
    private javax.swing.JCheckBox jCheckBoxSparseData;
    private javax.swing.JCheckBox jCheckBoxSum;
    private javax.swing.JCheckBox jCheckBoxUseRegEx;
    private javax.swing.JCheckBox jCheckBoxUseStationToken;
    private javax.swing.JComboBox jComboBoxDPConfigGroupType;
    private javax.swing.JComboBox<String> jComboBoxDPConfigSubGroup;
    private javax.swing.JComboBox<String> jComboBoxDateType;
    private javax.swing.JComboBox<String> jComboBoxEquipType;
    private javax.swing.JComboBox<String> jComboBoxHistoryResolution;
    private javax.swing.JComboBox<String> jComboBoxLicenseTypes;
    private javax.swing.JComboBox<String> jComboBoxPageViewTypes;
    private javax.swing.JComboBox<String> jComboBoxReportMonth;
    private javax.swing.JComboBox<String> jComboBoxReportYear;
    private javax.swing.JComboBox<String> jComboBoxSiteInfoProducts;
    private javax.swing.JComboBox jComboBoxSiteTrendKPI;
    private javax.swing.JComboBox<String> jComboBoxSiteTrendResolutions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelActivationAvailability;
    private javax.swing.JLabel jLabelCheckForUpdatesMessage;
    private javax.swing.JLabel jLabelCheckForUpdatesTimestamp;
    private javax.swing.JLabel jLabelCommProtocol;
    private javax.swing.JLabel jLabelCommissionDate;
    private javax.swing.JLabel jLabelDPConfigPointCount;
    private javax.swing.JLabel jLabelHistoryLiveId;
    private javax.swing.JLabel jLabelHistoryNumberPoints;
    private javax.swing.JLabel jLabelHistoryQueryResultsSid;
    private javax.swing.JLabel jLabelHistoryResultsTimezone;
    private javax.swing.JLabel jLabelLastUpdated;
    private javax.swing.JLabel jLabelNumHistoryPointsSelected;
    private javax.swing.JLabel jLabelQueryResultsReso;
    private javax.swing.JLabel jLabelSiteOrCustomerLabel;
    private javax.swing.JLabel jLabelSiteSid;
    private javax.swing.JLabel jLabelStationActivationCode;
    private javax.swing.JLabel jLabelStationHostID;
    private javax.swing.JLabel jLabelStationId;
    private javax.swing.JLabel jLabelStationInfoPassword;
    private javax.swing.JLabel jLabelStationInfoUsername;
    private javax.swing.JLabel jLabelTimeZone;
    private javax.swing.JLabel jLabelUTCEndDate;
    private javax.swing.JLabel jLabelUTCStartDate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelActivation;
    private javax.swing.JPanel jPanelAddress;
    private javax.swing.JPanel jPanelAlarms;
    private javax.swing.JPanel jPanelBasicInfo;
    private javax.swing.JPanel jPanelContactInfo;
    private javax.swing.JPanel jPanelContacts;
    private javax.swing.JPanel jPanelDPConfig;
    private javax.swing.JPanel jPanelDatapointsList;
    private javax.swing.JPanel jPanelDownloads;
    private javax.swing.JPanel jPanelEnhancements;
    private javax.swing.JPanel jPanelGraphNodes;
    private javax.swing.JPanel jPanelHistory;
    private javax.swing.JPanel jPanelHistoryQueryResults;
    private javax.swing.JPanel jPanelHistoryStats;
    private javax.swing.JPanel jPanelJsonTree;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelLiveData;
    private javax.swing.JPanel jPanelOuterPageViewPanel;
    private javax.swing.JPanel jPanelPlantDetails;
    private javax.swing.JPanel jPanelQueryParameters;
    private javax.swing.JPanel jPanelReportsTab;
    private javax.swing.JPanel jPanelRulesInfo;
    private javax.swing.JPanel jPanelSelectedContactDetails;
    private javax.swing.JPanel jPanelSiteInfo;
    private javax.swing.JPanel jPanelSiteTrend;
    private javax.swing.JPanel jPanelStationInfo;
    private javax.swing.JPanel jPanelStationLicense;
    private javax.swing.JPanel jPanelSubscriptions;
    private javax.swing.JPanel jPanelSytheticJaceSettings;
    private javax.swing.JPanel jPanelTopPanel;
    private javax.swing.JPanel jPanelTotalPanel;
    private javax.swing.JPanel jPanelUploads;
    private javax.swing.JPanel jPanelViews;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSpinner jSpinnerHistoryPrec;
    private javax.swing.JSpinner jSpinnerPollInterval;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JSplitPane jSplitPane5;
    private javax.swing.JSplitPane jSplitPaneSubAndEnhance;
    private javax.swing.JTabbedPane jTabbedPaneAllTabs;
    private javax.swing.JTable jTableAddress;
    private javax.swing.JTable jTableAlarms;
    private javax.swing.JTable jTableContactPhones;
    private javax.swing.JTable jTableContacts;
    private javax.swing.JTable jTableDPHistoryAverages;
    private javax.swing.JTable jTableDatapointsAssociations;
    private javax.swing.JTable jTableDatapointsList;
    private javax.swing.JTable jTableEnhancements;
    private javax.swing.JTable jTableHistoryQueryResults;
    private javax.swing.JTable jTableListOfRules;
    private javax.swing.JTable jTablePlantDetails;
    private javax.swing.JTable jTableReportsList;
    private javax.swing.JTable jTableSiteDPConfig;
    private javax.swing.JTable jTableSubscriptions;
    private javax.swing.JTextField jTextFieldActivatedAt;
    private javax.swing.JTextField jTextFieldCurrencyCode;
    private javax.swing.JTextField jTextFieldEnabledAt;
    private javax.swing.JTextField jTextFieldExpiresAt;
    private javax.swing.JTextField jTextFieldExtSFID;
    private javax.swing.JTextField jTextFieldHistoryDPFilter;
    private javax.swing.JTextField jTextFieldHistoryEndDate;
    private javax.swing.JTextField jTextFieldHistoryMaxPoints;
    private javax.swing.JTextField jTextFieldHistoryStartDate;
    private javax.swing.JTextField jTextFieldNodeSid;
    private javax.swing.JTextField jTextFieldSFOpportunity;
    private javax.swing.JTextField jTextFieldSiteName;
    private javax.swing.JTextField jTextFieldSiteTrendEndTime;
    private javax.swing.JTextField jTextFieldSiteTrendStartTime;
    private javax.swing.JTextField jTextFieldStaleThresh;
    private javax.swing.JTextField jTextFieldStationHostID;
    private javax.swing.JTextField jTextFieldStationUserToken;
    private javax.swing.JToggleButton jToggleButtonViewShortList;
    private javax.swing.JToggleButton jToggleHistoryPollLiveData;
    private javax.swing.JTree jTreeEquipProps;
    private javax.swing.JTree jTreeReportSchema;
    private javax.swing.JTree jTreeRulesInfo;
    private javax.swing.JTree jTreeSiteTrend;
    private javax.swing.JTree jTreeStationConfigFile;
    private javax.swing.JTree jTreeViewsPageData;
    // End of variables declaration//GEN-END:variables
}
