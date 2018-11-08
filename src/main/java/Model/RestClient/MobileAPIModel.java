package Model.RestClient;

import Model.DataModels.TrendAPI.MobileCompanyList;
import Model.DataModels.TrendAPI.MobileCompanyOverview;
import Model.DataModels.TrendAPI.MobileHealthInfo;
import Model.DataModels.TrendAPI.MobileTrends.SpecificTrends.MobileChillerTrend;
import Model.DataModels.TrendAPI.MobileTrends.SpecificTrends.MobileKeyTrend;
import Model.DataModels.TrendAPI.MobileTrends.SpecificTrends.MobileOptimizationTrend;
import Model.DataModels.TrendAPI.MobileTrends.SpecificTrends.MobilePlantEfficiency;
import Model.DataModels.TrendAPI.MobileTrends.SpecificTrends.MobileSavingsTrend;
import Model.DataModels.TrendAPI.SiteInfo.EnumMobileTrendTypes;
import Model.DataModels.TrendAPI.SiteInfo.MobileSiteInfo;
import Model.OptiCxAPIModel;
import Model.PropertyChangeNames;
import Model.RestClient.Clients.RestClientCommon;
import Model.RestClient.Clients.MobileAPIClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobileAPIModel extends java.util.Observable {

    private final OptiCxAPIModel model;
    final private PropertyChangeSupport pcs;

    private MobileAPIClient client;

    public MobileAPIModel(OptiCxAPIModel model, PropertyChangeSupport pcs) {
        this.model = model;

        this.client = new MobileAPIClient(model.getRestClient());
        this.pcs = pcs;
    }

    public void resetClient(String serviceURL, String accessToken, RestClientCommon api) {
        this.client = new MobileAPIClient(api);
        this.client.setServiceURLAndToken(serviceURL, accessToken);
    }

    public void addPropChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    //===========
    public void getMobileHealth() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getMobileHealth();
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        MobileHealthInfo msg = (MobileHealthInfo) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.MobileHealthReturned.getName(), null, msg);

                    } else {
                        resp.responseObject = "TREND API : Could not get health";
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

    public void getMobileVersion() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getMobileVersion();
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        String msg = (String) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.MobileVersionReturned.getName(), null, msg);

                    } else {
                        resp.responseObject = "TREND API : Could not get version";
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

    public void getMobileCompanies() {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getMobileCompanies();
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        MobileCompanyList msg = (MobileCompanyList) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.MobileCompaniesReturned.getName(), null, msg);

                    } else {
                        resp.responseObject = "TREND API: Could not get companies";
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

    public void getMobileCompanyOverview(final String uuid) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getMobileCompanyOverview(uuid);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        MobileCompanyOverview msg = (MobileCompanyOverview) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.MobileCompanyOverviewReturned.getName(), null, msg);

                    } else {
                        resp.responseObject = "TREND API: Could not get company overview";
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

    public void getMobileSiteOverview(final String uuid, final String timeFrame) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.GetMobileSiteOverview(uuid, timeFrame);
                return results;
            }

            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {
                        MobileSiteInfo msg = (MobileSiteInfo) resp.responseObject;
                        pcs.firePropertyChange(PropertyChangeNames.MobileSiteOverviewReturned.getName(), null, msg);

                    } else {
                        resp.responseObject = "TREND API: Could not get company overview";
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

    public void getMobileTrend(final String uuid, final EnumMobileTrendTypes trendType, final String timeFrame) {

        SwingWorker worker = new SwingWorker< OEResponse, Void>() {

            @Override
            public OEResponse doInBackground() throws IOException {
                OEResponse results = client.getMobileTrend(uuid, trendType.getFriendlyName(), timeFrame);
                return results;
            }

            /*
                MobileSavingsTrendReturned("MobileSavingsTrendReturned"),
    MobilePlantEfficiencyTrendReturned("MobilePlantEfficiencyTrendReturned"),
    MobileOptimizationTrendReturned("MobileOptimizationTrendReturned"),
    MobileChillerTrendReturned("MobileChillerTrendReturned"),
    MobileKeyTrendReturned("MobileKeyTrendReturned");
             */
            @Override
            public void done() {
                try {
                    OEResponse resp = get();

                    if (resp.responseCode == 200) {

                        ObjectMapper mapper = new ObjectMapper();

                        switch (trendType) {

                            case savings:
                                resp.responseObject = mapper.readValue((String) resp.responseObject, MobileSavingsTrend.class);
                                MobileSavingsTrend st = (MobileSavingsTrend) resp.responseObject;
                                pcs.firePropertyChange(PropertyChangeNames.MobileSavingsTrendReturned.getName(), null, st);
                                break;

                            case plant:
                                resp.responseObject = mapper.readValue((String) resp.responseObject, MobilePlantEfficiency.class);
                                MobilePlantEfficiency pe = (MobilePlantEfficiency) resp.responseObject;
                                pcs.firePropertyChange(PropertyChangeNames.MobilePlantEfficiencyTrendReturned.getName(), null, pe);
                                break;
                                
                            case optimization:
                                resp.responseObject = mapper.readValue((String) resp.responseObject, MobileOptimizationTrend.class);
                                MobileOptimizationTrend opt = (MobileOptimizationTrend) resp.responseObject;
                                pcs.firePropertyChange(PropertyChangeNames.MobileOptimizationTrendReturned.getName(), null, opt);
                                break;
                                
                            case chiller:
                                resp.responseObject = mapper.readValue((String) resp.responseObject, MobileChillerTrend.class);
                                MobileChillerTrend ch = (MobileChillerTrend) resp.responseObject;
                                pcs.firePropertyChange(PropertyChangeNames.MobileOptimizationTrendReturned.getName(), null, ch);
                                break;
                                
                            case key:
                                resp.responseObject = mapper.readValue((String) resp.responseObject, MobileKeyTrend.class);
                                MobileKeyTrend key = (MobileKeyTrend) resp.responseObject;
                                pcs.firePropertyChange(PropertyChangeNames.MobileKeyTrendReturned.getName(), null, key);
                                break;
                                   
                        }

                    } else {
                        resp.responseObject = "TREND API: Could not get trend";
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
