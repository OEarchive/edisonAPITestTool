
package Model.DataModels.TrendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class MobileCompanyOverview {

    @JsonProperty("overview")
    private MobileOverview overview;
    
    @JsonProperty("sites")
    private List<MobileSite> sites;

    public MobileOverview getOverview() {
        return this.overview;
    }

    public List<MobileSite> getSites() {
        return this.sites;
    }

}
