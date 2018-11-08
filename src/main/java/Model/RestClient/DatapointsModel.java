package Model.RestClient;

import Model.DataModels.Datapoints.DatapointGenericResponse;
import Model.DataModels.Datapoints.DatapointHistoriesQueryParams;
import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.Datapoints.PostDatapointRequest;
import Model.DataModels.ReportVerification.ReportHistoryQueryParams;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.DatapointsClient;
import Model.RestClient.Clients.RestClientCommon;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatapointsModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;

    private DatapointsClient client;

    public DatapointsModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;

        this.client = new DatapointsClient(model.getRestClient());
        this.pcs = pcs;
    }

    public void resetClient(String serviceURL, String accessToken, RestClientCommon api) {
        this.client = new DatapointsClient(api);
        this.client.setServiceURLAndToken(serviceURL, accessToken);
    }

    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void getDatapoints(final String sid, final String listOfPoints) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getDatapoints(sid, listOfPoints);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        List<DatapointsAndMetadataResponse> getDataPointsResponse = (List<DatapointsAndMetadataResponse>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointsListReturned.getName(), null, getDataPointsResponse);
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

    public void getDatapointsUnion(final Map<String, List<String>> sidsAndPoints) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {

                List<DatapointsAndMetadataResponse> list = new ArrayList<>();
                for (String sid : sidsAndPoints.keySet()) {
                    List<String> points = sidsAndPoints.get(sid);
                    String pointsList = String.join(",", points);
                    OEResponse results = client.getDatapoints(sid, pointsList);
                    List<DatapointsAndMetadataResponse> getDataPointsResponse = (List<DatapointsAndMetadataResponse>) results.responseObject;
                    list.addAll(getDataPointsResponse);
                }

                OEResponse main = new OEResponse();
                main.responseCode = 200;
                main.responseObject = list;

                return main;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        List<DatapointsAndMetadataResponse> getDataPointsResponse = (List<DatapointsAndMetadataResponse>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointsListReturned.getName(), null, getDataPointsResponse);
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
    
    public void getDatapointsUnionForReports(final Map<String, List<String>> sidsAndPoints) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {

                List<DatapointsAndMetadataResponse> list = new ArrayList<>();
                for (String sid : sidsAndPoints.keySet()) {
                    List<String> points = sidsAndPoints.get(sid);
                    String pointsList = String.join(",", points);
                    OEResponse results = client.getDatapoints(sid, pointsList);
                    List<DatapointsAndMetadataResponse> getDataPointsResponse = (List<DatapointsAndMetadataResponse>) results.responseObject;
                    list.addAll(getDataPointsResponse);
                }

                OEResponse main = new OEResponse();
                main.responseCode = 200;
                main.responseObject = list;

                return main;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        List<DatapointsAndMetadataResponse> getDataPointsResponse = (List<DatapointsAndMetadataResponse>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointUnionForReportsReturned.getName(), null, getDataPointsResponse);
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


    public void postDatapoints(final PostDatapointRequest req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.postDatapoint(req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        DatapointsAndMetadataResponse getDataPointsResponse = (DatapointsAndMetadataResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointsPosted.getName(), null, getDataPointsResponse);
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

    public void getDatapointHistories(final DatapointHistoriesQueryParams params) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getDatapointHistories(params);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        List<DatapointHistoriesResponse> datapointHistoriesResponse = (List<DatapointHistoriesResponse>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointHistoriesResponseReturned.getName(), null, datapointHistoriesResponse);
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

    public void getComplexDatapointHistories(final List<DatapointHistoriesQueryParams> listOfQueryParams) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {

                List<DatapointHistoriesResponse> history = new ArrayList<>();
                for (DatapointHistoriesQueryParams params : listOfQueryParams) {

                    OEResponse queryResult = client.getDatapointHistories(params);
                    if (queryResult.responseCode == 200) {
                        List<DatapointHistoriesResponse> datapointHistoriesResponse = (List<DatapointHistoriesResponse>) queryResult.responseObject;
                        history.addAll(datapointHistoriesResponse);
                    } else {
                        Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                        logger.error(this.getClass().getName(), "History Query failed...");
                    }
                }
                OEResponse results = new OEResponse();
                results.responseCode = 200;
                results.responseObject = history;
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        List<DatapointHistoriesResponse> datapointHistoriesResponse = (List<DatapointHistoriesResponse>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointHistoriesResponseReturned.getName(), null, datapointHistoriesResponse);
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

    //ReportHistoryQueryParams reportHistoryParams
    public void getHistoryForReportVerification(final ReportHistoryQueryParams reportHistoryParams) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {

                List<DatapointHistoriesResponse> history = new ArrayList<>();
                for (List<DatapointHistoriesQueryParams> listOfQueryParams : reportHistoryParams.getQueries()) {
                    for (DatapointHistoriesQueryParams params : listOfQueryParams) {
                        OEResponse queryResult = client.getDatapointHistories(params);
                        if (queryResult.responseCode == 200) {
                            List<DatapointHistoriesResponse> datapointHistoriesResponse = (List<DatapointHistoriesResponse>) queryResult.responseObject;
                            history.addAll(datapointHistoriesResponse);
                        } else {
                            Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                            logger.error(this.getClass().getName(), "History Query failed...");
                        }
                    }
                }
                OEResponse results = new OEResponse();
                results.responseCode = 200;
                results.responseObject = history;
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        List<DatapointHistoriesResponse> datapointHistoriesResponse = (List<DatapointHistoriesResponse>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.ReportVerificationHistoryReturned.getName(), null, datapointHistoriesResponse);
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
    public void getHistory(final String sid, final HistoryQueryParams params) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getHistory(sid, params);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        GetDatapointHistoryResponse datapointsHistoryResponse = (GetDatapointHistoryResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointsHistoryReturned.getName(), null, datapointsHistoryResponse);
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
 /*
    public void putHistory(final String sid, final String token, final PushDatapointsRequest req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.putHistory(sid, token, req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        PushDatapointsResponse putHistoryResponse = (PushDatapointsResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointsHistoryPushed.getName(), null, putHistoryResponse);
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
 /*
    public void postQuery(final String sid) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.postQuery(sid);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        DatapointGenericResponse genericResponse = (DatapointGenericResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointsQueryPosted.getName(), null, genericResponse);
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
 /*
    public void deleteQuery(final String sid, final String name) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.deleteQuery(sid, name);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        DatapointGenericResponse genericResponse = (DatapointGenericResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointsQueryDeleted.getName(), null, genericResponse);
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

 /*
    public void getDatapointQueryResults(final String sid, final String name) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getDatapointQueryResults(sid, name);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        DatapointGenericResponse genericResponse = (DatapointGenericResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointsNamedQueryResultsReturned.getName(), null, genericResponse);
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
    public void deleteDatapointAssociation(final String sid, final String name) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.deleteDatapointAssociation(sid, name);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        DatapointGenericResponse genericResponse = (DatapointGenericResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointAssociationDeleted.getName(), null, genericResponse);
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
    public void getDatapointMetadata(final String sid, final String name) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getDatapointMetadata(sid, name);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        DatapointsAndMetadataResponse genericResponse = (DatapointsAndMetadataResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointMetadataReturned.getName(), null, genericResponse);
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
 /*

    public void getDatapointMetadataMultipleSids(final Map<String, List<String>> sidsAndPoints) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {

                Map<String, List<DatapointMetadata>> map = new HashMap<>();
                for (String sid : sidsAndPoints.keySet()) {
                    List<DatapointMetadata> metadataList = new ArrayList<>();
                    for (String pointName : sidsAndPoints.get(sid)) {
                        OEResponse resp = client.getDatapointMetadata(sid, pointName);
                        if (resp.responseCode == 200) {
                            DatapointsAndMetadataResponse dpmr = (DatapointsAndMetadataResponse) resp.responseObject;
                            //Note: There should be at most one point in this list I guess
                            for (DatapointMetadata md : dpmr.getPoints()) {
                                metadataList.add(md);
                            }
                        }
                    }
                    map.put(sid, metadataList);
                }

                OEResponse results = new OEResponse();
                results.responseCode = 200;
                results.responseObject = map;
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        Map<String, List<DatapointMetadata>> map = (Map<String, List<DatapointMetadata>>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointMetadataMultipleSidsReturned.getName(), null, map);
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
 /*
    public void updateDatapointMetadata(final String sid, final String name, final UpdateMetaDataRequest req) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.updateDatapointMetadata(sid, name, req);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        DatapointGenericResponse genericResponse = (DatapointGenericResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointMetadataUpdated.getName(), null, genericResponse);
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
 /*

    public void putHistoryFromXLS(
            final String sid,
            final List<String> timeStamps,
            final Map<String, List<Object>> pointsAndValues,
            final Map<String, DatapointMetadata> nameToMetadataMap
    ) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {

                int rowIndex = 0;

                for (rowIndex = 0; rowIndex < timeStamps.size(); rowIndex++) {
                    String ts = timeStamps.get(rowIndex);

                    List<HistoryPushPoint> points = new ArrayList<>();
                    for (String pointName : pointsAndValues.keySet()) {

                        HistoryPushPoint pointToPush = new HistoryPushPoint();
                        pointToPush.setName(pointName);
                        pointToPush.setPath("funStuff/" + pointName);
                        pointToPush.setPointType( nameToMetadataMap.get(pointName).getPointType() );

                        List<String> singleTsAsList = new ArrayList<>();
                        singleTsAsList.add(ts);
                        pointToPush.setTimestamps(singleTsAsList);

                        List<Object> singleValueAsList = new ArrayList<>();

                        List<Object> valuesForThisPoint = pointsAndValues.get(pointName);
                        Object singleValue = valuesForThisPoint.get(rowIndex);
                        singleValueAsList.add(singleValue);
                        pointToPush.setValues(singleValueAsList);

                        points.add(pointToPush);

                    }

                    PushDatapointsRequest req = new PushDatapointsRequest();
                    req.setLastSuccessTimestamp(ts);
                    req.setTimestamp(ts);
                    req.setSendingSid(sid);
                    req.setPoints(points);
                    
                    OEResponse results = client.putHistory(sid, req);
                    if( results.responseCode != 200 ){
                        System.out.println("error pushing calc point value");
                    }
                    
                    pcs.firePropertyChange(PropertyChangeNames.DatapointsCalcRowPushed.getName(), null, 1);

                }
                
                DatapointGenericResponse msg = new DatapointGenericResponse();
                msg.setMessage("done");
                msg.setTimestamp( DateTime.now().toString());
                msg.setSuccess(true);


                OEResponse resp = new OEResponse();
                resp.responseCode = 200;
                resp.responseObject = msg;
                return resp;
               
            }
            
            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        DatapointGenericResponse msg = (DatapointGenericResponse) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.DatapointsCalcValuesPushed.getName(), null, msg);
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
