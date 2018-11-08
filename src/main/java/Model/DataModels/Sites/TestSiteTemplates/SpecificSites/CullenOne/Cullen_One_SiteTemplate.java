
package Model.DataModels.Sites.TestSiteTemplates.SpecificSites.CullenOne;

import Model.DataModels.Sites.ChillerTypes.EnumChillerTypes;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.TestSiteTemplates.CommonSiteTemplate;
import Model.DataModels.Sites.TestSiteTemplates.SiteTemplateInfo;
import java.util.HashMap;
import java.util.Map;


public class Cullen_One_SiteTemplate {
    
    private final CommonSiteTemplate cst;
    
    
    public Cullen_One_SiteTemplate( String customerSid, String extSfId, String siteName){
        
        Map<EnumChillerTypes, Integer> chillerTypesAndCounts = new HashMap<>();
        chillerTypesAndCounts.put( EnumChillerTypes.smardt, 1 );
        chillerTypesAndCounts.put( EnumChillerTypes.traneCd, 2 );

        int primaryChilledWaterPumpsCount = 3; 
        int secondaryChilledWaterPumpCount = 0;
        int condenserCount = 3;
        int coolingTowerCount = 2;
        int heatExchangerCount = 0;
        
        SiteTemplateInfo sti = new SiteTemplateInfo( chillerTypesAndCounts, coolingTowerCount, condenserCount, primaryChilledWaterPumpsCount, secondaryChilledWaterPumpCount, heatExchangerCount);
        
        cst = new CommonSiteTemplate(customerSid, siteName, extSfId,  sti);
        
    }
    
    public Site getSiteTemplate(){
        return cst.getSite();
    }
    
}
