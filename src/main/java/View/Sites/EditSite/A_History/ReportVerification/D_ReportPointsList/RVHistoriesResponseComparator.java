
package View.Sites.EditSite.A_History.ReportVerification.D_ReportPointsList;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.ReportVerification.CalcPointClassification.EnumCalcPointFilter;
import java.util.Comparator;


public class RVHistoriesResponseComparator implements Comparator {
    
    private final EnumCalcPointFilter calcPoint;
    public RVHistoriesResponseComparator( EnumCalcPointFilter calcPoint ){
        this.calcPoint = calcPoint;
    }

    @Override
    public int compare(Object objA, Object objB) {
        
        DatapointHistoriesResponse A = (DatapointHistoriesResponse) objA;
        DatapointHistoriesResponse B = (DatapointHistoriesResponse) objB;
        
        String pointNameA = A.getName();
        String pointNameB = B.getName();
        
        if( pointNameA.contentEquals( calcPoint.getCalculatedPointName()) &&
            !pointNameB.contentEquals( calcPoint.getCalculatedPointName() )  ){
            
            return -1;
        }
        
        if( pointNameB.contentEquals( calcPoint.getCalculatedPointName()) &&
            !pointNameA.contentEquals( calcPoint.getCalculatedPointName() )  ){
            
            return 1;
        }
        
        return pointNameA.compareTo(pointNameB);
        
    }
    
}
