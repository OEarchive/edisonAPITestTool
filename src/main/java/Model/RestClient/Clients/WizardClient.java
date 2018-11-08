package Model.RestClient.Clients;

import Model.DataModels.Wizard.CompilePostCommand;
import Model.DataModels.Wizard.WizardStatusResponse;
import Model.DataModels.Wizard.NetworkSettingsResponse;
import Model.DataModels.Wizard.WizardActivationPostRequest;
import Model.DataModels.Wizard.WizardFile;
import Model.DataModels.Wizard.WizardFileList;
import Model.DataModels.Wizard.WizardPlantProfile;
import Model.DataModels.Wizard.WizardPoint;
import Model.DataModels.Wizard.WizardPointsList;
import Model.DataModels.Wizard.WizardPostConfigFileInfo;
import Model.DataModels.Wizard.WizardPostConfigFilesRequest;
import Model.DataModels.Wizard.WizardStep;
import Model.DataModels.Wizard.WizardSteps;
import Model.DataModels.Wizard.WizardUser;
import Model.RestClient.OEResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class WizardClient {

    private String serviceURL;
    private final RestClientCommon restClient;

    public WizardClient(RestClientCommon restClient) {
        this.restClient = restClient;
    }

    public void setServiceURLAndToken(String serviceURL, String accessToken) {
        this.serviceURL = serviceURL;
        restClient.setOauthToken(accessToken);
    }

    public OEResponse getCompileStatus() throws IOException {

        String url = serviceURL + "/compile";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardStatusResponse.class);
        }

        return resObj;
    }

    public OEResponse postCompileCommand(CompilePostCommand cmd) throws IOException {

        String url = serviceURL + "/compile";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(cmd);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardStatusResponse.class);
        }

        return resObj;
    }

    public OEResponse getNetworkSettings() throws IOException {

        //String url = serviceURL + "/networkSettings";
        String url = "https://192.168.4.112/networkSettings";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, NetworkSettingsResponse.class);
        }

        return resObj;
    }

    public OEResponse putNetworkSettings(NetworkSettingsResponse nwSettings) throws IOException {

        String url = serviceURL + "/networkSettings";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(nwSettings);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        return resObj;
    }

    public OEResponse getPlantProfile() throws IOException {

        String url = serviceURL + "/plantProfile";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardPlantProfile.class);
        }

        return resObj;
    }

    public OEResponse getPointsList() throws IOException {

        String url = serviceURL + "/points";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardPointsList.class);
        }

        return resObj;
    }

    public OEResponse getControlPoint(String pointName) throws IOException {

        String url = serviceURL + "/points/" + pointName;

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardPoint.class);
        }

        return resObj;
    }

    public OEResponse upsertControlPoint(WizardPoint point) throws IOException {

        String url = serviceURL + "/networkSettings";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(point);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        return resObj;
    }

    public OEResponse getUserMe() throws IOException {

        String url = serviceURL + "/users/me";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardUser.class);
        }

        return resObj;
    }

    public OEResponse putUsersMe(WizardUser user) throws IOException {

        String url = serviceURL + "/users/me";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(user);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        return resObj;
    }

    public OEResponse getWizardSteps() throws IOException {

        String url = serviceURL + "/wizard";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardSteps.class);
        }

        return resObj;
    }

    public OEResponse getWizardStep(String stepId) throws IOException {

        String url = serviceURL + "/wizard/" + stepId;

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardStep.class);
        }

        return resObj;
    }

    //TODO - does this need a step as a query param?
    public OEResponse putWizardStep(String stepId, WizardStep wizardStep) throws IOException {

        String url = serviceURL + "/wizard/" + stepId;

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(wizardStep);
        OEResponse resObj = restClient.doPutAndGetBody(url, payload, true);

        return resObj;
    }

    // ======CLOUD ACIVATION=================
    public OEResponse getCloudActivationStatus() throws IOException {

        String url = serviceURL + "/cloudActivation";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardStatusResponse.class);
        }

        return resObj;
    }

    public OEResponse postCloudActivation(WizardActivationPostRequest activationRequest) throws IOException {

        String url = serviceURL + "/cloudActivation/";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(activationRequest);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardStatusResponse.class);
        }

        return resObj;
    }

    public OEResponse getCloudAuthentication() throws IOException {

        String url = serviceURL + "/cloudAuthentication";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardStatusResponse.class);
        }

        return resObj;
    }

    //TODO - what should be posted?
    public OEResponse postCloudAuthentication(String whatever) throws IOException {

        String url = serviceURL + "/cloudAuthentication";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(whatever);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        return resObj;
    }

    public OEResponse getCloudConnection() throws IOException {

        String url = serviceURL + "/cloudConnection";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardStatusResponse.class);
        }

        return resObj;
    }

    //TODO - what should be posted?
    public OEResponse postCloudConnection(String whatever) throws IOException {

        String url = serviceURL + "/cloudConnection";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(whatever);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        return resObj;
    }
    
    public OEResponse getOptimizationConfigurationFile() throws IOException {

        String url = serviceURL + "/optimizationConfiguratonFile";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardFileList.class);
        }

        return resObj;
    }

    
    public OEResponse postOptimizationConfigurationFile(WizardPostConfigFilesRequest configFiles) throws IOException {

        String url = serviceURL + "/optimizationConfigurationFile";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(configFiles);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        return resObj;
    }
    
    public OEResponse getOptimizationProgram() throws IOException {

        String url = serviceURL + "/optimizationProgram";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardFile.class);
        }

        return resObj;
    }
    
    public OEResponse postOptimizationProgram(WizardPostConfigFileInfo configFile) throws IOException {

        String url = serviceURL + "/optimizationConfigurationFile";

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(configFile);
        OEResponse resObj = restClient.doPostAndGetBody(url, payload, true);

        if (resObj.responseCode == 200) {
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardFile.class);
        }
        
        return resObj;
    }
    
    public OEResponse getOptimizationStatus() throws IOException {

        String url = serviceURL + "/optimizationStatus";

        OEResponse resObj = restClient.getResponse(url);

        if (resObj.responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            resObj.responseObject = mapper.readValue((String) resObj.responseObject, WizardStatusResponse.class);
        }

        return resObj;
    }
}
