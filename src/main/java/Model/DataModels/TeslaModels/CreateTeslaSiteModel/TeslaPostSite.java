
package Model.DataModels.TeslaModels.CreateTeslaSiteModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class TeslaPostSite {
    
    @JsonProperty("name")
    private String name;

    @JsonProperty("shortName")
    private String shortName;


    @JsonIgnore
    public TeslaPostSite( String name, String shortName ){
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

}
