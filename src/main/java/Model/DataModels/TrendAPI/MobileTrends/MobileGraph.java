
package Model.DataModels.TrendAPI.MobileTrends;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class MobileGraph {
    @JsonProperty("series")
    private List<MobileTrendSeries> series;

    @JsonProperty("xAxis")
    private MobileAxis xAxis;
   
    public List<MobileTrendSeries> getSeries() {
        return this.series;
    }

    public MobileAxis getXAxis() {
        return this.xAxis;
    }
    
}
