package Model.DataModels.ReportVerification.PostProcess;

import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointFilter;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumConfigPointNames;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumDependentPointFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportTotalsCalculator {

    private final EnumCalcPointFilter calcPoint;
    private final BucketListStats bucketListStats;

    private Map< String, Object> keyValuePairs;
    private List<String> namesInOrder;

    public ReportTotalsCalculator(EnumCalcPointFilter calcPoint, BucketListStats bucketListStats) {
        this.calcPoint = calcPoint;
        this.bucketListStats = bucketListStats;

        setKeyValuePairs(calcPoint);

    }

    public List< String> getNamesInOrder() {
        return this.namesInOrder;
    }

    public Map< String, Object> getKeyValuePairs() {
        return this.keyValuePairs;
    }

    private void setKeyValuePairs(EnumCalcPointFilter calcPoint) {

        keyValuePairs = new HashMap<>();
        namesInOrder = new ArrayList<>();

        switch (calcPoint) {

            case kWTon: {
                namesInOrder.add(EnumCalcPointFilter.TotalkW.getCalculatedPointName());
                namesInOrder.add(EnumCalcPointFilter.TotalTon.getCalculatedPointName());
                namesInOrder.add(EnumCalcPointFilter.kWTon.getCalculatedPointName());

                double totalKw = bucketListStats.getSum(EnumCalcPointFilter.TotalkW.getCalculatedPointName());
                double totalTon = bucketListStats.getSum(EnumCalcPointFilter.TotalTon.getCalculatedPointName());
                double kWTon = (Math.abs(totalTon) >= 0.001) ? totalKw / totalTon : 0.0;
                keyValuePairs.put(EnumCalcPointFilter.TotalkW.getCalculatedPointName(), totalKw);
                keyValuePairs.put(EnumCalcPointFilter.TotalTon.getCalculatedPointName(), totalTon);
                keyValuePairs.put(EnumCalcPointFilter.kWTon.getCalculatedPointName(), kWTon);
                break;

            }

            case kWTon_Weight: {
                namesInOrder.add(EnumCalcPointFilter.TotalTon.getCalculatedPointName());
                namesInOrder.add(EnumCalcPointFilter.kWTon_Weight.getCalculatedPointName());

                double totalTon = bucketListStats.getSum(EnumCalcPointFilter.TotalTon.getCalculatedPointName());
                double kWTon_Weight = totalTon;
                keyValuePairs.put(EnumCalcPointFilter.TotalTon.getCalculatedPointName(), totalTon);
                keyValuePairs.put(EnumCalcPointFilter.kWTon_Weight.getCalculatedPointName(), kWTon_Weight);

                break;
            }

            case ChillerkWTon: {
                namesInOrder.add(EnumCalcPointFilter.ChillerkW.getCalculatedPointName());
                namesInOrder.add(EnumCalcPointFilter.TotalTon.getCalculatedPointName());
                namesInOrder.add(EnumCalcPointFilter.ChillerkWTon.getCalculatedPointName());

                double chillerkW = bucketListStats.getSum(EnumCalcPointFilter.ChillerkW.getCalculatedPointName());
                double totalTon = bucketListStats.getSum(EnumCalcPointFilter.TotalTon.getCalculatedPointName());
                double kWTon = (Math.abs(totalTon) >= 0.001) ? chillerkW / totalTon : 0.0;
                keyValuePairs.put(EnumCalcPointFilter.ChillerkW.getCalculatedPointName(), chillerkW);
                keyValuePairs.put(EnumCalcPointFilter.TotalTon.getCalculatedPointName(), totalTon);
                keyValuePairs.put(EnumCalcPointFilter.ChillerkWTon.getCalculatedPointName(), kWTon);

                break;
            }

            case ChillerkW: {
                namesInOrder.add(EnumCalcPointFilter.ChillerkW.getCalculatedPointName());
                double chillerkW = bucketListStats.getSum(EnumCalcPointFilter.ChillerkW.getCalculatedPointName());
                keyValuePairs.put(EnumCalcPointFilter.ChillerkW.getCalculatedPointName(), chillerkW);
                break;
            }

            case kWh: {
                namesInOrder.add(EnumCalcPointFilter.kWh.getCalculatedPointName());
                double kWh = bucketListStats.getMean(EnumCalcPointFilter.kWh.getCalculatedPointName());
                keyValuePairs.put(EnumCalcPointFilter.kWh.getCalculatedPointName(), kWh);
                break;
            }

            case TotalkWh: {
                namesInOrder.add(EnumCalcPointFilter.TotalkWh.getCalculatedPointName());
                double avgkWh = bucketListStats.getMean(EnumCalcPointFilter.TotalkWh.getCalculatedPointName());

                int hours = bucketListStats.getNumHours();
                double kWh = avgkWh * hours;

                //keyValuePairs.put(EnumCalcPointFilter.TotalkWh.getCalculatedPointName(), kWh);
                
                                
                double sum = bucketListStats.getSum("C_" + EnumCalcPointFilter.TotalkWh.getCalculatedPointName());
                keyValuePairs.put(EnumCalcPointFilter.TotalkWh.getCalculatedPointName(), sum);

                break;
            }

            case TotalkW: {
                namesInOrder.add(EnumCalcPointFilter.TotalkW.getCalculatedPointName());
                double totalKw = bucketListStats.getSum(EnumCalcPointFilter.TotalkW.getCalculatedPointName());
                keyValuePairs.put(EnumCalcPointFilter.TotalkW.getCalculatedPointName(), totalKw);
                break;
            }

            case TotalTon: {
                namesInOrder.add(EnumCalcPointFilter.TotalTon.getCalculatedPointName());
                double totalTon = bucketListStats.getSum(EnumCalcPointFilter.TotalTon.getCalculatedPointName());
                keyValuePairs.put(EnumCalcPointFilter.TotalTon.getCalculatedPointName(), totalTon);
                break;
            }

            case TonHours: {
                String avgOfAvgsName = "AvgOfAvg";
                //namesInOrder.add(EnumCalcPointFilter.TonHours.getCalculatedPointName());
                namesInOrder.add(avgOfAvgsName);

                double avgTonHours = bucketListStats.getMean(EnumCalcPointFilter.TotalTon.getCalculatedPointName());

                int hours = bucketListStats.getNumHours();
                double tonHours = avgTonHours * hours;

                double avgOfAvgs = bucketListStats.getMean(EnumCalcPointFilter.TonHours.getCalculatedPointName());

                //keyValuePairs.put(EnumCalcPointFilter.TonHours.getCalculatedPointName(), tonHours);
                keyValuePairs.put(avgOfAvgsName, avgOfAvgs * hours);
                break;
            }

            case DollarsCost: {
                namesInOrder.add(EnumCalcPointFilter.DollarsCost.getCalculatedPointName());
                double dollarsCost = bucketListStats.getSum(EnumCalcPointFilter.DollarsCost.getCalculatedPointName());
                keyValuePairs.put(EnumCalcPointFilter.DollarsCost.getCalculatedPointName(), dollarsCost);
                break;
            }

            case CO2Produced: {
                namesInOrder.add(EnumCalcPointFilter.CO2Produced.getCalculatedPointName());

                //double avgkWh = bucketListStats.getMean(EnumCalcPointFilter.TotalkWh.getCalculatedPointName());

                //int hours = bucketListStats.getNumHours();
                
                //double kWh = avgkWh * hours;
                
                double kWh = bucketListStats.getSum(EnumCalcPointFilter.TotalkWh.getCalculatedPointName());
                
                double configPointValue = (double) (bucketListStats.getConfigPoints().get(EnumConfigPointNames.CO2EmissionFactor.getConfigPointName()));

                keyValuePairs.put(EnumCalcPointFilter.CO2Produced.getCalculatedPointName(), configPointValue * kWh);
                break;
            }

            case PercentOptimized: {
                namesInOrder.add(EnumCalcPointFilter.PercentOptimized.getCalculatedPointName());
                int countWhereEquals = bucketListStats.countWhereEquals(EnumDependentPointFilter.OptimizationStatusEnum.getDependentPointName(), 1);
                int totalCount = bucketListStats.getCountNotNull(EnumDependentPointFilter.OptimizationStatusEnum.getDependentPointName());
                if (totalCount <= 0) {
                    keyValuePairs.put(EnumCalcPointFilter.PercentOptimized.getCalculatedPointName(), 0);
                } else {
                    keyValuePairs.put(EnumCalcPointFilter.PercentOptimized.getCalculatedPointName(), (double) countWhereEquals * 100 / (double) totalCount);
                }
                break;
            }

            case PercentPartiallyOptimized: {
                namesInOrder.add(EnumCalcPointFilter.PercentPartiallyOptimized.getCalculatedPointName());
                int countWhereEquals = bucketListStats.countWhereEquals(EnumDependentPointFilter.OptimizationStatusEnum.getDependentPointName(), 2);
                int totalCount = bucketListStats.getCountNotNull(EnumDependentPointFilter.OptimizationStatusEnum.getDependentPointName());
                if (totalCount <= 0) {
                    keyValuePairs.put(EnumCalcPointFilter.PercentPartiallyOptimized.getCalculatedPointName(), 0);
                } else {
                    keyValuePairs.put(EnumCalcPointFilter.PercentPartiallyOptimized.getCalculatedPointName(), (double) countWhereEquals * 100 / (double) totalCount);
                }
                break;
            }

            case PercentOptimizationDisabled: {
                namesInOrder.add(EnumCalcPointFilter.PercentOptimizationDisabled.getCalculatedPointName());
                int countWhereEquals = bucketListStats.countWhereEquals(EnumDependentPointFilter.OptimizationStatusEnum.getDependentPointName(), 3);
                int totalCount = bucketListStats.getCountNotNull(EnumDependentPointFilter.OptimizationStatusEnum.getDependentPointName());
                if (totalCount <= 0) {
                    keyValuePairs.put(EnumCalcPointFilter.PercentOptimizationDisabled.getCalculatedPointName(), 0);
                } else {
                    keyValuePairs.put(EnumCalcPointFilter.PercentOptimizationDisabled.getCalculatedPointName(), (double) countWhereEquals * 100 / (double) totalCount);
                }
                break;
            }

            case PercentPlantOff: {
                namesInOrder.add(EnumCalcPointFilter.PercentPlantOff.getCalculatedPointName());
                int countWhereEquals = bucketListStats.countWhereEquals(EnumDependentPointFilter.OptimizationStatusEnum.getDependentPointName(), 4);
                int totalCount = bucketListStats.getCountNotNull(EnumDependentPointFilter.OptimizationStatusEnum.getDependentPointName());
                if (totalCount <= 0) {
                    keyValuePairs.put(EnumCalcPointFilter.PercentPlantOff.getCalculatedPointName(), 0);
                } else {
                    keyValuePairs.put(EnumCalcPointFilter.PercentPlantOff.getCalculatedPointName(), (double) countWhereEquals * 100 / (double) totalCount);
                }
                break;
            }

            case PercentBASCommunicationFailure: {
                namesInOrder.add(EnumCalcPointFilter.PercentBASCommunicationFailure.getCalculatedPointName());
                int countWhereEquals = bucketListStats.countWhereEquals(EnumDependentPointFilter.OptimizationStatusEnum.getDependentPointName(), 5);
                int totalCount = bucketListStats.getCountNotNull(EnumDependentPointFilter.OptimizationStatusEnum.getDependentPointName());
                if (totalCount <= 0) {
                    keyValuePairs.put(EnumCalcPointFilter.PercentBASCommunicationFailure.getCalculatedPointName(), 0);
                } else {
                    keyValuePairs.put(EnumCalcPointFilter.PercentBASCommunicationFailure.getCalculatedPointName(), (double) countWhereEquals * 100 / (double) totalCount);
                }
                break;
            }

            case ChillerRunHours: {

                for (String name : bucketListStats.getDataPointNamesAndSids()) {

                    if (name.contains("C_")) {
                        namesInOrder.add(name);
                        double sum = bucketListStats.getSum(name);
                        keyValuePairs.put(name, sum);
                    }

                }

                break;
            }

        }

    }

}
