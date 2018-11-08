
package Model.DataModels.ReportVerification.CalculatedPointInfo;

import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Calc_kwTon extends CalculatedPointBase implements iCalculatedPoint {
    
    public Calc_kwTon( List<DatapointsAndMetadataResponse> pointsForReports ){
        
        super();
       
        Map<String, DatapointsAndMetadataResponse> filteredList = new HashMap<>();
        
        for( DatapointsAndMetadataResponse z : pointsForReports ){
            
            if( includeThis( z )){
                filteredList.put( z.getSid() + z.getName(), z);
            }
        }
        
        super.setFilteredList( filteredList );
        
    }
    
    
    public boolean includeThis( DatapointsAndMetadataResponse resp ){
        
        List<Map> listOfKeyValue = resp.getDatapointAssociations();
        
        for( Map pair : listOfKeyValue ){
            
            String name = (String)(pair.get("name"));
            String sid = (String)(pair.get("sid"));
            if( name.contentEquals("kw") && sid.contains("e:")){
                return true;
            }
        }
        return false;  
    }


    
}
