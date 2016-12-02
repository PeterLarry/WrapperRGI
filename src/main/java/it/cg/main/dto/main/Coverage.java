package it.cg.main.dto.main;

public class Coverage {
	
	private Boolean selected;
	private String code;
	private Limit limit;
	private Coinsurance coinsurance;
	private Deductible deductible;
	private Premium amount;
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
	public Limit getLimit() {
		return limit;
	}
	public void setLimit(Limit limit) {
		this.limit = limit;
	}
	public Coinsurance getCoinsurance() {
		return coinsurance;
	}
	public void setCoinsurance(Coinsurance coinsurance) {
		this.coinsurance = coinsurance;
	}
	public Deductible getDeductible() {
		return deductible;
	}
	public void setDeductible(Deductible deductible) {
		this.deductible = deductible;
	}
	public Premium getAmount() {
		return amount;
	}
	public void setAmount(Premium amount) {
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
