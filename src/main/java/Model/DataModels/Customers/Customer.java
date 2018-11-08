package Model.DataModels.Customers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class Customer {

    @JsonProperty("sid")
    private String sid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private Map address;
    @JsonProperty("extSfId")
    private String extSfId;

    public String getSid() {
        return sid;
    }
    
    public void setSid( String sid) {
        this.sid = sid;
    }

    public String getCustomerName() {
        return name;
    }

    @JsonIgnore
    public void setCustomerName(String str) {
        this.name = str;
    }

    public Map getAddress() {
        return address;
    }

    @JsonIgnore
    public void setAddress(Map map) {
        this.address = map;
    }

    public String getExtSfId() {
        return extSfId;
    }

    @JsonIgnore
    public void setExtSfId(String extSfId) {
        this.extSfId = extSfId;
    }
    
    

    @JsonIgnore
    public String getUpdatePayload() throws JsonProcessingException {

        Map<String, Object> keyPairs = new HashMap<>();

        if (name!=null && name.length() > 0) {
            keyPairs.put("name", getCustomerName());
        }

        if (extSfId != null && extSfId.length() > 0) {
            keyPairs.put("extSfId", getExtSfId());
        }

        if (address != null && address.size() > 0) {
            keyPairs.put("address", getAddress());
        }

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(keyPairs);

        return payload;
    }

}
