package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class NetworkAdapter {

    @JsonProperty("interfaceDescription")
    private String interfaceDescription;

    @JsonProperty("interfaceName")
    private String interfaceName;

    @JsonProperty("enabled")
    private Boolean enabled;

    @JsonProperty("ipV4Settings")
    private List<IPV4Settings> ipV4Settings;

    @JsonProperty("canDisable")
    private Boolean canDisable;

    @JsonProperty("ipV6Settings")
    private List<IPV6Settings> ipV6Settings;

    public String getInterfaceDescription() {
        return interfaceDescription;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public List<IPV4Settings> getIPV4Settings() {
        return ipV4Settings;
    }

    public Boolean getCanDisabled() {
        return canDisable;
    }

    public List<IPV6Settings> getIPV6Settings() {
        return ipV6Settings;
    }

}
