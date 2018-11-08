
import Model.DataModels.Customers.Customer;
import Model.DataModels.Sites.CreateSiteRequest;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.SiteQueryResponse;
import Model.DataModels.Sites.SiteTemplate;
import Model.RestClient.APIHosts;
import Model.RestClient.APIHostsEnum;
import Model.RestClient.Clients.CustomersClient;
import Model.RestClient.Clients.LoginClient;
import Model.RestClient.Clients.RestClientCommon;
import Model.RestClient.Clients.SitesClient;
import Model.RestClient.LoginResponse;
import Model.RestClient.OEResponse;
import Model.RestClient.RequestsResponses;
import java.io.IOException;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/*
public class CreateCustomerAndSiteTest {
    

    String serviceURL = "";
    String token = "";
    
    RestClientCommon rc;
   
    public CreateCustomerAndSiteTest() {
    }
    
    @Before
    public void setUp() throws IOException {
        RequestsResponses rr = new RequestsResponses();
        rc = new RestClientCommon( rr );
        LoginClient authClient = new LoginClient(rc);
        
        APIHosts hosts = new APIHosts();
        serviceURL = hosts.getHostInfo(APIHostsEnum.OMNIBUS).getServiceEndpointURL();
        
        OEResponse resp = authClient.login(serviceURL, AdminsEnum.TESTSUPER);
        assertTrue( "Could not login", resp.responseCode == 200);
        LoginResponse loginInfo = (LoginResponse)resp.responseObject;
        token = loginInfo.getAccessToken();
        assertTrue( "No token", !token.isEmpty() );
        
        CustomersClient custClient = new CustomersClient(rc);
        custClient.setServiceURLAndToken(serviceURL, token);
    }
    
    @After
    public void tearDown() {
    }


    
    @Test
    public void addCustomerAndSiteTest() throws IOException {
        CustomersClient custClient = new CustomersClient(rc);
        custClient.setServiceURLAndToken(serviceURL, token);
        
        Customer c = new Customer();
        c.setCustomerName("Omnibus Test Customer");
        
        String payload = c.getUpdatePayload();
        
        OEResponse resp = custClient.addCustomer( payload );
        assertTrue("failed to add customer", resp.responseCode == 200);
        
        Customer updatedCustomer = (Customer)resp.responseObject;
        assertTrue("customer was not added/updated", c.getCustomerName().compareToIgnoreCase(updatedCustomer.getCustomerName()) == 0 );
        
        SitesClient sitesClient = new SitesClient(rc);
        sitesClient.setServiceURLAndToken(serviceURL, token);
        
        String siteName = "CS" + Long.toString(DateTime.now().getMillis());
        SiteTemplate siteTemplate = new SiteTemplate(updatedCustomer.getSid(), null, siteName);
        CreateSiteRequest csr = new CreateSiteRequest( siteTemplate.getSite() );
        resp = sitesClient.createSite(csr);
        assertTrue( "Could not create site", resp.responseCode == 200 );
        Site createdSite = (Site)resp.responseObject;
        assertTrue( "No sid for new site", !createdSite.getSid().isEmpty());
 
    }
    
    @Test
    public void checkAllSitesQueryIsWorking() throws IOException {
        SitesClient sitesClient = new SitesClient(rc);
        sitesClient.setServiceURLAndToken(serviceURL, token);
        OEResponse resp = sitesClient.getSites("");

        assertTrue("could not get all sites", resp.responseCode == 200);
        List<Site> listOfSites = ((SiteQueryResponse)resp.responseObject).getSites();
        assertTrue("no sites were listed", listOfSites.size() > 0 );
        
    }   
}
*/
