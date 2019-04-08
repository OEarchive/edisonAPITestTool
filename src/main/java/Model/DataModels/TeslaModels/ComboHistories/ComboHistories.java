package Model.DataModels.TeslaModels.ComboHistories;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.TeslaModels.TeslaHistoryResults;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ComboHistories {

    private DateTimeFormatter zzFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");

    private final List<DateTime> timestamps;
    private final List<ComboHistoryPointNamePair> pointNamePairs;
    private final Map<DateTime, List<ComboHistoryPointValuePair>> timestampsToValuesMap;

    public ComboHistories(
            List<DatapointHistoriesResponse> datapointHistoriesResponse,
            TeslaHistoryResults historyResults) {

        timestamps = new ArrayList<>();
        pointNamePairs = new ArrayList<>();
        timestampsToValuesMap = new HashMap<>();

        List<String> tempEdisonNames = new ArrayList<>();
        Map<String, Integer> edisonPointNameToIndexMap = new HashMap<>();

        Map< DateTime, List<Object>> tempEdisonTimeStampToValuesMap = new HashMap<>();

        int edisonDataPointIndex = 0;
        for (DatapointHistoriesResponse edison : datapointHistoriesResponse) {

            tempEdisonNames.add(edison.getName());

            edisonPointNameToIndexMap.put(edison.getName(), edisonDataPointIndex);

            for (String ts : edison.getTimestamps()) {
                DateTime timestamp = DateTime.parse(ts, zzFormat).withZone(DateTimeZone.UTC);
                if (!timestamps.contains(timestamp)) {
                    timestamps.add(timestamp);
                }
            }
            edisonDataPointIndex++;
        }

        edisonDataPointIndex = 0;
        for (DatapointHistoriesResponse edison : datapointHistoriesResponse) {

            int timeStampIndex = 0;
            for (String ts : edison.getTimestamps()) {

                DateTime timestamp = DateTime.parse(ts, zzFormat).withZone(DateTimeZone.UTC);

                if (!tempEdisonTimeStampToValuesMap.containsKey(timestamp)) {
                    List<Object> vals = new ArrayList<>();
                    for (String name : tempEdisonNames) {
                        vals.add(null);
                    }
                    tempEdisonTimeStampToValuesMap.put(timestamp, vals);

                }
                List<Object> vals = tempEdisonTimeStampToValuesMap.get(timestamp);
                vals.set(edisonDataPointIndex, edison.getValues().get(timeStampIndex));
                timeStampIndex++;

            }
            edisonDataPointIndex++;
        }

        for (DateTime teslaTimeStamp : historyResults.getTimestamps()) {
            if (!timestamps.contains(teslaTimeStamp)) {
                timestamps.add(teslaTimeStamp);
            }
        }

        //create the names pair ====================
        Map< String, Integer> teslaNameToIndexMap = new HashMap<>();

        for (String tempEdisonName : tempEdisonNames) {
            ComboHistoryPointNamePair pointNamePair = new ComboHistoryPointNamePair(tempEdisonName, null);
            pointNamePairs.add(pointNamePair);
        }

        int teslaPointIndex = 0;
        for (String tempTeslaName : historyResults.getPointNames()) {

            teslaNameToIndexMap.put(tempTeslaName, teslaPointIndex);
            teslaPointIndex++;

            if (!setTeslaNameIfThere(tempTeslaName)) {
                ComboHistoryPointNamePair pointNamePair = new ComboHistoryPointNamePair(null, tempTeslaName);
                pointNamePairs.add(pointNamePair);

            }
        }

        // set the values....
        for (DateTime ts : timestamps) {
            List<ComboHistoryPointValuePair> valuePairs = new ArrayList<>();
            for (ComboHistoryPointNamePair pointNamePair : pointNamePairs) {
                valuePairs.add(new ComboHistoryPointValuePair());
            }
            timestampsToValuesMap.put(ts, valuePairs);
        }

        for (DateTime ts : timestamps) {

            List<ComboHistoryPointValuePair> listOfValuePairs = new ArrayList<>();

            for (ComboHistoryPointNamePair pointNamePair : pointNamePairs) {

                //get the edsion value
                Object edisonValue = "?";
                if (tempEdisonTimeStampToValuesMap.containsKey(ts)) {
                    List<Object> pointValues = tempEdisonTimeStampToValuesMap.get(ts);
                    int edisonPointIndex = edisonPointNameToIndexMap.get(pointNamePair.getEdisonName());
                    edisonValue = pointValues.get(edisonPointIndex);
                }

                //get the tesla value
                Object teslaValue = "?";
                if (historyResults.getTimeStampToValuesArray().containsKey(ts)) {
                    List<Object> teslaValues = historyResults.getTimeStampToValuesArray().get(ts);
                    int teslaPointIndex2 = teslaNameToIndexMap.get(pointNamePair.getTeslaName());
                    teslaValue = teslaValues.get(teslaPointIndex2);
                }

                // set the values
                ComboHistoryPointValuePair valuePair = new ComboHistoryPointValuePair();
                valuePair.setEdisonValue(edisonValue);
                valuePair.setTeslaValue(teslaValue);
                listOfValuePairs.add(valuePair);

            }

            timestampsToValuesMap.put(ts, listOfValuePairs);
        }
    }

    private boolean setTeslaNameIfThere(String teslaName) {
        for (ComboHistoryPointNamePair pnp : pointNamePairs) {
            if (pnp.getEdisonName().contentEquals(teslaName)) {
                pnp.setTeslaName(teslaName);
                return true;
            }
        }
        return false;
    }

    public List<DateTime> getTimestamps() {
        return this.timestamps;
    }

    public List<ComboHistoryPointNamePair> getPointNamePairs() {
        return this.pointNamePairs;
    }

    public Map<DateTime, List<ComboHistoryPointValuePair>> getTimestampsToValuesMap() {
        return this.timestampsToValuesMap;
    }

    public List<String> getAllPointNames() {

        List<String> allPointNames = new ArrayList<>();

        for (ComboHistoryPointNamePair namePair : pointNamePairs) {
            allPointNames.add(namePair.getEdisonName());
            allPointNames.add(namePair.getTeslaName());
        }

        return allPointNames;
    }

    //historyResults.getTimeStampToValuesArray().get(timeStamp);
    public Map< DateTime, List<Object>> getTimeStampToAllValuesArray() {

        Map< DateTime, List<Object>> allValues = new HashMap<>();

        for (DateTime ts : this.timestamps) {
            List<Object> values = new ArrayList<>();
            List<ComboHistoryPointValuePair> valuePairs = timestampsToValuesMap.get(ts);

            for (ComboHistoryPointValuePair vp : valuePairs) {
                values.add(vp.getEdisonValue());
                values.add(vp.getTeslaValue());

            }

            allValues.put(ts, values);
        }

        return allValues;
    }

}
