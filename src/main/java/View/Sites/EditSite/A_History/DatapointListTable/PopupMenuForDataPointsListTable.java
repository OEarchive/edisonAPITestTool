package View.Sites.EditSite.A_History.DatapointListTable;

import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class PopupMenuForDataPointsListTable extends JPopupMenu {

    public PopupMenuForDataPointsListTable(java.awt.event.MouseEvent evt, final JTable table) {

        JMenuItem item = new JMenuItem("Copy");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int[] selectedRowIndicies = table.getSelectedRows();

                int numrows = selectedRowIndicies.length;
                int numcols = table.getColumnCount();

                StringBuilder sbf = new StringBuilder();

                for (int i = 0; i < table.getColumnCount(); i++) {
                    sbf.append(table.getColumnName(i));
                    sbf.append("\t");
                }

                sbf.append("\n");

                for (int i = 0; i < numrows; i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {

                        Object value = table.getValueAt(selectedRowIndicies[i], j);

                        if (value instanceof Double) {
                            NumberFormat formatter = new DecimalFormat("#0.000");
                            value = formatter.format(value);
                        }

                        sbf.append(value);

                        if (j < numcols - 1) {
                            sbf.append("\t");
                        }
                    }
                    sbf.append("\n");
                }

                Toolkit tk = Toolkit.getDefaultToolkit();
                Clipboard cb = tk.getSystemClipboard();
                StringSelection s = new StringSelection(sbf.toString());
                cb.setContents(s, null);
            }
        });

        this.add(item);

        JMenuItem selectAllItem = new JMenuItem("Select All");
        selectAllItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                table.selectAll();
            }

        });
        this.add(selectAllItem);

        JMenuItem selectAtomPoints = new JMenuItem("Select Atom Points");
        selectAtomPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                List<String> listOfChillerPoints = getChillerPointNames();

                DatapointsListTableModel mod = (DatapointsListTableModel) table.getModel();

                for (int rowNumber = 0; rowNumber < table.getRowCount(); rowNumber++) {
                    int modelNumber = table.convertRowIndexToModel(rowNumber);
                    DatapointsAndMetadataResponse pointAndMetaData = mod.getRow(modelNumber);

                    for (Map nameAndSid : pointAndMetaData.getDatapointAssociations()) {
                        String name = (String) nameAndSid.get("name");
                        if (listOfChillerPoints.contains(name)) {
                            if (pointAndMetaData.getName().startsWith("CH", 0) || name.compareToIgnoreCase("OAWB") == 0) {
                                table.getSelectionModel().addSelectionInterval(rowNumber, rowNumber);
                            }
                        }
                    }
                }

            }
        });
        this.add(selectAtomPoints);

        JMenuItem selectNicholePoints = new JMenuItem("Select Nichole Points");
        selectNicholePoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                List<String> nicholePointNames = getNicolePointNames();

                DatapointsListTableModel mod = (DatapointsListTableModel) table.getModel();

                for (int rowNumber = 0; rowNumber < table.getRowCount(); rowNumber++) {
                    int modelNumber = table.convertRowIndexToModel(rowNumber);
                    DatapointsAndMetadataResponse pointAndMetaData = mod.getRow(modelNumber);

                    String name = pointAndMetaData.getName();

                    if (nicholePointNames.contains(name)) {
                        table.getSelectionModel().addSelectionInterval(rowNumber, rowNumber);

                    }
                }

            }
        });
        this.add(selectNicholePoints);

        JMenuItem selectConfigPoints = new JMenuItem("Select Config Points");
        selectConfigPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                List<String> configPointNames = getConfigPointNames();

                DatapointsListTableModel mod = (DatapointsListTableModel) table.getModel();

                for (int rowNumber = 0; rowNumber < table.getRowCount(); rowNumber++) {
                    int modelNumber = table.convertRowIndexToModel(rowNumber);
                    DatapointsAndMetadataResponse pointAndMetaData = mod.getRow(modelNumber);

                    String name = pointAndMetaData.getName();

                    if (configPointNames.contains(name)) {
                        table.getSelectionModel().addSelectionInterval(rowNumber, rowNumber);

                    }
                }

            }
        });
        this.add(selectConfigPoints);
        
        JMenuItem selectEAPoints = new JMenuItem("Select Erik/Andrew Points");
        selectEAPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                List<String> eaPoints = getAndrewErikPointNames();

                DatapointsListTableModel mod = (DatapointsListTableModel) table.getModel();

                for (int rowNumber = 0; rowNumber < table.getRowCount(); rowNumber++) {
                    int modelNumber = table.convertRowIndexToModel(rowNumber);
                    DatapointsAndMetadataResponse pointAndMetaData = mod.getRow(modelNumber);

                    String name = pointAndMetaData.getName();

                    if (eaPoints.contains(name)) {
                        table.getSelectionModel().addSelectionInterval(rowNumber, rowNumber);

                    }
                }

            }
        });
        this.add(selectEAPoints);

        this.show(evt.getComponent(), evt.getX(), evt.getY());

    }

    private List<String> getChillerPointNames() {
        List<String> chillerPoints = Arrays.asList(new String[]{
            "Status", "ChilledWaterFlow", "ChilledWaterReturnTemperature", "ChilledWaterSupplyTemperature", "kW", "OAWB"
        });
        return chillerPoints;
    }

    private List<String> getNicolePointNames() {
        List<String> chillerPoints = Arrays.asList(new String[]{
            "kWTon",
            "TotalTon",
            "TotalkW",
            "CH1kW",
            "CH2kW",
            "CH3kW",
            "TotalCapacity",
            "MininumChilledWaterFlow",
            "Ton",
            "ChilledWaterFlow",
            "ChilledWaterSupplyTemperature",
            "ChilledWaterReturnTemperature"
        });

        return chillerPoints;
    }

    private List<String> getConfigPointNames() {
        List<String> configPoints = Arrays.asList(new String[]{
            "TotalCapacity",
            "MinimumChilledWaterFlow",
            "BlendedUtilityRate",
            "CO2EmissionFactor"
        });
        return configPoints;
    }

    private List<String> getAndrewErikPointNames() {
        List<String> aePoints = Arrays.asList(new String[]{
            "BaselinekW",
            "BaselinekWh",
            "BaselinekWTon",
            "TotalkW",
            "TotalTon",
            "kWDelta",
            "kWhDelta",
            "OAT",
            "OAWB",
            "EDGEMODE"
        });
        return aePoints;
    }

    /*
    BaselinekW
    BaselinekWh
    BaselinekWTon
    TotalkW
    TotalTon
    kWDelta
    kWhDelta
    OAT
    OAWB
    EDGEMODE
     */
}
