
package Model.DataModels.TeslaModels.CreateTeslaSiteModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeslaPostCustomer {
    
    @JsonProperty("name")
    private String name;

    @JsonProperty("salesForceId")
    private String salesForceId;


    @JsonIgnore
    public TeslaPostCustomer( String name, String shortName ){
        this.name = name;
        this.salesForceId = shortName;
    }

    public String getName() {
        return name;
    }

    public String getSalesForceId() {
        return salesForceId;
    }

}
