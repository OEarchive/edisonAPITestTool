package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WizardPointsList {

    @JsonProperty("pointsList")
    private List<WizardPoint> pointsList;

    public List<WizardPoint> getPointsList() {
        return pointsList;
    }
}
