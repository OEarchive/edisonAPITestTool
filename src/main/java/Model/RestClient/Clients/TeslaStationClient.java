package Model.RestClient.Clients;

import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaGenEquipment;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostCustomer;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostEquipResponse;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostSite;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostStation;
import Model.DataModels.TeslaModels.EnumTeslaBaseURLs;
import Model.DataModels.TeslaModels.TeslaDPServiceDatapoint;
import Model.DataModels.TeslaModels.TeslaDataPointUpsertRequest;
import Model.DataModels.TeslaModels.TeslaHistoryRequest;
import Model.DataModels.TeslaModels.TeslaLiveDataPoint;
import Model.DataModels.TeslaModels.TeslaStationInfo;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TeslaStationClient {

    private TeslaRestClientCommon teslaRestClient;
    private EnumTeslaBaseURLs baseURL;

    public TeslaStationClient(EnumTeslaBaseURLs baseURL, TeslaRestClientCommon teslaRestClient) {
        this.teslaRestClient = teslaRestClient;
        this.baseURL = baseURL;
    }

    public void setTeslaBaseURL(EnumTeslaBaseURLs baseURL) {
        this.baseURL = baseURL;
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

    public OEResponse getTeslaStationDatapoints(String stationID) throws IOException {

        String url = baseURL.getURL() + "/stations/" + stationID + "/data-points";
        OEResponse resObj = teslaRestClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, new TypeReference<List<TeslaDPServiceDatapoint>>() {
            });
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

    // site creation ============
    public OEResponse postCustomer(TeslaPostCustomer postCustomer) throws JsonProcessingException, IOException {

        String url = baseURL.getURL() + "/customers";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(postCustomer);
        OEResponse resObj = teslaRestClient.doPostAndGetBody(url, payload);

        //Created{"id":"17a8c5e2-67de-4534-b652-a0f12b923b42","name":"skdkskdks","salesforceId":null,"createdAt":"2018-12-05T15:36:59.413Z","updatedAt":"2018-12-05T15:36:59.413Z"}
        if (resObj.responseCode == 201) {
            String response = (String) resObj.responseObject;
            response = response.substring("Created".length(), response.length());
            JsonNode jsonNode = mapper.readTree(response);
            Map<String, Object> map = mapper.convertValue(jsonNode, Map.class);
            resObj.responseObject = map;
        }
        return resObj;

    }

    public OEResponse postSite(String customerId, TeslaPostSite postSite) throws JsonProcessingException, IOException {

        String url = baseURL.getURL() + "/customers/" + customerId + "/sites";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(postSite);
        OEResponse resObj = teslaRestClient.doPostAndGetBody(url, payload);

        if (resObj.responseCode == 201) {
            String response = (String) resObj.responseObject;
            response = response.substring("Created".length(), response.length());
            JsonNode jsonNode = mapper.readTree(response);
            Map<String, Object> map = mapper.convertValue(jsonNode, Map.class);
            resObj.responseObject = map;
        }
        return resObj;

    }

    public OEResponse postStation(String siteId, TeslaPostStation postStation) throws JsonProcessingException, IOException {

        String url = baseURL.getURL() + "/sites/" + siteId + "/stations";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(postStation);
        OEResponse resObj = teslaRestClient.doPostAndGetBody(url, payload);

        if (resObj.responseCode == 201) {
            String response = (String) resObj.responseObject;
            response = response.substring("Created".length(), response.length());
            JsonNode jsonNode = mapper.readTree(response);
            Map<String, Object> map = mapper.convertValue(jsonNode, Map.class);
            resObj.responseObject = map;
        }
        return resObj;

    }

    public OEResponse postEquipmentList(String stationId, TeslaGenEquipment equip) throws JsonProcessingException, IOException {
        String url = baseURL.getURL() + "/stations/" + stationId + "/equipment";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(equip);
        OEResponse resObj = teslaRestClient.doPostAndGetBody(url, payload);

        if (resObj.responseCode == 201) {
            String response = (String) resObj.responseObject;
            response = response.substring("Created".length(), response.length());
            
            resObj.responseObject = mapper.readValue(response, TeslaPostEquipResponse.class);
            //JsonNode jsonNode = mapper.readTree(response);
            //Map<String, Object> map = mapper.convertValue(jsonNode, Map.class);
            //resObj.responseObject = map;
        }
        return resObj;

    }

}
