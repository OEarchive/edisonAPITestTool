package View.Sites.EditSite.A_History.ReportVerification.Chart;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointFilter;
import Model.DataModels.ReportVerification.PostProcess.CalculatedBucket;
import Model.DataModels.ReportVerification.PostProcess.CalculatedBucketList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

public class StampsAndPointsForChart {

    private final List<DateTime> timeStamps;
    private final Map<String, List<Object>> pointsMap;

    public StampsAndPointsForChart(CalculatedBucketList calculatedBucketList) {
        this.pointsMap = new HashMap<>();
        this.timeStamps = new ArrayList<>();

        List<DateTime> bucketDates = calculatedBucketList.getBucketDates();

        for (DateTime bucketTS : bucketDates) {

            CalculatedBucket bucket = calculatedBucketList.getBucketFromBucketTimestamp(bucketTS);
            Map<DateTime, Map<String, Object>> tsToPointNameAndValueMapMap = bucket.getTimestampToPointNameAndSidToValueMap();

            for (DateTime ts : bucket.getTimestampsInBucket()) {
                timeStamps.add( ts );
                Map<String, Object> pointNameToValueMap = tsToPointNameAndValueMapMap.get(ts);
                
                for (String pointName : pointNameToValueMap.keySet()) {
                    
                    EnumCalcPointFilter calcPointFilter = calculatedBucketList.getBucketListType();
                    if( !calcPointFilter.getCalculatedPointName().contentEquals(pointName)){
                        continue;
                    }
                    
                    if (!pointsMap.containsKey(pointName)) {
                        pointsMap.put(pointName, new ArrayList<Object>());
                    }

                    List<Object> listOfValues = pointsMap.get(pointName);
                    listOfValues.add(pointNameToValueMap.get(pointName));
                    pointsMap.put(pointName, listOfValues);
                }

            }

        }

    }

    public List<DateTime> getTimeStamps() {
        return this.timeStamps;
    }

    public Map getPointsMap() {
        return this.pointsMap;
    }
}
