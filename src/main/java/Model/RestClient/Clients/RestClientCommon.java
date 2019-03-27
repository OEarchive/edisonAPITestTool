package Model.RestClient.Clients;

import Model.RestClient.EnumRequestType;
import Model.RestClient.OEResponse;
import Model.RestClient.RRObj;
import Model.RestClient.EnumCallType;
import Model.RestClient.RequestsResponses;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClientCommon {

    private final String badCredentials = "incorrect credentials";

    static Logger logger = LoggerFactory.getLogger(RestClientCommon.class.getName());

    private String oauthToken;
    private RequestsResponses rrs;

    public RestClientCommon(RequestsResponses rrs) {
        this.rrs = rrs;
        this.oauthToken = null;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getOAuthToken() {
        return oauthToken;
    }

    /* 
     curl -X GET 
     -H "Authorization: Bearer 0a2f7df8-e0cd-4875-97a7-814b7bd34f2c" 
     -H "Content-Type: application/json" 
     -H "Accept: application/vnd.openxmlformats-officedoent.spreadsheetml.sheet" 
     -H "Cache-Control: no-cache" 
     -H "Postman-Token: 74550570-40a6-ab8c-e5ba-9b45d85c8fb1" 
     "http://192.168.99.100/api/2016.06/sites/c:ldfenterpr.s:springfiel-edge/points-list" 
     > x.xlsx
     */
    protected OEResponse getXLSResponse(String url) throws IOException {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));
        nvps.add(new BasicNameValuePair("Accept", "application/vnd.openxmlformats-officedoent.spreadsheetml.sheet"));
        nvps.add(new BasicNameValuePair("Cache-Control", "no-cache"));
        nvps.add(new BasicNameValuePair("Authorization", "Bearer " + oauthToken));
        return getXMLResponse(url, nvps);
    }

    protected OEResponse getResponse(String url) throws IOException {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));
        nvps.add(new BasicNameValuePair("Authorization", "Bearer " + oauthToken));
        return getResponse(url, nvps);
    }

    protected OEResponse getXMLResponse(String url, List<NameValuePair> nvps) throws IOException {

        OEResponse retVal = new OEResponse();
        String responseString = "";
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(url);
        int responseCode = 0;

        try {

            for (NameValuePair h : nvps) {
                getRequest.addHeader(h.getName(), h.getValue());
            }

            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.REQUEST, EnumRequestType.GET, url, oauthToken));
            response = httpClient.execute(getRequest);
            responseCode = response.getStatusLine().getStatusCode();
            retVal.responseCode = responseCode;

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

            File file = new File("/Users/hal/Desktop/sources/oe-test/oe-test-opticxapi-gui/foo.xlsx");

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            int octet;
            while ((octet = br.read()) != -1) {
                bw.write(octet);
            }
            bw.close();
            fw.close();
            br.close();

        } catch (ClientProtocolException ex) {
            String msg = "Client protocol excpetion";
            retVal.responseObject = msg;
            retVal.responseCode = 888;
        } catch (Exception ex) {
            retVal.responseObject = ex.getMessage();
            retVal.responseCode = 888;

        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }

        if (retVal.responseCode == 200) {
            retVal.objSize = responseString.length();
            retVal.responseObject = responseString;
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, responseString, oauthToken));
        } else if (responseString != null && responseString.length() > 0) {
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, responseString, oauthToken));
        } else {
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, (String) retVal.responseObject, oauthToken));
        }

        return retVal;

    }

    protected OEResponse getResponse(String url, List<NameValuePair> nvps) throws IOException {

        OEResponse retVal = new OEResponse();
        String responseString = "";
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(url);
        int responseCode = 0;

        try {

            for (NameValuePair h : nvps) {
                getRequest.addHeader(h.getName(), h.getValue());
            }

            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.REQUEST, EnumRequestType.GET, url, oauthToken));
            response = httpClient.execute(getRequest);
            responseCode = response.getStatusLine().getStatusCode();
            retVal.responseCode = responseCode;

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output;
            while ((output = br.readLine()) != null) {
                responseString += output;
            }
        } catch (ClientProtocolException ex) {
            String msg = "Client protocol excpetion";
            retVal.responseObject = msg;
            retVal.responseCode = 888;
        } catch (Exception ex) {
            retVal.responseObject = ex.getMessage();
            retVal.responseCode = 888;

        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }

        if (retVal.responseCode == 200) {
            retVal.objSize = responseString.length();
            retVal.responseObject = responseString;
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, responseString, oauthToken));
        } else if (responseString != null && responseString.length() > 0) {
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, responseString, oauthToken));
        } else {
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, (String) retVal.responseObject, oauthToken));
        }

        return retVal;

    }

    protected OEResponse doDelete(String url) throws IOException {

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));
        nvps.add(new BasicNameValuePair("Authorization", "Bearer " + oauthToken));

        return doDelete(url, nvps);

    }

    protected OEResponse doDelete(String url, List<NameValuePair> nvps) throws IOException {

        OEResponse retVal = new OEResponse();
        String responseString = "";
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete getRequest = new HttpDelete(url);
        int responseCode = 0;

        try {

            for (NameValuePair h : nvps) {
                getRequest.addHeader(h.getName(), h.getValue());
            }

            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.REQUEST, EnumRequestType.DELETE, url, oauthToken));

            response = httpClient.execute(getRequest);
            responseCode = response.getStatusLine().getStatusCode();
            retVal.responseCode = responseCode;

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output;
            while ((output = br.readLine()) != null) {
                responseString += output;
            }

        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
        rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.DELETE, responseCode, url, responseString, oauthToken));
        retVal.objSize = (retVal.responseCode == 200) ? responseString.length() : 0;
        retVal.responseObject = responseString;
        return retVal;
    }

    /*
            String url = this.serviceURL + "/auth/oauth/token";

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));

        Map<String, String> postBody = new HashMap<>();
        postBody.put("grant_type", "password");
        postBody.put("username", username);
        postBody.put("password", password);
        postBody.put("scope", "read+write");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(postBody);
        OEResponse resp = restClient.doPostAndGetBody(url, payload, false);
     */
    protected OEResponse doPostAndGetBody(String url, String payload, boolean addToken) throws UnsupportedEncodingException, IOException {

        return doPostAndGetBody(url, payload, oauthToken, addToken);

    }

    protected OEResponse doPostAndGetBody(String url, String payload, String token, boolean addToken) throws UnsupportedEncodingException, IOException {

        String responseString = "";
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));
        if (addToken) {
            nvps.add(new BasicNameValuePair("Authorization", "Bearer " + token));
        }
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(url);
        OEResponse resp = new OEResponse();

        try {
            for (NameValuePair h : nvps) {
                postRequest.addHeader(h.getName(), h.getValue());
            }
            postRequest.setEntity(new StringEntity(payload));
            String temp = postRequest.toString();
            if (addToken) {
                rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.REQUEST, EnumRequestType.POST, 0, url, payload, oauthToken));
            } else {

                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> mangledPayload = mapper.readValue(payload, Map.class);
                mangledPayload.put("password", "**********");
                payload = mapper.writeValueAsString(mangledPayload);

                rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.REQUEST, EnumRequestType.LOGIN, 0, url, payload, oauthToken));
            }
            response = httpClient.execute(postRequest);
            resp.responseCode = response.getStatusLine().getStatusCode();
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output;
            while ((output = br.readLine()) != null) {
                responseString += output;
            }
            if (resp.responseCode == 401) {
                responseString = badCredentials;
            }
            resp.responseObject = responseString;

        } catch (Exception ex) {
            resp.responseObject = ex.getMessage();
            resp.responseCode = 999;
            String msg = Integer.toString(resp.responseCode) + ": " + ex.getMessage();
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.POST, resp.responseCode, url, msg, oauthToken));
        } finally {
            if (response != null) {
                rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.POST, resp.responseCode, url, responseString, oauthToken));
                response.close();
            }
            httpClient.close();
        }
        return resp;
    }

    protected OEResponse doPutAndGetBody(String url, String payload, boolean addToken) throws UnsupportedEncodingException, IOException {
        return doPutAndGetBody(url, payload, oauthToken, addToken);
    }

    protected OEResponse doPutAndGetBody(String url, String payload, String token, boolean addToken) throws UnsupportedEncodingException, IOException {

        String responseString = "";
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));
        if (addToken) {
            nvps.add(new BasicNameValuePair("Authorization", "Bearer " + oauthToken));
        }
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut putRequest = new HttpPut(url);
        OEResponse resp = new OEResponse();

        try {
            for (NameValuePair h : nvps) {
                putRequest.addHeader(h.getName(), h.getValue());
            }
            putRequest.setEntity(new StringEntity(payload));
            String temp = putRequest.toString();
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.REQUEST, EnumRequestType.PUT, 0, url, payload, oauthToken));
            response = httpClient.execute(putRequest);
            resp.responseCode = response.getStatusLine().getStatusCode();
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output;
            while ((output = br.readLine()) != null) {
                responseString += output;
            }
            resp.responseObject = responseString;
        } catch (Exception ex) {
            resp.responseObject = ex.getMessage();
            resp.responseCode = 777;
            String msg = Integer.toString(resp.responseCode) + ": " + ex.getMessage();
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.PUT, resp.responseCode, url, msg, oauthToken));
        } finally {
            if (response != null) {
                rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.PUT, resp.responseCode, url, responseString, oauthToken));
                response.close();
            }
            httpClient.close();
        }
        return resp;
    }

}
