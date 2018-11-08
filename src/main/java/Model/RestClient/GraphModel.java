package Model.RestClient;

import Model.DataModels.Graph.EnumGraphNodeTypes;
import Model.DataModels.Graph.GetChildrenRequest;
import Model.DataModels.Graph.GetChildrenResponse;
import Model.DataModels.Graph.GetMetaDataRequest;
import Model.DataModels.Graph.RuleInfoResponse;
import Model.DataModels.Graph.RuleInRulesResponse;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.GraphClient;
import Model.RestClient.Clients.RestClientCommon;
import View.TreeInfo.CustomTreeNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Map;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;

    //private final RestClientCommon api;
    private GraphClient graphClient;

    public GraphModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;

        this.graphClient = new GraphClient(model.getRestClient());
        this.pcs = pcs;
    }

    public void resetClient(String serviceURL, String adminURL, String accessToken, RestClientCommon api) {
        this.graphClient = new GraphClient(api);
        this.graphClient.setServiceURLAndToken(serviceURL, adminURL, accessToken);
    }

    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void getGraphElements() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = graphClient.getGraphElements();
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.GraphReturned.getName(), null, msg);
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

    public void deleteGraphElement(final String sid) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = graphClient.deleteGraphElement(sid);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.GraphElementDeleted.getName(), null, msg);
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

    public void getElementMetadata(final String sid, final GetMetaDataRequest mdr) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = graphClient.getGraphElementMetadata(sid, mdr);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        Map<String, String> msg = (Map) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.GraphElementMetadataReturned.getName(), null, msg);
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

    public void getSiteDetailsFromGraph(final String sid) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = graphClient.getSiteDetails(sid);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        Map<String, String> msg = (Map) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.GraphSiteDetails.getName(), null, msg);
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

    /*
    public void getSiteAttributesFromGraph(final String sid) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = graphClient.getSiteDetailsZZZ(sid);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        JsonElement root = new JsonParser().parse((String) resp.responseObject);
                        CustomTreeNode siteInfoTree = new CustomTreeNode(null, -1, root);

                        pcs.firePropertyChange(PropertyChangeNames.GraphSiteAttributesReturned.getName(), null, siteInfoTree);
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
    */

    public void getGraphElementChildren(final String sid ) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = graphClient.getGraphElementChildren(sid );
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        GetChildrenResponse children = (GetChildrenResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.GraphChildrenReturned.getName(), null, children);
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

    public void putGraphElement(final String sid, final EnumGraphNodeTypes nodeType, final String payload) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = graphClient.putGraphElement(sid, nodeType, payload);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        Map<String, String> msg = (Map) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.GraphElementUpserted.getName(), null, msg);
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

    public void getGraphElementSchema(final String sid, final EnumGraphNodeTypes nodeType) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = graphClient.getGraphElementSchema(sid, nodeType);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.GraphElementSchemaReturned.getName(), null, msg);
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

    //OEResponse getRulesInfo(String siteSid )
    public void getRulesInfo(final String siteSid) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = graphClient.getRulesInfo(siteSid);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        RuleInfoResponse msg = (RuleInfoResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.GraphRulesInfoReturned.getName(), null, msg);
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
