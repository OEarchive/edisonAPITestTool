package Model.DataModels.ReportVerification.CalcPointClassification;

import java.util.ArrayList;
import java.util.List;

public class CalculatedPointAndDependenciesFilters {

    private final EnumCalcPointFilter calcPoint;
    private final List<EnumCalcPointFilter> dependentCalcPoints;
    private final List<EnumCalcPointFilter> partnerCalcPoints;

    private final List<EnumDependentPointFilter> dependentBasePoints;
    private final List<EnumDependentPointFilter> partnerBasePoints;

    public CalculatedPointAndDependenciesFilters(
            EnumCalcPointFilter calcPoint,
            List<EnumCalcPointFilter> dependentCalcPoints,
            List<EnumDependentPointFilter> dependentBasePoints
    ) {

        this.calcPoint = calcPoint;
        this.dependentCalcPoints = dependentCalcPoints;
        this.dependentBasePoints = dependentBasePoints;
        this.partnerCalcPoints = new ArrayList<>();
        this.partnerBasePoints = new ArrayList<>();
    }

    public CalculatedPointAndDependenciesFilters(
            EnumCalcPointFilter calcPoint,
            List<EnumCalcPointFilter> dependentCalcPoints,
            List<EnumDependentPointFilter> dependentBasePoints,
            List<EnumCalcPointFilter> partnerCalcPoints,
            List<EnumDependentPointFilter> partnerBasePoints
    ) {

        this.calcPoint = calcPoint;
        this.dependentCalcPoints = dependentCalcPoints;
        this.dependentBasePoints = dependentBasePoints;
        this.partnerCalcPoints = partnerCalcPoints;
        this.partnerBasePoints = partnerBasePoints;

    }

    public CalculatedPointAndDependenciesFilters(
            EnumCalcPointFilter calcPoint,
            List<EnumCalcPointFilter> dependentCalcPoints,
            List<EnumDependentPointFilter> dependentBasePoints,
            List<EnumCalcPointFilter> partnerCalcPoints
    ) {

        this.calcPoint = calcPoint;
        this.dependentCalcPoints = dependentCalcPoints;
        this.dependentBasePoints = dependentBasePoints;
        this.partnerCalcPoints = partnerCalcPoints;
        this.partnerBasePoints = new ArrayList<>();

    }

    public String getCaluclatedPointName() {
        return calcPoint.getCalculatedPointName();
    }

    public EnumCalcPointFilter getPointType() {
        return calcPoint;
    }

    public List<EnumCalcPointFilter> getOtherCalcPointFilters() {
        return this.dependentCalcPoints;
    }

    public List<EnumDependentPointFilter> getDependentPointFilters() {
        return this.dependentBasePoints;
    }

    public List<EnumCalcPointFilter> getPartnerCalcPointFilters() {
        return this.partnerCalcPoints;
    }

    public List<EnumDependentPointFilter> getPartnerBasePointFilters() {
        return this.partnerBasePoints;
    }
    
}
