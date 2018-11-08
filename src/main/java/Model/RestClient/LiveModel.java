
package Model.RestClient;

import Model.DataModels.Live.Subscriptions.CreateSubscriptionResponse;
import Model.DataModels.Live.GetLiveData.GetLiveDataResponse;
import Model.DataModels.Live.PostLiveData.PostLiveDataRequest;
import Model.DataModels.Live.PostLiveData.PostLiveDataResponse;
import Model.DataModels.Live.Subscriptions.CreateSubscriptionRequest;
import Model.DataModels.Live.Subscriptions.SubscriptionReqWithID;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.LiveClient;
import Model.RestClient.Clients.RestClientCommon;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LiveModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;

    private LiveClient client;

    public LiveModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;

        this.client = new LiveClient(model.getRestClient());
        this.pcs = pcs;
    }

    public void resetClient(String serviceURL, String accessToken, RestClientCommon api) {
        this.client = new LiveClient(api);
        this.client.setServiceURLAndToken(serviceURL, accessToken);
    }

    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void getLiveData(final String subscription_id) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getLiveData(subscription_id);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        GetLiveDataResponse msg = (GetLiveDataResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.LiveDataReturned.getName(), null, msg);
                        
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
    
    public void postNewSubscription( final CreateSubscriptionRequest req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.postNewSubscription( req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        CreateSubscriptionResponse subResp = (CreateSubscriptionResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.SubscriptionCreated.getName(), null, subResp);
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

    public void postLiveData(final String token, final String stationId, final PostLiveDataRequest req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.postLiveData( token, stationId, req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        PostLiveDataResponse msg = (PostLiveDataResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.LiveValuesPushed.getName(), null, msg);
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