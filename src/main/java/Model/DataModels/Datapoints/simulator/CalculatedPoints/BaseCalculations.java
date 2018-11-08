package Model.DataModels.Datapoints.simulator.CalculatedPoints;

import Model.DataModels.Sites.Chiller;
import java.util.List;

public class BaseCalculations {
    private final List<Chiller> chillers;
    
    public BaseCalculations( List<Chiller> chillers){
        
        this.chillers = chillers;
    }
    

    public Object getkwPerTon( double totalKw, double totalTon ) {

        if( totalTon != 0 ){
         return totalKw / totalTon;   
        }
        return null;
    }

    public Double getTotalkW( List<Double> chillerKws ) {
        Double total = 0.0;
        for( Double d : chillerKws ){
            total += d;
        }
        return total;
    }

    //TODO: Not sure
    public Object getkWh( List<Double> kws, int numOfHours) {
        Object val = 0;
        return val;
    }

    public Object getTotalTon( Object tons, Object chwflo, Object chkw, double minflo, double totalCapacity ) {
        
        //TODO : Are these if statements all in the correct order?
        if( tons == null || chwflo == null || chkw == null){
            return null;
        }
        
        if( (Double)tons == 0.0 && ((Double)chwflo) > minflo / 2 && ((Double)chkw) > 50.0 ){
            return null;
        }
        
        if( (Double)tons < 0 ){
            return 0.0;
        }
        
        if( (Double)tons > totalCapacity * 1.5 ){
            return totalCapacity * 1.5;
        }
        
        if( (Double)chwflo < minflo / 2 ){
            return 0;
        }
        
        return tons;
    }

    public Object getTon( double chwrt, double chwst, double chwflo) {
        return (chwrt - chwst) * chwflo / 24;
    }

    
    //TODO
    public Object getTotalCapacity() {
        //sum all chiller ratings tons
        Object val = 0;
        return val;
    }

    public Object getTonHours() {
        Object val = 0;
        return val;
    }

    public Object gettotalLoad() {
        Object val = 0;
        return val;
    }

    public Object getCHxrunhours() {
        Object val = 0;
        return val;
    }

    public Object getChillerkwhusage() {
        Object val = 0;
        return val;
    }

    public Object getOldkW() {
        Object val = 0;
        return val;
    }

    public Object getCalc_OldkWTon() {
        Object val = 0;
        return val;
    }

    public Object getOldCo2Cost() {
        Object val = 0;
        return val;
    }

    public Object getOldDollarCost() {
        Object val = 0;
        return val;
    }

    public Object getDollarCost() {
        Object val = 0;
        return val;
    }

    public Object getCO2Produced() {
        Object val = 0;
        return val;
    }

    public Object getkWDelta() {
        Object val = 0;
        return val;
    }

    public Object getDollarsSaved() {
        Object val = 0;
        return val;
    }

    public Object getEnergySaved() {
        Object val = 0;
        return val;
    }

    public Object getCO2Saved() {
        Object val = 0;
        return val;
    }

    public Object getPercentOptimized() {
        Object val = 0;
        return val;
    }

    public Object getPercentNotOptimized() {
        Object val = 0;
        return val;
    }

    public Object getPercentNotOperating() {
        Object val = 0;
        return val;
    }

    public Object getPartiallyOptimized() {
        Object val = 0;
        return val;
    }

    public Object getNotOptimized() {
        Object val = 0;
        return val;
    }

    public Object getFullyOptimized() {
        Object val = 0;
        return val;
    }
}


