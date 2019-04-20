
package Model.DataModels.TeslaModels;

import Model.DataModels.TeslaModels.ComboHistories.ComboHistories;
import Model.DataModels.TeslaModels.ComboHistories.ComboHistoryPointNamePair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

public class TeslaHistoryStats {
    
    private final Map< String, TeslaDataPointStatistics> pointNameToStatsMap;
    private final List<String> pointNames;

    
    public TeslaHistoryStats( ComboHistories historyResults ){
        
        this.pointNameToStatsMap = new HashMap<>();
        
        
        pointNames = historyResults.getFlatPointNames();

        Map<String, List<Object> > uNameToValuesMap = new HashMap<>();
        int pointIndex = 0;
        
        for( String pointName : pointNames ){
            
            List<Object> values = new ArrayList<>();
            for( DateTime ts : historyResults.getTimestamps() ){
                List<Object> valuesAtThisTime = historyResults.getFlatTimestampsToValuesMap().get(ts);
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
