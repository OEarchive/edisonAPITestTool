package Model.RestClient;

import Model.DataModels.Datapoints.DatapointHistoriesQueryParams;
import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.EnumAggregationType;
import Model.DataModels.Datapoints.EnumResolutions;
import Model.DataModels.Stations.StationStatusResponse;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaGenEquipment;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostCustomer;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostEquipResponse;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostSite;
import Model.DataModels.TeslaModels.CreateTeslaSiteModel.TeslaPostStation;
import Model.DataModels.TeslaModels.EnumTeslaBaseURLs;
import Model.DataModels.TeslaModels.MappingTableRow;
import Model.DataModels.TeslaModels.TeslaDPServiceDatapoint;
import Model.DataModels.TeslaModels.TeslaDataPointUpsertRequest;
import Model.DataModels.TeslaModels.TeslaHistoryRequest;
import Model.DataModels.TeslaModels.TeslaHistoryResultPoint;
import Model.DataModels.TeslaModels.TeslaHistoryResults;
import Model.DataModels.TeslaModels.TeslaStationInfo;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.DatapointsClient;
import Model.RestClient.Clients.TeslaStationClient;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.SwingWorker;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeslaAPIModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;
    private TeslaStationClient teslaStationClient;
    private DatapointsClient edisonClient;

    public TeslaAPIModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;

        this.teslaStationClient = new TeslaStationClient(EnumTeslaBaseURLs.Ninja, model.getTeslaRestClient());
        this.pcs = pcs;
    }

    public void resetTeslaClient(EnumTeslaBaseURLs baseURL) {
        //this.teslaStationClient = new TeslaStationClient(EnumTeslaBaseURLs.Ninja, teslaRestClientCommon);
        this.teslaStationClient.setTeslaBaseURL(baseURL);
    }

    public void setEdisonClient(DatapointsClient client) {
        edisonClient = client;
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

    public void getTeslaStationDatapoints(final String stationID) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = teslaStationClient.getTeslaStationDatapoints(stationID);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        List<TeslaDPServiceDatapoint> listOfStationDatapoints = (List<TeslaDPServiceDatapoint>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.TeslaStationDatapointsReturned.getName(), null, listOfStationDatapoints);
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

    public void getTeslaHistory(final TeslaHistoryRequest historyRequest) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = teslaStationClient.getTeslaHistory(historyRequest);

                if (results.responseCode == 200) {

                    TeslaHistoryResults historyResults = new TeslaHistoryResults((List<TeslaHistoryResultPoint>) results.responseObject);

                    results = new OEResponse();
                    results.responseCode = 200;
                    results.responseObject = historyResults;

                    return results;

                }

                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        TeslaHistoryResults historyResults = (TeslaHistoryResults) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.TeslaHistoryReturned.getName(), null, historyResults);
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

    public void pullFromEdisonPushToTesla(
            final String querySid,
            final DateTime pushStartTime,
            final DateTime pushEndTime,
            final List<MappingTableRow> mappedRows,
            final int maxHoursPerPush,
            final int maxPointsPerPush) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {

                //Push data on hour boudaries
                //Starting from startTime with minutes, seconds, millis set to zero.
                DateTime intervalStart = pushStartTime.minusMillis(pushStartTime.getMillisOfSecond());
                intervalStart = intervalStart.minusSeconds(intervalStart.getSecondOfMinute());
                intervalStart = intervalStart.minusMinutes(intervalStart.getMinuteOfHour());

                //endOfPeriod is the number of whole hours between the startDate and the endDate.
                Hours hours = Hours.hoursBetween(intervalStart, pushEndTime);
                DateTime endOfPeriod = intervalStart.plusHours(hours.getHours());

                //if the endDate was not on an hour boundary, and an hour to cover the remainder.
                //e.g., if endDate was ...03:45:37 we want to push data to ...04:00:00
                if (pushEndTime.isAfter(endOfPeriod)) {
                    endOfPeriod = endOfPeriod.plusHours(1);
                }

                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                while (intervalStart.isBefore(endOfPeriod)) {

                    DateTime intervalEnd = intervalStart.plusHours(maxHoursPerPush);

                    int startPushIndex = 0;

                    while (startPushIndex < mappedRows.size()) {

                        int endIndex = Math.min(startPushIndex + maxPointsPerPush, mappedRows.size());

                        List<MappingTableRow> pointsToPush = mappedRows.subList(startPushIndex, endIndex);
                        pullFromEdisonPushToTeslaInterval(querySid, intervalStart, intervalEnd, pointsToPush);
                        pcs.firePropertyChange(PropertyChangeNames.TeslaBatchPushed.getName(), null, 1);
                        startPushIndex += maxPointsPerPush;
                    }

                    //increment loop index
                    intervalStart = intervalEnd;
                }

                StationStatusResponse stationResponse = new StationStatusResponse();
                stationResponse.setSuccess(true);
                stationResponse.setResult(201);

                OEResponse periodHistoryPushStatus = new OEResponse();
                periodHistoryPushStatus.responseCode = 201;
                periodHistoryPushStatus.responseObject = stationResponse;
                return periodHistoryPushStatus;

            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 201) {
                        StationStatusResponse msg = (StationStatusResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.TeslaPushComplete.getName(), null, msg);
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

    private void pullFromEdisonPushToTeslaInterval(String querySid, DateTime pushStartTime, DateTime pushEndTime, List<MappingTableRow> mappedRows) {

        List<String> edisonPointNames = new ArrayList<>();
        Map< String, MappingTableRow> edisonNameToMappingTableRowMap = new HashMap<>();

        for (MappingTableRow mtr : mappedRows) {
            edisonPointNames.add(mtr.getEdsionShortName());
            edisonNameToMappingTableRowMap.put(mtr.getEdsionShortName(), mtr);
        }

        DatapointHistoriesQueryParams params = new DatapointHistoriesQueryParams(
                querySid,
                pushStartTime,
                pushEndTime,
                EnumResolutions.MINUTE5,
                true,
                edisonPointNames,
                EnumAggregationType.NORMAL);

        try {
            OEResponse ttt = edisonClient.getDatapointHistories(params);
            List<DatapointHistoriesResponse> dpr = (List<DatapointHistoriesResponse>) ttt.responseObject;
            if (dpr.size() > 0) {
                TeslaDataPointUpsertRequest tdpu = new TeslaDataPointUpsertRequest(dpr, edisonNameToMappingTableRowMap);
                teslaStationClient.putHistory(tdpu);
            }

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(TeslaAPIModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //site creation
    /*
    TeslaCustomerCreated("TeslaCustomerCreated"),
    TeslaSiteCreated("TeslaSiteCreated"),
    TeslaStationCreated("TeslaStationCreated"),
    TeslaEquipmentCreated("TeslaEquipmentCreated");
     */
    public void postCustomer(final TeslaPostCustomer postCustomer) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = teslaStationClient.postCustomer(postCustomer);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    if (resp.responseCode == 201) {
                        Map<String, Object> map = (Map<String, Object>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.TeslaCustomerCreated.getName(), null, map);
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

    public void postSite(final String customerId, final TeslaPostSite postSite) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = teslaStationClient.postSite(customerId, postSite);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    if (resp.responseCode == 201) {
                        Map<String, Object> map = (Map<String, Object>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.TeslaSiteCreated.getName(), null, map);
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

    public void postStation(final String siteId, final TeslaPostStation postStation) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = teslaStationClient.postStation(siteId, postStation);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    if (resp.responseCode == 201) {
                        Map<String, Object> map = (Map<String, Object>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.TeslaStationCreated.getName(), null, map);
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

    public void postEquipmentList(final String stationId, final List<TeslaGenEquipment> equipList) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {

                List<TeslaPostEquipResponse> equipResponses = new ArrayList<>();
                for (TeslaGenEquipment equip : equipList) {
                    OEResponse resp = teslaStationClient.postEquipmentList(stationId, equip);
                    if (resp.responseCode != 201) {
                        return resp;
                    }

                    TeslaPostEquipResponse equipResp = (TeslaPostEquipResponse) resp.responseObject;
                    equipResponses.add(equipResp);

                }

                OEResponse resp = new OEResponse();
                resp.responseCode = 201;
                resp.responseObject = equipResponses;
                return resp;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();
                    if (resp.responseCode == 201) {
                        List<TeslaPostEquipResponse> equipResponses = (List<TeslaPostEquipResponse>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.TeslaEquipmentCreated.getName(), null, equipResponses);
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
