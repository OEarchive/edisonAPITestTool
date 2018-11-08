package Model;

public enum PropertyChangeNames {

    UpdateHistoryChart("UpdateHistoryChart"),
    ErrorResponse("ErrorResponse"),
    LoginResponse("LoginResponse"),
    CurrentUserInfoRetrieved("CurrentUserInfoRetrieved"),
    CurrentUserUpdated("CurrentUserUpdated"),
    //
    TestTokenReturned("TestTokenReturned"),
    E3OSCIEPReturned("E3OSCIEPReturned"),
    E3OSTokenReturned("E3OSTokenReturned"),
    ForgotPasswordPosted("ForgotPasswordPosted"),
    ForgotPasswordPutted("ForgotPasswordPutted"),
    ForgotPasswordVerifyTokenPosted("ForgotPasswordVerifyTokenPosted"),
    //
    UsersListChanged("UsersListChanged"),
    UserCreated("UserCreated"),
    UsersAndRolesForSiteAdded("UsersAndRolesForSiteAdded"),
    UserUpdated("UserUpdated"),
    UserDeleted("UserDeleted"),
    SpecificUserInfoRetrieved("UserInfoRetrieved"),
    UserRolesChanged("UserRolesChanged"),
    UsersRolesForSidChanged("UsersRolesForSidChanged"),
    UserRoleDeleted("UserRoleDeleted"),
    UserNotificatonsRetrieved("UserNotificatonsRetrieved"),
    UserNotificatonsSet("UserNotificatonsSet"),
    UserPasswordSet("UserPasswordSet"),
    //
    CustomerListChanged("CustomerListChanged"),
    CustomerUpdated("CustomerUpdated"),
    CustomerCreated("CustomerCreated"),
    CustomerDetailsChanged("CustomerDetailsChanged"),
    RequestResponseChanged("RequestResponseChanged"),
    TotalSavingsReturned("TotalSavingsReturned"),
    //
    SiteListChanged("SiteListChanged"),
    SelectedSiteInfoChanged("SelectedSiteInfoChanged"),
    SiteCreated("SiteCreated"),
    SiteUpdated("SiteUpdated"),
    SiteDPConfigPointsReturned("SitePointsListReturned"),
    SitePointsListAsXLSReturned("SitePointsListAsXLSReturned"),
    SiteNewActivationCodeReturned("SiteNewActivationCodeReturned"),
    SiteCheckForUpdates("SiteCheckForUpdates"),
    SiteTrendReturned("SiteTrendReturned"),
    SiteTrendKPIReturned("SiteTrendKPIReturned"),
    UIMetaDataReturned("UIMetaDataReturned"),
    SiteAlarmHistoryReturned("SiteAlarmHistoryReturned"),
    //
    LiveDataReturned("LiveDataReturned"),
    SubscriptionCreated("SubscriptionCreated"),
    LiveValuesPushed("SubscriptionValuesPushed"),
    //
    GraphReturned("GraphReturned"),
    GraphElementDeleted("GraphElementDeleted"),
    GraphElementMetadataReturned("GraphElementMetadataReturned"),
    GraphSiteDetails("GraphSiteDetails"),
    GraphSiteAttributesReturned("GraphSiteAttributesReturned"),
    GraphChildrenReturned("GraphChildrenReturned"),
    GraphElementUpserted("GraphElementUpserted"),
    GraphElementSchemaReturned("GraphElementSchemaReturned"),
    GraphRulesInfoReturned("GraphRulesInfoReturned"),
    PGQueryResulsReturned("PGQueryResulsReturned"),
    //
    StationCheckActivateAvaiability("StationCheckActivateAvaiability"),
    StationActivatated("StationActivatated"),
    StationBogfileDownloaded("StationBogfileDownloaded"),
    StationConfigurationFileDownloaded("StationConfigurationFileDownloaded"),
    StationPointsConfigurationDownloaded("StationPointsConfigurationDownloaded"),
    StationDefaultParametersDownloaded("StationDefaultParametersDownloaded"),
    StationPortalParametersDownloaded("StationPortalParametersDownloaded"),
    StationValidateConfiguration("StationValidateConfiguration"),
    StationConfigurationStatusReturned("StationConfigurationStatusReturned"),
    StationConfigurationStatusSet("StationConfigurationStatusSet"),
    StationAuditHistorySubmitted("StationAuditHistorySubmitted"),
    StationLogHistorySubmitted("StationLogHistorySubmitted"),
    StationDatapointHistoryPushed("StationDatapointHistoryPushed"),
    StationDatapointHistoryOneHourPushed("StationDatapointHistoryOneHourPushed"),
    StationHistoryForPeriodPushed("StationHistoryForPeriodPushed"),
    StationAlarmChangesPushed("StationAlarmsPushed"),
    StationHeartbeatPushed("StationHeartbeatPushed"),
    StationRepushStarted("StationRepushStarted"),
    StationRepushComplete("StationRepushComplete"),
    //
    WizardCompileStatusResturned("WizardCompileStatusResturned"),
    WizardDidPostCompile("WizardDidPostCompile"),
    WizardNetworSettingsReturned("WizardNetworSettingsReturned"),
    WizardNetworkSettingsAppied("WizardNetworkSettingsAppied"),
    WizardPlantProfiledReturned("WizardPlantProfiledReturned"),
    WizardPointsListReturned("WizardPointsListReturned"),
    WizardPointReturned("WizardPointReturned"),
    WizardPointUpdated("WizardPointUpdated"),
    WizardUsersMeReturned("WizardUsersMeReturned"),
    WizardUsersMeUpdated("WizardUsersMeUpdated"),
    WizardListsOfStepsReturned("WizardListsOfStepsReturned"),
    WizardStepReturned("WizardStepReturned"),
    WizardStepUpdated("WizardStepUpdated"),
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
    //
    AlarmListReturned("AlarmListReturned"),
    AlarmCreated("AlarmCreated"),
    AlarmDeleted("AlarmDeleted"),
    AlarmAssociated("AlarmAssociated"),
    AlarmDetailsReturned("AlarmDetailsReturned"),
    AlarmTiggeredOrCleared("AlarmCleared"),
    AlarmUpdated("AlarmUpdated"),
    AlarmHistoryReturned("AlarmHistoryReturned"),
    AlarmSpecificHistoryReturned("AlarmSpecificHistoryReturned"),
    AlarmNotesReturned("AlarmNotesReturned"),
    AlarmNoteCreated("AlarmNoteCreated"),
    AlarmNoteDeleted("AlarmNoteDeleted"),
    //
    DatapointsListReturned("DatapointsListReturned"),
    DatapointUnionReturned("DatapointUnionReturned"),
    DatapointUnionForReportsReturned("DatapointUnionForReportsReturned"),
    DatapointsPosted("DatapointsPosted"),
    DatapointsHistoryReturned("DatapointsHistoryReturned"),
    ReportVerificationHistoryReturned("ReportVerificationHistoryReturned"),
    DatapointsComplexHistoryReturned("DatapointsComplexHistoryReturned"),
    DatapointHistoriesResponseReturned("DatapointHistoriesResponseReturned"),
    DatapointsHistoryPushed("DatapointsHistoryPushed"),
    DatapointsCalcRowPushed("DatapointsCalcRowPushed"),
    DatapointsCalcValuesPushed("DatapointsCalcValuesPushed"),
    DatapointsQueryPosted("DatapointsQueryPosted"),
    DatapointsQueryDeleted("DatapointsQueryDeleted"),
    DatapointsNamedQueryResultsReturned("DatapointsNamedQueryResultsReturned"),
    DatapointAssociationDeleted("DatapointAssociationDeleted"),
    DatapointMetadataReturned("DatapointMetadataReturned"),
    DatapointMetadataMultipleSidsReturned("DatapointMetadataMultipleSidsReturned"),
    DatapointMetadataUpdated("DatapointMetadataUpdated"),
    //
    ReportListReturned("ReportListReturned"),
    ReportSchemaReturned("ReportSchemaReturned"),
    ReportCreated("ReportCreated"),
    ReportReturned("ReportReturned"),
    ReportUpdated("ReportUpdated"),
    ReportResultsReturned("ReportResultsReturned"),
    ReportResultsPosted("ReportResultsPosted"),
    ReportPersistedResultReturned("ReportPersistedResultReturned"),
    ReportApprovedDisapproved("DatapointsListChanged"),
    //
    MobileHealthReturned("MobileHealthReturned"),
    MobileVersionReturned("MobileVersionReturned"),
    MobileCompaniesReturned("MobileCompaniesReturned"),
    MobileCompanyOverviewReturned("MobileCompanyOverviewReturned"),
    MobileSiteOverviewReturned("MobileSiteOverviewReturned"),
    
    MobileSavingsTrendReturned("MobileSavingsTrendReturned"),
    MobilePlantEfficiencyTrendReturned("MobilePlantEfficiencyTrendReturned"),
    MobileOptimizationTrendReturned("MobileOptimizationTrendReturned"),
    MobileChillerTrendReturned("MobileChillerTrendReturned"),
    MobileKeyTrendReturned("MobileKeyTrendReturned"),
    
    TeslaStationsListReturned("TeslaStationsListReturned"),
    TeslaStationInfoRetrieved("TeslaStationInfoRetrieved");

    private final String name;

    PropertyChangeNames(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
