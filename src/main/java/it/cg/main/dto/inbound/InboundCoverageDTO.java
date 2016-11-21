package it.cg.main.dto.inbound;

public class InboundCoverageDTO {
	
	private Boolean selected;
	private String code;
	private InboundLimitDTO limit;
	private InboundCoinsuranceDTO coinsurance;
	private InboundDeductibleDTO deductible;
	private InboundPremiumDTO amount;
	private Double taxRate;
	private Double fiddleFactor;
	
	
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public InboundLimitDTO getLimit() {
		return limit;
	}
	public void setLimit(InboundLimitDTO limit) {
		this.limit = limit;
	}
	public InboundCoinsuranceDTO getCoinsurance() {
		return coinsurance;
	}
	public void setCoinsurance(InboundCoinsuranceDTO coinsurance) {
		this.coinsurance = coinsurance;
	}
	public InboundDeductibleDTO getDeductible() {
		return deductible;
	}
	public void setDeductible(InboundDeductibleDTO deductible) {
		this.deductible = deductible;
	}
	public InboundPremiumDTO getAmount() {
		return amount;
	}
	public void setAmount(InboundPremiumDTO amount) {
		this.amount = amount;
	}
	public Double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	public Double getFiddleFactor() {
		return fiddleFactor;
	}
	public void setFiddleFactor(Double fiddleFactor) {
		this.fiddleFactor = fiddleFactor;
	}

	
}
