
package Model.DataModels.ReportVerification.PostProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

public final class CalculatedBucket {
    
    private final DateTime bucketTimestamp;
    
    private Map< DateTime, Map<String, Object>> newTimestampToPointNameAndSidToValueMap;
    private final List<String> newPointNamesAndSids; //cols
    private final List<DateTime> timestampsInBucket; //row indicies
    
    
    public CalculatedBucket( 
            DateTime bucketTimestamp, 
            List<String> newPointNamesAndSids, 
            List<DateTime> timestampsInBucket, 
            Map< DateTime, Map<String, Object>> oldTimestampToPointNameAndSidToValueMap  ){
        
        this.bucketTimestamp = bucketTimestamp;
        this.newPointNamesAndSids = newPointNamesAndSids;
        this.timestampsInBucket = timestampsInBucket;
        
        this.newTimestampToPointNameAndSidToValueMap = oldTimestampToPointNameAndSidToValueMap;
        
    }
    
    public DateTime getBucketTS(){
        return bucketTimestamp;
    }
    
    public List<DateTime> getTimestampsInBucket(){
        return timestampsInBucket;
    }
    
    public List<String> getNewPointNamesAndSids(){
        return newPointNamesAndSids;
    }
    

    public Map< DateTime, Map<String, Object>> getTimestampToPointNameAndSidToValueMap(){
        return newTimestampToPointNameAndSidToValueMap;
    }
    
    public Map< String, List<Object>> getPointNameAndSidToListOfValuesMap(){
        Map< String, List<Object>> pointNameAndSidToListOfValuesMap = new HashMap<>();
        
        for( DateTime ts : timestampsInBucket ){
            for( String pointNameAndSid : newPointNamesAndSids ){
                if( !pointNameAndSidToListOfValuesMap.containsKey(pointNameAndSid) ){
                    pointNameAndSidToListOfValuesMap.put(pointNameAndSid, new ArrayList<Object>());
                }
                pointNameAndSidToListOfValuesMap.get(pointNameAndSid).add(newTimestampToPointNameAndSidToValueMap.get(ts).get(pointNameAndSid));
                
            }
        }
        
        return pointNameAndSidToListOfValuesMap;
    }
   
}