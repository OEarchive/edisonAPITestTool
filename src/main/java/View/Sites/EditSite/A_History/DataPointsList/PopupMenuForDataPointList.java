package View.Sites.EditSite.A_History.DataPointsList;

import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.EnumDPTagType;
import Model.DataModels.Datapoints.simulator.CalculatedPoints.EnumDependentPoints;
import Model.DataModels.Sites.SiteDPConfig.EnumGroupTypes;
import Model.DataModels.Sites.SiteDPConfig.SiteDPConfigPoint;
import Model.DataModels.Sites.SiteDPConfig.SiteDPConfigPoints;
import Model.DataModels.Views.PageView;
import Model.DataModels.Views.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PopupMenuForDataPointList extends JPopupMenu {

    private final SiteDPConfigPoints siteConfigPoints;

    public PopupMenuForDataPointList(
            java.awt.event.MouseEvent evt,
            final JList list,
            final List<String> favPoints,
            final SiteDPConfigPoints siteConfigPoints,
            final Map<String, DatapointMetadata> nameToMetadataMap,
            final PageView pageView,
            final String sidInUrl) {

        this.siteConfigPoints = siteConfigPoints;

        JMenuItem item = new JMenuItem("Copy");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int[] selectedRowIndicies = list.getSelectedIndices();

                StringBuilder sbf = new StringBuilder();

                sbf.append("DataPoints");
                sbf.append("\n");

                for (int i = 0; i < selectedRowIndicies.length; i++) {
                    Object value = list.getSelectedValuesList().get(i);
                    sbf.append(value);
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
                int start = 0;
                int end = list.getModel().getSize() - 1;
                if (end >= 0) {
                    list.setSelectionInterval(start, end);
                }
            }

        });
        this.add(selectAllItem);

        JMenuItem selectFavorites = new JMenuItem("Select Favorites");
        selectFavorites.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();
                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);
                    if (favPoints.contains(pointName)) {
                        selectedIndicies.add(index);
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }

        });
        this.add(selectFavorites);

        JMenuItem selectConfigPoints = new JMenuItem("Select Config Points");
        selectConfigPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();

                /* comment this out and now use tags...
                 for (String group : siteConfigPoints.getGroups().keySet()) {
                 List<SiteDPConfigPoint> gList = siteConfigPoints.getGroups().get(group);
                 for (SiteDPConfigPoint dpConfigPoint : gList) {
                 String stationPointName = dpConfigPoint.getStationPointName();
                 for (int index = 0; index < list.getModel().getSize(); index++) {
                 String pointName = (String) list.getModel().getElementAt(index);
                 if (stationPointName.contains(pointName)) {
                 selectedIndicies.add(index);
                 }
                 }
                 }
                 }
                 */
                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);
                    DatapointMetadata md = nameToMetadataMap.get(pointName);
                    for (String tagName : md.getTags()) {
                        if (EnumDPTagType.getTagTypeFromName(tagName) == EnumDPTagType.config) {
                            selectedIndicies.add(index);
                        }
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectConfigPoints);

        JMenuItem selectCalcPoints = new JMenuItem("Select Calculated Points");
        selectCalcPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();

                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);

                    DatapointMetadata md = nameToMetadataMap.get(pointName);
                    for (String tagName : md.getTags()) {
                        if (EnumDPTagType.getTagTypeFromName(tagName) == EnumDPTagType.calculated) {
                            selectedIndicies.add(index);
                        }
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectCalcPoints);

        JMenuItem selectDependentPoints = new JMenuItem("Select Dependent Points");
        selectDependentPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();

                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);

                    DatapointMetadata md = nameToMetadataMap.get(pointName);

                    for (Object key : md.getAssociations().keySet()) {

                        //String assocName = (String)md.getAssociations().get(sidInUrl);
                        String assocName = (String) md.getAssociations().get(key);
                        for (EnumDependentPoints enumPoint : EnumDependentPoints.values()) {

                            if (enumPoint.isPurePoint()
                                    && enumPoint.getFreindlyName().equalsIgnoreCase(assocName)) {
                                selectedIndicies.add(index);
                            }
                        }
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectDependentPoints);

        JMenuItem selectSummaryPoints = new JMenuItem("Select Summary Points");
        selectSummaryPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();

                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);

                    DatapointMetadata md = nameToMetadataMap.get(pointName);
                    for (String tagName : md.getTags()) {
                        if (EnumDPTagType.getTagTypeFromName(tagName) == EnumDPTagType.summary) {
                            selectedIndicies.add(index);
                        }
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectSummaryPoints);

        JMenuItem selectOptimizationPoints = new JMenuItem("Select Optimization Points");
        selectOptimizationPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();

                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);
                    if (foundMatch(EnumGroupTypes.optimization.getGroupName(), pointName)) {

                        selectedIndicies.add(index);
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectOptimizationPoints);

        JMenuItem selectBASSystemPoints = new JMenuItem("Select BAS System pts.");
        selectBASSystemPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();

                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);
                    if (foundMatch(EnumGroupTypes.basSystem.getGroupName(), pointName)) {

                        selectedIndicies.add(index);
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectBASSystemPoints);

        JMenuItem selectBASDeterminedPoints = new JMenuItem("Select BASDetermined pts.");
        selectBASDeterminedPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();

                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);
                    if (foundMatch(EnumGroupTypes.basDetermined.getGroupName(), pointName)) {

                        selectedIndicies.add(index);
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectBASDeterminedPoints);

        JMenuItem selectEquipPoints = new JMenuItem("Select Equipment points ");
        selectEquipPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();

                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);
                    if (foundMatch(EnumGroupTypes.equipment.getGroupName(), pointName)) {

                        selectedIndicies.add(index);
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectEquipPoints);

        //PageView pageView
        JMenuItem selectPageViewPoints = new JMenuItem("Select PageView points ");
        selectPageViewPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();

                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);
                    DatapointMetadata md = nameToMetadataMap.get(pointName);
                    
                    if( pointName.compareTo("Status") == 0 ){
                        System.out.println("status found");
                    }

                    for (Object association : md.getAssociations().keySet()) {
                        String assocName = (String) md.getAssociations().get(association);
                        for (String key : pageView.getDatapointList().keySet()) {
                            List<Point> dpl = pageView.getDatapointList().get(key);
                            for (Point viewPoint : dpl) {
                                
                                String stationsName = viewPoint.getStationName();
                                String name = viewPoint.getName();
                                
                                if (stationsName != null && stationsName.equalsIgnoreCase(assocName)) {
                                    selectedIndicies.add(index);
                                }
                                else if (name != null && name.equalsIgnoreCase(assocName)) {
                                    selectedIndicies.add(index);
                                }
                            }
                        }
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectPageViewPoints);
        
        
        //PageView pageView
        JMenuItem selectUICalculatedPoints = new JMenuItem("Select UI calculated points ");
        selectUICalculatedPoints.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();
                
                List<String> uiCalcPointNames = getUICalculatedPointNames();

                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);
                    DatapointMetadata md = nameToMetadataMap.get(pointName);
                    
                    if( uiCalcPointNames.contains( pointName )){
                        selectedIndicies.add(index);
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectUICalculatedPoints);
        
        
        //PageView pageView
        JMenuItem selectLarrysList = new JMenuItem("Select Larry's points ");
        selectLarrysList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedIndicies = new ArrayList<>();
                
                List<String> larrysPoints = getLarrysListOfPoints();

                for (int index = 0; index < list.getModel().getSize(); index++) {
                    String pointName = (String) list.getModel().getElementAt(index);
                    DatapointMetadata md = nameToMetadataMap.get(pointName);
                    
                    if( larrysPoints.contains( pointName )){
                        selectedIndicies.add(index);
                    }
                }

                //TODO: There has to be a better way to convert list of ints to arr
                int arr[] = new int[selectedIndicies.size()];
                for (int i = 0; i < selectedIndicies.size(); i++) {
                    arr[i] = selectedIndicies.get(i);
                }

                list.setSelectedIndices(arr);
            }
        });
        this.add(selectLarrysList);
        
        
        this.show(evt.getComponent(), evt.getX(), evt.getY());

    }

    private Boolean foundMatch(String groupName, String pointName) {

        List<SiteDPConfigPoint> gList = siteConfigPoints.getGroups().get(groupName);
        for (SiteDPConfigPoint dpConfigPoint : gList) {
            String stationPointName = dpConfigPoint.getStationPointName();
            if (stationPointName.equalsIgnoreCase(pointName)) {
                return true;
            }
        }
        return false;
    }
    
    private List<String> getUICalculatedPointNames() {

        String[] pointNames = {
            "kWTon",
            "PlantCOP",
            "kWh",
            "BaselinekWh",
            "kWhDelta",
            "DollarsCost",
            "BaselineDollarsCost",
            "DollarsSaved",
            "CO2Produced",
            "BaselineCO2Produced",
            "CO2Saved",
            "ChillerkWTon",
            "TonHours",
            "PercentOptimized",
            "PercentPartiallyOptimized",
            "PercentOptimizationDisabled",
            "PercentPlantOff",
            "PercentBASCommunicationFailure"};

        List<String> names = Arrays.asList(pointNames);
        return names;
    }
    
    //CH1kW,CH2kW,CH3kW,CT1kW,CT2kW,CDWP1kW,CDWP2kW,CDWP3kW,PCHWP1kW,PCHWP2kW,PCHWP3kW"
    
        private List<String> getLarrysListOfPoints() {

        String[] pointNames = {
            "CH1kW","CH2kW","CH3kW","CT1kW","CT2kW","CDWP1kW","CDWP2kW","CDWP3kW","PCHWP1kW","PCHWP2kW","PCHWP3kW"};

        List<String> names = Arrays.asList(pointNames);
        return names;
    }
}
