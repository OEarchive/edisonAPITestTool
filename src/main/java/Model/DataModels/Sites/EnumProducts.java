
package Model.DataModels.Sites;

import java.util.ArrayList;
import java.util.List;


public enum EnumProducts {
    edge("edge", 0),
    air("air", 1);

    private String name;
    private int dropDownIndex;

    EnumProducts(String name, int dropDownIndex) {
        this.name = name;
        this.dropDownIndex = dropDownIndex;
    }

    public String getName() {
        return this.name;
    }
    
    public int getDropDownIndex(){
        return this.dropDownIndex;
    }

    static public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (EnumProducts res : EnumProducts.values()) {
            names.add(res.getName());
        }
        return names;
    }
    
    static public EnumProducts getEnumFromName( String name ){
        for (EnumProducts res : EnumProducts.values()) {
            if( res.getName().compareTo(name) == 0 ){
                return res;
            }
        }
        return null;
    }

}