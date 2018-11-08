package Model.DataModels.ReportVerification.PostProcess;

import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointFilter;
import Model.DataModels.ReportVerification.CalcPointClassification.AssociatedPoints;
import Model.DataModels.ReportVerification.CalcPointClassification.CalculatedPointAndDependenciesFilters;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumDependentPointFilter;
import Model.DataModels.ReportVerification.PreProcess.UnCalculatedBucketList;
import Model.DataModels.ReportVerification.PreProcess.UnCalculatedPoint;
import Model.DataModels.ReportVerification.PreProcess.UnCalculatedBucket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.Hours;

public class CalculatedBucketList {


    private final Map<String, CalculatedBucketPoint> uNameToCalculatedBucketPointMap;

    private final Map< DateTime, CalculatedBucket> bucketTSToBucketMap;

    private final Map< Integer, DateTime> rowIndexToTimestampMap;
    private final Map< Integer, Integer> rowIndexToBucketNumber;

    private final Map< Integer, CalculatedBucket> bucketNumberToBucketMap;
    private final Map< DateTime, DateTime> tsToBucketTsMap;
    private final Map< DateTime, Integer> bucketTimestampToRowIndexMap;
    private final List< DateTime> bucketDates;

    private final List<String> uNamesInOrder;
    private final List< CalculatedBucketPoint> pointsInOrder;

    private EnumCalcPointFilter bucketListType = null;
    
    private final AssociatedPoints associatedPoints;

    public CalculatedBucketList(
            AssociatedPoints associatedPoints,
            Map<String, UnCalculatedPoint> pointNameAndSidToRVDatapointMap,
            UnCalculatedBucketList preCalculationBucketList) {

        this.associatedPoints = associatedPoints;
        bucketListType = preCalculationBucketList.getBucketListType();


        List<CalculatedBucketPoint> calcPoints = new ArrayList<>();
        List<CalculatedBucketPoint> deptPoints = new ArrayList<>();

        CalculatedPointAndDependenciesFilters pointAndDependenciesFilters = associatedPoints.getCaclulatedPointAndDependencies(bucketListType);
        List<EnumCalcPointFilter> calcPointFilters = pointAndDependenciesFilters.getOtherCalcPointFilters();
        List<EnumCalcPointFilter> partnerCalcPointFilters = pointAndDependenciesFilters.getPartnerCalcPointFilters();
        List<EnumDependentPointFilter> depPointFilters = pointAndDependenciesFilters.getDependentPointFilters();
        List<EnumDependentPointFilter> partnerDepPointFilters = pointAndDependenciesFilters.getPartnerBasePointFilters();


        pointAndDependenciesFilters.getPartnerCalcPointFilters();
        
        for (String pointNameAndSid : preCalculationBucketList.getPointNamesAndSids()) {

            UnCalculatedPoint rawPoint = pointNameAndSidToRVDatapointMap.get(pointNameAndSid);

            for (EnumCalcPointFilter cFilter : calcPointFilters) {

                if (associatedPoints.matchesCalcPointFilter(cFilter, rawPoint)) {

                    CalculatedBucketPoint cbp = new CalculatedBucketPoint(rawPoint, false);

                    for (EnumCalcPointFilter cPartner : partnerCalcPointFilters) {

                        if (associatedPoints.matchesCalcPointFilter(cPartner, rawPoint)) {
                            cbp.setHasPartner(true);

                        }
                    }
                    calcPoints.add(cbp);
                }

            }

            for (EnumDependentPointFilter dFilter : depPointFilters) {

                if (associatedPoints.matchesDependentPointFilter(dFilter, rawPoint)) {
                    CalculatedBucketPoint cbp = new CalculatedBucketPoint(rawPoint, false);
                    for (EnumDependentPointFilter dcFilter : partnerDepPointFilters) {
                        if (associatedPoints.matchesDependentPointFilter(dcFilter, rawPoint)) {
                            cbp.setHasPartner(true);

                        }
                    }
                    deptPoints.add(cbp);
                }
            }
        }

        this.uNamesInOrder = new ArrayList<>();
        this.pointsInOrder = new ArrayList<>();
        this.uNameToCalculatedBucketPointMap = new HashMap<>();

        for (CalculatedBucketPoint point : calcPoints) {
            pointsInOrder.add(point);
            
            String pns  = getUName( point.getSourcePoint().getSid(), point.getSourcePoint().getName() );
            uNameToCalculatedBucketPointMap.put(pns, point);
            uNamesInOrder.add(pns );

            if (point.hasPartner()) {
                String partnerName = "C_" + point.getSourcePoint().getName();
                String partner  = getUName(  point.getSourcePoint().getSid(), partnerName );
                pointsInOrder.add(point);
                uNameToCalculatedBucketPointMap.put(partner, point);
                uNamesInOrder.add(partner);
            }
        }

        for (CalculatedBucketPoint point : deptPoints) {
            pointsInOrder.add(point);
            
            String pns  = getUName( point.getSourcePoint().getSid(), point.getSourcePoint().getName() );
            
            uNameToCalculatedBucketPointMap.put(pns, point);
            uNamesInOrder.add(pns);
            
            if (point.hasPartner()) {
                String partnerName = "C_" + point.getSourcePoint().getName();
                String partner  = getUName( point.getSourcePoint().getSid(), partnerName );
                pointsInOrder.add(point);
                uNameToCalculatedBucketPointMap.put(partner, point);
                uNamesInOrder.add(partner);
            }
        }

        BucketListCalculator calculator = new BucketListCalculator(
                associatedPoints,
                uNamesInOrder,
                pointNameAndSidToRVDatapointMap,
                uNameToCalculatedBucketPointMap,
                this,
                bucketListType);

        bucketTSToBucketMap = new HashMap<>();
        bucketDates = new ArrayList<>();

        int bucketNumber = 0;
        int rowIndex = 0;
        this.bucketNumberToBucketMap = new HashMap<>();

        rowIndexToTimestampMap = new HashMap<>();
        rowIndexToBucketNumber = new HashMap<>();
        tsToBucketTsMap = new HashMap<>();
        bucketTimestampToRowIndexMap = new HashMap<>();

        for (DateTime bucketDate : preCalculationBucketList.getbucketDates()) {
            bucketDates.add(bucketDate);
            UnCalculatedBucket bucket = preCalculationBucketList.getBucketFromBucketTimestamp(bucketDate);
            CalculatedBucket newBucket = calculator.computeCalculatedBucket(bucket);
            bucketTSToBucketMap.put(bucketDate, newBucket);
            this.bucketNumberToBucketMap.put(bucketNumber, newBucket);
            bucketTimestampToRowIndexMap.put(bucketDate, rowIndex);

            for (DateTime rowTs : newBucket.getTimestampsInBucket()) {
                tsToBucketTsMap.put(rowTs, bucketDate);
                rowIndexToTimestampMap.put(rowIndex, rowTs);
                rowIndexToBucketNumber.put(rowIndex, bucketNumber);
                rowIndex++;
            }
            bucketNumber++;
        }

    }

