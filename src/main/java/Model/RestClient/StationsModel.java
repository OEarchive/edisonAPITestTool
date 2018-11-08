package Model.RestClient;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.EnumResolutions;
import Model.DataModels.Datapoints.simulator.DGArgs;
import Model.DataModels.Datapoints.simulator.Patterns.LinearPattern;
import Model.DataModels.Datapoints.simulator.Patterns.SawPattern;
import Model.DataModels.Datapoints.simulator.Patterns.SinePattern;
import Model.DataModels.Datapoints.simulator.Patterns.SquarePattern;
import Model.DataModels.Graph.EnumGraphNodeTypes;
import Model.DataModels.Stations.HistoryPushObject;
import Model.DataModels.Stations.HistoryPushPoint;
import Model.DataModels.Stations.NetworkAvailabilityResponse;
import Model.DataModels.Stations.StationActivateRequest;
import Model.DataModels.Stations.StationActivateResponse;
import Model.DataModels.Stations.StationAlarmPushObject;
import Model.DataModels.Stations.StationAuditHistory;
import Model.DataModels.Stations.StationLogHistory;
import Model.DataModels.Stations.StationStatusResponse;
import Model.DataModels.Stations.WizardStationStatus;
import Model.DataModels.Stations.StationValidateQueryParams;
import Model.DataModels.Stations.StationsHeartbeat;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.RestClientCommon;
import Model.RestClient.Clients.StationsClient;
import View.Sites.EditSite.A_History.DataGenerator.DGTableRow;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.SwingWorker;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StationsModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;
    private StationsClient stationsClient;

    //Patterns for HistoryPushing
    private SinePattern sineFunction;
    private LinearPattern linearFunction;
    private SawPattern sawFunction;
    private SquarePattern stepFunction;

    public StationsModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;

        this.stationsClient = new StationsClient(model.getRestClient());
        this.pcs = pcs;
    }

    public void resetClient(String serviceURL, String accessToken, RestClientCommon api) {
        this.stationsClient = new StationsClient(api);
        this.stationsClient.setServiceURLAndToken(serviceURL, accessToken);
    }

    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void getActivationAvaiablity() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.getActivate();
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        NetworkAvailabilityResponse nwAvailMsg = (NetworkAvailabilityResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationCheckActivateAvaiability.getName(), null, nwAvailMsg);
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

    public void postActivate(final StationActivateRequest req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.postActivate(req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        StationActivateResponse msg = (StationActivateResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationActivatated.getName(), null, msg);
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

    public void getStationBogFile(final String stationId) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.getStationsBog(stationId);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationBogfileDownloaded.getName(), null, msg);
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

    public void getConfigurationProfile(final String stationId) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.getDownloadProfile(stationId);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationConfigurationFileDownloaded.getName(), null, msg);
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

    public void getPointsConfiguration(final String stationId) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.getDownloadPoints(stationId);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationPointsConfigurationDownloaded.getName(), null, msg);
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

    public void getDefaultParameters(final String stationId) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.getDownloadDefaultParams(stationId);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationDefaultParametersDownloaded.getName(), null, msg);
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

    public void getPortalParameters(final String stationId) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.getDownloadPortalsParams(stationId);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;;
                        pcs.firePropertyChange(PropertyChangeNames.StationPortalParametersDownloaded.getName(), null, msg);
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

    public void validateConfiguration(final StationValidateQueryParams params) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.getValidate(params);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationValidateConfiguration.getName(), null, msg);
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

    public void getConfigurationStatus(final String stationId) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.getConfigurationStatus(stationId);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        WizardStationStatus msg = (WizardStationStatus) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationConfigurationStatusReturned.getName(), null, msg);
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

    public void pushConfigurationStatus(final String stationId, final WizardStationStatus status) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.postConfigurationStatus(stationId, status);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationConfigurationStatusSet.getName(), null, msg);
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

    public void pushAuditHistory(final StationAuditHistory auditHistory) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.postStationsAudit(auditHistory);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationAuditHistorySubmitted.getName(), null, msg);
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

    public void pushStationLog(final StationLogHistory stationLogHistory) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.postStationsLog(stationLogHistory);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationLogHistorySubmitted.getName(), null, msg);
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

    public void postDatapointHistory(final HistoryPushObject history) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.postDataPointHistory(history);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        StationStatusResponse msg = (StationStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationDatapointHistoryPushed.getName(), null, msg);
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
    
    public void repostDatapointHistory(final String timeStamp, final String stationId, final List<DatapointHistoriesResponse> oldhistory) {

        
        pcs.firePropertyChange(PropertyChangeNames.StationRepushStarted.getName(), null, "starting" );

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                

                HistoryPushObject hpp = new HistoryPushObject( timeStamp, stationId, oldhistory  );
                
                
                OEResponse results = stationsClient.postDataPointHistory(hpp);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        StationStatusResponse msg = (StationStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationRepushComplete.getName(), null, msg);
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

    public void pushAlarmChanges(final StationAlarmPushObject alarms) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.postAlarmChanges(alarms);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationAlarmChangesPushed.getName(), null, msg);
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

    public void pushHeartbeat(final String stationId, final String token, final StationsHeartbeat heartBeat) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = stationsClient.postHeartBeat(stationId, token, heartBeat);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        StationStatusResponse msg = (StationStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationHeartbeatPushed.getName(), null, msg);
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

    //public void pushHistoryForPeriod(final EnumGraphNodeTypes scope, final DGArgs args, final Map<String, DGTableRow> tableRowsMap, final int maxPointsPerPush, final int maxRowsPerPush) {
    public void pushHistoryForPeriod(final EnumGraphNodeTypes scope, final DGArgs args, final Map<String, DGTableRow> tableRowsMap) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {

                final int maxPointsPerPush = 20;

                //Push data on hour boudaries
                //Starting from startTime with minutes, seconds, millis set to zero.
                DateTime intervalStart = args.getStartDate().minusMillis(args.getStartDate().getMillisOfSecond());
                intervalStart = intervalStart.minusSeconds(intervalStart.getSecondOfMinute());
                intervalStart = intervalStart.minusMinutes(intervalStart.getMinuteOfHour());

                //endOfPeriod is the number of whole hours between the startDate and the endDate.
                DateTime stopTime = args.getEndDate();
                Hours hours = Hours.hoursBetween(intervalStart, stopTime);
                DateTime endOfPeriod = intervalStart.plusHours(hours.getHours());

                //if the endDate was not on an hour boundary, and an hour to cover the remainder.
                //e.g., if endDate was ...03:45:37 we want to push data to ...04:00:00
                if (args.getEndDate().isAfter(endOfPeriod)) {
                    endOfPeriod = endOfPeriod.plusHours(1);
                }

                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                sineFunction = new SinePattern(intervalStart);
                linearFunction = new LinearPattern(intervalStart);
                sawFunction = new SawPattern(intervalStart);
                stepFunction = new SquarePattern(intervalStart);

                while (intervalStart.isBefore(endOfPeriod)) {

                    DateTime intervalEnd = intervalStart.plusHours(1);
                    HistoryPushObject req = new HistoryPushObject();
                    req.setSendingStationName(args.getSendingSid());

                    String ts = intervalStart.toString(fmt);

                    req.setTimestamp(ts);
                    req.setLastSuccessTimestamp(ts);
                    req.setStationName(args.getStationName());
                    req.setStationId(args.getStationId());

                    List<HistoryPushPoint> pointsListToPush = new ArrayList<>();
                    int pointCount = 0;

                    for (String pointName : tableRowsMap.keySet()) {
                        HistoryPushPoint hpp = getHistoryPushPoint(scope, args.getRes(), intervalStart, intervalEnd, tableRowsMap.get(pointName));
                        pointsListToPush.add(hpp);
                        pointCount++;
                        if (maxPointsPerPush != -1 && pointCount > maxPointsPerPush) {
                            req.setPoints(pointsListToPush);

                            try {

                                OEResponse results = stationsClient.postDataPointHistory(req, false);

                            } catch (Exception ex) {
                                System.out.println("error pushing data");
                                System.out.println(ex.getStackTrace().toString());
                            }

                            pointsListToPush.clear();
                            pointCount = 0;
                        }
                    }

                    if (pointsListToPush.size() > 0) {
                        req.setPoints(pointsListToPush);
                        OEResponse results = stationsClient.postDataPointHistory(req, false);
                        if (results.responseCode != 200) {
                            return results;
                        }
                        pointsListToPush.clear();
                    }

                    //increment loop index
                    pcs.firePropertyChange(PropertyChangeNames.StationDatapointHistoryOneHourPushed.getName(), null, 1);
                    intervalStart = intervalEnd;
                }

                StationStatusResponse stationResponse = new StationStatusResponse();
                stationResponse.setSuccess(true);
                stationResponse.setResult(200);

                OEResponse periodHistoryPushStatus = new OEResponse();
                periodHistoryPushStatus.responseCode = 200;
                periodHistoryPushStatus.responseObject = stationResponse;
                return periodHistoryPushStatus;

            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        StationStatusResponse msg = (StationStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.StationHistoryForPeriodPushed.getName(), null, msg);
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

    private HistoryPushPoint getHistoryPushPoint(EnumGraphNodeTypes scope, EnumResolutions res, DateTime intervalStart, DateTime intervalEnd, DGTableRow tableRow) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        HistoryPushPoint hpp = new HistoryPushPoint();

        String pointName = tableRow.getPointName();
        hpp.setName(pointName);

        //String path = "foo/bar/" + pointName;
        //hpp.setPath(path);

        String pointType = tableRow.getPointType().getEdisonName();
        hpp.setPointType(pointType);

        List<String> timestamps = getTimeStamps(res, intervalStart, intervalEnd);
        hpp.setTimestamps(timestamps);

        List<Object> values = new ArrayList<>();
        for (int i = 0; i < timestamps.size(); i++) {

            Object patternValue;

            DateTime ts = DateTime.parse(timestamps.get(i), fmt);

            switch (tableRow.getPattern()) {
                case sine:

                    patternValue = sineFunction.getValue(
                            ts,
                            (Double) tableRow.getOffset(),
                            (Double) tableRow.getMinValue(),
                            (Double) tableRow.getMaxValue(),
                            tableRow.getPeriod());
                    break;

                case linear:

                    patternValue = linearFunction.getValue(
                            ts,
                            (Double) tableRow.getMinValue(),
                            (Double) tableRow.getMaxValue(),
                            tableRow.getPeriod());

                    if ("boolean".equals(tableRow.getUOM())) {
                        if (1.0 == (Double)patternValue) {
                            patternValue = true;
                        } else {
                            patternValue = false;
                        }
                    }

                    break;

                case saw:

                    patternValue = sawFunction.getValue(
                            ts,
                            (Double) tableRow.getMinValue(),
                            (Double) tableRow.getMaxValue(),
                            tableRow.getPeriod());
                    break;
                case square:

                    patternValue = stepFunction.getValue(
                            ts,
                            (Double) tableRow.getMinValue(),
                            (Double) tableRow.getMaxValue(),
                            tableRow.getPeriod());

                    patternValue = ((Double) patternValue > 0.1) ? true : false;

                    break;

                default:
                    double min = 5;
                    double max = 100;
                    double val = 0;
                    //if ("boolean".equals((String) point.getMetadata().get("uom"))) {
                    if ("boolean".equals(tableRow.getUOM())) {
                        patternValue = true;
                    } else {
                        val = min + ((max - min) / timestamps.size()) * i;
                        patternValue = val;
                    }

            }

            values.add(patternValue);
        }

        hpp.setValues(values);
        return hpp;
    }

    private List<String> getTimeStamps(EnumResolutions res, DateTime start, DateTime end) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        List<String> timeStamps = new ArrayList<>();

        DateTime counter = start;
        while (counter.isBefore(end)) {
            String timestamp = counter.toString(fmt);
            timeStamps.add(timestamp);
            counter = counter.plusMinutes(res.getMinutes());
        }
        return timeStamps;
    }

}
