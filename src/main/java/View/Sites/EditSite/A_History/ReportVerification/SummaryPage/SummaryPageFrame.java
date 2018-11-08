
package View.Sites.EditSite.A_History.ReportVerification.SummaryPage;

import Controller.OptiCxAPIController;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.ReportVerification.PreProcess.UnCalculatedPoint;
import Model.PropertyChangeNames;
import View.Sites.EditSite.A_History.ReportVerification.ReportVerificationFrame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTimeZone;


public class SummaryPageFrame extends javax.swing.JFrame implements PropertyChangeListener {
    
    private static SummaryPageFrame thisInstance;
    private final OptiCxAPIController controller;
    private final String siteSid;
    private final DateTimeZone siteTimeZone;
    private final List<DatapointsAndMetadataResponse> initialPoints;
    
    
    private List<String> allUNames;
    private Map<String, UnCalculatedPoint> uNameToDatapointMap;


    public static SummaryPageFrame getInstance(final OptiCxAPIController controller, String siteSid, DateTimeZone siteTimeZone, List<DatapointsAndMetadataResponse> initialPoints) {

        if (thisInstance == null) {
            thisInstance = new SummaryPageFrame(controller, siteSid, siteTimeZone, initialPoints );
        }
        return thisInstance;

    }
    
    
    
    private SummaryPageFrame( final OptiCxAPIController controller, String siteSid, DateTimeZone siteTimeZone, List<DatapointsAndMetadataResponse> initialPoints ) {
        initComponents();
        
        this.controller = controller;
        this.siteSid = siteSid;
        this.siteTimeZone = siteTimeZone;
        this.initialPoints = initialPoints;
       
    }
    
    @Override
    public void dispose() {
        controller.removePropChangeListener(this);
        thisInstance = null;
        super.dispose();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 842, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 608, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        //Points list changed
        if (propName.equals(PropertyChangeNames.DatapointUnionForReportsReturned.getName())) {
            //datapointsForReports = (List<DatapointsAndMetadataResponse>) evt.getNewValue();
            //setCalcPointInfoAndPointNameMapping();

            //this.allUNames = new ArrayList<>();
            //for (DatapointsAndMetadataResponse resp : datapointsForReports) {
             //   String uName = getUName(resp.getSid(), resp.getName());
             //   allUNames.add(uName);
            //}

            //fillReportPointsTable(selectedCalculatedPointEnum);


        } else if (propName.equals(PropertyChangeNames.LoginResponse.getName())) {
            dispose();
        }
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
