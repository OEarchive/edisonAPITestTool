package Model.DataModels.Datapoints.simulator;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSReader {

    private final File file;

    public XLSReader(String fileName) {
        String configPath = System.getenv("PUSH_CONFIG_DIR");
        Path path = Paths.get(configPath, fileName);
        file = path.toFile();
    }

    public List<XLSPointRow> readFile() {

        List<XLSPointRow> xlsPoints = new ArrayList<>();
        try {

            FileInputStream fs = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Object v = false;

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int rowNumber = row.getRowNum();

                if (rowNumber == 0) {
                    continue;
                }

                XLSPointRow xlsPoint = new XLSPointRow();

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    int colIndex = cell.getColumnIndex();

                    switch (colIndex) {
                        case 0:
                            xlsPoint.setGroup(cell.getStringCellValue());
                            break;
                        case 1:
                            xlsPoint.setStAttrName(cell.getStringCellValue());
                            break;
                        case 2:
                            xlsPoint.setGraphAttrName(cell.getStringCellValue());
                            break;
                        case 3:
                            xlsPoint.setUOM(cell.getStringCellValue());
                            break;
                        case 4:
                            String pointTypeString = cell.getStringCellValue();
                            EnumXLSPointType pointType = EnumXLSPointType.getTypeFromName(pointTypeString);
                            xlsPoint.setPointType(pointType);
                            break;
                        case 5:
                            xlsPoint.setMin(readSafeValue(xlsPoint.getPointType(), cell));
                            break;
                        case 6:
                            xlsPoint.setMax(readSafeValue(xlsPoint.getPointType(), cell));
                            break;
                        case 7:
                            String patternString = cell.getStringCellValue();
                            EnumPattern pattern = EnumPattern.getEnumFromName(patternString);
                            if (pattern == EnumPattern.notSpecified) {
                                System.out.println("bad pattern: " + patternString);
                            }
                            xlsPoint.setPattern(pattern);
                            break;
                        case 8:
                            String periodString = cell.getStringCellValue();
                            EnumPeriod period = EnumPeriod.getEnumFromName(periodString);
                            if (period == EnumPeriod.notSpecified) {
                                System.out.println("bad period: " + periodString);
                            }
                            xlsPoint.setPeriod(period);
                            break;
                        case 9:
                            xlsPoint.setOffset(cell.getNumericCellValue());
                            break;
                    }
                }
                xlsPoints.add(xlsPoint);

            }
            fs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't read points xls please set env vars and start netbeans from command line", "oops...", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }

        return xlsPoints;
    }

    private Object readSafeValue(EnumXLSPointType pointType, Cell cell) {
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
