package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPV6Settings {

    @JsonProperty("address")
    private String address;

    @JsonProperty("canDisable")
    private Boolean canDisable;

    @JsonProperty("enabled")
    private Boolean enabled;

    @JsonProperty("networkPrefixLength")
    private String networkPrefixLength;

    @JsonProperty("gateway")
    private String gateway;

    @JsonProperty("dnsServer1")
    private String dnsServer1;

    @JsonProperty("dnsServer2")
    private String dnsServer2;

    @JsonProperty("supported")
    private Boolean supported;

    public String getAddress() {
        return address;
    }

    public Boolean getCanDisable() {
        return canDisable;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getNetworkPrefixLength() {
        return networkPrefixLength;
    }

    public String getGateway() {
        return gateway;
    }

    public String getDNSServer1() {
        return dnsServer1;
    }

    public String getDNSServer2() {
        return dnsServer2;
    }

    public Boolean getSupported() {
        return supported;
    }
}
