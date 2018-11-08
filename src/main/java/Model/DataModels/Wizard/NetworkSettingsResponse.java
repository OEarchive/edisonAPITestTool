
package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class NetworkSettingsResponse {

    @JsonProperty("networkAdapters")
    private List<NetworkAdapter> networkAdapters;

    public List<NetworkAdapter> getNetworkAdapters() {
        return networkAdapters;
    }

}
