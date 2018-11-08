package Model.RestClient.Clients;

import Model.DataModels.Alarms.AlarmListRequest;
import Model.DataModels.Alarms.AlarmsHistoryQueryParams;
import Model.DataModels.Alarms.AlarmsHistoryResponse;
import Model.DataModels.Alarms.AssociateAlarmReq;
import Model.DataModels.Alarms.CreateAlarmRequest;
import Model.DataModels.Alarms.CreateAlarmResponse;
import Model.DataModels.Alarms.TriggerAlarmResponse;
import Model.DataModels.Alarms.TriggerOrClearAlarmRequest;
import Model.DataModels.Alarms.UpdateAlarmRequest;
import Model.DataModels.Alarms.SiteAlarm;
import Model.DataModels.Alarms.SiteAlarms;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class AlarmsClient {

    private String serviceURL;
    private final RestClientCommon restClient;

    public AlarmsClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }

    public void setServiceURLAndToken(String serviceURL, String accessToken) {
        this.serviceURL = serviceURL;
        restClient.setOauthToken(accessToken);
    }

    /*
    public OEResponse getAlarmList(String sid, AlarmListRequest alarmList) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms" + alarmList.getQueryParamsString();

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, SiteAlarms.class);
        }

        return resObj;
    }

    public OEResponse postNewAlarm(String sid, CreateAlarmRequest req) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, CreateAlarmResponse.class);
        }

        return resObj;
    }

    public OEResponse associateAlarm(String sid, AssociateAlarmReq req) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms/associate";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        return resObj;
    }

    public OEResponse deleteAlarm(String sid, String name) throws IOException {
        String url = serviceURL + "/graph/" + sid + "/alarms/" + name;
        OEResponse resObj = restClient.doDelete(url);
        return resObj;
    }

    public OEResponse getAlarmDetails(String sid, String name, AlarmListRequest req) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms/" + name + "/" + req.getQueryParamsString();

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, SiteAlarm.class);
        }

        return resObj;
    }

    public OEResponse postTriggerOrClearAlarm(String siteSid, String alarmSid, String name, TriggerOrClearAlarmRequest req) throws IOException {

        String url = serviceURL + "/graph/" + alarmSid + "/alarms/" + siteSid;

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, TriggerAlarmResponse.class);
        }

        return resObj;
    }

    public OEResponse updateAlarmMetadata(String sid, String name, UpdateAlarmRequest req) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms/" + name;

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        return resObj;
    }


    public OEResponse getAlarmHistory(String sid, AlarmsHistoryQueryParams queryParams) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms/history" + queryParams.getQueryParamsString();
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, AlarmsHistoryResponse.class);
        }

        return resObj;
    }


    public OEResponse getSpecificAlarmHistory(String sid, String name) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms/" + name + "/history";
        OEResponse resObj = restClient.getResponse(url);
        
        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, AlarmsHistoryResponse.class);
        }

        return resObj;
    }

    public OEResponse getAlarmNotes(String sid, String name) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms/" + name + "/notes";

        OEResponse resObj = restClient.getResponse(url);

        return resObj;
    }

    //TODO - What's a note look like?
    public OEResponse postAlarmNote(String sid, String name, String note) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms/" + name;

        ObjectMapper mapper = new ObjectMapper();
        //String payload = mapper.writeValueAsString(note);

        OEResponse resObj = restClient.doPostAndGetBody(url, note, true);

        return resObj;
    }

    public OEResponse deleteAlarmNote(String sid, String name, String note_id) throws IOException {

        String url = serviceURL + "/graph/" + sid + "/alarms/" + name + "/notes/" + note_id;

        OEResponse resObj = restClient.doDelete(url);

        return resObj;
    }

    */

}
