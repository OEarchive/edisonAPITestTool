
package Model.DataModels.Datapoints.simulator.Patterns;

import Model.DataModels.Datapoints.simulator.EnumPeriod;
import org.joda.time.DateTime;


public class SinePattern {
    private final DateTime startTime;

    public SinePattern( DateTime startTime  ){ 
        this.startTime = startTime; 
    }
    
    public Double getValue( DateTime ts, Double offset, Double min, Double max, EnumPeriod period){      
        long ticks = ts.getMillis() - startTime.getMillis();
        Double amplitude = Math.abs( max - min ) / 2;        
        Double val = min + amplitude * (1 + Math.sin( ticks * 2 * Math.PI / period.getTicks()  ));
        return val;
    }
}
