package Model.DataModels.ReportVerification.PostProcess;

import Model.DataModels.ReportVerification.PreProcess.UnCalculatedPoint;

public class CalculatedBucketPoint {

    private UnCalculatedPoint sourcePoint;
    private boolean hasPartner;
    private Object partnerValue;

    public CalculatedBucketPoint( UnCalculatedPoint sourcePoint, boolean hasPartner ){

        this.sourcePoint = sourcePoint;
        this.hasPartner = hasPartner;
        this.partnerValue = null;
    }

    public UnCalculatedPoint getSourcePoint() {
        return this.sourcePoint;
    }
    
    public boolean hasPartner(){
        return hasPartner;
    }
    
    public void setHasPartner( boolean hasPartner ){
        this.hasPartner = hasPartner;
    }


    public Object getPartnerValue() {
        return this.partnerValue;
    }
    
    public void setPartnerValue( Object v){
        this.partnerValue = v;
    }
    
}
