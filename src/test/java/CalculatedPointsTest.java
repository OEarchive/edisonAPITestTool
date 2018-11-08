

import Model.DataModels.Datapoints.Rules.OptimizationStatusRule;
import Model.RestClient.APIHosts;
import Model.RestClient.APIHostsEnum;
import Model.RestClient.Clients.DatapointsClient;
import Model.RestClient.Clients.LoginClient;
import Model.RestClient.Clients.RestClientCommon;
import Model.RestClient.LoginResponse;
import Model.RestClient.OEResponse;
import Model.RestClient.RequestsResponses;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
public class CalculatedPointsTest  {

    String serviceURL = "";
    String token = "";

    RestClientCommon rc;
    DatapointsClient dpc;

    public CalculatedPointsTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {
        RequestsResponses rr = new RequestsResponses();
        rc = new RestClientCommon(rr);
        LoginClient authClient = new LoginClient(rc);

        APIHosts hosts = new APIHosts();
        serviceURL = hosts.getHostInfo(APIHostsEnum.OMNIBUS).getServiceEndpointURL();

        OEResponse resp = authClient.login(serviceURL, AdminLoginCreds );
        assertTrue("Could not login", resp.responseCode == 200);
        LoginResponse loginInfo = (LoginResponse) resp.responseObject;
        token = loginInfo.getAccessToken();
        assertTrue("No token", !token.isEmpty());

        dpc = new DatapointsClient(rc);
        dpc.setServiceURLAndToken(serviceURL, token);
    }
    


    @After
    public void tearDown() {
    }

    @Test
    public void getPointsForRuleTest() throws IOException {

        OptimizationStatusRule os = new OptimizationStatusRule();
        List<String> leaves = os.getListOfLeafNames();
        System.out.println("here are the leaves...");
        for( String leaf : leaves ){
            System.out.println(leaf);
        }
        
        //OEResponse resp = dpc.getDatapointMetadata("c:jackson.s:greentree-edge", "NotOptimized");

        //assertTrue("could not get metadata", resp.responseCode == 200 );
        

        

    }
   


}
*/