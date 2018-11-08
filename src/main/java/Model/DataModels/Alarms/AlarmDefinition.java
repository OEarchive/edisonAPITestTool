package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AlarmDefinition {

    @JsonProperty("description")
    private String description;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("baseSeverity")
    private String baseSeverity;

    @JsonProperty("recommendedActions")
    private String recommendedActions;

    public String getDescription() {
        return description;
    }

    @JsonIgnore
    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    @JsonIgnore
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getBaseSeverity() {
        return baseSeverity;
    }

    @JsonIgnore
    public void setBaseSeverity(String baseSeverity) {
        this.baseSeverity = baseSeverity;
    }

    public String getRecommendedActions() {
        return recommendedActions;
    }

    @JsonIgnore
    public void setRecommendedActions(String recommendedActions) {
        this.recommendedActions = recommendedActions;
    }

}
