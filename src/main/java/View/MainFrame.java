package View;

import Controller.OptiCxAPIController;
import Model.DataModels.Auth.AdminLoginCreds;
import Model.DataModels.Auth.PostForgotPasswordRequest;
import Model.DataModels.Auth.PutForgotPasswordRequest;
import Model.DataModels.Auth.VerifyResetPasswordTokenRequest;
import Model.DataModels.Customers.Customer;
import Model.DataModels.Sites.EnumPointsListDownloadType;
import Model.DataModels.Sites.GetSiteInfoQueryParams;
import Model.DataModels.Sites.Site;
import Model.DataModels.TrendAPI.MobileCompany;
import Model.DataModels.TrendAPI.MobileCompanyList;
import Model.DataModels.TrendAPI.MobileCompanyOverview;
import Model.DataModels.TrendAPI.MobileHealthInfo;
import Model.DataModels.TrendAPI.MobileOptimizationGraph;
import Model.DataModels.TrendAPI.MobileOverview;
import Model.DataModels.TrendAPI.MobileSite;
import Model.DataModels.TrendAPI.SiteInfo.EnumMobileTimePeriods;
import Model.DataModels.TrendAPI.SiteInfo.EnumMobileTrendTypes;
import Model.DataModels.TrendAPI.SiteInfo.MobileSiteInfo;
import Model.DataModels.Users.CurrentUserDisplay;
import Model.DataModels.Users.CurrentUserInfoResponse;
import Model.DataModels.Users.CurrentUserPreferences;
import Model.DataModels.Users.CurrentUserTerms;
import Model.DataModels.Users.ModifyCurrentUserPasswordResponse;
import Model.DataModels.Users.ModifyCurrentUserRequest;
import Model.DataModels.Users.UserInfo;
import Model.DataModels.Users.UserQueryFilter;
import Model.RestClient.APIHostsEnum;
import Model.RestClient.ErrorResponse;
import Model.RestClient.LoginResponse;
import Model.RestClient.OEResponse;
import View.Customers.CustomerDetails.CustomerDetailsFrame;
import View.Customers.CustomersTableCellRenderer;
import View.Customers.CustomersTableModel;
import View.RequestResponse.RRFrame;
import View.Sites.EditSite.EditSiteDetailsFrame;
import View.Sites.NewSite.NewSiteDetailsFrame;
import View.Sites.SiteListTableCellRenderer;
import View.Sites.SiteListTableModel;
import View.TotalSavings.TotalSavingsFrame;
import View.TreeInfo.CustomTreeNode;
import View.Users.AddOrUpdateEnum;
import View.Users.PopupMenuForCopyingToClipboard;
import View.CommonLibs.MapTableCellRenderer;
import View.CommonLibs.MapTableModel;
import View.MobileView.MobileTrendChart.MobileTrendChartFrame;
import View.MobileView.SitesList.MobileSitesTableCellRenderer;
import View.MobileView.SitesList.MobileSitesTableModel;
import View.Sites.SiteGenerator.SiteGeneratorFrame;
import View.Users.UserDetails.UserDetailsFrame;
import View.Users.UsersTableCellRenderer;
import View.Users.UsersTableModel;
import View.Wizard.WizardFrame;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultTreeModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainFrame extends javax.swing.JFrame {

    private OptiCxAPIController controller;

    private String selectedSiteSid;
    private Customer selectedCustomer;

    private MobileCompanyList mobileCompanies;
    private Map<String, String> mobileNameToUUIDmap;
    private String selectedMobileSiteUUID;

    public MainFrame() {
        initComponents();
        
        this.jTextFieldLoginUsername.setText("hal.wilkinson@optimumenergyco.com");
        this.jPasswordFieldLoginPassword.setText("xxxx");

        this.jTextFieldPWResetCode.setText("");
        this.jTextFieldPWResetPassword.setText("");
        this.jTextFieldResetPWConfirm.setText("");
        this.jTextFieldResetPWEmail.setText("");

        jTextFieldVerifyTokenResetCode.setText("");
        jTextFieldVerifyTokenResponse.setText("");

        this.jTextFieldResetPWRedirect.setText("");

        this.jLabelCustomerSid.setText("");
        this.jTextFieldCustomerName.setText("");
        this.jTextFieldCustomerExtSfId.setText("");

        this.jTextFieldUsersPassword.setText("");

        this.selectedSiteSid = null;
        this.selectedMobileSiteUUID = null;

        this.fillMobileTimePeriodsDropDown();
        this.fillMobileTrendTypesDropDown();

    }

    public void setController(OptiCxAPIController controller) {
        this.controller = controller;
    }

    public void fillAPIHosts() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(APIHostsEnum.getNames().toArray());
        this.jComboBoxApiHosts.setModel(comboBoxModel);
        this.jComboBoxApiHosts.setSelectedItem(APIHostsEnum.OEDEV.getName());
    }


    public void clearLoginInfo() {
        this.jTextPaneAccessToken.setText("");
        this.jTextPaneRefreshToken.setText("");
        this.jLabelExpiresIn.setText("");
        this.jLabelTokenType.setText("");
        this.jLabelScope.setText("");
    }

    public void fillInLoginInfo(LoginResponse loginInfo) {
        this.jTextPaneAccessToken.setText(loginInfo.getAccessToken());
        this.jTextPaneRefreshToken.setText(loginInfo.getRefreshToken());
        this.jLabelExpiresIn.setText(Integer.toString(loginInfo.getExpiresIn()));
        this.jLabelTokenType.setText(loginInfo.getTokenType());
        this.jLabelScope.setText(loginInfo.getScope());
    }

    public void clearUserMeInfo() {
        this.jLabelTimestamp.setText("");
        this.jLabelUserID.setText("");
        this.jLabelPasswordExpires.setText("");

        this.jTextFieldUsername.setText("");
        this.jTextFieldEmail.setText("");
        this.jTextFieldFirstName.setText("");
        this.jTextFieldLastName.setText("");

        this.jLabelTermsAccepted.setText("");
        this.jTextFieldUnits.setText("");
        this.jTextFieldDateFormat.setText("");

        this.jTextFieldCurrentPassword.setText("");
        this.jTextFieldPassword.setText("");
    }

    public void fillUserMeInfo(CurrentUserInfoResponse userInfo) {
        this.jLabelTimestamp.setText(userInfo.getTimestamp());
        this.jLabelUserID.setText(userInfo.getUserID());
        this.jLabelPasswordExpires.setText(userInfo.getPasswordExpires());

        this.jTextFieldUsername.setText(userInfo.getUsername());
        this.jTextFieldEmail.setText(userInfo.getEmail());
        this.jTextFieldFirstName.setText(userInfo.getFirstName());
        this.jTextFieldLastName.setText(userInfo.getLastName());

        if (userInfo.getPreferences() != null) {
            if (userInfo.getPreferences().getTerms() != null) {
                this.jLabelTermsAccepted.setText(userInfo.getPreferences().getTerms().getAccepted());
            }
            if (userInfo.getPreferences() != null && userInfo.getPreferences().getDisplay() != null) {
                this.jTextFieldUnits.setText(userInfo.getPreferences().getDisplay().getUnits());
                this.jTextFieldDateFormat.setText(userInfo.getPreferences().getDisplay().getDateFormat());
            }
        }

        this.jTextFieldCurrentPassword.setText("set me please");
        this.jTextFieldPassword.setText("set me please");
    }

    public void clearCurrentUserInfoTree() {
        this.jTreeCurrentUserInfo.setModel(new DefaultTreeModel(null));
    }

    public void fillCurrentUserInfoTree(CustomTreeNode root) {
        jTreeCurrentUserInfo.setModel(new DefaultTreeModel(root));
        setVisible(true);
    }

    public void enableTabSet(boolean enable) {
        this.jTabbedPaneTabSet.setEnabled(enable);
    }

    public void fillUsersTable(List<UserInfo> users) {
        this.jTableUsers.setDefaultRenderer(Object.class, new UsersTableCellRenderer());
        this.jTableUsers.setModel(new UsersTableModel(users));
        this.jTableUsers.setAutoCreateRowSorter(true);
        fixUserTableColumnWidths(jTableUsers);

    }

    public void fixUserTableColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        for (int i = 0; i < t.getColumnCount(); i++) {
            column = t.getColumnModel().getColumn(i);
            if (i < 3) {
                column.setPreferredWidth(200);
            } else {
                column.setPreferredWidth(100);
            }
        }

    }

    public void clearUsersTable() {
        this.jTableUsers.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
        this.jTableUsers.setModel(new DefaultTableModel());
    }

    public void fillCustomersTable(List<Customer> customers) {
        this.jTableCustomers.setDefaultRenderer(Object.class, new CustomersTableCellRenderer());
        this.jTableCustomers.setModel(new CustomersTableModel(customers));
        this.jTableCustomers.setAutoCreateRowSorter(true);
    }

    public void clearCustomersTable() {
        this.jTableCustomers.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
        this.jTableCustomers.setModel(new DefaultTableModel());
    }

    public void clearCustomerDetails() {
        this.selectedCustomer = null;

        this.jLabelCustomerSid.setText("");
        this.jTextFieldCustomerName.setText("");
        this.jTextFieldCustomerExtSfId.setText("");

        jTableCustomerAddress.setDefaultRenderer(Object.class, new MapTableCellRenderer());
        jTableCustomerAddress.setModel(new DefaultTableModel());

    }

    public void fillCustomerDetails(Customer customer) {

        this.jLabelCustomerSid.setText(customer.getSid());
        this.jTextFieldCustomerName.setText(customer.getCustomerName());
        this.jTextFieldCustomerExtSfId.setText(customer.getExtSfId());

        fillCustomerAddressTable(customer);
    }

    private void fillCustomerAddressTable(Customer customer) {

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

        jTableCustomerAddress.setDefaultRenderer(Object.class, new MapTableCellRenderer());
        jTableCustomerAddress.setModel(new MapTableModel(customer.getAddress()));
    }

    public void fillSiteListTable(List<Site> sites) {
        if (sites != null) {
            this.jTableSiteList.setDefaultRenderer(Object.class, new SiteListTableCellRenderer());
            this.jTableSiteList.setModel(new SiteListTableModel(sites));
            this.jTableSiteList.setAutoCreateRowSorter(true);
            this.jButtonQuerySites.setEnabled(true);
        } else {
            this.jTableSiteList.setDefaultRenderer(Object.class, new SiteListTableCellRenderer());
            this.jTableSiteList.setModel(new DefaultTableModel());
        }
    }

    public void fillSiteInfoTree(CustomTreeNode root) {
        jTreeSiteInfo.setModel(new DefaultTreeModel(root));
        setVisible(true);
    }

    public void clearSiteListTable() {
        this.jTableSiteList.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
        this.jTableSiteList.setModel(new DefaultTableModel());
    }

    //============= MOBILE ===============
    public void clearTrendInfo() {

        this.jLabelTrendAuthEndpoint.setText("");
        this.jLabelTrendService.setText("");
        this.jLabelTrendIssuer.setText("");
        this.jLabelTrendAud.setText("");
        this.jLabelTrendE3OS.setText("");
        this.jLabelTrendIssuerThumb.setText("");
        this.jLabelTrendAudienceThumb.setText("");

        this.jLabelTrendVersion.setText("");

        this.jComboBoxTrendCompanies.removeAllItems();

        ArrayList<MobileSite> dummy = new ArrayList<>();
        jTableMobileSites.setModel(new MobileSitesTableModel(dummy));
        jTableMobileSites.setDefaultRenderer(Object.class, new MobileSitesTableCellRenderer());
        
        
        jLabelEffAvg.setText("");
        jLabelDelta.setText("");
        jLabelCO2Saved.setText("");
        jLabelMoneySaved.setText("");
        jLabelEngSaved.setText("");
        jLabelStatus.setText("");
        jLabelSince.setText("");

    }

    public void fillTrendCompanyDropdown(MobileCompanyList companyList) {
        this.jComboBoxTrendCompanies.removeAllItems();
        this.mobileCompanies = companyList;
        this.mobileNameToUUIDmap = new HashMap<>();
        if (!mobileCompanies.getCompanies().isEmpty()) {
            List<String> companyNames = new ArrayList<>();
            for (MobileCompany co : mobileCompanies.getCompanies()) {
                companyNames.add(co.getName());
                mobileNameToUUIDmap.put(co.getName(), co.getUUID());
            }
            ComboBoxModel comboBoxModel = new DefaultComboBoxModel(companyNames.toArray());
            this.jComboBoxTrendCompanies.setModel(comboBoxModel);
            //this.jComboBoxTrendCompanies.setSelectedIndex(0);
            this.jComboBoxTrendCompanies.setEnabled(true);

            this.jComboBoxTrendCompanies.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    JComboBox<String> combo = (JComboBox<String>) event.getSource();
                    String name = (String) combo.getSelectedItem();
                    controller.getMobileCompanyOverview(mobileNameToUUIDmap.get(name));
                }
            });

            if (companyList != null && companyList.getCompanies().size() > 0) {
                this.jComboBoxTrendCompanies.setSelectedIndex(0);
            }
        }
    }

    public void fillMobileTimePeriodsDropDown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumMobileTimePeriods.getFriendlyNames().toArray());
        this.jComboBoxMobileTimePeriod.setModel(comboBoxModel);
        this.jComboBoxMobileTimePeriod.setSelectedItem(EnumMobileTimePeriods.year.getFriendlyName());

        this.jComboBoxMobileTimePeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String name = (String) combo.getSelectedItem();
                EnumMobileTimePeriods enumTimePeriod = EnumMobileTimePeriods.getEnumFromDropdownName(name);
                String mobileTimePeriod = enumTimePeriod.getFriendlyName();
                controller.getMobileSiteOverview(selectedMobileSiteUUID, mobileTimePeriod);
            }
        });
    }

    public void fillMobileTrendTypesDropDown() {
        ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EnumMobileTrendTypes.getFriendlyNames().toArray());
        this.jComboBoxMobileTrendTypes.setModel(comboBoxModel);
        this.jComboBoxMobileTrendTypes.setSelectedItem(EnumMobileTrendTypes.plant.getFriendlyName());
    }

    public void fillMobileCompanyOverviewPanel(MobileCompanyOverview mobileCompanyOverview) {

        MobileOverview co = mobileCompanyOverview.getOverview();

        String precFormatString = "#0.000";
        NumberFormat formatter = new DecimalFormat(precFormatString);

        jLabelEffAvg.setText(formatter.format(co.getEfficiencyAverage()));
        jLabelDelta.setText(formatter.format(co.getEfficiencyAverageDelta()));
        jLabelCO2Saved.setText(formatter.format(co.getSavings().getCO2Saved()));
        jLabelMoneySaved.setText(formatter.format(co.getSavings().getMoneySaved()));
        jLabelEngSaved.setText(formatter.format(co.getSavings().getEnergySaved()));
        jLabelStatus.setText(co.getSavings().getStatus());
        jLabelSince.setText(co.getSavings().getSince());

        MobileOptimizationGraph og = co.getOptimizationGraph();

        jLabelFull.setText(Integer.toString(og.getFullFlag()));
        jLabelPartial.setText(Integer.toString(og.getPartialFlag()));
        jLabelNot.setText(Integer.toString(og.getNotFlag()));
        jLabelOff.setText(Integer.toString(og.getOffFlag()));
        jLabelCommFailure.setText(Integer.toString(og.getCommFailureFlag()));

        fillSitesTable(mobileCompanyOverview.getSites());
    }

    public void fillSitesTable(List<MobileSite> sites) {
        if (sites == null) {
            ArrayList<MobileSite> dummy = new ArrayList<MobileSite>();
            jTableMobileSites.setModel(new MobileSitesTableModel(dummy));
            jTableMobileSites.setDefaultRenderer(Object.class, new MobileSitesTableCellRenderer());
            return;
        }
        jTableMobileSites.setModel(new MobileSitesTableModel(sites));
        jTableMobileSites.setDefaultRenderer(Object.class, new MobileSitesTableCellRenderer());
        jTableMobileSites.setAutoCreateRowSorter(true);
        fixMobileSitesTableColumnWidths(jTableSiteList);
    }

    public void fillMobileSiteOverviewPanel(MobileSiteInfo siteInfo) {
        try {
            fillMobileSiteInfoTree(siteInfo);
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fixMobileSitesTableColumnWidths(JTable t) {

        TableColumn column = null;
        Dimension d = t.getPreferredSize();
        int w = d.width;

        for (int i = 0; i < t.getColumnCount(); i++) {
            column = t.getColumnModel().getColumn(i);
            if (i < 2) {
                column.setPreferredWidth(400);
            } else {
                column.setPreferredWidth(100);
            }
        }

    }

    public void fillMobileSiteInfoTree(MobileSiteInfo siteInfo) throws JsonProcessingException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(siteInfo);
        JsonElement root = new JsonParser().parse(json);
        CustomTreeNode currentUserInfoTreeRootNode = new CustomTreeNode(null, -1, root);
        jTreeMobileSiteInfo.setModel(new DefaultTreeModel(currentUserInfoTreeRootNode));
    }

    public void fillTrendHealthInfo(MobileHealthInfo hi) {
        this.jLabelTrendAuthEndpoint.setText(hi.getAuthEndpoint());
        this.jLabelTrendService.setText(hi.getServiceEndpoint());
        this.jLabelTrendIssuer.setText(hi.getIssuer());
        this.jLabelTrendAud.setText(hi.getAudience());
        this.jLabelTrendE3OS.setText(hi.getE3osUri());
        this.jLabelTrendIssuerThumb.setText(hi.getIssuerThumbprint());
        this.jLabelTrendAudienceThumb.setText(hi.getAudienceThumbprint());
    }

    public void fillTrendVersion(String version) {
        this.jLabelTrendVersion.setText(version);
    }

    private void findSiteByID(String stationID) {

    }

    public void showError(OEResponse response) {

        ObjectMapper mapper = new ObjectMapper();
        String error = "";
        String message = "";

        try {
            ErrorResponse errorResponse = mapper.readValue((String) response.responseObject, ErrorResponse.class);
            error = errorResponse.getError();
            message = errorResponse.getMessage();
        } catch (Exception ex) {
            error = "?";
            message = (String) response.responseObject;
        }

        Object[] options = {"OK"};

        String multiLineMessage = "no message in response";
        if (message != null) {
            int maxlineLen = 80;
            multiLineMessage = "";
            while (message.length() > maxlineLen) {
                multiLineMessage += message.substring(0, maxlineLen) + System.getProperty("line.separator");
                message = message.substring(maxlineLen);
            }
            multiLineMessage += message;
        }

        JOptionPane.showOptionDialog(null,
                multiLineMessage,
                Integer.toString(response.responseCode) + " - " + error,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null, options, options[0]);
    }

    public void fillVerifyTokenResponse(String msg) {
        jTextFieldVerifyTokenResponse.setText(msg);
    }

    public void showPasswordChanged(ModifyCurrentUserPasswordResponse resp) {
        jTextFieldCurrentPassword.setText(resp.getMessage());
        jTextFieldPassword.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        buttonGroupLockedUsers = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPaneTabSet = new javax.swing.JTabbedPane();
        jPanelUsersMe = new javax.swing.JPanel();
        jPanelPasswordReset = new javax.swing.JPanel();
        jTextFieldResetPWEmail = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextFieldResetPWRedirect = new javax.swing.JTextField();
        jButtonResetPWForgot = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTextFieldPWResetPassword = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextFieldResetPWConfirm = new javax.swing.JTextField();
        jButtonResetPWReset = new javax.swing.JButton();
        jTextFieldPWResetCode = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldVerifyTokenResetCode = new javax.swing.JTextField();
        jButtonVerifyToken = new javax.swing.JButton();
        jTextFieldVerifyTokenResponse = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldUsername = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextFieldFirstName = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextFieldLastName = new javax.swing.JTextField();
        jPanelPreferences = new javax.swing.JPanel();
        jPanelDisplay = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jTextFieldUnits = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTextFieldDateFormat = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jButtonAcceptTerms = new javax.swing.JButton();
        jLabelTermsAccepted = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jTextFieldCurrentPassword = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTextFieldPassword = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabelTimestamp = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabelUserID = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabelPasswordExpires = new javax.swing.JLabel();
        jButtonModifyCurrentUserSettings = new javax.swing.JButton();
        jPanelLoginInfo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextPaneAccessToken = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextPaneRefreshToken = new javax.swing.JTextPane();
        jLabel4 = new javax.swing.JLabel();
        jLabelExpiresIn = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelTokenType = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelScope = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTreeCurrentUserInfo = new javax.swing.JTree();
        jPanelUsers = new javax.swing.JPanel();
        jButtonAddUser = new javax.swing.JButton();
        jButtonRunUserQuery = new javax.swing.JButton();
        jButtonDeleteUser = new javax.swing.JButton();
        jPanelUserFilter = new javax.swing.JPanel();
        jTextFieldAtSid = new javax.swing.JTextField();
        jCheckBoxNotLocked = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jCheckBoxHaveLoggedIn = new javax.swing.JCheckBox();
        jTextFieldWithRole = new javax.swing.JTextField();
        jCheckBoxLocked = new javax.swing.JCheckBox();
        jCheckBoxHaveNotLoggedIn = new javax.swing.JCheckBox();
        jPanelUsersList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableUsers = new javax.swing.JTable();
        jButtonLoginUser = new javax.swing.JButton();
        jTextFieldUsersPassword = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanelCustomers = new javax.swing.JPanel();
        jButtonAddCustomer = new javax.swing.JButton();
        jPanelCustomerListPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCustomers = new javax.swing.JTable();
        jPanelCustomerDetails = new javax.swing.JPanel();
        jPanelAddress = new javax.swing.JPanel();
        jScrollPaneAddress = new javax.swing.JScrollPane();
        jTableCustomerAddress = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jTextFieldCustomerName = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextFieldCustomerExtSfId = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabelCustomerSid = new javax.swing.JLabel();
        jButtonUpdateCustomer = new javax.swing.JButton();
        jButtonQueryCustomers = new javax.swing.JButton();
        jButtonClearSelectedCustomer = new javax.swing.JButton();
        jPanelSites = new javax.swing.JPanel();
        jButtonAddSite = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jButtonQuerySites = new javax.swing.JButton();
        jLabelSelectedCustName = new javax.swing.JLabel();
        jLabelSelectedCustomerSid = new javax.swing.JLabel();
        jButtonSiteGenerator = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTreeSiteInfo = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableSiteList = new javax.swing.JTable();
        jPanelTrendAPI = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabelTrendAuthEndpoint = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabelTrendService = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabelTrendIssuer = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabelTrendAud = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabelTrendE3OS = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabelTrendIssuerThumb = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabelTrendAudienceThumb = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabelTrendVersion = new javax.swing.JLabel();
        jPanelTrendCompanies = new javax.swing.JPanel();
        jComboBoxTrendCompanies = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabelEffAvg = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabelDelta = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabelCO2Saved = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabelMoneySaved = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabelEngSaved = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabelStatus = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabelSince = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabelFull = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabelPartial = new javax.swing.JLabel();
        jLabelOff = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabelNot = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabelCommFailure = new javax.swing.JLabel();
        jButtonGetTrendCompanies = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableMobileSites = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTreeMobileSiteInfo = new javax.swing.JTree();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxMobileTimePeriod = new javax.swing.JComboBox<>();
        jComboBoxMobileTrendTypes = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jButtonViewMobileChart = new javax.swing.JButton();
        jPanelLoginControls = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButtonTotalSavings = new javax.swing.JButton();
        jButtonViewRequests = new javax.swing.JButton();
        jComboBoxApiHosts = new javax.swing.JComboBox();
        jButtonWizard = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jTextFieldLoginUsername = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jPasswordFieldLoginPassword = new javax.swing.JPasswordField();
        jButtonLogin = new javax.swing.JButton();

        jLabel11.setText("jLabel11");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OptiCx API Tester");
        setMinimumSize(new java.awt.Dimension(1024, 768));

        jTabbedPaneTabSet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPaneTabSetFocusGained(evt);
            }
        });
        jTabbedPaneTabSet.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jTabbedPaneTabSetComponentShown(evt);
            }
        });

        jPanelPasswordReset.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Reset Password"));

        jTextFieldResetPWEmail.setText("jTextField1");

        jLabel29.setText("Email:");

        jLabel30.setText("Redirect URL:");

        jTextFieldResetPWRedirect.setText("jTextField2");

        jButtonResetPWForgot.setText("POST Forgot Password");
        jButtonResetPWForgot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetPWForgotActionPerformed(evt);
            }
        });

        jLabel31.setText("Reset Code:");

        jLabel32.setText("New Password:");

        jTextFieldPWResetPassword.setText("jTextField4");

        jLabel33.setText("Confirmation:");

        jTextFieldResetPWConfirm.setText("jTextField5");

        jButtonResetPWReset.setText("PUT Forgot Password");
        jButtonResetPWReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetPWResetActionPerformed(evt);
            }
        });

        jTextFieldPWResetCode.setText("jTextField1");

        jLabel19.setText("resetCode:");

        jTextFieldVerifyTokenResetCode.setText("jTextField1");

        jButtonVerifyToken.setText("Verify");
        jButtonVerifyToken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVerifyTokenActionPerformed(evt);
            }
        });

        jTextFieldVerifyTokenResponse.setText("jTextField1");

        javax.swing.GroupLayout jPanelPasswordResetLayout = new javax.swing.GroupLayout(jPanelPasswordReset);
        jPanelPasswordReset.setLayout(jPanelPasswordResetLayout);
        jPanelPasswordResetLayout.setHorizontalGroup(
            jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPasswordResetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPasswordResetLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldResetPWEmail))
                    .addGroup(jPanelPasswordResetLayout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldResetPWRedirect))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPasswordResetLayout.createSequentialGroup()
                        .addGroup(jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPasswordResetLayout.createSequentialGroup()
                                .addGap(0, 278, Short.MAX_VALUE)
                                .addComponent(jButtonResetPWForgot))
                            .addComponent(jTextFieldResetPWConfirm)
                            .addComponent(jTextFieldPWResetPassword)
                            .addComponent(jTextFieldPWResetCode)))
                    .addGroup(jPanelPasswordResetLayout.createSequentialGroup()
                        .addComponent(jButtonResetPWReset)
                        .addGap(0, 386, Short.MAX_VALUE))
                    .addGroup(jPanelPasswordResetLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldVerifyTokenResetCode, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonVerifyToken)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldVerifyTokenResponse, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelPasswordResetLayout.setVerticalGroup(
            jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPasswordResetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jTextFieldResetPWEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextFieldResetPWRedirect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonResetPWForgot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jTextFieldPWResetCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jTextFieldPWResetPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jTextFieldResetPWConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonResetPWReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPasswordResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonVerifyToken)
                    .addComponent(jLabel19)
                    .addComponent(jTextFieldVerifyTokenResetCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldVerifyTokenResponse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Current User Info"));

        jLabel16.setText("Username:");

        jTextFieldUsername.setText("jTextField1");

        jLabel10.setText("email:");

        jTextFieldEmail.setText("jTextField1");

        jLabel34.setText("First Name:");

        jTextFieldFirstName.setText("jTextField1");

        jLabel35.setText("Last Name:");

        jTextFieldLastName.setText("jTextField2");

        jPanelPreferences.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "User Preferences"));

        jPanelDisplay.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Display"));

        jLabel36.setText("Units:");

        jTextFieldUnits.setText("jTextField3");

        jLabel37.setText("Date Format:");

        jTextFieldDateFormat.setText("jTextField4");

        javax.swing.GroupLayout jPanelDisplayLayout = new javax.swing.GroupLayout(jPanelDisplay);
        jPanelDisplay.setLayout(jPanelDisplayLayout);
        jPanelDisplayLayout.setHorizontalGroup(
            jPanelDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDisplayLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUnits))
                    .addGroup(jPanelDisplayLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDateFormat)))
                .addContainerGap())
        );
        jPanelDisplayLayout.setVerticalGroup(
            jPanelDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jTextFieldUnits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jTextFieldDateFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jLabel39.setText("Terms:");

        jButtonAcceptTerms.setText("(Re)Accept Terms Now");
        jButtonAcceptTerms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAcceptTermsActionPerformed(evt);
            }
        });

        jLabelTermsAccepted.setText("*terms_accepted*");

        javax.swing.GroupLayout jPanelPreferencesLayout = new javax.swing.GroupLayout(jPanelPreferences);
        jPanelPreferences.setLayout(jPanelPreferencesLayout);
        jPanelPreferencesLayout.setHorizontalGroup(
            jPanelPreferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPreferencesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPreferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelPreferencesLayout.createSequentialGroup()
                        .addComponent(jButtonAcceptTerms)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTermsAccepted, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelPreferencesLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelPreferencesLayout.setVerticalGroup(
            jPanelPreferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPreferencesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPreferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAcceptTerms)
                    .addComponent(jLabelTermsAccepted))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Update Password with Update User"));

        jLabel40.setText("Current Password:");

        jTextFieldCurrentPassword.setText("jTextField5");

        jLabel41.setText("New Password:");

        jTextFieldPassword.setText("jTextField6");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCurrentPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jTextFieldCurrentPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jTextFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setText("Timestamp:");

        jLabelTimestamp.setText("*timestamp*");

        jLabel9.setText("User ID:");

        jLabelUserID.setText("*userid*");

        jLabel17.setText("Password Expires:");

        jLabelPasswordExpires.setText("*password expiration*");

        jButtonModifyCurrentUserSettings.setText("Modify Current User Settings");
        jButtonModifyCurrentUserSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifyCurrentUserSettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldLastName))
                    .addComponent(jPanelPreferences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonModifyCurrentUserSettings))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelUserID))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelPasswordExpires))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldEmail))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelTimestamp))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabelTimestamp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabelUserID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabelPasswordExpires))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jTextFieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(jTextFieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPreferences, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonModifyCurrentUserSettings))
        );

        jPanelLoginInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "LoginInfo"));

        jLabel2.setText("Access Token:");

        jTextPaneAccessToken.setEditable(false);
        jScrollPane8.setViewportView(jTextPaneAccessToken);

        jLabel3.setText("Refresh Token:");

        jTextPaneRefreshToken.setEditable(false);
        jScrollPane9.setViewportView(jTextPaneRefreshToken);

        jLabel4.setText("Expires In:");

        jLabelExpiresIn.setText("*expires_in*");

        jLabel5.setText("Token Type:");

        jLabelTokenType.setText("*bearer*");

        jLabel6.setText("Scope:");

        jLabelScope.setText("*scope*");

        javax.swing.GroupLayout jPanelLoginInfoLayout = new javax.swing.GroupLayout(jPanelLoginInfo);
        jPanelLoginInfo.setLayout(jPanelLoginInfoLayout);
        jPanelLoginInfoLayout.setHorizontalGroup(
            jPanelLoginInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLoginInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLoginInfoLayout.createSequentialGroup()
                        .addGroup(jPanelLoginInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLoginInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9)
                            .addComponent(jScrollPane8)))
                    .addGroup(jPanelLoginInfoLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelExpiresIn)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTokenType)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelScope)
                        .addGap(0, 53, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelLoginInfoLayout.setVerticalGroup(
            jPanelLoginInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLoginInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLoginInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLoginInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelExpiresIn)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabelTokenType)
                    .addComponent(jLabel6)
                    .addComponent(jLabelScope))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "UsersMe Json"));

        jScrollPane3.setViewportView(jTreeCurrentUserInfo);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelUsersMeLayout = new javax.swing.GroupLayout(jPanelUsersMe);
        jPanelUsersMe.setLayout(jPanelUsersMeLayout);
        jPanelUsersMeLayout.setHorizontalGroup(
            jPanelUsersMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsersMeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUsersMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelLoginInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUsersMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelPasswordReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelUsersMeLayout.setVerticalGroup(
            jPanelUsersMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsersMeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUsersMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelUsersMeLayout.createSequentialGroup()
                        .addComponent(jPanelPasswordReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelUsersMeLayout.createSequentialGroup()
                        .addComponent(jPanelLoginInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );

        jTabbedPaneTabSet.addTab("Auth", jPanelUsersMe);

        jButtonAddUser.setText("Add User");
        jButtonAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddUserActionPerformed(evt);
            }
        });

        jButtonRunUserQuery.setText("Run Query");
        jButtonRunUserQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunUserQueryActionPerformed(evt);
            }
        });

        jButtonDeleteUser.setText("Delete Selected User");
        jButtonDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteUserActionPerformed(evt);
            }
        });

        jPanelUserFilter.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Filter Users"));

        jCheckBoxNotLocked.setText("Are not locked");

        jLabel14.setText("With Role:");

        jLabel13.setText("At_sid:");

        jLabel15.setText(" Include users that:");

        jCheckBoxHaveLoggedIn.setText("Have logged-in");

        jCheckBoxLocked.setText("Are locked");
        jCheckBoxLocked.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxLockedActionPerformed(evt);
            }
        });

        jCheckBoxHaveNotLoggedIn.setText("Have not logged-in");

        javax.swing.GroupLayout jPanelUserFilterLayout = new javax.swing.GroupLayout(jPanelUserFilter);
        jPanelUserFilter.setLayout(jPanelUserFilterLayout);
        jPanelUserFilterLayout.setHorizontalGroup(
            jPanelUserFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUserFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelUserFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanelUserFilterLayout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldWithRole))
                        .addGroup(jPanelUserFilterLayout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldAtSid, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelUserFilterLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxLocked)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxNotLocked)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxHaveLoggedIn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxHaveNotLoggedIn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelUserFilterLayout.setVerticalGroup(
            jPanelUserFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUserFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldAtSid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUserFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldWithRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUserFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jCheckBoxLocked)
                    .addComponent(jCheckBoxNotLocked)
                    .addComponent(jCheckBoxHaveLoggedIn)
                    .addComponent(jCheckBoxHaveNotLoggedIn))
                .addContainerGap())
        );

        jPanelUsersList.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "List of Users"));

        jTableUsers.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableUsers.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableUsers.setShowGrid(true);
        jTableUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableUsersMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableUsersMouseClicked(evt);
            }
        });
        jTableUsers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableUsersKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableUsers);

        javax.swing.GroupLayout jPanelUsersListLayout = new javax.swing.GroupLayout(jPanelUsersList);
        jPanelUsersList.setLayout(jPanelUsersListLayout);
        jPanelUsersListLayout.setHorizontalGroup(
            jPanelUsersListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsersListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanelUsersListLayout.setVerticalGroup(
            jPanelUsersListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsersListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonLoginUser.setText("LogIn");
        jButtonLoginUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginUserActionPerformed(evt);
            }
        });

        jTextFieldUsersPassword.setText("jTextField1");

        jLabel20.setText("User's Password: ");

        javax.swing.GroupLayout jPanelUsersLayout = new javax.swing.GroupLayout(jPanelUsers);
        jPanelUsers.setLayout(jPanelUsersLayout);
        jPanelUsersLayout.setHorizontalGroup(
            jPanelUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelUsersList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelUserFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanelUsersLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jButtonDeleteUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldUsersPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLoginUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRunUserQuery)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAddUser)
                .addGap(6, 6, 6))
        );
        jPanelUsersLayout.setVerticalGroup(
            jPanelUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelUserFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelUsersList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddUser)
                    .addComponent(jButtonDeleteUser)
                    .addComponent(jButtonRunUserQuery)
                    .addComponent(jButtonLoginUser)
                    .addComponent(jTextFieldUsersPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap())
        );

        jTabbedPaneTabSet.addTab("Users", jPanelUsers);

        jButtonAddCustomer.setText("Add Customer");
        jButtonAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddCustomerActionPerformed(evt);
            }
        });

        jPanelCustomerListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Customers"));

        jTableCustomers.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCustomers.setShowGrid(true);
        jTableCustomers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableCustomersMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCustomersMouseClicked(evt);
            }
        });
        jTableCustomers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableCustomersKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTableCustomers);

        javax.swing.GroupLayout jPanelCustomerListPanelLayout = new javax.swing.GroupLayout(jPanelCustomerListPanel);
        jPanelCustomerListPanel.setLayout(jPanelCustomerListPanelLayout);
        jPanelCustomerListPanelLayout.setHorizontalGroup(
            jPanelCustomerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustomerListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelCustomerListPanelLayout.setVerticalGroup(
            jPanelCustomerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustomerListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelCustomerDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Customer Details"));

        jTableCustomerAddress.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCustomerAddress.setShowGrid(true);
        jScrollPaneAddress.setViewportView(jTableCustomerAddress);

        javax.swing.GroupLayout jPanelAddressLayout = new javax.swing.GroupLayout(jPanelAddress);
        jPanelAddress.setLayout(jPanelAddressLayout);
        jPanelAddressLayout.setHorizontalGroup(
            jPanelAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelAddressLayout.setVerticalGroup(
            jPanelAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddressLayout.createSequentialGroup()
                .addComponent(jScrollPaneAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel26.setText("Name:");

        jTextFieldCustomerName.setText("jTextField1");

        jLabel28.setText("Ext SF ID:");

        jTextFieldCustomerExtSfId.setText("jTextField2");

        jLabel42.setText("Sid:");

        jLabelCustomerSid.setText("*customersid*");

        jButtonUpdateCustomer.setText("Update Customer");
        jButtonUpdateCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCustomerDetailsLayout = new javax.swing.GroupLayout(jPanelCustomerDetails);
        jPanelCustomerDetails.setLayout(jPanelCustomerDetailsLayout);
        jPanelCustomerDetailsLayout.setHorizontalGroup(
            jPanelCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustomerDetailsLayout.createSequentialGroup()
                .addGroup(jPanelCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCustomerDetailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCustomerDetailsLayout.createSequentialGroup()
                                .addGroup(jPanelCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelCustomerDetailsLayout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldCustomerName))
                                    .addGroup(jPanelCustomerDetailsLayout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldCustomerExtSfId, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)))
                                .addGap(18, 18, 18))
                            .addGroup(jPanelCustomerDetailsLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelCustomerSid)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustomerDetailsLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonUpdateCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanelAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelCustomerDetailsLayout.setVerticalGroup(
            jPanelCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustomerDetailsLayout.createSequentialGroup()
                .addGroup(jPanelCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelCustomerDetailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(jLabelCustomerSid))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jTextFieldCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(jTextFieldCustomerExtSfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonUpdateCustomer))
                    .addComponent(jPanelAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButtonQueryCustomers.setText("Query Customers");
        jButtonQueryCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQueryCustomersActionPerformed(evt);
            }
        });

        jButtonClearSelectedCustomer.setText("Clear Selected Customer");
        jButtonClearSelectedCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearSelectedCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCustomersLayout = new javax.swing.GroupLayout(jPanelCustomers);
        jPanelCustomers.setLayout(jPanelCustomersLayout);
        jPanelCustomersLayout.setHorizontalGroup(
            jPanelCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustomersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCustomerListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCustomerDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelCustomersLayout.createSequentialGroup()
                        .addComponent(jButtonAddCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClearSelectedCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonQueryCustomers)))
                .addContainerGap())
        );
        jPanelCustomersLayout.setVerticalGroup(
            jPanelCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustomersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCustomerListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelCustomerDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddCustomer)
                    .addComponent(jButtonQueryCustomers)
                    .addComponent(jButtonClearSelectedCustomer))
                .addContainerGap())
        );

        jTabbedPaneTabSet.addTab("Customers", jPanelCustomers);

        jButtonAddSite.setText("Add Site");
        jButtonAddSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddSiteActionPerformed(evt);
            }
        });

        jLabel24.setText("Selected Customer Sid:");

        jLabel25.setText("Name:");

        jButtonQuerySites.setText("Query Sites");
        jButtonQuerySites.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQuerySitesActionPerformed(evt);
            }
        });

        jLabelSelectedCustName.setText("*selected cust name*");

        jLabelSelectedCustomerSid.setText("*selectedCustomerSid*");

        jButtonSiteGenerator.setText("Site Generator");
        jButtonSiteGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSiteGeneratorActionPerformed(evt);
            }
        });

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.5);

        jScrollPane7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Site Info"));
        jScrollPane7.setViewportView(jTreeSiteInfo);

        jSplitPane1.setRightComponent(jScrollPane7);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("Site List"));

        jTableSiteList.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableSiteList.setShowGrid(true);
        jTableSiteList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableSiteListMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSiteListMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableSiteList);

        jSplitPane1.setLeftComponent(jScrollPane4);

        javax.swing.GroupLayout jPanelSitesLayout = new javax.swing.GroupLayout(jPanelSites);
        jPanelSites.setLayout(jPanelSitesLayout);
        jPanelSitesLayout.setHorizontalGroup(
            jPanelSitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSitesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSitesLayout.createSequentialGroup()
                        .addComponent(jButtonSiteGenerator)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAddSite))
                    .addGroup(jPanelSitesLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSelectedCustomerSid)
                        .addGap(176, 176, 176)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSelectedCustName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonQuerySites))
                    .addComponent(jSplitPane1))
                .addContainerGap())
        );
        jPanelSitesLayout.setVerticalGroup(
            jPanelSitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSitesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jButtonQuerySites)
                    .addComponent(jLabelSelectedCustName)
                    .addComponent(jLabelSelectedCustomerSid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSitesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddSite)
                    .addComponent(jButtonSiteGenerator))
                .addContainerGap())
        );

        jTabbedPaneTabSet.addTab("Sites", jPanelSites);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Trend Health"));

        jLabel27.setText("Auth Endpoint:");

        jLabelTrendAuthEndpoint.setText("*auth*");

        jLabel38.setText("Service:");

        jLabelTrendService.setText("*service*");

        jLabel43.setText("Issuer:");

        jLabelTrendIssuer.setText("*issuer*");

        jLabel45.setText("Audience:");

        jLabelTrendAud.setText("*aud*");

        jLabel47.setText("E3OS:");

        jLabelTrendE3OS.setText("*e3os*");

        jLabel49.setText("Issuer Thumb:");

        jLabelTrendIssuerThumb.setText("*IssuerThumb*");

        jLabel51.setText("Audience Thumb:");

        jLabelTrendAudienceThumb.setText("*audienceThumb*");

        jLabel44.setText("Version:");

        jLabelTrendVersion.setText("*version*");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTrendAuthEndpoint))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTrendService))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTrendIssuer))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTrendAud))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTrendE3OS))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTrendIssuerThumb))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTrendAudienceThumb))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTrendVersion)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabelTrendAuthEndpoint))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTrendService)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTrendIssuer)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTrendAud)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jLabelTrendE3OS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(jLabelTrendIssuerThumb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(jLabelTrendAudienceThumb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jLabelTrendVersion))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanelTrendCompanies.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Company Info"));

        jComboBoxTrendCompanies.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel18.setText("Companies:");

        jLabel21.setText("EffAvg:");

        jLabelEffAvg.setText("*effAvg*");

        jLabel23.setText("EffAvgDelta:");

        jLabelDelta.setText("*delta*");

        jLabel48.setText("co2saved:");

        jLabelCO2Saved.setText("*co2Saved*");

        jLabel52.setText("$saved:");

        jLabelMoneySaved.setText("*$saved*");

        jLabel54.setText("energySaved:");

        jLabelEngSaved.setText("*engSaved*");

        jLabel56.setText("Status:");

        jLabelStatus.setText("*status*");

        jLabel58.setText("Since:");

        jLabelSince.setText("*since*");

        jLabel60.setText("full:");

        jLabelFull.setText("*full*");

        jLabel62.setText("partial:");

        jLabelPartial.setText("*partial*");

        jLabelOff.setText("*off*");

        jLabel65.setText("off:");

        jLabelNot.setText("*not*");

        jLabel67.setText("not:");

        jLabel68.setText("commFailure:");

        jLabelCommFailure.setText("*commFailure*");

        javax.swing.GroupLayout jPanelTrendCompaniesLayout = new javax.swing.GroupLayout(jPanelTrendCompanies);
        jPanelTrendCompanies.setLayout(jPanelTrendCompaniesLayout);
        jPanelTrendCompaniesLayout.setHorizontalGroup(
            jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTrendCompaniesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTrendCompaniesLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxTrendCompanies, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelTrendCompaniesLayout.createSequentialGroup()
                        .addGroup(jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTrendCompaniesLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelEffAvg)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelDelta))
                            .addGroup(jPanelTrendCompaniesLayout.createSequentialGroup()
                                .addComponent(jLabel48)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelCO2Saved)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel52)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelMoneySaved)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel54)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelEngSaved)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel56)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelStatus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel58)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelSince))
                            .addGroup(jPanelTrendCompaniesLayout.createSequentialGroup()
                                .addComponent(jLabel60)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelFull)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel62)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelPartial)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel67)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelNot)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel65)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelOff)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel68)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelCommFailure)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTrendCompaniesLayout.setVerticalGroup(
            jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTrendCompaniesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jComboBoxTrendCompanies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabelEffAvg)
                    .addComponent(jLabel23)
                    .addComponent(jLabelDelta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jLabelCO2Saved)
                    .addComponent(jLabel52)
                    .addComponent(jLabelMoneySaved)
                    .addComponent(jLabel54)
                    .addComponent(jLabelEngSaved)
                    .addComponent(jLabel56)
                    .addComponent(jLabelStatus)
                    .addComponent(jLabel58)
                    .addComponent(jLabelSince))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel68)
                        .addComponent(jLabelCommFailure))
                    .addGroup(jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel67)
                        .addComponent(jLabelNot)
                        .addComponent(jLabel65)
                        .addComponent(jLabelOff))
                    .addGroup(jPanelTrendCompaniesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel60)
                        .addComponent(jLabelFull)
                        .addComponent(jLabel62)
                        .addComponent(jLabelPartial)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonGetTrendCompanies.setText("Get Companies");
        jButtonGetTrendCompanies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetTrendCompaniesActionPerformed(evt);
            }
        });

        jSplitPane2.setDividerLocation(300);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Site List"));

        jTableMobileSites.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableMobileSites.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableMobileSites.setShowGrid(true);
        jTableMobileSites.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMobileSitesMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableMobileSites);

        jSplitPane2.setTopComponent(jScrollPane5);

        jScrollPane6.setViewportView(jTreeMobileSiteInfo);

        jLabel8.setText("Time Period:");

        jComboBoxMobileTimePeriod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxMobileTrendTypes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel22.setText("Trend:");

        jButtonViewMobileChart.setText("Chart");
        jButtonViewMobileChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewMobileChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane6)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxMobileTimePeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 358, Short.MAX_VALUE)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxMobileTrendTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonViewMobileChart))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxMobileTimePeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxMobileTrendTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jButtonViewMobileChart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(jPanel6);

        javax.swing.GroupLayout jPanelTrendAPILayout = new javax.swing.GroupLayout(jPanelTrendAPI);
        jPanelTrendAPI.setLayout(jPanelTrendAPILayout);
        jPanelTrendAPILayout.setHorizontalGroup(
            jPanelTrendAPILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTrendAPILayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTrendAPILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelTrendCompanies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSplitPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTrendAPILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGetTrendCompanies, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelTrendAPILayout.setVerticalGroup(
            jPanelTrendAPILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTrendAPILayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTrendAPILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTrendAPILayout.createSequentialGroup()
                        .addComponent(jPanelTrendCompanies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanelTrendAPILayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 386, Short.MAX_VALUE)
                        .addComponent(jButtonGetTrendCompanies)))
                .addContainerGap())
        );

        jTabbedPaneTabSet.addTab("MobileAPI", jPanelTrendAPI);

        jPanelLoginControls.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("APIHost:");

        jButtonTotalSavings.setText("TotSvngs");
        jButtonTotalSavings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTotalSavingsActionPerformed(evt);
            }
        });

        jButtonViewRequests.setText("Requests");
        jButtonViewRequests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewRequestsActionPerformed(evt);
            }
        });

        jComboBoxApiHosts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxApiHosts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxApiHostsActionPerformed(evt);
            }
        });

        jButtonWizard.setText("Wiz");
        jButtonWizard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonWizardActionPerformed(evt);
            }
        });

        jLabel46.setText("Username:");

        jTextFieldLoginUsername.setText("jTextField1");

        jLabel50.setText("Password:");

        jPasswordFieldLoginPassword.setText("jPasswordField1");

        jButtonLogin.setText("Login");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLoginControlsLayout = new javax.swing.GroupLayout(jPanelLoginControls);
        jPanelLoginControls.setLayout(jPanelLoginControlsLayout);
        jPanelLoginControlsLayout.setHorizontalGroup(
            jPanelLoginControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxApiHosts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldLoginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordFieldLoginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonTotalSavings)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonWizard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonViewRequests)
                .addContainerGap())
        );
        jPanelLoginControlsLayout.setVerticalGroup(
            jPanelLoginControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLoginControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxApiHosts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonViewRequests)
                    .addComponent(jButtonWizard)
                    .addComponent(jButtonTotalSavings)
                    .addComponent(jLabel46)
                    .addComponent(jTextFieldLoginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(jPasswordFieldLoginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLogin))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPaneTabSet)
                    .addComponent(jPanelLoginControls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelLoginControls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneTabSet)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxApiHostsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxApiHostsActionPerformed
        //login();
    }//GEN-LAST:event_jComboBoxApiHostsActionPerformed

    private void jButtonViewRequestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewRequestsActionPerformed
        RRFrame frame = RRFrame.getInstance(controller);
        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonViewRequestsActionPerformed

    private void jButtonWizardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonWizardActionPerformed

        WizardFrame frame = new WizardFrame(controller);
        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonWizardActionPerformed

    private void jButtonTotalSavingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTotalSavingsActionPerformed
        TotalSavingsFrame frame = new TotalSavingsFrame(controller);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);

        controller.getTotalSavings();
    }//GEN-LAST:event_jButtonTotalSavingsActionPerformed

    private void jTabbedPaneTabSetComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTabbedPaneTabSetComponentShown

    }//GEN-LAST:event_jTabbedPaneTabSetComponentShown

    private void jTabbedPaneTabSetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPaneTabSetFocusGained

    }//GEN-LAST:event_jTabbedPaneTabSetFocusGained

    private void jButtonGetTrendCompaniesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetTrendCompaniesActionPerformed
        controller.getTrendCompanies();
    }//GEN-LAST:event_jButtonGetTrendCompaniesActionPerformed

    private void jTableSiteListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSiteListMouseClicked

        if (evt.getClickCount() == 2) {
            int row = jTableSiteList.rowAtPoint(evt.getPoint());
            int modelIndex = jTableSiteList.convertRowIndexToModel(row);
            SiteListTableModel mod = (SiteListTableModel) jTableSiteList.getModel();
            Site site = mod.getSiteAtIndex(modelIndex);
            this.selectedSiteSid = site.getSid();

            String customerSid = jLabelSelectedCustomerSid.getText();
            EditSiteDetailsFrame frame = EditSiteDetailsFrame.getInstance(controller);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            controller.addModelListener(frame);
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);

            GetSiteInfoQueryParams params = new GetSiteInfoQueryParams(new ArrayList<String>());

            controller.runSiteInfoQuery(this.selectedSiteSid, params);
            controller.getPointsConfig(site.getSid(), EnumPointsListDownloadType.JSON);
        }
    }//GEN-LAST:event_jTableSiteListMouseClicked

    private void jTableSiteListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSiteListMousePressed
        int row = jTableSiteList.rowAtPoint(evt.getPoint());
        int modelIndex = jTableSiteList.convertRowIndexToModel(row);
        SiteListTableModel mod = (SiteListTableModel) jTableSiteList.getModel();
        Site site = mod.getSiteAtIndex(modelIndex);
        this.selectedSiteSid = site.getSid();
    }//GEN-LAST:event_jTableSiteListMousePressed

    private void jButtonQuerySitesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQuerySitesActionPerformed
        if (this.jLabelSelectedCustomerSid != null && this.jLabelSelectedCustomerSid.getText().length() > 0) {
            controller.setCurrentCustomerSid(this.jLabelSelectedCustomerSid.getText());
            controller.runSiteListQuery();
        } else {
            controller.runSiteListQuery("");
        }
    }//GEN-LAST:event_jButtonQuerySitesActionPerformed

    private void jButtonAddSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddSiteActionPerformed
        Site site = new Site();
        site.setSid(this.jLabelSelectedCustomerSid.getText());
        NewSiteDetailsFrame frame = new NewSiteDetailsFrame(controller, this.jLabelSelectedCustomerSid.getText());
        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonAddSiteActionPerformed

    private void jButtonClearSelectedCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearSelectedCustomerActionPerformed
        this.jLabelSelectedCustomerSid.setText("");
        this.jLabelSelectedCustName.setText("");
        this.jTableCustomers.clearSelection();
        controller.setCurrentCustomerSid(null);
        this.selectedCustomer = null;
        controller.runSiteListQuery();
    }//GEN-LAST:event_jButtonClearSelectedCustomerActionPerformed

    private void jButtonQueryCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQueryCustomersActionPerformed
        controller.customersQuery();
    }//GEN-LAST:event_jButtonQueryCustomersActionPerformed

    private void jButtonUpdateCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateCustomerActionPerformed
        selectedCustomer.setCustomerName(jTextFieldCustomerName.getText());
        selectedCustomer.setExtSfId(jTextFieldCustomerExtSfId.getText());
        //The address is set in the table model

        controller.updateCustomer(selectedCustomer);
    }//GEN-LAST:event_jButtonUpdateCustomerActionPerformed

    private void jTableCustomersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableCustomersKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            this.clearSiteListTable();

            int row = jTableCustomers.getSelectedRow();
            int modelIndex = jTableCustomers.convertRowIndexToModel(row);
            CustomersTableModel mod = (CustomersTableModel) jTableCustomers.getModel();
            selectedCustomer = mod.getCustomerAtRow(modelIndex);

            fillCustomerDetails(selectedCustomer);

            this.jLabelSelectedCustomerSid.setText(selectedCustomer.getSid());
            this.jLabelSelectedCustName.setText(selectedCustomer.getCustomerName());
            this.jButtonQuerySites.setEnabled(true);
        }
    }//GEN-LAST:event_jTableCustomersKeyPressed

    private void jTableCustomersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCustomersMouseClicked
        if (evt.getClickCount() == 2) {

            int row = jTableCustomers.rowAtPoint(evt.getPoint());
            int modelIndex = jTableCustomers.convertRowIndexToModel(row);
            CustomersTableModel mod = (CustomersTableModel) jTableCustomers.getModel();
            selectedCustomer = mod.getCustomerAtRow(modelIndex);

            CustomerDetailsFrame frame = new CustomerDetailsFrame(controller, selectedCustomer, AddOrUpdateEnum.UPDATE);
            controller.addModelListener(frame);
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }
    }//GEN-LAST:event_jTableCustomersMouseClicked

    private void jTableCustomersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCustomersMousePressed

        this.clearSiteListTable();

        int row = jTableCustomers.rowAtPoint(evt.getPoint());
        int modelIndex = jTableCustomers.convertRowIndexToModel(row);
        CustomersTableModel mod = (CustomersTableModel) jTableCustomers.getModel();
        selectedCustomer = mod.getCustomerAtRow(modelIndex);
        fillCustomerDetails(selectedCustomer);

        this.jLabelSelectedCustomerSid.setText(selectedCustomer.getSid());
        this.jLabelSelectedCustName.setText(selectedCustomer.getCustomerName());

        if (evt.isPopupTrigger()) {
            PopupMenuForCopyingToClipboard popup = new PopupMenuForCopyingToClipboard(evt, jTableCustomers);
        }
    }//GEN-LAST:event_jTableCustomersMousePressed

    private void jButtonAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddCustomerActionPerformed
        Customer c = new Customer();
        c.setSid("NEW");
        CustomerDetailsFrame frame = new CustomerDetailsFrame(controller, c, AddOrUpdateEnum.ADD);
        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonAddCustomerActionPerformed

    private void jTableUsersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableUsersKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {

        }
    }//GEN-LAST:event_jTableUsersKeyPressed

    private void jTableUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableUsersMouseClicked

        if (evt.getClickCount() >= 2) {
            int row = jTableUsers.rowAtPoint(evt.getPoint());

            UsersTableModel mod = (UsersTableModel) this.jTableUsers.getModel();

            int modelIndex = jTableUsers.convertRowIndexToModel(row);
            UserInfo user = mod.getUserAtRow(modelIndex);

            UserDetailsFrame frame = new UserDetailsFrame(controller, user, AddOrUpdateEnum.UPDATE);
            controller.addModelListener(frame);
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }
    }//GEN-LAST:event_jTableUsersMouseClicked

    private void jTableUsersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableUsersMousePressed

        if (evt.isPopupTrigger()) {
            PopupMenuForCopyingToClipboard popup = new PopupMenuForCopyingToClipboard(evt, jTableUsers);
        }
    }//GEN-LAST:event_jTableUsersMousePressed

    private void jCheckBoxLockedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxLockedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxLockedActionPerformed

    private void jButtonDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteUserActionPerformed
        if (jTableUsers != null && jTableUsers.getSelectedRows().length > 0) {
            int rowNumber = jTableUsers.getSelectedRow();
            int modelIndex = jTableUsers.convertRowIndexToModel(rowNumber);
            UserInfo user = controller.getUsers().get(modelIndex);
            controller.deleteUser(user.getUserID());
        }
    }//GEN-LAST:event_jButtonDeleteUserActionPerformed

    private void jButtonRunUserQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunUserQueryActionPerformed

        UserQueryFilter filter = new UserQueryFilter(
                this.jTextFieldAtSid.getText(),
                this.jTextFieldWithRole.getText(),
                this.jCheckBoxLocked.isSelected(),
                this.jCheckBoxNotLocked.isSelected(),
                this.jCheckBoxHaveLoggedIn.isSelected(),
                this.jCheckBoxHaveNotLoggedIn.isSelected()
        );

        controller.usersQuery(filter);
    }//GEN-LAST:event_jButtonRunUserQueryActionPerformed

    private void jButtonAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddUserActionPerformed
        UserDetailsFrame frame = new UserDetailsFrame(controller, new UserInfo(), AddOrUpdateEnum.ADD);
        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonAddUserActionPerformed

    private void jButtonModifyCurrentUserSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifyCurrentUserSettingsActionPerformed

        ModifyCurrentUserRequest req = new ModifyCurrentUserRequest();

        req.setUsername(jTextFieldUsername.getText());
        req.setEmail(jTextFieldEmail.getText());
        req.setFirstName(jTextFieldFirstName.getText());
        req.setLastName(jTextFieldLastName.getText());

        CurrentUserDisplay cud = new CurrentUserDisplay();
        cud.setUnits(jTextFieldUnits.getText());
        cud.setDateFormat(jTextFieldDateFormat.getText());

        CurrentUserTerms cut = new CurrentUserTerms();
        cut.setAccepted(jLabelTermsAccepted.getText());
        CurrentUserPreferences cup = new CurrentUserPreferences();
        cup.setDisplay(cud);
        cup.setTerms(cut);
        req.setPreferences(cup);

        req.setCurrentPassword(jTextFieldCurrentPassword.getText());
        req.setPassword(jTextFieldPassword.getText());

        controller.putUsersMe(req);
    }//GEN-LAST:event_jButtonModifyCurrentUserSettingsActionPerformed

    private void jButtonAcceptTermsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAcceptTermsActionPerformed
        DateTime triggerTimestamp = DateTime.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        String triggerTimestampAsString = triggerTimestamp.toString(fmt) + ".000Z";
        this.jLabelTermsAccepted.setText(triggerTimestampAsString);
    }//GEN-LAST:event_jButtonAcceptTermsActionPerformed

    private void jButtonResetPWResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetPWResetActionPerformed
        String resetCode = this.jTextFieldPWResetCode.getText();
        String newPassword = this.jTextFieldPWResetPassword.getText();
        String confirm = this.jTextFieldResetPWConfirm.getText();

        PutForgotPasswordRequest req = new PutForgotPasswordRequest();
        req.setRestCode(resetCode);
        req.setPassword(newPassword);
        req.setConfirm(confirm);
        controller.putForgotPasword(req);
    }//GEN-LAST:event_jButtonResetPWResetActionPerformed

    private void jButtonResetPWForgotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetPWForgotActionPerformed

        String email = this.jTextFieldResetPWEmail.getText();
        String redirectURL = this.jTextFieldResetPWRedirect.getText();

        PostForgotPasswordRequest req = new PostForgotPasswordRequest();
        req.setEmail(email);
        req.setRedirectURL(redirectURL);
        controller.postForgotPasword(req);
    }//GEN-LAST:event_jButtonResetPWForgotActionPerformed

    private void jButtonVerifyTokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVerifyTokenActionPerformed
        if (jTextFieldVerifyTokenResetCode.getText().length() > 0) {
            VerifyResetPasswordTokenRequest req = new VerifyResetPasswordTokenRequest();
            req.setResetCode(jTextFieldVerifyTokenResetCode.getText());
            controller.postForgotPasswordVerifyToken(req);
        }
    }//GEN-LAST:event_jButtonVerifyTokenActionPerformed

    private void jButtonSiteGeneratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSiteGeneratorActionPerformed
        Site site = new Site();
        site.setSid(this.jLabelSelectedCustomerSid.getText());
        SiteGeneratorFrame frame = SiteGeneratorFrame.getInstance(controller);
        controller.addModelListener(frame);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonSiteGeneratorActionPerformed

    private void jButtonLoginUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginUserActionPerformed
        if (jTableUsers.getSelectedRowCount() > 0 && jTextFieldUsersPassword.getText().length() > 0) {
            int rowNumber = jTableUsers.getSelectedRow();

            int modelIndex = jTableUsers.convertRowIndexToModel(rowNumber);
            UsersTableModel mod = (UsersTableModel) jTableUsers.getModel();
            UserInfo selectedUser = mod.getUserAtRow(modelIndex);

            clearUserMeInfo();
            clearCurrentUserInfoTree();
            clearUsersTable();
            clearCustomersTable();

            APIHostsEnum host = APIHostsEnum.getHostFromName((String) jComboBoxApiHosts.getSelectedItem());
            String password = jTextFieldUsersPassword.getText();
            controller.login(host, selectedUser.getUserName(), password);

        }
    }//GEN-LAST:event_jButtonLoginUserActionPerformed

    private void jTableMobileSitesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMobileSitesMouseClicked
        int row = jTableMobileSites.rowAtPoint(evt.getPoint());
        int modelIndex = jTableMobileSites.convertRowIndexToModel(row);
        MobileSitesTableModel mod = (MobileSitesTableModel) jTableMobileSites.getModel();
        MobileSite site = mod.getSiteAtRow(modelIndex);

        this.selectedMobileSiteUUID = site.getUUID();
        String mobileTimePeriod = (String) jComboBoxMobileTimePeriod.getSelectedItem();

        controller.getMobileSiteOverview(selectedMobileSiteUUID, mobileTimePeriod);
    }//GEN-LAST:event_jTableMobileSitesMouseClicked

    private void jButtonViewMobileChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewMobileChartActionPerformed
        String trendType = (String) this.jComboBoxMobileTrendTypes.getSelectedItem();
        String timeFrame = (String) this.jComboBoxMobileTimePeriod.getSelectedItem();

        EnumMobileTrendTypes enumTrendType = EnumMobileTrendTypes.getEnumFromDropdownName(trendType);
        MobileTrendChartFrame mcf = new MobileTrendChartFrame(controller, selectedMobileSiteUUID, enumTrendType, timeFrame);
        controller.addModelListener(mcf);
        mcf.pack();
        mcf.setLocationRelativeTo(this);
        mcf.setVisible(true);

    }//GEN-LAST:event_jButtonViewMobileChartActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        login();
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void login() {
        
        if (jTextFieldLoginUsername != null && jComboBoxApiHosts.getSelectedObjects() != null) {
            AdminLoginCreds alc = new AdminLoginCreds( jTextFieldLoginUsername.getText(), jPasswordFieldLoginPassword.getPassword() );
            APIHostsEnum host = APIHostsEnum.getHostFromName((String) jComboBoxApiHosts.getSelectedItem());
            this.clearLoginInfo();
            controller.login(host, alc);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroupLockedUsers;
    private javax.swing.JButton jButtonAcceptTerms;
    private javax.swing.JButton jButtonAddCustomer;
    private javax.swing.JButton jButtonAddSite;
    private javax.swing.JButton jButtonAddUser;
    private javax.swing.JButton jButtonClearSelectedCustomer;
    private javax.swing.JButton jButtonDeleteUser;
    private javax.swing.JButton jButtonGetTrendCompanies;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonLoginUser;
    private javax.swing.JButton jButtonModifyCurrentUserSettings;
    private javax.swing.JButton jButtonQueryCustomers;
    private javax.swing.JButton jButtonQuerySites;
    private javax.swing.JButton jButtonResetPWForgot;
    private javax.swing.JButton jButtonResetPWReset;
    private javax.swing.JButton jButtonRunUserQuery;
    private javax.swing.JButton jButtonSiteGenerator;
    private javax.swing.JButton jButtonTotalSavings;
    private javax.swing.JButton jButtonUpdateCustomer;
    private javax.swing.JButton jButtonVerifyToken;
    private javax.swing.JButton jButtonViewMobileChart;
    private javax.swing.JButton jButtonViewRequests;
    private javax.swing.JButton jButtonWizard;
    private javax.swing.JCheckBox jCheckBoxHaveLoggedIn;
    private javax.swing.JCheckBox jCheckBoxHaveNotLoggedIn;
    private javax.swing.JCheckBox jCheckBoxLocked;
    private javax.swing.JCheckBox jCheckBoxNotLocked;
    private javax.swing.JComboBox jComboBoxApiHosts;
    private javax.swing.JComboBox<String> jComboBoxMobileTimePeriod;
    private javax.swing.JComboBox<String> jComboBoxMobileTrendTypes;
    private javax.swing.JComboBox<String> jComboBoxTrendCompanies;
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
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCO2Saved;
    private javax.swing.JLabel jLabelCommFailure;
    private javax.swing.JLabel jLabelCustomerSid;
    private javax.swing.JLabel jLabelDelta;
    private javax.swing.JLabel jLabelEffAvg;
    private javax.swing.JLabel jLabelEngSaved;
    private javax.swing.JLabel jLabelExpiresIn;
    private javax.swing.JLabel jLabelFull;
    private javax.swing.JLabel jLabelMoneySaved;
    private javax.swing.JLabel jLabelNot;
    private javax.swing.JLabel jLabelOff;
    private javax.swing.JLabel jLabelPartial;
    private javax.swing.JLabel jLabelPasswordExpires;
    private javax.swing.JLabel jLabelScope;
    private javax.swing.JLabel jLabelSelectedCustName;
    private javax.swing.JLabel jLabelSelectedCustomerSid;
    private javax.swing.JLabel jLabelSince;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTermsAccepted;
    private javax.swing.JLabel jLabelTimestamp;
    private javax.swing.JLabel jLabelTokenType;
    private javax.swing.JLabel jLabelTrendAud;
    private javax.swing.JLabel jLabelTrendAudienceThumb;
    private javax.swing.JLabel jLabelTrendAuthEndpoint;
    private javax.swing.JLabel jLabelTrendE3OS;
    private javax.swing.JLabel jLabelTrendIssuer;
    private javax.swing.JLabel jLabelTrendIssuerThumb;
    private javax.swing.JLabel jLabelTrendService;
    private javax.swing.JLabel jLabelTrendVersion;
    private javax.swing.JLabel jLabelUserID;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelAddress;
    private javax.swing.JPanel jPanelCustomerDetails;
    private javax.swing.JPanel jPanelCustomerListPanel;
    private javax.swing.JPanel jPanelCustomers;
    private javax.swing.JPanel jPanelDisplay;
    private javax.swing.JPanel jPanelLoginControls;
    private javax.swing.JPanel jPanelLoginInfo;
    private javax.swing.JPanel jPanelPasswordReset;
    private javax.swing.JPanel jPanelPreferences;
    private javax.swing.JPanel jPanelSites;
    private javax.swing.JPanel jPanelTrendAPI;
    private javax.swing.JPanel jPanelTrendCompanies;
    private javax.swing.JPanel jPanelUserFilter;
    private javax.swing.JPanel jPanelUsers;
    private javax.swing.JPanel jPanelUsersList;
    private javax.swing.JPanel jPanelUsersMe;
    private javax.swing.JPasswordField jPasswordFieldLoginPassword;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollPaneAddress;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPaneTabSet;
    private javax.swing.JTable jTableCustomerAddress;
    private javax.swing.JTable jTableCustomers;
    private javax.swing.JTable jTableMobileSites;
    private javax.swing.JTable jTableSiteList;
    private javax.swing.JTable jTableUsers;
    private javax.swing.JTextField jTextFieldAtSid;
    private javax.swing.JTextField jTextFieldCurrentPassword;
    private javax.swing.JTextField jTextFieldCustomerExtSfId;
    private javax.swing.JTextField jTextFieldCustomerName;
    private javax.swing.JTextField jTextFieldDateFormat;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldFirstName;
    private javax.swing.JTextField jTextFieldLastName;
    private javax.swing.JTextField jTextFieldLoginUsername;
    private javax.swing.JTextField jTextFieldPWResetCode;
    private javax.swing.JTextField jTextFieldPWResetPassword;
    private javax.swing.JTextField jTextFieldPassword;
    private javax.swing.JTextField jTextFieldResetPWConfirm;
    private javax.swing.JTextField jTextFieldResetPWEmail;
    private javax.swing.JTextField jTextFieldResetPWRedirect;
    private javax.swing.JTextField jTextFieldUnits;
    private javax.swing.JTextField jTextFieldUsername;
    private javax.swing.JTextField jTextFieldUsersPassword;
    private javax.swing.JTextField jTextFieldVerifyTokenResetCode;
    private javax.swing.JTextField jTextFieldVerifyTokenResponse;
    private javax.swing.JTextField jTextFieldWithRole;
    private javax.swing.JTextPane jTextPaneAccessToken;
    private javax.swing.JTextPane jTextPaneRefreshToken;
    private javax.swing.JTree jTreeCurrentUserInfo;
    private javax.swing.JTree jTreeMobileSiteInfo;
    private javax.swing.JTree jTreeSiteInfo;
    // End of variables declaration//GEN-END:variables
}
