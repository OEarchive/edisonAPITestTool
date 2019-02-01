package Model.DataModels.TeslaModels;

import java.util.ArrayList;
import java.util.List;

public enum EnumTeslaBaseURLs {
    LocalHost("http://localhost/v1"),
    Ninja("https://api.optimumenergy.ninja/v1"),
    Prod("https://api.optimumenergy.ninja/v1");

    private String url;

    EnumTeslaBaseURLs(String url) {
        this.url = url;

    }

    public String getURL() {
        return this.url;
    }

    static public List<String> getURLs() {
        List<String> urls = new ArrayList<>();
        for (EnumTeslaBaseURLs loc : EnumTeslaBaseURLs.values()) {
            urls.add(loc.getURL());
        }
        return urls;
    }

    static public EnumTeslaBaseURLs getHostFromName(String url) {
        for (EnumTeslaBaseURLs enumBaseURL : EnumTeslaBaseURLs.values()) {
            if (enumBaseURL.getURL().compareTo(url) == 0) {
                return enumBaseURL;
            }
        }
        return null;
    }

}
