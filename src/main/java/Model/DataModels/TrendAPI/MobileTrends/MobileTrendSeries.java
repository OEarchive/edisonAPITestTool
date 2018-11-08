
package Model.DataModels.TrendAPI.MobileTrends;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class MobileTrendSeries {
    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
    private List<Double> data;
    
    @JsonProperty("type")
    private String trendChartType;
   
    public String getName() {
        return this.name;
    }

    public List<Double> getData() {
        return this.data;
    }
    
    public String getTrendChartType() {
        return this.trendChartType;
    }
    
    
}
