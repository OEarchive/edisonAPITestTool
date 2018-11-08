package Model.Atom;

import java.io.IOException;
import java.util.HashMap;

public final class PlantHistory {
    private final HistoryBuilder historyBuilder;
    private final String pathToCSVFile;

    public PlantHistory(String pathToCSVFile) throws IOException {
        this.pathToCSVFile = pathToCSVFile;
        this.historyBuilder = new CSVHistoryBuilder(pathToCSVFile);
        init();
    }

    public PlantHistory(HistoryBuilder historyBuilder) {
        this.pathToCSVFile = null;
        this.historyBuilder = historyBuilder;
        init();
    }

    public HashMap<Integer, IntervalInHistory> getHistory() {
        return historyBuilder.getHistory();
    }

    public HashMap<Integer, ChillerModel> getChillerModels() {
        return this.historyBuilder.getChillerModels();
    }

    /**
     * Whether or not this plant history was initialized from a CSV file.
     *
     * @return true when this plant history was initialized from a CSV file
     */
    public boolean wasInitializedFromCSV() {
        return this.pathToCSVFile != null;
    }

    /**
     * The CSV file that this plant history was initialized from, if any.
     *
     * @return Path to the CSV file that this plan history was initialized from, or null.
     */
    public String getPathToCSVFile() {
        return this.pathToCSVFile;
    }

    /**
     * Generate the chiller models from the history.
     */
    private void init() {

        historyBuilder.generateChillerModels();

    }
}
