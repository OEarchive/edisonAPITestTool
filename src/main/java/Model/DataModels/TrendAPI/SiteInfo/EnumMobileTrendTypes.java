
package Model.DataModels.TrendAPI.SiteInfo;

import java.util.ArrayList;
import java.util.List;


public enum EnumMobileTrendTypes {
    
    savings("savings"),
    plant("plant"),
    optimization("optimization"),
    chiller("chiller"),
    key("key");

    final private String friendlyName;

    EnumMobileTrendTypes(String name) {
        this.friendlyName = name;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }
    
    static public List<String> getFriendlyNames(){
        List<String> names = new ArrayList<>();
        
        for( EnumMobileTrendTypes trend : EnumMobileTrendTypes.values() ){
            names.add( trend.getFriendlyName() );
        }
        
        return names;
    }

    static public EnumMobileTrendTypes getEnumFromDropdownName(String name) {
        for (EnumMobileTrendTypes res : EnumMobileTrendTypes.values()) {
            if (res.name().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }
}
