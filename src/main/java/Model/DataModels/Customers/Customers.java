package Model.DataModels.Customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Customers {

    @JsonProperty("customers")
    private List<Customer> customers;
    
    
    public List<Customer> getCustomers(){
        return this.customers;
    }

}
