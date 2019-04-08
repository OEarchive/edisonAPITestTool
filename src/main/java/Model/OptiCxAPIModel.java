package Model;

import Model.DataModels.Alarms.AlarmHistoryEntry;
import Model.DataModels.Alarms.AlarmListEntry;
import Model.DataModels.Auth.AdminLoginCreds;
import Model.DataModels.Auth.GetE3OSTokenRequest;
import Model.DataModels.Auth.PostForgotPasswordRequest;
import Model.DataModels.Auth.PutForgotPasswordRequest;
import Model.DataModels.Auth.VerifyResetPasswordTokenRequest;
import Model.DataModels.Customers.Customer;
import Model.DataModels.Customers.Customers;
import Model.DataModels.Datapoints.DatapointHistoriesQueryParams;
import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.Datapoints.PostDatapointRequest;
import Model.DataModels.Datapoints.simulator.DGArgs;
import Model.DataModels.Graph.EnumGraphNodeTypes;
import Model.DataModels.Graph.GetMetaDataRequest;
import Model.DataModels.Live.PostLiveData.PostLiveDataRequest;
import Model.DataModels.Live.Subscriptions.CreateSubscriptionRequest;
import Model.DataModels.ReportVerification.ReportHistoryQueryParams;
import Model.DataModels.Sites.ActivationCodeResponse;
import Model.DataModels.Sites.CheckForUpdatesResponse;
import Model.DataModels.Sites.CreateSiteRequest;
import Model.DataModels.Sites.EnumPointsListDownloadType;
import Model.DataModels.Sites.GetSiteInfoQueryParams;
import Model.DataModels.Sites.Phone;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.SiteDPConfig.SiteDPConfigPoints;
import Model.DataModels.Sites.SiteQueryResponse;
import Model.DataModels.Sites.SiteTrend;
import Model.DataModels.Sites.SiteTrendAndKPIRequest;
import Model.DataModels.Sites.SiteTrendKPI;
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
import Model.DataModels.TeslaModels.TeslaHistoryResultPoint;
import Model.DataModels.TeslaModels.TeslaHistoryResults;
import Model.DataModels.TotalSavings.TotalSavings;
import Model.DataModels.TrendAPI.SiteInfo.EnumMobileTrendTypes;
import Model.DataModels.Users.CreateUserRequest;
import Model.DataModels.Users.UserInfo;
import Model.DataModels.Users.CurrentUserInfoResponse;
import Model.DataModels.Users.ModifyCurrentUserPasswordResponse;
import Model.DataModels.Users.ModifyCurrentUserRequest;
import Model.DataModels.Users.ModifyUsersPasswordRequest;
import Model.DataModels.Users.NotificationSetting;
import Model.DataModels.Users.NotificationSettingList;
import Model.DataModels.Users.RoleItem;
import Model.DataModels.Users.SetNoficationsResponse;
import Model.DataModels.Users.TestUsers.EnumTestUsers;
import Model.DataModels.Users.TestUsers.TestSitesAndUsers;
import Model.DataModels.Users.UserQueryFilter;
import Model.DataModels.Users.Users;
import Model.DataModels.Views.PageView;
import Model.DataModels.Wizard.CompilePostCommand;
import Model.DataModels.Wizard.NetworkSettingsResponse;
import Model.DataModels.Wizard.WizardActivationPostRequest;
import Model.DataModels.Wizard.WizardPoint;
import Model.DataModels.Wizard.WizardPostConfigFileInfo;
import Model.DataModels.Wizard.WizardPostConfigFilesRequest;
import Model.DataModels.Wizard.WizardStep;
import Model.DataModels.Wizard.WizardUser;
import Model.RestClient.APIHosts;
import Model.RestClient.RequestsResponses;
import Model.RestClient.APIHostsEnum;
import Model.RestClient.AlarmsModel;
import Model.RestClient.Clients.CustomersClient;
import Model.RestClient.Clients.DatapointsClient;
import Model.RestClient.Clients.LoginClient;
import Model.RestClient.Clients.RestClientCommon;
import Model.RestClient.Clients.SitesClient;
import Model.RestClient.Clients.TeslaRestClientCommon;
import Model.RestClient.Clients.UsersClient;
import Model.RestClient.DatapointsModel;
import Model.RestClient.GraphModel;
import Model.RestClient.LiveModel;
import Model.RestClient.OEResponse;
import Model.RestClient.LoginResponse;
import Model.RestClient.PGDAOIModel;
import Model.RestClient.ReportsModel;
import Model.RestClient.StationsModel;
import Model.RestClient.MobileAPIModel;
import Model.RestClient.TeslaAPIModel;
import Model.RestClient.WizardModel;
import View.Sites.EditSite.A_History.DPHistoryChart.StampsAndPoints;
import View.Sites.EditSite.A_History.DataGenerator.DGTableRow;
import View.TreeInfo.CustomTreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.SwingWorker;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptiCxAPIModel extends java.util.Observable {
    
    private RestClientCommon api;
    private TeslaRestClientCommon teslaRestClientCommon;
    private LoginClient loginClient;
    private CustomersClient customersClient;
    private UsersClient usersClient;
    private SitesClient sitesClient;
    private DatapointsClient datapointsClient;
    
    private StationsModel stationsModel;
    private GraphModel graphModel;
    private WizardModel wizardModel;
    private AlarmsModel alarmsModel;
    private LiveModel liveModel;
    private MobileAPIModel trendAPIModel;
    private DatapointsModel datapointsModel;
    private PGDAOIModel pgDaoModel;
    private ReportsModel reportsModel;
    private TeslaAPIModel teslaAPIModel;
    
    private RequestsResponses rrs;
    
    private LoginResponse loginResponse = null;
    
    private CurrentUserInfoResponse userMeInfoFromResponseObj;
    private CustomTreeNode currentUserInfoTreeRootNode;
    
    private List<UserInfo> users;
    private UserInfo selectedUserInfo;
    private List<Customer> customers;
    private Customer customerDetails;
    private DatapointsAndMetadataResponse getDataPointsResponse;
    private DatapointMetadata datapointsHistoryResponse;
    
    private String currentCustomerSid;
    private List<Site> siteList;
    private Site selectedSiteInfo;
    private ActivationCodeResponse activationCodeResponse;
    
    final private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    static Logger logger = LoggerFactory.getLogger(OptiCxAPIModel.class.getName());
    
    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
    
    public void initModel() throws IOException {
        rrs = new RequestsResponses();
        api = new RestClientCommon(rrs);
        teslaRestClientCommon = new TeslaRestClientCommon(rrs);
        
        loginClient = new LoginClient(api);
        customersClient = new CustomersClient(api);
        usersClient = new UsersClient(api);
        sitesClient = new SitesClient(api);
        datapointsClient = new DatapointsClient(api);
        
        this.stationsModel = new StationsModel(this, pcs);
        this.graphModel = new GraphModel(this, pcs);
        this.wizardModel = new WizardModel(this, pcs);
        this.alarmsModel = new AlarmsModel(this, pcs);
        this.liveModel = new LiveModel(this, pcs);
        this.trendAPIModel = new MobileAPIModel(this, pcs);
        this.datapointsModel = new DatapointsModel(this, pcs);
        this.pgDaoModel = new PGDAOIModel(this, pcs);
        this.reportsModel = new ReportsModel(this, pcs);
        this.teslaAPIModel = new TeslaAPIModel(this, pcs);
        
    }
    
    public RestClientCommon getRestClient() {
        return api;
    }
    
    public TeslaRestClientCommon getTeslaRestClient() {
        return teslaRestClientCommon;
    }
    
    public RequestsResponses getRRS() {
        return this.rrs;
    }
    
    public void clearRRS() {
        rrs.clear();
        pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
    }

    // === LOGIN ====================
    public boolean isLoggedIn() {
        return !(loginResponse == null
                || loginResponse.getAccessToken() == null
                || loginResponse.getAccessToken().length() <= 0);
    }
    
    public LoginResponse getLoginResponse() {
        return loginResponse;
    }
    
    public void login(final APIHostsEnum loc, final AdminLoginCreds user) {
        if (loc != null && user != null) {
            login(loc, user.getUsername(), user.getPassword());
        }
        
    }
    
    public void login(final APIHostsEnum loc, final String userName, final String password) {
        
        if (loc == null || userName == null) {
            return;
        }
        
        usersClient = new UsersClient(api);
        customersClient = new CustomersClient(api);
        
        APIHosts hosts = new APIHosts();
        final String serviceURL = hosts.getHostInfo(loc).getServiceEndpointURL();
        final String adminURL = hosts.getHostInfo(loc).getAdminEndpointURL();
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = loginClient.loginUser(serviceURL, userName, password);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        loginResponse = (LoginResponse) resp.responseObject;
                        
                        usersClient.setServiceURLAndToken(serviceURL, loginResponse.getAccessToken());
                        customersClient.setServiceURLAndToken(serviceURL, loginResponse.getAccessToken());
                        sitesClient.setServiceURLAndToken(serviceURL, loginResponse.getAccessToken());
                        datapointsClient.setServiceURLAndToken(serviceURL, loginResponse.getAccessToken());
                        
                        stationsModel.resetClient(serviceURL, loginResponse.getAccessToken(), api);
                        graphModel.resetClient(serviceURL, adminURL, loginResponse.getAccessToken(), api);
                        wizardModel.resetClient(serviceURL, loginResponse.getAccessToken(), api);
                        alarmsModel.resetClient(serviceURL, loginResponse.getAccessToken(), api);
                        liveModel.resetClient(serviceURL, loginResponse.getAccessToken(), api);
                        trendAPIModel.resetClient(serviceURL, loginResponse.getAccessToken(), api);
                        datapointsModel.resetClient(serviceURL, loginResponse.getAccessToken(), api);
                        pgDaoModel.resetClient(serviceURL, adminURL, password, api);
                        reportsModel.resetClient(serviceURL, loginResponse.getAccessToken(), api);
                        
                    } else {
                        loginResponse = new LoginResponse();
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.LoginResponse.getName(), null, loginResponse);
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }
    
    public void getOauthToken(final String username, final String password) {
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = loginClient.getOauthToken(username, password);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        LoginResponse msg = (LoginResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.TestTokenReturned.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void getOriginatedCallInEndpoint() {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = loginClient.getOriginatedCallInEndpoint();
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.E3OSCIEPReturned.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void getE3OSToken(final GetE3OSTokenRequest req) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = loginClient.getE3OSToken(req);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.E3OSTokenReturned.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void postForgotPasword(final PostForgotPasswordRequest req) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = loginClient.postForgotPassword(req);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.ForgotPasswordPosted.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void putForgotPasword(final PutForgotPasswordRequest req) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = loginClient.putForgotPassword(req);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.ForgotPasswordPutted.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }

    //public OEResponse postForgotPasswordVerifyToken(VerifyResetPasswordTokenRequest req) throws IOException {
    public void postForgotPasswordVerifyToken(final VerifyResetPasswordTokenRequest req) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = loginClient.postForgotPasswordVerifyToken(req);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.ForgotPasswordVerifyTokenPosted.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }

    //=== LIVE ==========
    public void getLiveData(String subscription_id) {
        liveModel.getLiveData(subscription_id);
    }
    
    public void postNewSubscription(CreateSubscriptionRequest req) {
        liveModel.postNewSubscription(req);
    }
    
    public void postLiveData(String token, String stationId, PostLiveDataRequest req) {
        liveModel.postLiveData(token, stationId, req);
    }
    
    public void fireResetHistoryChartEvent(StampsAndPoints sp) {
        pcs.firePropertyChange(PropertyChangeNames.UpdateHistoryChart.getName(), null, sp);
    }

    // ===  GRAPH =====================
    public void graphGetCustomerList() {
        graphModel.getGraphElements();
    }
    
    public void deleteGraphElement(String sid) {
        graphModel.deleteGraphElement(sid);
    }
    
    public void getElementMetaData(String sid, GetMetaDataRequest mdr) {
        graphModel.getElementMetadata(sid, mdr);
    }
    
    public void getSiteDetailsFromGraph(String sid) {
        graphModel.getSiteDetailsFromGraph(sid);
    }

    /*
    public void getSiteAttributesFromGraph(String sid) {
        graphModel.getSiteAttributesFromGraph(sid);
    }
     */
    public void runQuery(String queryString) {
        pgDaoModel.runQuery(queryString);
    }
    
    public void getChildren(String sid) {
        graphModel.getGraphElementChildren(sid);
    }
    
    public void upsertElement(String sid, EnumGraphNodeTypes nodeType, String payload) {
        graphModel.putGraphElement(sid, nodeType, payload);
    }
    
    public void getElementSchema(String sid, EnumGraphNodeTypes nodeType) {
        graphModel.getGraphElementSchema(sid, nodeType);
    }
    
    public void getRulesInfo(String siteSid) {
        graphModel.getRulesInfo(siteSid);
    }

    // === STATIONS  ======================
    public void getActivationAvaiablity() {
        stationsModel.getActivationAvaiablity();
    }
    
    public void postActivate(final StationActivateRequest req) {
        stationsModel.postActivate(req);
    }
    
    public void getStationBogFile(String stationId) {
        stationsModel.getStationBogFile(stationId);
    }
    
    public void getConfigurationProfile(String stationId) {
        stationsModel.getConfigurationProfile(stationId);
    }
    
    public void getPointsConfiguration(String stationId) {
        stationsModel.getPointsConfiguration(stationId);
    }
    
    public void getDefaultParameters(String stationId) {
        stationsModel.getDefaultParameters(stationId);
    }
    
    public void getPortalParameters(String stationId) {
        stationsModel.getPortalParameters(stationId);
    }
    
    public void validateConfiguration(StationValidateQueryParams params) {
        stationsModel.validateConfiguration(params);
    }
    
    public void getConfigurationStatus(String stationId) {
        stationsModel.getConfigurationStatus(stationId);
    }
    
    public void pushConfigurationStatus(String stationId, WizardStationStatus status) {
        stationsModel.pushConfigurationStatus(stationId, status);
    }
    
    public void pushAuditHistory(StationAuditHistory auditHistory) {
        stationsModel.pushAuditHistory(auditHistory);
    }
    
    public void pushStationLog(StationLogHistory stationLogHistory) {
        stationsModel.pushStationLog(stationLogHistory);
    }
    
    public void postDatapointHistory(HistoryPushObject history) {
        stationsModel.postDatapointHistory(history);
    }
    
    public void repostDatapointHistory(String timeStamp, String stationId, List<DatapointHistoriesResponse> oldhistory) {
        stationsModel.repostDatapointHistory(timeStamp, stationId, oldhistory);
    }
    
    public void pushAlarmChanges(StationAlarmPushObject alarms) {
        stationsModel.pushAlarmChanges(alarms);
    }
    
    public void pushHeartbeat(String stationId, String token, StationsHeartbeat heartBeat) {
        stationsModel.pushHeartbeat(stationId, token, heartBeat);
    }
    
    public void pushHistoryForPeriod(EnumGraphNodeTypes scope, DGArgs args, Map<String, DGTableRow> tableRowsMap) {
        stationsModel.pushHistoryForPeriod(scope, args, tableRowsMap);
    }

    // ===  ALARMS  =========================
    /*
    public void getAlarmList(String sid, AlarmListRequest alarmList) {
        alarmsModel.getAlarmList(sid, alarmList);
    }

    public void postNewAlarm(String sid, CreateAlarmRequest req) {
        alarmsModel.postNewAlarm(sid, req);
    }

    public void deleteAlarm(String sid, String name) {
        alarmsModel.deleteAlarm(sid, name);
    }

    public void associateAlarm(String sid, AssociateAlarmReq req) {
        alarmsModel.associateAlarm(sid, req);
    }

    public void getAlarmDetails(String sid, String name, AlarmListRequest req) {
        alarmsModel.getAlarmDetails(sid, name, req);
    }

    public void postTriggerOrClearAlarm(String sid, String alarmSid, String name, TriggerOrClearAlarmRequest req) {
        alarmsModel.postTriggerOrClearAlarm(sid, alarmSid, name, req);
    }

    public void updateAlarmMetadata(String sid, String name, UpdateAlarmRequest req) {
        alarmsModel.updateAlarmMetadata(sid, name, req);
    }

    public void getAlarmHistory(String sid, AlarmsHistoryQueryParams queryParams) {
        alarmsModel.getAlarmHistory(sid, queryParams);
    }
    
    public void getSpecificAlarmHistory(String sid, String name) {
        alarmsModel.getSpecificAlarmHistory(sid, name);
    }

    public void getAlarmNotes(String sid, String name) {
        alarmsModel.getAlarmNotes(sid, name);
    }

    public void postAlarmNote(String sid, String name, String note) {
        alarmsModel.postAlarmNote(sid, name, note);
    }

    public void deleteAlarmNote(String sid, String name, String note_id) {
        alarmsModel.deleteAlarmNote(sid, name, note_id);
    }
     */
    // === WIZARD ===============
    public void getCompileStatus() {
        wizardModel.getCompileStatus();
    }
    
    public void postCompileCommand(CompilePostCommand cmd) {
        wizardModel.postCompileCommand(cmd);
    }
    
    public void getNetworkSettings() {
        wizardModel.getNetworkSettings();
    }
    
    public void putNetworkSettings(NetworkSettingsResponse nwSettings) {
        wizardModel.putNetworkSettings(nwSettings);
    }
    
    public void getWizardPlantProfile() {
        wizardModel.getPlantProfile();
    }
    
    public void getWizardPointsList() {
        wizardModel.getPointsList();
    }
    
    public void getControlPoint(String pointName) {
        wizardModel.getControlPoint(pointName);
    }
    
    public void upsertControlPoint(WizardPoint point) {
        wizardModel.upsertControlPoint(point);
    }
    
    public void getWizardUsersMe() {
        wizardModel.getWizardUsersMe();
    }
    
    public void putWizardUsersMe(WizardUser user) {
        wizardModel.putWizardUsersMe(user);
    }
    
    public void getWizardSteps() {
        wizardModel.getWizardSteps();
    }
    
    public void getWizardStep(String stepId) {
        wizardModel.getWizardStep(stepId);
    }
    
    public void putWizardStep(String stepId, WizardStep wizardStep) {
        wizardModel.putWizardStep(stepId, wizardStep);
    }
    
    public void getCloudActivationStatus() {
        wizardModel.getCloudActivationStatus();
    }
    
    public void postCloudActivation(WizardActivationPostRequest activationRequest) {
        wizardModel.postCloudActivation(activationRequest);
    }
    
    public void getCloudAuthentication() {
        wizardModel.getCloudAuthentication();
    }
    
    public void postCloudAuthentication(String whatever) {
        wizardModel.postCloudAuthentication(whatever);
    }
    
    public void getCloudConnection() {
        wizardModel.getCloudConnection();
    }
    
    public void postCloudConnection(String whatever) {
        wizardModel.postCloudConnection(whatever);
    }
    
    public void getOptimizationConfigurationFile() {
        wizardModel.getOptimizationConfigurationFile();
    }
    
    public void postOptimizationConfigurationFile(WizardPostConfigFilesRequest configFiles) {
        wizardModel.postOptimizationConfigurationFile(configFiles);
    }
    
    public void getOptimizationProgram() {
        wizardModel.getOptimizationProgram();
    }
    
    public void postOptimizationProgram(WizardPostConfigFileInfo configFile) {
        wizardModel.postOptimizationProgram(configFile);
    }
    
    public void getOptimizationStatus() {
        wizardModel.getOptimizationStatus();
    }

    // === USERS =======================
    public List<UserInfo> getUserList() {
        return users;
    }
    
    public UserInfo getUserFromUserList(String userID) {
        for (UserInfo u : getUserList()) {
            if (u.getUserID().compareTo(userID) == 0) {
                return u;
            }
        }
        return null;
    }
    
    public void getUsersWithFilter(final String queryStr) {
        
        if (!this.isLoggedIn()) {
            users = new ArrayList<UserInfo>();
            pcs.firePropertyChange(PropertyChangeNames.UsersListChanged.getName(), null, users);
            return;
        }
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.getUsers(queryStr);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        Users userListClass = (Users) resp.responseObject;
                        users = userListClass.getUsers();
                        pcs.firePropertyChange(PropertyChangeNames.UsersListChanged.getName(), null, users);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }
    
    public void createUser(final CreateUserRequest cur) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.postUser(cur);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        UserInfo msg = (UserInfo) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.UserCreated.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void createSiteUsersAndOrSetRoles(final EnumTestSites templateType, final Site site) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                
                List<EnumTestUsers> listOfEnumUsersForSite = new TestSitesAndUsers().getUsers(templateType);
                
                OEResponse resp = usersClient.getUsers(new UserQueryFilter().getQueryString());
                if (resp.responseCode != 200) {
                    return resp;
                }
                
                List<UserInfo> currentUsers = ((Users) resp.responseObject).getUsers();
                
                List<EnumTestUsers> usersToBeAdded = new ArrayList<>();
                
                for (EnumTestUsers testUser : listOfEnumUsersForSite) {
                    UserInfo foundUser = null;
                    
                    for (UserInfo existingUser : currentUsers) {
                        if (testUser.getUsername().compareToIgnoreCase(existingUser.getUserName()) == 0) {
                            foundUser = existingUser;
                        }
                    }
                    
                    if (foundUser != null) {
                        //found the user. add the role if necessary
                        List<RoleItem> roleList = foundUser.getRoles();
                        boolean foundRole = false;
                        for (RoleItem ri : roleList) {
                            if (ri.getSid().compareTo(site.getSid()) == 0
                                    && ri.getRoleName().compareTo(testUser.getRolename()) == 0) {
                                foundRole = true;
                            }
                        }
                        if (!foundRole) {
                            RoleItem newRoleItem = new RoleItem();
                            newRoleItem.setSid(site.getSid());
                            newRoleItem.setRoleName(testUser.getRolename());
                            roleList.add(newRoleItem);
                            resp = usersClient.putUsersRoles(foundUser.getUserID(), roleList);
                            if (resp.responseCode != 200) {
                                return resp;
                            }
                        }
                    } else {
                        //need to add the user
                        usersToBeAdded.add(testUser);
                    }
                }
                
                for (EnumTestUsers userToBeAdded : usersToBeAdded) {
                    CreateUserRequest cur = new CreateUserRequest();
                    cur.setUsername(userToBeAdded.getUsername());
                    cur.setFirstname("Tester First Name");
                    cur.setLastname("Tester Last Name");
                    cur.setEmail(userToBeAdded.getUsername());
                    cur.setNoInvite(true);
                    cur.setInvitationMessage("Welcome to OEdge!!!");
                    
                    Phone p = new Phone();
                    p.setPhone("111-222-3333");
                    p.setPhoneType("watermellon");
                    
                    List<Phone> phones = new ArrayList<>();
                    phones.add(p);
                    cur.setPhones(phones);
                    
                    RoleItem ri = new RoleItem();
                    ri.setSid(site.getSid());
                    ri.setRoleName(userToBeAdded.getRolename());
                    
                    List<RoleItem> roleList = new ArrayList<>();
                    roleList.add(ri);
                    cur.setRoles(roleList);
                    
                    resp = usersClient.postUser(cur);
                    
                    if (resp.responseCode != 200) {
                        return resp;
                    }
                    
                }
                
                OEResponse results = new OEResponse();
                results.responseCode = 200;
                results.responseObject = "OK";
                
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.UsersAndRolesForSiteAdded.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void deleteUser(final String userID) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.deleteUser(userID);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String message = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.UserDeleted.getName(), null, message);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public UserInfo getUserSetFromResponseObj() {
        return selectedUserInfo;
    }
    
    public void getUser(final String userID) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.getUser(userID);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        selectedUserInfo = (UserInfo) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.SpecificUserInfoRetrieved.getName(), null, selectedUserInfo);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void putUserInfo(final UserInfo user) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.putUser(user.getUserID(), user.getUpdatePayload());
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        UserInfo msg = (UserInfo) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.UserUpdated.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void overwriteAllRoleMappings(final String userSid, final List<RoleItem> roles) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.putUsersRoles(userSid, roles);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.UserRolesChanged.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void changeRolesForSid(final String userSid, final String roleSid, final List<RoleItem> roles) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.changeRolesForSid(userSid, roleSid, roles);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.UsersRolesForSidChanged.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void deleteUserRole(final String userID, final String roleSid) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.deleteUserRoles(userID, roleSid);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String message = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.UserRoleDeleted.getName(), null, message);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public CurrentUserInfoResponse getUserMeFromResponseObj() {
        return userMeInfoFromResponseObj;
    }
    
    public CustomTreeNode getCurrentUserInfoTreeRootNode() {
        return this.currentUserInfoTreeRootNode;
    }
    
    public void getUsersMe() {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.getUsersMe();
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        
                        ObjectMapper mapper = new ObjectMapper();
                        userMeInfoFromResponseObj = mapper.readValue((String) resp.responseObject, CurrentUserInfoResponse.class);
                        
                        JsonElement root = new JsonParser().parse((String) resp.responseObject);
                        currentUserInfoTreeRootNode = null;
                        try {
                            currentUserInfoTreeRootNode = new CustomTreeNode(null, -1, root);
                        } catch (Exception ex) {
                            java.util.logging.Logger.getLogger(OptiCxAPIModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        pcs.firePropertyChange(PropertyChangeNames.CurrentUserInfoRetrieved.getName(), null, userMeInfoFromResponseObj);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void putUsersMe(final ModifyCurrentUserRequest req) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.putUsersMe(req);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        CurrentUserInfoResponse msg = (CurrentUserInfoResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.CurrentUserUpdated.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void getNotifications(final String userId) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.getNotifications(userId);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        
                        ObjectMapper mapper = new ObjectMapper();
                        List<NotificationSetting> msg = (List<NotificationSetting>) resp.responseObject;
                        
                        pcs.firePropertyChange(PropertyChangeNames.UserNotificatonsRetrieved.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void setNotifications(final String userId, final NotificationSettingList notifications) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.setNotifications(userId, notifications);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        
                        ObjectMapper mapper = new ObjectMapper();
                        SetNoficationsResponse msg = mapper.readValue((String) resp.responseObject, SetNoficationsResponse.class);
                        pcs.firePropertyChange(PropertyChangeNames.UserNotificatonsSet.getName(), null, msg);
                        
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void postPassword(final ModifyUsersPasswordRequest req) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = usersClient.postPassword(req);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        
                        ObjectMapper mapper = new ObjectMapper();
                        ModifyCurrentUserPasswordResponse msg = mapper.readValue((String) resp.responseObject, ModifyCurrentUserPasswordResponse.class);
                        pcs.firePropertyChange(PropertyChangeNames.UserPasswordSet.getName(), null, msg);
                        
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }

    // ===== TOTAL SAVINGS =================
    public void getTotalSavings() {
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = customersClient.getTotalSavings();
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        TotalSavings msg = (TotalSavings) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.TotalSavingsReturned.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }

    // ==== CUSTOMERS ====================
    public List<Customer> getCustomers() {
        return customers;
    }
    
    public Customer getCustomer(String sid) {
        for (Customer c : getCustomers()) {
            if (c.getSid().compareTo(sid) == 0) {
                return c;
            }
        }
        return null;
    }
    
    public void customersQuery() {
        
        if (!this.isLoggedIn()) {
            customers = new ArrayList<Customer>();
            pcs.firePropertyChange(PropertyChangeNames.CustomerListChanged.getName(), null, users);
            return;
        }
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = customersClient.getCustomers();
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        customers = ((Customers) resp.responseObject).getCustomers();
                        pcs.firePropertyChange(PropertyChangeNames.CustomerListChanged.getName(), null, customers);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }
    
    public void queryCustomerDetails(final String sid) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = customersClient.getCustomerDetails(sid);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        customerDetails = ((Customer) resp.responseObject);
                        pcs.firePropertyChange(PropertyChangeNames.CustomerDetailsChanged.getName(), null, customerDetails);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }
    
    public void addCustomer(final Customer customer) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = customersClient.addCustomer(customer.getUpdatePayload());
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        Customer newCustomer = (Customer) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.CustomerCreated.getName(), null, newCustomer);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void updateCustomer(final Customer customer) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = customersClient.updateCustomer(customer.getSid(), customer.getUpdatePayload());
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String message = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.CustomerUpdated.getName(), null, message);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void setCurrentCustomerSid(String customerSid) {
        this.currentCustomerSid = customerSid;
    }

    // === SITES ========================
    public List<Site> getSiteList() {
        return this.siteList;
    }
    
    public void runSiteListQuery() {
        runSiteListQuery(currentCustomerSid);
    }
    
    public void runSiteListQuery(final String customerSid) {
        
        if (!this.isLoggedIn()) {
            siteList = new ArrayList<Site>();
            pcs.firePropertyChange(PropertyChangeNames.SiteListChanged.getName(), null, siteList);
            return;
        }
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.getSites(customerSid);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        SiteQueryResponse sqr = (SiteQueryResponse) resp.responseObject;
                        siteList = sqr.getSites();
                        pcs.firePropertyChange(PropertyChangeNames.SiteListChanged.getName(), null, siteList);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }
    
    public Site getSelectedSiteInfo() {
        return selectedSiteInfo;
    }
    
    public void runSiteInfoQuery(final String siteSid, final GetSiteInfoQueryParams params) {
        
        if (!this.isLoggedIn()) {
            selectedSiteInfo = new Site();
            pcs.firePropertyChange(PropertyChangeNames.SelectedSiteInfoChanged.getName(), null, selectedSiteInfo);
            return;
        }
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.getSiteInfo(siteSid, params);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    selectedSiteInfo = (Site) resp.responseObject;
                    
                    if (resp.responseCode != 200) {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    
                    pcs.firePropertyChange(PropertyChangeNames.SelectedSiteInfoChanged.getName(), null, resp);
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }
    
    public void createSite(final CreateSiteRequest csr) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.createSite(csr);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        Site createSiteResponse = (Site) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.SiteCreated.getName(), null, createSiteResponse);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void updateSite(final String siteSid, final Boolean forceUpdate, final UpdateSiteRequest usr) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.updateSite(siteSid, forceUpdate, usr);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.SiteUpdated.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void getPointsConfig(final String sid, final EnumPointsListDownloadType downLoadType) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.getPointsConfig(sid, downLoadType);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        SiteDPConfigPoints msg = (SiteDPConfigPoints) resp.responseObject;
                        switch (downLoadType) {
                            case JSON:
                                pcs.firePropertyChange(PropertyChangeNames.SiteDPConfigPointsReturned.getName(), null, msg);
                                break;
                            default:
                                pcs.firePropertyChange(PropertyChangeNames.SitePointsListAsXLSReturned.getName(), null, msg);
                        }
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void getNewActivationCode(final String sid) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.getNewActivationCode(sid);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        activationCodeResponse = (ActivationCodeResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.SiteNewActivationCodeReturned.getName(), null, activationCodeResponse);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void callCheckForUpdates(final String sid) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.checkForUpdates(sid);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        CheckForUpdatesResponse cur = (CheckForUpdatesResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.SiteCheckForUpdates.getName(), null, cur);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void querySiteTrend(final String sid, final SiteTrendAndKPIRequest req) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.getSiteTrend(sid, req);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        SiteTrend cur = (SiteTrend) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.SiteTrendReturned.getName(), null, cur);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }

    //getSiteKPI(String sid, String kpi, SiteTrendAndKPIRequest req)
    public void getSiteKPI(final String sid, final String kpi, final SiteTrendAndKPIRequest req) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.getSiteKPI(sid, kpi, req);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        SiteTrendKPI respObject = (SiteTrendKPI) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.SiteTrendKPIReturned.getName(), null, respObject);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void getUIMetaData(final String productType, final String sid, final String view) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.getUIMetaData(productType, sid, view);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        PageView view = (PageView) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.UIMetaDataReturned.getName(), null, view);
                    } else {
                        resp.responseObject = "Could not get ui meta data";
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void getAlarmHistory(final String siteSid, final String startDateString, final String endDateString) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.getAlarmHistory(siteSid, startDateString, endDateString);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        List<AlarmHistoryEntry> listOfAlarmHistories = (List<AlarmHistoryEntry>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.SiteAlarmHistoryReturned.getName(), null, listOfAlarmHistories);
                    } else {
                        resp.responseObject = "Could not get alarm history";
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    public void getAlarms(final String siteSid) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = sitesClient.getAlarms(siteSid);
                return results;
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        List<AlarmListEntry> listOfAlarmHistories = (List<AlarmListEntry>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmListReturned.getName(), null, listOfAlarmHistories);
                    } else {
                        resp.responseObject = "Could not get alarm history";
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }

    // === DATAPOINTS =====================
    public void getDatapoints(String sid, String listOfPoints) {
        datapointsModel.getDatapoints(sid, listOfPoints);
    }
    
    public void postDatapoints(PostDatapointRequest req) {
        datapointsModel.postDatapoints(req);
    }
    
    public void getDatapointsUnion(final Map<String, List<String>> sidsAndPoints) {
        datapointsModel.getDatapointsUnion(sidsAndPoints);
    }
    
    public void getDatapointsUnionForReports(final Map<String, List<String>> sidsAndPoints) {
        datapointsModel.getDatapointsUnionForReports(sidsAndPoints);
    }
    
    public void getDatapointHistories(final DatapointHistoriesQueryParams params) {
        datapointsModel.getDatapointHistories(params);
    }
    
    public void getComplexDatapointHistories(List<DatapointHistoriesQueryParams> listOfQueryParams) {
        datapointsModel.getComplexDatapointHistories(listOfQueryParams);
    }
    
    public void getHistoryForReportVerification(ReportHistoryQueryParams reportHistoryParams) {
        datapointsModel.getHistoryForReportVerification(reportHistoryParams);
    }

    /*
    public void getHistory(String sid, HistoryQueryParams params) {
        datapointsModel.getHistory(sid, params);
    }

    public void putHistory(String sid, String token, PushDatapointsRequest req) {
        datapointsModel.putHistory(sid, token, req);
    }

    public void postQuery(String sid) {
        datapointsModel.postQuery(sid);
    }

    public void deleteQuery(String sid, String name) {
        datapointsModel.deleteQuery(sid, name);
    }

    public void getDatapointQueryResults(String sid, String name) {
        datapointsModel.getDatapointQueryResults(sid, name);
    }

    public void deleteDatapointAssociation(String sid, String name) {
        datapointsModel.deleteDatapointAssociation(sid, name);
    }

    public void getDatapointMetadata(String sid, String name) {
        datapointsModel.getDatapointMetadata(sid, name);
    }

    public void getDatapointMetadataMultipleSids(Map<String, List<String>> sidsAndPoints) {
        datapointsModel.getDatapointMetadataMultipleSids(sidsAndPoints);
    }

    public void updateDatapointMetadata(String sid, String name, UpdateMetaDataRequest req) {
        datapointsModel.updateDatapointMetadata(sid, name, req);
    }

    public void putHistoryFromXLS(
            String sid,
            List<String> timeStamps,
            Map<String, List<Object>> pointsAndValues,
            Map<String, DatapointMetadata> nameToMetadataMap
    ) {
        datapointsModel.putHistoryFromXLS(
                sid,
                timeStamps,
                pointsAndValues,
                nameToMetadataMap);
    }
     */
