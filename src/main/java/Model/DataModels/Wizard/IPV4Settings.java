
package Model.DataModels.Wizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPV4Settings {

    @JsonProperty("address")
    private String address;

    @JsonProperty("subnetMask")
    private String subnetMask;

    @JsonProperty("dhcpSupported")
    private Boolean dhcpSupported;

    @JsonProperty("gateway")
    private String gateway;

    @JsonProperty("dnsServer1")
    private String dnsServer1;

    @JsonProperty("dnsServer2")
    private String dnsServer2;

    @JsonProperty("dhcpEnabled")
    private Boolean dhcpEnabled;

    public String getAddress() {
        return address;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public Boolean getDHCPSupported() {
        return dhcpSupported;
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

    public Boolean getDHCPEnabled() {
        return dhcpEnabled;
    }

}
