package Model.DataModels.TrendAPI.MobileTrends;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MobileAxis {

    @JsonProperty("categories")
    private List<String> categories;

    public List<String> getCategories() {
        return this.categories;
    }
}
