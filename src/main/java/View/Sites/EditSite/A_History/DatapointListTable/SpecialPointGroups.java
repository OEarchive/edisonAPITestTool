
package View.Sites.EditSite.A_History.DatapointListTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpecialPointGroups {
    
    private static Map<String,List<String>> specialPointsGroup;
    
    public SpecialPointGroups(){
        specialPointsGroup = new HashMap<>();
        specialPointsGroup.put("Baseline",
                Arrays.asList( new String[]{
                        "BaselinekW",
                        "BaselinekWTon",
                        "BaselinekWh",
                        "TotalTon",
                        "TotalkW",
                        "TotalkWh",
                        "kWDelta",
                        "kWhDelta"
                } ) );
        
    } 
    
    public static List<String> getGroupNames(){
        List<String> names = new ArrayList<>();
        
        for( String name : specialPointsGroup.keySet()){
            names.add(name);
        }
        
        return names;
    }
    
    public static List<String> getPointNamesInGroup( String key ){
        return specialPointsGroup.get(key);
    }
}
