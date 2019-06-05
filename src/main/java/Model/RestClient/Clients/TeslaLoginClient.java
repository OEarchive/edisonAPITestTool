
package Model.RestClient.Clients;

import Model.DataModels.TeslaModels.EnumTeslaBaseURLs;
import Model.DataModels.TeslaModels.TeslaLoginResponse;
import Model.DataModels.TeslaModels.TeslaUsersInfo;
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

    private EnumTeslaBaseURLs serviceURL = null;
    private String teslaUsername = null;
    private String teslaPassword = null;

    private final TeslaRestClientCommon restClient;

    public TeslaLoginClient(TeslaRestClientCommon restClient) {
        this.restClient = restClient;
    }

    public String getNewToken() throws IOException {

        OEResponse newTokenResponse = login(serviceURL);
        TeslaLoginResponse loginResponse = (TeslaLoginResponse) newTokenResponse.responseObject;
        return loginResponse.getAccessToken();

    }

    public OEResponse login(EnumTeslaBaseURLs serviceURL ) throws IOException {

        TeslaUsersInfo teslaUser = new TeslaUsersInfo();

        this.serviceURL = serviceURL;
        this.teslaUsername = teslaUser.getUserName();
        this.teslaPassword = teslaUser.getPassword();

        String url = serviceURL.getURL() + "/oauth/token";

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("content-type", "application/json"));

        Map<String, String> postBody = new HashMap<>();
        postBody.put("grant_type", "password");
        postBody.put("email", this.teslaUsername);
        postBody.put("password", this.teslaPassword);

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
