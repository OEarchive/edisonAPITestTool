package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StationValidate {

    @JsonProperty("bog")
    private ConfigInfoFromValidate bog;

    @JsonProperty("profileConfiguration")
    private ConfigInfoFromValidate profileConfiguration;

    @JsonProperty("pointsConfiguration")
    private ConfigInfoFromValidate pointsConfiguration;

    @JsonProperty("defaultParametersConfiguration")
    private ConfigInfoFromValidate defaultParametersConfiguration;

    @JsonProperty("portalParametersConfiguration")
    private ConfigInfoFromValidate portalParametersConfiguration;

    public ConfigInfoFromValidate getBog() {
        return bog;
    }

    @JsonIgnore
    public void setBog(ConfigInfoFromValidate bog) {
        this.bog = bog;
    }

    public ConfigInfoFromValidate getDifferent() {
        return profileConfiguration;
    }

    @JsonIgnore
    public void setDifferent(ConfigInfoFromValidate profileConfiguration) {
        this.profileConfiguration = profileConfiguration;
    }

    public ConfigInfoFromValidate getPointsConfiguration() {
        return pointsConfiguration;
    }

    @JsonIgnore
    public void setPointsConfiguration(ConfigInfoFromValidate pointsConfiguration) {
        this.pointsConfiguration = pointsConfiguration;
    }

    public ConfigInfoFromValidate getDefaultParametersConfiguration() {
        return defaultParametersConfiguration;
    }

    @JsonIgnore
    public void setDefaultParametersConfiguration(ConfigInfoFromValidate defaultParametersConfiguration) {
        this.defaultParametersConfiguration = defaultParametersConfiguration;
    }

    public ConfigInfoFromValidate getPortalParametersConfiguration() {
        return portalParametersConfiguration;
    }

    @JsonIgnore
    public void setPortalParametersConfiguration(ConfigInfoFromValidate portalParametersConfiguration) {
        this.portalParametersConfiguration = portalParametersConfiguration;
    }

}
