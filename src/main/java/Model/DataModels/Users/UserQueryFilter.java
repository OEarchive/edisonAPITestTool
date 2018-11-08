package Model.DataModels.Users;

public class UserQueryFilter {

    private final String atSid;
    private final String withRole;
    private final boolean includeLocked;
    private final boolean includeNotLocked;
    private final boolean includeLoggedIn;
    private final boolean includeNotLoggedIn;

    
    public UserQueryFilter(){
        this.atSid = "";
        this.withRole = "";
        this.includeLocked = true;
        this.includeNotLocked = true;
        this.includeLoggedIn = true;
        this.includeNotLoggedIn = true;
    }
    
    
    
    public UserQueryFilter(
            String atSid,
            String withRole,
            boolean includeLocked,
            boolean includeNotLocked,
            boolean includeLoggedIn,
            boolean includeNotLoggedIn
            ){
        this.atSid = atSid;
        this.withRole = withRole;
        this.includeLocked = includeLocked;
        this.includeNotLocked = includeNotLocked;
        this.includeLoggedIn = includeLoggedIn;
        this.includeNotLoggedIn = includeNotLoggedIn;
    }
    

    
    
    public String getQueryString(){
        
        String queryString = "";
        
        if( atSid.length() > 0 ){
            queryString += "atSid=" + atSid;
        }
        
        if( withRole.length() > 0 ){
            if( queryString.length() > 0 ){
                queryString += "&";
            }
            queryString += "withRole=" + withRole;
        }
        
        if( !includeLocked && includeNotLocked ){
            if( queryString.length() > 0 ){
                queryString += "&";
            }
            queryString += "isLocked=false";
        }
        else if( includeLocked && !includeNotLocked){
            if( queryString.length() > 0 ){
                queryString += "&";
            }
            queryString += "isLocked=true";
        }
        
        if( !includeLoggedIn && includeNotLoggedIn ){
            if( queryString.length() > 0 ){
                queryString += "&";
            }
            queryString += "redeemedInvitation=false";
        }
        else if( includeLoggedIn && !includeNotLoggedIn){
            if( queryString.length() > 0 ){
                queryString += "&";
            }
            queryString += "redeemedInvitation=true";
        }
        
        if( queryString.length() > 0 ){
                queryString = "?" + queryString;
        }
        
        return queryString;
    }

}
