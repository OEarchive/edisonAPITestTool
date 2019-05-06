package Model.DataModels.CSVMaker;

import Model.DataModels.Datapoints.DatapointHistoriesResponse;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;

public class EdisonCSVMaker {

    private final String filePath;

    private final Map< String, Map<String, Object>> timestampToUNameValueMap;
    private final int prec;
    private final List<String> uNames;
    private final List<String> timestamps;

    public EdisonCSVMaker(String filePath, int prec, List<String> uNames, List<String> timestamps, Map< String, Map<String, Object>> timestampToUNameValueMap) {
        this.filePath = filePath;
        this.prec = prec;
        this.uNames = uNames;
        this.timestamps = timestamps;
        this.timestampToUNameValueMap = timestampToUNameValueMap;
    }

    public boolean makeCSV() {

        BufferedWriter writer = null;
        boolean status = true;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));

            String header = "timestamp," + String.join(",", uNames);
            writer.write(header);
            writer.newLine();

            for (String timeStamp : timestamps) {

                writer.write(timeStamp.toString());

                Map<String, Object> nameToValueMap = timestampToUNameValueMap.get(timeStamp);

                List<Object> values = new ArrayList<>();
                for (String name : uNames) {
                    values.add(nameToValueMap.get(name));
                }
                for (Object value : values) {

                    writer.write(",");
                    if (value == null) {
                        writer.write("null");
                    } else if (value instanceof Double) {

                        try {
                            String precFormatString = "#0";
                            String stringOfZeros = "000000";
                            if (prec > 0) {
                                precFormatString += ".";
                                precFormatString = precFormatString.concat(stringOfZeros.substring(0, prec));
                            }
                            NumberFormat formatter = new DecimalFormat(precFormatString);
                            String str = formatter.format(value);
                            writer.write( str);

                        } catch (Exception ex) {
                            value = "oops";
                        }

                    } else if (value instanceof Integer) {
                        String str = Integer.toString((Integer) value);
                        writer.write(str);
                    } else if (value instanceof Boolean) {
                        boolean flag = (boolean) value;
                        writer.write((flag) ? "true" : "false");
                    } else if (value instanceof String) {
                        writer.write((String) value);
                    } else {
                        writer.write("strange type");
                    }
                }
                writer.newLine();
            }

        } catch (Exception ex) {
            Logger.getLogger(EdisonCSVMaker.class.getName()).log(Level.SEVERE, null, ex);
            status = false;
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                Logger.getLogger(EdisonCSVMaker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return status;
    }

}
