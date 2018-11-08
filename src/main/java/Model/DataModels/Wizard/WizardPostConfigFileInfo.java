package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WizardPostConfigFileInfo {

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("download")
    private Boolean download;

    public String getFileName() {
        return fileName;
    }

    public Boolean getDownload() {
        return download;
    }
}
