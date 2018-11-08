
package Model.DataModels.Sites;

import java.util.ArrayList;
import java.util.List;


public enum EnumCommProtocols {
    bacnet("bacnet", 0),
    modbus("modbus", 1);

    private String name;
    private int dropDownIndex;

    EnumCommProtocols(String name, int dropDownIndex) {
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
        for (EnumCommProtocols res : EnumCommProtocols.values()) {
            names.add(res.getName());
        }
        return names;
    }
    
    static public EnumCommProtocols getEnumFromName( String name ){
        for (EnumCommProtocols res : EnumCommProtocols.values()) {
            if( res.getName().compareTo(name) == 0 ){
                return res;
            }
        }
        return null;
    }

}
