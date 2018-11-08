
package Model.DataModels.ReportVerification.CalcPointClassification;


public class PairedDepPoint {
    
 
       private final EnumDependentPointFilter depPoint;
       private final boolean partnerFlag;
       
       
       public PairedDepPoint(  EnumDependentPointFilter depPoint ,boolean partnerFlag ){
           this.depPoint = depPoint;
           this.partnerFlag = partnerFlag;
       }
       
       public EnumDependentPointFilter getPointType(){
           return depPoint;
       }
       
       public boolean hasPartner(){
           return partnerFlag;
       }

}