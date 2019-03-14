/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.RestClient.Clients;

import Model.DataModels.TeslaModels.EnumTeslaBaseURLs;
import Model.DataModels.TeslaModels.EnumTeslaUsers;
import Model.DataModels.TeslaModels.TeslaLoginResponse;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


public class TeslaLoginClient {
    
    private final String badCredentials = "incorrect credentials";

    private final TeslaRestClientCommon restClient;

    public TeslaLoginClient(TeslaRestClientCommon restClient) {
        this.restClient = restClient;
    }
    
    
    public OEResponse login(EnumTeslaBaseURLs serviceURL, EnumTeslaUsers user) throws IOException {

        String url = serviceURL.getURL() + "/oauth/token";

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));

        Map<String, String> postBody = new HashMap<>();
        postBody.put("grantType", "password");
        postBody.put("email", user.getEmail());
        postBody.put("password", user.getPassword());

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(postBody);
        OEResponse resp = restClient.doPostAndGetBody(url, payload, false);
        if (resp.responseCode == 200) {
            resp.responseObject = mapper.readValue((String) resp.responseObject, TeslaLoginResponse.class);
            return resp;
        }
        
        OEResponse resp2 = new OEResponse();
        resp2.responseCode = resp.responseCode;
        resp2.responseObject = badCredentials;
        return resp2;
    }

}
