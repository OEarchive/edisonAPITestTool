
package Model.DataModels.ReportVerification;

import Model.DataModels.Datapoints.DatapointHistoriesQueryParams;
import java.util.ArrayList;
import java.util.List;


public class ReportHistoryQueryParams {
    
    
    static List< List<DatapointHistoriesQueryParams> > multipleQueries;
    
    public ReportHistoryQueryParams(){
        this.multipleQueries = new ArrayList<>();
    }
    
    public void addQuery( List<DatapointHistoriesQueryParams> query){
        multipleQueries.add( query);
    }
    
    public List< List<DatapointHistoriesQueryParams> > getQueries(){
        return multipleQueries;
    }
    
}
