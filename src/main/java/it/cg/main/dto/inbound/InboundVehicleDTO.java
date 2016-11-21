package it.cg.main.dto.inbound;

import java.util.Date;

public class InboundVehicleDTO {
	
	private Integer purchaseYear;
	private Integer vehicleAge;
	private Integer kmPerYear;
	private Integer kmPerDay;
	private String shelterType;
	private String antitheftType;
	private Date matriculationYearMonth;
	private String habitualUse;
	private InboundTechnicalDataDTO technicalData;
	private Integer insuredValue;
	private Integer carAgeAtPurchase;
	
	
	public Integer getPurchaseYear() {
		return purchaseYear;
	}
	public void setPurchaseYear(Integer purchaseYear) {
		this.purchaseYear = purchaseYear;
	}
	public Integer getVehicleAge() {
		return vehicleAge;
	}
	public void setVehicleAge(Integer vehicleAge) {
		this.vehicleAge = vehicleAge;
	}
	public Integer getKmPerYear() {
		return kmPerYear;
	}
	public void setKmPerYear(Integer kmPerYear) {
		this.kmPerYear = kmPerYear;
	}
	public Integer getKmPerDay() {
		return kmPerDay;
	}
	public void setKmPerDay(Integer kmPerDay) {
		this.kmPerDay = kmPerDay;
	}
	public String getShelterType() {
		return shelterType;
	}
	public void setShelterType(String shelterType) {
		this.shelterType = shelterType;
	}
	public String getAntitheftType() {
		return antitheftType;
	}
	public void setAntitheftType(String antitheftType) {
		this.antitheftType = antitheftType;
	}
	public Date getMatriculationYearMonth() {
		return matriculationYearMonth;
	}
	public void setMatriculationYearMonth(Date matriculationYearMonth) {
		this.matriculationYearMonth = matriculationYearMonth;
	}
	public String getHabitualUse() {
		return habitualUse;
	}
	public void setHabitualUse(String habitualUse) {
		this.habitualUse = habitualUse;
	}
	public InboundTechnicalDataDTO getTechnicalData() {
		return technicalData;
	}
	public void setTechnicalData(InboundTechnicalDataDTO technicalData) {
		this.technicalData = technicalData;
	}
	public Integer getInsuredValue() {
		return insuredValue;
	}
	public void setInsuredValue(Integer insuredValue) {
		this.insuredValue = insuredValue;
	}
	/**
	 * @return the carAgeAtPurchase
	 */
	public Integer getCarAgeAtPurchase() {
		return carAgeAtPurchase;
	}
	/**
	 * @param carAgeAtPurchase the carAgeAtPurchase to set
	 */
	public void setCarAgeAtPurchase(Integer carAgeAtPurchase) {
		this.carAgeAtPurchase = carAgeAtPurchase;
	}
	
	

}
