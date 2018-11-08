package Model.RestClient;

public class APILoginInfo {

    private final String serviceEndpointURL;
    private final String adminEndpointURL;

    public APILoginInfo(
            String serviceEndpointURL,
            String adminEndpointURL ) {
        this.serviceEndpointURL = serviceEndpointURL;
        this.adminEndpointURL = adminEndpointURL;
    }

    public String getServiceEndpointURL() {
        return this.serviceEndpointURL;
    }
    
        public String getAdminEndpointURL() {
        return this.adminEndpointURL;
    }

}
