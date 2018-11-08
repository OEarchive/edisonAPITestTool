
package Model.DataModels.Auth;


public class AdminLoginCreds {
    
    private final String username;
    private final char[] password;
    
    public AdminLoginCreds( String username, char[] password ){
        this.username = username;
        this.password = password;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return String.valueOf(password);
    }
    
}
