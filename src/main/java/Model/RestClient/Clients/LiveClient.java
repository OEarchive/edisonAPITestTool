package Model.RestClient.Clients;

import Model.DataModels.Live.Subscriptions.CreateSubscriptionRequest;
import Model.DataModels.Live.Subscriptions.CreateSubscriptionResponse;
import Model.DataModels.Live.GetLiveData.GetLiveDataResponse;
import Model.DataModels.Live.PostLiveData.PostLiveDataRequest;
import Model.DataModels.Live.PostLiveData.PostLiveDataResponse;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class LiveClient {

    private String serviceURL;
    private final RestClientCommon restClient;

    public LiveClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }

    public void setServiceURLAndToken(String serviceURL, String accessToken) {
        this.serviceURL = serviceURL;
        restClient.setOauthToken(accessToken);
    }

    public OEResponse getLiveData(String subscriptionId) throws IOException {

        String url = serviceURL + "/live?subscriptionId=" + subscriptionId;

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, GetLiveDataResponse.class);
        }

        return resObj;
    }

    public OEResponse postNewSubscription(CreateSubscriptionRequest req) throws IOException {

        String url = serviceURL + "/live";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);

        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, CreateSubscriptionResponse.class);
        }

        return resObj;
    }

    public OEResponse postLiveData(String token, String stationId, PostLiveDataRequest req) throws IOException {

        String url = serviceURL + "/stations/live" + "?stationId=" + stationId;

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);

        OEResponse resObj;
        if (token.length() > 0) {
            resObj = restClient.doPostAndGetBody(url, payload, token, true);
        } else {
            resObj = restClient.doPostAndGetBody(url, payload, true);
        }

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, PostLiveDataResponse.class);
        }

        return resObj;
    }

}
