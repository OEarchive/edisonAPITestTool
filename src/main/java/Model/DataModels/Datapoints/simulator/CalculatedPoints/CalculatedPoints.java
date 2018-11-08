package Model.DataModels.Datapoints.simulator.CalculatedPoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatedPoints {

    private final Map<EnumCalcPoints, List<EnumDependentPoints>> cpToDPListMap;

    public CalculatedPoints() {
        cpToDPListMap = new HashMap<>();

        cpToDPListMap.put(EnumCalcPoints.TotalCapacity, new ArrayList<EnumDependentPoints>());
        cpToDPListMap.put(EnumCalcPoints.MinimumChilledWaterFlow, new ArrayList<EnumDependentPoints>());
        cpToDPListMap.put(EnumCalcPoints.BlendedUtilityRate, new ArrayList<EnumDependentPoints>());
        cpToDPListMap.put(EnumCalcPoints.CO2EmissionFactor, new ArrayList<EnumDependentPoints>());
        
        cpToDPListMap.put(EnumCalcPoints.kWTon, Arrays.asList(EnumDependentPoints.TotalkW, EnumDependentPoints.TotalTon));
        cpToDPListMap.put(EnumCalcPoints.kWTon_Weight, Arrays.asList(EnumDependentPoints.TotalTon));
        cpToDPListMap.put(EnumCalcPoints.ChillerkWTon, Arrays.asList(EnumDependentPoints.ChillerkW, EnumDependentPoints.TotalTon));
        cpToDPListMap.put(EnumCalcPoints.ChillerkW, Arrays.asList(EnumDependentPoints.kW));
        cpToDPListMap.put(EnumCalcPoints.kWh, Arrays.asList(EnumDependentPoints.kW ));
        cpToDPListMap.put(EnumCalcPoints.TotalkW, Arrays.asList(EnumDependentPoints.kW));
        cpToDPListMap.put(EnumCalcPoints.TotalkWh, Arrays.asList(EnumDependentPoints.kWh));
        
        cpToDPListMap.put(EnumCalcPoints.TotalTon, Arrays.asList(
                EnumDependentPoints.Ton,
                EnumDependentPoints.ChillerkW,
                EnumDependentPoints.ChilledWaterFlow,
                EnumDependentPoints.MinimumChilledWaterFlow,
                EnumDependentPoints.TotalCapacity));
        
        
        cpToDPListMap.put(EnumCalcPoints.Ton, Arrays.asList(EnumDependentPoints.ChilledWaterReturnTemperature, EnumDependentPoints.ChilledWaterSupplyTemperature, EnumDependentPoints.ChilledWaterFlow));
        cpToDPListMap.put(EnumCalcPoints.TonHours, Arrays.asList(EnumDependentPoints.TotalTon));
        
        cpToDPListMap.put(EnumCalcPoints.BaselinekW, Arrays.asList(EnumDependentPoints.BaselinekW, EnumDependentPoints.TotalTon));
        cpToDPListMap.put(EnumCalcPoints.BaselinekWh, Arrays.asList(EnumDependentPoints.BaselinekW));
        cpToDPListMap.put(EnumCalcPoints.BaselinekWTon, Arrays.asList(EnumDependentPoints.kWTon));
        cpToDPListMap.put(EnumCalcPoints.BaselinekWTon_Weight, Arrays.asList(EnumDependentPoints.kWTon));
        cpToDPListMap.put(EnumCalcPoints.BaselineDollarsCost, Arrays.asList(EnumDependentPoints.BaselinekWh, EnumDependentPoints.BlendedUtilityRate));
        cpToDPListMap.put(EnumCalcPoints.DollarsCost, Arrays.asList(EnumDependentPoints.TotalkWh, EnumDependentPoints.BlendedUtilityRate));
        cpToDPListMap.put(EnumCalcPoints.DollarsSaved, Arrays.asList(EnumDependentPoints.kWhDelta, EnumDependentPoints.BlendedUtilityRate));
        cpToDPListMap.put(EnumCalcPoints.BaselineCO2Produced, Arrays.asList(EnumDependentPoints.BaselinekWh,EnumDependentPoints.CO2EmissionFactor));
        cpToDPListMap.put(EnumCalcPoints.CO2Produced, Arrays.asList(EnumDependentPoints.TotalkWh, EnumDependentPoints.CO2EmissionFactor));
        cpToDPListMap.put(EnumCalcPoints.CO2Saved, Arrays.asList(EnumDependentPoints.kWhDelta, EnumDependentPoints.CO2EmissionFactor));
        
        cpToDPListMap.put(EnumCalcPoints.kWDelta, Arrays.asList(EnumDependentPoints.BaselinekW, EnumDependentPoints.TotalkW ));
        cpToDPListMap.put(EnumCalcPoints.kWhDelta, Arrays.asList(EnumDependentPoints.kWDelta));
        
        cpToDPListMap.put(EnumCalcPoints.PercentOptimized, Arrays.asList(EnumDependentPoints.OptimizationStatusEnum));
        cpToDPListMap.put(EnumCalcPoints.PercentPartiallyOptimized, Arrays.asList(EnumDependentPoints.OptimizationStatusEnum));
        cpToDPListMap.put(EnumCalcPoints.PercentOptimizationDisabled, Arrays.asList(EnumDependentPoints.OptimizationStatusEnum));
        cpToDPListMap.put(EnumCalcPoints.PercentPlantOff, Arrays.asList(EnumDependentPoints.OptimizationStatusEnum));
        cpToDPListMap.put(EnumCalcPoints.PercentBASCommunicationFailure, Arrays.asList(EnumDependentPoints.OptimizationStatusEnum));
        
        cpToDPListMap.put(EnumCalcPoints.Optimized, Arrays.asList(EnumDependentPoints.OptimizationStatusEnum));
        cpToDPListMap.put(EnumCalcPoints.PartiallyOptimized, Arrays.asList(EnumDependentPoints.OptimizationStatusEnum));
        cpToDPListMap.put(EnumCalcPoints.OptimizationDisabled, Arrays.asList(EnumDependentPoints.OptimizationStatusEnum));
        cpToDPListMap.put(EnumCalcPoints.PlantOff, Arrays.asList(EnumDependentPoints.OptimizationStatusEnum));
        cpToDPListMap.put(EnumCalcPoints.BASCommunicationFailure, new ArrayList<EnumDependentPoints>());
        cpToDPListMap.put(EnumCalcPoints.OptimizationStatus, Arrays.asList(EnumDependentPoints.OptimizationStatusEnum));
        //cpToDPListMap.put(EnumCalcPoints.PlantCOP, Arrays.asList(EnumDependentPoints.kWTon));
        //cpToDPListMap.put(EnumCalcPoints.PlantCOP_Weight, Arrays.asList(EnumDependentPoints.TotalTon));
        //cpToDPListMap.put(EnumCalcPoints.ChillersRunning, Arrays.asList(EnumDependentPoints.Status));

    }

    public Map<EnumCalcPoints, List<EnumDependentPoints>> getMap() {
        return cpToDPListMap;
    }
}
