package Model.RestClient.Clients;

import Model.DataModels.Alarms.AlarmHistoryEntry;
import Model.DataModels.Alarms.AlarmListEntry;
import Model.DataModels.Sites.ActivationCodeResponse;
import Model.DataModels.Sites.CheckForUpdatesResponse;
import Model.DataModels.Sites.CreateSiteRequest;
import Model.DataModels.Sites.EnumPointsListDownloadType;
import Model.DataModels.Sites.GetSiteInfoQueryParams;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.SiteDPConfig.SiteDPConfigPoints;
import Model.DataModels.Sites.SiteQueryResponse;
import Model.DataModels.Sites.SiteTrend;
import Model.DataModels.Sites.SiteTrendAndKPIRequest;
import Model.DataModels.Sites.SiteTrendKPI;
import Model.DataModels.Sites.UpdateSiteRequest;
import Model.DataModels.Views.PageView;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SitesClient {

    private String serviceURL;
    private final RestClientCommon restClient;

    public SitesClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }

    public void setServiceURLAndToken(String serviceURL, String accessToken) {
        this.serviceURL = serviceURL;
        restClient.setOauthToken(accessToken);
    }

    public OEResponse getSites(String customerSid) throws IOException {

        String url = serviceURL + "/sites";

        if (customerSid != null && customerSid.length() > 0) {
            url = url + "/?customerSid=" + customerSid;
        }

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, SiteQueryResponse.class);
        }

        return resObj;
    }

    public OEResponse getSiteInfo(String sid, GetSiteInfoQueryParams params) throws IOException {

        String url = serviceURL + "/sites/" + sid+ "?datapoints=*&activeAlarms=false&hierarchyAlarms=false"; // + params.getQueryParams();

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, Site.class);
        }

        return resObj;
    }

    public OEResponse createSite(CreateSiteRequest csr) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(csr);

        String url = serviceURL + "/sites";
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {

            resObj.responseObject = mapper.readValue((String) resObj.responseObject, Site.class);

        }
        return resObj;
    }

    public OEResponse updateSite(String siteSid, Boolean forceUpdate, UpdateSiteRequest usr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(usr);
        String forceUpdateFlag = (forceUpdate) ? "true" : "false";

        String url = serviceURL + "/sites/" + siteSid + "/?" + "forceUpdate=" + forceUpdateFlag;
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    public OEResponse getPointsConfig(String sid, EnumPointsListDownloadType downLoadType) throws IOException {

        String payload = "";

        String url = serviceURL + "/sites/" + sid + "/points-list";

        //TODO: Add header for download xls
        OEResponse resObj;
        switch (downLoadType) {
            case JSON:

                resObj = restClient.getResponse(url);
                break;
            default:
                resObj = restClient.getXLSResponse(url);
        }

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, SiteDPConfigPoints.class);
        }
        return resObj;
    }

    public OEResponse getNewActivationCode(String sid) throws IOException {

        String payload = "";

        String url = serviceURL + "/sites/" + sid + "/activation-code";
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        //OEResponse resObj = new OEResponse();
        //resObj.responseCode = 200;
        //resObj.responseObject = "{\"activationCode\" : \"12345\"}";
        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, ActivationCodeResponse.class);
        }
        return resObj;
    }

    public OEResponse checkForUpdates(String sid) throws IOException {

        String payload = "{\"message\":\"the site has changed\"}";

        String url = serviceURL + "/sites/" + sid + "/check-for-updates";
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, CheckForUpdatesResponse.class);
        }
        return resObj;
    }

    public OEResponse getSiteTrend(String sid, SiteTrendAndKPIRequest req) throws IOException {

        String url = serviceURL + "/sites/" + sid + "/trend" + req.getQueryParamsString();
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, SiteTrend.class);
        }
        return resObj;
    }

    public OEResponse getSiteKPI(String sid, String kpi, SiteTrendAndKPIRequest req) throws IOException {

        String url = serviceURL + "/sites/" + sid + "/trend/" + kpi + "/" + req.getQueryParamsString();
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, SiteTrendKPI.class);
        }
        return resObj;
    }

    public OEResponse getUIMetaData(String productType, String sid, String view) throws IOException {

        String url = serviceURL + "/ui/" + productType + "/" + sid + "/" + view;
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, PageView.class);
        }
        return resObj;
    }

    public OEResponse getSitesByStationID(String stationID) throws IOException {

        String url = serviceURL + "/sites";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode != 200) {
            return resObj;
        }

        ObjectMapper mapper = new ObjectMapper();
        resObj.responseObject = mapper.readValue((String) resObj.responseObject, SiteQueryResponse.class);

        return resObj;
    }
    
    
    public OEResponse getAlarmHistory(String siteSid, String startDateString, String endDateString) throws IOException {

        String url = serviceURL + "/sites/" + siteSid + "/alarm-histories/?startDate=" + startDateString + "&endDate=" + endDateString;
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = 
                    mapper.readValue((String) resObj.responseObject, new TypeReference<List<AlarmHistoryEntry>>() {});
        }

        return resObj;
    }


    public OEResponse getAlarms(String siteSid) throws IOException {

        String url = serviceURL + "/sites/" + siteSid + "/alarms";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = 
                    mapper.readValue((String) resObj.responseObject, new TypeReference<List<AlarmListEntry>>() {});
        }

        return resObj;
    }
    

}
