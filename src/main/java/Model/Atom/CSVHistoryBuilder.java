package Model.Atom;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Load chiller history row by row from a CSV file.
 *
 * This implementation assumes that the CSV file includes columns for total chiller tons (CH1Tons, CH2Tons, ...),
 * KW per ton (CH1kWPerTon, CH2kWPerTon, ...), whether or not a chiller was added (CH1Add, CH2Add, ...), and
 * date related fields (day, Weekend, etc).
 */
public class CSVHistoryBuilder  extends HistoryBuilder {

    /**
     * This is the original implementation for loading plant history from a CSV file.
     *
     * @param pathToCSVFile   Path to the CSV file
     * @throws IOException
     */
    public CSVHistoryBuilder(String pathToCSVFile) throws IOException {

        File inputFile = new File(pathToCSVFile);

        CsvMapper mapper = new CsvMapper();
        CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
        MappingIterator<Map<?, ?>> it = mapper.reader(Map.class).with(bootstrap).readValues(inputFile);

        boolean keepGoing = true;
        boolean headerRowHasBeenRead = false;
        int firstDayNumber = -1;
        while (keepGoing) {

            try {
                Map<?, ?> colValues = it.nextValue();

                //ignore the header row
                if (!headerRowHasBeenRead) {
                    headerRowHasBeenRead = true;
                }

                if (colValues == null) {
                    keepGoing = false;
                    continue;
                }

                int obsNumber = Integer.parseInt((String) colValues.get("OBS_Number"));

                if (firstDayNumber == -1) {
                    firstDayNumber = Integer.parseInt((String) colValues.get("Daynumber"));
                }

                int dayNumberAdjusted = Integer.parseInt((String) colValues.get("Daynumber")) - firstDayNumber + 1;

                this.add(obsNumber, new IntervalInHistory(obsNumber, dayNumberAdjusted, colValues));

            } catch (Exception ex) {
                keepGoing = false;

            }
        }
    }


    public void generateChillerModels() {
        HashMap<Integer, IntervalInHistory> history = getHistory();

        int numOfChillersInRecTable = 0;

        if (history.size() > 0) {
            //create chiller models based on the number of chillers found in the first interval.
            numOfChillersInRecTable = history.get(1).getNumberOfChillers();
        }

        double plantGrossUp = getPlantGrossUp(numOfChillersInRecTable);

        for (int index = 0; index < numOfChillersInRecTable; index++) {
            int chillerId = index + 1;
            double chillerCapacity = getChillerCapacity(chillerId);
            double chillerSize = getChillerSize(chillerId);
            addChillerModel(chillerId, chillerCapacity, chillerSize, plantGrossUp);
        }

        applyMeanCoefficients();
    }

    private double getChillerCapacity(int chillerId) {

        switch (chillerId) {
            case 1:
                return 500.0;
            case 2:
                return 500.0;
            default:
                return 800.0;
        }
    }

    private double getChillerSize(int chillerId) {

        switch (chillerId) {
            case 1:
                return 500.0 / 800.0;
            case 2:
                return 500.0 / 800.0;
            default:
                return 1.0;
        }
    }

    private Double getPlantCapacity(int totalNumberOfChillers) {
        Double pc = 0.0;
        for (int Id = 1; Id <= totalNumberOfChillers; Id++) {
            pc += getChillerCapacity(Id);
        }
        return pc;
    }

    private Double getPlantGrossUp(int totalNumberOfChillers) {
        final double hypoPlantCapacity = totalNumberOfChillers * 800.0;
        return hypoPlantCapacity / getPlantCapacity(totalNumberOfChillers);
    }
}
