
package Model.DataModels.ReportVerification.CalculatedPointInfo;

import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;


public interface iCalculatedPoint {
    public Map<String, DatapointsAndMetadataResponse> getNameToPointsMap();
    public List<String> computedColumnNames();
    public List<DateTime> getTimeStamps();
    public Map< DateTime, Map<String, Object>> getTimestampToNameToValuesMap();
    
}
