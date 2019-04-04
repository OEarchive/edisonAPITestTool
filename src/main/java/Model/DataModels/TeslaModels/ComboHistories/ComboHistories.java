package Model.DataModels.TeslaModels.ComboHistories;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.TeslaModels.TeslaHistoryResults;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ComboHistories {

    //private final List<DatapointHistoriesResponse> edisonHistoryList;
    //private final TeslaHistoryResults teslaHistory;
    
     private DateTimeFormatter zzFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
     
     private final List<DateTime> timestamps;
     private final List<ComboHistoryPointNamePair> pointNamePairs;
     private final Map<DateTime, List<ComboHistoryPointValuePair>> timestampsToValuesMap;

    public ComboHistories(
        List<DatapointHistoriesResponse> datapointHistoriesResponse,
        TeslaHistoryResults historyResults) {
        //this.edisonHistoryList = datapointHistoriesResponse;
        //this.teslaHistory = historyResults;
        
        timestamps = new ArrayList<>();
        pointNamePairs = new ArrayList<>();
        timestampsToValuesMap = new HashMap<>();
        
        Map<String, Integer> edisonPointNameToIndexMap = new HashMap<>();
        
        List<String> edisonPointNames = new ArrayList<>();
       
        //Map<DateTime, List<>
        
       
        for( DatapointHistoriesResponse edison : datapointHistoriesResponse ){
            for( String ts : edison.getTimestamps()){
                DateTime timestamp = DateTime.parse(ts,zzFormat).withZone(DateTimeZone.UTC);
                if( !timestamps.contains(timestamp)){
                    timestamps.add(timestamp);
                }
            }
        }
        
        for(  DateTime teslaTimeStamp : historyResults.getTimestamps() ){
                if( !timestamps.contains(teslaTimeStamp)){
                    timestamps.add(teslaTimeStamp);
                }
        }
        
        Collections.sort(timestamps);
        
        for( DateTime ts : timestamps ){
            timestampsToValuesMap.put(ts, new ArrayList<ComboHistoryPointValuePair>());
        }
        
        
        
        
        ///===========
        int pointIndex = 0;
        for( DatapointHistoriesResponse edison : datapointHistoriesResponse ){
            String name = edison.getName();
            edisonPointNames.add(name);
            
            edisonPointNameToIndexMap.put(name, pointIndex++);
            
            List<String> edisonTimeStamps = edison.getTimestamps();
            List<Object> edisonValues = edison.getValues();
            
            int edisonValueIndex = 0;
            for( String ts : edisonTimeStamps ){
                DateTime timestamp = DateTime.parse(ts,zzFormat).withZone(DateTimeZone.UTC);
                if(!timestamps.contains(timestamp)){
                    timestamps.add(timestamp);
                }
                
                ComboHistoryPointValuePair valuePair = new ComboHistoryPointValuePair();
                valuePair.setEdisonValue( edisonValues.get(edisonValueIndex));
                
                List<ComboHistoryPointValuePair> listOfValuePairs = timestampsToValuesMap.get( timestamp);
                listOfValuePairs.add(valuePair);
                
                
                edisonValueIndex++;
            }
           
        }
        
        
         List<String> teslaPointNames = historyResults.getPointNames();
         List<DateTime> teslaTimeStamps = historyResults.getTimestamps();
        historyResults.getTimeStampToValuesArray();
        
        
        
        for( DateTime teslaTimeStamp : teslaTimeStamps ){
            
            //List<ComboHistoryPointValuePair> listOfValuePairs = timestampsToValuesMap.get(timestamp);
            
            //timestampsToValuesMap
                
                
        }
        
        
        
     
        for( String teslaName : historyResults.getPointNames()){
            
            int valuePairIndex = edisonPointNameToIndexMap.get( teslaName );
            
            
        }
        
        
    }
    
    
    
}
