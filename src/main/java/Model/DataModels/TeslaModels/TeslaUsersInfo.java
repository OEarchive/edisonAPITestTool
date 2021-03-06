package Model.DataModels.TeslaModels;

public class TeslaUsersInfo {    
private final String tesla_user;
    private final String tesla_password;

    public TeslaUsersInfo(EnumTeslaBaseURLs url) {
        switch (url) {
            case Ninja:
                tesla_user = System.getenv(EnumConnProperties.TESLA_DEV_USER.getPropertyName());
                tesla_password = System.getenv(EnumConnProperties.TESLA_DEV_PASSWORD.getPropertyName());
                break;
            default:
                tesla_user = System.getenv(EnumConnProperties.TESLA_USER.getPropertyName());
                tesla_password = System.getenv(EnumConnProperties.TESLA_PASSWORD.getPropertyName());
        }
    }

    public String getUserName() {
        return this.tesla_user;
    }

    public String getPassword() {
        return this.tesla_password;
    }

}