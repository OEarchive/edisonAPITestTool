
package Model.DataModels.Datapoints;

import java.util.ArrayList;
import java.util.List;


public enum EnumPointLevel {
    Site("Site", 0),
    Station("Station", 1);

    private String name;
    private int dropDownIndex;

    EnumPointLevel(String name, int dropDownIndex) {
        this.name = name;
        this.dropDownIndex = dropDownIndex;

    }

    public String getName() {
        return this.name;
    }
    
    public int getDropDownIndex(){
        return this.dropDownIndex;
    }

    static public List<String> getLevelNames() {
        List<String> names = new ArrayList<>();
        for (EnumPointLevel res : EnumPointLevel.values()) {
            names.add(res.getName());
        }
        return names;
    }
    
    static public EnumPointLevel getLevelFromName( String name ){
        for (EnumPointLevel res : EnumPointLevel.values()) {
            if( res.getName().compareTo(name) == 0 ){
                return res;
            }
        }
        return null;  
    }
}
