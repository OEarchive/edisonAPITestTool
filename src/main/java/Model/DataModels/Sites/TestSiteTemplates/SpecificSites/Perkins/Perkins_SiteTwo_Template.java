
package Model.DataModels.Sites.TestSiteTemplates.SpecificSites.Perkins;

import Model.DataModels.Sites.ChillerTypes.EnumChillerTypes;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.TestSiteTemplates.CommonSiteTemplate;
import Model.DataModels.Sites.TestSiteTemplates.SiteTemplateInfo;
import java.util.HashMap;
import java.util.Map;


public class Perkins_SiteTwo_Template {
    
    private final Site site;
    
    public Perkins_SiteTwo_Template( String customerSid, String extSfId, String siteName){
        
        Map<EnumChillerTypes, Integer> chillerTypesAndCounts = new HashMap<>();
        chillerTypesAndCounts.put( EnumChillerTypes.yorkYm, 3 );

        final int primaryChilledWaterPumpsCount = 3; 
        final int secondaryChilledWaterPumpCount = 0;
        final int condenserCount = 3;
        final int coolingTowerCount = 6;
        final int heatExchangerCount = 1;
        
        final SiteTemplateInfo sti = new SiteTemplateInfo( chillerTypesAndCounts, coolingTowerCount, condenserCount, primaryChilledWaterPumpsCount, secondaryChilledWaterPumpCount, heatExchangerCount);
        
        CommonSiteTemplate cst = new CommonSiteTemplate(customerSid, siteName, extSfId,  sti);
        site = cst.getSite();
        
    }
    
    public Site getSiteTemplate(){
        return site;
    }
}
    
