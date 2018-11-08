
package Model.RestClient;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import java.util.List;


public class TotalkWh_Analysis {
    
    private DatapointHistoriesResponse resp;
    
    public TotalkWh_Analysis( List<DatapointHistoriesResponse> datapointHistoriesResponse ){
        
        resp = datapointHistoriesResponse.get(0);
        
    }
    
    public void dumpStats(){
        
        //resp.
    }
    
}