    public int getTotalNumOfRows() {
        int numRows = 0;

        for (DateTime bucketTimestamp : bucketDates) {
            CalculatedBucket bucket = bucketTSToBucketMap.get(bucketTimestamp);
            numRows += bucket.getTimestampsInBucket().size();
        }
        return numRows;
    }
    
    private String getUName(String sid, String name) {
        String uName = name;
        String[] pieces = sid.split("\\.");

        if (pieces.length > 2) {
            uName = pieces[2] + "." + uName;
        }
        return uName;
    }

    public CalculatedBucket getBucketFromBucketTimestamp(DateTime timeStamp) {
        return bucketTSToBucketMap.get(timeStamp);
    }

    public CalculatedBucket getBucketFromRowTimestamp(DateTime timeStamp) {
        DateTime bucketTimeStamp = tsToBucketTsMap.get(timeStamp);
        return bucketTSToBucketMap.get(bucketTimeStamp);
    }

    public CalculatedBucket getBucketFromBucketNumber(int bucketNumber) {
        return bucketNumberToBucketMap.get(bucketNumber);
    }

    public int getFirstRowIndexOfBucket(int bucketNumber) {
        CalculatedBucket bucket = getBucketFromBucketNumber(bucketNumber);
        DateTime ts = bucket.getTimestampsInBucket().get(0);
        return bucketTimestampToRowIndexMap.get(ts);
    }

    public List<DateTime> getBucketDates() {
        return bucketDates;
    }

    public int getNumberOfBuckets() {
        return bucketDates.size();
    }

    public DateTime getTimestampAt(int rowNumber) {
        return rowIndexToTimestampMap.get(rowNumber);
    }

    public List<DateTime> getAllTimestampsInBucketList() {
        List<DateTime> allTimeStamps = new ArrayList<>();

        for (CalculatedBucket bucket : this.bucketTSToBucketMap.values()) {
            allTimeStamps.addAll(bucket.getTimestampsInBucket());
        }

        return allTimeStamps;
    }
    
    public int getNumHours(){
        if( bucketDates.size() <= 0 ){
            return 0;
        }
        //this is the start time of the first bucket
        DateTime aa = bucketDates.get(0);
        
        //this is the start time of the last bucket
        DateTime end = bucketDates.get( bucketDates.size() - 1);
        
        CalculatedBucket temp = bucketTSToBucketMap.get(end);
        List<DateTime> tempDates = temp.getTimestampsInBucket();
        
        //end is now the time of the last slice int bucket
        end = tempDates.get( tempDates.size() - 1);
        
        //end is now the end time of the last slice in the last bucket
        end = end.plusMinutes(5);
        
        //these are the hours between the start times
        Hours hours = Hours.hoursBetween(aa, end);
        
        return hours.getHours(); //adding one to include the hour of the last bucket. end
    }
    
    public Map<String,Object> getConfigPoints(){
        return associatedPoints.getConfigPoints();
    }

    public Map<String, Object> getValuesAt(int rowNumber) {
        DateTime ts = rowIndexToTimestampMap.get(rowNumber);
        DateTime bucketTs = tsToBucketTsMap.get(ts);
        CalculatedBucket bucket = bucketTSToBucketMap.get(bucketTs);
        Map<String, Object> pointValues = bucket.getTimestampToPointNameAndSidToValueMap().get(ts);
        return pointValues;
    }

    public int getBucketNumberFromRowIndex(int rowNumber) {
        return rowIndexToBucketNumber.get(rowNumber);
    }

    public List<String> getUNames() {
        return uNamesInOrder;
    }
    
    public List<String> getChillerUNames(){
        List<String> chillerNames = new ArrayList<>();
        for( String uName : uNamesInOrder ){
            if( uName.contains("e:chiller")){
                chillerNames.add(uName);
            }
        }
        return chillerNames;
    }

    public EnumCalcPointFilter getBucketListType() {
        return this.bucketListType;
    }

}
