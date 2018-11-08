package Model.DataModels.Views;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ItemGroup {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String typeName;

    @JsonProperty("items")
    private List<ViewItem> items;
    
    @JsonProperty("pointGroups")
    private List<PointGroup> pointGroups;

    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<ViewItem> getItems() {
        return items;
    }
    
    public List<PointGroup> getPointGroups() {
        return pointGroups;
    }

}
