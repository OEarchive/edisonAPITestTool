package Model.RestClient;

import Model.DataModels.TeslaModels.EnumTeslaBaseURLs;
import Model.DataModels.TeslaModels.TeslaStationInfo;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.TeslaRestClientCommon;
import Model.RestClient.Clients.TeslaStationClient;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.List;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeslaAPIModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;
    private TeslaStationClient teslaStationClient;

    public TeslaAPIModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;

        this.teslaStationClient = new TeslaStationClient(EnumTeslaBaseURLs.Ninja, model.getTeslaRestClient());
        this.pcs = pcs;
    }

    public void resetTeslaClient(EnumTeslaBaseURLs baseURL ) {
        //this.teslaStationClient = new TeslaStationClient(EnumTeslaBaseURLs.Ninja, teslaRestClientCommon);
        this.teslaStationClient.setTeslaBaseURL(baseURL);
    }

    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void getTeslaStations() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = teslaStationClient.getStations();
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        List<TeslaStationInfo> stations = (List<TeslaStationInfo>) resp.responseObject;

                        pcs.firePropertyChange(PropertyChangeNames.TeslaStationsListReturned.getName(), null, stations);
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

    public void getTeslaStationInfo(final String stationID) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = teslaStationClient.getStationInfo(stationID);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        TeslaStationInfo stationInfo = (TeslaStationInfo) resp.responseObject;

                        pcs.firePropertyChange(PropertyChangeNames.TeslaStationInfoRetrieved.getName(), null, stationInfo);
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
