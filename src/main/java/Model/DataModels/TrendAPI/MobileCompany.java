
package Model.DataModels.TrendAPI;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MobileCompany {

    @JsonProperty("name")
    private String name;
    
    @JsonProperty("uuid")
    private String uuid;

    public String getName() {
        return this.name;
    }

    public String getUUID() {
        return this.uuid;
    }

}
