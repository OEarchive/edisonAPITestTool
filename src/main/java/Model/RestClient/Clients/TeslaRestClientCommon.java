package Model.RestClient.Clients;

import Model.RestClient.EnumCallType;
import Model.RestClient.EnumRequestType;
import Model.RestClient.OEResponse;
import Model.RestClient.RRObj;
import Model.RestClient.RequestsResponses;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeslaRestClientCommon {

    private final String badCredentials = "incorrect credentials";

    static Logger logger = LoggerFactory.getLogger(RestClientCommon.class.getName());

    private String oauthToken;
    private RequestsResponses rrs;

    public TeslaRestClientCommon(RequestsResponses rrs) {
        this.rrs = rrs;
        this.oauthToken = null;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getOAuthToken() {
        return oauthToken;
    }

    protected OEResponse getResponse(String url) throws IOException {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));
        nvps.add(new BasicNameValuePair("Authorization", "Bearer " + oauthToken));
        return getResponse(url, nvps);
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

            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.REQUEST, EnumRequestType.GET, url));
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
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, responseString));
        } else if (responseString != null && responseString.length() > 0) {
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, responseString));
        } else {
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, (String) retVal.responseObject));
        }

        return retVal;

    }
    
    protected OEResponse doPostAndGetBody(String url, String payload, boolean addToken) throws UnsupportedEncodingException, IOException {
        return doPostAndGetBody(url, payload, oauthToken, addToken);
    }

    protected OEResponse doPostAndGetBody(String url, String payload, String token, boolean addToken) throws UnsupportedEncodingException, IOException {

        String responseString = "";
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));

        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(url);
        OEResponse resp = new OEResponse();

        try {
            if (addToken) {
                nvps.add(new BasicNameValuePair("Authorization", "Bearer " + oauthToken));
            }
            
            for (NameValuePair h : nvps) {
                postRequest.addHeader(h.getName(), h.getValue());
            }

            postRequest.setEntity(new StringEntity(payload));
            String temp = postRequest.toString();

            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.REQUEST, EnumRequestType.POST, 0, url, payload));

            response = httpClient.execute(postRequest);

            StatusLine statusLine = response.getStatusLine();
            resp.responseObject = statusLine.getReasonPhrase();
            //responseString = statusLine.getReasonPhrase();
            responseString = "";
            resp.responseCode = statusLine.getStatusCode();

            if (resp.responseCode != 204) {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
                    String output;
                    while ((output = br.readLine()) != null) {
                        responseString += output;
                    }

                    resp.responseCode = response.getStatusLine().getStatusCode();
                    if (resp.responseCode == 401) {
                        responseString = badCredentials;
                    }
                    resp.responseObject = responseString;
                } catch (Exception ex) {

                }
            }

        } catch (Exception ex) {
            resp.responseObject = ex.getMessage();
            resp.responseCode = 999;
            String msg = Integer.toString(resp.responseCode) + ": " + ex.getMessage();
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.POST, resp.responseCode, url, msg));
        } finally {
            if (response != null) {
                rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.POST, resp.responseCode, url, responseString));
                response.close();
            }
            httpClient.close();
        }
        return resp;
    }

}
