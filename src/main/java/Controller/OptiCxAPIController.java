package Controller;

import Model.DataModels.Auth.AdminLoginCreds;
import Model.RestClient.APIHostsEnum;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.RequestsResponses;
import Model.DataModels.Auth.GetE3OSTokenRequest;
import Model.DataModels.Auth.PostForgotPasswordRequest;
import Model.DataModels.Auth.PutForgotPasswordRequest;
import Model.DataModels.Auth.VerifyResetPasswordTokenRequest;
import Model.DataModels.Customers.Customer;
import Model.DataModels.Datapoints.DatapointHistoriesQueryParams;
import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.PostDatapointRequest;
import Model.DataModels.Datapoints.simulator.DGArgs;
import Model.DataModels.Graph.EnumGraphNodeTypes;
import Model.DataModels.Live.PostLiveData.PostLiveDataRequest;
import Model.DataModels.Live.Subscriptions.CreateSubscriptionRequest;
import Model.DataModels.ReportVerification.ReportHistoryQueryParams;
import Model.DataModels.Sites.CreateSiteRequest;
import Model.DataModels.Sites.EnumPointsListDownloadType;
import Model.DataModels.Sites.GetSiteInfoQueryParams;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.SiteTrendAndKPIRequest;
import Model.DataModels.Sites.TestSiteTemplates.EnumTestSites;
import Model.DataModels.Sites.UpdateSiteRequest;
import Model.DataModels.Stations.HistoryPushObject;
import Model.DataModels.Stations.StationActivateRequest;
import Model.DataModels.Stations.StationAlarmPushObject;
import Model.DataModels.Stations.StationAuditHistory;
import Model.DataModels.Stations.StationLogHistory;
import Model.DataModels.Stations.WizardStationStatus;
import Model.DataModels.Stations.StationValidateQueryParams;
import Model.DataModels.Stations.StationsHeartbeat;
import Model.DataModels.TeslaModels.ComboHistories.ComboHistories;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaGenEquipment;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostCustomer;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostSite;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostStation;
import Model.DataModels.TeslaModels.EnumTeslaBaseURLs;
import Model.DataModels.TeslaModels.EnumTeslaUsers;
import Model.DataModels.TeslaModels.MappingTableRow;
import Model.DataModels.TeslaModels.TeslaDataPointUpsertRequest;
import Model.DataModels.TeslaModels.TeslaHistoryRequest;
import Model.DataModels.TrendAPI.MobileCompanyList;
import Model.DataModels.TrendAPI.MobileCompanyOverview;
import Model.DataModels.TrendAPI.MobileHealthInfo;
import Model.DataModels.TrendAPI.SiteInfo.EnumMobileTrendTypes;
import Model.DataModels.TrendAPI.SiteInfo.MobileSiteInfo;
import Model.DataModels.Users.CreateUserRequest;
import Model.DataModels.Users.CurrentUserInfoResponse;
import Model.DataModels.Users.ModifyCurrentUserRequest;
import Model.DataModels.Users.ModifyUsersPasswordRequest;
import Model.DataModels.Users.NotificationSettingList;
import Model.DataModels.Users.RoleItem;
import Model.DataModels.Users.UserInfo;
import Model.DataModels.Users.UserQueryFilter;
import Model.DataModels.Wizard.CompilePostCommand;
import Model.DataModels.Wizard.NetworkSettingsResponse;
import Model.DataModels.Wizard.WizardActivationPostRequest;
import Model.DataModels.Wizard.WizardPoint;
import Model.DataModels.Wizard.WizardPostConfigFileInfo;
import Model.DataModels.Wizard.WizardPostConfigFilesRequest;
import Model.DataModels.Wizard.WizardStep;
import Model.DataModels.Wizard.WizardUser;
import Model.RestClient.LoginResponse;
import Model.RestClient.OEResponse;
import View.MainFrame;
import View.Sites.EditSite.A_History.DPHistoryChart.StampsAndPoints;
import View.Sites.EditSite.A_History.DataGenerator.DGTableRow;
import View.TreeInfo.CustomTreeNode;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;

public class OptiCxAPIController implements java.awt.event.ActionListener, PropertyChangeListener {

