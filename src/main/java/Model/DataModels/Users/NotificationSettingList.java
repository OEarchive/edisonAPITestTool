
package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class NotificationSettingList {

    @JsonProperty("NotificationSettingList")
    private List<NotificationSetting> notifications;


    public List<NotificationSetting> getNotifications() {
        return this.notifications;
    }
    
    @JsonIgnore
    public void setNotifications( List<NotificationSetting> notifications ){
        this.notifications = notifications;
    }


}