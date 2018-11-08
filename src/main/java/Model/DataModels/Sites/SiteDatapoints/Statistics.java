
package Model.DataModels.Sites.SiteDatapoints;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.GetDatapointHistoryResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Statistics {
    
    private final Map< String, DatapointStatistics> map;
    private final List<String> uNames;

    
    public Statistics( List<String> uNames, List<DatapointHistoriesResponse> res ){
        
        this.map = new HashMap<>();
        this.uNames = uNames;

        Map<String, List<Object> > uNameToValuesMap = new HashMap<>();
        for( DatapointHistoriesResponse resp : res ){
            
            String uName = getUName(resp.getSid(), resp.getName() );
            
            uNameToValuesMap.put( uName, resp.getValues() );
        }
        
        for( String uName : uNames){
            map.put( uName, new DatapointStatistics( uNameToValuesMap.get(uName) ));
        }
    }
    
    private String getUName(String sid, String name) {
        String uName = name;
        String[] pieces = sid.split("\\.");

        if (pieces.length > 2) {
            uName = pieces[2] + "." + uName;
        }
        return uName;
    }
    
    public List<String> getUNames(){
        
        return this.uNames;

    }
    
    public DatapointStatistics getDataPointStatistics( String uName ){
        return map.get(uName );
    }
    
}