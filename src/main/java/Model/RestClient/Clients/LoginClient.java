package Model.RestClient.Clients;

import Model.DataModels.Auth.AdminLoginCreds;
import Model.DataModels.Auth.E3OSOACIEResponse;
import Model.DataModels.Auth.PostForgotPasswordRequest;
import Model.DataModels.Auth.GetE3OSTokenRequest;
import Model.DataModels.Auth.PutForgotPasswordRequest;
import Model.DataModels.Auth.VerifyResetPasswordTokenRequest;
import Model.RestClient.LoginResponse;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class LoginClient {
    
    private final String badCredentials = "incorrect credentials";

    private final RestClientCommon restClient;
    private String serviceURL;

    public LoginClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }
    
    
    public OEResponse login(String serviceURL, AdminLoginCreds user) throws IOException {

        this.serviceURL = serviceURL;
        String url = serviceURL + "/auth/oauth/token";

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));

        Map<String, String> postBody = new HashMap<>();
        postBody.put("grant_type", "password");
        postBody.put("username", user.getUsername());
        postBody.put("password", user.getPassword());
        postBody.put("scope", "read+write");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(postBody);
        OEResponse resp = restClient.doPostAndGetBody(url, payload, false);
        if (resp.responseCode == 200) {
            resp.responseObject = mapper.readValue((String) resp.responseObject, LoginResponse.class);
            return resp;
        }
        
        OEResponse resp2 = new OEResponse();
        resp2.responseCode = resp.responseCode;
        resp2.responseObject = badCredentials;
        return resp2;
    }

    public OEResponse loginUser(String serviceURL, String userName, String password) throws IOException {

        this.serviceURL = serviceURL;
        String url = serviceURL + "/auth/oauth/token";

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));

        Map<String, String> postBody = new HashMap<>();
        postBody.put("grant_type", "password");
        postBody.put("username", userName);
        postBody.put("password", password);
        postBody.put("scope", "read+write");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(postBody);
        OEResponse resp = restClient.doPostAndGetBody(url, payload, false);
        if (resp.responseCode == 200) {
            resp.responseObject = mapper.readValue((String) resp.responseObject, LoginResponse.class);
            return resp;
        }
        OEResponse resp2 = new OEResponse();
        resp2.responseCode = resp.responseCode;
        resp2.responseObject = badCredentials;
        return resp2;
    }

    public OEResponse getOauthToken(String username, String password) throws IOException {

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
        if (resp.responseCode == 200) {
            resp.responseObject = mapper.readValue((String) resp.responseObject, LoginResponse.class);
            return resp;
        }
        
        OEResponse resp2 = new OEResponse();
        resp2.responseCode = resp.responseCode;
        resp2.responseObject = badCredentials;
        return resp2;
        //return resp;
    }

    public OEResponse getOriginatedCallInEndpoint() throws IOException {

        String url = serviceURL + "/auth/e3os";
        OEResponse resp = restClient.getResponse(url);

        if (resp.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resp.responseObject = mapper.readValue((String) resp.responseObject, E3OSOACIEResponse.class);
        }
        return resp;
    }

    public OEResponse getE3OSToken(GetE3OSTokenRequest req) throws IOException {

        String url = serviceURL + "/auth/e3os/" + req.getQueryParamsString();
        OEResponse resp = restClient.getResponse(url);

        return resp;
    }

    public OEResponse postForgotPassword(PostForgotPasswordRequest req) throws IOException {

        String url = serviceURL + "/auth/forgot_password";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resp = restClient.doPostAndGetBody(url, payload, false);

        return resp;
    }

    public OEResponse putForgotPassword(PutForgotPasswordRequest req) throws IOException {

        String url = serviceURL + "/auth/forgot_password";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resp = restClient.doPutAndGetBody(url, payload, false);

        return resp;
    }

    public OEResponse postForgotPasswordVerifyToken(VerifyResetPasswordTokenRequest req) throws IOException {

        String url = serviceURL + "/auth/forgot-password/verify-token";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resp = restClient.doPostAndGetBody(url, payload, true);

        return resp;
    }
}

