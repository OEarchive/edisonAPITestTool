package Model.RestClient.Clients;

import Model.DataModels.TrendAPI.MobileCompanyList;
import Model.DataModels.TrendAPI.MobileCompanyOverview;
import Model.DataModels.TrendAPI.MobileHealthInfo;
import Model.DataModels.TrendAPI.SiteInfo.MobileSiteInfo;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class MobileAPIClient {

    private String serviceURL;
    private final RestClientCommon restClient;

    public MobileAPIClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }

    public void setServiceURLAndToken(String serviceURL, String accessToken) {
        this.serviceURL = serviceURL + "/trend";
        restClient.setOauthToken(accessToken);
    }

    //returns list of customers for current user
    public OEResponse getGraph() throws IOException {

        String url = serviceURL + "/graph";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //==================
    public OEResponse getMobileHealth() throws IOException {
        
        String url = serviceURL + "/health";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, MobileHealthInfo.class);
        }

        return resObj;
    }
    
    public OEResponse getMobileVersion() throws IOException {
        String url = serviceURL + "/version";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, MobileCompanyList.class);
        }

        return resObj;

    }

    public OEResponse getMobileCompanies() throws IOException {
        String url = serviceURL + "/companies";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, MobileCompanyList.class);
        }

        return resObj;

    }

    
    public OEResponse getMobileCompanyOverview(String uuid) throws IOException {

        String url = serviceURL + "/company-overview?uuid=" + uuid;

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            String responseString = (String) resObj.responseObject;
            responseString = responseString.replaceAll("\"\\s*\\\\n\\s*\"", "\"\"");
            resObj.responseObject = mapper.readValue(responseString, MobileCompanyOverview.class);

        }
        return resObj;
    }

    public OEResponse GetMobileSiteOverview(String uuid, String timeFrame) throws IOException {

         String url = serviceURL + "/site-overview?uuid=" + uuid + "&timeframe=" + timeFrame;

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, MobileSiteInfo.class);
        }

        return resObj;
    }
    
    
    public OEResponse getMobileTrend(String uuid, String trendType, String timeFrame) throws IOException {
        String url = serviceURL + "/site-trends?uuid=" + uuid + "&trend=" + trendType + "&timeframe=" + timeFrame;
        OEResponse resObj = restClient.getResponse(url);

        return resObj;
    }
    

    /*
    // ======================TRENDS==================
    public OEResponse getTrendKey(String uuid, String timeFrame) throws IOException {
        String url = apiHost.getServiceEndpointURL()
                + "site-trends?uuid=" + uuid + "&trend="
                + EnumTrendTypes.KEY.getRestServiceName() + "&timeframe=" + timeFrame;

        OEResponse resp = getResponse(url);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, KeyStatistics.class);
        }

        return resp;

    }

    public OEResponse getPlantEfficiencyTrends(String uuid, String timeFrame) throws IOException {
        String url = apiHost.getServiceEndpointURL()
                + "site-trends?uuid=" + uuid + "&trend="
                + EnumTrendTypes.PLANT.getRestServiceName() + "&timeframe=" + timeFrame;

        OEResponse resp = getResponse(url);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, PlantEfficiencyTrendInfo.class);
        }

        return resp;

    }

    public OEResponse getChillerTrendInfo(String uuid, String timeFrame) throws IOException {

        String url = apiHost.getServiceEndpointURL()
                + "site-trends?uuid=" + uuid + "&trend="
                + EnumTrendTypes.CHILLER.getRestServiceName() + "&timeframe=" + timeFrame;

        OEResponse resp = getResponse(url);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, ChillerTrendInfo.class);
        }

        return resp;

    }

    public OEResponse getOptimizationTrendInfo(String uuid, String timeFrame) throws IOException {

        String url = apiHost.getServiceEndpointURL()
                + "site-trends?uuid=" + uuid + "&trend="
                + EnumTrendTypes.OPTIMIZATION.getRestServiceName() + "&timeframe=" + timeFrame;

        OEResponse resp = getResponse(url);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, OptimizationTrendInfo.class);
        }

        return resp;
    }

    public OEResponse getTrendSavings(String uuid, String timeFrame) throws IOException {

        String url = apiHost.getServiceEndpointURL()
                + "site-trends?uuid=" + uuid + "&trend="
                + EnumTrendTypes.MONEYSAVINGS.getRestServiceName() + "&timeframe=" + timeFrame;

        OEResponse resp = getResponse(url);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, SavingsTrendInfoClass.class);
        }

        return resp;
    }

    public OEResponse getReportJSON(String uuid, String reportId) throws IOException {

        String url = apiHost.getServiceEndpointURL() + "report?uuid=" + uuid + "&reportId=" + reportId;
        OEResponse resp = getResponse(url);

        return resp;
    }

    public OEResponse getSiteReports(String uuid) throws IOException {

        String url = apiHost.getServiceEndpointURL() + "site-reports?uuid=" + uuid;

        OEResponse resp = getResponse(url, 100);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, SiteReportList.class);
        }

        return resp;

    }

    public OEResponse getChillerDiagnostics(String uuid) throws IOException {
        String url = apiHost.getServiceEndpointURL() + "site-chiller-diagnostics?uuid=" + uuid;

        OEResponse resp = getResponse(url, 100);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, ChillerDiagnosticList.class);
        }

        return resp;

    }

    // ============ OTHER ===================================================
    public OEResponse GetInsights(String uuid) throws IOException {

        String url = apiHost.getServiceEndpointURL() + "insights?uuid=" + uuid;
        OEResponse resp = getResponse(url);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, Insights.class);
        }

        return resp;

    }

    public OEResponse GetReps(String uuid) throws IOException {

        String url = apiHost.getServiceEndpointURL() + "reps?uuid=" + uuid;
        OEResponse resp = getResponse(url);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, AccountReps.class);
        }

        return resp;

    }

    public OEResponse getVersion() throws IOException {

        String url = apiHost.getServiceEndpointURL() + "version";

        String body = "{\"version\" :\"1.3.13\"}";
        OEResponse resp = doPostAndGetBody(url, body);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, TrendVersionInfo.class);
        }

        return resp;

    }

    public OEResponse GetTotalSavings() throws IOException {

        //String url = apiHost.getServiceEndpointURL() + "public/totalsavings";
        String url = "https://api.oeprod.us/api/2015.10/public/totalsavings";
        OEResponse resp = getResponse(url);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, TotalSavings.class);
        }

        return resp;

    }

    */

}
