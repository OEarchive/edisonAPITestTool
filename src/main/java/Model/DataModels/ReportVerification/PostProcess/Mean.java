
package Model.DataModels.ReportVerification.PostProcess;

import java.util.List;

public class Mean {
    
    private boolean hasAverage;
    private double mean;
    private int count;
    
    public Mean( List<Object> objs ){ 
        hasAverage = false;
        mean = 0.0;
        count = 0;
        
        for (Object obj : objs) {
            
            if (obj == null) {
                continue;
            }

            double temp;
            if (obj instanceof Integer) {
                temp = (Integer) obj;
            } else if (obj instanceof Double) {
                temp = (Double) obj;
            } else {
                continue;
            }

            mean += temp;
            count += 1;
        }

        if (count > 0) {
            hasAverage = true;
            mean /= count;
        }
        
    }
    
    public boolean hasMean(){
        return hasAverage;
    }
    
    public int getCount(){
        return count;
    }
    
    public double getMean(){
        return mean;
    }
    
    
}
