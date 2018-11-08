package View.Sites.EditSite.A_History.DPHistoryChart;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StampsAndPoints {

    private final List<String> timeStamps;
    private final Map<String, List<Object>> pointsMap;

    public StampsAndPoints( List<DatapointHistoriesResponse> historyQueryResponse ){
        this.timeStamps = historyQueryResponse.get(0).getTimestamps();
        this.pointsMap = new HashMap<>();
        
        for( DatapointHistoriesResponse resp : historyQueryResponse ){
            pointsMap.put(resp.getName(), resp.getValues());
        }
    }
  
    public List<String> getTimeStamps() {
        return this.timeStamps;
    }

    public Map getPointsMap() {
        return this.pointsMap;
    }
}
