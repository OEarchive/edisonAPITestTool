package Model.DataModels.Auth;

import java.io.UnsupportedEncodingException;

public class GetE3OSTokenRequest {

    private final String username;
    private final String cust_id;
    private final String site_id;
    private final String redirectURL;

    public GetE3OSTokenRequest(
            String username,
            String cust_id,
            String site_id,
            String redirectURL) {
        this.username = username;
        this.cust_id = cust_id;
        this.site_id = site_id;
        this.redirectURL = redirectURL;
    }


    public String getQueryParamsString() throws UnsupportedEncodingException {

        String queryString = String.format(
                "?username=%s&cust_id=%s&site_id=%s",
                username,
                cust_id,
                site_id
        );
        
        if( redirectURL.length() > 0 ){
            queryString += "&redirectURL=" + redirectURL;
        }

        return queryString;
    }

}
