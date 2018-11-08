package Model.DataModels.Datapoints.simulator.CalcPointsXLSReader;

import Model.DataModels.Datapoints.simulator.XLSPointRow;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class UICalcPointXLSReader {

    private final File file;

    private final List<String> timestamps;
    private final Map<String, List<Object>> pointsAndValues;
    private final Map<Integer, String> colIndexToPointNameMap;

    public UICalcPointXLSReader(String fileName) {
        String configPath = System.getenv("PUSH_CONFIG_DIR");
        Path path = Paths.get(configPath, fileName);
        file = path.toFile();
        timestamps = new ArrayList<>();
        pointsAndValues = new HashMap<>();
        colIndexToPointNameMap = new HashMap<>();

        try {
            FileInputStream fs = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Object v = false;

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int rowNumber = row.getRowNum();

                XLSPointRow xlsPoint = new XLSPointRow();

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    int colIndex = cell.getColumnIndex();

                    if (row.getRowNum() == 0) {

                        if (colIndex == 0) {
                            continue;
                        }

                        String pointName = cell.getStringCellValue();
                        E3OSToEdisonNamesEnum pointNameMapEnum = E3OSToEdisonNamesEnum.getEnumFromE3OSName(pointName);
                        if (pointNameMapEnum != null) {
                            String edgePointName = pointNameMapEnum.getEdgeName();

                            pointsAndValues.put(edgePointName, new ArrayList<Object>());
                            colIndexToPointNameMap.put(colIndex, edgePointName);
                        }
                        continue;
                    }

                    if (colIndex == 0) {

                        Date d = cell.getDateCellValue();
                        DateTime jodaDate = new DateTime(d);
                        DateTimeFormatter utcFmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                        String timeStamp = jodaDate.toString(utcFmt);
                        timestamps.add(timeStamp.toString());
                    } else {
                        if (colIndexToPointNameMap.containsKey(colIndex)) {
                            Object val = readSafeValue(cell);
                            String pointName = colIndexToPointNameMap.get(colIndex);
                            List<Object> values = pointsAndValues.get(pointName);
                            values.add(val);
                        }
                    }
                }
            }
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> getTimestamps() {
        return this.timestamps;
    }

    public Map<String, List<Object>> getPointsAndValues() {
        return pointsAndValues;
    }

    private Object readSafeValue(Cell cell) {
        Object v = null;

        try {
            v = cell.getNumericCellValue();
        } catch (Exception ex) {
            System.out.println("Could not read from cell");
            v = null;
        }

        return v;
    }
}
