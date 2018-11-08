
package Model.RestClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class APIHosts {

    private Map<APIHostsEnum,  APILoginInfo> dbsAndServices;

    public APIHosts() {

        InputStream input = null;

        try {
            
            Properties prop = new Properties();
            input = APIHosts.class.getResourceAsStream("OptiCxAPITool.properties");
            prop.load(input);
    
        
            APILoginInfo OMNIBUS = new APILoginInfo(
                    prop.getProperty("OMNIBUS_SERVICE_ENDPOINT"),
                    prop.getProperty("OMNIBUS_ADMIN_ENDPOINT")
            );
            
            APILoginInfo OEDEV = new APILoginInfo(
                    prop.getProperty("OEDEV_SERVICE_ENDPOINT"),
                    prop.getProperty("OEDEV_ADMIN_ENDPOINT")
            );


            APILoginInfo PROD = new APILoginInfo(
                    prop.getProperty("PROD_SERVICE_ENDPOINT"),
                    prop.getProperty("PROD_ADMIN_ENDPOINT")
            );
            
            APILoginInfo DOCKER = new APILoginInfo(
                    prop.getProperty("DOCKER_SERVICE_ENDPOINT"),
                    prop.getProperty("DOCKER_ADMIN_ENDPOINT")
            );
            
            dbsAndServices = new HashMap<APIHostsEnum, APILoginInfo>();
            dbsAndServices.put(APIHostsEnum.OMNIBUS, OMNIBUS);
            dbsAndServices.put(APIHostsEnum.PROD, PROD);
            dbsAndServices.put(APIHostsEnum.DOCKER, DOCKER);
            dbsAndServices.put(APIHostsEnum.OEDEV, OEDEV);

            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
    

    public Map<APIHostsEnum, APILoginInfo> getList() {
        return this.dbsAndServices;
    }

    public APILoginInfo getHostInfo(APIHostsEnum db) {
        return this.dbsAndServices.get(db);
    }

    public List<String> getListOfDBNames() {
        List<String> listOfDBNames = new ArrayList<>();

        for (APIHostsEnum db : this.dbsAndServices.keySet()) {
            listOfDBNames.add(db.getName());
        }
        return listOfDBNames;
    }

}