
package Model.DataModels.Live.Subscriptions;

import java.util.Map;


public class SubscriptionReqWithID {
    
    private final Map<String,String> jaceToEdsionNameMap;
    private final Map<String,String> jaceToUOMMap;
    
    private final CreateSubscriptionRequest req;
    private CreateSubscriptionResponse resp;
    
    public SubscriptionReqWithID( Map<String,String> jaceToEdsionNameMap, Map<String,String> jaceToUOMMap, CreateSubscriptionRequest req ){
        this.jaceToEdsionNameMap = jaceToEdsionNameMap;
        this.jaceToUOMMap = jaceToUOMMap;
        this.req = req;
        this.resp = null;
    }
    
    public void setResp( CreateSubscriptionResponse resp ){
        this.resp = resp;
    }
    
    public Map<String,String> getJaceToEdisonNameMap(){
        return jaceToEdsionNameMap;
    }
    
        public Map<String,String> getJaceToUOMMap(){
        return jaceToUOMMap;
    }
    
    public CreateSubscriptionRequest getReq(){
        return this.req;
    }
    
    public CreateSubscriptionResponse getResp(){
        return this.resp;
    }
    
}
