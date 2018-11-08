
package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class SiteQueryResponse {
    
    @JsonProperty("sites")
    private List<Site> sites;
    
    public List<Site> getSites(){
        return sites;
    }
      
}