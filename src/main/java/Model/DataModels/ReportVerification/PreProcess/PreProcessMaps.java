package Model.DataModels.ReportVerification.PreProcess;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreProcessMaps {

    private final List< String> uNames;
    private final Map<String, UnCalculatedPoint> uNameToRVDatapointMap;
    private final Map< String, Map<String, Object>> uNameToTimestampsAndVaulesMap;

    private final List<String> masterListOfTimestamps;
    private final Map< String, Map<String, Object>> timestampToUNameValueMap;

    public PreProcessMaps(
            List<DatapointHistoriesResponse> listOfDatapointHistories,
            Map<String, UnCalculatedPoint> uNameToRVDatapointMap) {
        this.uNames = new ArrayList<>();
        this.uNameToRVDatapointMap = uNameToRVDatapointMap;
        uNameToTimestampsAndVaulesMap = new HashMap<>();

        masterListOfTimestamps = new ArrayList<>();

        for (DatapointHistoriesResponse resp : listOfDatapointHistories) {

            String uName = getUName(resp.getSid(), resp.getName());
            uNames.add(uName);

            Map<String, Object> timeStampToValueMap = new HashMap<>();

            int index = 0;
            for (String ts : resp.getTimestamps()) {
                if (!masterListOfTimestamps.contains(ts)) {
                    masterListOfTimestamps.add(ts);
                }
                if (index < resp.getValues().size()) {
                    timeStampToValueMap.put(ts, resp.getValues().get(index));
                } else {
                    timeStampToValueMap.put(ts, "?");
                    System.out.println("# timestamps different from # of values");
                }
                index++;
            }
            uNameToTimestampsAndVaulesMap.put(uName, timeStampToValueMap);

        }

        Collections.sort(masterListOfTimestamps);

        timestampToUNameValueMap = new HashMap<>();

        //make sure that for eah timestamp, every point has a value
        for (String ts : masterListOfTimestamps) {

            if (!timestampToUNameValueMap.containsKey(ts)) {
                timestampToUNameValueMap.put(ts, new HashMap<String, Object>());
            }

            Map<String, Object> pointNameAndSidToValue = timestampToUNameValueMap.get(ts);
            for (String uName : uNames) {
                if (!pointNameAndSidToValue.containsKey(uName)) {
                    pointNameAndSidToValue.put(uName, "?");
                }

                Map<String, Object> timestampToValue = uNameToTimestampsAndVaulesMap.get(uName);

                if (timestampToValue.containsKey(ts)) {
                    pointNameAndSidToValue.put(uName, timestampToValue.get(ts));
                }
            }
        }

    }

    private String getUName(String sid, String name) {
        String uName = name;
        String[] pieces = sid.split("\\.");

        if (pieces.length > 2) {
            uName = pieces[2] + "." + uName;
        }
        return uName;
    }

    public List< String> getUNames() {
        return uNames;
    }

    public List<String> getMasterListOfTimestamps() {
        return masterListOfTimestamps;
    }

    public Map< String, Map<String, Object>> getUNameToTimestampsAndVaulesMap() {
        return uNameToTimestampsAndVaulesMap;
    }

    public Map< String, Map<String, Object>> getTimestampToUNameValueMap() {
        return timestampToUNameValueMap;
    }

    public Map<String, UnCalculatedPoint> getUNameToRVDatapointMap() {
        return uNameToRVDatapointMap;
    }

}
