
package Model.DataModels.Datapoints.simulator.Patterns;

import Model.DataModels.Datapoints.simulator.EnumPeriod;
import org.joda.time.DateTime;

public class SquarePattern {
    private final DateTime startTime;

    public SquarePattern( DateTime startTime ){
        this.startTime = startTime;
    }
    
    public Double getValue( DateTime ts, Double min, Double max, EnumPeriod period){
        long ticks = ts.getMillis() - startTime.getMillis();
        long periodTicks = period.getTicks();
        long startTick = (ticks / periodTicks) * periodTicks;
        long halfWay = ((2 * startTick) + periodTicks) / 2;
        double val = ( ticks > halfWay ) ? max : min;
            
        return val;
    }
}
    