package it.cg.main.dto.inbound;

public class InboundOtherVehicleDTO {
	
	private String province;
	private String bmClass;
	private Integer taxableHorsePower;
	private Integer ccCapacity;
	private Integer numberOfCitizens;
	private String selectedDeductible;
	private String selectedLimit;
	private Integer weight;
	private Integer numberOfSeats;
	private Boolean ownBehalf;
	
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getBmClass() {
		return bmClass;
	}
	public void setBmClass(String bmClass) {
		this.bmClass = bmClass;
	}
	public Integer getTaxableHorsePower() {
		return taxableHorsePower;
	}
	public void setTaxableHorsePower(Integer taxableHorsePower) {
		this.taxableHorsePower = taxableHorsePower;
	}
	public Integer getCcCapacity() {
		return ccCapacity;
	}
	public void setCcCapacity(Integer ccCapacity) {
		this.ccCapacity = ccCapacity;
	}
	public Integer getNumberOfCitizens() {
		return numberOfCitizens;
	}
	public void setNumberOfCitizens(Integer numberOfCitizens) {
		this.numberOfCitizens = numberOfCitizens;
	}
	public String getSelectedDeductible() {
		return selectedDeductible;
	}
	public void setSelectedDeductible(String selectedDeductible) {
		this.selectedDeductible = selectedDeductible;
	}
	public String getSelectedLimit() {
		return selectedLimit;
	}
	public void setSelectedLimit(String selectedLimit) {
		this.selectedLimit = selectedLimit;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	public Boolean getOwnBehalf() {
		return ownBehalf;
	}
	public void setOwnBehalf(Boolean ownBehalf) {
		this.ownBehalf = ownBehalf;
	}
	
	

}
