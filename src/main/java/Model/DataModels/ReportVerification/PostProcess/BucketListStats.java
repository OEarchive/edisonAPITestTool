package Model.DataModels.ReportVerification.PostProcess;

import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BucketListStats {

    private final CalculatedBucketList bucketList;
    private final Map<String, Object> configPoints;

    List<String> uNames;

    List<Object> vals;
    private Map< String, Integer> countNotNull;
    private Map< String, Integer> totalCount;
    private Map< String, Double> sum;
    private Map< String, Double> mean;
    private Map< String, Double> stdDev;
    private Map< String, Double> pointMax;
    private Map< String, Double> pointMin;

    private Map< String, Boolean> maxSet;
    private Map< String, Boolean> minSet;

    private Map< Integer, Boolean> hasAllValues = new HashMap<>();
    private Map< Integer, Boolean> hasAllCValues = new HashMap<>();

    public BucketListStats(
            CalculatedBucketList bucketList,
            Map<String, Object> configPoints
    ) {

        this.bucketList = bucketList;
        this.configPoints = configPoints;
        this.uNames = bucketList.getUNames();

        initMaps();
        setTotalCounts();
        setHasAllValuesMaps();
        computeSums(false, hasAllValues);
        computeSums(true, hasAllValues);

        computeMeans();
        computeStdDeviations();

    }

    private void initMaps() {

        this.countNotNull = new HashMap<>();
        this.totalCount = new HashMap<>();
        this.sum = new HashMap<>();
        this.mean = new HashMap<>();
        this.stdDev = new HashMap<>();
        this.pointMax = new HashMap<>();
        this.pointMin = new HashMap<>();

        maxSet = new HashMap<>();
        minSet = new HashMap<>();

        for (String pointName : uNames) {

            countNotNull.put(pointName, 0);
            totalCount.put(pointName, 0);
            sum.put(pointName, 0.0);
            mean.put(pointName, 0.0);
            stdDev.put(pointName, 0.0);
            pointMax.put(pointName, 0.0);
            pointMin.put(pointName, 0.0);
            maxSet.put(pointName, false);
            minSet.put(pointName, false);

        }

        hasAllValues = new HashMap<>();
        hasAllCValues = new HashMap<>();

    }

    private void setTotalCounts() {

        for (int i = 0; i < bucketList.getTotalNumOfRows(); i++) {

            Map<String, Object> valuesAtRow = bucketList.getValuesAt(i);

            for (String pointName : uNames) {

                int currentSize = totalCount.get(pointName);
                currentSize++;
                totalCount.put(pointName, currentSize);

                Object v = valuesAtRow.get(pointName);
                if (v != null) {
                    
                    if( v instanceof String ){
                        String temp = (String)v;
                        
                        if( temp.contentEquals("?") ){
                            //System.out.println("found question mark");
                            continue;
                        }
                    }
                    
                    
                    int currCountNotNull = countNotNull.get(pointName);
                    currCountNotNull++;
                    countNotNull.put(pointName, currCountNotNull);
                }

            }
        }
    }

    private void setHasAllValuesMaps() {

        for (int i = 0; i < bucketList.getTotalNumOfRows(); i++) {
            hasAllValues.put(i, true);
            hasAllCValues.put(i, true);
        }

        if (!bucketList.getBucketListType().getRequiresAllValues()) {
            return;
        }

        for (int i = 0; i < bucketList.getTotalNumOfRows(); i++) {

            Map<String, Object> valuesAtRow = bucketList.getValuesAt(i);

            for (String pointName : uNames) {

                boolean isPartner = pointName.contains("C_");

                Object v = valuesAtRow.get(pointName);

                //TODO : fix this "TotalTon" w/ a property
                if (v == null || (bucketList.getBucketListType() == EnumCalcPointFilter.kWTon && pointName.contentEquals("TotalTon") && isZero(v))) {
                    if (isPartner) {
                        hasAllCValues.put(i, false);
                    } else {
                        hasAllValues.put(i, false);
                    }
                }
            }
        }
    }

    private boolean isZero(Object v) {

        if (v instanceof Double) {
            double temp = (double) v;
            if (Math.abs(temp) < 0.000001) {
                return true;
            }
            return false;

        } else if (v instanceof Integer) {
            int temp = (int) v;
            return temp == 0;
        } else if (v instanceof Boolean) {
            Boolean flag = (Boolean) v;
            return flag;

        }

        return true;
    }

    private void computeSums(boolean sumPartners, Map< Integer, Boolean> hasAllValues) {
        
        double total_kw_h_sum = 0.0;
        boolean headerPrinted = false;
        for (int i = 0; i < bucketList.getTotalNumOfRows(); i++) {

            Map<String, Object> valuesAtRow = bucketList.getValuesAt(i);

            for (String pointName : uNames) {

                boolean isPartner = pointName.contains("C_");

                if (isPartner && !sumPartners) {
                    continue;
                }

                if (!isPartner && sumPartners) {
                    continue;
                }

                if (!hasAllValues.get(i)) {
                    continue;
                }

                Object v = valuesAtRow.get(pointName);

                
                if (v == null) {
                    continue;
                } 
                
                double temp = getDouble( v );
                
                /*
                if( pointName.contentEquals("TotalkWh") && !isPartner){
                    
                    
                            if( !headerPrinted ){
                                headerPrinted = true;
                                System.out.println( "val,recsum,mysum" );
                            }
                    
                            Double sss = sum.get("TotalkWh");
                            
                            
                            total_kw_h_sum += temp;
                            
                            String msg = String.format("%f,%f,%f", temp, sss+temp, total_kw_h_sum);
                            
                            System.out.println( msg );
                }
                */

                double currSum = sum.get(pointName);
                currSum += temp;
                sum.put(pointName, currSum);

                if (!minSet.get(pointName) || temp < pointMin.get(pointName)) {
                    pointMin.put(pointName, temp);
                    minSet.put(pointName, true);
                }

                if (!maxSet.get(pointName) || temp > pointMax.get(pointName)) {
                    this.pointMax.put(pointName, temp);
                    maxSet.put(pointName, true);
                }

            }
        }
        
        //Double sss = sum.get("TotalkWh");
        //System.out.println( sss );
    }

    public int countWhereEquals(String pointName, int val) {

        int countWhereEquals = 0;
        for (int i = 0; i < bucketList.getTotalNumOfRows(); i++) {

            Map<String, Object> valuesAtRow = bucketList.getValuesAt(i);

            Object v = valuesAtRow.get(pointName);

            if (v == null) {
                continue;
            }

            if (getDouble(v) == val) {
                countWhereEquals++;
            }
        }
        return countWhereEquals;
    }

    private void computeMeans() {
        for (String pointName : uNames) {
            if (this.countNotNull.get(pointName) > 0) {
                this.mean.put(pointName, this.sum.get(pointName) / this.countNotNull.get(pointName));
            }
        }
    }

    private void computeStdDeviations() {

        Map< String, Double> sumOfSquaredDeviations = new HashMap<>();
        for (String pointName : uNames) {
            sumOfSquaredDeviations.put(pointName, 0.0);
        }

        for (int i = 0; i < bucketList.getTotalNumOfRows(); i++) {

            Map<String, Object> valuesAtRow = bucketList.getValuesAt(i);

            for (String pointName : uNames) {

                //compute stddev
                Object v = valuesAtRow.get(pointName);

                if (v == null) {
                    continue;
                }
                double temp = getDouble(v);
                double sumSqDev = sumOfSquaredDeviations.get(pointName);
                sumSqDev += Math.pow(temp - mean.get(pointName), 2.0);
                sumOfSquaredDeviations.put(pointName, sumSqDev);
            }

        }

        for (String pointName : uNames) {
            double variance = (Math.abs(countNotNull.get(pointName)) > 0) ? sumOfSquaredDeviations.get(pointName) / countNotNull.get(pointName) : 0;
            this.stdDev.put(pointName, Math.sqrt(variance));
        }
    }

    private double getDouble(Object v) {

        if (v instanceof Double) {
            return (double) v;
        } else if (v instanceof Integer) {

            return (int) v;
        } else if (v instanceof Boolean) {

            Boolean flag = (Boolean) v;
            double temp = (flag) ? 1.0 : 0;
            return temp;
        } else {
            return 0.0;
        }
    }

    //=====
    public List<String> getDataPointNamesAndSids() {
        return bucketList.getUNames();
    }

    public int getCountNotNull(String pointName) {
        return this.countNotNull.get(pointName);
    }

    public int getTotalCount(String pointName) {
        return this.totalCount.get(pointName);
    }

    public double getSum(String pointName) {
        return this.sum.get(pointName);
    }

    public double getMean(String pointName) {
        return this.mean.get(pointName);
    }

    public int getNumHours() {
        return this.bucketList.getNumHours();
    }

    public Map<String, Object> getConfigPoints() {
        return this.bucketList.getConfigPoints();
    }

    public double getStdDev(String pointName) {
        return this.stdDev.get(pointName);
    }

    public double getMax(String pointName) {
        return this.pointMax.get(pointName);
    }

    public double getMin(String pointName) {
        return this.pointMin.get(pointName);
    }

}
