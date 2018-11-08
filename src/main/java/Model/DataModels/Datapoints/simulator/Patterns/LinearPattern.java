
package Model.DataModels.Datapoints.simulator.Patterns;

import Model.DataModels.Datapoints.simulator.EnumPeriod;
import org.joda.time.DateTime;


public class LinearPattern {
    private final DateTime startTime;

    public LinearPattern( DateTime startTime ){
        this.startTime = startTime;
    }
    
    public Double getValue( DateTime ts, Double min, Double max, EnumPeriod period ){
        
        long ticks = ts.getMillis() - startTime.getMillis();
        long periodTicks = period.getTicks();
        long startTick = (ticks / periodTicks) * periodTicks;
        Double slope = (periodTicks == 0 ) ? 0 : (max - min) / periodTicks;
        Double val = min + slope * ( ticks - startTick );
        
        return val;
        
    }
    
}