
package Model.RestClient;

import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.PGDAOIClient;
import Model.RestClient.Clients.RestClientCommon;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PGDAOIModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;
    private PGDAOIClient pgClient;

    public PGDAOIModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;
        this.pgClient = new PGDAOIClient();
        this.pcs = pcs;
    }

    //TODO: Reset client to point to correct db
    public void resetClient(String serviceURL, String adminURL, String accessToken, RestClientCommon api) {
        this.pgClient = new PGDAOIClient();
    }

    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void runQuery( final String queryString) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = pgClient.runQuery(queryString);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.PGQueryResulsReturned.getName(), null, msg);
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