    private OptiCxAPIModel model = null;
    private MainFrame view = null;

    public OptiCxAPIController() {

    }

    public void tellControllerAboutTheModel(OptiCxAPIModel model) {
        this.model = model;
        model.addPropChangeListener(this);
    }

    public void addModelListener(PropertyChangeListener listener) {
        model.addPropChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        model.removePropChangeListener(listener);
    }

    public OptiCxAPIModel getModel() {
        return model;
    }

    public RequestsResponses getRRS() {
        return model.getRRS();
    }

    public void clearRRS() {
        model.clearRRS();
    }

    public void tellTheControllerAboutTheView(MainFrame view) {
        this.view = view;
    }

    public void initModel() {

        try {
            model.initModel();
            view.fillAPIHosts();

        } catch (IOException ex) {
            Logger.getLogger(OptiCxAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    //OTHER
    public void fireResetHistoryChartEvent(StampsAndPoints sp) {
        model.fireResetHistoryChartEvent(sp);
    }

    //LOGIN
    public void login(APIHostsEnum loc, String userName, String password) {
        model.login(loc, userName, password);
    }

    public void login(APIHostsEnum apiHost, AdminLoginCreds admin) {
        model.login(apiHost, admin);
    }

    public LoginResponse getLoginResponse() {
        return model.getLoginResponse();
    }

    public void getOauthToken(final String username, final String password) {
        model.getOauthToken(username, password);
    }

    public void getOriginatedCallInEndpoint() {
        model.getOriginatedCallInEndpoint();
    }

    public void getE3OSToken(GetE3OSTokenRequest req) {
        model.getE3OSToken(req);
    }

    public void postForgotPasword(PostForgotPasswordRequest req) {
        model.postForgotPasword(req);
    }

    public void putForgotPasword(PutForgotPasswordRequest req) {
        model.putForgotPasword(req);
    }

    public void postForgotPasswordVerifyToken(VerifyResetPasswordTokenRequest req) {
        model.postForgotPasswordVerifyToken(req);
    }

    //CURRENT USER
    public CurrentUserInfoResponse getAdminUserInfo() {
        return model.getUserMeFromResponseObj();
    }

    //STATIONS
    public void getActivationAvaiablity() {
        model.getActivationAvaiablity();
    }

    public void postActivate(StationActivateRequest req) {
        model.postActivate(req);
    }

    public void getStationBogFile(String stationId) {
        model.getStationBogFile(stationId);
    }

    public void getConfigurationProfile(String stationId) {
        model.getConfigurationProfile(stationId);
    }

    public void getPointsConfiguration(String stationId) {
        model.getPointsConfiguration(stationId);
    }

    public void getDefaultParameters(String stationId) {
        model.getDefaultParameters(stationId);
    }

    public void getPortalParameters(String stationId) {
        model.getPortalParameters(stationId);
    }

    public void validateConfiguration(StationValidateQueryParams params) {
        model.validateConfiguration(params);
    }

    public void getConfigurationStatus(String stationId) {
        model.getConfigurationStatus(stationId);
    }

    public void pushConfigurationStatus(String stationId, WizardStationStatus status) {
        model.pushConfigurationStatus(stationId, status);
    }

    public void pushAuditHistory(StationAuditHistory auditHistory) {
        model.pushAuditHistory(auditHistory);
    }

    public void pushStationLog(StationLogHistory stationLogHistory) {
        model.pushStationLog(stationLogHistory);
    }

    public void postDatapointHistory(HistoryPushObject history) {
        model.postDatapointHistory(history);
    }

    public void repostDatapointHistory(String timeStamp, String stationId, List<DatapointHistoriesResponse> oldhistory) {
        model.repostDatapointHistory(timeStamp, stationId, oldhistory);
    }

    public void pushAlarmChanges(StationAlarmPushObject alarms) {
        model.pushAlarmChanges(alarms);
    }

    public void pushHeartbeat(String stationId, String token, StationsHeartbeat heartBeat) {
        model.pushHeartbeat(stationId, token, heartBeat);
    }

    public void pushHistoryForPeriod(EnumGraphNodeTypes scope, DGArgs args, Map<String, DGTableRow> tableRowsMap) {
        model.pushHistoryForPeriod(scope, args, tableRowsMap);
    }

    //ALARMS
    /*
    public void getAlarmList(String sid, AlarmListRequest alarmList) {
        model.getAlarmList(sid, alarmList);
    }

    public void postNewAlarm(String sid, CreateAlarmRequest req) {
        model.postNewAlarm(sid, req);
    }

    public void deleteAlarm(String sid, String name) {
        model.deleteAlarm(sid, name);
    }

    public void associateAlarm(String sid, AssociateAlarmReq req) {
        model.associateAlarm(sid, req);
    }

    public void getAlarmDetails(String sid, String name, AlarmListRequest req) {
        model.getAlarmDetails(sid, name, req);
    }

    public void postTriggerOrClearAlarm(String sid, String alarmSid, String name, TriggerOrClearAlarmRequest req) {
        model.postTriggerOrClearAlarm(sid, alarmSid, name, req);
    }

    public void updateAlarmMetadata(String sid, String name, UpdateAlarmRequest req) {
        model.updateAlarmMetadata(sid, name, req);
    }

    public void getAlarmHistory(String sid, AlarmsHistoryQueryParams queryParams) {
        model.getAlarmHistory(sid, queryParams);
    }

    public void getSpecificAlarmHistory(String sid, String name) {
        model.getSpecificAlarmHistory(sid, name);
    }

    public void getAlarmNotes(String sid, String name) {
        model.getAlarmNotes(sid, name);
    }

    public void postAlarmNote(String sid, String name, String note) {
        model.postAlarmNote(sid, name, note);
    }

    public void deleteAlarmNote(String sid, String name, String note_id) {
        model.deleteAlarmNote(sid, name, note_id);
    }
     */
    //LIVE
    public void getLiveData(String subscriptionId) {
        model.getLiveData(subscriptionId);
    }

    public void postNewSubscription(CreateSubscriptionRequest req) {
        model.postNewSubscription(req);
    }

    public void postLiveData(String token, String stationId, PostLiveDataRequest req) {
        model.postLiveData(token, stationId, req);
    }

    //Graph
    /*
    public void graphGetCustomerList() {
        model.graphGetCustomerList();
    }

    public void deleteGraphElement(String sid) {
        model.deleteGraphElement(sid);
    }

    public void getElementMetaData(String sid, GetMetaDataRequest mdr) {
        model.getElementMetaData(sid, mdr);
    }

    public void getSiteDetailsFromGraph(String sid) {
        model.getSiteDetailsFromGraph(sid);
    }
    
     */
 /*
    public void getSiteAttributesFromGraph(String sid) {
        model.getSiteAttributesFromGraph(sid);
    }
     */
    public void runQuery(final String queryString) {
        model.runQuery(queryString);
    }

    public void getChildren(String sid) {
        model.getChildren(sid);
    }

    public void upsertElement(String sid, EnumGraphNodeTypes nodeType, String payload) {
        model.upsertElement(sid, nodeType, payload);
    }

    public void getElementSchema(String sid, EnumGraphNodeTypes nodeType) {
        model.getElementSchema(sid, nodeType);
    }

    public void getRulesInfo(String siteSid) {
        model.getRulesInfo(siteSid);
    }

    //WIZARD
    public void getCompileStatus() {
        model.getCompileStatus();
    }

    public void postCompileCommand(CompilePostCommand cmd) {
        model.postCompileCommand(cmd);
    }

    public void getNetworkSettings() {
        model.getNetworkSettings();
    }

    public void putNetworkSettings(NetworkSettingsResponse nwSettings) {
        model.putNetworkSettings(nwSettings);
    }

    public void getPlantProfile() {
        model.getWizardPlantProfile();
    }

    public void getPointsList() {
        model.getWizardPointsList();
    }

    public void getControlPoint(String pointName) {
        model.getControlPoint(pointName);
    }

    public void upsertControlPoint(WizardPoint point) {
        model.upsertControlPoint(point);
    }

    public void getWizardUsersMe() {
        model.getWizardUsersMe();
    }

    public void putWizardUsersMe(WizardUser user) {
        model.putWizardUsersMe(user);
    }

    public void getWizardSteps() {
        model.getWizardSteps();
    }

    public void getWizardStep(String stepId) {
        model.getWizardStep(stepId);
    }

    public void putWizardStep(String stepId, WizardStep wizardStep) {
        model.putWizardStep(stepId, wizardStep);
    }

    public void getCloudActivationStatus() {
        model.getCloudActivationStatus();
    }

    public void postCloudActivation(WizardActivationPostRequest activationRequest) {
        model.postCloudActivation(activationRequest);
    }

    public void getCloudAuthentication() {
        model.getCloudAuthentication();
    }

    public void postCloudAuthentication(String whatever) {
        model.postCloudAuthentication(whatever);
    }

    public void getCloudConnection() {
        model.getCloudConnection();
    }

    public void postCloudConnection(String whatever) {
        model.postCloudConnection(whatever);
    }

    public void getOptimizationConfigurationFile() {
        model.getOptimizationConfigurationFile();
    }

    public void postOptimizationConfigurationFile(WizardPostConfigFilesRequest configFiles) {
        model.postOptimizationConfigurationFile(configFiles);
    }

    public void getOptimizationProgram() {
        model.getOptimizationProgram();
    }

    public void postOptimizationProgram(WizardPostConfigFileInfo configFile) {
        model.postOptimizationProgram(configFile);
    }

    public void getOptimizationStatus() {
        model.getOptimizationStatus();
    }

    //USERS
    public void usersQuery(UserQueryFilter filter) {
        model.getUsersWithFilter(filter.getQueryString());
    }

    public List<UserInfo> getUsers() {
        return model.getUserList();
    }

    public void createUser(CreateUserRequest createUserRequest) {
        model.createUser(createUserRequest);
    }

    public void createSiteUsersAndOrSetRoles(EnumTestSites templateType, Site site) {
        model.createSiteUsersAndOrSetRoles(templateType, site);
    }

    public void deleteUser(String userID) {
        model.deleteUser(userID);
    }

    public UserInfo getUser(String userID) {
        return model.getUserFromUserList(userID);
    }

    //TODO - why return only first name, last name, phones (only)
    public void queryForSpecificUserInfo(String userID) {
        model.getUser(userID);
    }

    public UserInfo getSelectedUserInfo() {
        return model.getUserSetFromResponseObj();
    }

    public void putUserInfo(UserInfo user) {
        model.putUserInfo(user);
    }

    public void overwriteAllRoleMappings(String userSid, List<RoleItem> roles) {
        model.overwriteAllRoleMappings(userSid, roles);
    }

    public void changeRolesForSid(String userSid, String roleSid, List<RoleItem> roles) {
        model.changeRolesForSid(userSid, roleSid, roles);
    }

    public void deleteUserRole(String userID, String roleSid) {
        model.deleteUserRole(userID, roleSid);
    }

    public void getUsersMe() {
        model.getUsersMe();
    }

    public void putUsersMe(ModifyCurrentUserRequest req) {
        model.putUsersMe(req);
    }

    public void getNotifications(String userId) {
        model.getNotifications(userId);
    }

    public void setNotifications(String userId, NotificationSettingList notifications) {
        model.setNotifications(userId, notifications);
    }

    public void postPassword(ModifyUsersPasswordRequest req) {
        model.postPassword(req);
    }

    //CUSTOMERS
    public void getTotalSavings() {
        model.getTotalSavings();
    }

    public void customersQuery() {
        model.customersQuery();
    }

    public List<Customer> getCustomers() {
        return model.getCustomers();
    }

    public Customer getCustomer(String sid) {
        return model.getCustomer(sid);
    }

    public void addCustomer(Customer customer) {
        model.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) {
        model.updateCustomer(customer);
    }

    public void queryCustomerDetails(String sid) {
        model.queryCustomerDetails(sid);
    }

    public void setCurrentCustomerSid(String customerSid) {
        model.setCurrentCustomerSid(customerSid);
    }

    //SITES
    public void runSiteListQuery(String customerSid) {
        model.runSiteListQuery(customerSid);
    }

    public void runSiteListQuery() {
        model.runSiteListQuery();
    }

    public List<Site> getSiteList() {
        return model.getSiteList();
    }

    public Site getSelectedSiteInfo() {
        return model.getSelectedSiteInfo();
    }

    public void runSiteInfoQuery(String siteSid, GetSiteInfoQueryParams params) {
        model.runSiteInfoQuery(siteSid, params);
    }

    public void updateSite(String siteSid, Boolean forceUpdate, UpdateSiteRequest usr) {
        model.updateSite(siteSid, forceUpdate, usr);
    }

    public void createSite(final CreateSiteRequest csr) {
        model.createSite(csr);
    }

    public void querySiteTrend(String sid, SiteTrendAndKPIRequest req) {
        model.querySiteTrend(sid, req);
    }

    public void getSiteKPI(String sid, String kpi, SiteTrendAndKPIRequest req) {
        model.getSiteKPI(sid, kpi, req);
    }

    public void getPointsConfig(final String sid, final EnumPointsListDownloadType downLoadType) {
        model.getPointsConfig(sid, downLoadType);
    }

    public void callForNewActivationCode(String sid) {
        model.getNewActivationCode(sid);
    }

    public void callCheckForUpdates(String sid) {
        model.callCheckForUpdates(sid);
    }

    public void getUIMetaData(String productType, String sid, String view) {
        model.getUIMetaData(productType, sid, view);
    }

    public void getAlarmHistory(String siteSid, String startDateString, String endDateString) {
        model.getAlarmHistory(siteSid, startDateString, endDateString);
    }

    public void getAlarms(String siteSid) {
        model.getAlarms(siteSid);
    }

    //DATAPOINTS
    public void getDatapoints(String sid, String listOfPoints) {
        model.getDatapoints(sid, listOfPoints);
    }

    public void getDatapointsUnion(final Map<String, List<String>> sidsAndPoints) {
        model.getDatapointsUnion(sidsAndPoints);
    }

    public void getDatapointsUnionForReports(final Map<String, List<String>> sidsAndPoints) {
        model.getDatapointsUnionForReports(sidsAndPoints);
    }

    public void postDatapoints(PostDatapointRequest req) {
        model.postDatapoints(req);
    }

    public void getDatapointHistories(final DatapointHistoriesQueryParams params) {
        model.getDatapointHistories(params);
    }

    public void getComplexDatapointHistories(List<DatapointHistoriesQueryParams> listOfQueryParams) {
        model.getComplexDatapointHistories(listOfQueryParams);
    }

    public void getHistoryForReportVerification(ReportHistoryQueryParams reportHistoryParams) {
        model.getHistoryForReportVerification(reportHistoryParams);
    }

    //Tesla
    public void teslaLogin(final EnumTeslaBaseURLs baseUrl, final EnumTeslaUsers user) {
        model.teslaLogin(baseUrl, user);
    }

    public void getTeslaStations() {
        model.getTeslaStations();
    }

    public void getTeslaStationInfo(String stationID) {
        model.getTeslaStationInfo(stationID);
    }

    public void getTeslaStationDatapoints(String stationID) {
        model.getTeslaStationDatapoints(stationID);
    }

    public void setEdisonClient() {
        model.setEdisonClient();
    }

    public void pullFromEdisonPushToTesla(String querySid, DateTime pushStartTime, DateTime pushEndTime,
            List<MappingTableRow> mappedRows, int maxHoursPerPush, int maxPointsPerPush) {
        model.pullFromEdisonPushToTesla(querySid, pushStartTime, pushEndTime, mappedRows,
                maxHoursPerPush, maxPointsPerPush);
    }

    public void postCustomer(TeslaPostCustomer postCustomer) {
        model.postCustomer(postCustomer);
    }

    public void postSite(String customerId, TeslaPostSite postSite) {
        model.postSite(customerId, postSite);
    }

    public void postStation(String siteId, TeslaPostStation postStation) {
        model.postStation(siteId, postStation);
    }

    public void postEquipmentList(final String stationId, final List<TeslaGenEquipment> equipList) {
        model.postEquipmentList(stationId, equipList);
    }

    public void getTeslaHistory(TeslaHistoryRequest historyRequest) {
        model.getTeslaHistory(historyRequest);
    }
    
    public void getTeslaAndEdisonHistory( List<DatapointHistoriesQueryParams> listOfParams, TeslaHistoryRequest historyRequest ){
        model.getTeslaAndEdisonHistory(listOfParams, historyRequest);
    }

    public void postSparsePoints(TeslaDataPointUpsertRequest upsertRequest) {
        model.postSparsePoints(upsertRequest);
    }
    
    public void createCSV(final String filePath, final ComboHistories comboHistories){
        model.createCSV(filePath, comboHistories);
    }
    
    public void createEdisonCSV( String filePath, int prec, List<String> uNames, List<String> timestamps, Map< String, Map<String, Object>> timestampToUNameValueMap ) {
        model.createEdisonCSV(filePath, prec, uNames, timestamps, timestampToUNameValueMap);
    }

    /*
    public void getHistory(String sid, HistoryQueryParams params) {
        model.getHistory(sid, params);
    }

    public void putHistory(String sid, String token, PushDatapointsRequest req) {
        model.putHistory(sid, token, req);
    }

    public void postQuery(String sid) {
        model.postQuery(sid);
    }

    public void deleteQuery(String sid, String name) {
        model.deleteQuery(sid, name);
    }

    public void getDatapointQueryResults(String sid, String name) {
        model.getDatapointQueryResults(sid, name);
    }

    public void deleteDatapointAssociation(String sid, String name) {
        model.deleteDatapointAssociation(sid, name);
    }

    public void getDatapointMetadata(String sid, String name) {
        model.getDatapointMetadata(sid, name);
    }

    public void getDatapointMetadataMultipleSids(Map<String, List<String>> sidsAndPoints) {
        model.getDatapointMetadataMultipleSids(sidsAndPoints);
    }

    public void updateDatapointMetadata(String sid, String name, UpdateMetaDataRequest req) {
        model.updateDatapointMetadata(sid, name, req);
    }

    public void putHistoryFromXLS(
            String sid,
            List<String> timeStamps,
            Map<String, List<Object>> pointsAndValues,
            Map<String, DatapointMetadata> nameToMetadataMap
    ) {
        model.putHistoryFromXLS(
                sid,
                timeStamps,
                pointsAndValues,
                nameToMetadataMap);
    }
     */
    //TREND API
    public void getTrendHealth() {
        model.getMobileHealth();
    }

    public void getTrendVersion() {
        model.getMobileVersion();
    }

    public void getTrendCompanies() {
        model.getMobileCompanies();
    }

    public void getMobileCompanyOverview(String uuid) {
        model.getMobileCompanyOverview(uuid);
    }

    public void getMobileSiteOverview(String uuid, String timeFrame) {
        model.getMobileSiteOverview(uuid, timeFrame);
    }

    public void getMobileTrend(String uuid, EnumMobileTrendTypes trendType, String timeFrame) {
        model.getMobileTrend(uuid, trendType, timeFrame);
    }

    //REPORTS
    public void getReportsList(String sid, boolean onlyApproved) {
        model.getReportsList(sid, onlyApproved);
    }

    public void getReportsSchema(String reportId) {
        model.getReportSchema(reportId);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Object obj = e.getSource();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String propName = evt.getPropertyName();

        if (propName.equals(PropertyChangeNames.ErrorResponse.getName())) {
            view.showError((OEResponse) evt.getNewValue());

        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            view.enableTabSet(false);
            view.clearLoginInfo();
            view.clearUserMeInfo();
            view.clearCurrentUserInfoTree();
            view.clearUsersTable();
            view.clearCustomerDetails();
            view.clearCustomersTable();
            view.clearSiteListTable();
            view.clearTrendInfo();

            view.fillInLoginInfo(model.getLoginResponse());

            if (model.isLoggedIn()) {
                view.enableTabSet(true);
                model.getUsersMe();
                model.getUsersWithFilter((new UserQueryFilter()).getQueryString());
                model.customersQuery();
                //model.graphGetCustomerList();
                model.runSiteListQuery("");
                //model.getTrendAPIHealth();
                //model.getTrendVersion();
                view.clearTrendInfo();
            } else {
                view.enableTabSet(false);
                view.clearLoginInfo();
                view.clearUserMeInfo();
                view.clearCurrentUserInfoTree();
                view.clearUsersTable();
                view.clearCustomersTable();
                view.clearSiteListTable();
                view.clearTrendInfo();

            }

        } else if (propName.equals(PropertyChangeNames.CurrentUserInfoRetrieved.getName())) {
            view.fillUserMeInfo(model.getUserMeFromResponseObj());
            view.fillCurrentUserInfoTree(model.getCurrentUserInfoTreeRootNode());

        } else if (propName.equals(PropertyChangeNames.UsersListChanged.getName())) {
            view.fillUsersTable(model.getUserList());

        } else if (propName.equals(PropertyChangeNames.UserCreated.getName())) {
            model.getUsersWithFilter((new UserQueryFilter()).getQueryString());

        } else if (propName.equals(PropertyChangeNames.UserUpdated.getName())) {
            model.getUsersWithFilter((new UserQueryFilter()).getQueryString());

        } else if (propName.equals(PropertyChangeNames.UserDeleted.getName())) {
            model.getUsersWithFilter((new UserQueryFilter()).getQueryString());

        } else if (propName.equals(PropertyChangeNames.UserRolesChanged.getName())) {
            model.getUsersWithFilter((new UserQueryFilter()).getQueryString());

        } else if (propName.equals(PropertyChangeNames.UserRoleDeleted.getName())) {
            model.getUsersWithFilter((new UserQueryFilter()).getQueryString());

        } else if (propName.equals(PropertyChangeNames.CustomerListChanged.getName())) {
            List<Customer> customers = (List<Customer>) evt.getNewValue();
            view.fillCustomersTable(customers);

        } else if (propName.equals(PropertyChangeNames.CustomerCreated.getName())) {
            model.customersQuery();

        } else if (propName.equals(PropertyChangeNames.CustomerUpdated.getName())) {
            model.customersQuery();

        } else if (propName.equals(PropertyChangeNames.SiteCreated.getName())) {
            Site msg = (Site) evt.getNewValue();
            model.runSiteListQuery();

        } else if (propName.equals(PropertyChangeNames.SiteListChanged.getName())) {
            view.fillSiteListTable(model.getSiteList());

            //jTreeSiteInfo
        } else if (propName.equals(PropertyChangeNames.GraphSiteAttributesReturned.getName())) {
            CustomTreeNode siteInfoTree = (CustomTreeNode) evt.getNewValue();
            view.fillSiteInfoTree(siteInfoTree);

        } else if (propName.equals(PropertyChangeNames.MobileHealthReturned.getName())) {
            MobileHealthInfo hi = (MobileHealthInfo) evt.getNewValue();
            view.fillTrendHealthInfo(hi);
            //model.getTrendCompanies();

        } else if (propName.equals(PropertyChangeNames.MobileVersionReturned.getName())) {
            String version = (String) evt.getNewValue();
            view.fillTrendVersion(version);
            //model.getTrendCompanies();

        } else if (propName.equals(PropertyChangeNames.MobileCompaniesReturned.getName())) {
            MobileCompanyList companies = (MobileCompanyList) evt.getNewValue();
            view.fillTrendCompanyDropdown(companies);

        } else if (propName.equals(PropertyChangeNames.MobileCompanyOverviewReturned.getName())) {
            MobileCompanyOverview mobileCompanyOverview = (MobileCompanyOverview) evt.getNewValue();
            view.fillMobileCompanyOverviewPanel(mobileCompanyOverview);

        } else if (propName.equals(PropertyChangeNames.MobileSiteOverviewReturned.getName())) {
            MobileSiteInfo mobileSiteInfo = (MobileSiteInfo) evt.getNewValue();
            view.fillMobileSiteOverviewPanel(mobileSiteInfo);

        } else if (propName.equals(PropertyChangeNames.ForgotPasswordVerifyTokenPosted.getName())) {
            String msg = (String) evt.getNewValue();
            view.fillVerifyTokenResponse(msg);

        }

    }
}
