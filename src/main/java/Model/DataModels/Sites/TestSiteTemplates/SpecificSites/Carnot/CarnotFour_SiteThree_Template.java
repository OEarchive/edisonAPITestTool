
package Model.DataModels.Sites.TestSiteTemplates.SpecificSites.Carnot;

import Model.DataModels.Sites.ChillerTypes.EnumChillerTypes;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.TestSiteTemplates.CommonSiteTemplate;
import Model.DataModels.Sites.TestSiteTemplates.SiteTemplateInfo;
import java.util.HashMap;
import java.util.Map;

public class CarnotFour_SiteThree_Template {
    
    private final Site site;
    
    public CarnotFour_SiteThree_Template( String customerSid, String extSfId, String siteName){
        
        Map<EnumChillerTypes, Integer> chillerTypesAndCounts = new HashMap<>();
        chillerTypesAndCounts.put( EnumChillerTypes.carrier, 2 );

        final int primaryChilledWaterPumpsCount = 2; 
        final int secondaryChilledWaterPumpCount = 2;
        final int condenserCount = 2;
        final int coolingTowerCount = 1;
        final int heatExchangerCount = 0;
        
        final SiteTemplateInfo sti = new SiteTemplateInfo( chillerTypesAndCounts, coolingTowerCount, condenserCount, primaryChilledWaterPumpsCount, secondaryChilledWaterPumpCount, heatExchangerCount);
        
        CommonSiteTemplate cst = new CommonSiteTemplate(customerSid, siteName, extSfId,  sti);
        site = cst.getSite();
        
    }
    
    public Site getSiteTemplate(){
        return site;
    }
    
}