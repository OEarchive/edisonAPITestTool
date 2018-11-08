
package Model.DataModels.ReportVerification.CalcPointClassification;


public class PairedCalculatedPoint {
    
  
       private final EnumCalcPointFilter calcPoint;
       private final boolean partnerFlag;
       
       
       public PairedCalculatedPoint(  EnumCalcPointFilter calcPoint ,boolean partnerFlag ){
           this.calcPoint = calcPoint;
           this.partnerFlag = partnerFlag;
       }
       
     
       public EnumCalcPointFilter getPointType(){
           return calcPoint;
       }
       
       public boolean hasPartner(){
           return partnerFlag;
       }

}
