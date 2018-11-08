package Model.RestClient;

import Model.DataModels.Wizard.CompilePostCommand;
import Model.DataModels.Wizard.NetworkSettingsResponse;
import Model.DataModels.Wizard.WizardActivationPostRequest;
import Model.DataModels.Wizard.WizardFile;
import Model.DataModels.Wizard.WizardFileList;
import Model.DataModels.Wizard.WizardPlantProfile;
import Model.DataModels.Wizard.WizardPoint;
import Model.DataModels.Wizard.WizardPointsList;
import Model.DataModels.Wizard.WizardPostConfigFileInfo;
import Model.DataModels.Wizard.WizardPostConfigFilesRequest;
import Model.DataModels.Wizard.WizardStatusResponse;
import Model.DataModels.Wizard.WizardStep;
import Model.DataModels.Wizard.WizardSteps;
import Model.DataModels.Wizard.WizardUser;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.RestClientCommon;
import Model.RestClient.Clients.WizardClient;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WizardModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;

    private WizardClient client;

    public WizardModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;

        this.client = new WizardClient(model.getRestClient());
        this.pcs = pcs;
    }

    public void resetClient(String serviceURL, String accessToken, RestClientCommon api) {
        this.client = new WizardClient(api);
        this.client.setServiceURLAndToken(serviceURL, accessToken);
    }

    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void getCompileStatus() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getCompileStatus();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStatusResponse wizardStatus = (WizardStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardCompileStatusResturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void postCompileCommand(final CompilePostCommand cmd) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.postCompileCommand(cmd);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStatusResponse wizardStatus = (WizardStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardDidPostCompile.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getNetworkSettings() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getNetworkSettings();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        NetworkSettingsResponse wizardStatus = (NetworkSettingsResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardNetworSettingsReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void putNetworkSettings(final NetworkSettingsResponse nwSettings) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.putNetworkSettings(nwSettings);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        NetworkSettingsResponse wizardStatus = (NetworkSettingsResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardNetworkSettingsAppied.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getPlantProfile() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getPlantProfile();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardPlantProfile wizardStatus = (WizardPlantProfile) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardPointsListReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getPointsList() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getPointsList();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardPointsList wizardStatus = (WizardPointsList) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardPlantProfiledReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getControlPoint(final String pointName) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getControlPoint(pointName);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardPoint wizardStatus = (WizardPoint) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardPointReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void upsertControlPoint(final WizardPoint point) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.upsertControlPoint(point);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardPointUpdated.getName(), null, msg);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getWizardUsersMe() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getUserMe();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardUser wizardStatus = (WizardUser) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardUsersMeReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void putWizardUsersMe(final WizardUser user) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.putUsersMe(user);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String wizardStatus = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardUsersMeUpdated.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getWizardSteps() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getWizardSteps();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardSteps wizardStatus = (WizardSteps) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardListsOfStepsReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getWizardStep(final String stepId) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getWizardStep(stepId);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStep wizardStatus = (WizardStep) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardStepReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void putWizardStep(final String stepId, final WizardStep wizardStep) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.putWizardStep(stepId, wizardStep);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String wizardStatus = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardStepUpdated.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getCloudActivationStatus() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getCloudActivationStatus();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStatusResponse wizardStatus = (WizardStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardCloudActivationStatusRetunred.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void postCloudActivation(final WizardActivationPostRequest activationRequest) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.postCloudActivation(activationRequest);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStatusResponse wizardStatus = (WizardStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardActivatePosted.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getCloudAuthentication() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getCloudAuthentication();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStatusResponse wizardStatus = (WizardStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardAuthenticationReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void postCloudAuthentication(final String whatever) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.postCloudAuthentication(whatever);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStatusResponse wizardStatus = (WizardStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardAuthenticationPosted.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getCloudConnection() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getCloudConnection();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStatusResponse wizardStatus = (WizardStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardConnectionStatusReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void postCloudConnection(final String whatever) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.postCloudConnection(whatever);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStatusResponse wizardStatus = (WizardStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardConnectionPinged.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getOptimizationConfigurationFile() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getOptimizationConfigurationFile();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardFileList wizardStatus = (WizardFileList) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardConfigFilesStatusReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void postOptimizationConfigurationFile(final WizardPostConfigFilesRequest configFiles) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.postOptimizationConfigurationFile(configFiles);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStatusResponse wizardStatus = (WizardStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardConfigFilesDownloadStarted.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getOptimizationProgram() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getOptimizationProgram();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardFile wizardStatus = (WizardFile) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardBogFileStatusReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void postOptimizationProgram(final WizardPostConfigFileInfo configFile) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.postOptimizationProgram(configFile);
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardFile wizardStatus = (WizardFile) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardBogFileDownloadStarted.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getOptimizationStatus() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getOptimizationStatus();
                return results;
            }

            @Override
            public void done() {
                try {

                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStatusResponse wizardStatus = (WizardStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.WizardOptimizationStatusReturned.getName(), null, wizardStatus);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

}
