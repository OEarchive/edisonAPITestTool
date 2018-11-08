package Model.Atom;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * This entire file is just for debugging purposes - it just dumps info to stdout.
 * 
 */

public class PrintStats {

    PrintStream out;
    String inputFilePath;
    String recTableFilePath;
    HashMap<Integer, ChillerModel> chillerModels;
    List<Map.Entry<Integer, Double>> mapEntries;
    String stagingOrder;
    int[][] recommendationTable;
    int validModelCount;
    int totalNumChillers;

    public PrintStats(
            PrintStream out,
            String inputFilePath,
            String recTableFilePath,
            String stagingOrder,
            int[][] recommendationTable,
            HashMap<Integer, ChillerModel> chillerModels,
            List<Map.Entry<Integer, Double>> mapEntries
    ) {
        this.out = out;
        this.inputFilePath = inputFilePath;
        this.recTableFilePath = recTableFilePath;

        this.chillerModels = chillerModels;
        this.mapEntries = mapEntries;
        this.stagingOrder = stagingOrder;
        this.recommendationTable = recommendationTable;

        this.totalNumChillers = chillerModels.size();

        this.validModelCount = 0;
        for (int chillerId : this.chillerModels.keySet()) {
            ChillerModel m = this.chillerModels.get(chillerId);
            if (!m.modelIsValid()) {
                continue;
            }
            this.validModelCount++;
        }

    }
    
    public void DumpFilePaths() {

        this.out.println("==========");
        this.out.println("File Paths");
        this.out.println("==========");

        this.out.println("Input file: " + this.inputFilePath);
        this.out.println("Recommendation Table: " + this.recTableFilePath);

    }

    public void DumpStagingOrder() {

        this.out.println("=============================");
        this.out.println("Staging order: " + this.stagingOrder);
        for (Map.Entry<Integer, Double> entry : mapEntries) {
            String msg = String.format("Chiller: %s total of KW per Ton: %f", entry.getKey(), entry.getValue());
            this.out.println(msg);
        }

        this.out.println();

    }

    public void DumpModels() {
        for (int chillerId : this.chillerModels.keySet()) {
            ChillerModel m = chillerModels.get(chillerId);

            this.out.println("===============");
            this.out.printf("Chiller%s Model %n", chillerId);
            this.out.println("===============");

            if (!m.modelIsValid()) {
                this.out.println("Model is invalid ");
                this.out.printf("Count of valid intervals: %d %n", m.getCountOfValidIntervals());
                continue;
            }

            this.out.printf("Count of valid rows (sample size) = %d %n", m.getCountOfValidIntervals());
            this.out.printf("Degrees of freedom (sample size - count of (5) variables) = %d %n", m.getCountOfValidIntervals() - EnumRegressorTypes.values().length);
            this.out.printf("Sum of Predictions: %.6f %n", m.getSumOfKWPerTonPredictions());
            this.out.printf("R-Squared: %.6f %n", m.getRSQuared());
            this.out.printf("Regressand Variance: %.6f %n", m.getRegressandVariance());
            this.out.printf("Sigma (Std Deviation): %6f %n", m.getSigma());

            this.out.println();

            ChillerModelStats s = m.getModelStats();
            if (s == null) {
                continue;
            }

            this.out.println("Residuals");
            String msg = "   Min       Q1        Median    Q3       Max";
            this.out.println(msg);
            msg = "";
            DecimalFormat f = new DecimalFormat("0.000000");
            msg += f.format(s.getMin()) + "  ";
            msg += f.format(s.getQ1()) + "  ";
            msg += f.format(s.getMedian()) + "  ";
            msg += f.format(s.getQ3()) + "  ";
            msg += f.format(s.getMax()) + "  ";
            this.out.println(msg);
            this.out.println();

            this.out.format("%15s %15s %n", "Regressor", "Value");
            this.out.format("%15s %15s %n", "----------", "----------");

            for (EnumRegressorTypes enc : EnumRegressorTypes.values()) {

                DecimalFormat formatter = new DecimalFormat("0.###E0");
                String temp = formatter.format(m.getRegressor(enc));

                this.out.printf("%15s %15.6f (%s) %n", enc.name(), m.getRegressor(enc), temp);
            }
            this.out.printf("%n");

        }
    }

