package Model.DataModels.TeslaModels;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TeslaDataPointUpsertRequest {

    @JsonProperty("list_of_points")
    private final List<TeslaDataPointUpsert> listOfPoints;

    private final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZoneUTC();

    public TeslaDataPointUpsertRequest(List<TeslaDataPointUpsert> listOfPoints) {
        this.listOfPoints = listOfPoints;
    }

    public TeslaDataPointUpsertRequest(List<DatapointHistoriesResponse> edisonHistory, Map< String, MappingTableRow> edisonNameToMappingTableRowMap) {
        listOfPoints = new ArrayList<>();

        for (DatapointHistoriesResponse history : edisonHistory) {

            int tsIndex = 0;
            for (String timeStampString : history.getTimestamps()) {

                DateTime timeStamp = null;
                try {
                    timeStamp = DateTime.parse(timeStampString, fmt);
                } catch (Exception ex) {
                    System.out.println("bad date!!!");
                }
                Object val = history.getValues().get(tsIndex);
                
                if( val instanceof Boolean){
                    Boolean tempBool = (Boolean) val;
                    val = (tempBool)? 1.0 : 0.0;
                }
                
                tsIndex++;

                MappingTableRow mtr = edisonNameToMappingTableRowMap.get(history.getName());

                TeslaDataPointUpsert dpUpsert = new TeslaDataPointUpsert(
                        mtr.getTeslaID(),
                        val,
                        timeStamp.toString(fmt));
                listOfPoints.add(dpUpsert);

            }
        }

    }

    public List<TeslaDataPointUpsert> getListOfPoints() {
        return this.listOfPoints;
    }
}
