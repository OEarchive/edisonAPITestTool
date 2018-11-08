
package Model.DataModels.Sites.TestSiteTemplates.SpecificSites.Carnot;

import Model.DataModels.Sites.ChillerTypes.EnumChillerTypes;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.TestSiteTemplates.CommonSiteTemplate;
import Model.DataModels.Sites.TestSiteTemplates.SiteTemplateInfo;
import java.util.HashMap;
import java.util.Map;


public class CarnotFour_SiteOne_Template {
    
    private final CommonSiteTemplate cst;
    
    
    public CarnotFour_SiteOne_Template( String customerSid, String extSfId, String siteName){
        
        Map<EnumChillerTypes, Integer> chillerTypesAndCounts = new HashMap<>();
        chillerTypesAndCounts.put( EnumChillerTypes.yorkYk, 2 );

        int primaryChilledWaterPumpsCount = 2; 
        int secondaryChilledWaterPumpCount = 3;
        int condenserCount = 2;
        int coolingTowerCount = 1;
        int heatExchangerCount = 1;
        
        SiteTemplateInfo sti = new SiteTemplateInfo( chillerTypesAndCounts, coolingTowerCount, condenserCount, primaryChilledWaterPumpsCount, secondaryChilledWaterPumpCount, heatExchangerCount);
        
        cst = new CommonSiteTemplate(customerSid, siteName, extSfId,  sti);
        
    }
    
    public Site getSiteTemplate(){
        return cst.getSite();
    }
    
}