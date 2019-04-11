
import Model.DataModels.Customers.Customer;
import Model.DataModels.Datapoints.EnumAggregationType;
import Model.DataModels.Datapoints.EnumEdisonResolutions;
import Model.DataModels.Datapoints.GetDatapointHistoryResponse;
import Model.DataModels.Datapoints.HistoryQueryDataPointValues;
import Model.DataModels.Datapoints.HistoryQueryParams;
import Model.DataModels.Sites.CreateSiteRequest;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.SiteTemplate;
import Model.DataModels.Stations.HistoryPushObject;
import Model.DataModels.Stations.HistoryPushPoint;
import Model.DataModels.Stations.StationStatusResponse;
import Model.RestClient.APIHosts;
import Model.RestClient.APIHostsEnum;
import Model.RestClient.Clients.CustomersClient;
import Model.RestClient.Clients.DatapointsClient;
import Model.RestClient.Clients.LoginClient;
import Model.RestClient.Clients.RestClientCommon;
import Model.RestClient.Clients.SitesClient;
import Model.RestClient.Clients.StationsClient;
import Model.RestClient.LoginResponse;
import Model.RestClient.OEResponse;
import Model.RestClient.RequestsResponses;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/*
public class GenerateHistoryTest {

    String serviceURL = "";
    String token = "";

    RestClientCommon rc;

    public GenerateHistoryTest() {
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

        OEResponse resp = authClient.login(serviceURL, AdminsEnum.TESTSUPER);
        assertTrue("Could not login", resp.responseCode == 200);
        LoginResponse loginInfo = (LoginResponse) resp.responseObject;
        token = loginInfo.getAccessToken();
        assertTrue("No token", !token.isEmpty());

        CustomersClient custClient = new CustomersClient(rc);
        custClient.setServiceURLAndToken(serviceURL, token);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void genHistoryTest() throws IOException {
        CustomersClient custClient = new CustomersClient(rc);
        custClient.setServiceURLAndToken(serviceURL, token);

        //create customer
        Customer c = new Customer();
        c.setCustomerName("OTest");
        String payload = c.getUpdatePayload();
        OEResponse resp = custClient.addCustomer(payload);
        assertTrue("failed to add customer", resp.responseCode == 200);
        Customer updatedCustomer = (Customer) resp.responseObject;
        assertTrue("customer was not added/updated", c.getCustomerName().compareToIgnoreCase(updatedCustomer.getCustomerName()) == 0);

        
        // create site 
        SitesClient sitesClient = new SitesClient(rc);
        sitesClient.setServiceURLAndToken(serviceURL, token);
        String siteName = "DG" + Long.toString(DateTime.now().getMillis());
        
        SiteTemplate siteTemplate = new SiteTemplate(updatedCustomer.getSid(), null, siteName);
        CreateSiteRequest csr = new CreateSiteRequest(siteTemplate.getSite());
        resp = sitesClient.createSite(csr);
        assertTrue("Could not create site", resp.responseCode == 200);
        Site createdSite = (Site) resp.responseObject;
        assertTrue("No sid for new site", !createdSite.getSid().isEmpty());



        // post some history
        
        StationsClient sc = new StationsClient(rc);
        sc.setServiceURLAndToken(serviceURL, token);
        
        String sendingStationName = "foobar";
        String stationName = "foobar";
        String stationId = createdSite.getStationID();
        
        DateTime endDate = DateTime.now();
        DateTime startDate = endDate.minusHours(1);
        
        
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String timestamp = endDate.toString(fmt);
        String lastSuccessTimestamp = timestamp;


        String pointName = "CDWFLO";
        HistoryPushPoint pushPoint = new HistoryPushPoint();
        pushPoint.setName(pointName);
        pushPoint.setPath("foo/bar");
        pushPoint.setPointType("numeric");
        
        List<String> timestamps = getTimeStamps( EnumResolutions.MINUTE5, startDate, endDate);
        
        assertTrue( "no timestamps", timestamps.size() > 0 );
        pushPoint.setTimestamps(timestamps);
        
        List<Object> values = getNumericValues( timestamps.size() );
        pushPoint.setValues( values);
       
        
        List<HistoryPushPoint> points = new ArrayList<>();
        points.add( pushPoint );
     
        HistoryPushObject req = new HistoryPushObject();
        req.setSendingStationName(sendingStationName);
        req.setStationId(stationId);
        req.setStationName(stationName);
        
        req.setTimestamp(timestamp);
        req.setLastSuccessTimestamp(lastSuccessTimestamp);
        req.setPoints(points);
        
        resp = sc.postDataPointHistory(req);
        
        assertTrue( resp.responseCode == 200 );
          
        StationStatusResponse stationStatus =  (StationStatusResponse)resp.responseObject;
        
        assertTrue("history push failed", (Boolean)stationStatus.getSuccess());
        
        DatapointsClient dpc = new DatapointsClient(rc);
        dpc.setServiceURLAndToken(serviceURL, token);
        
        List<String> pointNames = new ArrayList<>();
        pointNames.add("CDWFLO");
        HistoryQueryParams hqp = new HistoryQueryParams(startDate, endDate, EnumResolutions.MINUTE5, pointNames, EnumAggregationType.NORMAL );
        
        //resp = dpc.getHistory(createdSite.getSid() + ".st:1", hqp);
        assertTrue("could not query for history", resp.responseCode == 200);
        
        GetDatapointHistoryResponse getHistoryResponse = (GetDatapointHistoryResponse)resp.responseObject;
       
        //List<HistoryQueryDataPointValues> returnPoints = getHistoryResponse.getDatapoints();
        
        assertTrue("point not there", getHistoryResponse.getValues("CDWFLO") != null);

    }
   
    private List<String> getTimeStamps(EnumResolutions res, DateTime start, DateTime end) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        List<String> timeStamps = new ArrayList<>();

        DateTime counter = start;
        while (counter.isBefore(end)) {
            String timestamp = counter.toString(fmt);
            timeStamps.add(timestamp);
            counter = counter.plusMinutes(res.getMinutes());
        }
        return timeStamps;
    }

    public List<Object> getNumericValues(int countOfValues) {

        double min = 5;
        double max = 100;

        List<Object> values = new ArrayList<>();
        for (int i = 0; i < countOfValues; i++) {
            double val = min + ((max - min) / countOfValues) * i;
            values.add(val);
        }
        
        return values;
    }

}
*/
