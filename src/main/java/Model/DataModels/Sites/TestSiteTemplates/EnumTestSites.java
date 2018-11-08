
package Model.DataModels.Sites.TestSiteTemplates;

import java.util.ArrayList;
import java.util.List;


public enum EnumTestSites {

    Cullen("Cullen_One", 0),
    Carnot_1("Carnot_1", 1),
    Carnot_2("Carnot_2", 2),
    Carnot_3("Carnot_3", 3),
    Carnot_4("Carnot_4", 4),
    Perkins_1("Perkins_1", 5),
    Perkins_2("Perkins_2", 6),
    Perkins_3("Perkins_3", 7);

    private String templateName;
    private int dropDownIndex;

    EnumTestSites(String freindlyName, int dropDownIndex) {
        this.templateName = freindlyName;
        this.dropDownIndex = dropDownIndex;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    static public List<String> getTemplateNames() {
        List<String> names = new ArrayList<>();
        for (EnumTestSites res : EnumTestSites.values()) {
            names.add(res.getTemplateName());
        }
        return names;
    }

    static public EnumTestSites getEnumFromName(String name) {
        for (EnumTestSites templateType : EnumTestSites.values()) {
            if (templateType.getTemplateName().compareTo(name) == 0) {
                return templateType;
            }
        }
        return null;
    }
}
