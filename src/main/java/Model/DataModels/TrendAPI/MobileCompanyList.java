package Model.DataModels.TrendAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class MobileCompanyList {

    @JsonProperty("companies")
    private List<MobileCompany> companies;

    public List<MobileCompany> getCompanies() {
        return this.companies;
    }

    @JsonIgnore
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (MobileCompany c : companies) {
            names.add(c.getName());
        }
        return names;
    }

}
