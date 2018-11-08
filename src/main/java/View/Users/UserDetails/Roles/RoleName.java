
package View.Users.UserDetails.Roles;


public class RoleName {
    private String roleName;
    
    public RoleName( String roleName ){
        super();
        this.roleName = roleName;
    }
    
    public String getRoleName(){
        return this.roleName;
    }
    
    public void setRoleName( String roleName ){
        this.roleName = roleName;
    }
    
    public String toString(){
        return this.roleName;
    }
}
