
package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WizardFile {

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("complete")
    private Boolean complete;

    @JsonProperty("versionOnJace")
    private String versionOnJace;

    @JsonProperty("versionOnServer")
    private String versionOnServer;

    @JsonProperty("updateAvailable")
    private Boolean updateAvailable;

    @JsonProperty("message")
    private String message;

    public String getFileName() {
        return fileName;
    }

    public Boolean getComplete() {
        return complete;
    }

    public String getVersionOnJace() {
        return versionOnJace;
    }

    public String getVersionOnServer() {
        return versionOnServer;
    }

    public Boolean getUpdateAvailable() {
        return updateAvailable;
    }

    public String getMessage() {
        return message;
    }
}
