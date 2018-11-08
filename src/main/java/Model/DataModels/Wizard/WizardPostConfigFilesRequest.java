package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WizardPostConfigFilesRequest {

    @JsonProperty("files")
    private List<WizardPostConfigFileInfo> files;

    public List<WizardPostConfigFileInfo> getFileList() {
        return files;
    }
}
