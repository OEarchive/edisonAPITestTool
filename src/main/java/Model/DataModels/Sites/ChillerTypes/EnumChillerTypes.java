package Model.DataModels.Sites.ChillerTypes;

import java.util.ArrayList;
import java.util.List;

public enum EnumChillerTypes {
    carrier("carrier", "carrier", "super chiller", 1),
    defaultChiller("default","who knows", "engima model", 2),
    mcquay("mcquay","mcquay", "mcCheese", 3),
    mcquayWmc("mcquay-wmc","mcquay", "wmc sedan", 4),
    multistack("multistack","multistack", "antipipe", 5),
    smardt("smardt","smardt", "einstein", 6),
    traneCd("trane-cd","trane", "cd to home dir", 7),
    traneCv("trane-cv","trane", "cv sport coup", 8),
    turbocor("turbocor","turbocor", "sleek n' shiny", 9),
    yorkYk("york-yk","york", "yk 2000", 10),
    yorkYm("york-ym","york", "ym mm good", 11),
    yorkYt("york-yt","york", "ytt", 12);

    private String name;
    private String make;
    private String model;
    private int dropDownIndex;

    EnumChillerTypes(String name, String make, String model, int dropDownIndex) {
        this.name = name;
        this.make = make;
        this.model = model;
        this.dropDownIndex = dropDownIndex;
    }

    public String getName() {
        return this.name;
    }
    
    public String getMake(){
        return this.make;
    }
    
    public String getModel(){
        return this.model;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    static public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (EnumChillerTypes res : EnumChillerTypes.values()) {
            names.add(res.getName());
        }
        return names;
    }

    static public EnumChillerTypes getEnumFromName(String name) {
        for (EnumChillerTypes res : EnumChillerTypes.values()) {
            if (res.getName().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }

}
