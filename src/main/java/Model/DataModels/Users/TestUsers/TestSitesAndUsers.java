package Model.DataModels.Users.TestUsers;

import Model.DataModels.Sites.TestSiteTemplates.EnumTestSites;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSitesAndUsers {

    private final Map<EnumTestSites, List<EnumTestUsers>> map;

    public TestSitesAndUsers() {

        map = new HashMap<>();

        //cullen
        List<EnumTestUsers> list = new ArrayList<>();
        list.add(EnumTestUsers.Bill);
        list.add(EnumTestUsers.Perry);
        list.add(EnumTestUsers.Eddy);
        list.add(EnumTestUsers.Pete);
        map.put(EnumTestSites.Cullen, list);

        //carnot
        list = new ArrayList<>();
        list.add(EnumTestUsers.Bill);
        list.add(EnumTestUsers.Aaron);
        list.add(EnumTestUsers.Patty);
        list.add(EnumTestUsers.Perry);
        list.add(EnumTestUsers.Eddy);
        list.add(EnumTestUsers.Pete);
        map.put(EnumTestSites.Carnot_1, list);
        map.put(EnumTestSites.Carnot_2, list);
        map.put(EnumTestSites.Carnot_3, list);
        map.put(EnumTestSites.Carnot_4, list);

        //perkins
        list = new ArrayList<>();
        list.add(EnumTestUsers.Bill);
        list.add(EnumTestUsers.Aaron);
        list.add(EnumTestUsers.Patty);
        list.add(EnumTestUsers.Elon);
        list.add(EnumTestUsers.Perry);
        list.add(EnumTestUsers.Eddy);
        list.add(EnumTestUsers.Pete);
        list.add(EnumTestUsers.Brenda);
        map.put(EnumTestSites.Perkins_1, list);
        map.put(EnumTestSites.Perkins_2, list);
        map.put(EnumTestSites.Perkins_3, list);

    }
    
    public List<EnumTestUsers> getUsers( EnumTestSites testSite ){
        return map.get(testSite);
    }

}
