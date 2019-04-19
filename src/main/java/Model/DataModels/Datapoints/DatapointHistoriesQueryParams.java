
package Model.DataModels.Datapoints;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class DatapointHistoriesQueryParams {

    private final String querySid;
    private final DateTime start;
    private final DateTime end;
    private final EnumEdisonResolutions resolution;
    private final List<String> points;
    private final EnumAggregationType aggType;
    private final boolean sparseData;
    
    public DatapointHistoriesQueryParams( String querySid, DateTime start, DateTime end, EnumEdisonResolutions resolution, boolean sparseData, List<String> points, EnumAggregationType aggType ){
        
        this.querySid = querySid;
        this.start = start;
        this.end = end;
        this.resolution = resolution;
        this.points = points;
        this.aggType = aggType;
        this.sparseData = sparseData;
        
    }
          
    public String getQueryParamsString() throws UnsupportedEncodingException{
        
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String startDateStr = start.toString(fmt);
        String endDateStr = end.toString(fmt);
        
        List<String> pointsEndcodedAndDecoratedWithAggregation = new ArrayList<>();
        for( String pointName : points ){
            
            String cleanName = URLEncoder.encode(pointName, "UTF-8");
            
            switch( aggType ){
                case NORMAL:
                    pointsEndcodedAndDecoratedWithAggregation.add(cleanName);
                    break;
                default:
                    cleanName = aggType.getTagTypeName() + "(" + cleanName + ")";
                    pointsEndcodedAndDecoratedWithAggregation.add(cleanName);
                    break; 
            }
            
        }
        
        List<String> paramPoints = new ArrayList<>();
        for( String p : pointsEndcodedAndDecoratedWithAggregation ){
            paramPoints.add("names=" + p);
        }
        
        String pointsList = String.join("&", paramPoints);
        String queryString = String.format("sid=%s&startDate=%s&endDate=%s&resolution=%s&sparse=%s&%s",
                querySid,
                startDateStr,
                endDateStr,
                resolution.getFriendlyName(),
                (sparseData)?"true":"false",
                pointsList
                );

        return queryString;
    }
    
}