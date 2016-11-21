package it.cg.main.dto.inbound;

import java.util.Date;

public class InboundQuoteDTO {
	
	private String affinity;
//	private String bmClass;
//	private String bmClassFrom;
	private String bmalType;
	private Boolean flagInsuredLast5Years;
	private Boolean claimsInLast5Years;
	private Integer numberOfClaimsInLastYear;
	private String usualDriverOwnerRelationship;
	private Integer numberOfYoungDriver;
	private Date effectiveDate;
	private InboundVehicleDTO vehicle;
	private InboundCoverageDTO coverages;
	private InboundFigureDTO figures;
	private Integer numberOfVehiclesOwned;
	private Boolean fnb;
	private InboundContextDTO context;
	private String insuredCondition;
	private Integer installments;
	private String campaign;
	private String pncdSelected;
	private Date inceptionDate;
	private Date rateFromDate;
	private Integer renewalYears;
	private Boolean otherVehiclesInsuredWithUs;
	private InboundRatingInfoDTO ratingInfo;
	private Integer rateVersion;
	private InboundPremiumDTO premium;
	private String goodDriverClass;
	private String innerClassFrom;
	private String innerClass;
	private Boolean claimsInLast5YearInner;
	private Integer claimsInLastYearInner;
	private InboundOtherVehicleDTO otherVehicle;
	private String previousInsuranceCompany;
	private Integer driverNumber;
	private Boolean clean1;
	
//	Da qua in poi iniziano i nuovi attributi presi dalla versione 1.1 non presenti nel file di debora.
	
//	private Boolean policyHolderEqualMainDriver;
//	private Boolean policyHolderEqualVehicleOwner;
//	private Boolean ownerEqualMainDriver;
//	private Integer driverNumber;
//	private Boolean clean1;
//	private Boolean clean5;
//	private Boolean enableDebugging;
//	private String debuggingLog;
	
