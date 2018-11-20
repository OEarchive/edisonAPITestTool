package Model.RestClient.Clients;

import Model.DataModels.TeslaModels.EnumTeslaBaseURLs;
import Model.DataModels.TeslaModels.TeslaDataPointUpsertRequest;
import Model.DataModels.TeslaModels.TeslaHistoryRequest;
import Model.DataModels.TeslaModels.TeslaLiveDataPoint;
import Model.DataModels.TeslaModels.TeslaStationInfo;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

public class TeslaStationClient {

    //private String serviceURL;
    private TeslaRestClientCommon teslaRestClient;
    private EnumTeslaBaseURLs baseURL;

    public TeslaStationClient(EnumTeslaBaseURLs baseURL, TeslaRestClientCommon teslaRestClient) {
        this.teslaRestClient = teslaRestClient;
        this.baseURL = baseURL;
    }

    public void setTeslaBaseURL(EnumTeslaBaseURLs baseURL) {
        this.baseURL = baseURL;
        //teslaRestClient.setOauthToken(accessToken);
    }

    public OEResponse getStations() throws IOException {

        String url = baseURL.getURL() + "/stations";

        OEResponse resObj = teslaRestClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, new TypeReference<List<TeslaStationInfo>>() {
            });
        }

        return resObj;
    }

    public OEResponse getStationInfo(String stationID) throws IOException {

        String url = baseURL.getURL() + "/stations/" + stationID;
        OEResponse resObj = teslaRestClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, TeslaStationInfo.class);
        }

        return resObj;
    }

    public OEResponse getSubscribed(String stationID) throws IOException {

        String url = baseURL.getURL() + "/stations/" + stationID + "/data-points/subscribed";
        OEResponse resObj = teslaRestClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, new TypeReference<List<String>>() {
            });
        }

        return resObj;
    }

    public OEResponse getLiveData(List<String> dataPointIDs) throws IOException {

        String url = baseURL.getURL() + "/live-data/query";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(dataPointIDs);

        payload = "{ \"ids\" : " + payload + "}";

        OEResponse resObj = teslaRestClient.doPostAndGetBody(url, payload);

        if (resObj.responseCode == 200) {
            mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, new TypeReference<List<TeslaLiveDataPoint>>() {
            });
        }

        return resObj;
    }

    public OEResponse getHistory(TeslaHistoryRequest historyRequest) throws IOException {

        String url = baseURL.getURL() + "/data/query";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(historyRequest);

        OEResponse resObj = teslaRestClient.doPostAndGetBody(url, payload);

        if (resObj.responseCode == 200) {
            mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, new TypeReference<List<TeslaLiveDataPoint>>() {
            });
        }

        return resObj;
    }

    
    public OEResponse putHistory(TeslaDataPointUpsertRequest dur) throws JsonProcessingException, IOException {

        String url = baseURL.getURL() + "/data/upsert";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(dur.getListOfPoints());
        //String payload = mapper.writeValueAsString(dur);

        OEResponse resObj = teslaRestClient.doPostAndGetBody(url, payload);

        return resObj;

    }
    
}
