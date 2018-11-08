package Model.DataModels.Sites;

import Model.DataModels.Datapoints.EnumResolutions;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SiteTrendAndKPIRequest {

    private DateTime start;
    private DateTime end;
    private EnumResolutions resolution;

    public SiteTrendAndKPIRequest(
            DateTime start,
            DateTime end,
            EnumResolutions resolution
    ) {
        this.start = start;
        this.end = end;
        this.resolution = resolution;
    }

    public String getQueryParamsString() throws UnsupportedEncodingException {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        String startTimeString = start.toString(fmt) + 'Z';
        String endDateString = end.toString(fmt) + 'Z';


        String queryString = String.format(
                "?start=%s&end=%s&resolution=%s",
                startTimeString,
                endDateString,
                resolution.getName()
        );

        return queryString;
    }

}
