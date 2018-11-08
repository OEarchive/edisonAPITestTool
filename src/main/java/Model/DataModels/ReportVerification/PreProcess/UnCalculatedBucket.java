package Model.DataModels.ReportVerification.PreProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

public class UnCalculatedBucket {

    private final DateTime bucketTimestamp;

    private final Map< DateTime, Map<String, Object>> timestampToUNameValueMap;
    private final List<String> uNames; //cols
    private final List<DateTime> bucketTimestamps; //row indicies

    public UnCalculatedBucket(DateTime bucketTimestamp,
            List<String> uNames,
            List<DateTime> masterListOfTimestamps,
            Map< DateTime, Map<String, Object>> timestampToUNameValueMap) {

        this.bucketTimestamp = bucketTimestamp;
        this.uNames = uNames;
        this.bucketTimestamps = masterListOfTimestamps;
        this.timestampToUNameValueMap = timestampToUNameValueMap;

    }

    public DateTime getTimestamp() {
        return bucketTimestamp;
    }

    public List<DateTime> getBucketTimestamps() {
        return bucketTimestamps;
    }

    public Map< DateTime, Map<String, Object>> getTimestampToUNameAndValueMap() {
        return timestampToUNameValueMap;
    }

    public List<String> getUNames() {
        return uNames;
    }

    public Map< String, List<Object>> getUNameToListOfValuesMap() {
        Map< String, List<Object>> uNameToListOfValuesMap = new HashMap<>();

        for (DateTime ts : bucketTimestamps) {
            for (String psn : uNames) {
                if (!uNameToListOfValuesMap.containsKey(psn)) {
                    uNameToListOfValuesMap.put(psn, new ArrayList<>());
                }
                uNameToListOfValuesMap.get(psn).add(timestampToUNameValueMap.get(ts).get(psn));

            }
        }

        return uNameToListOfValuesMap;
    }

}