	public String getAffinity() {
		return affinity;
	}
	public void setAffinity(String affinity) {
		this.affinity = affinity;
	}
	public String getBmalType() {
		return bmalType;
	}
	public void setBmalType(String bmalType) {
		this.bmalType = bmalType;
	}
	public Boolean getFlagInsuredLast5Years() {
		return flagInsuredLast5Years;
	}
	public void setFlagInsuredLast5Years(Boolean flagInsuredLast5Years) {
		this.flagInsuredLast5Years = flagInsuredLast5Years;
	}
	public Boolean getClaimsInLast5Years() {
		return claimsInLast5Years;
	}
	public void setClaimsInLast5Years(Boolean claimsInLast5Years) {
		this.claimsInLast5Years = claimsInLast5Years;
	}
	public Integer getNumberOfClaimsInLastYear() {
		return numberOfClaimsInLastYear;
	}
	public void setNumberOfClaimsInLastYear(Integer numberOfClaimsInLastYear) {
		this.numberOfClaimsInLastYear = numberOfClaimsInLastYear;
	}
	public String getUsualDriverOwnerRelationship() {
		return usualDriverOwnerRelationship;
	}
	public void setUsualDriverOwnerRelationship(String usualDriverOwnerRelationship) {
		this.usualDriverOwnerRelationship = usualDriverOwnerRelationship;
	}
	public Integer getNumberOfYoungDriver() {
		return numberOfYoungDriver;
	}
	public void setNumberOfYoungDriver(Integer numberOfYoungDriver) {
		this.numberOfYoungDriver = numberOfYoungDriver;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public InboundVehicleDTO getVehicle() {
		return vehicle;
	}
	public void setVehicle(InboundVehicleDTO vehicle) {
		this.vehicle = vehicle;
	}
	public InboundCoverageDTO getCoverages() {
		return coverages;
	}
	public void setCoverages(InboundCoverageDTO coverages) {
		this.coverages = coverages;
	}
	public InboundFigureDTO getFigures() {
		return figures;
	}
	public void setFigures(InboundFigureDTO figures) {
		this.figures = figures;
	}
	public Integer getNumberOfVehiclesOwned() {
		return numberOfVehiclesOwned;
	}
	public void setNumberOfVehiclesOwned(Integer numberOfVehiclesOwned) {
		this.numberOfVehiclesOwned = numberOfVehiclesOwned;
	}
	public Boolean getFnb() {
		return fnb;
	}
	public void setFnb(Boolean fnb) {
		this.fnb = fnb;
	}
	public InboundContextDTO getContext() {
		return context;
	}
	public void setContext(InboundContextDTO context) {
		this.context = context;
	}
	public String getInsuredCondition() {
		return insuredCondition;
	}
	public void setInsuredCondition(String insuredCondition) {
		this.insuredCondition = insuredCondition;
	}
	public Integer getInstallments() {
		return installments;
	}
	public void setInstallments(Integer installments) {
		this.installments = installments;
	}
	public String getCampaign() {
		return campaign;
	}
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	public String getPncdSelected() {
		return pncdSelected;
	}
	public void setPncdSelected(String pncdSelected) {
		this.pncdSelected = pncdSelected;
	}
	public Date getInceptionDate() {
		return inceptionDate;
	}
	public void setInceptionDate(Date inceptionDate) {
		this.inceptionDate = inceptionDate;
	}
	public Date getRateFromDate() {
		return rateFromDate;
	}
	public void setRateFromDate(Date rateFromDate) {
		this.rateFromDate = rateFromDate;
	}
	public Integer getRenewalYears() {
		return renewalYears;
	}
	public void setRenewalYears(Integer renewalYears) {
		this.renewalYears = renewalYears;
	}
	public Boolean getOtherVehiclesInsuredWithUs() {
		return otherVehiclesInsuredWithUs;
	}
	public void setOtherVehiclesInsuredWithUs(Boolean otherVehiclesInsuredWithUs) {
		this.otherVehiclesInsuredWithUs = otherVehiclesInsuredWithUs;
	}
	public InboundRatingInfoDTO getRatingInfo() {
		return ratingInfo;
	}
	public void setRatingInfo(InboundRatingInfoDTO ratingInfo) {
		this.ratingInfo = ratingInfo;
	}
	public Integer getRateVersion() {
		return rateVersion;
	}
	public void setRateVersion(Integer rateVersion) {
		this.rateVersion = rateVersion;
	}
	public InboundPremiumDTO getPremium() {
		return premium;
	}
	public void setPremium(InboundPremiumDTO premium) {
		this.premium = premium;
	}
	public String getGoodDriverClass() {
		return goodDriverClass;
	}
	public void setGoodDriverClass(String goodDriverClass) {
		this.goodDriverClass = goodDriverClass;
	}
	public String getInnerClassFrom() {
		return innerClassFrom;
	}
	public void setInnerClassFrom(String innerClassFrom) {
		this.innerClassFrom = innerClassFrom;
	}
	public String getInnerClass() {
		return innerClass;
	}
	public void setInnerClass(String innerClass) {
		this.innerClass = innerClass;
	}
	public Boolean getClaimsInLast5YearInner() {
		return claimsInLast5YearInner;
	}
	public void setClaimsInLast5YearInner(Boolean claimsInLast5YearInner) {
		this.claimsInLast5YearInner = claimsInLast5YearInner;
	}
	public Integer getClaimsInLastYearInner() {
		return claimsInLastYearInner;
	}
	public void setClaimsInLastYearInner(Integer claimsInLastYearInner) {
		this.claimsInLastYearInner = claimsInLastYearInner;
	}
	public InboundOtherVehicleDTO getOtherVehicle() {
		return otherVehicle;
	}
	public void setOtherVehicle(InboundOtherVehicleDTO otherVehicle) {
		this.otherVehicle = otherVehicle;
	}
	public String getPreviousInsuranceCompany() {
		return previousInsuranceCompany;
	}
	public void setPreviousInsuranceCompany(String previousInsuranceCompany) {
		this.previousInsuranceCompany = previousInsuranceCompany;
	}
	/**
	 * @return the driverNumber
	 */
	public Integer getDriverNumber() {
		return driverNumber;
	}
	/**
	 * @param driverNumber the driverNumber to set
	 */
	public void setDriverNumber(Integer driverNumber) {
		this.driverNumber = driverNumber;
	}
	/**
	 * @return the clean1
	 */
	public Boolean getClean1() {
		return clean1;
	}
	/**
	 * @param clean1 the clean1 to set
	 */
	public void setClean1(Boolean clean1) {
		this.clean1 = clean1;
	}
	
	
	
	
	
	
	
	

}
