package Model.RestClient.Clients;

import Model.DataModels.Users.CreateUserRequest;
import Model.DataModels.Users.CurrentUserInfoResponse;
import Model.DataModels.Users.ModifyCurrentUserPasswordResponse;
import Model.DataModels.Users.ModifyCurrentUserRequest;
import Model.DataModels.Users.ModifyUserStatusRequest;
import Model.DataModels.Users.ModifyUsersPasswordRequest;
import Model.DataModels.Users.NotificationSetting;
import Model.DataModels.Users.NotificationSettingList;
import Model.DataModels.Users.ResendInviteRequest;
import Model.DataModels.Users.RoleItem;
import Model.DataModels.Users.SetSiteActivationCodeRequest;
import Model.DataModels.Users.UserInfo;
import Model.DataModels.Users.Users;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

public class UsersClient {

    private String serviceURL;
    private final RestClientCommon restClient;

    public UsersClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }

    public void setServiceURLAndToken(String serviceURL, String accessToken) {
        this.serviceURL = serviceURL;
        restClient.setOauthToken(accessToken);
    }
    
    
    public OEResponse getUsers(String queryParams) throws IOException {

        String url = serviceURL + "/users" + queryParams;
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            //resObj.responseObject =  mapper.readValue((String)resObj.responseObject, new TypeReference<List<UserInfo>>(){});
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, Users.class);
        }
        return resObj;
    }

    //Create or invite a user
    public OEResponse postUser(CreateUserRequest cur) throws IOException {

        String url = serviceURL + "/users";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(cur);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, UserInfo.class);
        }
        return resObj;
    }

    //resend invite
    public OEResponse resendInvite(String userID, ResendInviteRequest req) throws IOException {

        String url = serviceURL + "/users/" + userID + "/resend-invite";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //Delete a user
    public OEResponse deleteUser(String userID) throws IOException {

        String url = serviceURL + "/users/" + userID;
        OEResponse resObj = restClient.doDelete(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //Get info for specific user
    public OEResponse getUser(String userID) throws IOException {

        String url = serviceURL + "/users/" + userID;
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, UserInfo.class);
        }
        return resObj;
    }

    //Update a speicific user
    public OEResponse putUser(String userID, String payload) throws IOException {

        String url = serviceURL + "/users/" + userID;
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, UserInfo.class);
        }
        return resObj;
    }

    //modify user's status
    public OEResponse putUserStatus(String userID, ModifyUserStatusRequest req) throws IOException {

        String url = serviceURL + "/users/" + userID + "/status";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //Modify a user's roles
    public OEResponse putUsersRoles(String userID, List<RoleItem> roles) throws IOException {

        String url = serviceURL + "/users/" + userID + "/roles";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(roles);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //Remove a role from a user
    public OEResponse deleteUserRoles(String userID, String sid) throws IOException {

        String url = serviceURL + "/users/" + userID + "/roles/" + sid;
        OEResponse resObj = restClient.doDelete(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //Change the user's roles for a given sid. This is a paste over operation that overwrites all of the user's role mappings for a given sid.
    public OEResponse changeRolesForSid(String userID, String roleSid, List<RoleItem> roles) throws IOException {

        String url = serviceURL + "/users/" + userID + "/roles/" + roleSid;

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(roles);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //Set site activation code for a user
    public OEResponse putActivationCode(String userID, SetSiteActivationCodeRequest req) throws IOException {

        String url = serviceURL + "/users/" + userID + "/activation_code";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //Get Notifications
    public OEResponse getNotifications(String userID) throws IOException {

        String url = serviceURL + "/users/" + userID + "/notifications";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            //resObj.responseObject = mapper.readValue((String) resObj.responseObject, NotificationSettingList.class);
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, new TypeReference<List<NotificationSetting>>() {});
        }
        return resObj;
    }

    public OEResponse setNotifications(String userID, NotificationSettingList req) throws IOException {

        String url = serviceURL + "/users/" + userID + "/notifications";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req.getNotifications());
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //Get info about current user
    public OEResponse getUsersMe() throws IOException {

        String url = serviceURL + "/users/me";
        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            resObj.responseObject = (String) resObj.responseObject;
        }
        return resObj;
    }

    //Get info about current user
    public OEResponse putUsersMe(ModifyCurrentUserRequest req) throws IOException {

        String url = serviceURL + "/users/me";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, CurrentUserInfoResponse.class);
        }
        return resObj;
    }
    
    //Modify current user password
    public OEResponse postPassword(ModifyUsersPasswordRequest req) throws IOException {

        String url = serviceURL + "/users/me/password";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(req);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, ModifyCurrentUserPasswordResponse.class);
        }
        return resObj;
    }

}
