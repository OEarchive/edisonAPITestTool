package Model.RestClient;

import Model.DataModels.Reports.MonthlyReportItem;
import Model.DataModels.Reports.ReportsList;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.ReportsClient;
import Model.RestClient.Clients.RestClientCommon;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.List;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportsModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;
    private ReportsClient reportsClient;

    public ReportsModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;

        this.reportsClient = new ReportsClient(model.getRestClient());
        this.pcs = pcs;
    }

    public void resetClient(String serviceURL, String accessToken, RestClientCommon api) {
        this.reportsClient = new ReportsClient(api);
        this.reportsClient.setServiceURLAndToken(serviceURL, accessToken);
    }

    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void getReportsList(final String sid, final boolean onlyApproved) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = reportsClient.getReports(sid, onlyApproved);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        List<MonthlyReportItem> list = (List<MonthlyReportItem>) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.ReportListReturned.getName(), null, list);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

    public void getReportSchema(final String reportId) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = reportsClient.getReportSchema(reportId);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        //ReportsList list = (ReportsList) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.ReportSchemaReturned.getName(), null, resp.responseObject);
                    } else {
                        pcs.firePropertyChange(PropertyChangeNames.ErrorResponse.getName(), null, resp);
                    }
                    pcs.firePropertyChange(PropertyChangeNames.RequestResponseChanged.getName(), null, model.getRRS());

                } catch (Exception ex) {
                    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
                    logger.error(this.getClass().getName(), ex);
                }
            }
        };
        worker.execute();
    }

}
