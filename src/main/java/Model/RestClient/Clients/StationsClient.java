package Model.RestClient.Clients;

import Model.DataModels.Stations.HistoryPushObject;
import Model.DataModels.Stations.NetworkAvailabilityResponse;
import Model.DataModels.Stations.StationActivateRequest;
import Model.DataModels.Stations.StationActivateResponse;
import Model.DataModels.Stations.StationAlarmPushObject;
import Model.DataModels.Stations.StationAuditHistory;
import Model.DataModels.Stations.StationLogHistory;
import Model.DataModels.Stations.WizardStationStatus;
import Model.DataModels.Stations.StationStatusResponse;
import Model.DataModels.Stations.StationValidate;
import Model.DataModels.Stations.StationValidateQueryParams;
import Model.DataModels.Stations.StationsHeartbeat;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class StationsClient {

    private String serviceURL;
    private final RestClientCommon restClient;

    public StationsClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }

    public void setServiceURLAndToken(String serviceURL, String accessToken) {
        this.serviceURL = serviceURL;
        restClient.setOauthToken(accessToken);
    }

    public OEResponse getActivate() throws IOException {

        String url = serviceURL + "/stations/activate";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, NetworkAvailabilityResponse.class);
        }

        return resObj;
    }

    public OEResponse postActivate(StationActivateRequest req) throws IOException {

        String url = serviceURL + "/stations/activate";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, StationActivateResponse.class);
        }

        return resObj;
    }

    public OEResponse getStationsBog(String stationId) throws IOException {

        String url = serviceURL + "/stations/bog";

        if (stationId != null && stationId.length() > 0) {
            url += "?stationId=" + stationId;
        }

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            //resObj.responseObject = (String) resObj.responseObject;
            resObj.responseObject = "{\"halstatus\":\"bogfiledownloaded\"}";
        }
        return resObj;
    }

    public OEResponse getDownloadProfile(String stationId) throws IOException {

        String url = serviceURL + "/stations/configuration/profile";

        if (stationId != null && stationId.length() > 0) {
            url += "?stationId=" + stationId;
        }

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    public OEResponse getDownloadPoints(String stationId) throws IOException {
        String url = serviceURL + "/stations/configuration/points";

        if (stationId != null && stationId.length() > 0) {
            url += "?stationId=" + stationId;
        }

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    public OEResponse getDownloadDefaultParams(String stationId) throws IOException {

        String url = serviceURL + "/stations/configuration/default-parameters?stationId=" + stationId;

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    public OEResponse getDownloadPortalsParams(String stationId) throws IOException {

        String url = serviceURL + "/stations/configuration/portal-parameters";

        if (stationId != null && stationId.length() > 0) {
            url += "?stationId=" + stationId;
        }

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    public OEResponse getValidate(StationValidateQueryParams params) throws IOException {
        String url = serviceURL + "/stations/configuration/validate" + params.getQueryParams();
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {

            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, StationValidate.class);
        }
        return resObj;
    }

    public OEResponse getConfigurationStatus(String stationId) throws IOException {

        String url = serviceURL + "/stations/configuration/status";

        if (stationId != null && stationId.length() > 0) {
            url += "?stationId=" + stationId;
        }

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardStationStatus.class);
        }
        return resObj;
    }

    public OEResponse postConfigurationStatus(String stationId, WizardStationStatus status) throws IOException {

        String url = serviceURL + "/stations/configuration/status";
        if (stationId != null && stationId.length() > 0) {
            url += "?stationId=" + stationId;
        }

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(status);

        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);
        return resObj;
    }

    public OEResponse postStationsAudit(StationAuditHistory auditHistory) throws IOException {

        String url = serviceURL + "/stations/audit";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(auditHistory);

        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        return resObj;
    }

    public OEResponse postStationsLog(StationLogHistory auditLogHistory) throws IOException {

        String url = serviceURL + "/stations/log";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(auditLogHistory);

        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, StationStatusResponse.class);
        }
        return resObj;
    }

    public OEResponse postDataPointHistory(HistoryPushObject history) throws IOException {

        String url = serviceURL + "/stations/datapoint-history";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(history);

        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, StationStatusResponse.class);
        }

        return resObj;
    }
    
    public OEResponse postDataPointHistory(HistoryPushObject history, boolean doLogMessage) throws IOException {

        String url = serviceURL + "/stations/datapoint-history";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(history);

        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, StationStatusResponse.class);
        }

        return resObj;
    }

    public OEResponse postAlarmChanges(StationAlarmPushObject alarms) throws IOException {

        String url = serviceURL + "/stations/alarms";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(alarms);

        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, StationStatusResponse.class);
        }
        return resObj;
    }

    public OEResponse postHeartBeat(String stationID, String token, StationsHeartbeat heartBeat) throws IOException {

        String url = serviceURL + "/stations/heartbeat";

        if (stationID.length() > 0) {
            url += "?stationId=" + stationID;
        }

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(heartBeat);

        OEResponse resObj;

        if (token.length() > 0) {
            resObj = restClient.doPostAndGetBody(url, payload, token, true);
        } else {
            resObj = restClient.doPostAndGetBody(url, payload, true);
        }

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, StationStatusResponse.class);
        }

        return resObj;
    }

}
