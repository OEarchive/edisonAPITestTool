package Model.RestClient;

import Model.DataModels.Alarms.AlarmListRequest;
import Model.DataModels.Alarms.AlarmsHistoryQueryParams;
import Model.DataModels.Alarms.AssociateAlarmReq;
import Model.DataModels.Alarms.CreateAlarmRequest;
import Model.DataModels.Alarms.CreateAlarmResponse;
import Model.DataModels.Alarms.TriggerAlarmResponse;
import Model.DataModels.Alarms.TriggerOrClearAlarmRequest;
import Model.DataModels.Alarms.UpdateAlarmRequest;
import Model.DataModels.Alarms.SiteAlarm;
import Model.DataModels.Alarms.SiteAlarms;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.AlarmsClient;
import Model.RestClient.Clients.RestClientCommon;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlarmsModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;

    private AlarmsClient alarmClient;

    public AlarmsModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;

        this.alarmClient = new AlarmsClient(model.getRestClient());
        this.pcs = pcs;
    }

    public void resetClient(String serviceURL, String accessToken, RestClientCommon api) {
        this.alarmClient = new AlarmsClient(api);
        this.alarmClient.setServiceURLAndToken(serviceURL, accessToken);
    }

    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /*
    public void getAlarmList(final String sid, final AlarmListRequest alarmList) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.getAlarmList(sid, alarmList);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        SiteAlarms msg = (SiteAlarms) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmListReturned.getName(), null, msg);
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

    public void postNewAlarm(final String sid, final CreateAlarmRequest req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.postNewAlarm(sid, req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        CreateAlarmResponse msg = (CreateAlarmResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmCreated.getName(), null, msg);
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

    public void associateAlarm(final String sid, final AssociateAlarmReq req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.associateAlarm(sid, req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmAssociated.getName(), null, msg);
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

    public void deleteAlarm(final String sid, final String name) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.deleteAlarm(sid, name);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmDeleted.getName(), null, msg);
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

    public void getAlarmDetails(final String sid, final String name, final AlarmListRequest req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.getAlarmDetails(sid, name, req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        SiteAlarm msg = (SiteAlarm) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmDetailsReturned.getName(), null, msg);
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

    public void postTriggerOrClearAlarm(final String sid, final String alarmSid, final String name, final TriggerOrClearAlarmRequest req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.postTriggerOrClearAlarm(sid, alarmSid, name, req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        TriggerAlarmResponse msg = (TriggerAlarmResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmTiggeredOrCleared.getName(), null, msg);
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

    public void updateAlarmMetadata(final String sid, final String name, final UpdateAlarmRequest req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.updateAlarmMetadata(sid, name, req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmUpdated.getName(), null, msg);
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

    public void getAlarmHistory(final String sid, final AlarmsHistoryQueryParams queryParams ) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.getAlarmHistory(sid, queryParams);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmHistoryReturned.getName(), null, msg);
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
    
    public void getSpecificAlarmHistory(final String sid, final String name) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.getSpecificAlarmHistory(sid, name);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmSpecificHistoryReturned.getName(), null, msg);
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


    public void getAlarmNotes(final String sid, final String name) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.getAlarmNotes(sid, name);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmNotesReturned.getName(), null, msg);
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

    public void postAlarmNote(final String sid, final String name, final String note) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.postAlarmNote(sid, name, note);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmNoteCreated.getName(), null, msg);
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

    public void deleteAlarmNote(final String sid, final String name, final String note_id) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = alarmClient.deleteAlarmNote(sid, name, note_id);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.AlarmNoteDeleted.getName(), null, msg);
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

}
