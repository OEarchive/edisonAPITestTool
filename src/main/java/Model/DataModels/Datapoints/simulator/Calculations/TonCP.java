package Model.DataModels.Datapoints.simulator.Calculations;

import java.util.List;

public class TonCP {


    public TonCP() {

    }
    
    
    
    public double getkWTon( double TotalkW , double TotalTon ){
        if( TotalTon == 0.0 ) return 0;
        return TotalkW / TotalTon;
    }
    
    public double getChillerKW( List<Double> chillerKW){
        double total = 0;
        
        for( double d : chillerKW){
            total += d;
        }
        
        return total;
    }
    
    public double getkwH( List<Double> kws, int numOfHours ){
        
        if( numOfHours == 0 ){
            return 0;
        }
                
        double kWh = 0;
        
        for( double d : kws ){
            kWh += d;
        }
        
        return kWh / numOfHours;
        
    }
    
        public double getTotalKW( List<Double> allEquipmentKw){
        double total = 0;
        
        for( double d : allEquipmentKw){
            total += d;
        }
        
        return total;
    }
        
    
    public Object getTotalTon( Object Ton, Object ChilledWaterFlow, Object ChillerkW, Object TotalCapcity, Object MinimumChilledWaterFlow){
        
        if( Ton == null || ChilledWaterFlow == null || ChillerkW == null ){
            return null;
        }
        
        if( (double)Ton == 0.0 && 
                (double)ChilledWaterFlow > (double)MinimumChilledWaterFlow / 2  &&
                (double)ChillerkW > 50 )
            return null;
        
        if( (double)Ton < 0 ){
            return 0;
        }
        
        if( (double)Ton > (double)TotalCapcity * 1.5 ){
            return (double)TotalCapcity * 1.5;
            
        }
        
        if( (double ) ChilledWaterFlow < (double)ChilledWaterFlow / 2){
            return 0;
        }
        
        return Ton;
        
    }
    
    
    
    public double getChillerKwPerTon( double chillerkW, double TotalTon ){
        
        if( TotalTon == 0 ) return 0;
        
        return chillerkW / TotalTon;
        
    }

    public Object getTon(double ChilledWaterReturnTemperature,
            double ChilledWaterSupplyTemperature,
            double ChilledWaterFlow) {
        return (ChilledWaterReturnTemperature - ChilledWaterSupplyTemperature) * ChilledWaterFlow / 24;
    }

    public Object getTonHours(List<Object> totalTons, int numOfHours) {

        double mean = 0;
        for (Object ton : totalTons) {
            mean += (double) ton;
        }

        if (totalTons.size() > 0) {
            mean /= totalTons.size();
        }

        return mean;
    }
}
