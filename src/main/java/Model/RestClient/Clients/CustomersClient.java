package Model.RestClient.Clients;

import Model.DataModels.Customers.Customer;
import Model.DataModels.Customers.Customers;
import Model.DataModels.TotalSavings.TotalSavings;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class CustomersClient {

    private String serviceURL;
    private final RestClientCommon restClient;
    
    public CustomersClient( RestClientCommon restClient ){
        this.restClient = restClient;
        
    }
   
    public void setServiceURLAndToken( String serviceURL, String accessToken ){
        this.serviceURL = serviceURL;
        restClient.setOauthToken(accessToken);
    }
    
    public OEResponse getTotalSavings() throws IOException {
        String url = this.serviceURL + "/public/totalsavings";
        OEResponse resObj = restClient.getResponse(url);
        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, TotalSavings.class );
            
        }
        return resObj;
    }
    
    
    public OEResponse getCustomers() throws IOException {

        String url = serviceURL + "/customers";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, Customers.class );
        }
        return resObj;
    }
    
    //TODO: if ext_sf_id exists, will call back...
    public OEResponse addCustomer(String payload) throws IOException {

        String url = serviceURL + "/customers";
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, Customer.class );
        }
        return resObj;
    }

    public OEResponse updateCustomer(String sid, String payload) throws IOException {
        String url = serviceURL + "/customers/" + sid;
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);
        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
    
    public OEResponse getCustomerDetails(String sid) throws IOException {
        String url = serviceURL + "/customers/" + sid;
        OEResponse resObj = restClient.getResponse(url);
        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue( (String)resObj.responseObject, Customer.class );
        }
        return resObj;
    }

    public OEResponse checkForCustomerUpdates(String sid) throws IOException {
        String url = serviceURL + "/customers/" + sid + "/check-for-updates";
        OEResponse resObj = restClient.doPostAndGetBody(url, "", true);
        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }
    

}
