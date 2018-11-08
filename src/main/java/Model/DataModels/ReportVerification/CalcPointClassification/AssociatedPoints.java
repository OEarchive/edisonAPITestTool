package Model.DataModels.ReportVerification.CalcPointClassification;

import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.ReportVerification.CalcPointClassification.Equipment.EquipmentInfo;
import Model.DataModels.ReportVerification.PreProcess.UnCalculatedPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AssociatedPoints {

    private final EquipmentInfo equipInfo;
    private final Map<EnumCalcPointFilter, CalculatedPointAndDependenciesFilters> calcPointToInfoMap;
    private List<UnCalculatedPoint> rvDataPoints;
    private Map<String,Object> configPoints;

    public AssociatedPoints(EquipmentInfo equipInfo, List<DatapointsAndMetadataResponse> listOfMetadata) {
        this.equipInfo = equipInfo;
        this.rvDataPoints = new ArrayList<>();
        this.configPoints = new HashMap<>();
        for (DatapointsAndMetadataResponse metaData : listOfMetadata) {
            rvDataPoints.add(new UnCalculatedPoint(metaData));
            
            String pointName = metaData.getName();
            if( getConfigPointNames().contains(pointName)){
                configPoints.put(pointName, metaData.getValue());
            }
        }

        calcPointToInfoMap = new HashMap<>();
        setList();
    }
    
    
    public Map<String,Object> getConfigPoints(){
        return configPoints;
    }
    
    
    private List<String> getConfigPointNames(){
        
        return EnumConfigPointNames.getPointNames();
        /*
        List<String>names = new ArrayList<>();
        
        names.add("TotalCapacity");
        names.add("MinimumChilledWaterFlow");
        names.add("BlendedUtilityRate");
        names.add("CO2EmissionFactor");
        
        return names;
        */
    }
    
    

    public void setPoints(List<UnCalculatedPoint> rvDataPoints) {
        this.rvDataPoints = rvDataPoints;
    }

    public CalculatedPointAndDependenciesFilters getCaclulatedPointAndDependencies(EnumCalcPointFilter calcPoint) {
        return calcPointToInfoMap.get(calcPoint);
    }

    public List<UnCalculatedPoint> getAssociatedPoints(EnumCalcPointFilter calcPoint) {
        List<UnCalculatedPoint> list = new ArrayList<>();

        for (UnCalculatedPoint uPoint : rvDataPoints) {

            if (uPoint.getName().contentEquals(calcPoint.getCalculatedPointName())
                    || isDependent(calcPoint, uPoint)) {
                list.add(uPoint);
            }
        }

        return list;
    }

    public boolean isDependent(EnumCalcPointFilter calcPoint, UnCalculatedPoint dataPoint) {
        
        String temp = dataPoint.getName();
        
        CalculatedPointAndDependenciesFilters calcPointInfo = calcPointToInfoMap.get(calcPoint);

        if (calcPointInfo == null) {
            throw new NoSuchFieldError("no info on :" + calcPoint.name());
        }

        if (calcPoint.getCalculatedPointName().contentEquals(dataPoint.getName())) {
            return true;
        }

        for (EnumCalcPointFilter otherCalcPoint : calcPointInfo.getOtherCalcPointFilters()) {
            if (otherCalcPoint.getCalculatedPointName().contentEquals(dataPoint.getName())) {
                return true;
            }
        }
        
        
        

        for (EnumDependentPointFilter basePoint : calcPointInfo.getDependentPointFilters()) {

            if (matchesDependentPointFilter(basePoint, dataPoint)) {
                return true;
            }
        }

        return false;
    }

    public boolean matchesCalcPointFilter(EnumCalcPointFilter calcPoint, UnCalculatedPoint dataPoint) {

        return calcPoint.getCalculatedPointName().contentEquals(dataPoint.getName());
    }

    public boolean matchesDependentPointFilter(EnumDependentPointFilter depPointInfo, UnCalculatedPoint dataPoint) {
        
        boolean hasAssocation = false;
        boolean hasSid = false;

        String detPointAssFilter = depPointInfo.getAssociationFilter();
        String depPointSidFilter = depPointInfo.getSidFilter();
        
        
        //if the dependent filter as an "e:" and the uname does not, bail
        if( !depPointSidFilter.contentEquals("SITE_ONLY") && depPointSidFilter.length() > 0 && !dataPoint.getSid().contains(depPointSidFilter) ){
            return false;
        }
        
       
        if( depPointInfo.getDependentPointName().contentEquals(dataPoint.getName()) &&
                depPointSidFilter.length() <= 0 
                ){
            return true;
        }

        for (Map ass : dataPoint.getDatapointAssociations()) {
            String name = (String) ass.get("name");
            String sid = (String) ass.get("sid");
            

            if (detPointAssFilter.length() > 0 && name.contentEquals(detPointAssFilter)) {
                hasAssocation = true;
            }

            if (depPointSidFilter.length() > 0 && sid.contains(depPointSidFilter)) {
                hasSid = true;
            }

            if (depPointSidFilter.length() > 0 && depPointSidFilter.contentEquals("SITE_ONLY")) {

                String[] pieces = sid.split(":");
                hasSid = pieces.length <= 3;
            }
        }
        
        return hasAssocation && hasSid;
    }

    public void setList() {

        //kWTon
        {

            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.kWTon);
            cpts.add(EnumCalcPointFilter.TotalkW);
            cpts.add(EnumCalcPointFilter.TotalTon);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.kWTon);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.kWTon, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.kWTon, cpts, dpts, partnerCpts));
        }

        //kWTon_Weight
        {

            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.kWTon_Weight);
            cpts.add(EnumCalcPointFilter.TotalTon);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.kWTon_Weight);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.kWTon_Weight, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.kWTon_Weight, cpts, dpts, partnerCpts));
        }

        //ChillerkWTon
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.ChillerkWTon);
            cpts.add(EnumCalcPointFilter.ChillerkW);
            cpts.add(EnumCalcPointFilter.TotalTon);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.ChillerkWTon);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.ChillerkWTon, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.ChillerkWTon, cpts, dpts, partnerCpts));
        }

        //ChillerkW
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.ChillerkW);
            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.ChillerkW);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.filter_chiller_kw);

            ArrayList<EnumDependentPointFilter> dPartners = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.ChillerkW, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.ChillerkW, cpts, dpts, partnerCpts, dPartners));
        }

        //kWh
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.kWh);
            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.kWh);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.filter_chiller_kWh);

            calcPointToInfoMap.put(EnumCalcPointFilter.kWh, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.kWh, cpts, dpts, partnerCpts));
        }

        //TotalkWh
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.TotalkWh);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.TotalkWh);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.filter_all_kw);

            calcPointToInfoMap.put(EnumCalcPointFilter.TotalkWh, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.TotalkWh, cpts, dpts, partnerCpts));
        }

        //TotalkW
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.TotalkW);
            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.TotalkW);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.filter_all_kw);

            calcPointToInfoMap.put(EnumCalcPointFilter.TotalkW, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.TotalkW, cpts, dpts, partnerCpts));
        }

        //TotalTon
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.TotalTon);
            cpts.add(EnumCalcPointFilter.Ton);
            cpts.add(EnumCalcPointFilter.ChillerkW);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.TotalTon);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.ChilledWaterFlow);
            dpts.add(EnumDependentPointFilter.MinimumChilledWaterFlow);
            dpts.add(EnumDependentPointFilter.TotalCapacity);

            calcPointToInfoMap.put(EnumCalcPointFilter.TotalTon, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.TotalTon, cpts, dpts, partnerCpts));
        }

        //Ton
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.Ton);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.Ton);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.ChilledWaterFlow);
            dpts.add(EnumDependentPointFilter.ChilledWaterReturnTemperature);
            dpts.add(EnumDependentPointFilter.ChilledWaterSupplyTemperature);

            calcPointToInfoMap.put(EnumCalcPointFilter.Ton, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.Ton, cpts, dpts, partnerCpts));
        }

        //TonHours
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.TonHours);
            cpts.add(EnumCalcPointFilter.TotalTon);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.TonHours);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.TonHours, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.TonHours, cpts, dpts, partnerCpts));
        }

        //BaselinekW
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.BaselinekW);
            cpts.add(EnumCalcPointFilter.BaselinekWTon);
            cpts.add(EnumCalcPointFilter.TotalTon);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.BaselinekW);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.BaselinekW, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.BaselinekW, cpts, dpts, partnerCpts));
        }

        //BaselinekWh
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.BaselinekWh);
            cpts.add(EnumCalcPointFilter.BaselinekW);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.BaselinekWh);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.BaselinekWh, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.BaselinekWh, cpts, dpts, partnerCpts));
        }

        //BaselinekWTon
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.BaselinekWTon);
            cpts.add(EnumCalcPointFilter.kWTon);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.BaselinekWTon);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.BaselinekWTon, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.BaselinekWTon, cpts, dpts, partnerCpts));
        }

        //BaselinekWTon_Weight
        //ToDo: Check this
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.BaselinekWTon_Weight);
            cpts.add(EnumCalcPointFilter.TotalTon);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.BaselinekWTon_Weight);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.BaselinekWTon_Weight, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.BaselinekWTon_Weight, cpts, dpts, partnerCpts));
        }

        //BaselineDollarsCost
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.BaselineDollarsCost);
            cpts.add(EnumCalcPointFilter.BaselinekWh);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.BaselineDollarsCost);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.BlendedUtilityRate);

            calcPointToInfoMap.put(EnumCalcPointFilter.BaselineDollarsCost, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.BaselineDollarsCost, cpts, dpts, partnerCpts));
        }

        //DollarsCost
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.DollarsCost);
            cpts.add(EnumCalcPointFilter.TotalkWh);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.DollarsCost);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.BlendedUtilityRate);

            calcPointToInfoMap.put(EnumCalcPointFilter.DollarsCost, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.DollarsCost, cpts, dpts, partnerCpts));
        }

        //DollarsSaved
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.DollarsSaved);
            cpts.add(EnumCalcPointFilter.kWhDelta);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.DollarsSaved);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.BlendedUtilityRate);

            calcPointToInfoMap.put(EnumCalcPointFilter.DollarsSaved, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.DollarsSaved, cpts, dpts, partnerCpts));
        }

        //BaselineCO2Produced
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.BaselineCO2Produced);
            cpts.add(EnumCalcPointFilter.BaselinekWh);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.BaselineCO2Produced);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.CO2EmissionFactor);

            calcPointToInfoMap.put(EnumCalcPointFilter.BaselineCO2Produced, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.BaselineCO2Produced, cpts, dpts, partnerCpts));
        }

        //CO2Produced
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.CO2Produced);
            cpts.add(EnumCalcPointFilter.TotalkWh);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.CO2Produced);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.CO2EmissionFactor);

            calcPointToInfoMap.put(EnumCalcPointFilter.CO2Produced, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.CO2Produced, cpts, dpts, partnerCpts));
        }

        //CO2Saved
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.CO2Saved);
            cpts.add(EnumCalcPointFilter.kWhDelta);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.CO2Saved);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.CO2EmissionFactor);

            calcPointToInfoMap.put(EnumCalcPointFilter.CO2Saved, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.CO2Saved, cpts, dpts, partnerCpts));
        }

        //kWDelta
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.kWDelta);
            cpts.add(EnumCalcPointFilter.TotalkW);
            cpts.add(EnumCalcPointFilter.BaselinekW);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.kWDelta);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.kWDelta, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.kWDelta, cpts, dpts, partnerCpts));
        }

        //kW__h__Delta
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.kWhDelta);
            cpts.add(EnumCalcPointFilter.kWDelta);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.kWhDelta);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.kWhDelta, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.kWhDelta, cpts, dpts, partnerCpts));
        }

        //PercentOptimized
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.PercentOptimized);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.PercentOptimized);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.PercentOptimized, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PercentOptimized, cpts, dpts, partnerCpts));

        }

        //PercentPartiallyOptimized
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.PercentPartiallyOptimized);
            
            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.PercentPartiallyOptimized);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.PercentPartiallyOptimized, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PercentPartiallyOptimized, cpts, dpts, partnerCpts));

        }

        //PercentOptimizationDisabled
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.PercentOptimizationDisabled);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.PercentOptimizationDisabled);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.PercentOptimizationDisabled, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PercentOptimizationDisabled, cpts, dpts, partnerCpts));

        }

        //PercentPlantOff
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.PercentPlantOff);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.PercentPlantOff);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.PercentPlantOff, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PercentPlantOff, cpts, dpts, partnerCpts));

        }

        //PercentBASCommunicationFailure
        //TODO:  Check this
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.PercentBASCommunicationFailure);

            ArrayList<EnumCalcPointFilter> partnerCpts = new ArrayList<>();
            partnerCpts.add(EnumCalcPointFilter.PercentBASCommunicationFailure);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.PercentBASCommunicationFailure, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PercentBASCommunicationFailure, cpts, dpts, partnerCpts));

        }

        //Optimized
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.Optimized, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.Optimized, cpts, dpts));

        }

        //PartiallyOptimized
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.PartiallyOptimized, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PartiallyOptimized, cpts, dpts));

        }

        //OptimizationDisabled
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.OptimizationDisabled, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.OptimizationDisabled, cpts, dpts));

        }

        //PlantOff
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.PlantOff, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PlantOff, cpts, dpts));

        }

        //BASCommunicationFailure
        // TODO: CheckThis
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.BASCommunicationFailure, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.BASCommunicationFailure, cpts, dpts));

        }

        //OptimizationStatus
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.OptimizationStatusEnum);

            calcPointToInfoMap.put(EnumCalcPointFilter.OptimizationStatus, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.OptimizationStatus, cpts, dpts));

        }

        //PlantCOP
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.kWTon);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.PlantCOP, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PlantCOP, cpts, dpts));
        }

        //PlantCOP_Weight
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.TotalTon);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.PlantCOP_Weight, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PlantCOP_Weight, cpts, dpts));
        }

        //ChillersRunning
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.ChillersRunning);

            ArrayList<EnumCalcPointFilter> cPartners = new ArrayList<>();
            cPartners.add(EnumCalcPointFilter.ChillersRunning);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.ChillerStatus);

            ArrayList<EnumDependentPointFilter> dPartners = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.ChillersRunning,
                    new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.ChillersRunning, cpts, dpts, cPartners, dPartners));
        }

        //TotalLoad
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.PercentTotalLoad);
            cpts.add(EnumCalcPointFilter.TotalTon);
            
            ArrayList<EnumCalcPointFilter> cPartners = new ArrayList<>();
            cPartners.add(EnumCalcPointFilter.PercentTotalLoad);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.TotalCapacity);
            
            ArrayList<EnumDependentPointFilter> dPartners = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.PercentTotalLoad, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PercentTotalLoad, cpts, dpts, cPartners, dPartners));
        }

        //ChillerkWh
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.ChillerkWh);
            
            ArrayList<EnumCalcPointFilter> cPartners = new ArrayList<>();
            cPartners.add(EnumCalcPointFilter.ChillerkWh);


            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.filter_chiller_kWh);
            
            ArrayList<EnumDependentPointFilter> dPartners = new ArrayList<>();

            calcPointToInfoMap.put(EnumCalcPointFilter.ChillerkWh, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.ChillerkWh, cpts, dpts, cPartners, dPartners));
        }

        //ChillerRunHours
        {

            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.ChillerRunHours);
            
            ArrayList<EnumCalcPointFilter> cPartners = new ArrayList<>();
            cpts.add(EnumCalcPointFilter.ChillerRunHours);

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.ChillerStatus);

            ArrayList<EnumDependentPointFilter> dPartners = new ArrayList<>();
            dPartners.add(EnumDependentPointFilter.ChillerStatus);

            calcPointToInfoMap.put(EnumCalcPointFilter.ChillerRunHours, new CalculatedPointAndDependenciesFilters(
                    EnumCalcPointFilter.ChillerRunHours, cpts, dpts, cPartners, dPartners));
        }

        //PercentMissingData
        {
            ArrayList<EnumCalcPointFilter> cpts = new ArrayList<>();

            ArrayList<EnumDependentPointFilter> dpts = new ArrayList<>();
            dpts.add(EnumDependentPointFilter.filter_samples);

            calcPointToInfoMap.put(EnumCalcPointFilter.PercentMissingData, new CalculatedPointAndDependenciesFilters(EnumCalcPointFilter.PercentMissingData, cpts, dpts));
        }

    }

}
