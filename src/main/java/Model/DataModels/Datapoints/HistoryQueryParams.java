
package Model.DataModels.Datapoints;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class HistoryQueryParams {

    private final DateTime start;
    private final DateTime end;
    private final EnumEdisonResolutions resolution;
    private final List<String> points;
    private final EnumAggregationType aggType;
    
    public HistoryQueryParams( DateTime start, DateTime end, EnumEdisonResolutions resolution, List<String> points, EnumAggregationType aggType ){
        
        this.start = start;
        this.end = end;
        this.resolution = resolution;
        this.points = points;
        this.aggType = aggType;
        
    }
          
    public String getQueryParamsString() throws UnsupportedEncodingException{
        
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String startDateStr = start.toString(fmt);
        String endDateStr = end.toString(fmt);
        


        List<String> decoratedPoints = new ArrayList<>();
        for( String pointName : points ){
            
            String cleanName = URLEncoder.encode(pointName, "UTF-8");
            
            switch( aggType ){
                case NORMAL:
                    decoratedPoints.add(cleanName);
                    break;
                default:
                    cleanName = aggType.getTagTypeName() + "(" + cleanName + ")";
                    decoratedPoints.add(cleanName);
                    break; 
            }
            
        }
        
        String pointsList = String.join(",", decoratedPoints);
        String queryString = String.format(
                "start=%s&end=%s&resolution=%s&points=%s",
                startDateStr,
                endDateStr,
                resolution.getFriendlyName(),
                pointsList
                );

        return queryString;
    }
    
}
