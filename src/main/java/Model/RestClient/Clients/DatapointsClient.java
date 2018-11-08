package Model.RestClient.Clients;

import Model.DataModels.Datapoints.DatapointHistoriesQueryParams;
import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.Datapoints.GetDatapointHistoryResponse;
import Model.DataModels.Datapoints.PostDatapointRequest;
import Model.DataModels.Datapoints.PushDatapointsRequest;
import Model.DataModels.Datapoints.PushDatapointsResponse;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

public class DatapointsClient {

    private String serviceURL;
    private final RestClientCommon restClient;

    public DatapointsClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }

    public void setServiceURLAndToken(String serviceURL, String accessToken) {
        this.serviceURL = serviceURL;
        restClient.setOauthToken(accessToken);
    }

    //TODO : New
    public OEResponse postDatapointAssocaions(String sourceSid, String sourceName, String payload) throws IOException {

        String url = serviceURL + "/datapoint-associations/" + sourceSid + "/" + sourceName;

        //ObjectMapper mapper = new ObjectMapper();
        //String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //TODO : New
    public OEResponse deleteDatapointAssociation(String sourceSid, String sourceName) throws IOException {

        //String url = serviceURL + "/graph/" + sid + "/datapoints/" + includeDescendents;
        String url = serviceURL + "/datapoint-associations/" + sourceSid + "/" + sourceName;

        OEResponse resObj = restClient.doDelete(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, DatapointsAndMetadataResponse.class);
        }

        return resObj;
    }

    public OEResponse getDatapointHistories(DatapointHistoriesQueryParams queryParams) throws IOException {

        String url = serviceURL + "/datapoint-histories?" + queryParams.getQueryParamsString();

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, new TypeReference<List<DatapointHistoriesResponse>>() {
            });
            //resObj.responseObject = mapper.readValue((String) resObj.responseObject, DatapointsAndMetadataResponse.class);
        }

        return resObj;
    }

    public OEResponse getDatapoints(String sourceSid, String listOfPoints) throws IOException {

        String url = serviceURL + "/datapoints/?sid=" + sourceSid;

        if (listOfPoints.length() > 0) {
            url += "&names=" + listOfPoints;
        }

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, new TypeReference<List<DatapointsAndMetadataResponse>>() {
            });
            //resObj.responseObject = mapper.readValue((String) resObj.responseObject, DatapointsAndMetadataResponse.class);
        }

        return resObj;
    }

    public OEResponse postDatapoint(PostDatapointRequest req) throws IOException {

        String url = serviceURL + "/datapoints";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);

        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //TODO: New
    public OEResponse updateDatapoint(String sourceSid, String sourceName, String body) throws IOException {

        String url = serviceURL + "/datapoints/" + sourceSid + "/" + sourceName;

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, GetDatapointHistoryResponse.class);
        }

        return resObj;
    }

    /*
    public OEResponse putHistory(String sid, PushDatapointsRequest req) throws IOException {
        return putHistory(sid, restClient.getOAuthToken(), req);  
    }
     */
    public OEResponse putHistory(String sid, String token, PushDatapointsRequest req) throws IOException {

        //String url = serviceURL + "/graph/" + sid + "/datapoints/history";
        String url = serviceURL + "/stations/datapoint-history";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);

        OEResponse resObj = restClient.doPutAndGetBody(url, payload, token, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, PushDatapointsResponse.class);
        }
        return resObj;
    }


    /*
    public OEResponse postQuery(String sid) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/datapoints/query";
        String payload = "";
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
     */

 /*
    public OEResponse deleteQuery(String sid, String name) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/datapoints/query/" + name;
        String payload = "";
        OEResponse resObj = restClient.doDelete(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
     */

 /*
    public OEResponse getDatapointQueryResults(String sid, String name) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/datapoints/query/" + name;
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
    
    
    /* apihange
    public OEResponse deleteDatapointAssociation(String sid, String name) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/datapoints/" + name;
        OEResponse resObj = restClient.doDelete(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
     */
 /*    apihange  
    
    public OEResponse getDatapointMetadata(String sid, String name) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/datapoints/" + name + "?includeDescendents=true";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, DatapointsAndMetadataResponse.class);
        }
        return resObj;
    }
     */
 /* apichange
    public OEResponse updateDatapointMetadata(String sid, String name, UpdateMetaDataRequest req) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/datapoints/" + name;

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);

        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {

            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
     */
}