    public void DumpResults() {

        this.out.println("================");
        this.out.println("kw_per_ton predictions for every 5 minute interval in a day based on: ");
        this.out.println(" -- last 24 hrs worth of data");
        this.out.println(" -- the models that are valid");
        this.out.println("================");

        if (this.validModelCount <= 0) {
            this.out.println("No valid models!");
            return;
        }

        double[][] matrix = new double[288][this.validModelCount];

        for (int i = 0; i < 288; i++) {

            double row[] = new double[this.validModelCount];

            int colIndex = 0;
            for (int chillerId : this.chillerModels.keySet()) {
                ChillerModel m = this.chillerModels.get(chillerId);
                if (!m.modelIsValid()) {
                    continue;
                }
                Prediction p = m.getKWPerTonPredictions().get(i);
                row[colIndex++] = p.getKWPerTonPrediction();
            }
            matrix[i] = row;
        }

        double[] totals = new double[this.validModelCount];

        String header = "        ";

        for (int chillerId : this.chillerModels.keySet()) {
            ChillerModel m = this.chillerModels.get(chillerId);
            if (!m.modelIsValid()) {
                continue;
            }
            header += "Chiller" + Integer.toString(chillerId) + "    ";
        }
        this.out.println(header);

        this.out.print("        ");
        for (int i = 0; i < this.validModelCount; i++) {
            this.out.print("========    ");
        }
        this.out.println();

        for (int i = 0; i < 288; i++) {
            String msg = Integer.toString(i + 1) + ".)";
            while (msg.length() < 6) {
                msg += " ";
            }
            for (int j = 0; j < this.validModelCount; j++) {

                double d = matrix[i][j];
                totals[j] += d;

                DecimalFormat f = new DecimalFormat("  0.000000");
                msg += f.format(d) + "  ";

            }
            this.out.println(msg);
        }

        this.out.print("      ");
        for (int i = 0; i < this.validModelCount; i++) {
            this.out.print("==========  ");
        }
        this.out.println();
        String msg = "      ";
        for (int i = 0; i < this.validModelCount; i++) {
            DecimalFormat f = new DecimalFormat("000.000000");
            msg += f.format(totals[i]);
            if (i < this.validModelCount - 1) {
                msg += "  ";
            }

        }
        this.out.println(msg);
        this.out.println();
    }

    /*
     public void DumpPredictions() {

     this.out.println("================");
     this.out.println("Predictions");
     this.out.println("================");

     int obsIndex = 0;
     boolean keepGoing = true;

     //16994  0.522280  16994  0.656999  16994  0.534655  16994  0.620946  16994  0.612568  16994  0.502797 
     String msg = "";

     while (keepGoing) {

     msg = "row ";

     msg += Integer.toString(obsIndex + 1) + ".) ";

     keepGoing = false;

     for (int chillerId = 1; chillerId <= 6; chillerId++) {

     ChillerModel m = this.chillerModels.get(chillerId);

     HashMap<Integer, Prediction> kwPredictions = m.getKWPerTonPredictions();

     int obsNumber = -1;
     double kwPerTonPredicion = 0.0;

     if (obsIndex < kwPredictions.size()) {

     keepGoing = true;
     Prediction prediction = kwPredictions.get(obsIndex);

     obsNumber = prediction.getObsNumber();
     kwPerTonPredicion = prediction.getKWPerTonPrediction();
     }

     if (obsNumber > 0) {
     msg += Integer.toString(obsNumber);

     DecimalFormat f = new DecimalFormat("  0.000000");
     msg += f.format(kwPerTonPredicion) + "  ";
     } else {
     msg += "                    ";
     }

     }

     obsIndex++;

     this.out.println(msg);
     this.out.println();

     }
     }
     */
    public void DumpTheRecommentationTable() {

        this.out.println("=====================");
        this.out.println("Recommentdation Table");
        this.out.println("=====================");
        this.out.println("");

        String msg = "S  R  ";
        for( int chillerId : this.chillerModels.keySet() ){
            msg += chillerId;
            msg += "  ";
        }
        this.out.println(msg);
        msg = "";
        for( int i=0; i< this.totalNumChillers + 2; i++ ){
            msg += "-- ";
        }
        this.out.println(msg);

        for (int i = 0; i < this.recommendationTable.length; i++) {
            msg = "";
            for (int j = 0; j < this.totalNumChillers + 2; j++) {
                msg += this.recommendationTable[i][j];
                if (j < this.totalNumChillers + 1) {
                    msg += ", ";
                }
            }
            this.out.println(msg);
        }

    }
}