// ==== TRENDAPI =======
    public void getMobileHealth() {
        trendAPIModel.getMobileHealth();
    }
    
    public void getMobileVersion() {
        trendAPIModel.getMobileVersion();
    }
    
    public void getMobileCompanies() {
        trendAPIModel.getMobileCompanies();
    }
    
    public void getMobileCompanyOverview(String uuid) {
        trendAPIModel.getMobileCompanyOverview(uuid);
    }
    
    public void getMobileSiteOverview(String uuid, String timeFrame) {
        trendAPIModel.getMobileSiteOverview(uuid, timeFrame);
    }
    
    public void getMobileTrend(String uuid, EnumMobileTrendTypes trendType, String timeFrame) {
        trendAPIModel.getMobileTrend(uuid, trendType, timeFrame);
    }

    //REPORTS
    public void getReportsList(String sid, boolean onlyApproved) {
        reportsModel.getReportsList(sid, onlyApproved);
    }
    
    public void getReportSchema(String reportId) {
        reportsModel.getReportSchema(reportId);
    }

    // Tesla
    public void teslaLogin(final EnumTeslaBaseURLs baseUrl, final EnumTeslaUsers user) {
        teslaAPIModel.teslaLogin(baseUrl, user);
    }
    
    public void setEdisonClient() {
        teslaAPIModel.setEdisonClient(this.datapointsClient);
    }
    
    public void getTeslaStations() {
        teslaAPIModel.getTeslaStations();
    }
    
    public void getTeslaStationInfo(String stationID) {
        teslaAPIModel.getTeslaStationInfo(stationID);
    }
    
    public void getTeslaStationDatapoints(String stationID) {
        teslaAPIModel.getTeslaStationDatapoints(stationID);
    }
    
    public void pullFromEdisonPushToTesla(String querySid, DateTime pushStartTime, DateTime pushEndTime, List<MappingTableRow> mappedRows, int maxHoursPerPush, int maxPointsPerPush) {
        teslaAPIModel.pullFromEdisonPushToTesla(querySid, pushStartTime, pushEndTime, mappedRows, maxHoursPerPush, maxPointsPerPush);
    }
    
    public void postCustomer(TeslaPostCustomer postCustomer) {
        teslaAPIModel.postCustomer(postCustomer);
    }
    
    public void postSite(String customerId, TeslaPostSite postSite) {
        teslaAPIModel.postSite(customerId, postSite);
    }
    
    public void postStation(String siteId, TeslaPostStation postStation) {
        teslaAPIModel.postStation(siteId, postStation);
    }
    
    public void postEquipmentList(final String stationId, final List<TeslaGenEquipment> equipList) {
        teslaAPIModel.postEquipmentList(stationId, equipList);
    }
    
    public void getTeslaHistory(TeslaHistoryRequest historyRequest) {
        teslaAPIModel.getTeslaHistory(historyRequest);
    }
    
    public void postSparsePoints(TeslaDataPointUpsertRequest upsertRequest) {
        teslaAPIModel.postSparsePoints(upsertRequest);
    }
    
    //Tesla and Edison Query
    public void getTeslaAndEdisonHistory(final List<DatapointHistoriesQueryParams> listOfParams, final TeslaHistoryRequest historyRequest ) {
        
        SwingWorker worker = new SwingWorker< OEResponse, Void>() {
            
            @Override
            public OEResponse doInBackground() throws IOException {
                
                
                //edison 
                List<DatapointHistoriesResponse> history = new ArrayList<>();
                for (DatapointHistoriesQueryParams params : listOfParams) {

                    OEResponse queryResult = datapointsClient.getDatapointHistories(params);
                    if (queryResult.responseCode == 200) {
                        List<DatapointHistoriesResponse> datapointHistoriesResponse = (List<DatapointHistoriesResponse>) queryResult.responseObject;
                        history.addAll(datapointHistoriesResponse);
                    } else {
                        Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                        logger.error(this.getClass().getName(), "History Query failed...");
                        //return queryResult;
                    }
                }

                // tesla
                OEResponse results = teslaAPIModel.getTeslaStationClient().getTeslaHistory(historyRequest);
                
                if (results.responseCode != 200) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), "Tesla History Query failed...");
                    return results;
                }
                    
                
                TeslaHistoryResults historyResults = new TeslaHistoryResults((List<TeslaHistoryResultPoint>) results.responseObject);
                ComboHistories comboHistories = new ComboHistories(history, historyResults);
                
                results = new OEResponse();

                results.responseCode = 200;
                results.responseObject = comboHistories;

                return results;
                
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    
                    if (resp.responseCode == 200) {
                        ComboHistories comboHistory = (ComboHistories) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.TeslaEdisonHistoryReturned.getName(), null, comboHistory);
                    } else {
                        resp.responseObject = "Could not get tesla and edison combined histories";
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, getRRS());
                    
                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
            
        };
        
        worker.execute();
    }
    
    
}
