
package Model.DataModels.Datapoints.simulator.old_chiller_points_stuff;

import Model.DataModels.Datapoints.simulator.old_chiller_points_stuff.EnumChillerPoints;


public class ChillerPointValue {
    
    EnumChillerPoints point;
    Object pointValue;
    
    
    public ChillerPointValue( EnumChillerPoints point, Object pointValue ){
        this.point = point;
        this.pointValue = pointValue; 
    }
    
    public EnumChillerPoints getPoint(){
        return this.point;
    }
    
    public Object getPointValue(){
        return this.pointValue;
    }
    
}
