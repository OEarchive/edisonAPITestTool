
package Model.DataModels.TeslaModels;

import java.util.ArrayList;
import java.util.List;


public enum EnumTeslaUsers {
    DevOps("devops@optimumenergyco.com", "password");

    private final String email;
    private final String password;

    EnumTeslaUsers(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
    
    static public List<String> getUsernames(){
        List<String> userNames = new ArrayList<>();
        for( EnumTeslaUsers user : EnumTeslaUsers.values()){
          userNames.add(user.name() );
        }
        return userNames;
    }
    
    static public EnumTeslaUsers getUserFromName( String userName ){
        for( EnumTeslaUsers user : EnumTeslaUsers.values() ){
            if( user.name().compareTo(userName) == 0 ){
                return user; 
            }
        }
        return null;
    }

}

