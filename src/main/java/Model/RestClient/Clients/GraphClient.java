package Model.RestClient.Clients;

import Model.DataModels.Graph.EnumGraphNodeTypes;
import Model.DataModels.Graph.GetChildrenRequest;
import Model.DataModels.Graph.GetChildrenResponse;
import Model.DataModels.Graph.GetMetaDataRequest;
import Model.DataModels.Graph.RuleInfoResponse;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphClient {

    private String serviceURL;
    private String adminURL;
    private final RestClientCommon restClient;

    public GraphClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }

    public void setServiceURLAndToken(String serviceURL, String adminURL, String accessToken) {
        this.serviceURL = serviceURL;
        this.adminURL = adminURL;
        restClient.setOauthToken(accessToken);
    }

    //returns list of customers for current user
    public OEResponse getGraphElements() throws IOException {

        String url = serviceURL + "/graph";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //delete something
    public OEResponse deleteGraphElement(String sid) throws IOException {

        String url = serviceURL + "/graph/" + sid;
        OEResponse resObj = restClient.doDelete(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //get metadata for element
    public OEResponse getGraphElementMetadata(String sid, GetMetaDataRequest mdr) throws IOException {

        String url = serviceURL + "/graph/" + sid + mdr.getQueryParamsString();
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
    
    
    //TODO: new....
    public OEResponse postElementAttributes(String sid, String payload) throws IOException {

        String url = serviceURL + "/graph/" + sid;

        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //TODO: get site details - TODO: Is this a valid api to call?
    public OEResponse getSiteDetails(String sid) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/site_details";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
    
    //Re: ED-1076
    /* ---- removed apichannge
    public OEResponse getSiteDetailsZZZ(String sid) throws IOException {

        String url = serviceURL + "/graph/" + sid;
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
    */

    //get children of specific type
    public OEResponse getGraphElementChildren(String sid ) throws IOException {

        String url = serviceURL + "/graph/" + sid;
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, GetChildrenResponse.class);
        }

        return resObj;
    }

    //upsert node  TODO - what's the payload
    public OEResponse putGraphElement(String sid, EnumGraphNodeTypes nodeType, String payload) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/" + nodeType.getEdisonName();

        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //get schema for element
    public OEResponse getGraphElementSchema(String sid, EnumGraphNodeTypes nodeType) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/" + nodeType.getEdisonName();
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
    
    
    //tainted graph calls
    
    /*
    curl -X POST -H "Authorization: Bearer 71b78a2c-6f56-4f47-8eb1-d937ecd1776f" -H "Content-Type: application/json" -d '{
  "@class": ".RuleOwnerGraphQuery",
  "ownerSid": "c:piggins.s:biggnshig-edge"
}
' "http://internal-edison-dev-graph-lb-1908683909.us-west-2.elb.amazonaws.com/api/query"
    */
    
    public OEResponse getRulesInfo(String siteSid ) throws IOException {

        String url = adminURL + "/query";
        
        
        Map<String, String> postBody = new HashMap<>();
        postBody.put("@class", ".RuleOwnerGraphQuery");
        postBody.put("ownerSid", siteSid);

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(postBody);
        
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, RuleInfoResponse.class);
        }

        return resObj;
    }
    

}
