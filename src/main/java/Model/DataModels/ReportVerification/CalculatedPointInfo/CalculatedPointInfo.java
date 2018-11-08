
package Model.DataModels.ReportVerification.CalculatedPointInfo;

import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import java.util.List;


public class CalculatedPointInfo {
    
    List<DatapointsAndMetadataResponse> pointsForReports;
    
    public CalculatedPointInfo( List<DatapointsAndMetadataResponse> pointsForReports ){
        this.pointsForReports = pointsForReports;
        
    }
}
