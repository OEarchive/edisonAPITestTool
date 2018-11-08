package View.Sites.EditSite.A_History.ReportVerification.C_RVGrandStatsTable;

import Model.DataModels.ReportVerification.PostProcess.CalculatedBucket;
import Model.DataModels.ReportVerification.PostProcess.CalculatedBucketList;
import Model.DataModels.Sites.SiteDatapoints.DatapointStatistics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

public class RVGrandStats {

    private final Map< String, DatapointStatistics> map;
    private final List<String> pointNamesAndSids;

    public RVGrandStats(CalculatedBucketList bucketList) {
        this.map = new HashMap<>();
        this.pointNamesAndSids = new ArrayList<>();

        Map<String, List<Object>> pointNamesToListOfGrandListOfValuesMap = new HashMap<>();
        
        for( String pointNameAndSid : bucketList.getUNames()){
            pointNamesAndSids.add(pointNameAndSid);
            pointNamesToListOfGrandListOfValuesMap.put(pointNameAndSid, new ArrayList<>());
        }
        
        for (DateTime bucketTimeStamp : bucketList.getBucketDates()) {
            CalculatedBucket bucket = bucketList.getBucketFromBucketTimestamp(bucketTimeStamp);

            for (String pointName : bucket.getNewPointNamesAndSids()) {
                pointNamesToListOfGrandListOfValuesMap.get(pointName).addAll(bucket.getPointNameAndSidToListOfValuesMap().get(pointName));
            }
        }
        
        for( String pointNameAndSid : bucketList.getUNames()){
            map.put(pointNameAndSid, new DatapointStatistics( pointNamesToListOfGrandListOfValuesMap.get(pointNameAndSid)));
        }
        
        
    }

    public List<String> getDataPointNamesAndSids() {
        return this.pointNamesAndSids;
    }

    public DatapointStatistics getDataPointStatistics(String pointNameAndSid) {
        return map.get(pointNameAndSid);
    }

}
