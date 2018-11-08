package Model.DataModels.Stations;

public class StationValidateQueryParams {

    private final String stationId;
    private final String bog_hash;
    private final String profile_hash;
    private final String points_hash;
    private final String default_parameters_hash;
    private final String portal_parameters_hash;

    public StationValidateQueryParams(
            String stationId,
            String bog_hash,
            String profile_hash,
            String points_hash,
            String default_parameters_hash,
            String portal_parameters_hash) {

        this.stationId = stationId;
        this.bog_hash = bog_hash;
        this.profile_hash = profile_hash;
        this.points_hash = points_hash;
        this.default_parameters_hash = default_parameters_hash;
        this.portal_parameters_hash = portal_parameters_hash;
    }

    public String getQueryParams() {
        String queryParams = String.format("?stationId=%s&bog_hash=%s&profile_hash=%s&points_hash=%s&default_parameters_hash=%s&portal_parameters_hash=%s",
                stationId,
                bog_hash,
                profile_hash,
                points_hash,
                default_parameters_hash,
                portal_parameters_hash);

        return queryParams;
    }

}
