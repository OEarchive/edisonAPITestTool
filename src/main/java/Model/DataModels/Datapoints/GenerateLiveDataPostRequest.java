/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DataModels.Datapoints;

import Model.DataModels.Live.PostLiveData.PostLiveDataRequest;
import Model.DataModels.Live.PostLiveData.PostLiveDataRequestData;
import Model.DataModels.Live.PostLiveData.PostLiveDataRequestDebugInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author hal
 */
public class GenerateLiveDataPostRequest {
    private final String sendingSid;
    private final String stationId;
    private final Map<String, Object> pointNamesAndValues;

    public GenerateLiveDataPostRequest(String sendingSid, String stationId, Map<String, Object> pointNamesAndValues) {
        this.sendingSid = sendingSid;
        this.stationId = stationId;
        this.pointNamesAndValues = pointNamesAndValues;
    }

    public PostLiveDataRequest getPostLiveDataRequest() {

        PostLiveDataRequest req = new PostLiveDataRequest();

        DateTime triggerTimestamp = DateTime.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        String timestamp = triggerTimestamp.toString(fmt) + ".000Z";

        req.setTimestamp(timestamp);

        req.setSendingSid(sendingSid);

        req.setData(getData(timestamp));

        return req;

    }

    private List<PostLiveDataRequestData> getData(String timestamp) {

        PostLiveDataRequestData data = new PostLiveDataRequestData();

        data.setSid(sendingSid);
        data.setStationId(stationId);
        data.setPointScope("All");

        List<Object> pointValues = new ArrayList<>();
        List<String> pointNames = new ArrayList<>();

        for ( String pointName : pointNamesAndValues.keySet() ) {
            pointNames.add(pointName);
            pointValues.add(pointNamesAndValues.get( pointName));
        }

        data.setPointNames(pointNames);
        data.setPointValues(pointValues);

        data.setDebugInfo(getDebugInfo());

        List<PostLiveDataRequestData> list = new ArrayList<>();

        list.add(data);
        return list;

    }

    private PostLiveDataRequestDebugInfo getDebugInfo() {

        PostLiveDataRequestDebugInfo debugInfo = new PostLiveDataRequestDebugInfo();

        debugInfo.setStalePointsCount(0);
        debugInfo.setStringPointsCount(0);
        debugInfo.setErrorPoints("not sure");
        debugInfo.setTotalPointsCount(0);

        return debugInfo;

    }

}
