package Model.DataModels.Datapoints.simulator;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.EnumResolutions;
import Model.DataModels.Stations.HistoryPushObject;
import Model.DataModels.Stations.HistoryPushPoint;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class HistoryPusher implements PropertyChangeListener {

    private final OptiCxAPIController controller;
    private final EnumResolutions res;
    private final DateTime start;
    private final DateTime end;
    
    private final String sendingStationName;
    private final String timestamp;
    private final String lastSuccessTimestamp;
    private final String stationName;
    private final String stationId;
    
    private final List<DatapointMetadata> pointsList;
    
    private final HistoryPushObject historyPushObject;

    public HistoryPusher(
            OptiCxAPIController controller,
            EnumResolutions res, 
            DateTime start, 
            DateTime end, 
            
            String sendingStationName,
            String timestamp, 
            String lastSuccessTimestamp, 
            String stationName, 
            String stationId,
            
            List<DatapointMetadata> pointsList
    ) {

        this.controller = controller;
        this.res = res;
        this.start = start;
        this.end = end;
        
        this.sendingStationName = sendingStationName;
        this.timestamp = timestamp;
        this.lastSuccessTimestamp = lastSuccessTimestamp;
        this.stationName = stationName;
        this.stationId = stationId;
        this.pointsList = pointsList;
        
        this.historyPushObject = getHistoryPushObject();

    }

    private int getMinutes(EnumResolutions res) {
        switch (res) {
            case MINUTE:
                return 1;
            case MINUTE5:
                return 5;
            case HOUR:
                return 60;
            case DAY:
                return 60 * 24;
            case WEEK:
                return 60 * 24 * 7;
            default:
                return 0;
        }
    }

    private List<String> getTimeStamps(DateTime start, DateTime end) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        List<String> timeStamps = new ArrayList<>();

        DateTime counter = start;
        while (counter.isBefore(end)) {
            String timestamp = counter.toString(fmt);
            timeStamps.add(timestamp);
            counter = counter.plusMinutes(getMinutes(res));
        }
        return timeStamps;
    }

    private HistoryPushPoint getHistoryPointsDetail(DatapointMetadata point) {

        HistoryPushPoint hpp = new HistoryPushPoint();
        
        String pointName = (String)point.getMetadata().get("name");
        String path = "foo/bar/" + pointName;

        //hpp.setName( pointName );
        //hpp.setPath( path );
        
        String pointType = "boolean";

        if (((String) point.getMetadata().get("uom")).compareTo("boolean") != 0) {
            pointType = "numeric";
        }

        hpp.setPointType(pointType);

        List<String> timestamps = getTimeStamps(this.start, this.end);
        hpp.setTimestamps(timestamps);

        List<Object> values = new ArrayList<>();
        for (int i = 0; i < timestamps.size(); i++) {
            double min = 5;
            double max = 100;
            Object temp;
            double val = 0;
            if ("boolean".equals((String) point.getMetadata().get("uom"))) {
                temp = true;
            } else {
                val = min + ((max - min) / timestamps.size()) * i;
                temp = val;
            }
            values.add(temp);
        }
        hpp.setValues(values);
        return hpp;
    }

    private HistoryPushObject getHistoryPushObject() {

        HistoryPushObject req = new HistoryPushObject();
        req.setSendingStationName(sendingStationName);
        req.setTimestamp(timestamp);
        req.setLastSuccessTimestamp(lastSuccessTimestamp);
        req.setStationName(stationName);
        req.setStationId( stationId );

        List<HistoryPushPoint> pointsListToPush = new ArrayList<>();
        for (DatapointMetadata point : pointsList) {
            pointsListToPush.add( getHistoryPointsDetail(point));
        }

        req.setPoints(pointsListToPush);
        return req;
    }
    
    //TODO: Not needed
    public void pushHistory(){ 
        HistoryPushObject history = historyPushObject;
        controller.postDatapointHistory(history);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
