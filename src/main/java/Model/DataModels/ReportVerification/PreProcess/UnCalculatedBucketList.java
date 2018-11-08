
package Model.DataModels.ReportVerification.PreProcess;

import Model.DataModels.Datapoints.EnumResolutions;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public final class UnCalculatedBucketList {
    
    private final EnumCalcPointFilter bucketListType;
    private final EnumResolutions bucketResolution;
    private final int numOfDays;
    private final Map< DateTime, UnCalculatedBucket> bucketDateToBucketMap;
    private final PreProcessMaps ppMaps;
    private final DateTimeZone siteTimeZone;
    
    
    private final Map< Integer, DateTime> rowIndexToTimestampMap;
    private final Map< Integer, Integer> rowIndexToBucketNumber;
    private final Map< Integer, UnCalculatedBucket> bucketNumberToBucketMap;
    private final Map< DateTime, DateTime> tsToBucketTsMap;
    private final Map< DateTime, Integer> bucketTimestampToRowIndexMap;
    private final List< DateTime> bucketDates;
    
    public UnCalculatedBucketList( 
            EnumCalcPointFilter bucketListType,
            EnumResolutions bucketResolution,
            int numOfDays,
            DateTimeZone siteTimeZone,
            PreProcessMaps ppMaps ){
        
        this.bucketListType = bucketListType;
        this.bucketDateToBucketMap = new HashMap<>();
        this.ppMaps = ppMaps;
        this.siteTimeZone = siteTimeZone;
        this.bucketResolution = bucketResolution;
        this.numOfDays = numOfDays;
        
        rowIndexToTimestampMap = new HashMap<>();
        rowIndexToBucketNumber = new HashMap<>();
        bucketNumberToBucketMap = new HashMap<>();
        tsToBucketTsMap = new HashMap<>();
        bucketDates = new ArrayList<>();
        bucketTimestampToRowIndexMap = new HashMap<>();

        DateTime currBucketDate = null;
        int currBucketStartingRow = 0;
        Map< DateTime, Map<String, Object>> currentimestampToPointNameValueMap = null;
        List<DateTime> currBucketTimestamps = null;

        int bucketNumber = 0;
        int rowIndex = 0;
        for (String timeStamp : ppMaps.getMasterListOfTimestamps()) {
            DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            DateTime ts = DateTime.parse(timeStamp, dateFormatter).withZone(siteTimeZone);
            
            rowIndexToTimestampMap.put(rowIndex, ts);

            if (currBucketDate == null) {
                currBucketDate = ts;
                currBucketTimestamps = new ArrayList<>();
                currentimestampToPointNameValueMap = new HashMap<>();
            }

            int totalHoursInBucket = getHoursFromResAndDays();
            if (ts.isAfter(currBucketDate.plusMinutes((totalHoursInBucket * 60) - 5))) {
                
                UnCalculatedBucket bucket = new UnCalculatedBucket(
                        currBucketDate,
                        ppMaps.getUNames(),
                        currBucketTimestamps, 
                        currentimestampToPointNameValueMap);
                
                bucketDateToBucketMap.put(currBucketDate, bucket);
                bucketDates.add(currBucketDate);
                bucketNumberToBucketMap.put(bucketNumber, bucket);
                bucketTimestampToRowIndexMap.put(currBucketDate, currBucketStartingRow);
                currBucketStartingRow += bucket.getBucketTimestamps().size();

                currBucketTimestamps = new ArrayList<>();
                currentimestampToPointNameValueMap = new HashMap<>();
                currBucketDate = currBucketDate.plusHours( getHoursFromResAndDays() );
                bucketNumber++;
            }

            currBucketTimestamps.add(ts);
            tsToBucketTsMap.put(ts, currBucketDate);
            rowIndexToBucketNumber.put(rowIndex, bucketNumber);

            rowIndex++;

            Map<String, Object> pointNameAndSidToValueMap = ppMaps.getTimestampToUNameValueMap().get(timeStamp);
            currentimestampToPointNameValueMap.put(ts, pointNameAndSidToValueMap);

        }

        if (currBucketTimestamps != null && currBucketTimestamps.size() > 0) {
            UnCalculatedBucket bucket = new UnCalculatedBucket(currBucketDate, ppMaps.getUNames(), currBucketTimestamps, currentimestampToPointNameValueMap);
            bucketDateToBucketMap.put(currBucketDate, bucket);
            bucketDates.add(currBucketDate);
            bucketNumberToBucketMap.put(bucketNumber, bucket);
            bucketTimestampToRowIndexMap.put(currBucketDate, currBucketStartingRow);
        }

    }
    
    public int getHoursFromResAndDays(){
        switch( bucketResolution ){
            case MINUTE:
                return 1;
            case MINUTE5:
                return 1;
            case HOUR:
                return 1;
            case MONTH:
                return numOfDays * 24;
            case YEAR:
                return numOfDays * 24;
            case DAY:
                return 24;
            case WEEK:
                return 7 * 24;
            default:
                return 1;
        }
    }
    
    
    public EnumCalcPointFilter getBucketListType(){
        return bucketListType;
    }
    
    public UnCalculatedBucket getBucketFromBucketTimestamp( DateTime bucketTimestamp ){
        return bucketDateToBucketMap.get(bucketTimestamp);
    }
    
    public List<String> getPointNamesAndSids(){
        return ppMaps.getUNames();
        
    }
    
    public DateTimeZone getTZOffSet(){
        return siteTimeZone;
    }
    
    public EnumResolutions getBucketResolution(){
        return bucketResolution;
    }

    public Map< DateTime, UnCalculatedBucket> getBucketDateToBucketMap(){
        return bucketDateToBucketMap;
    }
    
    public Map< Integer, DateTime> getRowIndexToTimestampMap(){
        return rowIndexToTimestampMap;
    }
    
    public Map< Integer, Integer> getRowIndexToBucketNumber(){
        return rowIndexToBucketNumber;
    }
    
    public Map< Integer, UnCalculatedBucket> getBucketNumberToBucketMap(){
        return bucketNumberToBucketMap;
    }
    
    public Map< DateTime, DateTime> getTimestampToBucketTimestampMap(){
        return tsToBucketTsMap;
    }
    
    public Map< DateTime, Integer> getbucketTimestampToRowIndexMap(){
        return bucketTimestampToRowIndexMap;
    }
    
    public List< DateTime> getbucketDates(){
        return bucketDates;
    }
    
    public Map<String, UnCalculatedPoint> getPointNameAndSidToRVDatapointMap() {
        return ppMaps.getUNameToRVDatapointMap();
    }
    
}
