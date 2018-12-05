
package Model.DataModels.TeslaModels.CreateTeslaSiteModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeslaPostCustomer {
    
    @JsonProperty("name")
    private final String name;

    @JsonProperty("salesForceId")
    private final String salesForceId;


    @JsonIgnore
    public TeslaPostCustomer( String name, String salesForceId ){
        this.name = name;
        this.salesForceId = salesForceId;
    }

    public String getName() {
        return name;
    }

    public String getSalesForceId() {
        return salesForceId;
    }

}
