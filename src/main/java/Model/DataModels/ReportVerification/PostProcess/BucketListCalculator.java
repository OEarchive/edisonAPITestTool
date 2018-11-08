package Model.DataModels.ReportVerification.PostProcess;

import Model.DataModels.ReportVerification.PreProcess.UnCalculatedPoint;
import Model.DataModels.ReportVerification.CalcPointClassification.AssociatedPoints;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumDependentPointFilter;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointFilter;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointMinimumResolution;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumPercentStatusTypes;
import Model.DataModels.ReportVerification.PreProcess.UnCalculatedBucket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BucketListCalculator {

    static Logger logger = LoggerFactory.getLogger(BucketListCalculator.class.getName());

    private final AssociatedPoints calculatedPointsInfo;
    private final List<String> uNames;
    private final Map<String, UnCalculatedPoint> uNameToUnCalculatedBucketPointMap;
    private final Map<String, CalculatedBucketPoint> uNameToCalculatedBucketPointMap;
    private final CalculatedBucketList bucketList;
    private final EnumCalcPointFilter bucketType;

    private final List<CalculatedBucket> calculatedQueryBuckets;

    public BucketListCalculator(
            AssociatedPoints calculatedPointsInfo,
            List<String> uNamesInOrder,
            Map<String, UnCalculatedPoint> uNameToUnCalculatedBucketPointMap,
            Map<String, CalculatedBucketPoint> uNameToCalculatedBucketPointMap,
            CalculatedBucketList bucketList,
            EnumCalcPointFilter bucketType) {

        this.calculatedPointsInfo = calculatedPointsInfo;
        this.uNames = uNamesInOrder;
        this.uNameToUnCalculatedBucketPointMap = uNameToUnCalculatedBucketPointMap;
        this.uNameToCalculatedBucketPointMap = uNameToCalculatedBucketPointMap;
        this.bucketList = bucketList;
        this.bucketType = bucketType;

        this.calculatedQueryBuckets = new ArrayList<>();
    }

    private Object getConfigPointValue(String configPointName) {
        UnCalculatedPoint rvDataPoint = uNameToUnCalculatedBucketPointMap.get(configPointName);
        return rvDataPoint.getValue();
    }

    private double getDouble(Object obj) {
        double val = 0;
        if (obj instanceof Integer) {
            val = (Integer) obj;
        } else {
            try {
                val = (Double) obj;
            } catch (Exception ex) {
                System.out.println("Can't get double");
            }
        }
        return val;
    }

    public boolean isChillerStatusPoint(UnCalculatedPoint rv) {

        for (Map association : rv.getDatapointAssociations()) {
            String name = (String) association.get("name");
            String sid = (String) association.get("sid");

            if (name.contentEquals("Status") && sid.contains("chiller")) {
                return true;
            }
        }

        return false;
    }

    private String sidToUName(String sid) {
        //c:testcustomerdemo.s:v5demosa-edge.e:chiller3.kW
        String[] pieces = sid.split("\\.");
        return ((pieces.length > 0) ? pieces[pieces.length - 1] : sid);

    }

    public CalculatedBucket computeCalculatedBucket(UnCalculatedBucket bucket) {

        CalculatedBucket calcedBucket = new CalculatedBucket(
                bucket.getTimestamp(),
                uNames,
                bucket.getBucketTimestamps(),
                bucket.getTimestampToUNameAndValueMap()
        );

        if (bucket == null) {
            System.out.println("bucket is null");
        }

        boolean isFirstTimeStamp = true;
        for (DateTime ts : bucket.getBucketTimestamps()) {
            if (bucketType.getMinResolution() == EnumCalcPointMinimumResolution.HOURLY && !isFirstTimeStamp) {
                continue;
            }
            Map<String, Object> oldMap = bucket.getTimestampToUNameAndValueMap().get(ts);

            HashMap<Object, Object> computedValuesAtPointNameAndSid = new HashMap<>();

            //fill in the existing values
            for (String sourcePoint : oldMap.keySet()) {
                computedValuesAtPointNameAndSid.put(sourcePoint, oldMap.get(sourcePoint));
            }

            Map<String, Object> newMap = calcedBucket.getTimestampToPointNameAndSidToValueMap().get(ts);

            switch (bucketType) {
                case kWTon:
                    set_kWTon(newMap, oldMap);
                    break;
                case kWTon_Weight:
                    set_kWTon_Weight(newMap, oldMap);
                    break;
                case ChillerkWTon:
                    set_ChillerkWTon(newMap, oldMap);
                    break;
                case ChillerkW:
                    set_ChillerkW(newMap, oldMap);
                    break;
                case kWh:
                    set_kWh(newMap, bucket);
                case TotalkWh:
                    set_TotalkWh(newMap, bucket);
                    break;
                case TotalkW:
                    set_TotalkW(newMap, oldMap);
                    break;
                case TotalTon:
                    set_TotalTon(newMap, oldMap);
                    break;
                case Ton:
                    set_Ton(newMap, oldMap);
                    break;
                case TonHours:
                    set_TonHours(newMap, bucket);
                    break;
                case BaselinekW:
                    set_BaselinekW(newMap, oldMap);
                    break;
                case BaselinekWh:
                    set_BaselinekWh(newMap, bucket);
                    break;
                case BaselinekWTon:
                    set_BaselinekWTon(newMap, oldMap);
                    break;
                case BaselinekWTon_Weight:
                    set_BaselinekW_Weight(newMap, oldMap);
                    break;
                case BaselineDollarsCost:
                    set_BaselineDollarsCost(newMap, bucket);
                    break;
                case DollarsCost:
                    set_DollarsCost(newMap, bucket);
                    break;
                case DollarsSaved:
                    set_DollarsSaved(newMap, bucket);
                    break;
                case BaselineCO2Produced:
                    set_BaselineCO2Produced(newMap, bucket);
                    break;
                case CO2Produced:
                    set_CO2Produced(newMap, bucket);
                    break;
                case CO2Saved:
                    set_CO2Saved(newMap, bucket);
                    break;
                case kWDelta:
                    set_kWDelta(newMap, oldMap);
                    break;
                case kWhDelta:
                    set_kWhDelta(newMap, bucket);
                    break;
                case PercentOptimized:
                    set_PercentOptimized(newMap, oldMap);
                    break;
                case PercentPartiallyOptimized:
                    set_PercentPartiallyOptimized(newMap, oldMap);
                    break;
                case PercentOptimizationDisabled:
                    set_PercentOptimizationDisabled(newMap, oldMap);
                    break;
                case PercentBASCommunicationFailure:
                    set_PercentBASCommunicationFailure(newMap, oldMap);
                    break;
                case PercentPlantOff:
                    set_PercentPlantOff(newMap, oldMap);
                    break;

                case ChillerRunHours:
                    set_ChillersRunHours(newMap, bucket);
                    break;

                case ChillersRunning:
                    set_ChillersRunning(newMap, oldMap);
                    break;

                case PercentTotalLoad:
                    set_TotalPercentLoad(newMap, oldMap);
                    break;
            }

            isFirstTimeStamp = false;
        }

        return calcedBucket;
    }

    //TODO : Null Checking --> write null
    public void set_kWTon(Map<String, Object> newMap, Map<String, Object> oldMap) {
        //kWTon = TotalkW / TotalTon
        String calc_kwTonName = "C_" + EnumCalcPointFilter.kWTon.getCalculatedPointName();

        try {
            String totalkWName = EnumCalcPointFilter.TotalkW.getCalculatedPointName();
            String totalTonName = EnumCalcPointFilter.TotalTon.getCalculatedPointName();

            Object totalkWObj = null;
            Object totalTonObj = null;
            for (String uName : oldMap.keySet()) {

                if (uName.contentEquals(totalkWName)) {
                    totalkWObj = oldMap.get(uName);
                }

                if (uName.contentEquals(totalTonName)) {
                    totalTonObj = oldMap.get(uName);
                }
            }

            if (totalkWObj == null || totalTonObj == null) {
                oldMap.put(calc_kwTonName, null);
                return;
            }

            double totalKW = getDouble(totalkWObj);
            double totalTon = getDouble(totalTonObj);

            if (Math.abs(totalTon) > 0.00001) {
                double kWTon = totalKW / totalTon;
                newMap.put(calc_kwTonName, kWTon);
            } else {
                newMap.put(calc_kwTonName, null);
            }
        } catch (Exception ex) {
            newMap.put(calc_kwTonName, null);
            logger.error(calc_kwTonName, ex);
        }

    }

    public void set_kWTon_Weight(Map<String, Object> newMap, Map<String, Object> oldMap) {
        //kWTon_Weight = TotalTon
        String calced_kWTon_WeightName = "C_" + EnumCalcPointFilter.kWTon_Weight.getCalculatedPointName();

        try {
            String totalTonName = EnumCalcPointFilter.TotalTon.getCalculatedPointName();
            double totalTon = 0.0;
            Object totalTonObj = null;
            for (String pointNameAndSid : oldMap.keySet()) {
                if (pointNameAndSid.contentEquals(totalTonName)) {
                    totalTonObj = oldMap.get(pointNameAndSid);
                    continue;
                }
            }
            if (totalTonObj instanceof Integer) {
                totalTon = (Integer) totalTonObj;
            } else {
                totalTon = (Double) totalTonObj;
            }

            if (totalTonObj != null) {
                double kWTon_Weight = totalTon;
                newMap.put(calced_kWTon_WeightName, kWTon_Weight);
            } else {
                newMap.put(calced_kWTon_WeightName, null);
            }
        } catch (Exception ex) {
            newMap.put(calced_kWTon_WeightName, null);
            logger.error(calced_kWTon_WeightName, ex);
        }
    }

    public void set_ChillerkWTon(Map<String, Object> newMap, Map<String, Object> oldMap) {
        //ChillerkWTon = ChillerkW / TotalTon
        String calced_ChillerkWTonName = "C_" + EnumCalcPointFilter.ChillerkWTon.getCalculatedPointName();

        try {
            String ChillerkWName = EnumCalcPointFilter.ChillerkW.getCalculatedPointName();
            String totalTonName = EnumCalcPointFilter.TotalTon.getCalculatedPointName();

            Object chillerkWObj = null;
            Object totalTonObj = null;
            for (String pointNameAndSid : oldMap.keySet()) {
                if (pointNameAndSid.contentEquals(ChillerkWName)) {
                    chillerkWObj = oldMap.get(pointNameAndSid);
                }
                if (pointNameAndSid.contentEquals(totalTonName)) {
                    totalTonObj = oldMap.get(pointNameAndSid);
                }

            }

            double chillerkW = 0.0;
            if (chillerkWObj != null) {
                if (chillerkWObj instanceof Integer) {
                    chillerkW = (Integer) chillerkWObj;
                } else {
                    chillerkW = (Double) chillerkWObj;
                }
            }

            double totalTon = 0.0;
            if (totalTonObj != null) {
                if (totalTonObj instanceof Integer) {
                    totalTon = (Integer) totalTonObj;
                } else {
                    totalTon = (Double) totalTonObj;
                }
            }

            if (chillerkWObj != null && totalTonObj != null && Math.abs(totalTon) > 0.00001) {
                double kWTon = chillerkW / totalTon;
                newMap.put(calced_ChillerkWTonName, kWTon);
            } else {
                newMap.put(calced_ChillerkWTonName, null);
            }
        } catch (Exception ex) {
            newMap.put(calced_ChillerkWTonName, null);
            logger.error(calced_ChillerkWTonName, ex);
        }

    }

    public void set_ChillerkW(Map<String, Object> newMap, Map<String, Object> oldMap) {
        //ChillerkW = sum<all chiller kW>
        String ChillerkWName = EnumCalcPointFilter.ChillerkW.getCalculatedPointName();
        String calced_ChillerkWName = "C_" + ChillerkWName;

        try {

            double sum = 0.0;
            boolean foundAValue = false;

            List<UnCalculatedPoint> points = calculatedPointsInfo.getAssociatedPoints(bucketType);

            for (UnCalculatedPoint point : points) {

                //ignore station points in calculation
                if (point.getSid().contains("st:")) {
                    continue;
                }

                for (Map association : point.getDatapointAssociations()) {
                    String assocName = (String) association.get("name");
                    String sid = (String) association.get("sid");
                    if (sid.contains("st:")) {
                        continue;
                    }

                    String tempUName = sidToUName(sid);
                    if (assocName.contentEquals("kW") && tempUName.contains("e:chiller")) {
                        String uName = tempUName + "." + assocName;
                        Object kwObj = oldMap.get(uName);

                        if (kwObj != null) {
                            foundAValue = true;
                            double amt = getDouble(kwObj);
                            sum += amt;
                            break; //avoid double sum by ignoring values for other associations
                        }
                    }
                }
            }

            if (foundAValue) {
                newMap.put(calced_ChillerkWName, sum);
            } else {
                newMap.put(calced_ChillerkWName, null);
            }
        } catch (Exception ex) {
            newMap.put(calced_ChillerkWName, null);
            logger.error(calced_ChillerkWName, ex);
        }
    }

    public void set_kWh(Map<String, Object> newMap, UnCalculatedBucket bucket) {
        //AVG(kW)
        String kWhName = EnumCalcPointFilter.kWh.getCalculatedPointName();
        String calcedkWhName = "C_" + kWhName;

        try {

            Map<String, List<Double>> averagesMap = new HashMap<>();

            for (DateTime timeStamp : bucket.getBucketTimestamps()) {

                Map<String, Object> uNamesAndValues = bucket.getTimestampToUNameAndValueMap().get(timeStamp);

                for (String uName : uNamesAndValues.keySet()) {
                    if (uName.contentEquals(kWhName) || uName.contentEquals(calcedkWhName)) {
                        continue;
                    }
                    UnCalculatedPoint rvDatapoint = uNameToUnCalculatedBucketPointMap.get(uName);
                    if (calculatedPointsInfo.isDependent(bucketType, rvDatapoint)) {
                        Object kWObj = uNamesAndValues.get(uName);

                        if (!averagesMap.containsKey(uName)) {
                            averagesMap.put(uName, new ArrayList<Double>());
                        }

                        if (kWObj == null) {
                            continue;
                        }

                        double temp;
                        if (kWObj instanceof Integer) {
                            temp = (Integer) kWObj;
                        } else {
                            temp = (Double) kWObj;
                        }

                        averagesMap.get(uName).add(temp);
                    }
                }
            }

            List<Double> averages = new ArrayList<>();
            for (String pointNameAndSid : averagesMap.keySet()) {
                Double avgForThisEquip = 0.0;
                for (Double machAvg : averagesMap.get(pointNameAndSid)) {
                    avgForThisEquip += machAvg;
                }

                avgForThisEquip /= averagesMap.get(pointNameAndSid).size();
                averages.add(avgForThisEquip);
            }

            Double totalAvg = 0.0;
            for (Double avg : averages) {
                totalAvg += avg;
            }

            newMap.put(calcedkWhName, totalAvg);
        } catch (Exception ex) {
            newMap.put(calcedkWhName, null);
            logger.error(calcedkWhName, ex);
        }
    }

    public void set_TotalkWh(Map<String, Object> newMap, UnCalculatedBucket bucket) {
        //AVG(kW)
        String totalkWhName = EnumCalcPointFilter.TotalkWh.getCalculatedPointName();
        String totalKWHCalcName = "C_" + totalkWhName;

        try {

            Map<String, List<Double>> averagesMap = new HashMap<>();

            for (DateTime timeStamp : bucket.getBucketTimestamps()) {

                Map<String, Object> uNameAndValues = bucket.getTimestampToUNameAndValueMap().get(timeStamp);

                for (String uName : uNameAndValues.keySet()) {
                    if (uName.contentEquals(totalkWhName) || uName.contentEquals(totalKWHCalcName)) {
                        continue;
                    }
                    UnCalculatedPoint rvDatapoint = uNameToUnCalculatedBucketPointMap.get(uName);
                    if (calculatedPointsInfo.isDependent(bucketType, rvDatapoint)) {
                        Object kWhObj = uNameAndValues.get(uName);

                        if (!averagesMap.containsKey(uName)) {
                            averagesMap.put(uName, new ArrayList<Double>());
                        }

                        if (kWhObj == null) {
                            continue;
                        }

                        double temp;
                        if (kWhObj instanceof Integer) {
                            temp = (Integer) kWhObj;

                        } else if (kWhObj instanceof Double) {
                            temp = (Double) kWhObj;

                        } else {
                            continue;
                        }

                        averagesMap.get(uName).add(temp);
                    }
                }
            }

            List<Double> averages = new ArrayList<>();
            for (String pointNameAndSid : averagesMap.keySet()) {
                Double avgForThisEquip = 0.0;
                for (Double machAvg : averagesMap.get(pointNameAndSid)) {
                    avgForThisEquip += machAvg;
                }

                avgForThisEquip /= averagesMap.get(pointNameAndSid).size();
                averages.add(avgForThisEquip);
            }

            Double totalAvg = 0.0;
            for (Double avg : averages) {
                if (!Double.isNaN(avg)) {
                    totalAvg += avg;
                }
            }

            newMap.put(totalKWHCalcName, totalAvg);
        } catch (Exception ex) {
            newMap.put(totalKWHCalcName, null);
            logger.error(totalKWHCalcName, ex);
        }
    }

    public void set_TotalkW(Map<String, Object> newMap, Map<String, Object> oldMap) {
        //ChillerkW = sum<all chiller kW>
        String TotalkWName = EnumCalcPointFilter.TotalkW.getCalculatedPointName();
        String calced_TotalkWName = "C_" + TotalkWName;

        try {
            double sum = 0.0;

            boolean foundAkwValue = false;

            for (String uName : oldMap.keySet()) {

                //ignore station points
                if (uName.contains("st:")) {
                    continue;
                }

                if (uName.contentEquals(TotalkWName) || uName.contentEquals(calced_TotalkWName)) {
                    continue;
                }
                UnCalculatedPoint rvDatapoint = uNameToUnCalculatedBucketPointMap.get(uName);
                if (calculatedPointsInfo.isDependent(bucketType, rvDatapoint)) {
                    Object kwObj = oldMap.get(uName);

                    if (kwObj == null) {
                        continue;
                    }

                    if (kwObj instanceof String) {
                        continue;
                    }

                    if (kwObj instanceof Boolean) {
                        continue;
                    }

                    foundAkwValue = true;

                    double temp;

                    if (kwObj instanceof Integer) {
                        temp = (Integer) kwObj;
                    } else {
                        temp = (Double) kwObj;
                    }

                    sum += temp;
                }
            }

            if (foundAkwValue) {
                newMap.put(calced_TotalkWName, sum);
            } else {
                newMap.put(calced_TotalkWName, null);
            }
        } catch (Exception ex) {
            newMap.put(calced_TotalkWName, null);
            logger.error(calced_TotalkWName, ex);
        }

    }

    public void set_TotalTon(Map<String, Object> newMap, Map<String, Object> oldMap) {
        //TotalTon = big formual below...
        String calced_TotalTonName = "C_" + EnumCalcPointFilter.TotalTon.getCalculatedPointName();

        try {

            String TonName = EnumCalcPointFilter.Ton.getCalculatedPointName();
            String ChillerkWName = EnumCalcPointFilter.ChillerkW.getCalculatedPointName();
            String ChilledWaterFlowName = EnumDependentPointFilter.ChilledWaterFlow.getDependentPointName();

            String MinimumChilledWaterFlowName = EnumDependentPointFilter.MinimumChilledWaterFlow.getDependentPointName();
            String TotalCapacityName = EnumDependentPointFilter.TotalCapacity.getDependentPointName();

            Object TonObj = null;
            Object ChillerkWObj = null;
            Object ChilledWaterFlowObj = null;
            Object MinimumChilledWaterFlowObj = getConfigPointValue(MinimumChilledWaterFlowName);
            Object TotalCapacityObj = getConfigPointValue(TotalCapacityName);

            for (String uName : oldMap.keySet()) {
                if (uName.contentEquals(TonName)) {
                    TonObj = oldMap.get(uName);
                }
                if (uName.contentEquals(ChillerkWName)) {
                    ChillerkWObj = oldMap.get(uName);
                }
                if (uName.contentEquals(ChilledWaterFlowName)) {
                    ChilledWaterFlowObj = oldMap.get(uName);
                }
                if (uName.contentEquals(MinimumChilledWaterFlowName)) {
                    continue;
                }
                if (uName.contentEquals(TotalCapacityName)) {
                    continue;
                }
            }


            /*

        when [Ton] is null or [ChilledWaterFlow] is null or [ChillerkW] is null 
            then null
        when [Ton] = 0 and [ChilledWaterFlow] > [MinimumChilledWaterFlow]/2 and [ChillerkW] > 50 
            then null
        when [Ton]<0 
            then 0
        when [Ton] > [TotalCapacity] * 1.5 
            then [TotalCapacity] * 1.5
        when [CHWFLO] < ([MinimumChilledWaterFlow]/2) 
            then 0
        else [Ton]
             */
            if (TonObj == null || ChilledWaterFlowObj == null || ChillerkWObj == null) {
                newMap.put(calced_TotalTonName, null);
                return;
            }

            double ton = getDouble(TonObj);
            double chilledWaterFlow = getDouble(ChilledWaterFlowObj);
            double chillerkW = getDouble(ChillerkWObj);

            //TODO: What to do if this is null:
            double minChilledWaterFlo = getDouble(MinimumChilledWaterFlowObj);

            if (ton == 0 && chilledWaterFlow > minChilledWaterFlo / 2 && chillerkW > 50.0) {
                newMap.put(calced_TotalTonName, null);
                return;
            }

            if (ton < 0) {
                newMap.put(calced_TotalTonName, (double) 0);
                return;
            }

            //TODO: What to do if this is null:
            double totalCapacity = getDouble(TotalCapacityObj);

            if (ton > totalCapacity * 1.5) {
                newMap.put(calced_TotalTonName, totalCapacity * 1.5);
                return;
            }

            if (chilledWaterFlow < minChilledWaterFlo / 2) {
                newMap.put(calced_TotalTonName, (double) 0);
                return;
            }

            newMap.put(calced_TotalTonName, ton);

        } catch (Exception ex) {
            newMap.put(calced_TotalTonName, null);
            logger.error(calced_TotalTonName, ex);
        }

    }

    public void set_Ton(Map<String, Object> newMap, Map<String, Object> oldMap) {
        //Ton = ((ChilledWaterReturnTemperature - ChilledWaterSupplyTemperature) * ChilledWaterFlow) / 24
        String calcTonName = "C_" + EnumCalcPointFilter.Ton.getCalculatedPointName();

        try {

            String cwRetName = EnumDependentPointFilter.ChilledWaterReturnTemperature.getDependentPointName();
            String cwSupName = EnumDependentPointFilter.ChilledWaterSupplyTemperature.getDependentPointName();
            String cwFloName = EnumDependentPointFilter.ChilledWaterFlow.getDependentPointName();

            Object cwRetObj = null;
            Object cwSupObj = null;
            Object cwFloObj = null;

            for (String uName : oldMap.keySet()) {
                if (uName.contentEquals(cwRetName)) {
                    cwRetObj = oldMap.get(uName);
                }
                if (uName.contentEquals(cwSupName)) {
                    cwSupObj = oldMap.get(uName);
                }
                if (uName.contentEquals(cwFloName)) {
                    cwFloObj = oldMap.get(uName);
                }
            }

            if (cwRetObj == null || cwSupObj == null || cwFloObj == null) {
                newMap.put(calcTonName, null);
                return;
            }

            double cwRet = getDouble(cwRetObj);
            double cwSup = getDouble(cwSupObj);
            double cwFlo = getDouble(cwFloObj);

            double Ton = (cwRet - cwSup) * cwFlo / 24;

            newMap.put(calcTonName, Ton);
        } catch (Exception ex) {
            newMap.put(calcTonName, null);
            logger.error(calcTonName, ex);

        }

    }

    public void set_TonHours(Map<String, Object> newMap, UnCalculatedBucket bucket) {
        //AVG(kW)
        String tonHoursName = EnumCalcPointFilter.TonHours.getCalculatedPointName();
        String calcedTonHoursName = "C_" + tonHoursName;

        try {

            List<Object> objs = new ArrayList<>();

            for (DateTime timeStamp : bucket.getBucketTimestamps()) {
                Map<String, Object> uNameAndValues = bucket.getTimestampToUNameAndValueMap().get(timeStamp);
                for (String uName : uNameAndValues.keySet()) {
                    if (uName.contentEquals(tonHoursName) || uName.contentEquals(calcedTonHoursName)) {
                        continue;
                    }
                    UnCalculatedPoint rvDatapoint = uNameToUnCalculatedBucketPointMap.get(uName);
                    if (calculatedPointsInfo.isDependent(bucketType, rvDatapoint)) {
                        objs.add(uNameAndValues.get(uName));

                    }
                }
            }

            Mean mean = new Mean(objs);
            if (mean.hasMean()) {
                newMap.put(calcedTonHoursName, mean.getMean());
            } else {
                newMap.put(calcedTonHoursName, null);
            }

        } catch (Exception ex) {
            newMap.put(calcedTonHoursName, null);
            logger.error(calcedTonHoursName, ex);
        }
    }

    public void set_BaselinekW(Map<String, Object> newMap, Map<String, Object> oldMap) {
        // baslinekWName = BaselinekWTon * TotalTon
        String calcd_baslinekWName = "C_" + EnumCalcPointFilter.BaselinekW.getCalculatedPointName();

        try {

            String baslinekWTonName = EnumCalcPointFilter.BaselinekWTon.getCalculatedPointName();
            String totalTonName = EnumCalcPointFilter.TotalTon.getCalculatedPointName();

            Object baslinekWTonObj = null;
            Object totalTonObj = null;
            for (String uName : oldMap.keySet()) {
                if (uName.contentEquals(baslinekWTonName)) {
                    baslinekWTonObj = oldMap.get(uName);
                }
                if (uName.contentEquals(totalTonName)) {
                    totalTonObj = oldMap.get(uName);
                }
            }
            if (baslinekWTonObj == null || totalTonObj == null) {
                newMap.put(calcd_baslinekWName, null);
                return;
            }

            double baslinekWTon = getDouble(baslinekWTonObj);
            double totalTon = getDouble(totalTonObj);
            double baslinekW = baslinekWTon * totalTon;
            newMap.put(calcd_baslinekWName, baslinekW);

        } catch (Exception ex) {
            newMap.put(calcd_baslinekWName, null);
            logger.error(calcd_baslinekWName, ex);
        }
    }

    public void set_BaselinekWh(Map<String, Object> newMap, UnCalculatedBucket bucket) {
        //AVG(BaselinekW)
        String calcd_baselinekWhName = "C_" + EnumCalcPointFilter.BaselinekWh.getCalculatedPointName();

        try {

            List<Object> objs = new ArrayList<>();
            for (DateTime timeStamp : bucket.getBucketTimestamps()) {
                Map<String, Object> uNameToValues = bucket.getTimestampToUNameAndValueMap().get(timeStamp);
                for (String uName : uNameToValues.keySet()) {
                    if (uName.contentEquals(EnumCalcPointFilter.BaselinekWh.getCalculatedPointName())) {
                        continue;
                    }
                    UnCalculatedPoint rvDatapoint = uNameToUnCalculatedBucketPointMap.get(uName);
                    if (calculatedPointsInfo.isDependent(bucketType, rvDatapoint)) {
                        objs.add(uNameToValues.get(uName));
                    }
                }
            }

            Mean mean = new Mean(objs);

            if (mean.hasMean()) {
                newMap.put(calcd_baselinekWhName, mean.getMean());
            } else {
                newMap.put(calcd_baselinekWhName, null);
            }

        } catch (Exception ex) {
            newMap.put(calcd_baselinekWhName, null);
            logger.error(calcd_baselinekWhName, ex);
        }
    }

    public void set_BaselinekWTon(Map<String, Object> newMap, Map<String, Object> oldMap) {
        // baslinekWName = BaselinekWTon * TotalTon
        String calcd_baslinekWTonName = "C_" + EnumCalcPointFilter.BaselinekWTon.getCalculatedPointName();

        try {

            String kWTonName = EnumCalcPointFilter.kWTon.getCalculatedPointName();
            Object kWTonObj = null;

            for (String uName : oldMap.keySet()) {
                if (uName.contentEquals(kWTonName)) {
                    kWTonObj = oldMap.get(uName);
                }
            }

            if (kWTonObj == null) {
                newMap.put(calcd_baslinekWTonName, null);
                return;
            }

            double baselinekWTonValue = (double) kWTonObj;
            newMap.put(calcd_baslinekWTonName, baselinekWTonValue);

        } catch (Exception ex) {
            newMap.put(calcd_baslinekWTonName, null);
            logger.error(calcd_baslinekWTonName, ex);
        }
    }

    public void set_BaselinekW_Weight(Map<String, Object> newMap, Map<String, Object> oldMap) {
        // baslinekWName = BaselinekWTon * TotalTon
        String calcd_baslinekW_Weight = "C_" + EnumCalcPointFilter.BaselinekWTon_Weight.getCalculatedPointName();

        try {

            String tt = EnumCalcPointFilter.TotalTon.getCalculatedPointName();
            Object ttObj = oldMap.get(tt);

            if (ttObj == null) {
                newMap.put(calcd_baslinekW_Weight, null);
                return;
            }

            double ttValue = (double) ttObj;
            newMap.put(calcd_baslinekW_Weight, ttValue);

        } catch (Exception ex) {
            newMap.put(calcd_baslinekW_Weight, null);
            logger.error(calcd_baslinekW_Weight, ex);
        }
    }

    public void set_BaselineDollarsCost(Map<String, Object> newMap, UnCalculatedBucket bucket) {

        String calcd_baselineDollarsCostName = "C_" + EnumCalcPointFilter.BaselineDollarsCost.getCalculatedPointName();

        try {

            String baselinekWhName = EnumCalcPointFilter.BaselinekWh.getCalculatedPointName();
            String blendedUtilityRate = EnumDependentPointFilter.BlendedUtilityRate.getDependentPointName();

            Map<String, Object> oldMap = bucket.getTimestampToUNameAndValueMap().get(bucket.getTimestamp());

            Object baselinekWhObj = oldMap.get(baselinekWhName);

            Object burObj = getConfigPointValue(blendedUtilityRate);
            //Object burObj = oldMap.get(blendedUtilityRate);

            if (baselinekWhObj == null || burObj == null) {
                newMap.put(calcd_baselineDollarsCostName, null);
            }

            double baselinekWh = getDouble(baselinekWhObj);
            double bur = getDouble(burObj);

            newMap.put(calcd_baselineDollarsCostName, baselinekWh * bur);

        } catch (Exception ex) {
            newMap.put(calcd_baselineDollarsCostName, null);
            logger.error(calcd_baselineDollarsCostName, ex);
        }
    }

    public void set_DollarsCost(Map<String, Object> newMap, UnCalculatedBucket bucket) {

        String calcd_DollarsCostName = "C_" + EnumCalcPointFilter.DollarsCost.getCalculatedPointName();

        try {

            String kWhName = EnumCalcPointFilter.TotalkWh.getCalculatedPointName();
            String blendedUtilityRate = EnumDependentPointFilter.BlendedUtilityRate.getDependentPointName();

            Map<String, Object> oldMap = bucket.getTimestampToUNameAndValueMap().get(bucket.getTimestamp());

            Object kWhObj = oldMap.get(kWhName);

            Object burObj = getConfigPointValue(blendedUtilityRate);
            //Object burObj = oldMap.get(blendedUtilityRate);

            if (kWhObj == null || burObj == null) {
                newMap.put(calcd_DollarsCostName, null);
            }

            double kWh = getDouble(kWhObj);
            double bur = getDouble(burObj);

            newMap.put(calcd_DollarsCostName, kWh * bur);

        } catch (Exception ex) {
            newMap.put(calcd_DollarsCostName, null);
            logger.error(calcd_DollarsCostName, ex);
        }
    }

    public void set_DollarsSaved(Map<String, Object> newMap, UnCalculatedBucket bucket) {

        String calcd_DollarsSavedName = "C_" + EnumCalcPointFilter.DollarsSaved.getCalculatedPointName();

        try {

            String kWhDeltaName = EnumCalcPointFilter.kWhDelta.getCalculatedPointName();
            String blendedUtilityRate = EnumDependentPointFilter.BlendedUtilityRate.getDependentPointName();

            Map<String, Object> oldMap = bucket.getTimestampToUNameAndValueMap().get(bucket.getTimestamp());

            Object kWhDeltaObj = oldMap.get(kWhDeltaName);
            Object burObj = getConfigPointValue(blendedUtilityRate);

            if (kWhDeltaObj == null || burObj == null) {
                newMap.put(calcd_DollarsSavedName, null);
            }

            double kWhDelta = getDouble(kWhDeltaObj);
            double bur = getDouble(burObj);

            newMap.put(calcd_DollarsSavedName, kWhDelta * bur);

        } catch (Exception ex) {
            newMap.put(calcd_DollarsSavedName, null);
            logger.error(calcd_DollarsSavedName, ex);
        }
    }

    public void set_BaselineCO2Produced(Map<String, Object> newMap, UnCalculatedBucket bucket) {

        String calcd_BaselineCO2ProducedName = "C_" + EnumCalcPointFilter.BaselineCO2Produced.getCalculatedPointName();

        try {

            String BaselinekWhName = EnumCalcPointFilter.BaselinekWh.getCalculatedPointName();
            String CO2EmissionFactorName = EnumDependentPointFilter.CO2EmissionFactor.getDependentPointName();

            Map<String, Object> oldMap = bucket.getTimestampToUNameAndValueMap().get(bucket.getTimestamp());

            Object bkwhObj = oldMap.get(BaselinekWhName);
            Object co2eObj = getConfigPointValue(CO2EmissionFactorName);

            if (bkwhObj == null || co2eObj == null) {
                newMap.put(calcd_BaselineCO2ProducedName, null);
            }

            double bkwhObjValue = getDouble(bkwhObj);
            double co2eObjValue = getDouble(co2eObj);

            newMap.put(calcd_BaselineCO2ProducedName, bkwhObjValue * co2eObjValue);

        } catch (Exception ex) {
            newMap.put(calcd_BaselineCO2ProducedName, null);
            logger.error(calcd_BaselineCO2ProducedName, ex);
        }
    }

    public void set_CO2Produced(Map<String, Object> newMap, UnCalculatedBucket bucket) {

        String calcd_CO2ProducedName = "C_" + EnumCalcPointFilter.CO2Produced.getCalculatedPointName();

        try {

            String kWhName = EnumCalcPointFilter.TotalkWh.getCalculatedPointName();
            String CO2EmissionFactorName = EnumDependentPointFilter.CO2EmissionFactor.getDependentPointName();

            Map<String, Object> oldMap = bucket.getTimestampToUNameAndValueMap().get(bucket.getTimestamp());

            Object kwhObj = oldMap.get(kWhName);
            Object co2eObj = getConfigPointValue(CO2EmissionFactorName);

            if (kwhObj == null || co2eObj == null) {
                newMap.put(calcd_CO2ProducedName, null);
            }

            double kwhObjValue = getDouble(kwhObj);
            double co2eObjValue = getDouble(co2eObj);

            newMap.put(calcd_CO2ProducedName, kwhObjValue * co2eObjValue);

        } catch (Exception ex) {
            newMap.put(calcd_CO2ProducedName, null);
            logger.error(calcd_CO2ProducedName, ex);
        }
    }

    public void set_CO2Saved(Map<String, Object> newMap, UnCalculatedBucket bucket) {

        String calcd_CO2SavedName = "C_" + EnumCalcPointFilter.CO2Saved.getCalculatedPointName();

        try {

            String deltaName = EnumCalcPointFilter.kWhDelta.getCalculatedPointName();
            String CO2EmissionFactorName = EnumDependentPointFilter.CO2EmissionFactor.getDependentPointName();

            Map<String, Object> oldMap = bucket.getTimestampToUNameAndValueMap().get(bucket.getTimestamp());

            Object deltaObj = oldMap.get(deltaName);
            Object co2eObj = getConfigPointValue(CO2EmissionFactorName);

            if (deltaObj == null || co2eObj == null) {
                newMap.put(calcd_CO2SavedName, null);
            }

            double deltaObjValue = getDouble(deltaObj);
            double co2eObjValue = getDouble(co2eObj);

            newMap.put(calcd_CO2SavedName, deltaObjValue * co2eObjValue);

        } catch (Exception ex) {
            newMap.put(calcd_CO2SavedName, null);
            logger.error(calcd_CO2SavedName, ex);
        }
    }

    public void set_kWDelta(Map<String, Object> newMap, Map<String, Object> oldMap) {
        // baslinekWName = BaselinekWTon * TotalTon
        String calcd_kWDeltaName = "C_" + EnumCalcPointFilter.kWDelta.getCalculatedPointName();

        try {

            String basekWName = EnumCalcPointFilter.BaselinekW.getCalculatedPointName();
            String totalkWName = EnumCalcPointFilter.TotalkW.getCalculatedPointName();

            Object basekWObj = oldMap.get(basekWName);
            Object totalkObj = oldMap.get(totalkWName);

            if (basekWObj == null || totalkObj == null) {
                newMap.put(calcd_kWDeltaName, null);
                return;
            }

            double basekWValue = (double) basekWObj;
            double totalValue = (double) totalkObj;
            newMap.put(calcd_kWDeltaName, basekWValue - totalValue);

        } catch (Exception ex) {
            newMap.put(calcd_kWDeltaName, null);
            logger.error(calcd_kWDeltaName, ex);
        }
    }

    public void set_kWhDelta(Map<String, Object> newMap, UnCalculatedBucket bucket) {

        String calcd_kWhDeltaName = "C_" + EnumCalcPointFilter.kWhDelta.getCalculatedPointName();

        try {

            String deltaName = EnumCalcPointFilter.kWDelta.getCalculatedPointName();

            List<Object> objs = new ArrayList<>();
            for (DateTime timeStamp : bucket.getBucketTimestamps()) {
                Map<String, Object> pointNamesAndValues = bucket.getTimestampToUNameAndValueMap().get(timeStamp);
                objs.add(pointNamesAndValues.get(deltaName));
            }

            Mean mean = new Mean(objs);

            if (mean.hasMean()) {
                newMap.put(calcd_kWhDeltaName, mean.getMean());
            } else {
                newMap.put(calcd_kWhDeltaName, null);
            }

        } catch (Exception ex) {
            newMap.put(calcd_kWhDeltaName, null);
            logger.error(calcd_kWhDeltaName, ex);
        }
    }

    public void set_PercentOptimized(Map<String, Object> newMap, Map<String, Object> oldMap) {
        String calcd_PercentOptimizedName = "C_" + EnumCalcPointFilter.PercentOptimized.getCalculatedPointName();

        try {

            List<Object> objs = new ArrayList<>();
            for (String pointName : oldMap.keySet()) {
                if (pointName.contentEquals(EnumCalcPointFilter.PercentOptimized.getCalculatedPointName())) {
                    continue;
                }
                UnCalculatedPoint rvDatapoint = uNameToUnCalculatedBucketPointMap.get(pointName);
                if (calculatedPointsInfo.isDependent(EnumCalcPointFilter.PercentOptimized, rvDatapoint)) {
                    objs.add(oldMap.get(pointName));
                }
            }

            boolean foundAValue = false;
            int count = 0;
            int sum = 0;

            for (Object obj : objs) {
                count++;
                if (obj == null) {
                    continue;
                }

                if (!(obj instanceof Integer)) {
                    continue;
                }

                foundAValue = true;

                int val = (Integer) obj;

                if (val == EnumPercentStatusTypes.Optimized.getStatusValue()) {
                    sum += 1;
                }
            }

            if (!foundAValue || count <= 0) {
                newMap.put(calcd_PercentOptimizedName, null);
                return;
            }

            newMap.put(calcd_PercentOptimizedName, (double) sum * 100.0 / (double) count);

        } catch (Exception ex) {
            newMap.put(calcd_PercentOptimizedName, null);
            logger.error(calcd_PercentOptimizedName, ex);
        }
    }

    //PercentPartiallyOptimized
    public void set_PercentPartiallyOptimized(Map<String, Object> newMap, Map<String, Object> oldMap) {
        String calcd_PercentPartiallyOptimizedName = "C_" + EnumCalcPointFilter.PercentPartiallyOptimized.getCalculatedPointName();

        try {

            List<Object> objs = new ArrayList<>();
            for (String pointName : oldMap.keySet()) {
                if (pointName.contentEquals(EnumCalcPointFilter.PercentPartiallyOptimized.getCalculatedPointName())) {
                    continue;
                }
                UnCalculatedPoint rvDatapoint = uNameToUnCalculatedBucketPointMap.get(pointName);
                if (calculatedPointsInfo.isDependent(EnumCalcPointFilter.PercentPartiallyOptimized, rvDatapoint)) {
                    objs.add(oldMap.get(pointName));
                }
            }

            boolean foundAValue = false;
            int countOfStatusEqualsTwoEntries = 0;
            int totalCount = 0;

            for (Object obj : objs) {

                if (obj == null) {
                    continue;
                }

                if (!(obj instanceof Integer)) {
                    continue;
                }

                foundAValue = true;

                int val = (Integer) obj;

                totalCount++;
                if (val == EnumPercentStatusTypes.PartiallyOptimzied.getStatusValue()) {
                    countOfStatusEqualsTwoEntries++;
                }
            }

            if (!foundAValue || totalCount <= 0) {
                newMap.put(calcd_PercentPartiallyOptimizedName, null);
                return;
            }

            newMap.put(calcd_PercentPartiallyOptimizedName, (double) countOfStatusEqualsTwoEntries * 100.0 / (double) totalCount);

        } catch (Exception ex) {
            newMap.put(calcd_PercentPartiallyOptimizedName, null);
            logger.error(calcd_PercentPartiallyOptimizedName, ex);
        }
    }

    public void set_PercentOptimizationDisabled(Map<String, Object> newMap, Map<String, Object> oldMap) {
        String calcd_PercentOptimizationDisabledName = "C_" + EnumCalcPointFilter.PercentOptimizationDisabled.getCalculatedPointName();

        try {

            List<Object> objs = new ArrayList<>();
            for (String pointName : oldMap.keySet()) {
                if (pointName.contentEquals(EnumCalcPointFilter.PercentOptimizationDisabled.getCalculatedPointName())) {
                    continue;
                }
                UnCalculatedPoint rvDatapoint = uNameToUnCalculatedBucketPointMap.get(pointName);
                if (calculatedPointsInfo.isDependent(EnumCalcPointFilter.PercentOptimizationDisabled, rvDatapoint)) {
                    objs.add(oldMap.get(pointName));
                }
            }

            boolean foundAValue = false;
            int count = 0;
            int sum = 0;

            for (Object obj : objs) {
                count++;
                if (obj == null) {
                    continue;
                }

                if (!(obj instanceof Integer)) {
                    continue;
                }

                foundAValue = true;

                int val = (Integer) obj;

                if (val == EnumPercentStatusTypes.Disabled.getStatusValue()) {
                    sum += 1;
                }
            }

            if (!foundAValue || count <= 0) {
                newMap.put(calcd_PercentOptimizationDisabledName, null);
                return;
            }

            newMap.put(calcd_PercentOptimizationDisabledName, (double) sum * 100.0 / (double) count);

        } catch (Exception ex) {
            newMap.put(calcd_PercentOptimizationDisabledName, null);
            logger.error(calcd_PercentOptimizationDisabledName, ex);
        }
    }

    public void set_PercentBASCommunicationFailure(Map<String, Object> newMap, Map<String, Object> oldMap) {
        String calcd_PercentBASCommunicationFailureName = "C_" + EnumCalcPointFilter.PercentBASCommunicationFailure.getCalculatedPointName();

        try {

            List<Object> objs = new ArrayList<>();
            for (String pointName : oldMap.keySet()) {
                if (pointName.contentEquals(EnumCalcPointFilter.PercentBASCommunicationFailure.getCalculatedPointName())) {
                    continue;
                }
                UnCalculatedPoint rvDatapoint = uNameToUnCalculatedBucketPointMap.get(pointName);
                if (calculatedPointsInfo.isDependent(EnumCalcPointFilter.PercentBASCommunicationFailure, rvDatapoint)) {
                    objs.add(oldMap.get(pointName));
                }
            }

            boolean foundAValue = false;
            int count = 0;
            int sum = 0;

            for (Object obj : objs) {
                count++;
                if (obj == null) {
                    continue;
                }

                if (!(obj instanceof Integer)) {
                    continue;
                }

                foundAValue = true;

                int val = (Integer) obj;

                if (val == EnumPercentStatusTypes.BasCommFailure.getStatusValue()) {
                    sum += 1;
                }
            }

            if (!foundAValue || count <= 0) {
                newMap.put(calcd_PercentBASCommunicationFailureName, null);
                return;
            }

            newMap.put(calcd_PercentBASCommunicationFailureName, (double) sum * 100.0 / (double) count);

        } catch (Exception ex) {
            newMap.put(calcd_PercentBASCommunicationFailureName, null);
            logger.error(calcd_PercentBASCommunicationFailureName, ex);
        }
    }

    public void set_PercentPlantOff(Map<String, Object> newMap, Map<String, Object> oldMap) {
        String calcd_PercentPlantOffName = "C_" + EnumCalcPointFilter.PercentPlantOff.getCalculatedPointName();

        try {

            List<Object> objs = new ArrayList<>();
            for (String pointName : oldMap.keySet()) {
                if (pointName.contentEquals(EnumCalcPointFilter.PercentPlantOff.getCalculatedPointName())) {
                    continue;
                }
                UnCalculatedPoint rvDatapoint = uNameToUnCalculatedBucketPointMap.get(pointName);
                if (calculatedPointsInfo.isDependent(EnumCalcPointFilter.PercentPlantOff, rvDatapoint)) {
                    objs.add(oldMap.get(pointName));
                }
            }

            boolean foundAValue = false;
            int count = 0;
            int sum = 0;

            for (Object obj : objs) {
                count++;
                if (obj == null) {
                    continue;
                }

                if (!(obj instanceof Integer)) {
                    continue;
                }

                foundAValue = true;

                int val = (Integer) obj;

                if (val == EnumPercentStatusTypes.PlantOff.getStatusValue()) {
                    sum += 1;
                }
            }

            if (!foundAValue || count <= 0) {
                newMap.put(calcd_PercentPlantOffName, null);
                return;
            }

            newMap.put(calcd_PercentPlantOffName, (double) sum * 100.0 / (double) count);

        } catch (Exception ex) {
            newMap.put(calcd_PercentPlantOffName, null);
            logger.error(calcd_PercentPlantOffName, ex);
        }
    }

    //=================
    /*
    public void getChillerRunHoursBucket(CalculatedBucket newBucket, UnCalculatedBucket bucket) {

        //get chiller points
        List<String> chillerStatusPoints = new ArrayList<>();
        for (String pointName : bucketList.getUNames()) {
            if (uNameToUnCalculatedBucketPointMap.containsKey(pointName)) {
                UnCalculatedPoint oldPoint = uNameToUnCalculatedBucketPointMap.get(pointName);
                if (isChillerStatusPoint(oldPoint)) {
                    chillerStatusPoints.add(oldPoint.getName());
                }
            }
        }

        List<String> manufacturedPointNames = new ArrayList<>();

        Map<String, String> chillerStatusNameToManufacturedPointNameMap = new HashMap<>();
        Map<String, Double> chillerToRunHoursMap = new HashMap<>();
        for (String chillerNStatusPointName : chillerStatusPoints) {
            String manufacturedPointName = "C_" + chillerNStatusPointName;
            chillerStatusNameToManufacturedPointNameMap.put(chillerNStatusPointName, manufacturedPointName);
            manufacturedPointNames.add(manufacturedPointName);
            chillerToRunHoursMap.put(manufacturedPointName, 0.0);
        }

        try {

            for (DateTime timeStamp : bucket.getBucketTimestamps()) {

                Map<String, Object> pointNamesAndValues = bucket.getTimestampToUNameAndValueMap().get(timeStamp);

                for (String chillerNStatusPointName : chillerStatusPoints) {
                    Object chillerNStatusObj = pointNamesAndValues.get(chillerNStatusPointName);
                    if (chillerNStatusObj != null) {
                        String manfacturedName = chillerStatusNameToManufacturedPointNameMap.get(chillerNStatusPointName);
                        double runHours = chillerToRunHoursMap.get(manfacturedName);
                        runHours += ((Boolean) chillerNStatusObj) ? (double) 1 / (double) 12 : 0;
                        chillerToRunHoursMap.put(manfacturedName, runHours);
                    }
                }
            }

            for (String chillerNStatusPointName : chillerStatusPoints) {
                String manfacturedName = chillerStatusNameToManufacturedPointNameMap.get(chillerNStatusPointName);
                double runHours = chillerToRunHoursMap.get(manfacturedName);
                newBucket.getTimestampToPointNameAndSidToValueMap().get(bucket.getTimestamp()).put(manfacturedName, runHours);
            }

        } catch (Exception ex) {
            for (String chillerNStatusPointName : chillerStatusPoints) {
                String manfacturedName = chillerStatusNameToManufacturedPointNameMap.get(chillerNStatusPointName);
                newBucket.getTimestampToPointNameAndSidToValueMap().get(bucket.getTimestamp()).put(manfacturedName, null);
                logger.error(manfacturedName, ex);
            }
        }

    }

     */
    public void set_ChillersRunning(Map<String, Object> newMap, Map<String, Object> oldMap) {
        //Chillers running = sum( chillers on)
        String calChillersRunningName = "C_" + EnumCalcPointFilter.ChillersRunning.getCalculatedPointName();

        try {

            List<UnCalculatedPoint> points = calculatedPointsInfo.getAssociatedPoints(bucketType);

            int countOfChillersRunning = 0;
            boolean foundAValue = false;

            for (UnCalculatedPoint point : points) {

                for (Map association : point.getDatapointAssociations()) {
                    String assocName = (String) association.get("name");
                    if (assocName.contentEquals("Status")) {
                        Object statObj = oldMap.get(point.getName());

                        if (statObj != null) {
                            foundAValue = true;
                            boolean flag = (boolean) statObj;
                            countOfChillersRunning += (flag) ? 1 : 0;
                        }
                    }
                }
            }

            if (foundAValue) {
                newMap.put(calChillersRunningName, countOfChillersRunning);
            } else {
                newMap.put(calChillersRunningName, null);
            }
        } catch (Exception ex) {
            newMap.put(calChillersRunningName, null);
            logger.error(calChillersRunningName, ex);

        }

    }

    public void set_TotalPercentLoad(Map<String, Object> newMap, Map<String, Object> oldMap) {
        //% TotalLoad = (TotalTon / TotalCapacity) * 100.0
        String calcTotalLoad = "C_" + EnumCalcPointFilter.PercentTotalLoad.getCalculatedPointName();

        try {

            String totalTonName = EnumCalcPointFilter.TotalTon.getCalculatedPointName();
            String totalCapacityName = EnumDependentPointFilter.TotalCapacity.getDependentPointName();

            Object totalTonObj = oldMap.get(totalTonName);
            Object totalCapcityObj = getConfigPointValue(totalCapacityName);

            if (totalTonObj == null || totalCapcityObj == null) {
                newMap.put(calcTotalLoad, null);
                return;
            }

            double totalTonValue = getDouble(totalTonObj);
            double totalCapcityValue = getDouble(totalCapcityObj);

            if (Math.abs(totalCapcityValue) > 0.001) {
                newMap.put(calcTotalLoad, (totalTonValue / totalCapcityValue) * 100.0);
            } else {
                newMap.put(calcTotalLoad, null);
            }

        } catch (Exception ex) {
            newMap.put(calcTotalLoad, null);
            logger.error(calcTotalLoad, ex);
        }
    }

    public void set_ChillersRunHours(Map<String, Object> newMap, UnCalculatedBucket bucket) {

        try {

            Map<String, Double> chillerRunHours = new HashMap<>();
            for (DateTime timeStamp : bucket.getBucketTimestamps()) {
                Map<String, Object> uNameToValues = bucket.getTimestampToUNameAndValueMap().get(timeStamp);
                for (String uName : uNameToValues.keySet()) {
                    UnCalculatedPoint point = uNameToUnCalculatedBucketPointMap.get(uName);

                    for (Map association : point.getDatapointAssociations()) {
                        String assocName = (String) association.get("name");
                        if (assocName.contentEquals("Status")) {

                            String chillerSid = point.getSid();
                            String chillerName = (chillerSid.split("\\."))[2];

                            String oldMapPointName = chillerName + ".Status";
                            String newMapPointName = chillerName + ".C_Status";

                            if (!chillerSid.contains("e:chiller")) {
                                continue;
                            }

                            if (!chillerRunHours.containsKey(newMapPointName)) {
                                chillerRunHours.put(newMapPointName, 0.0);
                            }

                            Object statObj = uNameToValues.get(oldMapPointName);

                            if (statObj != null) {
                                
                                try {
                                    boolean flag = (boolean) statObj;
                                    Double currRunHours = chillerRunHours.get(newMapPointName);
                                    currRunHours += (flag) ? 1.0 / 12.0 : 0.0;
                                    chillerRunHours.put(newMapPointName, currRunHours);
                                } catch (Exception ex) {
                                    System.out.println("oops---chiller run hours");
                                }

                            }
                        }
                    }
                }
            }

            for (String newMapPointName : chillerRunHours.keySet()) {
                newMap.put(newMapPointName, chillerRunHours.get(newMapPointName));
            }

        } catch (Exception ex) {
            newMap.put("?", null);
            logger.error("?", ex);

        }

    }
}
