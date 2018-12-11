
package Model.DataModels.TeslaModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

public class TeslaHistoryStats {
    
    private final Map< String, TeslaDataPointStatistics> pointNameToStatsMap;
    private final List<String> pointNames;

    
    public TeslaHistoryStats( TeslaHistoryResults historyResults ){
        
        this.pointNameToStatsMap = new HashMap<>();
        this.pointNames = historyResults.getPointNames();
        
        Map<String, List<Object> > uNameToValuesMap = new HashMap<>();
        int pointIndex = 0;
        for( String pointName : historyResults.getPointNames() ){
            
            List<Object> values = new ArrayList<>();
            for( DateTime ts : historyResults.getTimestamps() ){
                Map<DateTime, List<Object>> ddd = historyResults.getTimeStampToValuesArray();
                List<Object> valuesAtThisTime = historyResults.getTimeStampToValuesArray().get(ts);
                values.add( valuesAtThisTime.get(pointIndex));
            }
            
            pointNameToStatsMap.put( pointName, new TeslaDataPointStatistics(values));
            pointIndex++;
        }
        
    }
    
    
    public List<String> getPointNames(){
        return this.pointNames;
    }
    
    public TeslaDataPointStatistics getDataPointStatistics( String uName ){
        return pointNameToStatsMap.get(uName );
    }
    
}
