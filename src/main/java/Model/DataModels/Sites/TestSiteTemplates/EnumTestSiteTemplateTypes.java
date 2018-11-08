package Model.DataModels.Sites.TestSiteTemplates;

import java.util.ArrayList;
import java.util.List;

public enum EnumTestSiteTemplateTypes {

    small("small", 0),
    medium("medium", 1),
    large("large", 2),
    extraLarge("extraLarge", 3);

    private String templateName;
    private int dropDownIndex;

    EnumTestSiteTemplateTypes(String freindlyName, int dropDownIndex) {
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
        for (EnumTestSiteTemplateTypes res : EnumTestSiteTemplateTypes.values()) {
            names.add(res.getTemplateName());
        }
        return names;
    }

    static public EnumTestSiteTemplateTypes getEnumFromName(String name) {
        for (EnumTestSiteTemplateTypes templateType : EnumTestSiteTemplateTypes.values()) {
            if (templateType.getTemplateName().compareTo(name) == 0) {
                return templateType;
            }
        }
        return null;
    }
}
