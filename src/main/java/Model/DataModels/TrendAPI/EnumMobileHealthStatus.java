
package Model.DataModels.TrendAPI;


public enum EnumMobileHealthStatus {
    Alive("Alive"),
    Dead("Dead");
    
    final private String friendlyName;
    
    EnumMobileHealthStatus( String name ){
        this.friendlyName = name;
    }
    
    public String getFriendlyName(){
        return this.friendlyName;
    }
    
}