package Model.DataModels.TeslaModels;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.TeslaModels.ComboHistories.ComboHistories;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

public class TeslaStampsAndPoints {

    private final List<DateTime> timeStamps;
    private final Map<String, List<Object>> pointsMap;

    public TeslaStampsAndPoints(ComboHistories historyResults) {
        this.timeStamps = historyResults.getTimestamps();
        this.pointsMap = new HashMap<>();

        int pointIndex = 0;
        for (String pointName : historyResults.getFlatPointNames()) {
            List<Object> values = new ArrayList<>();
            for (DateTime ts : historyResults.getTimestamps()) {
                values.add(historyResults.getFlatTimestampsToValuesMap().get(ts).get(pointIndex));
            }

            pointsMap.put(pointName, values);
            pointIndex++;
        }
    }

    public List<DateTime> getTimeStamps() {
        return this.timeStamps;
    }
    
    public List<String> getTimeStampsAsStrings() {
        
        List<String> timeStampStrs = new ArrayList<>();
        
        for( DateTime ts : this.timeStamps){
            timeStampStrs.add(ts.toString());
        }
        
        return timeStampStrs;
    }
    
    

    public Map getPointsMap() {
        return this.pointsMap;
    }
}
