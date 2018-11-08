
package Model.Atom;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author halwilkinson
 */
public class ChillerStagingOrder {

    private HashMap<Integer, ChillerModel> allChillerModels;
    private HashMap<Integer, ChillerModel> validChillerModels;
    private HashMap<Integer, ChillerModel> inValidChillerModels;

    private final List<Map.Entry<Integer, Double>> mapEntries;
    private int numberOfModeledChillers;
    private int numberOfChillersInRecommendationTable;

    public ChillerStagingOrder(HashMap<Integer, ChillerModel> chillerModels) {

        this.numberOfChillersInRecommendationTable = chillerModels.size();

        this.validChillerModels = new HashMap<>();
        this.inValidChillerModels = new HashMap<>();
        this.allChillerModels = chillerModels;
        for (int chillerId : this.allChillerModels.keySet()) {
            ChillerModel m = this.allChillerModels.get(chillerId);
            if (m.modelIsValid()) {
                validChillerModels.put(chillerId, m);
            } else {
                inValidChillerModels.put(chillerId, m);
            }
        }

        this.numberOfModeledChillers = this.validChillerModels.size();

        Comparator<Map.Entry<Integer, Double>> byKWPredictions = new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> left, Map.Entry<Integer, Double> right) {
                return left.getValue().compareTo(right.getValue());
            }
        };

        HashMap<Integer, Double> stagingOrder = new HashMap<>();
        for (int chillerId : this.validChillerModels.keySet()) {
            stagingOrder.put(chillerId, this.validChillerModels.get(chillerId).getSumOfKWPerTonPredictions());
        }

        mapEntries = new ArrayList<>();
        mapEntries.addAll(stagingOrder.entrySet());
        Collections.sort(mapEntries, byKWPredictions);

    }

    public PrintStats makePrintStats() {
        return makePrintStats(System.out, null, null);
    }

    public PrintStats makePrintStats(PrintStream out, String inputFilePath, String recTableFilePath) {
        return new PrintStats(out, inputFilePath, recTableFilePath, getStagingOrder(), getRecommendationTable(), allChillerModels, getMapEntries());
    }

    public List<Map.Entry<Integer, Double>> getMapEntries() {
        return this.mapEntries;
    }

    public String getStagingOrder() {
        String order = "";

        for (int i = 0; i < this.getStagingOrderList().length; i++) {
            order += this.getStagingOrderList()[i];
        }

        return order;
    }

    public int[] getStagingOrderList() {
        int[] order = new int[this.numberOfChillersInRecommendationTable];
        int index = 0;
        for (Map.Entry<Integer, Double> entry : mapEntries) {
            order[index++] = entry.getKey();
        }

        //TODO: now add the invalid chillers to the end of the list:
        //The order of which is unimporant (per Clark) 
        //This will come back to haunt us (per Hal). 
        //I think we *do* need defaults.
        for (int chillerId : inValidChillerModels.keySet()) {
            order[index++] = chillerId;
        }

        return order;
    }

    public int[][] getRecommendationTable() {

        int width = this.numberOfChillersInRecommendationTable + 2;
        int height = (int) Math.pow(2, this.numberOfChillersInRecommendationTable);

        int[][] recommendationTable = new int[height][width];

        int[] stageOrder = this.getStagingOrderList();

        //create the unsorted staging order table (0,1,10,11,...,111111 ie, 0,1,2,3,...,63 )
        List<Integer> stagingTable = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, this.numberOfChillersInRecommendationTable); i++) {
            stagingTable.add(i);
        }

        //instatiate a comparer that uses the staging order to compare 2 rows in the table
        StageTableComparator orderer = new StageTableComparator(stageOrder);

        //sort the table by this staging order
        Collections.sort(stagingTable, orderer);

        //build the recommendation table by tacking on the stage and rank numbers
        int currentStage = 0;
        int currentRank = 0;
        for (int i = 0; i < Math.pow(2, this.numberOfChillersInRecommendationTable); i++) {

            int row = stagingTable.get(i);
            int stage = orderer.getStage(row);

            if (stage != currentStage) {
                currentRank = 0;
                currentStage = stage;
            }

            currentRank++;

            recommendationTable[i][0] = currentStage;
            recommendationTable[i][1] = currentRank;

            for (int j = 0; j < this.numberOfChillersInRecommendationTable; j++) {
                recommendationTable[i][j + 2] = ((row & 1 << (this.numberOfChillersInRecommendationTable - 1 - j)) > 0) ? 1 : 0;
            }
        }

        return recommendationTable;
    }

    public String getHeaderRow() {

        String headerRow = "Stage,Rank,";

        int index = 0;
        for( int chillerId : this.allChillerModels.keySet() ){
            // FIXME: find a way to ue the given chiller name
            String header = String.format("Chiller%d", chillerId);
            if (index < this.numberOfChillersInRecommendationTable - 1) {
                header += ",";
            }
            index++;
            headerRow += header;
        }
        return headerRow;

    }

    public void writeCSVtoDisk(String pathToCSV) {

        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(pathToCSV), "utf-8"));

            int[][] recTable = this.getRecommendationTable();

            writer.write(this.getHeaderRow());
            writer.write(System.lineSeparator());

            for (int i = 0; i < recTable.length; i++) {
                String rowString = "";
                for (int j = 0; j < this.numberOfChillersInRecommendationTable + 2; j++) {
                    rowString += recTable[i][j];
                    if (j < this.numberOfChillersInRecommendationTable + 1) {
                        rowString += ",";
                    }

                }
                writer.write(rowString);
                writer.write(System.lineSeparator());
            }
        } catch (IOException ex) {

            String msg = "Failed to write recommendation table to disk";
            Logger.getLogger(ChillerStagingOrder.class.getName()).log(Level.SEVERE, msg, ex);
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                Logger.getLogger(ChillerStagingOrder.class.getName()).log(Level.SEVERE, "not able to close file", ex);
            }
        }

    }
}
