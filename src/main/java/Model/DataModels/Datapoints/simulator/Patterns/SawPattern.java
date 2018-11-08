
package Model.DataModels.Datapoints.simulator.Patterns;

import Model.DataModels.Datapoints.simulator.EnumPeriod;
import org.joda.time.DateTime;

public class SawPattern {
    private final DateTime startTime;

    
    public SawPattern( DateTime startTime ){
        this.startTime = startTime;
    }
    
    public Double getValue( DateTime ts, Double min, Double max, EnumPeriod period ){
        
        long ticks = ts.getMillis() - startTime.getMillis();
        long periodTicks = period.getTicks();
        long startTick = (ticks / periodTicks) * periodTicks;
        Double slope = 0.0;
        Double val = 0.0;
        if(periodTicks != 0 ){
            long halfWay = ((2 * startTick) + periodTicks) / 2;
            slope =  2 * (max - min) / periodTicks;
            if( ticks > halfWay){
                slope *= -1;
                val = max + slope * ( ticks - (startTick + (periodTicks / 2)) );
            }
            else{
            val = min + slope * ( ticks - startTick );
            }
        }
        return val;
    }
   
}