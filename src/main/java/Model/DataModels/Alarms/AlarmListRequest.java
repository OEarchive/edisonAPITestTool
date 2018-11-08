
package Model.DataModels.Alarms;

import java.io.UnsupportedEncodingException;
import java.util.List;


public class AlarmListRequest {
    
    private final boolean setOnlyActiveParam;
    private final boolean onlyActive;
    private final List<String> tags;
    
    public AlarmListRequest( boolean setOnlyActiveParam, boolean onlyActive, List<String>tags){
        this.setOnlyActiveParam = setOnlyActiveParam;
        this.onlyActive = onlyActive;
        this.tags = tags;
    }
    

            
    public String getQueryParamsString() throws UnsupportedEncodingException{
        
        String queryString = "";
        if( setOnlyActiveParam ){
            queryString += "?onlyActive=" + ( onlyActive? "true" : "false" ) ;
        }
        
        //TODO: The tags parameter is iffy. This is taken into account where returning results for "get site info"
        /*
        if( tags.size() > 0 ){
            String tagsList = String.join(",", tags);
            if( queryString.length() > 0 ){
                queryString += "&";
            }
            else{
                queryString += "?";
            }
            queryString += "tags=" + tagsList;
        }
        */
        
        return queryString;
    }
    
}
