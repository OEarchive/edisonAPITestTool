
package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


public class AlarmsHistoryQueryParams {

    
    @JsonProperty("alarms")
    private List<String> alarms;
    
    
    @JsonProperty("start")
    private String start;

    @JsonProperty("end")
    private String end;



    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }


 
    public void setAlarmNames(List<String> alarms) {
        this.alarms = alarms;
    }


    public String getQueryParamsString() throws UnsupportedEncodingException {
        
        String alarmsList = "*";
        if( alarms.size() > 0 ){
            alarmsList = String.join(",", alarms);
        }
        
        String queryString = String.format(
                "?alarmNames=%s&start=%s&end=%s",
                URLEncoder.encode(alarmsList, "UTF-8"),
                start,
                end);

        return queryString;
    }
}
