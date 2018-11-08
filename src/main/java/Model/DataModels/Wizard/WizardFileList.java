package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WizardFileList {

    @JsonProperty("files")
    private List<WizardFile> files;

    public List<WizardFile> getFileList() {
        return files;
    }

}
