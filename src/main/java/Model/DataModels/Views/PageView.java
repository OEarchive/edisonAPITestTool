package Model.DataModels.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageView {

    @JsonProperty("site")
    private ViewSite site;

    @JsonProperty("ui")
    private Map ui;

    @JsonProperty("groups")
    private List<ItemGroup> groups;

    public ViewSite getSite() {
        return site;
    }

    public Map getUI() {
        return ui;
    }

    public List<ItemGroup> getGroups() {
        return groups;
    }

    @JsonIgnore
    public Map< String, List<Point>> getDatapointList() {

        Map< String, List<Point>> dpMap = new HashMap<>();

        List<Point> dpList = new ArrayList<>();
        for (Point p : site.getPoints()) {
            dpList.add(p);
        }
        dpMap.put(site.getSid(), dpList);

        dpList = new ArrayList<>();
        for (PointGroup g : site.getPointGroups()) {
            for (Point p : g.getPoints()) {
                dpList.add(p);
            }
        }
        dpMap.put(site.getSid(), dpList);

        for (ItemGroup g : groups) {
            for (ViewItem item : g.getItems()) {

                String itemSid = item.getSid();
                dpList = new ArrayList<>();
                for (PointGroupSection pgs : item.getPointGroupSections()) {

                    for (PointGroup pg : pgs.getPointGroups()) {
                        for (Point p : pg.getPoints()) {
                            dpList.add(p);
                        }
                    }
                }
                dpMap.put(itemSid, dpList);

                dpList = new ArrayList<>();
                for (Point p : item.getPoints()) {
                    dpList.add(p);
                }
                dpMap.put(itemSid, dpList);
            }
        }

        return dpMap;
    }

}
