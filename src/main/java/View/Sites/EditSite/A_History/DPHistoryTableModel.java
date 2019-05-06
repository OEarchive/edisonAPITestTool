package View.Sites.EditSite.A_History;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Live.Subscriptions.SubscriptionResponseDatapoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DPHistoryTableModel extends AbstractTableModel {

    private final Map< String, Map<String, Object>> timestampToUNameValueMap;
    private final List<String> uNames; //cols
    private final List<String> masterListOfTimestamps; //row indicies

    public DPHistoryTableModel(List<DatapointHistoriesResponse> listOfDatapointHistories) {
        super();

        Map< String, Map<String, Object>> pointNameToTimestampsAndVaulesMap = new HashMap<>();
        masterListOfTimestamps = new ArrayList<>();

        uNames = new ArrayList<>();
        for (DatapointHistoriesResponse resp : listOfDatapointHistories) {

            String uName = getUName(resp);
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
            pointNameToTimestampsAndVaulesMap.put(uName, timeStampToValueMap);
        }

        Collections.sort(uNames);
        Collections.sort(masterListOfTimestamps);
        timestampToUNameValueMap = new HashMap<>();

        //make sure that for eah timestamp, every point has a value
        for (String ts : masterListOfTimestamps) {

            if (!timestampToUNameValueMap.containsKey(ts)) {
                timestampToUNameValueMap.put(ts, new HashMap<String, Object>());
            }

            Map<String, Object> pointNameToValue = timestampToUNameValueMap.get(ts);
            for (String point : uNames) {
                if (!pointNameToValue.containsKey(point)) {
                    pointNameToValue.put(point, "?");
                }

                Map<String, Object> timestampToValue = pointNameToTimestampsAndVaulesMap.get(point);

                if (timestampToValue.containsKey(ts)) {
                    pointNameToValue.put(point, timestampToValue.get(ts));
                }
            }
        }
    }

    private String getUName(DatapointHistoriesResponse resp) {
        return getUName(resp.getSid(), resp.getName());
    }

    //SubscriptionResponseDatapoint
    private String getUName(SubscriptionResponseDatapoint livePoint) {
        return getUName(livePoint.getSid(), livePoint.getName());
    }

    private String getUName(String sid, String name) {
        String uName = name;
        String[] pieces = sid.split("\\.");

        if (pieces.length > 2) {
            uName = pieces[2] + "." + uName;
        }
        return uName;
    }

    public Map< String, Map<String, Object>> getTimestampToPointNameValueMap() {
        return timestampToUNameValueMap;
    }

    public List<String> getUNames() {
        return this.uNames;
    }

    public List<String> getTimeStamps() {
        return this.masterListOfTimestamps;
    }

    @Override
    public int getRowCount() {
        return masterListOfTimestamps.size();
    }

    @Override
    public String getColumnName(int col) {
        if (col == 0) {
            return "ResultsDate";
        } else if (col == 1) {
            return "UTC";
        } else {
            return uNames.get(col - 2);
        }
    }

    @Override
    public int getColumnCount() {
        return uNames.size() + 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        if (columnIndex < 2) {
            val = masterListOfTimestamps.get(rowIndex);
        } else {
            String uName = uNames.get(columnIndex - 2);
            String ts = masterListOfTimestamps.get(rowIndex);

            if (timestampToUNameValueMap.containsKey(ts)) {
                Map<String, Object> uNameToValueMap = timestampToUNameValueMap.get(ts);
                val = uNameToValueMap.get(uName);
            } else {
                val = "oops";
            }
        }
        return val;
    }

    public void appendLiveData(String timestamp, List<SubscriptionResponseDatapoint> livePoints, int staleThreshold) {

        for (SubscriptionResponseDatapoint livePoint : livePoints) {
            String ts = livePoint.getTimestamp();

            DateTimeFormatter fromFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            //DateTimeFormatter toFormat = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
            //DateTimeFormatter resultFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
            DateTime temp = DateTime.parse(ts, fromFormat).withZone(DateTimeZone.UTC);
            temp = temp.withMillisOfSecond(0);
            ts = temp.toString(fromFormat);

            if (!masterListOfTimestamps.contains(ts)) {
                masterListOfTimestamps.add(ts);
            }

            if (!timestampToUNameValueMap.containsKey(ts)) {
                Map<String, Object> tempNameToValueMap = new HashMap<>();
                for (String uName : uNames) {
                    tempNameToValueMap.put(uName, "?");
                }
                timestampToUNameValueMap.put(ts, tempNameToValueMap);
            }
            Map<String, Object> pointNameToValueMap = timestampToUNameValueMap.get(ts);

            String uName = getUName(livePoint);
            pointNameToValueMap.replace(uName, livePoint.getValue());
        }
        Collections.sort(masterListOfTimestamps);
        fireTableDataChanged();
    }
}
