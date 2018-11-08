

import Model.DataModels.Customers.Customer;
import Model.DataModels.Sites.CreateSiteRequest;
import Model.DataModels.Sites.Phone;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.SiteTemplate;
import Model.DataModels.Users.CreateUserRequest;
import Model.DataModels.Users.EnumUserRoles;
import Model.DataModels.Users.RoleItem;
import Model.DataModels.Users.UserInfo;
import Model.DataModels.Users.Users;
import Model.RestClient.APIHosts;
import Model.RestClient.APIHostsEnum;
import Model.RestClient.Clients.CustomersClient;
import Model.RestClient.Clients.LoginClient;
import Model.RestClient.Clients.RestClientCommon;
import Model.RestClient.Clients.SitesClient;
import Model.RestClient.Clients.UsersClient;
import Model.RestClient.LoginResponse;
import Model.RestClient.OEResponse;
import Model.RestClient.RequestsResponses;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import static org.junit.Assert.assertTrue;

/*
public class UsersAndRolesTest {

    String serviceURL = "";
    String token = "";

    RestClientCommon rc;
    CustomersClient custClient;

    public UsersAndRolesTest() {
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

        custClient = new CustomersClient(rc);
        custClient.setServiceURLAndToken(serviceURL, token);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void simpleAddUserTest() throws IOException {

        EnumUserRoles role = EnumUserRoles.OEUser;
        String userName = "Omni-" + role.getFriendlyName();
        String roleSid = null;

        //add customer
        Customer templateCustomer = new Customer();
        templateCustomer.setCustomerName("omniSimple");
        templateCustomer.setAddress(getAddressTemplate());
        String payload = templateCustomer.getUpdatePayload();
        OEResponse resp = custClient.addCustomer(payload);
        assertTrue("failed to add customer", resp.responseCode == 200);
        Customer cust = (Customer) resp.responseObject;
        assertTrue("customer was not added/updated", templateCustomer.getCustomerName().compareToIgnoreCase(cust.getCustomerName()) == 0);
        roleSid = cust.getSid();

        //add a users
        UsersClient uc = new UsersClient(rc);
        uc.setServiceURLAndToken(serviceURL, token);

        CreateUserRequest cur = new CreateUserRequest();
        cur.setFirstname("Omni");
        cur.setUsername(userName);
        cur.setLastname("Bus");
        cur.setEmail("someAccount@overThere.com");
        cur.setInvitationMessage("Welcome!");
        cur.setNoInvite(Boolean.TRUE);

        cur.setPhones(getPhonesTemplate(5));
        cur.setRoles(getRoleItemsTemplate(roleSid, role));

        uc.postUser(cur);

        resp = uc.getUsers("");

        assertTrue("Could not get users", resp.responseCode == 200);

        Users users = (Users) resp.responseObject;
        List<UserInfo> userList = users.getUsers();

        boolean foundUser = false;
        boolean foundRole = false;
        for (UserInfo u : userList) {

            if (u.getUserName().equalsIgnoreCase(userName)) {
                foundUser = true;

                List<RoleItem> roles = u.getRoles();
                for (RoleItem r : roles) {
                    if (r.getSid().equalsIgnoreCase(roleSid)
                            && r.getRoleName().equalsIgnoreCase(role.getEdisonRoleName())) {
                        foundRole = true;
                    }
                }
            }
        }

        assertTrue("user was not found", foundUser);
        assertTrue("role was not found", foundRole);

    }

    @Test
    public void addUserForEachRoleTest() throws IOException {
        CustomersClient custClient = new CustomersClient(rc);
        custClient.setServiceURLAndToken(serviceURL, token);

        //create 3 customers
        final int customerCount = 3;

        List<Customer> customerList = new ArrayList<>();

        for (int i = 0; i < customerCount; i++) {
            Customer c = new Customer();
            c.setCustomerName("testcust" + Integer.toString(i));

            c.setAddress(getAddressTemplate());

            String payload = c.getUpdatePayload();
            OEResponse resp = custClient.addCustomer(payload);
            assertTrue("failed to add customer", resp.responseCode == 200);

            Customer cust = (Customer) resp.responseObject;
            assertTrue("customer was not added/updated", c.getCustomerName().compareToIgnoreCase(cust.getCustomerName()) == 0);

            customerList.add(cust);
        }

        SitesClient sitesClient = new SitesClient(rc);
        sitesClient.setServiceURLAndToken(serviceURL, token);

        //for each customer, add a site
        List<Site> siteList = new ArrayList<>();
        for (Customer c : customerList) {

            DateTimeFormatter fmt = DateTimeFormat.forPattern("MMddHHmmssSSS");
            String siteName = "s" + DateTime.now().toString(fmt);

            SiteTemplate siteTemplate = new SiteTemplate(c.getSid(), null, siteName);
            CreateSiteRequest csr = new CreateSiteRequest(siteTemplate.getSite());
            OEResponse resp = sitesClient.createSite(csr);
            assertTrue("Could not create site", resp.responseCode == 200);
            Site createdSite = (Site) resp.responseObject;
            assertTrue("No sid for new site", !createdSite.getSid().isEmpty());

            siteList.add(createdSite);

        }

        UsersClient uc = new UsersClient(rc);
        uc.setServiceURLAndToken(serviceURL, token);

        List<String> userNames = new ArrayList<>();

        int roleIndex = 1;
        for (EnumUserRoles role : EnumUserRoles.values()) {

            DateTimeFormatter fmt = DateTimeFormat.forPattern("MMddHHmmssSSS");
            String userName = "U" + DateTime.now().toString(fmt);
            userName = userName.toLowerCase();

            CreateUserRequest cur = new CreateUserRequest();
            cur.setFirstname("Omnibus_" + Integer.toString(roleIndex) );
            roleIndex++;
            cur.setUsername(userName);
            cur.setLastname(role.getFriendlyName());
            cur.setEmail(userName + "@overThere.com");
            cur.setInvitationMessage("Welcome to the lunatic fringe!");
            cur.setNoInvite(Boolean.TRUE);

            cur.setPhones(getPhonesTemplate(5));
            cur.setRoles(getRoleItemsTemplate(siteList, role));

            OEResponse resp = uc.postUser(cur);
            assertTrue("Could not add user", resp.responseCode == 200);

            userNames.add(userName);

        }

        OEResponse resp = uc.getUsers("");

        assertTrue("Could not get users", resp.responseCode == 200);

        Users users = (Users) resp.responseObject;
        List<UserInfo> userList = users.getUsers();

        for (UserInfo user : userList) {

            if (userNames.contains( user.getUserName())) {
                resp = uc.getUser(user.getUserID());
                assertTrue("could not get userInfo", resp.responseCode == 200);

                UserInfo u = (UserInfo) resp.responseObject;

                List<RoleItem> roles = u.getRoles();
                assertTrue("user should have 3 roles", roles.size() == 3);
            }

        }

    }

    private Map getAddressTemplate() {
        Map addressMap = new HashMap();

        addressMap.put("street", "411 First Avenue");
        addressMap.put("city", "Seattle");
        addressMap.put("state", "Washington");
        addressMap.put("postCode", "98111");
        addressMap.put("country", "USA");

        return addressMap;
    }

    public List<Phone> getPhonesTemplate(int countOfPhones) {

        List<Phone> phones = new ArrayList();

        for (int i = 0; i < countOfPhones; i++) {
            Phone p = new Phone();
            p.setPhone(String.format("123-123-%04d", i));
            p.setPhoneType("PhoneType" + Integer.toString(i));
            phones.add(p);
        }
        return phones;
    }

    public List<RoleItem> getRoleItemsTemplate(List<Site> sites, EnumUserRoles roleNameType) {
        List<RoleItem> roles = new ArrayList<>();

        for (Site s : sites) {
            RoleItem ri = new RoleItem();
            ri.setSid(s.getSid());
            ri.setRoleName(roleNameType.getEdisonRoleName());
            roles.add(ri);
        }

        return roles;

    }

    public List<RoleItem> getRoleItemsTemplate(String sid, EnumUserRoles roleNameType) {
        List<RoleItem> roles = new ArrayList<>();
        RoleItem ri = new RoleItem();
        ri.setSid(sid);
        ri.setRoleName(roleNameType.getEdisonRoleName());
        roles.add(ri);
        return roles;
    }
}
*/
