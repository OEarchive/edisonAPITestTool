
package Model.RestClient.Clients;

import Model.DataModels.Alarms.AlarmHistoryEntry;
import Model.DataModels.Reports.CreateReportRequest;
import Model.DataModels.Reports.MonthlyReportItem;
import Model.DataModels.Reports.ReportsList;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;


public class ReportsClient {

    private String serviceURL;
    private final RestClientCommon restClient;

    public ReportsClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }

    public void setServiceURLAndToken(String serviceURL, String accessToken) {
        this.serviceURL = serviceURL;
        restClient.setOauthToken(accessToken);
    }

    public OEResponse getReports(String sid, boolean onlyApproved ) throws IOException {

        String url = serviceURL + "/sites/" + sid + "/reports";
        
        if( onlyApproved ){
            url += "?onlyApproved=true";
        }
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, new TypeReference<List<MonthlyReportItem>>() {});
        }

        return resObj;
    }
    
    
    public OEResponse getReportSchema(String reportId ) throws IOException {
        
       //https://opticx.oedev.us/api/2016.06/report-schemas/c:testcustomerdemo.s:v5demosa-edge.rp:monthly_report/reports
       //            {{API_URL}}/api/2016.06/report-schemas/c:testcustomerdemo.s:demo2-edge     .rp:monthly_report/reports
       //https://opticx.oedev.us/api/2016.06/report-schemas/c:testcustomerdemo.s:v5demosa-edge  .rp:monthly_report/reports

        String url = serviceURL + "/reports/" + reportId;
        OEResponse resObj = restClient.getResponse(url);

        //if (resObj.responseCode == 200) {
        //    ObjectMapper mapper = new ObjectMapper();
        //    resObj.responseObject = mapper.readValue((String) resObj.responseObject, ReportsList.class);
        //}

        return resObj;
    }

    //TODO - not clear in swagger
    public OEResponse postReport(String sid, CreateReportRequest req) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        return resObj;
    }

}