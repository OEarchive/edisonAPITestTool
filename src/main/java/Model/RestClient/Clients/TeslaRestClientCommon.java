package Model.RestClient.Clients;

import Model.RestClient.EnumCallType;
import Model.RestClient.EnumRequestType;
import Model.RestClient.OEResponse;
import Model.RestClient.RRObj;
import Model.RestClient.RequestsResponses;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
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
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeslaRestClientCommon {

    private final String badCredentials = "incorrect credentials";

    static Logger logger = LoggerFactory.getLogger(RestClientCommon.class.getName());

    private String oauthToken;
    private final RequestsResponses rrs;

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
        } else if (responseString.length() > 0) {
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, responseString, oauthToken));
        } else {
            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.GET, retVal.responseCode, url, (String) retVal.responseObject, oauthToken));
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

            rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.REQUEST, EnumRequestType.POST, 0, url, payload, oauthToken));

            response = httpClient.execute(postRequest);

            StatusLine statusLine = response.getStatusLine();
            resp.responseObject = statusLine.getReasonPhrase();
            responseString = "";
            resp.responseCode = statusLine.getStatusCode();

            String logts = DateTime.now().withZone(DateTimeZone.UTC).toString();
            if (resp.responseCode >= 500) { 
                
                String msg = String.format("%s - bad status: %d ", logts, resp.responseCode);
                System.out.println(msg);
                System.out.println("url= " + url);
                System.out.println("payload:");
                System.out.println(payload);
                System.out.println("");
                
                rrs.addRequest(new RRObj(DateTime.now(), EnumCallType.RESPONSE, EnumRequestType.POST, resp.responseCode, url, payload, oauthToken));
                
            } else if (resp.responseCode >= 300) {
                String msg = String.format("%s - bad status: %d ", logts, resp.responseCode);
                if (addToken) {
                    msg += "token: " + oauthToken;
                }
                System.out.println(msg);
            }

            try {
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    InputStream inputStream = httpEntity.getContent();
                    BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
                    String output;
                    while ((output = br.readLine()) != null) {
                        responseString += output;
                    }
                }

                resp.responseCode = response.getStatusLine().getStatusCode();
                if (resp.responseCode == 401) {
                    responseString = badCredentials;
                }
                resp.responseObject = responseString;
            } catch (Exception ex) {
                System.out.println("caught exception");
            }

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

}
