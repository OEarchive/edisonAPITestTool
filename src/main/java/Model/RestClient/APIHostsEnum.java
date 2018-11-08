
package Model.RestClient;

import java.util.ArrayList;
import java.util.List;

public enum APIHostsEnum {
    OMNIBUS("OMNIBUS"),
    DOCKER("DOKRMAC"),
    OEDEV("OEDEV"),
    PROD("PROD");

    private String dropDownName;

    APIHostsEnum( String name )
    {
        this.dropDownName = name;
        
    }
    
    public String getName(){
        return this.dropDownName;
    }
    
    static public List<String> getNames(){
        List<String> names = new ArrayList<>();
        for( APIHostsEnum loc : APIHostsEnum.values()){
          names.add(loc.getName() );
        }
        return names;
    }
    
    static public APIHostsEnum getHostFromName( String name ){
        for( APIHostsEnum host : APIHostsEnum.values() ){
            if( host.getName().compareTo(name) == 0 ){
                return host; 
            }
        }
        return null;
    }
    
    
}



